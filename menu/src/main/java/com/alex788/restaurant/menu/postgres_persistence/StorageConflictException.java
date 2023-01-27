package com.alex788.restaurant.menu.postgres_persistence;

public class StorageConflictException extends RuntimeException {

    public StorageConflictException() {
    }

    public StorageConflictException(String message) {
        super(message);
    }

    public StorageConflictException(Throwable cause) {
        super(cause);
    }
}
