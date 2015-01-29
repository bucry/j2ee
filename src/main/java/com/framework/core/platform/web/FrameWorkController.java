package com.framework.core.platform.web;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.krysalis.barcode4j.servlet.BarcodeServlet;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import com.framework.core.platform.exception.ResourceNotFoundException;
import com.framework.core.platform.exception.UserAuthorizationException;

public class FrameWorkController extends BarcodeServlet implements SpringController {

    @ExceptionHandler(UserAuthorizationException.class)
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    public ModelAndView unauthorized(UserAuthorizationException e) {
        Map<String, Throwable> map = new HashMap<String, Throwable>();
        map.put("authorizationException", e);
        return new ModelAndView("default-403", map);
    }

    @ExceptionHandler(ResourceNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ModelAndView notFound(ResourceNotFoundException e) {
        Map<String, Throwable> map = new HashMap<String, Throwable>();
        map.put("exception", e);
        return new ModelAndView("default-404", map);
    }

    @ExceptionHandler
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ModelAndView resolveException(Throwable e) {
        Map<String, Throwable> map = new HashMap<String, Throwable>();
        map.put("exception", e);
        return new ModelAndView("default-error", map);
    }
    
    public boolean validateImageFile(List<MultipartFile> imageFiles) {
        for (MultipartFile imageFile : imageFiles) {
            boolean result = false;
            String contentType = imageFile.getContentType();
            for (String ct : new String[]{"image/jpeg", "image/pjpeg", "image/x-png", "image/gif", "image/png"}) {
                if (contentType.equalsIgnoreCase(ct.trim())) {
                    result = true;
                    break;
                }
            }
            if (!result)
                return false;
        }
        return true;
    }
    
}