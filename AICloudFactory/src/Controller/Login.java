package Controller;

import javax.swing.*;

import DataBase.UserDataBase;
import Model.CloudAdmin;
import Model.SystemAdmin;
import Model.User;
import View.CloudUserGUI;
import View.MenuGUI;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;


class RegisterGUI extends JFrame{
    private JLabel accountLabel;
    private JLabel passwordLabel;
    private JLabel nameLabel;
    private JLabel phoneNumberLabel;
    private JLabel registerTypeLabel;
    private JLabel factoryNameLabel;
    private JLabel factoryIntroLabel;
    private JTextField accountField;
    private JPasswordField passwordField;
    private JTextField nameField;
    private JTextField phoneNumberField;
    private JTextField factoryNameField;
    private JRadioButton cloudFactoryRadioButton;
    private JRadioButton dealerRadioButton;
    private ButtonGroup registerTypeGroup;
    private JTextArea factoryIntroArea;
    private JButton registerButton;
    private JButton cancelButton;
    private JPanel mainPanel;
    private JScrollPane scrollPane;

    public RegisterGUI() {
        super("注册");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        mainPanel = new JPanel(new GridLayout(8, 1, 5, 5)); 

        JPanel accountPanel = new JPanel(new FlowLayout(FlowLayout.CENTER)); 
        accountLabel = new JLabel("账号：", JLabel.CENTER);
        accountPanel.add(accountLabel);
        accountField = new JTextField(20);
        accountPanel.add(accountField);
        mainPanel.add(accountPanel); 

        JPanel passwordPanel = new JPanel(new FlowLayout(FlowLayout.CENTER)); 
        passwordLabel = new JLabel("密码：", JLabel.CENTER);
        passwordPanel.add(passwordLabel);
        passwordField = new JPasswordField(20);
        passwordPanel.add(passwordField);
        mainPanel.add(passwordPanel); 

        JPanel namePanel = new JPanel(new FlowLayout(FlowLayout.CENTER)); 
        nameLabel = new JLabel("真实姓名：", JLabel.CENTER);
        namePanel.add(nameLabel);
        nameField = new JTextField(20);
        namePanel.add(nameField);
        mainPanel.add(namePanel); 

        JPanel phoneNumberPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        phoneNumberLabel = new JLabel("联系方式：", JLabel.CENTER);
        phoneNumberPanel.add(phoneNumberLabel);
        phoneNumberField = new JTextField(20);
        phoneNumberPanel.add(phoneNumberField);
        mainPanel.add(phoneNumberPanel);

        JPanel registerTypePanel = new JPanel(new FlowLayout(FlowLayout.CENTER)); 
        registerTypeLabel = new JLabel("注册方式：", JLabel.CENTER);
        registerTypePanel.add(registerTypeLabel);
        cloudFactoryRadioButton = new JRadioButton("云工厂");
        dealerRadioButton = new JRadioButton("经销商");
        registerTypeGroup = new ButtonGroup();
        registerTypeGroup.add(cloudFactoryRadioButton);
        registerTypeGroup.add(dealerRadioButton);
        registerTypePanel.add(cloudFactoryRadioButton);
        registerTypePanel.add(dealerRadioButton);
        mainPanel.add(registerTypePanel); 
        JPanel empty = new JPanel(new FlowLayout(FlowLayout.CENTER));
        mainPanel.add(empty);

        if(cloudFactoryRadioButton.isSelected()){
            JPanel factoryPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
            factoryNameLabel = new JLabel("工厂名称：", JLabel.CENTER);
            factoryPanel.add(factoryNameLabel);
            factoryNameField = new JTextField(20);
            factoryPanel.add(factoryNameField);
            factoryIntroLabel = new JLabel("工厂简介：", JLabel.CENTER);
            factoryPanel.add(factoryIntroLabel);
            factoryIntroArea = new JTextArea(5, 20);
            JScrollPane scrollPane = new JScrollPane(factoryIntroArea);
            mainPanel.add(scrollPane);
            mainPanel.revalidate();
            mainPanel.repaint();
        } else {
            mainPanel.revalidate();
            mainPanel.repaint();
        }



        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        registerButton = new JButton("注册");
        registerButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String account = accountField.getText();
                String password = new String(passwordField.getPassword());
                String name = nameField.getText();
                String phoneNumber = phoneNumberField.getText();
                String registerType = "";
                if(cloudFactoryRadioButton.isSelected()){
                    registerType = "云工厂";
                }else if(dealerRadioButton.isSelected()){
                    registerType = "经销商";
                }
                String factoryName = "";
                String factoryIntro = "";
                if (cloudFactoryRadioButton.isSelected()) {
                    factoryName = factoryNameField.getText();
                    factoryIntro = factoryIntroArea.getText();
                }
                if (account.isEmpty() || password.isEmpty() || name.isEmpty() || phoneNumber.isEmpty() || (cloudFactoryRadioButton.isSelected() && (factoryName.isEmpty() || factoryIntro.isEmpty()))) {
                    JOptionPane.showMessageDialog(RegisterGUI.this, "请填写完整信息！", "错误", JOptionPane.ERROR_MESSAGE);
                } else {
                    
                    if(registerType.equals("云工厂")){
                        new CloudAdmin(account, password,registerType,name , phoneNumber , (UserDataBase.getUsers().size() != 0) ? UserDataBase.getUsers().get(UserDataBase.getUsers().size() - 1).getId() + 1 : 1,factoryName, factoryIntro);
                    }else{
                        User user = new User(account, password,registerType,name , phoneNumber , (UserDataBase.getUsers().size() != 0) ? UserDataBase.getUsers().get(UserDataBase.getUsers().size() - 1).getId() + 1 : 1);
                        UserDataBase.addUser(user);
                    }

                    
                    JOptionPane.showMessageDialog(RegisterGUI.this, "注册成功！", "成功", JOptionPane.INFORMATION_MESSAGE);
                    dispose();
                    new Login();
                }
            }
        });
        buttonPanel.add(registerButton);
        cloudFactoryRadioButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(cloudFactoryRadioButton.isSelected() && factoryNameLabel == null && factoryNameField == null && factoryIntroLabel == null && factoryIntroArea == null && scrollPane == null){
                    factoryNameLabel = new JLabel("工厂名称：", JLabel.LEADING);
                    mainPanel.add(factoryNameLabel);
                    factoryNameField = new JTextField(20);
                    mainPanel.add(factoryNameField);

                    factoryIntroLabel = new JLabel("工厂简介：", JLabel.LEADING);
                    mainPanel.add(factoryIntroLabel);
                    factoryIntroArea = new JTextArea(5, 20);
                    scrollPane = new JScrollPane(factoryIntroArea);
                    mainPanel.add(scrollPane);
                    mainPanel.revalidate();
                    mainPanel.repaint();
                    }
            }
        });
        dealerRadioButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(dealerRadioButton.isSelected() && factoryNameLabel != null && factoryNameField != null && factoryIntroLabel != null && factoryIntroArea != null && scrollPane != null){
                    mainPanel.remove(factoryNameLabel);
                    mainPanel.remove(factoryNameField);
                    mainPanel.remove(factoryIntroLabel);
                    mainPanel.remove(factoryIntroArea);
                    mainPanel.remove(scrollPane);
                    factoryNameField = null;
                    factoryNameLabel = null;
                    factoryIntroArea = null;
                    factoryIntroLabel = null;
                    scrollPane = null;
                    mainPanel.revalidate();
                    mainPanel.repaint();
            }
        }
        });



        cancelButton = new JButton("取消");
        cancelButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
                new Login();
            }
        });
        buttonPanel.add(cancelButton);

        add(mainPanel, BorderLayout.WEST);
        add(buttonPanel, BorderLayout.SOUTH);

        setSize(500, 500);
        setLocationRelativeTo(null);
        setVisible(true);
    }
}





