package com.example.csse.csse.payment;

public interface IPaymentMethod {
    public boolean makePay(String cardno,float amnt);
}
