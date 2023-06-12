package DataBase;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;

import Model.User;

public class UserDataBase {
    private static ArrayList<User> users = new ArrayList<User>();
    private static final String FILE_PATH = "C:\\Users\\DELL\\Desktop\\vscodejava\\Work\\AICloudFactory\\Data\\Users.dat";

    public static void addUser(User user) {
        users.add(user);
        updateDatabase();
    }

    public static void removeUser(User user) {
        users.remove(user);
        updateDatabase();
    }

    public static ArrayList<User> getUsers() {
        loadDatabase();
        return users;
    }

    private static void updateDatabase() {
        try {
            FileOutputStream fos = new FileOutputStream(FILE_PATH);
            for (User user : users) {
                String userString = user.getId() + "_" + user.getAccount() + "_" + user.getPassword() + "_"
                        + user.getRole() + "_" + user.getName() + "_" + user.getPhoneNumber();
                fos.write(userString.getBytes());
                fos.write("\n".getBytes());
            }
            fos.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void loadDatabase() {
        try {
            FileInputStream fis = new FileInputStream(FILE_PATH);
            byte[] buffer = new byte[1024];
            int len = 0;
            StringBuilder sb = new StringBuilder();
            while ((len = fis.read(buffer)) != -1) {
                sb.append(new String(buffer, 0, len));
            }
            users.clear();
            String[] userStrings = sb.toString().split("\n");
            for (String userString : userStrings) {
                String[] userAttrs = userString.split("_");
                User user = new User(userAttrs[1], userAttrs[2], userAttrs[3], userAttrs[4], userAttrs[5] ,Integer.parseInt(userAttrs[0]));
                users.add(user);
            }
            fis.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static User getUserByAccount(String userName) {
        loadDatabase();
        for (User user : users) {
            if (user.getAccount().equals(userName)) {
                return user;
            }
        }
        return null;
    }

    public static User getUserById(int id) {
        loadDatabase();
        for (User user : users) {
            if (user.getId() == id) {
                return user;
            }
        }
        return null;
    }

    public static void updateUser(User selectedUser) {
        for (int i = 0; i < users.size(); i++) {
            if (users.get(i).getId() == selectedUser.getId()) {
                users.set(i, selectedUser);
                updateDatabase();
                break;
            }
        }
    }


    public static User getUserByName(String ownername) {
        loadDatabase();
        for (User user : users) {
            if (user.getName().equals(ownername)) {
                return user;
            }
        }
        return null;
    }

}
