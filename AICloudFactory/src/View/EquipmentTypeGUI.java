package View;

import javax.swing.*;
import javax.swing.plaf.basic.BasicBorders.ButtonBorder;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import DataBase.EquipmentTypeDataBase;
import Model.EquipmentType;

public class EquipmentTypeGUI extends JFrame implements ActionListener {
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

    public EquipmentTypeGUI() {
        setTitle("设备类型管理系统");
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
        tableModel.addColumn("设备类型");
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
        loadEquipmentTypes();
        setVisible(true);

    }

    private void loadEquipmentTypes() {
        ArrayList<EquipmentType> equipmentTypes = EquipmentTypeDataBase.getEquipmentTypes();
        for (EquipmentType equipmentType : equipmentTypes) {
            Object[] rowData = new Object[2];
            rowData[0] = equipmentType.getID();
            rowData[1] = equipmentType.getName();
            tableModel.addRow(rowData);
        }
    }

    private void resetTable() {
        tableModel.setRowCount(0);
        loadEquipmentTypes();
    }


    private void searchEquipmentType() {
        String equipmentTypeName = searchField.getText();
        EquipmentType equipmentType = EquipmentTypeDataBase.getEquipmentTypeByName(equipmentTypeName);
        if (equipmentType != null) {
            tableModel.setRowCount(0);
            Object[] rowData = new Object[2];
            rowData[0] = equipmentType.getID();
            rowData[1] = equipmentType.getName();
            tableModel.addRow(rowData);
        } else {
            JOptionPane.showMessageDialog(this, "未找到该设备类型");
        }
    }

    private void addEquipmentType() {
        new EquipmentTypeDataBase();
        String equipmentTypeName = JOptionPane.showInputDialog(this, "请输入设备类型名称");
        if (equipmentTypeName != null && !equipmentTypeName.equals("")) {
            EquipmentType equipmentType = new EquipmentType((EquipmentTypeDataBase.getEquipmentTypes().size()!= 0)?EquipmentTypeDataBase.getEquipmentTypes().get(EquipmentTypeDataBase.getEquipmentTypes().size() - 1).getID() + 1 : 1 , equipmentTypeName);
            EquipmentTypeDataBase.addEquipmentType(equipmentType);
            Object[] rowData = new Object[2];
            rowData[0] = equipmentType.getID();
            rowData[1] = equipmentType.getName();
            tableModel.addRow(rowData);
        }
    }

    private void deleteEquipmentType() {
        int selectedRow = table.getSelectedRow();
        if (selectedRow != -1) {
            int option = JOptionPane.showConfirmDialog(this, "确定删除该设备类型吗？");
            if (option == JOptionPane.YES_OPTION) {
                String equipmentTypeName = (String) tableModel.getValueAt(selectedRow, 1);
                EquipmentType equipmentType = EquipmentTypeDataBase.getEquipmentTypeByName(equipmentTypeName);
                EquipmentTypeDataBase.removeEquipmentType(equipmentType);
                tableModel.removeRow(selectedRow);
            }
        } else {
            JOptionPane.showMessageDialog(this, "请先选择要删除的设备类型");
        }
    }


    private void modifyEquipmentType() {
        int selectedRow = table.getSelectedRow();
        new EquipmentTypeDataBase();
        if (selectedRow != -1) {
            String equipmentTypename = (String) tableModel.getValueAt(selectedRow, 1);
            String equipmentTypeName = JOptionPane.showInputDialog(this, "请输入新的设备类型名称");
            if (equipmentTypeName != null && !equipmentTypeName.equals("")) {
                EquipmentType equipmentType = EquipmentTypeDataBase.getEquipmentTypeByName(equipmentTypename);
                EquipmentTypeDataBase.modifyEquipmentType(equipmentType, equipmentTypeName);
                tableModel.setValueAt(equipmentTypeName, selectedRow, 1);
            }
        } else {
            JOptionPane.showMessageDialog(this, "请先选择要修改的设备类型");
        }
    }
 
    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == searchButton) {
            searchEquipmentType();
        } else if (e.getSource() == resetButton) {
            resetTable();
        } else if (e.getSource() == addButton) {
            addEquipmentType();
        } else if (e.getSource() == deleteButton) {
            deleteEquipmentType();
        } else if (e.getSource() == modifyButton) {
            modifyEquipmentType();
        } else if (e.getSource() == backButton) {
            dispose();
            new MenuGUI();
        }
    }
    
}