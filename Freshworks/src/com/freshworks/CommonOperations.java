package com.freshworks;

import org.json.JSONObject;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.Date;
import java.util.HashMap;

public class CommonOperations {
   //checking whether key is <=32 characters
   public static boolean validKey(String key) {
      if (key.length() > 32) {
         return false;
      }
      return true;
   }

   //checking JSON object size limit
   public static boolean validJSON(JSONObject value) {
      return false;
   }

   //checking whether key is present in datastore
   public static boolean alreadyexist(String key, String filePath) {
      boolean keyexist = false;
      FileInputStream fin = null;
      ObjectInputStream obin = null;
      FileOutputStream fout = null;
      ObjectOutputStream obout = null;
      HashMap<String, Data> hmap = new HashMap<String, Data>();
      try {
         File file = new File(filePath);
         if (file.exists()) {
            fin = new FileInputStream(file);
            obin = new ObjectInputStream(fin);
            hmap = (HashMap<String, Data>) obin.readObject();
            if (hmap.containsKey(key)) {
               keyexist = true;
            }

            fin.close();
            obin.close();
         }
         //if key exist then check for timetolive
         if (keyexist) {
            Data data = hmap.get(key);
            long currentTime = new Date().getTime();
            if (data.getTimeToLive() > 0 && ((currentTime - data.getCreateTime()) / 1000) >= data.getTimeToLive()) {
               //time expired so remove from datastore
               hmap.remove(key);
               fout = new FileOutputStream(filePath);
               obout = new ObjectOutputStream(fout);
               obout.writeObject(hmap);
               fout.close();
               obout.close();
               keyexist = false;
            }
         }
      } catch (Exception e) {
         System.out.println("Operation Failed.Exception in alreadyexist method call");
      } finally {
         if (fin != null) {
            try {
               fin.close();
            } catch (Exception e) {
               System.out.println("Error in file close in alreadyexist");
            }
         }
         if (obin != null) {
            try {
               obin.close();
            } catch (Exception e) {
               System.out.println("Error in file close in alreadyexist");
            }
         }
      }
      return keyexist;
   }

   // write data in datastore
   public static boolean writeData(Data data, String filePath) {
      FileInputStream fin = null;
      ObjectInputStream obin = null;
      FileOutputStream fout = null;
      ObjectOutputStream obout = null;
      HashMap<String, Data> hmap = null;
      try {
         File file = new File(filePath);
         if (file.exists()) {
            //read the existing file data
            fin = new FileInputStream(file);
            obin = new ObjectInputStream(fin);
            hmap = (HashMap<String, Data>) obin.readObject();
            fin.close();
            obin.close();
            // add new element
            hmap.put(data.getKey(), data);
            //write into the existing file data
            fout = new FileOutputStream(file);
            obout = new ObjectOutputStream(fout);
            obout.writeObject(hmap);
            fout.close();
            obout.close();
            return true;
         } else {
            hmap = new HashMap<String, Data>();
            hmap.put(data.getKey(), data);
            //write data to file
            fout = new FileOutputStream(file);
            obout = new ObjectOutputStream(fout);
            obout.writeObject(hmap);
            fout.close();
            obout.close();
            return true;
         }
      } catch (Exception e) {
         System.out.println(e.getMessage());
         return false;
      } finally {
         if (fin != null) {
            try {
               fin.close();
            } catch (Exception e) {
               e.printStackTrace();
            }
         }
         if (fout != null) {
            try {
               fout.close();
            } catch (Exception e) {
               e.printStackTrace();
            }
         }
         if (obin != null) {
            try {
               obin.close();
            } catch (Exception e) {
               e.printStackTrace();
            }
         }
         if (obout != null) {
            try {
               obout.close();
            } catch (Exception e) {
               e.printStackTrace();
            }
         }
      }
   }

   // read data from datastore
   public static Data readData(String key, String filePath) {
      FileInputStream fin = null;
      ObjectInputStream obin = null;
      HashMap<String, Data> hmap = null;
      try {
         File file = new File(filePath);
         if (file.exists()) {
            fin = new FileInputStream(filePath);
            obin = new ObjectInputStream(fin);
            hmap = (HashMap<String, Data>) obin.readObject();
            fin.close();
            obin.close();
            return hmap.get(key);
         } else {
            return null;
         }
      } catch (Exception e) {
         e.printStackTrace();
         return null;

      } finally {
         if (fin != null) {
            try {
               fin.close();
            } catch (Exception e) {
               e.printStackTrace();
            }
         }
         if (obin != null) {
            try {
               obin.close();
            } catch (Exception e) {
               e.printStackTrace();
            }
         }
      }
   }

   //delete data from datastore
   public static boolean deleteData(String key, String filePath) {
      FileInputStream fin = null;
      ObjectInputStream obin = null;
      FileOutputStream fout = null;
      ObjectOutputStream obout = null;
      HashMap<String, Data> hmap = null;
      try {
         File file = new File(filePath);
         if (file.exists()) {
            //read the existing file data
            fin = new FileInputStream(file);
            obin = new ObjectInputStream(fin);
            hmap = (HashMap<String, Data>) obin.readObject();
            fin.close();
            obin.close();
            hmap.remove(key);
            fout = new FileOutputStream(file);
            obout = new ObjectOutputStream(fout);
            obout.writeObject(hmap);
            fout.close();
            obout.close();
            return true;
         } else {
            return false;
         }
      } catch (Exception e) {
         e.printStackTrace();
         return false;
      } finally {
         if (fin != null) {
            try {
               fin.close();
            } catch (Exception e) {
               e.printStackTrace();
            }
         }
         if (fout != null) {
            try {
               fout.close();
            } catch (Exception e) {
               e.printStackTrace();
            }
         }
         if (obin != null) {
            try {
               obin.close();
            } catch (Exception e) {
               e.printStackTrace();
            }
         }
         if (obout != null) {
            try {
               obout.close();
            } catch (Exception e) {
               e.printStackTrace();
            }
         }
      }
   }

}
