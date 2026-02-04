package com.musicstream.service.interfaces;

import com.musicstream.exception.InvalidInputException;

public interface Validatable<T> {
    void validate(T obj) throws InvalidInputException;

    default boolean isValid(T obj) {
        try {
            validate(obj);
            return true;
        } catch (InvalidInputException e) {
            return false;
        }
    }

    static void log(String msg) {
        System.out.println("[VALIDATION] " + msg);
    }
}
