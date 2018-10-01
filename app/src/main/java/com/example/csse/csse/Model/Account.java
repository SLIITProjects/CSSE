package com.example.csse.csse.Model;

public class Account  {

   private String accountId,cardNo,userId,loanFlag;
    private float crediteBal,loPoint,loanAmnt;

    public Account(){

    }

    public Account(String accountId, String cardNo, float crediteBal, float loPoint,String loanFlag,float loanAmnt) {
        this.accountId = accountId;
        this.cardNo = cardNo;
//        this.userId = userId;
        this.crediteBal = crediteBal;
        this.loPoint = loPoint;
        this.loanFlag=loanFlag;
        this.loanAmnt=loanAmnt;
    }

    public String getAccountId() { return accountId; }

    public void setAccountId(String accountId) { this.accountId = accountId; }

    public String getCardNo() { return cardNo; }

    public void setCardNo(String cardNo) { this.cardNo = cardNo; }

  //  public String getUserId() { return userId; }

    //public void setUserId(String userId) { this.userId = userId; }

    public float getCrediteBal() { return crediteBal; }

    public void setCrediteBal(float crediteBal) { this.crediteBal = crediteBal; }

    public float getLoPoint() { return loPoint; }

    public void setLoPoint(float loPoint) { this.loPoint = loPoint; }

    public String getLoanFlag() { return loanFlag; }

    public void setLoanFlag(String loanFlag) { this.loanFlag = loanFlag; }

    public float getLoanAmnt() { return loanAmnt; }

    public void setLoanAmnt(float loanAmnt) { this.loanAmnt = loanAmnt; }

    public void updateBal(float crediteBal){

        float lonam =getLoanAmnt();
        this.crediteBal=crediteBal+getCrediteBal();

    }
}
