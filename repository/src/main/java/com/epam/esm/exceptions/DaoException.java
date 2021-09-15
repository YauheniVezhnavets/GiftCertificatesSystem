package com.epam.esm.exceptions;

public class DaoException extends RuntimeException {

    private static final int ERROR_CODE = 50001;


    public DaoException (){}


    public static int getErrorCode (){
        return ERROR_CODE;
    }


}
