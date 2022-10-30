package ewmmainservice.common.exception;

public class DataNotFoundException extends RuntimeException {

    public DataNotFoundException(String message) {
        super(message);
    }

    public String getMassage() {
        return super.getMessage();
    }
}
