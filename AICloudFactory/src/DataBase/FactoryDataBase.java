package DataBase;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

import Model.Factory;
import Model.User;

public class FactoryDataBase {
    private static ArrayList<Factory> factories = new ArrayList<Factory>();
    private static final String FILE_PATH = "C:\\Users\\DELL\\Desktop\\vscodejava\\Work\\AICloudFactory\\Data\\factories.dat";

    public static void addFactory(Factory factory) {
        loadDatabase();
        factories.add(factory);
        updateDatabase();
    }

    public static void removeFactory(Factory factory) {
        loadDatabase();
        factories.remove(factory);
        updateDatabase();
    }


    public static ArrayList<Factory> getFactories() {
        loadDatabase();
        return factories;
    }

    public static void updateDatabase() {
        try {
            FileWriter writer = new FileWriter(FILE_PATH);
            for (Factory factory : factories) {
                String factoryInfo = factory.getId() + "_" + factory.getName() + "_" +  factory.getIntroduction() + "_" + factory.getOwner().getName() + "_" + factory.getStatus() + "\n";
                writer.write(factoryInfo);
            }
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void loadDatabase() {
        try {
            BufferedReader reader = new BufferedReader(new FileReader(FILE_PATH));
            factories.clear();
            String line = reader.readLine();
            while (line != null && !line.equals("")) {
                String[] infoArray = line.split("_");
                Factory factory = new Factory(Integer.parseInt(infoArray[0]), infoArray[1], infoArray[2], infoArray[3] , infoArray[4]);
                factories.add(factory);
                line = reader.readLine();
            }
            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void removeFactoryByOwnerid(int id) {
        loadDatabase();
        for (Factory factory : factories) {
            if (factory.getOwner().getId() == id) {
                factories.remove(factory);
                updateDatabase();
                return;
            }
        }
    }



    public static Factory getFactoryByName(String factoryName) {
        loadDatabase();
        for (Factory factory : factories) {
            if (factory.getName().equals(factoryName)) {
                return factory;
            }
        }
        return null;
    }

    
public static Factory getFactoryByOwnerName(String ownerName) {
        loadDatabase();
        for (Factory factory : factories) {
            if (factory.getOwner().getName().equals(ownerName)) {
                return factory;
            }
        }
        return null;
    }


    public static void updateFactoryStatus(int id, String status) {
        loadDatabase();
        for (Factory factory : factories) {
            if (factory.getId() == id) {
                factory.setStatus(status);
                updateDatabase();
                return;
            }
        }
    }

    public static Factory getFactoryByID(int factoryID) {
        loadDatabase();
        for (Factory factory : factories) {
            if (factory.getId() == factoryID) {
                return factory;
            }
        }
        return null;
    }

    public static Factory getFactoryByownerID(int id) {
        loadDatabase();
        for(int i = 0 ; i < factories.size() ;  i++)
        {
            if(factories.get(i).getOwner().getId() == id)
            {
                return factories.get(i);
            }
        }
        return null;
    }

    public static void modifyFactory(Factory factory) {
        loadDatabase();
        for (int i = 0; i < factories.size(); i++) {
            if (factories.get(i).getId() == factory.getId()) {
                factories.set(i, factory);
                updateDatabase();
                return;
            }
        }
    }




}


