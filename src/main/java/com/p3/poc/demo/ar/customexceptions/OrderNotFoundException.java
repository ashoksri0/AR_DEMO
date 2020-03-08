package com.p3.poc.demo.ar.customexceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Created by Suriyanarayanan K
 * on 08/03/20 10:00 PM.
 */

@ResponseStatus(HttpStatus.NOT_FOUND)
public class OrderNotFoundException extends RuntimeException {

    private static final long serialVersionUID = 1L;
    public OrderNotFoundException() {
        super("Order does not exist");
    }
}


