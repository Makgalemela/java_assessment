package com.matome.ledger.account.filters;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import com.matome.ledger.account.model.LogObject;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.util.ContentCachingRequestWrapper;
import org.springframework.web.util.ContentCachingResponseWrapper;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

@Component
//@JsonSerialize(using = LocalDateTimeSerializer.class)
//@JsonDeserialize(using = LocalDateTimeDeserializer.class)
@Slf4j
public class LoggingFilter /* extends OncePerRequestFilter */ {

//    @Override
//    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
//            throws ServletException, IOException {
//        ContentCachingRequestWrapper requestWrapper = new ContentCachingRequestWrapper(request);
//        ContentCachingResponseWrapper responseWrapper = new ContentCachingResponseWrapper(response);
//        log.info("======================================================");
//        log.info(request.getRequestURI());
//        if(!request.getRequestURI().equalsIgnoreCase("/swagger-ui/index.html")) {
//
//            LocalDateTime startTime = LocalDateTime.now();
//        filterChain.doFilter(requestWrapper, responseWrapper);
//        LocalDateTime completionTime = LocalDateTime.now();
//
//        String requestBody = getStringValue(requestWrapper.getContentAsByteArray(),
//                request.getCharacterEncoding());
//        String responseBody = getStringValue(responseWrapper.getContentAsByteArray(),
//                response.getCharacterEncoding());
//
//        ObjectMapper objectMapper = new ObjectMapper();
//        objectMapper.configure(DeserializationFeature.ACCEPT_EMPTY_STRING_AS_NULL_OBJECT, true);
//
//        Object requestObject = null;
//
////        if(!requestBody.isBlank() && !responseBody.isEmpty()) {
////            objectMapper.readValue(requestBody, Object.class);
////        }
////        Object responseObject = objectMapper.readValue(responseBody, Object.class);
////
////            LogObject  logObject = LogObject.builder()
////                    .responseTime(String.valueOf(ChronoUnit.MILLIS.between(startTime, completionTime)))
////                    .path(request.getRequestURI())
////                    .hostname(request.getLocalAddr())
////                    .httpMethod(request.getMethod())
////                    .requestBody(String.valueOf(requestObject))
////                    .responseBody(String.valueOf(responseObject))
////                    .statusCode(String.valueOf(response.getStatus()))
////                    .build();
////
////            log.info(String.valueOf(logObject));
//
//        }
//
//        responseWrapper.copyBodyToResponse();
//
//    }
//
//    private String getStringValue(byte[] contentAsByteArray, String characterEncoding) throws UnsupportedEncodingException {
//        try {
//            return new String(contentAsByteArray, 0, contentAsByteArray.length, characterEncoding);
//        } catch (UnsupportedEncodingException e) {
//            e.printStackTrace();
//        }
//        return "";
//    }

}
