package com.funtime.dubbo.config.exception;

/**
 * Created by steven on 2018/9/26.
 */
public class FileStorageException extends RuntimeException {
    public FileStorageException(String message) {
        super(message);
    }

    public FileStorageException(String message, Throwable cause) {
        super(message, cause);
    }
}
