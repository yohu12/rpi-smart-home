package com.yohu.smarthome.exception;

/**
 * @author: huyong
 * @date: 2018/11/30 16:45
 */
public class UnableToWakeUpWOLNodeException extends Exception {

    /**
     *
     */
    private static final long serialVersionUID = 8276665758554295391L;

    public UnableToWakeUpWOLNodeException() {
        super();
    }

    public UnableToWakeUpWOLNodeException(String message, Throwable cause) {
        super(message, cause);
    }

    public UnableToWakeUpWOLNodeException(String message) {
        super(message);
    }

    /**
     * Instantiates a new unable to wake up wol node exception.
     *
     * @param cause the cause
     */
    public UnableToWakeUpWOLNodeException(Throwable cause) {
        super(cause);
    }

}