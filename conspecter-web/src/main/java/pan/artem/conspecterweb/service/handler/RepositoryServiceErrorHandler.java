package pan.artem.conspecterweb.service.handler;

import org.springframework.http.HttpStatus;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.web.client.ResponseErrorHandler;
import pan.artem.conspecterweb.exception.RepositoryInitializationException;
import pan.artem.conspecterweb.exception.RepositoryServiceInternalErrorException;
import pan.artem.conspecterweb.exception.ResourceNotFoundException;

import java.io.IOException;

public class RepositoryServiceErrorHandler implements ResponseErrorHandler {

    @Override
    public boolean hasError(ClientHttpResponse response) throws IOException {
        return response.getStatusCode().is5xxServerError() ||
                response.getStatusCode().is4xxClientError();
    }

    @Override
    public void handleError(ClientHttpResponse response) throws IOException {
        var statusCode = response.getStatusCode();
        if (statusCode.is5xxServerError()) {
            throw new RepositoryServiceInternalErrorException(response.getStatusText());
        } else if (statusCode.is4xxClientError()) {
            if (statusCode == HttpStatus.NOT_FOUND) {
                throw new ResourceNotFoundException();
            } else {
                throw new RepositoryInitializationException(response.getBody());
            }
        }
    }
}
