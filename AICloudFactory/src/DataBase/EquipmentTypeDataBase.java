package DataBase;


import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;

import javax.print.attribute.standard.Sides;

import Model.EquipmentType;

public class EquipmentTypeDataBase {
    private static ArrayList<EquipmentType> equipmentTypes = new ArrayList<EquipmentType>();
    private static final String FILE_PATH = "C:\\Users\\DELL\\Desktop\\vscodejava\\Work\\AICloudFactory\\Data\\EquipmentTypes.dat";

    public static void addEquipmentType(EquipmentType equipmentType) {
        loadDatabase();
        equipmentTypes.add(equipmentType);
        updateDatabase();
    }

    public static void removeEquipmentType(EquipmentType equipmentType) {
        loadDatabase();
        for(int i = 0 ; i < equipmentTypes.size(); i ++)
        {
            if(equipmentTypes.get(i).getID() == equipmentType.getID())
            {
                equipmentTypes.remove(i);
                break;
            }
        }
        updateDatabase();
    }

    public static ArrayList<EquipmentType> getEquipmentTypes() {
        loadDatabase();
        return equipmentTypes;
    }

    private static void updateDatabase() {
        try {
            FileOutputStream outputStream = new FileOutputStream(FILE_PATH);
            for (EquipmentType equipmentType : equipmentTypes) {
                String equipmentTypeInfo = equipmentType.getID() + "_" + equipmentType.getName();
                outputStream.write(equipmentTypeInfo.getBytes());
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
            String equipmentTypeInfo = "";
            equipmentTypes.clear();
            while ((length = inputStream.read(buffer)) != -1) {
                equipmentTypeInfo += new String(buffer, 0, length);
            }
            String[] equipmentTypeInfos = equipmentTypeInfo.split("\n");
            for (String info : equipmentTypeInfos) {
                if(info.isEmpty()) continue;
                String[] equipmentTypeAttributes = info.split("_");
                EquipmentType equipmentType = new EquipmentType(Integer.parseInt(equipmentTypeAttributes[0]) , equipmentTypeAttributes[1]);
                equipmentType.setID(Integer.parseInt(equipmentTypeAttributes[0]));
                equipmentTypes.add(equipmentType);
            }
            inputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public static EquipmentType getEquipmentTypeByName(String equipmentTypeName) {
        loadDatabase();
        for (EquipmentType equipmentType : equipmentTypes) {
            if (equipmentType.getName().equals(equipmentTypeName)) {
                return equipmentType;
            }
        }
        return null;
    }

    
    public static void modifyEquipmentType(EquipmentType equipmentType, String newName) {
    loadDatabase();
        for(int i = 0 ; i < equipmentTypes.size(); i ++)
        {
            if(equipmentTypes.get(i).getID() == equipmentType.getID())
            {
                equipmentTypes.get(i).setName(newName);
                break;
            }
        }
        updateDatabase();
    }
}

