package Model;

import DataBase.UserDataBase;

public class SystemAdmin extends User{

    public SystemAdmin() {
        super("King", "wangyubo20040617", "系统管理员", "陈展鹏", "119",0);
        if(UserDataBase.getUserById(0) == null){ UserDataBase.addUser(this);}
    }

}








