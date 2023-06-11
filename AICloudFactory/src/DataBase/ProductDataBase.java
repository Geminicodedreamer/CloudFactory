package DataBase;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.time.Period;
import java.util.ArrayList;

import Model.Product;

public class ProductDataBase {
    private static ArrayList<Product> products = new ArrayList<Product>();
    private static final String FILE_PATH = "C:\\Users\\DELL\\Desktop\\vscodejava\\Work\\AICloudFactory\\Data\\Products.dat";

    public static void addProduct(Product product) {
        products.add(product);
        updateDatabase();
    }

    public static void removeProduct(Product product) {
        products.remove(product);
        updateDatabase();
    }

    public static ArrayList<Product> getProducts() {
        loadDatabase();
        return products;
    }



    private static void updateDatabase() {
        try {
            FileOutputStream outputStream = new FileOutputStream(FILE_PATH);
            for (Product product : products) {
                String productInfo = product.getID() + "_" + product.getProductNumber() + "_" + product.getProductName() + "_" + product.getProductCategory() + "_" + product.getFactory().getId()  + "_" +product.getEquipment().getID() + "_" + product.getProductType().getName() + "_" + product.getProductDescription();
                outputStream.write(productInfo.getBytes());
                outputStream.write("\n".getBytes());
            }
            outputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    
    public static void updateDataBase(Product selectedProduct) {
        for (Product product : products) {
            if (product.getID() == selectedProduct.getID()) {
                product.setProductNumber(selectedProduct.getProductNumber());
                product.setProductName(selectedProduct.getProductName());
                product.setProductCategory(selectedProduct.getProductCategory());
                product.setFactory(selectedProduct.getFactory());
                product.setEquipment(selectedProduct.getEquipment());
                product.setProductType(selectedProduct.getProductType());
                product.setProductDescription(selectedProduct.getProductDescription());
                updateDatabase();
                break;
            }
        }
    }

    
    public static void loadDatabase() {
        try {
            FileInputStream inputStream = new FileInputStream(FILE_PATH);
            byte[] buffer = new byte[1024];
            int length;
            String productInfo = "";
            products.clear();
            while ((length = inputStream.read(buffer)) != -1) {
                productInfo += new String(buffer, 0, length);
            }
            String[] productInfos = productInfo.split("\n");
            for (String info : productInfos) {
                if(!info.isEmpty()){
                String[] productAttributes = info.split("_");
                Product product = new Product(Integer.parseInt(productAttributes[0]), productAttributes[1], productAttributes[2], productAttributes[3], Integer.parseInt(productAttributes[4]),Integer.parseInt(productAttributes[5]), ProductTypeDataBase.getProductTypeByName(productAttributes[6]) , productAttributes[7]);
                products.add(product);
                }
            }
            inputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }



    public static Product getProductByName(String productName) {
        loadDatabase();
        for (Product product : products) {
            if (product.getProductName().equals(productName)) {
                return product;
            }
        }
        return null;
    }

    public static Product getProductByID(int id) {
        loadDatabase();
        for (Product product : products) {
            if (product.getID() == id) {
                return product;
            }
        }
        return null;
    }

    public static void modifyProduct(Product newProduct) {
        int idx = products.indexOf(getProductByID(newProduct.getID()));
        products.get(idx).setProductName(newProduct.getProductName());
        products.get(idx).setProductCategory(newProduct.getProductCategory());
        products.get(idx).setFactory(newProduct.getFactory());
        products.get(idx).setEquipment(newProduct.getEquipment());
        products.get(idx).setProductType(newProduct.getProductType());
        products.get(idx).setProductDescription(newProduct.getProductDescription());
        updateDatabase();
    }




}


