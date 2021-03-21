package com.Exeptions;

public class DataNotValid extends Exception{

    public DataNotValid(String dataFieldNotValid) {
        super(dataFieldNotValid);
    }
}
