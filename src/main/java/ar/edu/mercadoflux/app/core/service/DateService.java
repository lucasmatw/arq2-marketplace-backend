package ar.edu.mercadoflux.app.core.service;

import ar.edu.mercadoflux.app.ports.output.persistence.entities.BaseEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class DateService {
    private BaseEntity baseEntity;
    public LocalDateTime getNowDate() {
        new BaseEntity();
        return LocalDateTime.now();
    }
}
