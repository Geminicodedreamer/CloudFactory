package View;
 
import javax.swing.*;
import javax.swing.table.DefaultTableModel;

import DataBase.EquipmentDataBase;
import DataBase.FactoryDataBase;
import DataBase.ProductDataBase;
import DataBase.ProductTypeDataBase;
import Model.Equipment;
import Model.Factory;
import Model.Product;
import Model.ProductType;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

class CreateProductDialog extends JDialog implements ActionListener{
    private JLabel nameLabel, categoryLabel, specLabel, descLabel;
    private JTextField nameField, specField, descField;
    private JComboBox<String> categoryComboBox;
    private JButton createButton, cancelButton;
    private JPanel panel;
    private ProductGUI parent;
    private JLabel factoryLabel;
    private JComboBox<String> factoryField;
    private JLabel deviceLabel;
    private JComboBox<String> deviceField;


    public CreateProductDialog(ProductGUI parent) {
        super(parent, "新建产品", true);
        this.parent = parent;
        setSize(400, 600);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);

        panel = new JPanel();
        panel.setLayout(null);

        nameLabel = new JLabel("产品名称:");
        nameLabel.setBounds(50, 50, 100, 30);
        panel.add(nameLabel);

        nameField = new JTextField();
        nameField.setBounds(150, 50, 150, 30);
        panel.add(nameField);

        categoryLabel = new JLabel("产品类别:");
        categoryLabel.setBounds(50, 100, 100, 30);
        panel.add(categoryLabel);

        categoryComboBox = new JComboBox<>();
        categoryComboBox.setBounds(150, 100, 150, 30);
        ArrayList<ProductType> types = ProductTypeDataBase.getProductTypes();
        for (ProductType type : types) {
            categoryComboBox.addItem(type.getName());
        }
        panel.add(categoryComboBox);

        specLabel = new JLabel("产品规格:");
        specLabel.setBounds(50, 150, 100, 30);
        panel.add(specLabel);

        specField = new JTextField();
        specField.setBounds(150, 150, 150, 30);
        panel.add(specField);


        factoryLabel = new JLabel("生产工厂:");
        factoryLabel.setBounds(50, 250, 100, 30);
        panel.add(factoryLabel);

        factoryField = new JComboBox<>();
        factoryField.setBounds(150, 250, 150, 30);
        ArrayList<Factory> factories = FactoryDataBase.getFactories();
        for (Factory factory : factories) {
            factoryField.addItem(factory.getName());
        }
        panel.add(factoryField);

        deviceLabel = new JLabel("设备:");
        deviceLabel.setBounds(50, 300, 100, 30);
        panel.add(deviceLabel);

        deviceField = new JComboBox<>();
        deviceField.setBounds(150, 300, 150, 30);
        for (Equipment device : EquipmentDataBase.getEquipments()) {
            deviceField.addItem(device.getName());
        }
        panel.add(deviceField);


        descLabel = new JLabel("产品描述:");
        descLabel.setBounds(50, 350, 100, 30);
        panel.add(descLabel);


        descField = new JTextField();
        descField.setBounds(150, 350, 150, 30);
        panel.add(descField);

        createButton = new JButton("确定");
        createButton.setBounds(50, 400, 100, 30);
        createButton.addActionListener(this);
        panel.add(createButton);

        cancelButton = new JButton("取消");
        cancelButton.setBounds(200, 400, 100, 30);
        cancelButton.addActionListener(this);
        panel.add(cancelButton);

        add(panel);
        setVisible(true);

