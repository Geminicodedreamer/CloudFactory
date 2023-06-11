
// 设备管理系统
// 最上面的是输入框、按照名称查询按钮、重置
// 点击按照类别查询按钮，根据输入框输入的信息，找到该设备名称的设备并展示在下面的表格中，点击重置后表格恢复原状
// 下一行有四个按钮，新建、删除、修改、设备状态
// 点击新建后弹出输入框，除了id和设备编号，其余都得输入初始化，其中设备类别选择时会出现下拉列表，只从现有的EquipmentTypeDataBase中选择，新建之后数据库和表格会更新
// 点击删除后会弹出界面确定是否删除，删除后表格和数据库更新
// 点击修改后会对选择的那一行进行修改，弹出窗口以修改信息，id和设备编号不可修改
// 点击设备状态后改变选择的那一行的设备的状态，true变false，false变true，更新数据库和表格
// 下面显示表格，表格信息从EquipmentDataBase读入，表格不可被修改
// 表格一共9列分别是ID、设备名称、设备编号、设备类别、设备规格、设备状态、设备描述、租用状态、所属工厂名称
// 其中设备状态true为开机false为关机，租用状态分为已被租用、未被租用(factory == null)、工厂设备，如果未被租用则后面的所属工厂为空
// 右下角有返回键返回主菜单
// 补充：
// 当设备被租用时，不允许删除。设备所属设备类型数据来源于系统维护的设备类型信息
// 当设备为关闭状态时，点击远程开机，可以远程开启设备
// 当设备为未被租用状态时，点击远程关机，可以远程关闭设备
// 表格信息从EquipmentDataBase读入，一切修改操作都从数据库操作
// 用swing写
// 下面是EquipmentDataBase类和Equipment类


package View;


import javax.swing.*;
import javax.swing.table.DefaultTableModel;

import DataBase.EquipmentDataBase;
import DataBase.EquipmentTypeDataBase;
import Model.Equipment;
import Model.EquipmentType;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Vector;

import javax.swing.*; 
import java.awt.*; 
import java.awt.event.*; 

