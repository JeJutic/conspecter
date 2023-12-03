package pan.artem.conspecterrepo.exception;

public class RepoInitializationException extends AppException {

    public RepoInitializationException(String message) {
        super(message);
    }

    public RepoInitializationException(String message, Throwable cause) {
        super(message, cause);
    }
}
