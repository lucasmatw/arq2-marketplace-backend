package ar.edu.mercadoflux.app.ports.output.persistence.adapter;

import ar.edu.mercadoflux.app.core.domain.Product;
import ar.edu.mercadoflux.app.core.dto.SearchProduct;
import ar.edu.mercadoflux.app.core.dto.SearchProductInternal;
import ar.edu.mercadoflux.app.core.repository.ProductRepository;
import ar.edu.mercadoflux.app.core.repository.UserRepository;
import ar.edu.mercadoflux.app.ports.output.persistence.entities.ProductDocument;
import ar.edu.mercadoflux.app.ports.output.persistence.repository.mongo.ReactiveProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.mongodb.core.ReactiveMongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.Optional;
import java.util.stream.Stream;

@Service
@RequiredArgsConstructor
public class ProductRepositoryMongoAdapter implements ProductRepository {

    private final static String NAME_FIELD = "name";
    private final static String CATEGORY_FIELD = "category";
    private final static String PRICE_FIELD = "price";
    private final static String STATUS_FIELD = "status";

    private final ReactiveMongoTemplate mongoTemplate;
    private final ReactiveProductRepository productRepository;
    private final UserRepository userRepository;

    @Override
    public Flux<Product> findBySeller(String sellerId) {
        Flux<ProductDocument> productsBySeller = productRepository.findBySellerId(sellerId);
        return userRepository.findById(sellerId)
                .flatMapMany(seller -> productsBySeller
                        .map(product -> product.toProduct(seller)));

    }

    @Override
    public Mono<Product> findById(String productId) {
        return productRepository.findById(productId)
                .flatMap(productDocument -> userRepository.findById(productDocument.getSellerId())
                        .map(productDocument::toProduct));
    }

    @Override
    public Mono<Product> save(Product product) {
        return productRepository.save(toProductDocument(product))
                .map(productDocument -> productDocument.toProduct(product.getSeller()));
    }

    @Override
    public Flux<Product> search(SearchProductInternal searchProduct) {
        Query searchQuery = Query.query(buildCriteria(searchProduct));
        Flux<ProductDocument> productDocumentFlux = mongoTemplate.find(searchQuery, ProductDocument.class);
        return productDocumentFlux.flatMap(productDocument -> userRepository.findById(productDocument.getSellerId())
                .map(productDocument::toProduct));
    }

    private Criteria buildCriteria(SearchProductInternal searchProductInternal) {
        Criteria criteria = new Criteria();
        SearchProduct searchProduct = searchProductInternal.getSearchProduct();

        Optional<Criteria> nameCriteria = searchProduct.getName()
                .map(name -> Criteria.where(NAME_FIELD).regex(name, "i"));
        Optional<Criteria> categoryCriteria = searchProduct.getCategory()
                .map(category -> Criteria.where(CATEGORY_FIELD).regex(category.name()));
        Optional<Criteria> minPriceCriteria = searchProduct.getMinPrice()
                .map(minPrice -> Criteria.where(PRICE_FIELD).gte(minPrice));
        Optional<Criteria> maxPriceCriteria = searchProduct.getMaxPrice()
                .map(maxPrice -> Criteria.where(PRICE_FIELD).lte(maxPrice));

        Optional<Criteria> statusCriteria = Optional.of(Criteria.where(STATUS_FIELD).regex(searchProductInternal.getStatus().name()));

        Stream<Optional<Criteria>> criterias = Stream.of(nameCriteria, categoryCriteria,
                maxPriceCriteria, minPriceCriteria, statusCriteria);

        return criteria.andOperator(criterias.filter(Optional::isPresent)
                .map(Optional::get)
                .toList());
    }

    private ProductDocument toProductDocument(Product product) {
        return ProductDocument.builder()
                .id(product.getId())
                .name(product.getName())
                .description(product.getDescription())
                .category(product.getCategory())
                .price(product.getPrice())
                .stock(product.getStock())
                .sellerId(product.getSeller().getId())
                .status(product.getStatus())
                .build();
    }
}
