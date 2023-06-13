
package Model;

import java.util.ArrayList;

import DataBase.UserDataBase;

public class Factory {
    private int id;
    private String name;
    private String introduction;
    private User owner;
    private String status;
    private ArrayList<Employee> employees;
    

    public Factory(int id,String name, String introduction, String ownername, String status) {
        setId(id);
        setName(name);
        setIntroduction(introduction);
        setOwner(UserDataBase.getUserByName(ownername));
        setStatus(status);
        employees = new ArrayList<Employee>();
    }

    public int getId() {
        return this.id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getIntroduction() {
        return introduction;
    }

    public void setIntroduction(String introduction) {
        this.introduction = introduction;
    }

    public User getOwner() {
        return owner;
    }

    public void setOwner(User owner) {
        this.owner = owner;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
    
    public void addEmployee(Employee employee) {
        employees.add(employee);
    }
    
    public void removeEmployee(Employee employee) {
        employees.remove(employee);
    }
    
    public ArrayList<Employee> getEmployees() {
        return employees;
    }
}




