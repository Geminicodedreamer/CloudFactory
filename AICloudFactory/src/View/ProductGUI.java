package View;
 
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import DataBase.EquipmentDataBase;
import DataBase.FactoryDataBase;
import DataBase.ProductDataBase;
import DataBase.ProductTypeDataBase;
import Model.Equipment;
import Model.Factory;
import Model.Product;
import Model.ProductType;

class NewProductGUI extends JFrame implements ActionListener {

    private JLabel titleLabel, productNameLabel, productCategoryLabel, productDescriptionLabel;
    private JTextField productNameField, productCategoryField, productDescriptionField;
    private JButton addButton, cancelButton;
    private JPanel panel, buttonPanel;
    private ProductType productType;
    JLabel factoryLabel;
    JTextField factoryField ;
    JLabel equipmentLabel;
    JTextField equipmentField;

    public NewProductGUI() {
        init();
        setTitle("添加新产品"); // 设置窗口标题
        setSize(500, 400); // set the size of the window
        setLocationRelativeTo(null); // center the window on the screen
        setVisible(true); // make the window visible
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE); // set the default close operation
    }


    private void init() {
        titleLabel = new JLabel("添加新产品");
        titleLabel.setFont(new Font("宋体", Font.BOLD, 16));
        titleLabel.setHorizontalAlignment(JLabel.CENTER);

        productNameLabel = new JLabel("产品名称:");
        productNameLabel.setFont(new Font("宋体", Font.PLAIN, 16));
        productNameField = new JTextField(20);
        productNameField.setFont(new Font("宋体", Font.PLAIN, 16));

        productType = null;
        JComboBox<String> productTypeComboBox = new JComboBox<String>();
        ArrayList<ProductType> productTypes = ProductTypeDataBase.getProductTypes();
        for (ProductType type : productTypes) {
            productTypeComboBox.addItem(type.getName());
        }
        productTypeComboBox.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                productType = ProductTypeDataBase.getProductTypeByName((String)productTypeComboBox.getSelectedItem());
            }
        });
        productTypeComboBox.setFont(new Font("宋体", Font.PLAIN, 16));
        JLabel productTypeLabel = new JLabel("产品类型:");
        productTypeLabel.setFont(new Font("宋体", Font.PLAIN, 16));


        

        productCategoryLabel = new JLabel("产品规格:");
        productCategoryLabel.setFont(new Font("宋体", Font.PLAIN, 16));
        productCategoryField = new JTextField(20);
        productCategoryField.setFont(new Font("宋体", Font.PLAIN, 16));

        productDescriptionLabel = new JLabel("产品描述:");
        productDescriptionLabel.setFont(new Font("宋体", Font.PLAIN, 16));
        productDescriptionField = new JTextField(20);
        productDescriptionField.setFont(new Font("宋体", Font.PLAIN, 16));

        factoryLabel = new JLabel("工厂:");
        factoryLabel.setFont(new Font("宋体", Font.PLAIN, 16));
        factoryField = new JTextField(20);
        factoryField.setFont(new Font("宋体", Font.PLAIN, 16));

        equipmentLabel = new JLabel("设备:");
        equipmentLabel.setFont(new Font("宋体", Font.PLAIN, 16));
        equipmentField = new JTextField(20);
        equipmentField.setFont(new Font("宋体", Font.PLAIN, 16));

        

        addButton = new JButton("添加");
        addButton.setFont(new Font("宋体", Font.PLAIN, 16));
        addButton.addActionListener(this);
        cancelButton = new JButton("取消");
        cancelButton.setFont(new Font("宋体", Font.PLAIN, 16));
        cancelButton.addActionListener(this);



        buttonPanel = new JPanel();
        buttonPanel.setLayout(new GridLayout(1, 2, 10, 10));
        buttonPanel.add(addButton);
        buttonPanel.add(cancelButton);

        panel = new JPanel();
        panel.setLayout(new GridLayout(5, 2, 10, 10));
 

        
        panel.setLayout(new GridLayout(6, 1, 10, 10));
        panel.add(productNameLabel);
        panel.add(productNameField);
        panel.add(productTypeLabel);
        panel.add(productTypeComboBox);
        panel.add(productCategoryLabel);
        panel.add(productCategoryField);
        panel.add(productDescriptionLabel);
        panel.add(productDescriptionField);
        panel.add(factoryLabel);
        panel.add(factoryField);
        panel.add(equipmentLabel);
        panel.add(equipmentField);

        
        

        add(titleLabel, BorderLayout.NORTH);
        add(panel, BorderLayout.CENTER);
        add(buttonPanel, BorderLayout.SOUTH);
    }

    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == addButton) {
            String productName = productNameField.getText();
            String productCategory = productCategoryField.getText();
            String productDescription = productDescriptionField.getText();
            FactoryDataBase.loadDatabase();
            EquipmentDataBase.loadDatabase();
            Equipment equipment = EquipmentDataBase.getEquipmentByName(equipmentField.getText());
            Factory factory = FactoryDataBase.getFactoryByName(factoryField.getText());
            
            if (productName.equals("") || productCategory.equals("")) {
                JOptionPane.showMessageDialog(this, "请填写所有字段。"); // 提示用户填写所有字段
            }else if(factory == null)
            {
                JOptionPane.showMessageDialog(this, "请填写存在的工厂");  
            }else if(equipment == null)
            {
                JOptionPane.showMessageDialog(this, "请填写存在的设备");   
            } else {
                new ProductDataBase();
                Product product = new Product((ProductDataBase.getProducts().size() != 0)?ProductDataBase.getProducts().get(ProductDataBase.getProducts().size()).getID() + 1 : 1, productName, productCategory,factory.getId() , equipment.getID(), productType, productDescription);
                ProductDataBase.addProduct(product);
                dispose();
                new ProductGUI();
            }
        } else if (e.getSource() == cancelButton) {
            dispose();
            new ProductGUI();
        }
    }
}


 
class ModifyProductGUI extends JFrame implements ActionListener {

