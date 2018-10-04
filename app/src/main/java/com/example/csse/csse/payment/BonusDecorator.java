package com.example.csse.csse.payment;

public class BonusDecorator implements IPaymentMethod {

    protected IPaymentMethod tempPay;

    public BonusDecorator(IPaymentMethod mpay) {
        tempPay=mpay;
    }


    @Override
    public boolean makePay(String cardno, float amnt) {
        // TODO Auto-generated method stub
        return false;
    }


}
