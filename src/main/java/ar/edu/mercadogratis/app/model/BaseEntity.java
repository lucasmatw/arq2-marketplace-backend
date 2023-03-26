package ar.edu.mercadogratis.app.model;

import org.springframework.data.annotation.Id;

public class BaseEntity {
    @Id
    protected Long id;
}
