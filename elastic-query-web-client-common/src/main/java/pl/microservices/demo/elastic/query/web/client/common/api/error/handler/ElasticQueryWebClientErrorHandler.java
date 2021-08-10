package pl.microservices.demo.elastic.query.web.client.common.api.error.handler;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.ui.Model;
import org.springframework.validation.BindException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import pl.microservices.demo.elastic.query.web.client.common.model.ElasticQueryWebClientRequestModel;


import java.util.HashMap;
import java.util.Map;


@ControllerAdvice
public class ElasticQueryWebClientErrorHandler {

    private static final Logger LOG = LoggerFactory.getLogger(ElasticQueryWebClientErrorHandler.class);

    @ExceptionHandler(AccessDeniedException.class)
    public String handle(AccessDeniedException exception, Model model){
        LOG.error("Access denied exception!", exception);
        model.addAttribute("error", HttpStatus.UNAUTHORIZED.getReasonPhrase());
        model.addAttribute("error_description", "You are not authorized to access this resource");
        return "error";
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public String handle(IllegalArgumentException exception, Model model){
        LOG.error("Illegal argument exception!", exception);
        model.addAttribute("error", HttpStatus.BAD_REQUEST.getReasonPhrase());
        model.addAttribute("error_description", "Illegal Argument Exception!" + exception.getMessage());
        return "error";
    }

    @ExceptionHandler(Exception.class)
    public String handle(Exception exception, Model model){
        LOG.error("Internal server error!", exception);
        model.addAttribute("error", HttpStatus.INTERNAL_SERVER_ERROR.getReasonPhrase());
        model.addAttribute("error_description", "A server error occurred! ");
        return "error";
    }

    @ExceptionHandler(RuntimeException.class)
    public String handle(RuntimeException exception, Model model){
        LOG.error("Service runtime exception!", exception);
        model.addAttribute("elasticQueryClientRequestModel", ElasticQueryWebClientRequestModel.builder().build());
        model.addAttribute("error", "Could not get response! " + exception.getMessage());
        model.addAttribute("error_description", "Service runtime exception!" + exception.getMessage());
        return "home";
    }

    @ExceptionHandler(BindException.class)
    public String handle(BindException exception, Model model){
        LOG.error("Method argument not valid exception", exception);
        Map<String, String> errors = new HashMap<>();
        exception.getBindingResult().getAllErrors().forEach(error -> errors.put(((FieldError)error).getField(), error.getDefaultMessage()));

        model.addAttribute("elasticQueryClientRequestModel", ElasticQueryWebClientRequestModel.builder().build());
        model.addAttribute("error", HttpStatus.BAD_REQUEST.getReasonPhrase());
        model.addAttribute("error_description", errors);
        return "home";
    }

}
