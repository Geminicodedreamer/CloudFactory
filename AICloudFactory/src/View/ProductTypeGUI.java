package View;

import javax.swing.*;
import javax.swing.plaf.basic.BasicBorders.ButtonBorder;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import DataBase.ProductDataBase;
import DataBase.ProductTypeDataBase;
import Model.ProductType;

public class ProductTypeGUI extends JFrame implements ActionListener {
    private static final long serialVersionUID = 1L;
    private JTextField searchField;
    private JButton searchButton;
    private JButton resetButton;
    private JButton addButton;
    private JButton deleteButton;
    private JButton modifyButton;
    private JTable table;
    private DefaultTableModel tableModel;
    private JButton backButton;
    private JPanel bottomPanel;

    public ProductTypeGUI() {
        setTitle("产品类型管理系统");
        setSize(800, 600);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JPanel topPanel = new JPanel();
        topPanel.setLayout(new FlowLayout(FlowLayout.LEFT));
        searchField = new JTextField(20);
        topPanel.add(searchField);
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new FlowLayout(FlowLayout.RIGHT));
        searchButton = new JButton("查询");
        searchButton.addActionListener(this);
        buttonPanel.add(searchButton);
        resetButton = new JButton("重置");
        resetButton.addActionListener(this);
        buttonPanel.add(resetButton);
        addButton = new JButton("新增");
        addButton.addActionListener(this);
        buttonPanel.add(addButton);
        deleteButton = new JButton("删除");
        deleteButton.addActionListener(this);
        buttonPanel.add(deleteButton);
        modifyButton = new JButton("修改");
        modifyButton.addActionListener(this);
        buttonPanel.add(modifyButton);
        topPanel.add(buttonPanel);
        add(topPanel, BorderLayout.NORTH);
        
        tableModel = new DefaultTableModel(){
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        tableModel.addColumn("ID");
        tableModel.addColumn("产品类型");
        table = new JTable(tableModel);
        JScrollPane scrollPane = new JScrollPane(table);
        add(scrollPane, BorderLayout.CENTER);
        bottomPanel = new JPanel(new BorderLayout());
        buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        
        backButton = new JButton("返回");
        backButton.addActionListener(this);
        
        buttonPanel.add(backButton);
        bottomPanel.add(buttonPanel, BorderLayout.EAST);

        add(bottomPanel, BorderLayout.SOUTH);
        loadProductTypes();
        setVisible(true);

    }

    private void loadProductTypes() {
        ArrayList<ProductType> productTypes = ProductTypeDataBase.getProductTypes();
        for (ProductType productType : productTypes) {
            Object[] rowData = new Object[2];
            rowData[0] = productType.getID();
            rowData[1] = productType.getName();
            tableModel.addRow(rowData);
        }
    }

    private void resetTable() {
        tableModel.setRowCount(0);
        loadProductTypes();
    }


    private void searchProductType() {
        String productTypeName = searchField.getText();
        ProductType productType = ProductTypeDataBase.getProductTypeByName(productTypeName);
        if (productType != null) {
            tableModel.setRowCount(0);
            Object[] rowData = new Object[2];
            rowData[0] = productType.getID();
            rowData[1] = productType.getName();
            tableModel.addRow(rowData);
        } else {
            JOptionPane.showMessageDialog(this, "未找到该产品类型");
        }
    }

    private void addProductType() {
        new ProductTypeDataBase();
        String productTypeName = JOptionPane.showInputDialog(this, "请输入产品类型名称");
        if (productTypeName != null && !productTypeName.equals("")) {
            ProductType productType = new ProductType((ProductTypeDataBase.getProductTypes().size()!= 0)?ProductTypeDataBase.getProductTypes().get(ProductTypeDataBase.getProductTypes().size() - 1).getID() + 1 : 1 , productTypeName);
            ProductTypeDataBase.addProductType(productType);
            Object[] rowData = new Object[2];
            rowData[0] = productType.getID();
            rowData[1] = productType.getName();
            tableModel.addRow(rowData);
        }
    }

    private void deleteProductType() {
        int selectedRow = table.getSelectedRow();
        if (selectedRow != -1) {
            int option = JOptionPane.showConfirmDialog(this, "确定删除该产品类型吗？");
            if (option == JOptionPane.YES_OPTION) {
                String productTypeName = (String) tableModel.getValueAt(selectedRow, 1);
                ProductType productType = ProductTypeDataBase.getProductTypeByName(productTypeName);
                ProductDataBase.removeProductbyproducttype(productType.getID());
                ProductTypeDataBase.removeProductType(productType);
                tableModel.removeRow(selectedRow);
            }
        } else {
            JOptionPane.showMessageDialog(this, "请先选择要删除的产品类型");
        }
    }


    private void modifyProductType() {
        int selectedRow = table.getSelectedRow();
        new ProductTypeDataBase();
        if (selectedRow != -1) {
            String productTypename = (String) tableModel.getValueAt(selectedRow, 1);
            String productTypeName = JOptionPane.showInputDialog(this, "请输入新的产品类型名称");
            if (productTypeName != null && !productTypeName.equals("")) {
                ProductType productType = ProductTypeDataBase.getProductTypeByName(productTypename);
                ProductType newpeProductType = new ProductType(productType.getID(), productTypeName);
                ProductDataBase.modifyProductbyproducttype(newpeProductType);
                ProductTypeDataBase.modifyProductType(newpeProductType);      
                tableModel.setValueAt(productTypeName, selectedRow, 1);
            }
        } else {
            JOptionPane.showMessageDialog(this, "请先选择要修改的产品类型");
        }
    }
    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == searchButton) {
            searchProductType();
        } else if (e.getSource() == resetButton) {
            resetTable();
        } else if (e.getSource() == addButton) {
            addProductType();
        } else if (e.getSource() == deleteButton) {
            deleteProductType();
        } else if (e.getSource() == modifyButton) {
            modifyProductType();
        } else if (e.getSource() == backButton) {
            dispose();
            new MenuGUI();
        }
    }
}



