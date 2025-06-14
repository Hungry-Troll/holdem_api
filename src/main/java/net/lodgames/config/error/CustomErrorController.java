package net.lodgames.config.error;

import net.lodgames.config.error.exception.RestException;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.boot.autoconfigure.web.servlet.error.AbstractErrorController;
import org.springframework.boot.web.error.ErrorAttributeOptions;
import org.springframework.boot.web.servlet.error.ErrorAttributes;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collections;
import java.util.Map;

@RestController
@RequestMapping({CustomErrorController.ERROR_PATH})
public class CustomErrorController extends AbstractErrorController {

    static final String ERROR_PATH = "/api/error";

    public CustomErrorController(final ErrorAttributes errorAttributes) {
        super(errorAttributes, Collections.emptyList());
    }

    @RequestMapping
    public ResponseEntity<Map<String, Object>> error(HttpServletRequest request, RestException e) {
        Map<String, Object> body = this.getErrorAttributes(request, ErrorAttributeOptions.defaults()
                .including(ErrorAttributeOptions.Include.MESSAGE));
        HttpStatus status = this.getStatus(request);
        return new ResponseEntity<>(body, status);
    }

}
