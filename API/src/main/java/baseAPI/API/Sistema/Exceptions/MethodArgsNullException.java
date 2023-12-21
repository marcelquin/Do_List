package baseAPI.API.Sistema.Exceptions;

public class MethodArgsNullException extends RuntimeException {
    private static final long servialVersionUID = 1L;
    public MethodArgsNullException(String message){
        super(message);
    }
    public MethodArgsNullException(Throwable cause) {
        super(cause);
    }
    public MethodArgsNullException(String message, Throwable cause) {
        super(message, cause);
    }
}
