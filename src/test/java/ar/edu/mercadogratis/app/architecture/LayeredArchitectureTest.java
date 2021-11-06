package ar.edu.mercadogratis.app.architecture;

import com.tngtech.archunit.junit.AnalyzeClasses;
import com.tngtech.archunit.junit.ArchTest;
import com.tngtech.archunit.lang.ArchRule;

import static com.tngtech.archunit.library.Architectures.layeredArchitecture;

@AnalyzeClasses(packages = "ar.edu.mercadogratis.app")
public class LayeredArchitectureTest {

    private static final String ROOT_PKG = "ar.edu.mercadogratis.app";

    @ArchTest
    static final ArchRule layeredDependenciesRespected = layeredArchitecture()

            .layer("Controllers").definedBy(ROOT_PKG + ".controller..")
            .layer("Services").definedBy(ROOT_PKG + ".service..")
            .layer("Persistence").definedBy(ROOT_PKG + ".dao..")

            .whereLayer("Controllers").mayNotBeAccessedByAnyLayer()
            .whereLayer("Services").mayOnlyBeAccessedByLayers("Controllers")
            .whereLayer("Persistence").mayOnlyBeAccessedByLayers("Services");
}
