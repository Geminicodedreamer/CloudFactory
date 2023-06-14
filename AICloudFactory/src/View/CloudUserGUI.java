package View;

import Model.User;

import java.awt.BorderLayout;
import java.awt.Frame;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Vector;

import javax.swing.*;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.DefaultTableModel;

import Controller.Login;
import DataBase.EquipmentDataBase;
import DataBase.EquipmentTypeDataBase;
import DataBase.FactoryDataBase;
import DataBase.UserDataBase;
import Model.Equipment;
import Model.EquipmentType;
import Model.Factory;

class rentEquipmentGUI extends JDialog implements ActionListener
{
    private JTable equipmentTable;
    private JPanel panel;
    private JButton rentButton;
    private JButton exitButton;
    private DefaultTableModel tableModel;
    private ArrayList<Equipment> equipments;
    private Factory factory;
    public rentEquipmentGUI(JFrame parent , Factory factory)
    {
        super(parent, "租用设备", true);
        this.factory = factory; 
        setSize(800, 600);
        setLocationRelativeTo(null);
        setResizable(false);
        
        panel = new JPanel();
        panel.setLayout(null);
        add(panel);
        ArrayList<Equipment> notRentedEquipments = new ArrayList<Equipment>();
        for (Equipment equipment : EquipmentDataBase.getEquipments()) {
            if(equipment.isBorrowable().equals("否")){
                notRentedEquipments.add(equipment);
            }
        }
        String[] columnNames = {"ID", "设备名称", "设备编号", "设备类别", "设备规格", "设备状态", "设备描述", "租用状态", "所属工厂"};
        Object[][] rowData = new Object[notRentedEquipments.size()][9];
        for (int i = 0; i < notRentedEquipments.size(); i++) {
            Equipment equipment = notRentedEquipments.get(i);
            rowData[i][0] = equipment.getID();
            rowData[i][1] = equipment.getName();
            rowData[i][2] = equipment.getCode();
            rowData[i][3] = equipment.getType().getName();
            rowData[i][4] = equipment.getSpecification();
            rowData[i][5] = equipment.isSwitchable()?"开机":"关机";
            rowData[i][6] = equipment.getDescription();
            rowData[i][7] = (equipment.isBorrowable().equals("是"))?"已被租用":"未被租用";
            rowData[i][8] = (equipment.getFactory() != null)?equipment.getFactory().getName():"";
        }
        tableModel = new DefaultTableModel(rowData, columnNames);
        equipmentTable = new JTable(tableModel);
        JScrollPane scrollPane = new JScrollPane(equipmentTable);
        scrollPane.setBounds(50, 50, 700, 400);
        panel.add(scrollPane);
        
        rentButton = new JButton("租用");
        rentButton.setBounds(200, 500, 80, 30);
        rentButton.addActionListener(this);
        panel.add(rentButton);

        exitButton = new JButton("退出");
        exitButton.setBounds(400, 500, 80, 30);
        exitButton.addActionListener(this);
        panel.add(exitButton);
        
        setVisible(true);
    }
    
    public void actionPerformed(ActionEvent e)
    {
        if(e.getSource() == rentButton)
        {
            int selectedRow = equipmentTable.getSelectedRow();
            if(selectedRow == -1){
                JOptionPane.showMessageDialog(null, "请选择设备");
                return;
            }
            int id = (int)tableModel.getValueAt(selectedRow, 0);
            Equipment equipment = EquipmentDataBase.getEquipmentByID(id);
            equipment.setBorrowable("是");
            equipment.setFactory(factory);
            EquipmentDataBase.modifyEquipment(equipment);
            updateTable();
        }
        else if(e.getSource() == exitButton)
        {
            dispose();
        }
    }

    
    private void updateTable() {
        ArrayList<Equipment> notRentedEquipments = new ArrayList<Equipment>();
        for (Equipment equipment : EquipmentDataBase.getEquipments()) {
            if(equipment.isBorrowable().equals("否")){
                notRentedEquipments.add(equipment);
            }
        }
        String[] columnNames = {"ID", "设备名称", "设备编号", "设备类别", "设备规格", "设备状态", "设备描述", "租用状态", "所属工厂"};
        Object[][] rowData = new Object[notRentedEquipments.size()][9];
        for (int i = 0; i < notRentedEquipments.size(); i++) {
            Equipment equipment = notRentedEquipments.get(i);
            rowData[i][0] = equipment.getID();
            rowData[i][1] = equipment.getName();
            rowData[i][2] = equipment.getCode();
            rowData[i][3] = equipment.getType();
            rowData[i][4] = equipment.getSpecification();
            rowData[i][5] = equipment.isSwitchable()?"开机":"关机";
            rowData[i][6] = equipment.getDescription();
            rowData[i][7] = (equipment.isBorrowable().equals("是"))?"已被租用":"未被租用";
            rowData[i][8] = (equipment.getFactory() != null)?equipment.getFactory().getName():"";
        }
        tableModel.setDataVector(rowData, columnNames);
    }
    

}



