package com.musicstream.interfaces;

import com.musicstream.exception.InvalidInputException;

public interface Validatable {
    void validate() throws InvalidInputException;
}
