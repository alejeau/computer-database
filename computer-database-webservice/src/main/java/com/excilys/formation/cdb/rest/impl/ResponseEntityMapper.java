package com.excilys.formation.cdb.rest.impl;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class ResponseEntityMapper {

    public static <T> ResponseEntity<T> toResponseEntity(T object) {
        if (object == null) {
            return new ResponseEntity<T>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<T>(object, HttpStatus.OK);
    }

    public static <T> ResponseEntity<List<T>> toListResponseEntity(List<T> list) {
        if (list.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(list, HttpStatus.OK);
    }
}
