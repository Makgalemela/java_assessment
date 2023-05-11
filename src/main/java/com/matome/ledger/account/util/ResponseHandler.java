package com.matome.ledger.account.util;


import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ResponseHandler {

    public static final String DATA = "data";
    public static final String STATUS = "status";
    public static final String IS_SUCCESS = "isSuccess";
    public static final String MESSAGE = "message";
    public static final String TIMESTAMP = "timestamp";

    public static final String SIM_DATA ="Simulated";

    public static ResponseEntity<Object> generateResponse(HttpStatus status, boolean isSuccess, String message,
                                                          Object data) {
        Map<String, Object> map = new HashMap<>();
        try {
            map.put(TIMESTAMP, LocalDateTime.now());
            map.put(STATUS, status.value());
            map.put(IS_SUCCESS, isSuccess);
            map.put(MESSAGE, message);
            map.put(DATA, data);

            return new ResponseEntity<Object>(map, status);
        } catch (Exception e) {
            map.clear();
            map.put(TIMESTAMP, LocalDateTime.now());
            map.put(STATUS, HttpStatus.INTERNAL_SERVER_ERROR.value());
            map.put(IS_SUCCESS, false);
            map.put(MESSAGE, e.getMessage());
            map.put(DATA, null);
            return new ResponseEntity<Object>(map, status);
        }
    }

    public static ResponseEntity<Object> generateResponse(HttpStatus status, boolean isSuccess, String message,
                                                          Object data, Object additional) {
        Map<String, Object> map = new HashMap<>();
        List<Object> simulated = new ArrayList<>();
        simulated.add(additional);

        try {
            map.put(TIMESTAMP, LocalDateTime.now());
            map.put(STATUS, status.value());
            map.put(IS_SUCCESS, isSuccess);
            map.put(MESSAGE, message);
            map.put(DATA, data);
            map.put(SIM_DATA, additional);

            return new ResponseEntity<Object>(map, status);
        } catch (Exception e) {
            map.clear();
            map.put(TIMESTAMP, LocalDateTime.now());
            map.put(STATUS, HttpStatus.INTERNAL_SERVER_ERROR.value());
            map.put(IS_SUCCESS, false);
            map.put(MESSAGE, e.getMessage());
            map.put(DATA, null);
            return new ResponseEntity<Object>(map, status);
        }
    }

    public static ResponseEntity<Object> generateResponse(HttpStatus status, boolean isSuccess, String message) {
        Map<String, Object> map = new HashMap<String, Object>();
        try {
            map.put(STATUS, status.value());
            map.put(IS_SUCCESS, isSuccess);
            map.put(MESSAGE, message);
            map.put(TIMESTAMP, LocalDateTime.now());
            return new ResponseEntity<Object>(map, status);
        } catch (Exception e) {
            map.clear();
            map.put(TIMESTAMP, LocalDateTime.now());
            map.put(STATUS, HttpStatus.INTERNAL_SERVER_ERROR.value());
            map.put(IS_SUCCESS, false);
            map.put(MESSAGE, e.getMessage());
            map.put(DATA, null);
            return new ResponseEntity<Object>(map, status);
        }
    }
}