class newEquipmentDialog extends JDialog implements ActionListener
{
    private JLabel nameLabel, typeLabel, specificationLabel, switchableLabel, descriptionLabel;
    private JTextField nameTextField, specificationTextField;
    private JComboBox<String> typeComboBox;
    private JCheckBox switchableCheckBox;
    private JTextArea descriptionTextArea;
    private JButton okButton, cancelButton;
    private JPanel panel;
    Equipment equipment;
    Factory factory;
    public newEquipmentDialog(JFrame parent , Factory factory)
    {
        super(parent, "新建设备", true);
        this.factory = factory; 
        setSize(400, 600);
        setLocationRelativeTo(null);
        setResizable(false);
        
        panel = new JPanel();
        panel.setLayout(null);
        add(panel);
        
        nameLabel = new JLabel("设备名称：");
        nameLabel.setBounds(50, 50, 80, 30);
        panel.add(nameLabel);
        
        nameTextField = new JTextField();
        nameTextField.setBounds(130, 50, 150, 30);
        panel.add(nameTextField);
        
        typeLabel = new JLabel("设备类别：");
        typeLabel.setBounds(50, 100, 80, 30);
        panel.add(typeLabel);

        
        typeComboBox = new JComboBox<String>();
        typeComboBox.setBounds(130, 100, 150, 30);
        ArrayList<EquipmentType> types = EquipmentTypeDataBase.getEquipmentTypes();
        for (EquipmentType type : types) {
            typeComboBox.addItem(type.getName());
        }
        panel.add(typeComboBox);
        
        specificationLabel = new JLabel("设备规格：");
        specificationLabel.setBounds(50, 150, 80, 30);
        panel.add(specificationLabel);
        
        specificationTextField = new JTextField();
        specificationTextField.setBounds(130, 150, 150, 30);
        panel.add(specificationTextField);
        
        switchableLabel = new JLabel("是否可开机：");
        switchableLabel.setBounds(50, 250, 80, 30);
        panel.add(switchableLabel);
        
        switchableCheckBox = new JCheckBox();
        switchableCheckBox.setBounds(130, 250, 30, 30);
        panel.add(switchableCheckBox);
        
        descriptionLabel = new JLabel("设备描述：");
        descriptionLabel.setBounds(50, 300, 80, 60);
        panel.add(descriptionLabel);
        
        descriptionTextArea = new JTextArea();
        descriptionTextArea.setBounds(130, 300, 120, 60);
        panel.add(descriptionTextArea);
        
        okButton = new JButton("确定");
        okButton.setBounds(100, 450, 80, 30);
        okButton.addActionListener(this);
        panel.add(okButton);
        
        cancelButton = new JButton("取消");
        cancelButton.setBounds(220, 450, 80, 30);
        cancelButton.addActionListener(this);
        panel.add(cancelButton);
        
        setVisible(true);
    }
    
    public void actionPerformed(ActionEvent e)
    {
        if(e.getSource() == okButton)
        {
            String name = nameTextField.getText();
            String type = (String)typeComboBox.getSelectedItem();
            String specification = specificationTextField.getText();
            String borrowable = "工厂设备";
            boolean switchable = switchableCheckBox.isSelected();
            String description = descriptionTextArea.getText();
            String factoryName = factory.getName();
            
            equipment = new Equipment((EquipmentDataBase.getEquipments().size() != 0)? EquipmentDataBase.getEquipments().get(EquipmentDataBase.getEquipments().size() - 1).getID() + 1 : 1,name, type, specification, borrowable, switchable, description, factoryName);
            EquipmentDataBase.addEquipment(equipment);
            dispose();
        }
        else if(e.getSource() == cancelButton)
        {
            dispose();
        }
    }

    public Equipment getEquipment() {
        return equipment;
    }

}




public class CloudUserGUI extends JFrame{
    private User user;
    private Factory factory;
    private ArrayList<Equipment> equipments;
    private JTable equipmentTable;
    private JPanel panel;
    private JButton newButton;
    private JButton rentButton;
    private JButton deleteButton;
    private JButton switchButton;
    private JLabel rentLabel;
    private JComboBox<String> rentBox;
    private JButton rentConfirmButton;
    private JButton rentCancelButton;
    private DefaultTableModel tableModel;
    private JButton backButton;
    