    private JLabel titleLabel, productNameLabel, productCategoryLabel, productSpecificationLabel, productDescriptionLabel;
    private JTextField productNameField, productCategoryField, productSpecificationField, productDescriptionField;
    private JButton modifyButton, cancelButton;
    private JPanel panel, buttonPanel;
    private Product product;
    JLabel factoryLabel;
    JTextField factoryField ;
    JLabel equipmentLabel;
    JTextField equipmentField;

    public ModifyProductGUI(Product product) {
        this.product = product;
        init();
        setTitle("修改产品");
        setSize(500, 400);
        setLocationRelativeTo(null);
        setVisible(true);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
    }

    private void init() {
        titleLabel = new JLabel("修改产品");
        titleLabel.setFont(new Font("宋体", Font.BOLD, 24));
        titleLabel.setHorizontalAlignment(JLabel.CENTER);

        productNameLabel = new JLabel("产品名称:");
        productNameLabel.setFont(new Font("宋体", Font.PLAIN, 16));
        productNameField = new JTextField(20);
        productNameField.setFont(new Font("宋体", Font.PLAIN, 16));
        productNameField.setText(product.getProductName());

        productCategoryLabel = new JLabel("产品类别:");
        productCategoryLabel.setFont(new Font("宋体", Font.PLAIN, 16));
        JComboBox<String> productTypeComboBox = new JComboBox<String>();
        new ProductTypeDataBase();
        ArrayList<ProductType> productTypes = ProductTypeDataBase.getProductTypes();
        for(ProductType productType : productTypes) {
            productTypeComboBox.addItem(productType.getName());
        }
        ArrayList<ProductType> types = ProductTypeDataBase.getProductTypes();
        for (int i = 0; i < types.size(); i++) {
            if (types.get(i).getName().equals(product.getProductType().getName())) {
                productTypeComboBox.setSelectedIndex(i);
                break;
            }
        }

        productDescriptionLabel = new JLabel("产品描述:");
        productDescriptionLabel.setFont(new Font("宋体", Font.PLAIN, 16));
        productDescriptionField = new JTextField(20);
        productDescriptionField.setFont(new Font("宋体", Font.PLAIN, 16));
        productDescriptionField.setText(product.getProductDescription());

        factoryLabel = new JLabel("工厂:");
        factoryLabel.setFont(new Font("宋体", Font.PLAIN, 16));
        factoryField = new JTextField(20);
        factoryField.setFont(new Font("宋体", Font.PLAIN, 16));
        new FactoryDataBase();
        Factory factory = FactoryDataBase.getFactoryByID(product.getFactoryID());
        factoryField.setText(factory.getName());

        equipmentLabel = new JLabel("设备:");
        equipmentLabel.setFont(new Font("宋体", Font.PLAIN, 16));
        equipmentField = new JTextField(20);
        equipmentField.setFont(new Font("宋体", Font.PLAIN, 16));
        new EquipmentDataBase();
        Equipment equipment = EquipmentDataBase.getEquipmentByID(product.getEquipmentID());
        equipmentField.setText(equipment.getName());

        

        modifyButton = new JButton("修改");
        modifyButton.setFont(new Font("宋体", Font.PLAIN, 16));
        modifyButton.addActionListener(this);
        cancelButton = new JButton("取消");
        cancelButton.setFont(new Font("宋体", Font.PLAIN, 16));
        cancelButton.addActionListener(this);
        buttonPanel = new JPanel();
        buttonPanel.setLayout(new GridLayout(1, 2, 10, 10));
        buttonPanel.add(modifyButton);
        buttonPanel.add(cancelButton);

        panel = new JPanel();
        panel.setLayout(new GridLayout(5, 2, 10, 10));
        panel.add(productCategoryLabel);
        panel.add(productTypeComboBox);
        panel.add(productDescriptionLabel);
        panel.add(productDescriptionField);
        panel.add(factoryLabel);
        panel.add(factoryField);
        panel.add(equipmentLabel);
        panel.add(equipmentField);

        

        add(titleLabel, BorderLayout.NORTH);
        add(panel, BorderLayout.CENTER);
        add(buttonPanel, BorderLayout.SOUTH);
    }

    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == modifyButton) {
            String productName = productNameField.getText();
            String productCategory = productCategoryField.getText();
            String productSpecification = productSpecificationField.getText();
            String productDescription = productDescriptionField.getText();
            new EquipmentDataBase();
            Equipment equipment = EquipmentDataBase.getEquipmentByName(equipmentField.getText());
            new FactoryDataBase();
            Factory factory = FactoryDataBase.getFactoryByName(factoryField.getText());
            
            if (productName.equals("") || productCategory.equals("")) {
                JOptionPane.showMessageDialog(this, "Please fill in all fields.");
            } else {
                new ProductDataBase();
                Product newProduct = new Product(product.getID(), productName, productCategory,factory.getId() , equipment.getID(), product.getProductType(), productDescription);
                ProductDataBase.updateDataBase(newProduct);
                dispose();
                new ProductGUI();
            }
        } else if (e.getSource() == cancelButton) {
            dispose();
            new ProductGUI();
        }
    }
}




