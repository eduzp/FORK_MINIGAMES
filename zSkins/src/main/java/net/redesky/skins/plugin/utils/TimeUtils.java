package net.redesky.skins.plugin.utils;

import java.util.Calendar;

public class TimeUtils {
   public static long getExpireMonth() {
      Calendar cooldown = Calendar.getInstance();
      cooldown.set(5, cooldown.getActualMaximum(5));
      cooldown.set(10, 24);
      cooldown.set(12, 0);
      cooldown.set(13, 0);
      return cooldown.getTimeInMillis();
   }

   public static long getExpireIn(int days) {
      Calendar cooldown = Calendar.getInstance();
      cooldown.set(10, 24);

      for(int day = 0; day < days - 1; ++day) {
         cooldown.add(10, 24);
      }

      cooldown.set(12, 0);
      cooldown.set(13, 0);
      return cooldown.getTimeInMillis();
   }

   public static String getTimeUntil(long epoch) {
      epoch -= System.currentTimeMillis();
      return getTime(epoch);
   }

   public static String getTime(long ms) {
      ms = (long)Math.ceil((double)ms / 1000.0D);
      StringBuilder sb = new StringBuilder(40);
      long minutes;
      if (ms / 86400L > 0L) {
         minutes = ms / 86400L;
         sb.append(minutes + (minutes == 1L ? "d " : "d "));
         ms -= minutes * 86400L;
      }

      if (ms / 3600L > 0L) {
         minutes = ms / 3600L;
         sb.append(minutes + (minutes == 1L ? "h " : "h "));
         ms -= minutes * 3600L;
      }

      if (ms / 60L > 0L) {
         minutes = ms / 60L;
         sb.append(minutes + (minutes == 1L ? "m " : "m "));
         ms -= minutes * 60L;
      }

      if (ms > 0L) {
         sb.append(ms + (ms == 1L ? "s " : "s "));
      }

      if (sb.length() > 1) {
         sb.replace(sb.length() - 1, sb.length(), "");
      } else {
         sb = new StringBuilder("Expired");
      }

      return sb.toString();
   }
}
