/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package project.model;

import java.util.ArrayList;
import java.util.Date;


public class InvoiceHeader {
    private int number;
    private String name;
    private Date date;
    private ArrayList<InvoiceItem> items;

    public InvoiceHeader(int num, String name, Date date) {
        this.number = num;
        this.name = name;
        this.date = date;
    }
    
    public int getTotal() {
        int total = 0;
        for (InvoiceItem item : getItems()) {
            total += item.getTotal();
        }
        return total;
    }
    
    public ArrayList<InvoiceItem> getItems() {
        if (items == null) {
            items = new ArrayList<>();
        }
        return items;
    }
    
    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "InvoiceHeader{" + "num=" + number + ", name=" + name + ", date=" + date + '}';
    }
}
