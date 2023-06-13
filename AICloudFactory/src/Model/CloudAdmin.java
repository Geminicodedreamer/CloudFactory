package Model;


import DataBase.FactoryDataBase;
import DataBase.UserDataBase;

public class CloudAdmin extends User{
    private Factory factory;
   
   
    public CloudAdmin(String account, String password, String role, String name, String phoneNumber, int id , String factoryName , String factoryintroduction) {
        super(account, password, role, name, phoneNumber, id);
        if(UserDataBase.getUserById(id) == null) UserDataBase.addUser(this);
        else UserDataBase.updateUser(this);
        setFactory(factoryName, factoryintroduction, name);
    }
 

    public void setFactory(String factoryName, String factoryintroduction, String name) {
        this.factory = new Factory((FactoryDataBase.getFactories().size() != 0)?FactoryDataBase.getFactories().get(FactoryDataBase.getFactories().size() - 1).getId() + 1 : 1 ,factoryName , factoryintroduction , name ,"正常" );
        FactoryDataBase.addFactory(factory);        
    }

public Factory getFactory() {
    return factory;
}

}


