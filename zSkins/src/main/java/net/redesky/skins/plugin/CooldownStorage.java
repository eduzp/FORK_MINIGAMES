package net.redesky.skins.plugin;

import java.util.Iterator;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.TimeUnit;

public class CooldownStorage implements Runnable {
   private static final Map<String, Long> CACHE = new ConcurrentHashMap();

   public static void reset(String name) {
      CACHE.remove(name);
   }

   public static void setCooldownMin(String name) {
      CACHE.put(name, System.currentTimeMillis() + TimeUnit.SECONDS.toMillis(30L));
   }

   public static void setCooldown(String name) {
      CACHE.put(name, System.currentTimeMillis() + TimeUnit.MINUTES.toMillis(3L));
   }

   public static boolean hasCooldown(String name) {
      Long expire = (Long)CACHE.get(name);
      if (expire != null) {
         return expire > System.currentTimeMillis();
      } else {
         return false;
      }
   }

   public static String getCooldown(String name) {
      if (hasCooldown(name)) {
         Long value = (Long)CACHE.get(name);
         int seconds = (int)TimeUnit.MILLISECONDS.toSeconds(value - System.currentTimeMillis());
         if (seconds <= 0) {
            return "";
         } else {
            String rSec = (seconds % 60 < 10 ? "0" : "") + seconds % 60 + "s";
            String rMin = seconds / 60 < 1 ? "" : seconds / 60 + "m ";
            return rMin + rSec;
         }
      } else {
         return "";
      }
   }

   public void run() {
      long current = System.currentTimeMillis();
      Iterator iterator = CACHE.values().iterator();

      while(iterator.hasNext()) {
         if ((Long)iterator.next() <= current) {
            iterator.remove();
         }
      }

   }
}
