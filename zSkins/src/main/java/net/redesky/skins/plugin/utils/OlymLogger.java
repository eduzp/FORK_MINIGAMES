package net.redesky.skins.plugin.utils;

import java.util.logging.Level;
import java.util.logging.Logger;
import net.redesky.skins.Core;
import net.redesky.skins.plugin.compatibility.ConsoleLogger;

public class OlymLogger extends Logger {
   private String prefix;
   private ConsoleLogger sender;

   public OlymLogger() {
      this("[RedeSky] ");
   }

   public OlymLogger(String prefix) {
      super(prefix, (String)null);
      this.prefix = prefix;
      this.sender = Core.getConsoleLogger();
   }

   public void run(Level level, String method, Runnable runnable) {
      try {
         runnable.run();
      } catch (Exception var5) {
         this.log(level, method.replace("${n}", "RedeSky").replace("${v}", Core.getVersion()), var5);
      }

   }

   public void info(String message) {
      this.log(Level.INFO, message);
   }

   public void warning(String message) {
      this.log(Level.WARNING, message);
   }

   public void severe(String message) {
      this.log(Level.SEVERE, message);
   }

   public void log(Level level, String message) {
      this.hackLog(level, message, (Throwable)null);
   }

   public void log(Level level, String message, Throwable throwable) {
      this.hackLog(level, message, throwable);
   }

   private void hackLog(Level level, String message, Throwable throwable) {
      StringBuilder result = new StringBuilder(this.prefix + message);
      if (throwable != null) {
         result.append("\n" + throwable.getLocalizedMessage());
         StackTraceElement[] var5 = throwable.getStackTrace();
         int var6 = var5.length;

         for(int var7 = 0; var7 < var6; ++var7) {
            StackTraceElement ste = var5[var7];
            if (ste.toString().contains("dev.ony")) {
               result.append("\n" + ste.toString());
            }
         }
      }

      this.sender.sendMessage(OlymLogger.OlymLevel.valueOf(level.getName()).format(result.toString()));
   }

   public OlymLogger getModule(String module) {
      return new OlymLogger(this.prefix + "[" + module + "] ");
   }

   public static enum OlymLevel {
      INFO("§a"),
      WARNING("§e"),
      SEVERE("§c");

      private String color;

      private OlymLevel(String color) {
         this.color = color;
      }

      public String format(String message) {
         return this.color + message;
      }
   }
}
