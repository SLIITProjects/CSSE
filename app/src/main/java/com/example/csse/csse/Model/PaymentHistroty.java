package com.example.csse.csse.Model;

public class PaymentHistroty {

    private String cardno,date,time,route,accountId;
    private float cost;

    public PaymentHistroty(){

    }

    public PaymentHistroty(String accountId,String cardno, String date, String time, String route, float cost) {
        this.accountId = accountId;
        this.cardno = cardno;
        this.date = date;
        this.time = time;
        this.route = route;
        this.cost = cost;
    }

    public String getCardno() {
        return cardno;
    }

    public void setCardno(String cardno) {
        this.cardno = cardno;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getRoute() {
        return route;
    }

    public void setRoute(String route) {
        this.route = route;
    }

    public String getAccountId() {
        return accountId;
    }

    public void setAccountId(String accountId) {
        this.accountId = accountId;
    }

    public float getCost() {
        return cost;
    }

    public void setCost(float cost) {
        this.cost = cost;
    }
}
