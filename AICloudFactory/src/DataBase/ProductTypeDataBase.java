package DataBase;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;

import Model.ProductType;

public class ProductTypeDataBase {
    private static ArrayList<ProductType> productTypes = new ArrayList<ProductType>();
    private static final String FILE_PATH = "C:\\Users\\DELL\\Desktop\\vscodejava\\Work\\AICloudFactory\\Data\\ProductTypes.dat";

    public static void addProductType(ProductType productType) {
        loadDatabase();
        productTypes.add(productType);
        updateDatabase();
    }

    public static void removeProductType(ProductType productType) {
        loadDatabase();
        for(int i = 0 ; i < productTypes.size() ; i ++)
        {
            if(productTypes.get(i).getID() == productType.getID())
            {
                productTypes.remove(i);
                break;
            }
        }
        updateDatabase();
    }

    public static ArrayList<ProductType> getProductTypes() {
        loadDatabase();
        return productTypes;
    }

    private static void updateDatabase() {
        try {
            FileOutputStream outputStream = new FileOutputStream(FILE_PATH);
            for (ProductType productType : productTypes) {
                String productTypeInfo = productType.getID() + "_" + productType.getName();
                outputStream.write(productTypeInfo.getBytes());
                outputStream.write("\n".getBytes());
            }
            outputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void loadDatabase() {
        try {
            FileInputStream inputStream = new FileInputStream(FILE_PATH);
            byte[] buffer = new byte[1024];
            int length;
            String productTypeInfo = "";
            productTypes.clear();
            while ((length = inputStream.read(buffer)) != -1) {
                productTypeInfo += new String(buffer, 0, length);
            }
            String[] productTypeInfos = productTypeInfo.split("\n");
            for (String info : productTypeInfos) {
                String[] productTypeAttributes = info.split("_");
                ProductType productType = new ProductType(Integer.parseInt(productTypeAttributes[0]) , productTypeAttributes[1]);
                productType.setID(Integer.parseInt(productTypeAttributes[0]));
                productTypes.add(productType);
            }
            inputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public static ProductType getProductTypeByName(String productTypeName) {
        loadDatabase();
        for (ProductType productType : productTypes) {
            if (productType.getName().equals(productTypeName)) {
                return productType;
            }
        }
        return null;
    }

    
    public static void modifyProductType(ProductType productType) {
        loadDatabase();
        for(int i = 0 ; i < productTypes.size() ;  i++)
        {
            if(productTypes.get(i).getID() == productType.getID())
            {
                productTypes.get(i).setName(productType.getName());
                break;
            }
        }
        updateDatabase();
    }

    
}

