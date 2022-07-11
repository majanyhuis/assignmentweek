package com.accenture.assignmentweek;

import java.text.SimpleDateFormat;

public class Stock {

    // hier wird das Objekt "Stock" kreiert...

    protected int idStock;

    protected String price; // nochmal über Datentyp nachdenken!!!

    protected SimpleDateFormat date; // nochmal über Datentyp nachdenken!!!

    protected int CompanyID;
    protected String companyName;

    protected int industryID;
    protected String industryName;

    // Getter und Setter

    public int getIdStock() {
        return idStock;
    }

    public void setIdStock(int idStock) {
        this.idStock = idStock;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public SimpleDateFormat getDate() {
        return date;
    }

    public void setDate(SimpleDateFormat date) {
        this.date = date;
    }

    public int getCompanyID() {
        return CompanyID;
    }

    public void setCompanyID(int companyID) {
        CompanyID = companyID;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public int getIndustryID() {
        return industryID;
    }

    public void setIndustryID(int industryID) {
        this.industryID = industryID;
    }

    public String getIndustryName() {
        return industryName;
    }

    public void setIndustryName(String industryName) {
        this.industryName = industryName;
    }

}
