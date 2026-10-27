package project;

import project.ItemNotAvailableException;

public interface Borrowable {
    void borrowItem(String userId) throws ItemNotAvailableException;

    void returnItem(String userId) throws Exception;

    boolean isAvailable();
}