public class ProductGUI extends JFrame implements ActionListener {
    private static final long serialVersionUID = 1L;
    private JTextField searchField;
    private JButton searchButton;
    private JButton resetButton;
    private JButton newButton;
    private JButton deleteButton;
    private JButton modifyButton;
    private JTable table;
    private JScrollPane scrollPane;
    private JPanel panel;
    private JPanel buttonPanel;
    private JPanel searchPanel;
    private JPanel tablePanel;
    private JPanel mainPanel;
    private JComboBox<String> productTypeComboBox;
    private JPanel backPanel;
    private JPanel bottomPanel;
    private JLabel searchLabel;
    private JLabel titleLabel;
    private JButton backButton;
    private DefaultTableModel model;
    private ArrayList<Product> products;
    private ArrayList<ProductType> productTypes;

    public ProductGUI() {
        super("产品管理系统");

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(800, 600);
        setLocationRelativeTo(null);
        setResizable(false);
        init();
        setVisible(true);
    }

    private void init() {
        titleLabel = new JLabel("产品管理系统");
        titleLabel.setFont(new Font("宋体", Font.BOLD, 20));
        titleLabel.setHorizontalAlignment(JLabel.CENTER);

        searchLabel = new JLabel("按产品类别搜索:");
        searchLabel.setFont(new Font("宋体", Font.PLAIN, 16));
        searchField = new JTextField(20);
        searchField.setFont(new Font("宋体", Font.PLAIN, 16));
        searchButton = new JButton("搜索");
        searchButton.setFont(new Font("宋体", Font.PLAIN, 16));
        searchButton.addActionListener(this);
        resetButton = new JButton("重置");
        resetButton.setFont(new Font("宋体", Font.PLAIN, 16));
        resetButton.addActionListener(this);

        newButton = new JButton("新建");
        newButton.setFont(new Font("宋体", Font.PLAIN, 12));
        newButton.addActionListener(this);
        deleteButton = new JButton("删除");
        deleteButton.setFont(new Font("宋体", Font.PLAIN, 12));
        deleteButton.addActionListener(this);
        modifyButton = new JButton("修改");
        modifyButton.setFont(new Font("宋体", Font.PLAIN, 12));

        modifyButton.addActionListener(this);

        buttonPanel = new JPanel();
        buttonPanel.setLayout(new GridLayout(1, 3, 100, 0));
        buttonPanel.add(newButton);
        buttonPanel.add(deleteButton);
        buttonPanel.add(modifyButton);


        table = new JTable();
        table.setFont(new Font("宋体", Font.PLAIN, 16)); 
        table.getTableHeader().setFont(new Font("宋体", Font.BOLD, 16));
        table.setRowHeight(30);
        scrollPane = new JScrollPane(table);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);


        panel = new JPanel();
        panel.setLayout(new BorderLayout());
        panel.add(scrollPane, BorderLayout.CENTER);
        panel.add(buttonPanel , BorderLayout.PAGE_START);

        searchPanel = new JPanel();
        searchPanel.setLayout(new FlowLayout());
        searchPanel.add(searchLabel);
        searchPanel.add(searchField);
        searchPanel.add(searchButton);
        searchPanel.add(resetButton);

