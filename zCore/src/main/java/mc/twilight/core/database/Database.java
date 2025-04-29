package mc.twilight.core.database;

import mc.twilight.core.Core;
import mc.twilight.core.Manager;
import mc.twilight.core.booster.NetworkBooster;
import mc.twilight.core.bungee.Bungee;
import mc.twilight.core.database.cache.RoleCache;
import mc.twilight.core.database.data.DataContainer;
import mc.twilight.core.database.exception.ProfileLoadException;

import java.util.List;
import java.util.Map;
import java.util.Timer;
import java.util.concurrent.TimeUnit;
import java.util.logging.Logger;

public abstract class Database {
  
  public static Logger LOGGER;
  private static Database instance;
  
  public static void setupDatabase(String type, String mysqlHost, String mysqlPort, String mysqlDbname, String mysqlUsername, String mysqlPassword, boolean hikari, boolean mariadb,
                                   String mongoURL) {
    LOGGER = Manager.BUNGEE ? Bungee.getInstance().getLogger() : Core.getInstance().getLogger();
    if (type.equalsIgnoreCase("mongodb")) {
      instance = new MongoDBDatabase(mongoURL);
    } else {
      if (hikari) {
        instance = new HikariDatabase(mysqlHost, mysqlPort, mysqlDbname, mysqlUsername, mysqlPassword, mariadb);
      } else {
        instance = new MySQLDatabase(mysqlHost, mysqlPort, mysqlDbname, mysqlUsername, mysqlPassword, mariadb);
      }
    }
    
    // Limpeza do Cache de Rank/Nome da classe de Role.
    new Timer().scheduleAtFixedRate(RoleCache.clearCache(), TimeUnit.SECONDS.toMillis(60), TimeUnit.SECONDS.toMillis(60));
  }
  
  public static Database getInstance() {
    return instance;
  }
  
  public void setupBoosters() {
  }
  
  public void convertDatabase(Object player) {
    if (!Manager.BUNGEE) {
      ((org.bukkit.entity.Player) player).sendMessage("§cRecurso não suportado para seu tipo de Banco de Dados.");
    }
  }
  
  public abstract void setBooster(String minigame, String booster, double multiplier, long expires);
  
  public abstract NetworkBooster getBooster(String minigame);
  
  public abstract String getRankAndName(String player);
  
  public abstract boolean getPreference(String player, String id, boolean def);
  
  public abstract List<String[]> getLeaderBoard(String table, String... columns);
  
  public abstract void close();
  
  public abstract Map<String, Map<String, DataContainer>> load(String name) throws ProfileLoadException;

  public abstract void save(String name, Map<String, Map<String, DataContainer>> tableMap);
  
  public abstract void saveSync(String name, Map<String, Map<String, DataContainer>> tableMap);
  
  public abstract String exists(String name);
}
