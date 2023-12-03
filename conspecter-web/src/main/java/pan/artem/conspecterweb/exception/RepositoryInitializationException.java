package pan.artem.conspecterweb.exception;

import lombok.Getter;

import java.io.InputStream;

@Getter
public class RepositoryInitializationException extends AppException {

    private final InputStream errorInfo;

    public RepositoryInitializationException(InputStream errorInfo) {
        this.errorInfo = errorInfo;
    }
}
