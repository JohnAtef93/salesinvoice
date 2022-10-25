/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package project.model;


public class InvoiceItem {
    
    private String name;
    private int price;
    private int count;
    private InvoiceHeader invoice;

    public InvoiceItem(String name, int price, int count, InvoiceHeader invoice) {
        this.name = name;
        this.price = price;
        this.count = count;
        this.invoice = invoice;
        invoice.getItems().add(this);
    }

    public int getTotal() {
        return price * count;
    }
    
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public InvoiceHeader getInvoice() {
        return invoice;
    }

    public void setInvoice(InvoiceHeader invoice) {
        this.invoice = invoice;
    }

    @Override
    public String toString() {
        return "InvoiceItem{" + "name=" + name + ", price=" + price + ", count=" + count + '}';
    }
    
    
    
}
