package com.lahutina.courseinsuranse.models;

import java.sql.Date;

public class Option extends Document {

    private double buyerPayment;

    public Option() {
    }

    public Option(String title, Person buyer, Person seller, Date startDate, Date endDate, double risk, double buyerPayment) {
        super(title, buyer, seller, startDate, endDate, risk);
        this.buyerPayment = buyerPayment;
    }

    public Option(Document document) {
        super(document);
    }

    @Override
    public void calculateProfit() {
        setProfit(buyerPayment);
    }

    public double getBuyerPayment() {
        return buyerPayment;
    }

    public void setBuyerPayment(double buyerPayment) {
        this.buyerPayment = buyerPayment;
    }
}