        loadProductTypes();
    }

    private void loadProductTypes() {
        ArrayList<ProductType> productTypes = ProductTypeDataBase.getProductTypes();
        for (ProductType productType : productTypes) {
            categoryComboBox.addItem(productType.getName());
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == createButton) {
            String name = nameField.getText();
            String category = (String) categoryComboBox.getSelectedItem();
            String spec = specField.getText();
            String desc = descField.getText();
            String factory =(String) factoryField.getSelectedItem();
            String device =(String) deviceField.getSelectedItem(); 
            if (name.isEmpty() || category.isEmpty() || spec.isEmpty() || desc.isEmpty() || factory.isEmpty() || device.isEmpty()) {
                JOptionPane.showMessageDialog(this, "请填写所有信息。");
                return;
            }
        
            if (FactoryDataBase.getFactoryByName(factory) == null) {
                JOptionPane.showMessageDialog(this, "请输入合法的工厂");
                return;
            }
            if (EquipmentDataBase.getEquipmentByName(device) == null) {
                JOptionPane.showMessageDialog(this, "请输入合法的设备");
                return;
            }
            
            if (EquipmentDataBase.getEquipmentByName(device).getFactory() == null || FactoryDataBase.getFactoryByName(factory).getId() != EquipmentDataBase.getEquipmentByName(device).getFactory().getId()) {
                JOptionPane.showMessageDialog(this, "设备不是该公司租用的，不合法");
                return;
            }
            
            ProductType productType = ProductTypeDataBase.getProductTypeByName(category);
            Product product = new Product((ProductDataBase.getProducts().size() != 0)?ProductDataBase.getProducts().get(ProductDataBase.getProducts().size() - 1).getID() + 1 : 1 , name, spec , FactoryDataBase.getFactoryByName(factory).getId(),  EquipmentDataBase.getEquipmentByName(device).getID(), productType,desc);
            ProductDataBase.addProduct(product);
            parent.resetTable();
            dispose();
        } else if (e.getSource() == cancelButton) {
            dispose();
        }
    }
}



class ModifyProductDialog extends JDialog implements ActionListener{
    private JLabel nameLabel, categoryLabel, specLabel, descLabel;
    private JTextField nameField, specField, descField;
    private JComboBox<String> categoryComboBox;
    private JButton modifyButton, cancelButton;
    private JPanel panel;
    private ProductGUI parent;
    private JLabel factoryLabel;
    private JComboBox<String> factoryComboBox;
    private JLabel deviceLabel;
    private JTextField deviceField;
    private Product product;
    private JComboBox<String> deviceComboBox;


    public ModifyProductDialog(ProductGUI parent, Product product) {
        super(parent, "修改产品", true);
        this.parent = parent;
        this.product = product;
        setSize(400, 600);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);

        panel = new JPanel();
        panel.setLayout(null);

        nameLabel = new JLabel("产品名称:");
        nameLabel.setBounds(50, 50, 100, 30);
        panel.add(nameLabel);

        nameField = new JTextField(product.getProductName());
        nameField.setBounds(150, 50, 150, 30);
        panel.add(nameField);


        categoryLabel = new JLabel("产品类别:");
        categoryLabel.setBounds(50, 100, 100, 30);
        panel.add(categoryLabel);

        categoryComboBox = new JComboBox<>();
        categoryComboBox.setBounds(150, 100, 150, 30);
        ArrayList<ProductType> types = ProductTypeDataBase.getProductTypes();
        for (ProductType type : types) {
            categoryComboBox.addItem(type.getName());
        }
        String currentType = product.getProductType().getName();
        for (int i = 0; i < ProductTypeDataBase.getProductTypes().size(); i++) {
            if (ProductTypeDataBase.getProductTypes().get(i).getName().equals(currentType)) {
                categoryComboBox.setSelectedIndex(i);
                break;
            }
        }
        panel.add(categoryComboBox);

        specLabel = new JLabel("产品规格:");
        specLabel.setBounds(50, 150, 100, 30);
        panel.add(specLabel);

        specField = new JTextField(product.getProductCategory());
        specField.setBounds(150, 150, 150, 30);
        panel.add(specField);


        factoryLabel = new JLabel("生产工厂:");
        factoryLabel.setBounds(50, 250, 100, 30);
        panel.add(factoryLabel);


        factoryComboBox = new JComboBox<>();
        factoryComboBox.setBounds(150, 250, 150, 30);
        ArrayList<Factory> factories = FactoryDataBase.getFactories();
        for (Factory factory : factories) {
            factoryComboBox.addItem(factory.getName());
        }
        String currentFactory = FactoryDataBase.getFactoryByID(product.getFactoryID()).getName();
        for (int i = 0; i < factories.size(); i++) {
            if (factories.get(i).getName().equals(currentFactory)) {
                factoryComboBox.setSelectedIndex(i);
                break;
            }
        }
        panel.add(factoryComboBox);


        deviceLabel = new JLabel("设备:");
        deviceLabel.setBounds(50, 300, 100, 30);
        panel.add(deviceLabel);


