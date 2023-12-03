package pan.artem.conspecterweb.controller.handler;

import io.github.resilience4j.ratelimiter.RequestNotPermitted;
import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.NoHandlerFoundException;
import pan.artem.conspecterweb.exception.RepositoryInitializationException;
import pan.artem.conspecterweb.exception.RepositoryServiceInternalErrorException;
import pan.artem.conspecterweb.exception.ResourceNotFoundException;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.security.Principal;

@ControllerAdvice
public class GlobalExceptionHandler {

    private final Logger logger = LoggerFactory.getLogger(getClass());

    @ExceptionHandler({ RequestNotPermitted.class })
    @ResponseStatus(HttpStatus.TOO_MANY_REQUESTS)
    public void handleRequestNotPermitted() {
    }

    @ExceptionHandler({ NoHandlerFoundException.class })
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public String handleNotFound() {
        return "Specified resource not found";
    }

    @ExceptionHandler({ RepositoryServiceInternalErrorException.class })
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public void handleRepositoryServiceInternal(RepositoryServiceInternalErrorException e) {
        logger.info("Unexpected 5xx response from repository-service", e);
    }

    @ExceptionHandler({ RepositoryInitializationException.class })
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public void handleRepositoryInitialization() {
    }
}
