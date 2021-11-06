package ar.edu.mercadogratis.app.service;

public interface IEmailService {
    void send(String receiver, String subject, String message);
}