        deviceComboBox = new JComboBox<>();
        deviceComboBox.setBounds(150, 300, 150, 30);
        ArrayList<Equipment> equipments = EquipmentDataBase.getEquipments();
        for (Equipment equipment : equipments) {
            deviceComboBox.addItem(equipment.getName());
        }
        String currentEquipment = EquipmentDataBase.getEquipmentByID(product.getEquipmentID()).getName();
        for (int i = 0; i < equipments.size(); i++) {
            if (equipments.get(i).getName().equals(currentEquipment)) {
                deviceComboBox.setSelectedIndex(i);
                break;
            }
        }
        panel.add(deviceComboBox);


        descLabel = new JLabel("产品描述:");
        descLabel.setBounds(50, 350, 100, 30);
        panel.add(descLabel);


        descField = new JTextField(product.getProductDescription());
        descField.setBounds(150, 350, 150, 30);
        panel.add(descField);

        modifyButton = new JButton("确定");
        modifyButton.setBounds(50, 400, 100, 30);
        modifyButton.addActionListener(this);
        panel.add(modifyButton);

        cancelButton = new JButton("取消");
        cancelButton.setBounds(200, 400, 100, 30);
        cancelButton.addActionListener(this);
        panel.add(cancelButton);

        add(panel);
        setVisible(true);

        loadProductTypes();
    }

    private void loadProductTypes() {
        ArrayList<ProductType> productTypes = ProductTypeDataBase.getProductTypes();
        for (ProductType productType : productTypes) {
            categoryComboBox.addItem(productType.getName());
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == modifyButton) {
            String name = nameField.getText();
            String category = (String) categoryComboBox.getSelectedItem();
            String spec = specField.getText();
            String desc = descField.getText();
            String factory = factoryComboBox.getSelectedItem().toString();
            String device = deviceComboBox.getSelectedItem().toString();
            if (name.isEmpty() || category.isEmpty() || spec.isEmpty() || desc.isEmpty() || factory.isEmpty() || device.isEmpty()) {
                JOptionPane.showMessageDialog(this, "请填写所有信息。");
                return;
            }

            if (EquipmentDataBase.getEquipmentByName(device).getFactory() == null || FactoryDataBase.getFactoryByName(factory).getId() != EquipmentDataBase.getEquipmentByName(device).getFactory().getId()) {
                JOptionPane.showMessageDialog(this, "设备不是该公司租用的，不合法");
                return;
            }

            ProductType productType = ProductTypeDataBase.getProductTypeByName(category);
            Product newProduct = new Product(product.getID(), product.getProductNumber() ,name, spec , FactoryDataBase.getFactoryByName(factory).getId(),  EquipmentDataBase.getEquipmentByName(device).getID(), productType,desc);
            ProductDataBase.modifyProduct(newProduct);
            parent.resetTable();
            dispose();
        } else if (e.getSource() == cancelButton) {
            dispose();
        }
    }
}

public class ProductGUI extends JFrame implements ActionListener {
    private JLabel nameLabel, categoryLabel;
    private JTextField nameField;
    private JComboBox<String> categoryComboBox;
    private JButton searchByNameButton, searchByCategoryButton, resetButton, createButton, deleteButton, modifyButton, backButton;
    private JTable productTable;
    private JScrollPane scrollPane;
    private DefaultTableModel tableModel;
    private ArrayList<Product> products;
    private JPanel panel;

    public ProductGUI() {
        setTitle("产品管理系统");
        setSize(800, 600);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        panel = new JPanel();
        panel.setLayout(null);

        nameLabel = new JLabel("产品名称:");
        nameLabel.setBounds(50, 50, 100, 30);
        panel.add(nameLabel);

        nameField = new JTextField();
        nameField.setBounds(150, 50, 150, 30);
        panel.add(nameField);

        searchByNameButton = new JButton("按名称查询");
        searchByNameButton.setBounds(350, 50, 150, 30);
        searchByNameButton.addActionListener(this);
        panel.add(searchByNameButton);

        categoryLabel = new JLabel("产品类别:");
        categoryLabel.setBounds(50, 100, 100, 30);
        panel.add(categoryLabel);

        categoryComboBox = new JComboBox<>();
        categoryComboBox.setBounds(150, 100, 150, 30);
        panel.add(categoryComboBox);

        searchByCategoryButton = new JButton("按类别查询");
        searchByCategoryButton.setBounds(350, 100, 150, 30);
        searchByCategoryButton.addActionListener(this);
        panel.add(searchByCategoryButton);

        resetButton = new JButton("重置");
        resetButton.setBounds(550, 100, 100, 30);
        resetButton.addActionListener(this);
        panel.add(resetButton);

        createButton = new JButton("新建");
        createButton.setBounds(50, 150, 100, 30);
        createButton.addActionListener(this);
        panel.add(createButton);

        deleteButton = new JButton("删除");
        deleteButton.setBounds(200, 150, 100, 30);
        deleteButton.addActionListener(this);
        panel.add(deleteButton);

        modifyButton = new JButton("修改");
        modifyButton.setBounds(350, 150, 100, 30);
        modifyButton.addActionListener(this);
        panel.add(modifyButton);

        backButton = new JButton("返回");
        backButton.setBounds(550, 500, 100, 30);
        backButton.addActionListener(this);
        panel.add(backButton);

        String[] columnNames = {"ID", "产品编号", "产品名称", "产品类别", "产品规格", "产品描述"};
        tableModel = new DefaultTableModel(columnNames, 0);
        productTable = new JTable(tableModel);
        scrollPane = new JScrollPane(productTable);
        scrollPane.setBounds(50, 200, 600, 300);
        panel.add(scrollPane);

        add(panel);
        setVisible(true);

        loadProductTypes();
        loadProducts();
    }