class EquipmentDialog extends JDialog implements ActionListener
{
    private JLabel nameLabel, codeLabel, typeLabel, specificationLabel, borrowableLabel, switchableLabel, descriptionLabel, factoryNameLabel;
    private JTextField nameTextField, codeTextField, specificationTextField, factoryNameTextField;
    private JComboBox<String> typeComboBox, borrowableComboBox;
    private JCheckBox switchableCheckBox;
    private JTextArea descriptionTextArea;
    private JButton okButton, cancelButton;
    private JPanel panel;
    Equipment equipment;
    public EquipmentDialog(JFrame parent)
    {
        super(parent, "新建设备", true);
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
        
        borrowableLabel = new JLabel("是否可借用：");
        borrowableLabel.setBounds(50, 200, 80, 30);
        panel.add(borrowableLabel);
        
        String[] borrowableOptions = {"是", "否"};
        borrowableComboBox = new JComboBox<String>(borrowableOptions);
        borrowableComboBox.setBounds(130, 200, 150, 30);
        panel.add(borrowableComboBox);
        
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
        
        factoryNameLabel = new JLabel("所属工厂名称：");
        factoryNameLabel.setBounds(50, 400, 100, 30);
        panel.add(factoryNameLabel);
        
        factoryNameTextField = new JTextField();
        factoryNameTextField.setBounds(130, 400, 120, 30);
        panel.add(factoryNameTextField);
        
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
            String borrowable = (String)borrowableComboBox.getSelectedItem();
            boolean switchable = switchableCheckBox.isSelected();
            String description = descriptionTextArea.getText();
            String factoryName = factoryNameTextField.getText();
            
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

class EquipmentModifyDialog extends JDialog implements ActionListener {
    private JPanel panel;
    private JLabel nameLabel;
    private JTextField nameTextField;
    private JLabel typeLabel;
    private JComboBox<String> typeComboBox;
    private JLabel specificationLabel;
    private JTextField specificationTextField;
    private JLabel borrowableLabel;
    private JComboBox<String> borrowableComboBox;
    private JLabel switchableLabel;
    private JCheckBox switchableCheckBox;
    private JLabel descriptionLabel;
    private JTextArea descriptionTextArea;
    private JLabel factoryNameLabel;
    private JTextField factoryNameTextField;
    private JButton okButton;
    private JButton cancelButton;

    private Equipment equipment;

    public EquipmentModifyDialog(JFrame parent , Equipment equipment) {
        super(parent , "修改设备信息" , true);
        this.equipment = equipment;
        setSize(400, 600);
        setLocationRelativeTo(null);
        setResizable(false);

        panel = new JPanel();
        panel.setLayout(null);
        add(panel);

        nameLabel = new JLabel("设备名称：");
        nameLabel.setBounds(50, 50, 80, 30);
        panel.add(nameLabel);

        nameTextField = new JTextField(equipment.getName());
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
        String currentType = equipment.getType().getName();
        for (int i = 0; i < types.size(); i++) {
            if (types.get(i).getName().equals(currentType)) {
                typeComboBox.setSelectedIndex(i);
                break;
            }
        }
        panel.add(typeComboBox);

        specificationLabel = new JLabel("设备规格：");
        specificationLabel.setBounds(50, 150, 80, 30);
        panel.add(specificationLabel);

        specificationTextField = new JTextField(equipment.getSpecification());
        specificationTextField.setBounds(130, 150, 150, 30);
        panel.add(specificationTextField);

        switchableLabel = new JLabel("是否可开机：");
        switchableLabel.setBounds(50, 200, 80, 30);
        panel.add(switchableLabel);

        switchableCheckBox = new JCheckBox();
        switchableCheckBox.setBounds(130, 200, 30, 30);
        switchableCheckBox.setSelected(equipment.isSwitchable());
        panel.add(switchableCheckBox);

        descriptionLabel = new JLabel("设备描述：");
        descriptionLabel.setBounds(50, 250, 80, 60);
        panel.add(descriptionLabel);

        descriptionTextArea = new JTextArea(equipment.getDescription());
        descriptionTextArea.setBounds(130, 250, 120, 60);
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

    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == okButton) {
            String name = nameTextField.getText();
            String type = (String) typeComboBox.getSelectedItem();
            String specification = specificationTextField.getText();
            boolean switchable = switchableCheckBox.isSelected();
            String description = descriptionTextArea.getText();

            equipment.setName(name);
            equipment.setType(type);
            equipment.setSpecification(specification);
            equipment.setSwitchable(switchable);
            equipment.setDescription(description);

            EquipmentDataBase.modifyEquipment(equipment);
            dispose();
        } else if (e.getSource() == cancelButton) {
            dispose();
        }
    }
}


public class EquipmentGUI extends JFrame {
    private JPanel panel;
    private JTextField nameTextField;
    private JComboBox<String> typeComboBox;
    private JButton searchByNameButton;
    private JButton searchByTypeButton;
    private JButton resetButton;
    private JTable equipmentTable;
    private JButton newButton;
    private JButton deleteButton;
    private JButton modifyButton;
    private JButton switchButton;
    private JButton backButton;

    private DefaultTableModel tableModel;
    private ArrayList<Equipment> equipments;

