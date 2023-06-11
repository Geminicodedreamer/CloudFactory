package View;

import Model.User;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.*;

import DataBase.EquipmentDataBase;
import DataBase.EquipmentTypeDataBase;
import DataBase.FactoryDataBase;
import Model.Equipment;
import Model.Factory;

// 云管理员管理界面：
// 可以维护自己工厂的自有设备，也可以租用产能中心的设备
// 构造函数传入参数为User
// 先在FactoryDataBase中找到该用户所对应的工厂，再在EquipmentDataBase中找到该工厂租用的设备，然后将设备信息展示在表格中
// 首先有四个按键，分别是新建、租用设备、删除、切换设备状态
// 点击新建后会弹出界面，需要初始化设备名称、设备类别(从EquipmentTypeDataBase中选)、设备规格、设备状态、设备描述，然后其他属性中租用状态为"工厂设备"，所属工厂是当前人对应的工厂
// 点击删除选中行的设备租用状态变成未被租用(如果租用状态是"工厂设备"则删除设备)然后更新表格和数据库
// 切换设备状态就是将设备状态对调，true就是开机，false就是关机，只有这两种状态
// 点击租用设备会弹出窗口显示所有未租用的设备信息，再次点击确定选中行的设备会被该工厂租用，设备状态变成开机
// 下面是表格，一共有9列，ID、设备名称、设备编号、设备类别、设备规格、设备状态、设备描述、租用状态、所属工厂
// 下面是FactoryDataBase和RquipmentDataBase

class EquipmentTableModel
{
    
}

 
public class CloudUserGUI{
    private User user;
    private Factory factory;
    private ArrayList<Equipment> equipments;
    private JTable table;
    private JFrame frame;
    private JPanel panel;
    private JButton newButton;
    private JButton rentButton;
    private JButton deleteButton;
    private JButton switchButton;
    private JTextField nameField;
    private JComboBox<String> typeBox;
    private JTextField specificationField;
    private JComboBox<String> statusBox;
    private JTextArea descriptionArea;
    private JComboBox<String> factoryBox;
    private JButton confirmButton;
    private JButton cancelButton;
    private JDialog dialog;
    private JLabel nameLabel;
    private JLabel typeLabel;
    private JLabel specificationLabel;
    private JLabel statusLabel;
    private JLabel descriptionLabel;
    private JLabel factoryLabel;
    private JLabel rentLabel;
    private JComboBox<String> rentBox;
    private JButton rentConfirmButton;
    private JButton rentCancelButton;

