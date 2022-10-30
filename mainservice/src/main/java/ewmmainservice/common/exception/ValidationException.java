package ewmmainservice.common.exception;

public class ValidationException extends RuntimeException {

    public ValidationException(String message) {
        super(message);
    }

    public String getMassage() {
        return super.getMessage();
    }
}
