
package Model;

public class User {
    private String account;
    private String password;
    private String role;
    private String name;
    private int id;
    private String phoneNumber;

    
    public User(String account, String password, String role, String name, String phoneNumber, int id) {
        setAccount(account);
        setPassword(password);
        setRole(role);
        setName(name);
        setPhoneNumber(phoneNumber);
        setId(id);
    }

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }
}
