package com.freshworks;

import org.json.JSONObject;

import java.util.Date;
import java.util.Random;

import static com.freshworks.CommonOperations.alreadyexist;
import static com.freshworks.CommonOperations.deleteData;
import static com.freshworks.CommonOperations.readData;
import static com.freshworks.CommonOperations.validKey;
import static com.freshworks.CommonOperations.writeData;

public class DataStore {
   private String fileLocation;
   private String fileName;
   private String fileExtension;
   private Random random;

   // constructor to initialize datastore with default location
   public DataStore() {
      random = new Random();
      fileLocation = "C:/DEV/Freshworks/";
      fileName = "Process-" + random.nextInt();
      fileExtension = ".txt";
   }

   //constructor to initialize datastore with given location
   public DataStore(String fileLocation, String fileName) {
      this.fileLocation = fileLocation;
      random = new Random();
      this.fileName = fileName;
      fileExtension = ".txt";
   }
   //we define operations of datastore i.e, create, read and delete

   //create operation when TimeToLive is not defined
   public synchronized String create(String key, JSONObject value) {
      try {
         return create(key, value, -1);
      } catch (Exception e) {
         return "Failed to create a new key-value pair";
      }
   }

   //create operation when TimeToLive is defined
   public synchronized String create(String key, JSONObject value, int timeToLive) {
      try {
         String filePath = fileLocation + fileName + fileExtension;
         if (!validKey(key)) {
            return "Operation Failed.Given Key size exceeds 32 characters";
         }
         if (alreadyexist(key, filePath)) {
            return "Operation Failed.Given Key already exist in the datastore";
         }
			/*if(!validJSON(value))
			{
				return "Operation Faile.The size Limit of JSON Object is 16KB";
			}*/

         // successful validity of key and value. Data can be added to datastore
         // file path


         Data data = new Data();
         data.setKey(key);
         //data.setValue(value);
         data.setValue(value.toString());
         data.setTimeToLive(timeToLive);
         data.setCreateTime(new Date().getTime());

         if (writeData(data, filePath)) {
            return "Operation Successful.Data has been written to datastore";
         } else {
            return "Operation Failed. Data cannot be written to datastore";
         }
      } catch (Exception e) {
         return "Creation/Updation of data in datastore failed";
      }
   }

   //method to read an element from datastore
   public synchronized JSONObject read(String key) {
      try {
         String filePath = fileLocation + fileName + fileExtension;
         //key validation
         if (!validKey(key)) {
            return new JSONObject("Operation Failed.Given Key size exceeds 32 characters");
         }
         if (!alreadyexist(key, filePath)) {
            return new JSONObject("Operation Failed.Given Key does not exist in the datastore");
         }
         //success flow

         Data data = readData(key, filePath);
         if (data != null) {
            return new JSONObject(data.getValue());
         } else {
            return new JSONObject("Read Operation Failed due to Unknown error.Try Again");
         }

      } catch (Exception e) {
         System.out.println("Read Operation Failed.Try Again");
      }
      return new JSONObject();
   }

   //method to delete an element from datastore
   public synchronized String delete(String key) {
      try {
         String filePath = fileLocation + fileName + fileExtension;
         //key validation
         if (!validKey(key)) {
            return "Operation Failed.Given Key size exceeds 32 characters";
         }
         if (!alreadyexist(key, filePath)) {
            return "Operation Failed.Given Key does not exist in the datastore";
         }
         //sucess flow

         if (deleteData(key, filePath)) {
            return "Operation Successful. Data Deleted successfully";
         } else {
            return "Operation Failed due to some unknown error";
         }
      } catch (Exception e) {
         return "Deleted Operation Failed.Try Again";
      }
   }
}
