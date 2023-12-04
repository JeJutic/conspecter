package pan.artem.conspecterweb.exception;

public class RepositoryServiceInternalErrorException extends AppException {

    private final String statusText;

    public RepositoryServiceInternalErrorException(String statusText) {
        this.statusText = statusText;
    }
}
