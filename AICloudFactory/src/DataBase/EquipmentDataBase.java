package DataBase;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;

import Model.Equipment;
import Model.EquipmentType;
import Model.Factory;

public class EquipmentDataBase {
    private static ArrayList<Equipment> equipments = new ArrayList<Equipment>();
    private static final String FILE_PATH = "C:\\Users\\DELL\\Desktop\\vscodejava\\Work\\AICloudFactory\\Data\\equipments.dat";

    public static void addEquipment(Equipment equipment) {
        equipments.add(equipment);
        updateDatabase();
    }

    public static void removeEquipment(Equipment equipment) {
        equipments.remove(equipment);
        updateDatabase();
    }

    public static ArrayList<Equipment> getEquipments() {
        loadDatabase();
        return equipments;
    }

    private static String equipmentToString(Equipment equipment) {
        String equipmentString = equipment.getName() + "_" + equipment.getID() + "_" + equipment.getCode() + "_"
                + equipment.getType().getName() + "_" + equipment.getSpecification() + "_" + equipment.isBorrowable() + "_"
                + equipment.isSwitchable() + "_" + equipment.getDescription() + "_" + ((equipment.getFactory() != null)?equipment.getFactory().getName():"null");
        return equipmentString;
    }

    private static Equipment stringToEquipment(String equipmentString) {
        String[] equipmentInfo = equipmentString.split("_");
        String name = equipmentInfo[0];
        int id = Integer.parseInt(equipmentInfo[1]);
        String code = equipmentInfo[2];
        String type = equipmentInfo[3];
        String specification = equipmentInfo[4];
        String isBorrowable = equipmentInfo[5];
        boolean isSwitchable = (equipmentInfo[6].equals("true"))?true:false;
        String descrioption = equipmentInfo[7];
        String factoryName = equipmentInfo[8];
        Equipment equipment = new Equipment(id ,name, code , type , specification ,  isBorrowable , isSwitchable , descrioption,factoryName);
        return equipment;
    }

    private static void updateDatabase() {
        try {
            FileOutputStream outputStream = new FileOutputStream(FILE_PATH);
            for (Equipment equipment : equipments) {
                String equipmentString = equipmentToString(equipment) + "\n";
                outputStream.write(equipmentString.getBytes());
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
            int length = 0;
            String equipmentString = "";
            equipments.clear();
            while ((length = inputStream.read(buffer)) != -1) {
                equipmentString += new String(buffer, 0, length);
            }
            String[] equipmentStrings = equipmentString.split("\n");
            for (String equipmentStringTemp : equipmentStrings) {
                if(!equipmentStringTemp.isEmpty()){
                    Equipment equipment = stringToEquipment(equipmentStringTemp);
                    equipments.add(equipment);
                }
            }

            inputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static ArrayList<Equipment> getEquipmentByFactoryName(String factoryName) {
        loadDatabase();
        ArrayList<Equipment> equipmentsOfFactory = new ArrayList<Equipment>();
        for (Equipment equipment : equipments) {
            if (equipment.getFactory() != null && equipment.getFactory().getName().equals(factoryName)) {
                equipmentsOfFactory.add(equipment);
            }
        }
        return equipmentsOfFactory;
    }


    public static Equipment getEquipmentByName(String equipmentName) {
        loadDatabase();
        for (Equipment equipment : equipments) {
            if (equipment.getName().equals(equipmentName)) {
                return equipment;
            }
        }
        return null;
    }

    public static Equipment getEquipmentByID(int equipmentID) {
        loadDatabase();
        for (Equipment equipment : equipments) {
            if (equipment.getID() == equipmentID) {
                return equipment;
            }
        }
        return null;
    }

    public static void modifyEquipment(Equipment equipment) {
        int idx = equipments.indexOf(getEquipmentByID(equipment.getID()));
        equipments.get(idx).setName(equipment.getName()); 
        equipments.get(idx).setType(equipment.getType().getName()); 
        equipments.get(idx).setSpecification(equipment.getSpecification()); 
        equipments.get(idx).setBorrowable(equipment.isBorrowable()); 
        equipments.get(idx).setSwitchable(equipment.isSwitchable()); 
        equipments.get(idx).setDescription(equipment.getDescription()); 
        equipments.get(idx).setFactory(equipment.getFactory()); 
        updateDatabase(); 
    }

    public static ArrayList<Equipment> getEquipmentByType(String type) {
        loadDatabase();
        ArrayList<Equipment> equipmentsOfType = new ArrayList<Equipment>();
        for (Equipment equipment : equipments) {
            if (equipment.getType().getName().equals(type)) {
                equipmentsOfType.add(equipment);
            }
        }
        return equipmentsOfType;
    }

    public static void modifyEquipmentstatus(Equipment equipment) {
        loadDatabase();
        int index = equipments.indexOf(equipment);
        if (index != -1) {
            equipment.setSwitchable(!equipment.isSwitchable());
            equipments.set(index, equipment);
            updateDatabase();
        }
    }

    public static void removeEquipmentbyfactoryid(int id) {
        loadDatabase();
        for(int i = 0 ; i < equipments.size() ;  i++)
        {
            if(equipments.get(i).getFactory() != null && equipments.get(i).getFactory().getId() == id)
            {
                if(equipments.get(i).isBorrowable().equals("工厂设备"))
                {
                    equipments.remove(i);
                }
                else if(equipments.get(i).isBorrowable().equals("是"))
                {
                    equipments.get(i).setBorrowable("否");
                    equipments.get(i).setFactory(null);
                }
                
            }
        }
        updateDatabase();
    }

    public static void removeEquipmentbyequipmenttype(EquipmentType equipmentType) {
        loadDatabase();
        for(int i = 0 ; i < equipments.size() ;  i++)
        {
            if(equipments.get(i).getType().getID() == equipmentType.getID())
            {
                equipments.remove(i);
            }
        }
        updateDatabase();
    }

    public static void modifyEquipmentbyequipmenttype(EquipmentType newequipmentType) {
        loadDatabase();
        for(int i = 0 ; i < equipments.size() ;  i++)
        {
            if(equipments.get(i).getType().getID() == newequipmentType.getID())
            {
                equipments.get(i).setType(newequipmentType);
            }
        }
        updateDatabase();
    }

    public static ArrayList<Equipment> getEquipmentByEquipmentType(EquipmentType equipmentType) {
        loadDatabase();
        ArrayList<Equipment> equipmentsOfType = new ArrayList<Equipment>();
        for (Equipment equipment : equipments) {
            if (equipment.getType().getID() == equipmentType.getID()) {
                equipmentsOfType.add(equipment);
            }
        }
        return equipmentsOfType;
    }

    public static void modifyEquipmentbyfactory(Factory factory) {
        loadDatabase();
        for(int i = 0 ; i < equipments.size() ;  i++)
        {
            if(equipments.get(i).getFactory() != null && equipments.get(i).getFactory().getId() == factory.getId())
            {
                equipments.get(i).setFactory(factory);
            }
        }
        updateDatabase();
    }






}