    public EquipmentGUI() {
        setTitle("设备管理系统");
        setSize(1000, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        panel = new JPanel();
        panel.setLayout(null);
        add(panel);

        JLabel nameLabel = new JLabel("设备名称：");
        nameLabel.setBounds(50, 50, 80, 30);
        panel.add(nameLabel);

        nameTextField = new JTextField();
        nameTextField.setBounds(130, 50, 150, 30);
        panel.add(nameTextField);

        searchByNameButton = new JButton("按名称查询");
        searchByNameButton.setBounds(300, 50, 100, 30);
        panel.add(searchByNameButton);

        JLabel typeLabel = new JLabel("设备类别：");
        typeLabel.setBounds(450, 50, 80, 30);
        panel.add(typeLabel);

        typeComboBox = new JComboBox<String>();
        typeComboBox.setBounds(530, 50, 150, 30);
        panel.add(typeComboBox);

        searchByTypeButton = new JButton("按类别查询");
        searchByTypeButton.setBounds(700, 50, 100, 30);
        panel.add(searchByTypeButton);

        resetButton = new JButton("重置");
        resetButton.setBounds(850, 50, 100, 30);
        panel.add(resetButton); 
        
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(null);
        buttonPanel.setBounds(50, 470, 900, 30);
        panel.add(buttonPanel);

        newButton = new JButton("新建");
        newButton.setBounds(0, 0, 100, 30);
        buttonPanel.add(newButton);

        deleteButton = new JButton("删除");
        deleteButton.setBounds(150, 0, 100, 30);
        buttonPanel.add(deleteButton);

        modifyButton = new JButton("修改");
        modifyButton.setBounds(300, 0, 100, 30);
        buttonPanel.add(modifyButton);

        switchButton = new JButton("切换设备状态");
        switchButton.setBounds(450, 0, 130, 30);
        buttonPanel.add(switchButton);

        backButton = new JButton("返回");
        backButton.setBounds(750, 0, 100, 30);
        buttonPanel.add(backButton);

        panel.add(buttonPanel);

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

       

        // 初始化设备列表
        updateTable();

        // 初始化设备类别下拉列表
        ArrayList<EquipmentType> types = EquipmentTypeDataBase.getEquipmentTypes();
        for (EquipmentType type : types) {
            typeComboBox.addItem(type.getName());
        }

        
        // 重置按钮事件
        resetButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                updateTable();
            }
        });

        // 新建按钮事件
        newButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new EquipmentDialog(EquipmentGUI.this);
                updateTable();
            }
        });


        // 删除按钮事件
        deleteButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int row = equipmentTable.getSelectedRow();
                if (row == -1) {
                    JOptionPane.showMessageDialog(null, "请选择要删除的设备！", "提示", JOptionPane.WARNING_MESSAGE);
                    return;
                }
                int id = Integer.parseInt(tableModel.getValueAt(row, 0).toString());
                int result = JOptionPane.showConfirmDialog(null, "确定要删除该设备吗？", "提示", JOptionPane.YES_NO_OPTION);
                if (result == JOptionPane.YES_OPTION) {
                    EquipmentDataBase.removeEquipment(EquipmentDataBase.getEquipmentByID(id));
                    tableModel.removeRow(row);
                }
            }
        });

        // 修改按钮事件
        modifyButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int row = equipmentTable.getSelectedRow();
                if (row == -1) {
                    JOptionPane.showMessageDialog(null, "请选择要修改的设备！", "提示", JOptionPane.WARNING_MESSAGE);
                    return;
                }
                int id = Integer.parseInt(tableModel.getValueAt(row, 0).toString());
                Equipment equipment = DataBase.EquipmentDataBase.getEquipmentByID(id);
                new EquipmentModifyDialog(EquipmentGUI.this , equipment);
                updateTable();
            }
        });

        // 设备状态按钮事件
        switchButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int row = equipmentTable.getSelectedRow();
                if (row == -1) {
                    JOptionPane.showMessageDialog(null, "请选择要修改状态的设备！", "提示", JOptionPane.WARNING_MESSAGE);
                    return;
                }
                int id = Integer.parseInt(tableModel.getValueAt(row, 0).toString());
                Equipment equipment = EquipmentDataBase.getEquipmentByID(id);
                equipment.setSwitchable(equipment.isSwitchable());
                EquipmentDataBase.modifyEquipmentstatus(equipment);
                updateTable();
            }
        });

        // 返回按钮事件
        backButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
                new MenuGUI();
            }
        });


        
        
        // 按名称查询按钮事件
        searchByNameButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String name = nameTextField.getText();
                if (name.equals("")) {
                    JOptionPane.showMessageDialog(null, "请输入设备名称！", "提示", JOptionPane.WARNING_MESSAGE);
                    return;
                }
                Equipment equipment = DataBase.EquipmentDataBase.getEquipmentByName(name);
                if (equipment == null) {
                    JOptionPane.showMessageDialog(null, "未找到该设备！", "提示", JOptionPane.WARNING_MESSAGE);
                    return;
                }
                tableModel.setRowCount(0);
                Vector<String> rowData = new Vector<String>();
                rowData.add(String.valueOf(equipment.getID()));
                rowData.add(equipment.getName());
                rowData.add(equipment.getCode());
                rowData.add(equipment.getType().getName());
                rowData.add(equipment.getSpecification());
                rowData.add(String.valueOf(equipment.isSwitchable()));
                rowData.add(equipment.getDescription());
                if (equipment.getFactory() == null) {
                    rowData.add("未被租用");
                    rowData.add("");
                } else if (equipment.getFactory().getName().equals("工厂设备")) {
                    rowData.add("工厂设备");
                    rowData.add("");
                } else {
                    rowData.add("已被租用");
                    rowData.add(equipment.getFactory().getName());
                }
                tableModel.addRow(rowData);
            }
        });

        // 按类别查询按钮事件
