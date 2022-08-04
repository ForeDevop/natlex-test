package ru.novikov.natlex.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

public class FileNotFoundException extends ResponseStatusException {
    public FileNotFoundException(long id) {
        super(HttpStatus.NOT_FOUND, String.format("File with id '%d' not found.", id));
    }

}
