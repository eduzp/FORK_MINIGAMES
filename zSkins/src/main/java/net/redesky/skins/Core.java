package net.redesky.skins;

import java.io.InputStream;
import java.util.logging.Level;
import net.md_5.bungee.api.chat.BaseComponent;
import net.redesky.skins.bukkit.compatibility.BukkitConsoleLogger;
import net.redesky.skins.bukkit.compatibility.BukkitCore;
import net.redesky.skins.bungee.Main;
import net.redesky.skins.bungee.compatibility.BungeeConsoleLogger;
import net.redesky.skins.bungee.compatibility.BungeeCore;
import net.redesky.skins.plugin.compatibility.ConsoleLogger;
import net.redesky.skins.plugin.utils.OlymLogger;

public abstract class Core {
   public static final boolean BUNGEE = hasBungeeClass();
   public static final OlymLogger LOGGER = new OlymLogger();
   private static final Core METHODS;

   public abstract void dL(OlymLogger var1, Level var2, String var3);

   public abstract void sM(String var1, String var2);

   public abstract void sM(String var1, BaseComponent... var2);

   public abstract String gS();

   public static void delayedLog(String message) {
      METHODS.dL(LOGGER, Level.INFO, message);
   }

   public static void delayedLog(OlymLogger logger, Level level, String message) {
      METHODS.dL(logger, level, message);
   }

   public static String getVersion() {
      return METHODS.gS();
   }

   static boolean hasBungeeClass() {
      try {
         Class.forName("net.md_5.bungee.api.ProxyServer");
         return true;
      } catch (ClassNotFoundException var1) {
         return false;
      }
   }

   public static InputStream getResource(String name) {
      return BUNGEE ? Main.getInstance().getResourceAsStream(name) : net.redesky.skins.bukkit.Main.getInstance().getResource(name);
   }

   public static ConsoleLogger getConsoleLogger() {
      return (ConsoleLogger)(BUNGEE ? new BungeeConsoleLogger() : new BukkitConsoleLogger());
   }

   static {
      METHODS = (Core)(BUNGEE ? new BungeeCore() : new BukkitCore());
   }
}
