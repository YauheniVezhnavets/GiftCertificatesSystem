package com.epam.esm.exception;

public class ResourceDuplicateException extends RuntimeException {

    private static final int ERROR_CODE = 40402;


    public ResourceDuplicateException (){}


    public static int getErrorCode (){
        return ERROR_CODE;
    }


}
