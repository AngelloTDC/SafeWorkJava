package br.com.safework.exception;

public class BusinessException extends RuntimeException {

    private final String messageKey;

    public BusinessException(String messageKey) {
        super(messageKey);
        this.messageKey = messageKey;
    }

    public BusinessException(String messageKey, Throwable cause) {
        super(messageKey, cause);
        this.messageKey = messageKey;
    }

    public String getMessageKey() {
        return messageKey;
    }
}
