package com.freshworks;

import org.json.JSONObject;

public class TestDataStore {

   public static void main(String[] args) throws Exception {
      JSONObject jsonObject = new JSONObject();
      jsonObject.put("1", "Mani");
      jsonObject.put("2", "Nani");
      System.out.println("======================== CREATE with No File Path ==============================");
      DataStore dataStore = new DataStore();
      System.out.println(dataStore.create("RAJINI", jsonObject));
      System.out.println("======================== READ the Key ==============================");
      System.out.println(dataStore.read("RAJINI"));
      System.out.println("======================== Successfully Read the Key ==============================");
      System.out.println("======================== Trying to Read unknown value ==============================");
      System.out.println(dataStore.read("UNKNOWN"));
      System.out.println("========================CREATE with File Path ==============================");
      dataStore = new DataStore("C:\\DEV\\Freshworks", "PROCESS");
      System.out.println(dataStore.create("KAMAL", jsonObject));
      System.out.println("========================READ KEY created with File Path ==============================");
      System.out.println(dataStore.read("KAMAL"));
      System.out.println("======================== Successfully Read the Key  ==============================");
      System.out.println("======================== Delete an existing Key  ==============================");
      System.out.println(dataStore.delete("KAMAL"));
      System.out.println("======================== Deleted an new Key which is not present in datastore  ==============================");
      System.out.println(dataStore.delete("NEW"));
   }
}
