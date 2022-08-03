package ru.novikov.natlex.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

public class GeoClassNotFoundException extends ResponseStatusException {
    public GeoClassNotFoundException(long id) {
        super(HttpStatus.NOT_FOUND, String.format("Geological class with id '%d' not found.", id));
    }

}
