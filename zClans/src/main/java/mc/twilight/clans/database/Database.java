package mc.twilight.clans.database;

import mc.twilight.clans.clan.Clan;
import java.sql.Connection;
import java.util.List;
import javax.sql.rowset.CachedRowSet;

public abstract class Database {
  private static Database instance;
  
  public static void setupDatabase() {
    instance = new MySQLDatabase();
    Clan.setupClans();
  }
  
  public static Database getInstance() {
    return instance;
  }
  
  public abstract void closeConnection();
  
  public abstract void update(String paramString, Object... paramVarArgs);
  
  public abstract void execute(String paramString, Object... paramVarArgs);
  
  public abstract CachedRowSet query(String paramString, Object... paramVarArgs);
  
  public abstract Connection getConnection();
  
  public abstract List<String[]> getLeaderBoard(String paramString, String... paramVarArgs);
}
