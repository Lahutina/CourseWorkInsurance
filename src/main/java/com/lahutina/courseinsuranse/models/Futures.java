package com.lahutina.courseinsuranse.models;

import java.sql.Date;

public class Futures extends Document {
    private double buyerPayment;
    private double sellerPayment;

    public Futures() {
    }

    public Futures(Document document) {
        super(document);
    }

    public Futures(String title, Person buyer, Person seller, Date startDate,
                   Date endDate, double risk, double buyerPayment, double sellerPayment) {
        super(title, buyer, seller, startDate, endDate, risk);
        this.buyerPayment = buyerPayment;
        this.sellerPayment = sellerPayment;
    }


    @Override
    public void calculateProfit() {
        setProfit(buyerPayment + sellerPayment);
    }

    public double getBuyerPayment() {
        return buyerPayment;
    }

    public void setBuyerPayment(double buyerPayment) {
        this.buyerPayment = buyerPayment;
    }

    public double getSellerPayment() {
        return sellerPayment;
    }

    public void setSellerPayment(double sellerPayment) {
        this.sellerPayment = sellerPayment;
    }
}
