/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package project.model;

import java.util.ArrayList;
import javax.swing.table.AbstractTableModel;


public class ItemTableModel extends AbstractTableModel {

    private ArrayList<InvoiceItem> items;

    public ItemTableModel(ArrayList<InvoiceItem> items) {
        this.items = items;
    }
    
    @Override
    public int getRowCount() {
        return items.size();
    }

    @Override
    public int getColumnCount() {
        return 4;
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        InvoiceItem item = items.get(rowIndex);
        switch (columnIndex) {
            case 0: return item.getName();
            case 1: return item.getPrice();
            case 2: return item.getCount();
            case 3: return item.getTotal();
            default: return "";
        }
    }

    @Override
    public String getColumnName(int column) {
        switch (column) {
            case 0: return "Name";
            case 1: return "Price";
            case 2: return "Count";
            case 3: return "Total";
            default: return "";
        }
    }
}
