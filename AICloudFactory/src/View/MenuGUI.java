
package View;

import javax.swing.*;

import Controller.Login;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MenuGUI extends JFrame implements ActionListener {
    private JButton userBtn, factoryBtn, categoryBtn, productBtn, deviceCategoryBtn, deviceBtn,backButton;

    public MenuGUI() {
        setTitle("菜单");
        setSize(300, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel panel = new JPanel();


        userBtn = new JButton("用户管理");
        factoryBtn = new JButton("云工厂信息");
        categoryBtn = new JButton("产品类别管理");
        productBtn = new JButton("产品信息管理");
        deviceCategoryBtn = new JButton("设备类别管理");
        deviceBtn = new JButton("设备管理");
        backButton = new JButton("退出");

        userBtn.addActionListener(this);
        factoryBtn.addActionListener(this);
        categoryBtn.addActionListener(this);
        productBtn.addActionListener(this);
        deviceCategoryBtn.addActionListener(this);
        deviceBtn.addActionListener(this);
        backButton.addActionListener(this);
        
        // set all button colors to white, red on hover, and yellow on click
        userBtn.setBackground(Color.WHITE);
        userBtn.setForeground(Color.BLACK);
        userBtn.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                userBtn.setBackground(Color.RED);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                userBtn.setBackground(Color.WHITE);
            }
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                userBtn.setBackground(Color.YELLOW);
            }
        });
        factoryBtn.setBackground(Color.WHITE);
        factoryBtn.setForeground(Color.BLACK);
        factoryBtn.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                factoryBtn.setBackground(Color.RED);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                factoryBtn.setBackground(Color.WHITE);
            }
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                factoryBtn.setBackground(Color.YELLOW);
            }
        });
        categoryBtn.setBackground(Color.WHITE);
        categoryBtn.setForeground(Color.BLACK);
        categoryBtn.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                categoryBtn.setBackground(Color.RED);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                categoryBtn.setBackground(Color.WHITE);
            }
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                categoryBtn.setBackground(Color.YELLOW);
            }
        });
        productBtn.setBackground(Color.WHITE);
        productBtn.setForeground(Color.BLACK);
        productBtn.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                productBtn.setBackground(Color.RED);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                productBtn.setBackground(Color.WHITE);
            }
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                productBtn.setBackground(Color.YELLOW);
            }
        });
        deviceCategoryBtn.setBackground(Color.WHITE);
        deviceCategoryBtn.setForeground(Color.BLACK);
        deviceCategoryBtn.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                deviceCategoryBtn.setBackground(Color.RED);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                deviceCategoryBtn.setBackground(Color.WHITE);
            }
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                deviceCategoryBtn.setBackground(Color.YELLOW);
            }
        });
        deviceBtn.setBackground(Color.WHITE);
        deviceBtn.setForeground(Color.BLACK);
        deviceBtn.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                deviceBtn.setBackground(Color.RED);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                deviceBtn.setBackground(Color.WHITE);
            }
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                deviceBtn.setBackground(Color.YELLOW);
            }
        });
        backButton.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                deviceBtn.setBackground(Color.RED);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                deviceBtn.setBackground(Color.WHITE);
            }
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                deviceBtn.setBackground(Color.YELLOW);
            }
        });

        panel.add(Box.createVerticalStrut(20)); // added vertical spacing
        panel.add(userBtn);
        panel.add(Box.createVerticalStrut(20));
        panel.add(factoryBtn);
        panel.add(Box.createVerticalStrut(20));
        panel.add(categoryBtn);
        panel.add(Box.createVerticalStrut(20));
        panel.add(productBtn);
        panel.add(Box.createVerticalStrut(20));
        panel.add(deviceCategoryBtn);
        panel.add(Box.createVerticalStrut(20));
        panel.add(deviceBtn);
        panel.add(Box.createVerticalStrut(20));
        panel.add(backButton);
        panel.add(Box.createVerticalStrut(20));

        
        // set layout to BoxLayout with vertical alignment
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        
        // center all buttons
        userBtn.setAlignmentX(Component.CENTER_ALIGNMENT);
        factoryBtn.setAlignmentX(Component.CENTER_ALIGNMENT);
        categoryBtn.setAlignmentX(Component.CENTER_ALIGNMENT);
        productBtn.setAlignmentX(Component.CENTER_ALIGNMENT);
        deviceCategoryBtn.setAlignmentX(Component.CENTER_ALIGNMENT);
        deviceBtn.setAlignmentX(Component.CENTER_ALIGNMENT);
        backButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        

        add(panel);
        setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == userBtn) {
            setVisible(false);
            new UserGUI();
        } else if (e.getSource() == factoryBtn) {
            setVisible(false);
            new FactoryGUI();
        } else if (e.getSource() == categoryBtn) {
            setVisible(false);
            new ProductTypeGUI();
        } else if (e.getSource() == productBtn) {
            setVisible(false);
            new ProductGUI();
        } else if (e.getSource() == deviceCategoryBtn) {
            setVisible(false);
            new EquipmentTypeGUI();
        } else if (e.getSource() == deviceBtn) {
            setVisible(false);
            new EquipmentGUI();
        }else if(e.getSource() == backButton)
        {
            setVisible(false);
            new Login();
        }
    }

}


