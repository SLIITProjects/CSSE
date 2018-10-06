package com.example.csse.csse.Model;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class Card {
    private String fareType;
    private String cardNumber;
    private String accountId;
    private String pin;
    private String dateIssued;

    public Card() {
    }

    public Card(String fareType, String accountId, String pin, String cardNumber) {
        this.fareType = fareType;
        this.cardNumber = cardNumber;
        this.accountId = accountId;
        this.pin = pin;
        this.dateIssued = new SimpleDateFormat("MM/dd/yyyy").format(Calendar.getInstance().getTime());
    }

    public String getFareType() {
        return fareType;
    }

    public void setFareType(String fareType) {
        this.fareType = fareType;
    }

    public String getCardNumber() {
        return cardNumber;
    }

    public void setCardNumber(String cardNumber) {
        this.cardNumber = cardNumber;
    }

    public String getAccountId() {
        return accountId;
    }

    public void setAccountId(String accountId) {
        this.accountId = accountId;
    }

    public String getPin() {
        return pin;
    }

    public void setPin(String pin) {
        this.pin = pin;
    }

    public String getDateIssued() {
        return dateIssued;
    }

    public void setDateIssued(String dateIssued) {
        this.dateIssued = dateIssued;
    }


}
