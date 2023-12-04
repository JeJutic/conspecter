package pan.artem.conspecterrepo.controller.handler;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import pan.artem.conspecterrepo.exception.RepoInitializationException;
import pan.artem.conspecterrepo.exception.ResourceNotFoundException;

@ControllerAdvice
public class GlobalExceptionHandler {

    private final Logger logger = LoggerFactory.getLogger(getClass());

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<?> handleNotFound(
//            HttpServletRequest request, ResourceNotFoundException e
    ) {
        return ResponseEntity.status(404).build();
    }

    @ExceptionHandler(RepoInitializationException.class)
    public ResponseEntity<RepoInitializationException> handleRepoInitialization(
            RepoInitializationException e
    ) {
        logger.info("Repo initialization exception caught", e);
        return ResponseEntity.status(400).body(e);
    }
}
