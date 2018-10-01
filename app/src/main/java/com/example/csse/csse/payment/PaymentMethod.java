package com.example.csse.csse.payment;

public class PaymentMethod implements IPaymentMethod{

    @Override
    public boolean makePay(String cardno, float amount) {
        return false;
    }
}
