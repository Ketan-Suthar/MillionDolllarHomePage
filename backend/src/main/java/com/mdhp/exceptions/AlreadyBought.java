package com.mdhp.exceptions;



public class AlreadyBought extends Exception {
    public static final String MESSAGE = "Few or all pixels you are trying to buy is already bought.";

    public AlreadyBought(final String exMessage) {
        super(exMessage);
    }
}
