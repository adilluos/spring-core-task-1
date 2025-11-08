//package com.adilzhan.firsttask.exception;
//
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.ControllerAdvice;
//import org.springframework.web.bind.annotation.ExceptionHandler;
//
//@Slf4j
//@ControllerAdvice
//public class GlobalExceptionHandler {
//    @ExceptionHandler
//    public ResponseEntity<String> handleException(Exception e) {
//        log.error(":( Unhandled exception: {}", e.getMessage(), e);
//        return ResponseEntity.internalServerError().body("Internal Error");
//    }
//}


//Might change this code later