    private void loadProductTypes() {
        ArrayList<ProductType> productTypes = ProductTypeDataBase.getProductTypes();
        for (ProductType productType : productTypes) {
            categoryComboBox.addItem(productType.getName());
        }
    }

    private void loadProducts() {
        products = ProductDataBase.getProducts();
        for (Product product : products) {
            Object[] rowData = {product.getID(), product.getProductNumber(), product.getProductName(), product.getProductCategory(), product.getProductType().getName(), product.getProductDescription()};
            tableModel.addRow(rowData);
        }
    }


    private void searchByName() {
        String name = nameField.getText();
        if (name.isEmpty()) {
            JOptionPane.showMessageDialog(this, "请输入产品名称。");
            return;
        }
        Product product = ProductDataBase.getProductByName(name);
        if (product == null) {
            JOptionPane.showMessageDialog(this, "没有找到该名称的产品。");
            return;
        }
        tableModel.setRowCount(0);
        Object[] rowData = {product.getID(), product.getProductNumber(), product.getProductName(), product.getProductCategory(), product.getProductType().getName(), product.getProductDescription()};
        tableModel.addRow(rowData);
    }

    private void searchByCategory() {
        String category = (String) categoryComboBox.getSelectedItem();
        if (category.isEmpty()) {
            JOptionPane.showMessageDialog(this, "请选择产品类别。");
            return;
        }
        tableModel.setRowCount(0);
        for (Product product : products) {
            if (product.getProductType().getName().equals(category)) {
                Object[] rowData = {product.getID(), product.getProductNumber(), product.getProductName(), product.getProductCategory(), product.getProductType().getName(), product.getProductDescription()};
                tableModel.addRow(rowData);
            }
        }
    }

    void resetTable() {
        tableModel.setRowCount(0);
        loadProducts();
    }

    private void createProduct() {
        CreateProductDialog dialog = new CreateProductDialog(this);
    }

    private void deleteProduct() {
        int selectedRow = productTable.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "请选择要删除的产品。");
            return;
        }
        int id = (int) tableModel.getValueAt(selectedRow, 0);
        Product product = ProductDataBase.getProductByID(id);
        int option = JOptionPane.showConfirmDialog(this, "确定要删除所选产品吗？", "确认删除", JOptionPane.YES_NO_OPTION);
        if (option == JOptionPane.YES_OPTION) {
            ProductDataBase.removeProduct(product);
            tableModel.removeRow(selectedRow);
        }
    }

 
    public void modifyProduct() {
        int selectedRow = productTable.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "请选择要修改的产品。");
            return;
        }
        int id = (int) tableModel.getValueAt(selectedRow, 0);
        Product product = ProductDataBase.getProductByID(id);
        ModifyProductDialog dialog = new ModifyProductDialog(this, product);
    }


    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == searchByNameButton) {
            searchByName();
        } else if (e.getSource() == searchByCategoryButton) {
            searchByCategory();
        } else if (e.getSource() == resetButton) {
            resetTable();
        } else if (e.getSource() == createButton) {
            createProduct();
        } else if (e.getSource() == deleteButton) {
            deleteProduct();
        } else if (e.getSource() == modifyButton) {
            modifyProduct();
        } else if (e.getSource() == backButton) {
            dispose();
            new MenuGUI();
        }
    }

}