public class Login extends JFrame implements ActionListener {
    private JLabel title;
    private JLabel accountLabel;
    private JLabel passwordLabel;
    private JTextField accountField;
    private JPasswordField passwordField;
    private JButton loginButton;
    private JButton registerButton;
    private int tryTimes = 3;
    private HashMap<String , String> key = new HashMap<String ,String>(){{
            put("C:\\Users\\DELL\\Desktop\\vscodejava\\Work\\AICloudFactory\\img\\8hvjr.png" , "8hvjr");
            put("C:\\Users\\DELL\\Desktop\\vscodejava\\Work\\AICloudFactory\\img\\95inb.png","95inb");
            put("C:\\Users\\DELL\\Desktop\\vscodejava\\Work\\AICloudFactory\\img\\748em.png","748em");
            put("C:\\Users\\DELL\\Desktop\\vscodejava\\Work\\AICloudFactory\\img\\xp8x2.png","xp8x2");
            put("C:\\Users\\DELL\\Desktop\\vscodejava\\Work\\AICloudFactory\\img\\xt17y.png","xt17y");
        }}; ;
    private HashMap<Integer , String> img = new HashMap<Integer , String>() {{
            put(1 , "C:\\Users\\DELL\\Desktop\\vscodejava\\Work\\AICloudFactory\\img\\8hvjr.png");
            put(2 , "C:\\Users\\DELL\\Desktop\\vscodejava\\Work\\AICloudFactory\\img\\95inb.png");
            put(3 , "C:\\Users\\DELL\\Desktop\\vscodejava\\Work\\AICloudFactory\\img\\748em.png");
            put(4 , "C:\\Users\\DELL\\Desktop\\vscodejava\\Work\\AICloudFactory\\img\\xp8x2.png");
            put(5 , "C:\\Users\\DELL\\Desktop\\vscodejava\\Work\\AICloudFactory\\img\\xt17y.png");
        }};;