    public CloudUserGUI(User user) {
        setTitle("云工厂管理员管理系统");
        setSize(1000 , 600);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        this.user = user;
        factory = FactoryDataBase.getFactoryByOwnerName(user.getName());
        equipments = EquipmentDataBase.getEquipmentByFactoryName(factory.getName());
        newButton = new JButton("新建");
        rentButton = new JButton("租用设备");
        deleteButton = new JButton("删除");
        switchButton = new JButton("切换设备状态");
        rentBox = new JComboBox<String>();
        rentConfirmButton = new JButton("确定");
        rentCancelButton = new JButton("取消");

        panel = new JPanel();
        panel.setLayout(null);
        add(panel);
        tableModel = new DefaultTableModel(){
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        tableModel.addColumn("ID");
        tableModel.addColumn("设备名称");
        tableModel.addColumn("设备编号");
        tableModel.addColumn("设备类别");
        tableModel.addColumn("设备规格");
        tableModel.addColumn("设备状态");
        tableModel.addColumn("设备描述");
        tableModel.addColumn("租用状态");
        tableModel.addColumn("所属工厂名称");

        equipmentTable = new JTable(tableModel);
        JScrollPane scrollPane = new JScrollPane(equipmentTable);
        scrollPane.setBounds(50, 150, 900, 300);
        panel.add(scrollPane);
        JPanel buttonPanel = new JPanel();
        buttonPanel.setBounds(10, 50, 900, 40);
        buttonPanel.add(newButton);
        buttonPanel.add(rentButton);
        buttonPanel.add(deleteButton);
        buttonPanel.add(switchButton);
        backButton = new JButton("退出");
        backButton.setBounds(750, 500, 100, 30);
        panel.add(backButton);
        add(buttonPanel);
        add(panel);
        

        updateTable();

        setVisible(true);
        newButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new newEquipmentDialog(CloudUserGUI.this , factory);
                updateTable();
            }
        });

        rentButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new rentEquipmentGUI(CloudUserGUI.this , factory);
                updateTable();
            }
        });

        deleteButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Frame frame = new Frame();
                int selectedRow = equipmentTable.getSelectedRow();
                if (selectedRow == -1) {
                    JOptionPane.showMessageDialog(frame, "请先选择要删除的设备！", "提示", JOptionPane.INFORMATION_MESSAGE);
                    return;
                }
                Equipment equipment = equipments.get(selectedRow);
                if (equipment.isBorrowable().equals("工厂设备")) {
                    equipments.remove(selectedRow);
                    EquipmentDataBase.removeEquipment(equipment);
                } else {
                    equipment.setBorrowable("否");
                    equipment.setFactory(null);
                    EquipmentDataBase.modifyEquipment(equipment);
                }
                updateTable();
            }
        });

        switchButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Frame frame = new Frame();
                int selectedRow = equipmentTable.getSelectedRow();
                if (selectedRow == -1) {
                    JOptionPane.showMessageDialog(frame, "请先选择要切换状态的设备！", "提示", JOptionPane.INFORMATION_MESSAGE);
                    return;
                }
                Equipment equipment = equipments.get(selectedRow);
                equipment.setSwitchable(!equipment.isSwitchable());
                EquipmentDataBase.modifyEquipment(equipment);
                updateTable();
            }
        });
        backButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
                new Login();
            }
        });
    }

    public void updateTable()
    {
        equipments = EquipmentDataBase.getEquipmentByFactoryName(factory.getName());
        tableModel.setRowCount(0);
        for (Equipment equipment : equipments) {
            Vector<String> rowData = new Vector<String>();
            rowData.add(String.valueOf(equipment.getID()));
            rowData.add(equipment.getName());
            rowData.add(equipment.getCode());
            rowData.add(equipment.getType().getName());
            rowData.add(equipment.getSpecification());
            if(equipment.isSwitchable()){
                rowData.add("开机");
            }else{
                rowData.add("关机");
            }
            rowData.add(equipment.getDescription());
            if (equipment.getFactory() == null) {
                rowData.add("未被租用");
                rowData.add("");
            } else if (equipment.isBorrowable().equals("工厂设备")) {
                rowData.add("工厂设备");
                rowData.add(equipment.getFactory().getName());
            } else {
                rowData.add("已被租用");
                rowData.add(equipment.getFactory().getName());
            }
            tableModel.addRow(rowData);

        }
    }

}


        

