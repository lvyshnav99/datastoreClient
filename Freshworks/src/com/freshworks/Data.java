package com.freshworks;

import org.json.JSONObject;

import java.io.Serializable;

public class Data implements Serializable {
   private String key;
   private int timeToLive;
   private String value;
   private long createTime;

   public String getKey() {
      return key;
   }

   public void setKey(String key) {
      this.key = key;
   }

   public int getTimeToLive() {
      return timeToLive;
   }

   public void setTimeToLive(int timeToLive) {
      this.timeToLive = timeToLive;
   }

   public String getValue() {
      return value;
   }

   public void setValue(String value) {
      this.value = value;
   }

   public long getCreateTime() {
      return createTime;
   }

   public void setCreateTime(long createTime) {
      this.createTime = createTime;
   }
}
