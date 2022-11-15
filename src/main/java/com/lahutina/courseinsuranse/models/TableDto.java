package com.lahutina.courseinsuranse.models;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

public class TableDto {
    private SimpleIntegerProperty id;
    private SimpleStringProperty title;
    private SimpleStringProperty buyer;
    private SimpleStringProperty seller;
    private SimpleStringProperty endDate;
    private SimpleIntegerProperty risk;


    public TableDto(int id, String title, String buyer, String seller, String endDate, int risk) {
        this.id = new SimpleIntegerProperty(id);
        this.title = new SimpleStringProperty(title);
        this.buyer = new SimpleStringProperty(buyer);
        this.seller = new SimpleStringProperty(seller);
        this.endDate = new SimpleStringProperty(endDate);
        this.risk = new SimpleIntegerProperty(risk);

    }

    public int getId() {
        return id.get();
    }

    public String getTitle() {
        return title.get();
    }

    public String getBuyer() {
        return buyer.get();
    }

    public String getSeller() {
        return seller.get();
    }

    public String getEndDate() {
        return endDate.get();
    }

    public void setId(int id) {
        this.id = new SimpleIntegerProperty(id);
    }

    public void setTitle(String title) {
        this.title = new SimpleStringProperty(title);
    }

    public void setBuyer(String buyer) {
        this.buyer = new SimpleStringProperty(buyer);
    }

    public void setSeller(String seller) {
        this.seller = new SimpleStringProperty(seller);
    }

    public void setEndDate(String endDate) {
        this.endDate = new SimpleStringProperty(endDate);
    }

    public int getRisk() {
        return risk.get();
    }

    public void setRisk(int risk) {
        this.risk = new SimpleIntegerProperty(risk);
    }
}
