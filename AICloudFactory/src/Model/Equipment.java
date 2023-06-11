package Model;

import java.util.Random;

import DataBase.EquipmentTypeDataBase;
import DataBase.FactoryDataBase;
import Model.EquipmentType;

// 设备类
public class Equipment {
    private int ID; // 设备ID
    private String name; // 设备名称
    private String code; // 设备编码
    private EquipmentType type; // 设备类型
    private String specification; // 设备规格
    private String borrowable; // 是否可借用
    private boolean switchable; // 是否可开机
    private String description; // 设备描述

    private Factory factory; // 借用此设备的工厂

    public Equipment(int id, String name, String code, String type, String specification, String borrowable,
            boolean switchable, String description, String factoryName) {
        setID(id);
        setName(name);
        setCode(code);
        setType(type);
        setSpecification(specification);
        setBorrowable(borrowable);
        setSwitchable(switchable);
        setDescription(description);
        setFactory(FactoryDataBase.getFactoryByName(factoryName));
    }

    public Equipment(int id, String name, String type, String specification, String borrowable, boolean switchable,
            String description, String factoryName) {
        setID(id);
        setName(name);
        setCode(EquipmentCode());
        setType(type);
        setSpecification(specification);
        setBorrowable(borrowable);
        setSwitchable(switchable);
        setDescription(description);
        setFactory(FactoryDataBase.getFactoryByName(factoryName));
    }

    private String EquipmentCode() {
        Random random = new Random();
        String str = "PNO";
        for (int i = 0; i < 10; i++) {
            str = str + random.nextInt(10);
        }
        return str;
    }

    public Factory getFactory() {
        return factory;
    }

    public void setFactory(Factory factory) {
        this.factory = factory;
    }

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public EquipmentType getType() {
        return type;
    }

    public void setType(String type) {
        this.type = EquipmentTypeDataBase.getEquipmentTypeByName(type);
    }

    public String getSpecification() {
        return specification;
    }

    public void setSpecification(String specification) {
        this.specification = specification;
    }

    public String isBorrowable() {
        return borrowable;
    }

    public void setBorrowable(String borrowable) {
        this.borrowable = borrowable;
    }

    public boolean isSwitchable() {
        return switchable;
    }

    public void setSwitchable(boolean switchable) {
        this.switchable = switchable;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
