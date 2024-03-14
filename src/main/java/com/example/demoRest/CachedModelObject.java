package com.example.demoRest;

public class CachedModelObject {
    private String key;
    private String message;

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public long getObjectNumber() {
        return objectNumber;
    }

    public void setObjectNumber(long objectNumber) {
        this.objectNumber = objectNumber;
    }

    private long objectNumber;

}
