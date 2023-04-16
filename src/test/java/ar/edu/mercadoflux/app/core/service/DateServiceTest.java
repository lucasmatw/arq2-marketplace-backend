package ar.edu.mercadoflux.app.core.service;

import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class DateServiceTest {

    @InjectMocks
    private DateService dateService;

    @Test
    void getNowDateShouldMatchNowDate() {
        // given
        LocalDateTime beforeDate = LocalDateTime.now();
        // when
        LocalDateTime nowDate = dateService.getNowDate();

        // then
        assertThat(nowDate).isNotNull();
        assertThat(nowDate).isBefore(LocalDateTime.now());
        assertThat(nowDate).isAfter(beforeDate);
    }
}