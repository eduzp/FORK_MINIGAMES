package net.redesky.skins.plugin;

import java.lang.reflect.Method;
import java.util.logging.Logger;

public class Commons {
   private static boolean isBungee;
   private static Method loggerMethod;

   public static boolean isBungee() {
      return isBungee;
   }

   public static Logger getLogger() {
      try {
         return (Logger)((Logger)(isBungee ? loggerMethod.invoke(Class.forName("net.md_5.bungee.api.ProxyServer").getDeclaredMethod("getInstance").invoke((Object)null)) : loggerMethod.invoke((Object)null)));
      } catch (Exception var1) {
         throw new RuntimeException("Falha ao contatar o logger:", var1);
      }
   }

   static {
      try {
         Class<?> bukkit = Class.forName("org.bukkit.Bukkit");
         loggerMethod = bukkit.getDeclaredMethod("getLogger");
         isBungee = false;
      } catch (ClassNotFoundException var5) {
         try {
            Class<?> proxyServer = Class.forName("net.md_5.bungee.api.ProxyServer");
            loggerMethod = proxyServer.getMethod("getLogger");
            isBungee = true;
         } catch (ClassNotFoundException var3) {
            var3.printStackTrace();
         } catch (NoSuchMethodException var4) {
            var4.printStackTrace();
         }
      } catch (NoSuchMethodException var6) {
         var6.printStackTrace();
      }

   }
}
