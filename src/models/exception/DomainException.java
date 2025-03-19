package models.exception;

/**
 * Класът {@code DomainException} представлява потребителско изключение,
 * Използва се за сигнализиране на грешки, свързани с бизнес логиката на приложението.
 */
public class DomainException extends RuntimeException {
    public DomainException(String message) {
        super(message);
    }
}
