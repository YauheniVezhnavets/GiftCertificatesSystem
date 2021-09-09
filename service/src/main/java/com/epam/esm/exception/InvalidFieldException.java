package com.epam.esm.exception;

public class InvalidFieldException extends RuntimeException {

    private static final int ERROR_CODE = 40403;


    public InvalidFieldException (){}


    public static int getErrorCode (){
        return ERROR_CODE;
    }


}
