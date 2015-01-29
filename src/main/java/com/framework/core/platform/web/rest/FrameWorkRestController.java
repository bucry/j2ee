package com.framework.core.platform.web.rest;

import java.util.List;
import java.util.Locale;

import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import com.framework.core.exception.InvalidRequestException;
import com.framework.core.platform.exception.ResourceNotFoundException;
import com.framework.core.platform.exception.UserAuthorizationException;
import com.framework.core.platform.web.DefaultController;
import com.framework.core.utils.ExceptionUtils;

/**
 * @author neo
 */
public class FrameWorkRestController extends DefaultController {
    
    @ExceptionHandler
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ResponseBody
    public ErrorResponse error(Throwable e) {
        ErrorResponse error = new ErrorResponse();
        error.setMessage(e.getMessage());
        error.setExceptionTrace(ExceptionUtils.stackTrace(e));
        return error;
    }
    
    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    public ErrorResponse validationError(MethodArgumentNotValidException e) {
        return createValidationResponse(e.getBindingResult());
    }

    @ExceptionHandler(BindException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    public ErrorResponse validationError(BindException e) {
        return createValidationResponse(e.getBindingResult());
    }

    private ErrorResponse createValidationResponse(BindingResult bindingResult) {
        ErrorResponse error = new ErrorResponse();
        Locale locale = LocaleContextHolder.getLocale();
        List<org.springframework.validation.FieldError> fieldErrors = bindingResult.getFieldErrors();
        StringBuilder msg = new StringBuilder();
        for (org.springframework.validation.FieldError fieldError : fieldErrors) {
            msg.append(fieldError.getField()).append("=>").append(messages.getMessage(fieldError, locale)).append("/n");
        }
        error.setMessage("Arguments invalid.");
        error.setExceptionTrace(msg.toString());
        return error;
    }

    @ExceptionHandler(InvalidRequestException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    public ErrorResponse validationError(InvalidRequestException e) {
        Locale locale = LocaleContextHolder.getLocale();
        ErrorResponse error = new ErrorResponse();
        StringBuilder msg = new StringBuilder();
        msg.append(e.getField()).append("=>").append(messages.getMessage(e.getMessage(), locale));
        error.setMessage(msg.toString());
        error.setExceptionTrace(msg.toString());
        return error;
    }

    @ExceptionHandler(ResourceNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ResponseBody
    public ErrorResponse notFound(ResourceNotFoundException e) {
        ErrorResponse error = new ErrorResponse();
        error.setMessage(e.getMessage());
        error.setExceptionTrace(ExceptionUtils.stackTrace(e));
        return error;
    }

    @ExceptionHandler(UserAuthorizationException.class)
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    @ResponseBody
    public ErrorResponse unauthorized(UserAuthorizationException e) {
        ErrorResponse error = new ErrorResponse();
        error.setMessage(e.getMessage());
        error.setExceptionTrace(ExceptionUtils.stackTrace(e));
        return error;
    }
    
}