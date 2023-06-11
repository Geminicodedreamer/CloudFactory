package View;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableModel;

import DataBase.FactoryDataBase;
import Model.Factory;

public class FactoryGUI extends JFrame implements ActionListener {

    private static final long serialVersionUID = 1L;
    private JPanel topPanel, centerPanel, bottomPanel, searchPanel, tablePanel, buttonPanel;
    private JLabel searchLabel;
    private JTextField searchField;
    private JButton searchButton, toggleButton, backButton;
    private JTable factoryTable;
    private DefaultTableModel tableModel;
    private JScrollPane scrollPane;
    private ArrayList<Factory> factories;
    private Factory selectedFactory;

    public FactoryGUI() {
        super("工厂管理系统");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setPreferredSize(new Dimension(800, 600));
        setLayout(new BorderLayout());

        // Top Panel
        topPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));

        // Center Panel
        centerPanel = new JPanel(new BorderLayout());
        searchPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        searchLabel = new JLabel("工厂名称：");
        searchField = new JTextField(20);
        searchButton = new JButton("查找");
        searchButton.addActionListener(this);
        toggleButton = new JButton("切换状态");
        toggleButton.addActionListener(this);
        
        searchPanel.add(searchLabel);
        searchPanel.add(searchField);
        searchPanel.add(searchButton);
        searchPanel.add(toggleButton);
        new FactoryDataBase();
        FactoryDataBase.loadDatabase();
        tablePanel = new JPanel(new GridLayout(1, 1));
        factories = FactoryDataBase.getFactories();
        String[] columnNames = {"ID", "工厂名称", "工厂简介", "负责人", "负责人的联系方式", "负责人登录账号用户名", "工厂状态"};
        Object[][] data = new Object[factories.size()][8];
        for (int i = 0; i < factories.size(); i++) {
            data[i][0] = factories.get(i).getId();
            data[i][1] = factories.get(i).getName();
            data[i][2] = factories.get(i).getIntroduction();
            if(factories.get(i).getOwner() != null)
            {data[i][3] = factories.get(i).getOwner().getName();
            data[i][4] = factories.get(i).getOwner().getPhoneNumber();
            data[i][5] = factories.get(i).getOwner().getAccount();}
            else{
                data[i][3] = "";
            data[i][4] = "";
            data[i][5] = "";
            }
            data[i][6] = factories.get(i).getStatus();

        }
        tableModel = new DefaultTableModel(data, columnNames) {
            private static final long serialVersionUID = 1L;

            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        factoryTable = new JTable(tableModel);
        factoryTable.getTableHeader().setReorderingAllowed(false);
        factoryTable.getColumnModel().getColumn(0).setPreferredWidth(50);
        factoryTable.getColumnModel().getColumn(1).setPreferredWidth(100);
        factoryTable.getColumnModel().getColumn(2).setPreferredWidth(200);
        factoryTable.getColumnModel().getColumn(3).setPreferredWidth(100);
        factoryTable.getColumnModel().getColumn(4).setPreferredWidth(150);
        factoryTable.getColumnModel().getColumn(5).setPreferredWidth(150);
        factoryTable.getColumnModel().getColumn(6).setPreferredWidth(100);
        scrollPane = new JScrollPane(factoryTable);
        tablePanel.add(scrollPane);

        centerPanel.add(searchPanel, BorderLayout.NORTH);
        centerPanel.add(tablePanel, BorderLayout.CENTER);

        // Bottom Panel
        bottomPanel = new JPanel(new BorderLayout());
        buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        
        backButton = new JButton("返回");
        backButton.addActionListener(this);
        
        buttonPanel.add(backButton);
        bottomPanel.add(buttonPanel, BorderLayout.EAST);

        add(topPanel, BorderLayout.NORTH);
        add(centerPanel, BorderLayout.CENTER);
        add(bottomPanel, BorderLayout.SOUTH);

        pack();
        setLocationRelativeTo(null);
        setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == searchButton) {
            String factoryName = searchField.getText();
            if (factoryName.equals("")) {
                JOptionPane.showMessageDialog(this, "请输入工厂名称！", "错误", JOptionPane.ERROR_MESSAGE);
            } else {
                selectedFactory = FactoryDataBase.getFactoryByName(factoryName);
                if (selectedFactory == null) {
                    JOptionPane.showMessageDialog(this, "未找到该工厂！", "错误", JOptionPane.ERROR_MESSAGE);
                } else {
                    for (int i = 0; i < factoryTable.getRowCount(); i++) {
                        if (factoryTable.getValueAt(i, 1).equals(factoryName)) {
                            factoryTable.setRowSelectionInterval(i, i);
                            break;
                        }
                    }
                }
            }
        } else if (e.getSource() == toggleButton) {
            int selectedRow = factoryTable.getSelectedRow();
            if (selectedRow == -1) {
                JOptionPane.showMessageDialog(this, "请选择要切换状态的工厂！");
            }//切换工厂状态
            else {
                int id = (int) factoryTable.getValueAt(selectedRow, 0);
                String status = (String) factoryTable.getValueAt(selectedRow, 6);
                if (status.equals("正常")) {
                    FactoryDataBase.updateFactoryStatus(id, "停产");
                    factoryTable.setValueAt("停产", selectedRow, 6);
                } else {
                    FactoryDataBase.updateFactoryStatus(id, "正常");
                    factoryTable.setValueAt("正常", selectedRow, 6);
                }
            }
        } else if (e.getSource() == backButton) {
            dispose();
            new MenuGUI();
        }
    }

}




