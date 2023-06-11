package Model;

import java.util.Random;

import DataBase.EquipmentDataBase;
import DataBase.FactoryDataBase;
import DataBase.ProductTypeDataBase;

public class Product {
    private Equipment equipment;
    private int id;
    private String productNumber;
    private String productName;
    private String productCategory;
    private Factory factory;
    private ProductType productType; 
    
    private String productDescription;
    
    public Product(int id , String productNumber, String productName, String productCategory , int factoryID , int equipmentID , ProductType productType, String productDescription) {
        setID(id);
        setProductNumber(productNumber);
        setProductName(productName);
        setProductCategory(productCategory);
        new FactoryDataBase();
        setFactory(FactoryDataBase.getFactoryByID(factoryID));
        new EquipmentDataBase();
        setEquipment(EquipmentDataBase.getEquipmentByID(equipmentID));
        setProductType(productType);
        setProductDescription(productDescription);
    }
    
    public Product(int id , String productName, String productCategory , int factoryID , int equipmentID , ProductType productType, String productDescription) {
        setID(id);
        setProductNumber();
        setProductName(productName);
        setProductCategory(productCategory);
        new FactoryDataBase();
        setFactory(FactoryDataBase.getFactoryByID(factoryID));
        new EquipmentDataBase();
        setEquipment(EquipmentDataBase.getEquipmentByID(equipmentID));
        setProductType(productType);
        setProductDescription(productDescription);
    }
    
    public String getProductDescription() {
        return productDescription;
    }

    public void setProductDescription(String productDescription) {
        this.productDescription = productDescription;
    }

    private void setProductNumber() {
        Random random = new Random();
        String str = "PNO";
        for(int i = 0 ; i < 10 ; i ++)
        {
            str = str + random.nextInt(10);
        }
        this.productNumber = str;
    }
    public void setProductType(ProductType productType) {
        this.productType = productType;
    }


    public ProductType getProductType() {
        return productType;    
    }

    
    private void setID(int id) {
        this.id = id;
    }
    
    public int getID() {
        return id;
    }

    public void setEquipment(Equipment equipmentByName) {
        this.equipment = equipmentByName;
    }


    public void setFactory(Factory factoryByName) {
        this.factory = factoryByName;
    }

    
    public Factory getFactory() {
        return factory;
    }

    public Equipment getEquipment() {
        return equipment;
    }

    

    public String getProductNumber() {
        return productNumber;
    }

    public void setProductNumber(String productNumber) {
        this.productNumber = productNumber;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getProductCategory() {
        return productCategory;
    }

    public void setProductCategory(String productCategory) {
        this.productCategory = productCategory;
    }

    
    public int getFactoryID() {
        return factory.getId();
    }

    
    public int getEquipmentID() {
        return equipment.getID();
    }


}



