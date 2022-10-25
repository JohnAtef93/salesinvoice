/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package project.controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import project.model.HeaderTableModel;
import project.model.InvoiceHeader;
import project.model.InvoiceItem;
import project.model.ItemTableModel;
import project.view.SIGFrame;

public class SIGController implements ActionListener, ListSelectionListener {

    private SIGFrame uiframe;

    public SIGController(SIGFrame frame) {
        this.uiframe = frame;
    }
    
    @Override
    public void actionPerformed(ActionEvent e) {
        String ac = e.getActionCommand();

        System.out.println("user clicked " + ac);

        switch (ac) {
            case "New Invoice":
                newInvoice();
                break;

            case "Delete Invoice":
                deleteInvoice();
                break;

            case "New Line":
                newLine();
                break;

            case "Delete Line":
                deleteLine();
                break;

            case "Load":
                load(null, null);
                break;

            case "Save":
                save();
                break;

        }
    }
    
    @Override
    public void valueChanged(ListSelectionEvent e) {
        int selectedRow = uiframe.getInvoicesTable().getSelectedRow();
        System.out.println("Row Selected " + selectedRow);
        if (selectedRow != -1) {
            InvoiceHeader selectedInv = uiframe.getInvoices().get(selectedRow);
            uiframe.getCustomerNameLbl().setText(selectedInv.getName());
            uiframe.getInvDateLbl().setText(uiframe.df.format(selectedInv.getDate()));
            uiframe.getInvNumLbl().setText(""+selectedInv.getNumber());
            uiframe.getInvTotalLbl().setText(""+selectedInv.getTotal());
            uiframe.setItemTableModel(new ItemTableModel(selectedInv.getItems()));
        } else {
            
        }
    }

    private void newInvoice() {
        Date d = new Date();
        int num = uiframe.getInvoices().stream().mapToInt(item -> item.getNumber()).max().getAsInt() + 1;
        String name = "Invoice " + num;
        InvoiceHeader inv = new InvoiceHeader(num, name, d);
        uiframe.getInvoices().add(inv);
        uiframe.setHeaderTableModel(new HeaderTableModel(uiframe.getInvoices()));
    }

    private void deleteInvoice() {
        int selectedRow = uiframe.getInvoicesTable().getSelectedRow();
        System.out.println("Row Selected " + selectedRow);
        if (selectedRow != -1) {
            uiframe.getInvoices().remove(selectedRow);
            uiframe.setHeaderTableModel(new HeaderTableModel(uiframe.getInvoices()));
        } else {
            
        }
    }

    private void newLine() {
        int selectedRow = uiframe.getInvoicesTable().getSelectedRow();
        System.out.println("Row Selected " + selectedRow);
        if (selectedRow != -1) {
            InvoiceHeader selectedInv = uiframe.getInvoices().get(selectedRow);
            String name = "Name";
            int price = 100;
            int count = 5;
            InvoiceItem item = new InvoiceItem(name, price, count, selectedInv);
            uiframe.setItemTableModel(new ItemTableModel(selectedInv.getItems()));
        } else {
            
        }
    }

    private void deleteLine() {
    }

    public void load(String hPath, String lPath) {
        System.out.println("Load File");
        File hFile = null;
        File lFile = null;
        if (hPath == null && lPath == null) {
            JFileChooser fc = new JFileChooser();
            JOptionPane.showMessageDialog(uiframe, "Please Choose Header File", "Header", JOptionPane.WARNING_MESSAGE);
            int result = fc.showOpenDialog(uiframe);
            if (result == JFileChooser.APPROVE_OPTION) {
                hFile = fc.getSelectedFile();
                JOptionPane.showMessageDialog(uiframe, "Please Choose Line File!", "Line", JOptionPane.WARNING_MESSAGE);
                result = fc.showOpenDialog(uiframe);
                if (result == JFileChooser.APPROVE_OPTION) {
                    lFile = fc.getSelectedFile();
                }
            }
        } else {
            hFile = new File(hPath);
            lFile = new File(lPath);
        }
        
        if (hFile != null && lFile != null) {
            try {
                List<String> hLines = readFile(hFile);
                /*
                [
                    "1,12-11-2020,Sameer", 
                    "2,23-10-2021,Rana"
                ]
                */
                
                List<String> lLines = readFile(lFile);
                /*
                [
                    "1,Mobile,3200,1",
                    "1,Cover,90,2",
                    "1,Headphone,130,1",
                    "2,Laptop,9000,1",
                    "2,Mouse,135,2"
                ]
                */
                System.out.println("check");
                for (String hLine : hLines) {
                    /*
                        hLine = "1,12-11-2020,Sameer"
                    */
                    String[] parts = hLine.split(",");
                    /*
                        parts = ["1", "12-11-2020", "Sameer"]
                    */
                    Date d = new Date();
                    int num = Integer.parseInt(parts[0]);
                    try{d = SIGFrame.df.parse(parts[1]);}catch (ParseException pex) {}
                    String name = parts[2];
                    InvoiceHeader inv = new InvoiceHeader(num, name, d);
                    uiframe.getInvoices().add(inv);
                }
                uiframe.setHeaderTableModel(new HeaderTableModel(uiframe.getInvoices()));
                
                for (String lLine : lLines) {
                    /*
                        lLine = "1,Mobile,3200,1"
                    */
                    String[] parts = lLine.split(",");
                    /*
                        parts = ["1", "Mobile", "3200", "1"]
                    */
                    int invNum = Integer.parseInt(parts[0]);
                    String name = parts[1];
                    int price = Integer.parseInt(parts[2]);
                    int count = Integer.parseInt(parts[3]);
                    InvoiceHeader invoice = uiframe.getInvoiceByNum(invNum);
                    InvoiceItem item = new InvoiceItem(name, price, count, invoice);
                }
                System.out.println("Check");
            } catch (IOException ex) {
                JOptionPane.showMessageDialog(uiframe, "Error while loading files", "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private List<String> readFile(File myFile) throws IOException {
        List<String> lines = new ArrayList<>();

        FileReader fr = new FileReader(myFile);
        BufferedReader br = new BufferedReader(fr);
        String line = null;
        while ((line = br.readLine()) != null) {
            lines.add(line);
        }

        return lines;
    }

    private void save() {
    }

}
