package com.epam.esm.exception;

public class ResourceNotFoundException extends RuntimeException {

    private static final int ERROR_CODE = 40401;
    private long id;

    public ResourceNotFoundException (long id){
        this.id = id;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public static int getErrorCode (){
        return ERROR_CODE;
    }
}
