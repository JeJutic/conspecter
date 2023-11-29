package pan.artem.conspecterrepo.controller.handler;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import pan.artem.conspecterrepo.exception.ResourceNotFoundException;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<?> handleNotFound(
//            HttpServletRequest request, ResourceNotFoundException e
    ) {
        return ResponseEntity.status(404).build();
    }
}