    public Login() {
        super("登录");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new GridLayout(4, 1));

        title = new JLabel("云平台制造", JLabel.CENTER);
        title.setFont(new Font("宋体", Font.BOLD, 36));
        add(title);

        JPanel accountPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        accountLabel = new JLabel("账号：", JLabel.RIGHT);
        accountPanel.add(accountLabel);
        accountField = new JTextField(20);
        accountPanel.add(accountField);
        add(accountPanel);

        JPanel passwordPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        passwordLabel = new JLabel("密码：", JLabel.RIGHT);
        passwordPanel.add(passwordLabel);
        passwordField = new JPasswordField(20);
        passwordPanel.add(passwordField);
        add(passwordPanel);

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        loginButton = new JButton("登录");
        loginButton.addActionListener(this);
        buttonPanel.add(loginButton);

        registerButton = new JButton("注册");
        registerButton.addActionListener(this);
        buttonPanel.add(registerButton);
        add(buttonPanel);

        setSize(500, 500);
        setLocationRelativeTo(null);
        setVisible(true);
    }


    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == loginButton) {
            String account = accountField.getText();
            String password = new String(passwordField.getPassword());
            User user = UserDataBase.getUserByAccount(account);
            if (user == null) {
                JOptionPane.showMessageDialog(this, "该用户不存在！", "错误", JOptionPane.ERROR_MESSAGE);
            } else if (!user.getPassword().equals(password)) {
                tryTimes--;
                if (tryTimes == 0) {
                    JOptionPane.showMessageDialog(this, "密码错误，您已经没有尝试机会了！", "错误", JOptionPane.ERROR_MESSAGE);
                    System.exit(0);
                } else {
                    JOptionPane.showMessageDialog(this, "密码错误，您还有" + tryTimes + "次尝试机会！", "错误", JOptionPane.ERROR_MESSAGE);
                }
            } else {
                JDialog jDialog = new JDialog(this,"验证码登录",true);
                jDialog.setSize(500, 150);
                JPanel captchaPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
                JLabel captchaLabel = new JLabel();
                String image = img.get(new Integer(new Random().nextInt(5) + 1));
                captchaLabel.setIcon(new ImageIcon(image));
                captchaPanel.add(captchaLabel);
                JTextField captchaField = new JTextField(10);
                captchaPanel.add(captchaField);
                JButton captchaButton = new JButton("确认");
                captchaButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    if(captchaField.getText().equals(key.get(image))){
                        System.out.println(user.getRole());
                        if(user.getRole().equals("系统管理员")){new MenuGUI();}
                        else if(user.getRole().equals("云工厂")) { new CloudUserGUI(user);}
                        else{ 
                            JOptionPane.showMessageDialog(jDialog, "登陆成功！", "成功", JOptionPane.INFORMATION_MESSAGE); 
                        }
                        setVisible(false);
                        dispose();  
                    }
                    else{
                        JOptionPane.showMessageDialog(jDialog, "验证码错误,请重新登录", "错误", JOptionPane.ERROR_MESSAGE);             
                    }
                    jDialog.setVisible(false);
                        jDialog.dispose();
                }
                });
                captchaPanel.add(captchaButton);
                jDialog.add(captchaPanel); 
                jDialog.setVisible(true);
            }
        } else if (e.getSource() == registerButton) {
            new RegisterGUI();
            dispose();
        }
    }
}