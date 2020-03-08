package com.p3.poc.demo.ar.utils.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Created by Suriyanarayanan K
 * on 08/03/20 11:49 PM.
 */

@ResponseStatus(HttpStatus.NOT_FOUND)
public class LedgerNotFoundException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    public LedgerNotFoundException() {
        super("Invoice does not exist");
    }
}