searchByTypeButton.addActionListener(new ActionListener() {
    @Override
    public void actionPerformed(ActionEvent e) {
        String type = typeComboBox.getSelectedItem().toString();
        if (type.equals("")) {
            JOptionPane.showMessageDialog(null, "请选择设备类别！", "提示", JOptionPane.WARNING_MESSAGE);
            return;
        }
        ArrayList<Equipment> equipments = EquipmentDataBase.getEquipmentByType(type);
        if (equipments.size() == 0) {
            JOptionPane.showMessageDialog(null, "未找到该类别设备！", "提示", JOptionPane.WARNING_MESSAGE);
            return;
        }
        tableModel.setRowCount(0);
        for (Equipment equipment : equipments) {
            Vector<String> rowData = new Vector<String>();
            rowData.add(String.valueOf(equipment.getID()));
            rowData.add(equipment.getName());
            rowData.add(equipment.getCode());
            rowData.add(equipment.getType().getName());
            rowData.add(equipment.getSpecification());
            rowData.add(String.valueOf(equipment.isSwitchable()));
            rowData.add(equipment.getDescription());
            if (equipment.getFactory() == null) {
                rowData.add("未被租用");
                rowData.add("");
            } else if (equipment.getFactory().getName().equals("工厂设备")) {
                rowData.add("工厂设备");
                rowData.add("");
            } else {
                rowData.add("已被租用");
                rowData.add(equipment.getFactory().getName());
            }
            tableModel.addRow(rowData);
        }
    }
});

        setVisible(true);
    }
    public void updateTable()
    {equipments = DataBase.EquipmentDataBase.getEquipments();
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
                    } else if (equipment.getFactory().getName().equals("工厂设备")) {
                        rowData.add("工厂设备");
                        rowData.add("");
                    } else {
                        rowData.add("已被租用");
                        rowData.add(equipment.getFactory().getName());
                    }
                    tableModel.addRow(rowData);
        
                }
            }
    public void addEquipment(Equipment equipment) {
        Vector<String> rowData = new Vector<String>();
        rowData.add(String.valueOf(equipment.getID()));
        rowData.add(equipment.getName());
        rowData.add(equipment.getCode());
        rowData.add(equipment.getType().getName());
        rowData.add(equipment.getSpecification());
        rowData.add(String.valueOf(equipment.isSwitchable()));
        rowData.add(equipment.getDescription());
        if (equipment.getFactory() == null) {
            rowData.add("未被租用");
            rowData.add("");
        } else if (equipment.getFactory().getName().equals("工厂设备")) {
            rowData.add("工厂设备");
            rowData.add("");
        } else {
            rowData.add("已被租用");
            rowData.add(equipment.getFactory().getName());
        }
        tableModel.addRow(rowData);
        EquipmentDataBase.addEquipment(equipment);
    }
    
    public void deleteEquipment(int id) {
        int index = -1;
        for (int i = 0; i < equipments.size(); i++) {
            if (equipments.get(i).getID() == id) {
                index = i;
                break;
            }
        }
        if (index == -1) {
            JOptionPane.showMessageDialog(null, "未找到该设备！", "提示", JOptionPane.WARNING_MESSAGE);
            return;
        }
        tableModel.removeRow(index);
        DataBase.EquipmentDataBase.removeEquipment(EquipmentDataBase.getEquipmentByID(id));
    }
    
    public void modifyEquipment(Equipment equipment) {
        int index = -1;
        for (int i = 0; i < equipments.size(); i++) {
            if (equipments.get(i).getID() == equipment.getID()) {
                index = i;
                break;
            }
        }
        if (index == -1) {
            JOptionPane.showMessageDialog(null, "未找到该设备！", "提示", JOptionPane.WARNING_MESSAGE);
            return;
        }
        Vector<String> rowData = new Vector<String>();
        rowData.add(String.valueOf(equipment.getID()));
        rowData.add(equipment.getName());
        rowData.add(equipment.getCode());
        rowData.add(equipment.getType().getName());
        rowData.add(equipment.getSpecification());
        rowData.add(String.valueOf(equipment.isSwitchable()));
        rowData.add(equipment.getDescription());
        if (equipment.getFactory() == null) {
            rowData.add("未被租用");
            rowData.add("");
        } else if (equipment.getFactory().getName().equals("工厂设备")) {
            rowData.add("工厂设备");
            rowData.add("");
        } else {
            rowData.add("已被租用");
            rowData.add(equipment.getFactory().getName());
        }
        tableModel.removeRow(index);
        tableModel.insertRow(index, rowData);
        DataBase.EquipmentDataBase.modifyEquipment(equipment);
    }
    
    public void switchEquipment(int id) {
        int index = -1;
        for (int i = 0; i < equipments.size(); i++) {
            if (equipments.get(i).getID() == id) {
                index = i;
                break;
            }
        }
        if (index == -1) {
            JOptionPane.showMessageDialog(null, "未找到该设备！", "提示", JOptionPane.WARNING_MESSAGE);
            return;
        }
        Equipment equipment = equipments.get(index);
        if (equipment.isSwitchable()) {
            equipment.setSwitchable(false);
        } else {
            equipment.setSwitchable(true);
        }
        Vector<String> rowData = new Vector<String>();
        rowData.add(String.valueOf(equipment.getID()));
        rowData.add(equipment.getName());
        rowData.add(equipment.getCode());
        rowData.add(equipment.getType().getName());
        rowData.add(equipment.getSpecification());
        rowData.add(String.valueOf(equipment.isSwitchable()));
        rowData.add(equipment.getDescription());
        if (equipment.getFactory() == null) {
            rowData.add("未被租用");
            rowData.add("");
        } else if (equipment.getFactory().getName().equals("工厂设备")) {
            rowData.add("工厂设备");
            rowData.add("");
        } else {
            rowData.add("已被租用");
            rowData.add(equipment.getFactory().getName());
        }
        tableModel.removeRow(index);
        tableModel.insertRow(index, rowData);
        DataBase.EquipmentDataBase.modifyEquipment(equipment);
    }
    
    public void searchByName(String name) {
        if (name.equals("")) {
            JOptionPane.showMessageDialog(null, "请输入设备名称！", "提示", JOptionPane.WARNING_MESSAGE);
            return;
        }
        Equipment equipment = DataBase.EquipmentDataBase.getEquipmentByName(name);
        if (equipment == null) {
            JOptionPane.showMessageDialog(null, "未找到该设备！", "提示", JOptionPane.WARNING_MESSAGE);
            return;
        }
        tableModel.setRowCount(0);
        Vector<String> rowData = new Vector<String>();
        rowData.add(String.valueOf(equipment.getID()));
        rowData.add(equipment.getName());
        rowData.add(equipment.getCode());
        rowData.add(equipment.getType().getName());
        rowData.add(equipment.getSpecification());
        rowData.add(String.valueOf(equipment.isSwitchable()));
        rowData.add(equipment.getDescription());
        if (equipment.getFactory() == null) {
            rowData.add("未被租用");
            rowData.add("");
        } else if (equipment.getFactory().getName().equals("工厂设备")) {
            rowData.add("工厂设备");
            rowData.add("");
        } else {
            rowData.add("已被租用");
            rowData.add(equipment.getFactory().getName());
        }
        tableModel.addRow(rowData);
    }

    public static void main(String[] args) {
        new EquipmentGUI();
    }
}
                    

 


