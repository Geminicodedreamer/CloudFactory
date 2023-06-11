package Model;


public class EquipmentType {
    private int id;
    private String name;
    public EquipmentType(int id, String name) {
        setName(name);
        setID(id);
    }

    public void setID(int id) {
        this.id = id;
    }

 
    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public int getID() {
        return id;
    }

}

