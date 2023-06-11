package DataBase;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;

import Model.Factory;

public class FactoryDataBase {
    private static ArrayList<Factory> factories = new ArrayList<Factory>();
    private static final String FILE_PATH = "C:\\Users\\DELL\\Desktop\\vscodejava\\Work\\AICloudFactory\\Data\\factories.dat";

    public static void addFactory(Factory factory) {
        factories.add(factory);
        updateDatabase();
    }

    public static void removeFactory(Factory factory) {
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
            while (line != null) {
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




    public static Factory getFactoryByName(String factoryName) {
        loadDatabase();
        for (Factory factory : factories) {
            if (factory.getName().equals(factoryName)) {
                return factory;
            }
        }
        return null;
    }


    public static void updateFactoryStatus(int id, String status) {
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



}


