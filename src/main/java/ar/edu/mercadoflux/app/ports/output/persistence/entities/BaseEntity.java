package ar.edu.mercadoflux.app.ports.output.persistence.entities;

import org.springframework.data.annotation.Id;

public class BaseEntity {
    @Id
    protected Long id;
}
