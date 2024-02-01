package com.example.springtesttaskv1.exception;

import java.util.UUID;

public class EntityNotFoundException extends ClientApplicationException{
    public EntityNotFoundException(Class<?> clazz, UUID uuid) {
        super(String.format("%s with uuid: %s not found", clazz.getSimpleName(), uuid.toString()));
    }
}
