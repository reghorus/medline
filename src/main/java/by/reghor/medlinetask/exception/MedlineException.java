package by.reghor.medlinetask.exception;

public class MedlineException extends RuntimeException {
    public MedlineException() {
        super();
    }

    public MedlineException(String message) {
        super(message);
    }

    public MedlineException(String message, Throwable cause) {
        super(message, cause);
    }

    public MedlineException(Throwable cause) {
        super(cause);
    }

    protected MedlineException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
