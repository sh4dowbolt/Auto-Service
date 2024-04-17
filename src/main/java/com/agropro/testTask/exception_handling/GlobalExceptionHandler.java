package com.agropro.testTask.exception_handling;


import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.tomcat.util.http.fileupload.impl.FileSizeLimitExceededException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.multipart.MaxUploadSizeExceededException;
import org.springframework.web.multipart.MultipartException;
import org.springframework.web.servlet.FlashMap;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.support.RequestContextUtils;

import java.io.IOException;


@ControllerAdvice
public class GlobalExceptionHandler {
    @Autowired
    private MessageSource messageSource;

    @ExceptionHandler(value = MultipartException.class)
    public ModelAndView handleMultipartException(Exception ex, HttpServletRequest request) {
        ModelAndView model = new ModelAndView("error");
        FlashMap flash = RequestContextUtils.getOutputFlashMap(request);
        if (ex instanceof MultipartException) {
            MultipartException mEx = (MultipartException) ex;

            if (ex.getCause() instanceof FileSizeLimitExceededException) {
                FileSizeLimitExceededException flEx = (FileSizeLimitExceededException) mEx.getCause();
                float permittedSize = flEx.getPermittedSize() / 1024;
                String message = messageSource.getMessage(
                        "file.maxsize",
                        new Object[]{flEx.getFileName(), permittedSize},
                        LocaleContextHolder.getLocale());
                flash.put("errors", message);
            } else {
                flash.put("errors", "Please contact your administrator: " + ex.getMessage());
            }
        } else {
            flash.put("errors", "Please contact your administrator: " + ex.getMessage());
        }
        return model;
    }

    @ExceptionHandler(value = IOException.class)
    public ModelAndView handleIOException(Exception ex, HttpServletRequest request){
        ModelAndView modelAndView = new ModelAndView("errors");
        FlashMap flash = RequestContextUtils.getOutputFlashMap(request);
        flash.put("errors", "Please contact your administrator: " + ex.getMessage());
        return modelAndView;
    }



    @ExceptionHandler(MaxUploadSizeExceededException.class)
    public ModelAndView handleMaxSizeException(
            MaxUploadSizeExceededException exc,
            HttpServletRequest request,
            HttpServletResponse response) {
        ModelAndView modelAndView = new ModelAndView("errors");
        modelAndView.getModel().put("message", "File too large!");
        return modelAndView;
    }

    }




