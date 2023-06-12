package View;

import javax.swing.*;
import javax.swing.plaf.MenuItemUI;
import javax.swing.table.DefaultTableModel;

import DataBase.UserDataBase;
import Model.User;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

class newuser extends JDialog implements ActionListener{
    private JPanel panel ;
    private JLabel accountLabel;
    private JTextField accountField ;
    private JLabel nameLabel;
    private JTextField nameField ;
    private JLabel phoneNumberLabel ;
    private JTextField phoneNumberField;
    private JLabel roleLabel ;
    private JComboBox<String> roleComboBox ;
    private JButton confirmButton ;
    private JButton cancelButton ;
    public newuser(Frame parent , UserGUI usergui)
    {
        super(parent ,"填入用户基本信息",true);
        setSize(400, 400);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        panel = new JPanel();
        panel.setLayout(null);
        add(panel);

        accountLabel = new JLabel("用户名:");
        accountLabel.setBounds(50, 50, 80, 30);
        panel.add(accountLabel);

        accountField = new JTextField();
        accountField.setBounds(150, 50, 150, 30);
        panel.add(accountField);

        nameLabel = new JLabel("姓名:");
        nameLabel.setBounds(50, 100, 80, 30);
        panel.add(nameLabel);

        nameField = new JTextField();
        nameField.setBounds(150, 100, 150, 30);
        panel.add(nameField);

        phoneNumberLabel = new JLabel("电话号码:");
        phoneNumberLabel.setBounds(50, 150, 80, 30);
        panel.add(phoneNumberLabel);

        phoneNumberField = new JTextField();
        phoneNumberField.setBounds(150, 150, 150, 30);
        panel.add(phoneNumberField);

        roleLabel = new JLabel("用户类型:");
        roleLabel.setBounds(50, 200, 80, 30);
        panel.add(roleLabel);

        String[] roles = { "云工厂", "经销商" };
        roleComboBox = new JComboBox<>(roles);
        roleComboBox.setBounds(150, 200, 150, 30);
        panel.add(roleComboBox);

        confirmButton = new JButton("确认");
        confirmButton.setBounds(100, 250, 80, 30);
        confirmButton.addActionListener(this);
        panel.add(confirmButton);

        cancelButton = new JButton("取消");
        cancelButton.setBounds(200, 250, 80, 30);
        cancelButton.addActionListener(this);
        panel.add(cancelButton);
        setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource() == confirmButton) {
            String account = accountField.getText();
            String name = nameField.getText();
            String phoneNumber = phoneNumberField.getText();
            String role = (String) roleComboBox.getSelectedItem();
            if(account.isEmpty() || name.isEmpty() || phoneNumber.isEmpty()){
                Frame frame = new Frame();
                JOptionPane.showMessageDialog(frame, "请填写完整信息。");
                return;
            }
            User newUser = new User( account, "", role, name, phoneNumber , (UserDataBase.getUsers().size()!= 0) ? UserDataBase.getUsers().get(UserDataBase.getUsers().size() - 1).getId() + 1 : 1);
            UserDataBase.addUser(newUser);
            dispose();
        } else if(e.getSource() == cancelButton) {
            dispose();
        }
    }
}
public class UserGUI extends JFrame implements ActionListener {
    private static final long serialVersionUID = 1L;
    private JPanel panel;
    private JLabel nameLabel;
    private JTextField nameField;
    private JButton searchButton;
    private JButton newButton;
    private JButton deleteButton;
    private JButton modifyButton;
    private JButton resetButton;
    private JButton backButton;
    private JTable table;
    private JScrollPane scrollPane;
    private DefaultTableModel model;
    private ArrayList<User> users;
    private User selectedUser;
    private boolean flag = true;
    public UserGUI() {
        setTitle("用户管理界面");
        setSize(800, 600);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        panel = new JPanel();
        panel.setLayout(null);
        add(panel);

        nameLabel = new JLabel("用户名:");
        nameLabel.setBounds(50, 50, 50, 30);
        panel.add(nameLabel);

        nameField = new JTextField();
        nameField.setBounds(100, 50, 150, 30);
        panel.add(nameField);

        searchButton = new JButton("查找");
        searchButton.setBounds(270, 50, 80, 30);
        searchButton.addActionListener(this);
        panel.add(searchButton);

        newButton = new JButton("新建");
        newButton.setBounds(50, 100, 80, 30);
        newButton.addActionListener(this);
        panel.add(newButton);

        deleteButton = new JButton("删除");
        deleteButton.setBounds(150, 100, 80, 30);
        deleteButton.addActionListener(this);
        panel.add(deleteButton);

        modifyButton = new JButton("修改");
        modifyButton.setBounds(250, 100, 80, 30);
        modifyButton.addActionListener(this);
        panel.add(modifyButton);

        resetButton = new JButton("重置");
        resetButton.setBounds(350, 100, 80, 30);
        resetButton.addActionListener(this);
        panel.add(resetButton);

        backButton = new JButton("返回");
        backButton.setBounds(650, 500, 80, 30);
        backButton.addActionListener(this);
        panel.add(backButton);

        String[] columnNames = { "ID", "用户名", "姓名", "电话号码", "类型" };
        model = new DefaultTableModel(columnNames, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        table = new JTable(model);
        table.getColumnModel().getColumn(0).setPreferredWidth(50);
        table.getColumnModel().getColumn(1).setPreferredWidth(100);
        table.getColumnModel().getColumn(2).setPreferredWidth(100);
        table.getColumnModel().getColumn(3).setPreferredWidth(150);
        table.getColumnModel().getColumn(4).setPreferredWidth(100);

        scrollPane = new JScrollPane(table);
        scrollPane.setBounds(50, 150, 700, 300);
        panel.add(scrollPane);

        loadUsers();
        showUsers();
        setVisible(true);
    }

    private void loadUsers() {
        UserDataBase.loadDatabase();
        users = UserDataBase.getUsers();
    }

    private void showUsers() {
        model.getDataVector().removeAllElements();
        for (User user : users) {
            Object[] rowData = { user.getId(), user.getAccount(), user.getName(), user.getPhoneNumber(),
                    user.getRole() };
            model.addRow(rowData);
        }
    }

    private void searchUser() {
        String name = nameField.getText();
        if (name.isEmpty()) {
            JOptionPane.showMessageDialog(this, "请输入要查找的用户名。");
            return;
        }
        selectedUser = UserDataBase.getUserByAccount(name);
        if (selectedUser == null) {
            JOptionPane.showMessageDialog(this, "未找到该用户。");
            return;
        }
        model.setRowCount(0);
        Object[] rowData = { selectedUser.getId(), selectedUser.getAccount(), selectedUser.getName(),
                selectedUser.getPhoneNumber(), selectedUser.getRole() };
        model.addRow(rowData);
    }

    private void newUser() {
        new newuser(this , this);
        resetTable();
    }

    private void deleteUser() {
        int[] selectedRows = table.getSelectedRows();
        if (selectedRows.length == 0) {
            JOptionPane.showMessageDialog(this, "请选择要删除的用户。");
            return;
        }
        int option = JOptionPane.showConfirmDialog(this, "你确定要删除所选用户吗？");
        new UserDataBase();
        if (option == JOptionPane.YES_OPTION) {
            for (int i = selectedRows.length - 1; i >= 0; i--) {
                int id = (int) table.getValueAt(selectedRows[i], 0);
                User user = UserDataBase.getUserById(id);
                if (user != null) {
                    UserDataBase.removeUser(user);
                }
                model.removeRow(selectedRows[i]);
            }
        }
    }

    private void modifyUser() {
        int selectedRow = table.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "请选择要修改的用户。");
            return;
        }
        int id = (int) table.getValueAt(selectedRow, 0);
        selectedUser = UserDataBase.getUserById(id);
        if (selectedUser == null) {
            JOptionPane.showMessageDialog(this, "未找到该用户。");
            return;
        }
        JFrame frame = new JFrame("修改用户信息");
        frame.setSize(400, 400);
        frame.setLocationRelativeTo(null);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        JPanel panel = new JPanel();
        panel.setLayout(null);
        frame.add(panel);

