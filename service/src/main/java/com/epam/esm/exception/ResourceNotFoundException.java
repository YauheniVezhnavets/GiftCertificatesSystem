package com.epam.esm.exception;

public class ResourceNotFoundException extends RuntimeException {

    private static final int ERROR_CODE = 40401;
    private long id;
    private String email;
    private String message;

    private ResourceNotFoundException(long id){
        this.id = id;
    }

    public ResourceNotFoundException(long id, String message){
        this.id = id;
        this.message = message;
    }

    public ResourceNotFoundException(String email) {
        this.email = email;
    }


    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public static int getErrorCode (){
        return ERROR_CODE;
    }
}