    public CloudUserGUI(User user) {
        this.user = user;
        factory = FactoryDataBase.getFactoryByOwnerName(user.getName());
        equipments = EquipmentDataBase.getEquipmentByFactoryName(factory.getName());
        table = new JTable(new EquipmentTableModel(equipments));
        frame = new JFrame("设备管理");
        panel = new JPanel();
        newButton = new JButton("新建");
        rentButton = new JButton("租用设备");
        deleteButton = new JButton("删除");
        switchButton = new JButton("切换设备状态");
        nameField = new JTextField(10);
        typeBox = new JComboBox<String>();
        specificationField = new JTextField(10);
        statusBox = new JComboBox<String>(new String[]{"true", "false"});
        descriptionArea = new JTextArea(5, 10);
        factoryBox = new JComboBox<String>(new String[]{factory.getName()});
        confirmButton = new JButton("确定");
        cancelButton = new JButton("取消");
        dialog = new JDialog(frame, "新建设备", true);
        nameLabel = new JLabel("设备名称：");
        typeLabel = new JLabel("设备类别：");
        specificationLabel = new JLabel("设备规格：");
        statusLabel = new JLabel("设备状态：");
        descriptionLabel = new JLabel("设备描述：");
        factoryLabel = new JLabel("所属工厂：");
        rentLabel = new JLabel("选择设备：");
        rentBox = new JComboBox<String>();
        rentConfirmButton = new JButton("确定");
        rentCancelButton = new JButton("取消");

        panel.setLayout(new BorderLayout());
        panel.add(new JScrollPane(table), BorderLayout.CENTER);
        JPanel buttonPanel = new JPanel();
        buttonPanel.add(newButton);
        buttonPanel.add(rentButton);
        buttonPanel.add(deleteButton);
        buttonPanel.add(switchButton);
        panel.add(buttonPanel, BorderLayout.SOUTH);

        newButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                nameField.setText("");
                typeBox.setSelectedIndex(0);
                specificationField.setText("");
                statusBox.setSelectedIndex(0);
                descriptionArea.setText("");
                factoryBox.setSelectedIndex(0);
                dialog.setVisible(true);
            }
        });

        rentButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                rentBox.removeAllItems();
                for (Equipment equipment : equipments) {
                    if (equipment.isBorrowable().equals("未被租用")) {
                        rentBox.addItem(equipment.getName());
                    }
                }
                if (rentBox.getItemCount() == 0) {
                    JOptionPane.showMessageDialog(frame, "没有可租用的设备！", "提示", JOptionPane.INFORMATION_MESSAGE);
                    return;
                }
                rentBox.setSelectedIndex(0);
                rentConfirmButton.setEnabled(true);
                rentCancelButton.setEnabled(true);
                JDialog rentDialog = new JDialog(frame, "租用设备", true);
                JPanel rentPanel = new JPanel();
                rentPanel.setLayout(new GridLayout(2, 1));
                JPanel rentBoxPanel = new JPanel();
                rentBoxPanel.add(rentLabel);
                rentBoxPanel.add(rentBox);
                rentPanel.add(rentBoxPanel);
                JPanel rentButtonPanel = new JPanel();
                rentButtonPanel.add(rentConfirmButton);
                rentButtonPanel.add(rentCancelButton);
                rentPanel.add(rentButtonPanel);
                rentDialog.add(rentPanel);
                rentDialog.pack();
                rentDialog.setLocationRelativeTo(frame);
                rentDialog.setVisible(true);
            }
        });

        deleteButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int selectedRow = table.getSelectedRow();
                if (selectedRow == -1) {
                    JOptionPane.showMessageDialog(frame, "请先选择要删除的设备！", "提示", JOptionPane.INFORMATION_MESSAGE);
                    return;
                }
                Equipment equipment = equipments.get(selectedRow);
                if (equipment.isBorrowable().equals("工厂设备")) {
                    equipments.remove(selectedRow);
                    EquipmentDataBase.removeEquipment(equipment);
                    table.updateUI();
                } else {
                    equipment.setBorrowable("未被租用");
                    equipment.setFactory(null);
                    EquipmentDataBase.modifyEquipment(equipment);
                    table.updateUI();
                }
            }
        });

        switchButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int selectedRow = table.getSelectedRow();
                if (selectedRow == -1) {
                    JOptionPane.showMessageDialog(frame, "请先选择要切换状态的设备！", "提示", JOptionPane.INFORMATION_MESSAGE);
                    return;
                }
                Equipment equipment = equipments.get(selectedRow);
                equipment.setSwitchable(!equipment.isSwitchable());
                EquipmentDataBase.modifyEquipment(equipment);
                table.updateUI();
            }
        });


        confirmButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String name = nameField.getText();
                String type = (String) typeBox.getSelectedItem();
                String specification = specificationField.getText();
                boolean status = Boolean.parseBoolean((String) statusBox.getSelectedItem());
                String description = descriptionArea.getText();
                String factoryName = (String) factoryBox.getSelectedItem();
                Equipment equipment = new Equipment((EquipmentDataBase.getEquipments().size() != 0)? EquipmentDataBase.getEquipments().get(EquipmentDataBase.getEquipments().size() - 1).getID() + 1 : 1 ,name, type, specification, "工厂设备",status, description, factoryName);
                EquipmentDataBase.addEquipment(equipment);
                equipments.add(equipment);
                table.updateUI();
                dialog.dispose();
            }
        });
    }

    public static ArrayList<Equipment> getEquipmentByType(String type) {
        ArrayList<Equipment> equipments = EquipmentDataBase.getEquipments();
        ArrayList<Equipment> equipmentsOfType = new ArrayList<Equipment>();
        for (Equipment equipment : equipments) {
            if (equipment.getType().getName().equals(type)) {
                equipmentsOfType.add(equipment);
            }
        }
        return equipmentsOfType;
    }

}


        