        JLabel accountLabel = new JLabel("用户名:");
        accountLabel.setBounds(50, 50, 80, 30);
        panel.add(accountLabel);

        JTextField accountField = new JTextField(selectedUser.getAccount());
        accountField.setBounds(150, 50, 150, 30);
        panel.add(accountField);

        JLabel nameLabel = new JLabel("姓名:");
        nameLabel.setBounds(50, 100, 80, 30);
        panel.add(nameLabel);

        JTextField nameField = new JTextField(selectedUser.getName());
        nameField.setBounds(150, 100, 150, 30);
        panel.add(nameField);

        JLabel phoneNumberLabel = new JLabel("电话号码:");
        phoneNumberLabel.setBounds(50, 150, 80, 30);
        panel.add(phoneNumberLabel);

        JTextField phoneNumberField = new JTextField(selectedUser.getPhoneNumber());
        phoneNumberField.setBounds(150, 150, 150, 30);
        panel.add(phoneNumberField);

        JLabel roleLabel = new JLabel("用户类型:");
        roleLabel.setBounds(50, 200, 80, 30);
        panel.add(roleLabel);

        String[] roles = { "云工厂", "经销商" };
        JComboBox<String> roleComboBox = new JComboBox<>(roles);
        roleComboBox.setSelectedItem(selectedUser.getRole());
        roleComboBox.setBounds(150, 200, 150, 30);
        panel.add(roleComboBox);

        JButton confirmButton = new JButton("确认");
        confirmButton.setBounds(100, 250, 80, 30);
        confirmButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String account = accountField.getText();
                String name = nameField.getText();
                String phoneNumber = phoneNumberField.getText();
                String role = (String) roleComboBox.getSelectedItem();
                selectedUser.setAccount(account);
                selectedUser.setName(name);
                selectedUser.setPhoneNumber(phoneNumber);
                selectedUser.setRole(role);
                UserDataBase.updateUser(selectedUser);
                showUsers();
                frame.dispose();
            }
        });

        panel.add(confirmButton);

        JButton cancelButton = new JButton("取消");
        cancelButton.setBounds(200, 250, 80, 30);
        cancelButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                frame.dispose();
            }
        });
        panel.add(cancelButton);

        frame.setVisible(true);

    }

    public void resetTable() {
        nameField.setText("");
        selectedUser = null;
        loadUsers();
        showUsers();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == searchButton) {
            searchUser();
        } else if (e.getSource() == newButton) {
            newUser();
        } else if (e.getSource() == deleteButton) {
            deleteUser();
        } else if (e.getSource() == modifyButton) {
            modifyUser();
        } else if (e.getSource() == resetButton) {
            resetTable();
        } else if (e.getSource() == backButton) {
            dispose();
            new MenuGUI();
        }
    }
}