        tablePanel = new JPanel();
        tablePanel.setLayout(new BorderLayout());
        tablePanel.add(panel, BorderLayout.CENTER);

        mainPanel = new JPanel();
        mainPanel.setLayout(new BorderLayout());
        mainPanel.add(titleLabel, BorderLayout.NORTH);
        mainPanel.add(searchPanel, BorderLayout.CENTER);
        mainPanel.add(tablePanel, BorderLayout.SOUTH);
        bottomPanel = new JPanel(new BorderLayout());
        backPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        backButton = new JButton("返回");
        backButton.setFont(new Font("宋体", Font.PLAIN, 16));
        backButton.addActionListener(this);
        backPanel.add(backButton);
        bottomPanel.add(backPanel , BorderLayout.EAST);
        add(mainPanel, BorderLayout.CENTER);
        add(bottomPanel, BorderLayout.SOUTH);

        loadTableData();
    }

    private void loadTableData() {
        products = ProductDataBase.getProducts();
        String[] columnNames = {"ID", "产品编号", "产品名称", "产品类别", "产品规格", "产品描述"}; //表头
        model = new DefaultTableModel(columnNames, 0);
        for (Product product : products) {
            Object[] rowData = {product.getID(), product.getProductNumber(), product.getProductName(), product.getProductCategory(), product.getEquipment().getSpecification(), product.getProductType().getName() , product.getProductDescription()};
            model.addRow(rowData);
        }
        table.setModel(model);
    }
 
    private void searchByProductType() {
        String productTypeName = (String) productTypeComboBox.getSelectedItem();
        ProductType productType = ProductTypeDataBase.getProductTypeByName(productTypeName);
        ArrayList<Product> searchedProducts = new ArrayList<Product>();
        for (Product product : products) {
            if (product.getProductType().equals(productType)) {
                searchedProducts.add(product);
            }
        }
        String[] columnNames = {"编号", "产品编号", "产品名称", "产品类别", "产品规格", "产品描述"};

        model = new DefaultTableModel(columnNames, 0);
        for (Product product : searchedProducts) {
            Object[] rowData = {product.getID(), product.getProductNumber(), product.getProductName(), product.getProductCategory(), product.getEquipment().getSpecification(), product.getProductType().getName() , product.getProductDescription()};
            model.addRow(rowData);
        }
        table.setModel(model);
    }
    
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == newButton) {
            new NewProductGUI();  
        } else if (e.getSource() == modifyButton) {
            int row = table.getSelectedRow();
            if (row != -1) {
                int id = (int) table.getValueAt(row, 0);
                Product product = ProductDataBase.getProductByID(id);
                dispose();
                new ModifyProductGUI(product);
            } else {
 
                JOptionPane.showMessageDialog(this, "请选择要修改的行。");
            }
        } else if (e.getSource() == productTypeComboBox) {
            searchByProductType();
        }else if (e.getSource() == deleteButton) {
            int row = table.getSelectedRow();
            if (row != -1) {
                String name = (String) table.getValueAt(row, 1);
                ProductDataBase.removeProduct(ProductDataBase.getProductByName(name));;
                loadTableData();
            } else {
                JOptionPane.showMessageDialog(this, "请选择要删除的行。");
            }
        } else if (e.getSource() == modifyButton) {
            int row = table.getSelectedRow();
            if (row != -1) {
                int id = (int) table.getValueAt(row, 0);
                Product product = ProductDataBase.getProductByID(id);
                ProductType productType = productTypes.get(productTypeComboBox.getSelectedIndex());
                product.setProductType(productType);
                loadTableData();
            } else {
                JOptionPane.showMessageDialog(this, "请选择要修改的行。");

            }
        } else if (e.getSource() == searchButton) {
            String productCategory = searchField.getText();
            ArrayList<Product> searchedProducts = new ArrayList<Product>();
            for (Product product : products) {
                if (product.getProductCategory().equals(productCategory)) {
                    searchedProducts.add(product);
                }
            }

            String[] columnNames = {"ID", "产品编号", "产品名称", "产品类别", "产品规格", "产品描述"};

            model = new DefaultTableModel(columnNames, 0);
            for (Product product : searchedProducts) {
                Object[] rowData = {product.getID(), product.getProductNumber(), product.getProductName(), product.getProductCategory(), product.getEquipment().getSpecification(), product.getProductType() , product.getProductDescription()};
                model.addRow(rowData);
            }
            table.setModel(model);
        } else if (e.getSource() == resetButton) {
            loadTableData();
            searchField.setText("");
        } else if (e.getSource() == backButton) {
            dispose();
            new MenuGUI();
        }
    }

    public static void main(String[] args) {
        new ProductGUI();
    }
}
