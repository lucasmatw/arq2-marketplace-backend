package ar.edu.mercadoflux.app.core.service;

import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class DateService {
    public LocalDateTime getNowDate() {
        return LocalDateTime.now();
    }
}
