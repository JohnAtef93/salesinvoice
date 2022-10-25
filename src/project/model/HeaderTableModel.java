/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package project.model;

import java.util.ArrayList;
import javax.swing.table.AbstractTableModel;
import project.view.SIGFrame;


public class HeaderTableModel extends AbstractTableModel {

    private ArrayList<InvoiceHeader> invoices;
    private String[] columns = {"Num", "Name", "Date", "Total"};

    public HeaderTableModel(ArrayList<InvoiceHeader> invoices) {
        this.invoices = invoices;
    }
   
    @Override
    public int getRowCount() {
        return invoices.size();
    }

    @Override
    public int getColumnCount() {
        return columns.length;
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        InvoiceHeader inv = invoices.get(rowIndex);
        switch (columnIndex) {
            case 0:
                return inv.getNumber();
            case 1: 
                return inv.getName();
            case 2:
                return SIGFrame.df.format(inv.getDate());
            case 3:
                return inv.getTotal();
            default:
                return "";
        }
        
    }

    @Override
    public String getColumnName(int column) {
        return columns[column];
    }
}
