package com.lahutina.courseinsuranse.models;


import java.sql.Date;


public class Document {

    private String title;
    private Person buyer;
    private Person seller;
    private Date startDate;
    private Date endDate;
    private double risk;
    private double profit;

    public Document(Document document) {
        this.title = document.getTitle();
        this.buyer = document.getBuyer();
        this.seller = document.getSeller();
        this.startDate = document.getStartDate();
        this.endDate = document.getEndDate();
        this.risk = document.getRisk();
        this.profit = document.getProfit();
    }

    public void calculateProfit() {}

    public Document(String title, Person buyer, Person seller, Date startDate, Date endDate, double risk) {
        this.title = title;
        this.buyer = buyer;
        this.seller = seller;
        this.startDate = startDate;
        this.endDate = endDate;
        this.risk = risk;
    }

    public Document() {
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Person getBuyer() {
        return buyer;
    }

    public void setBuyer(Person buyer) {
        this.buyer = buyer;
    }

    public Person getSeller() {
        return seller;
    }

    public void setSeller(Person seller) {
        this.seller = seller;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public double getRisk() {
        return risk;
    }

    public void setRisk(double risk) {
        this.risk = risk;
    }

    public double getProfit() {
        return profit;
    }

    public void setProfit(double profit) {
        this.profit = profit;
    }
}
