package project.model;

import project.exception.InvalidReturnException;
import project.exception.ItemNotAvailableException;

public interface Borrowable {
    void borrowItem(String userId) throws ItemNotAvailableException;

    void returnItem(String userId) throws InvalidReturnException;

    boolean isAvailable();
}
