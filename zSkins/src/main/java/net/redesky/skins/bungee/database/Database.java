package net.redesky.skins.bungee.database;

import javax.sql.rowset.CachedRowSet;
import net.redesky.skins.Core;
import net.redesky.skins.plugin.utils.OlymLogger;

public abstract class Database {
   private static Database instance;
   public static final OlymLogger LOGGER;

   public abstract void closeConnection();

   public abstract void update(String var1, Object... var2);

   public abstract void execute(String var1, Object... var2);

   public abstract CachedRowSet query(String var1, Object... var2);

   public static void makeBackend() {
      instance = new MySQLDatabase();
   }

   public static Database getInstance() {
      return instance;
   }

   static {
      LOGGER = Core.LOGGER.getModule("DATABASE");
   }
}
