package Model;

//Employee class
public class Employee {
    private String telephone;
    private int age;
    private String gender;
    private String name;

    public Employee(String telephone, int age, String gender, String name) {
        setTelephone(telephone);
        setAge(age);
        setGender(gender);
        setName(name);
    }

    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}

