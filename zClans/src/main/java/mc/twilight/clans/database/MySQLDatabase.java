package mc.twilight.clans.database;

import mc.twilight.clans.Main;
import mc.twilight.core.plugin.logger.KLogger;
import mc.twilight.core.utils.StringUtils;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.logging.Level;
import javax.sql.rowset.CachedRowSet;
import javax.sql.rowset.RowSetProvider;
import org.bukkit.configuration.file.FileConfiguration;

public class MySQLDatabase extends Database {
  private static final KLogger LOGGER = (KLogger)Main.getInstance().getLogger();
  
  private Connection connection;
  
  private final ExecutorService executor;
  
  private final String host;
  
  private final String port;
  
  private final String database;
  
  private final String username;
  
  private final String password;
  
  public MySQLDatabase() {
    FileConfiguration config = Main.getInstance().getConfig();
    this.host = config.getString("database.mysql.host");
    this.port = config.get("database.mysql.porta").toString();
    this.database = config.getString("database.mysql.nome");
    this.username = config.getString("database.mysql.usuario");
    this.password = config.getString("database.mysql.senha");
    this.executor = Executors.newCachedThreadPool();
    openConnection();
    update("CREATE TABLE IF NOT EXISTS `zClans` (  `tag` varchar(5) NOT NULL,  `name` varchar(32) DEFAULT NULL,  `leader` text DEFAULT NULL,  `members` text DEFAULT NULL,  `officers` text DEFAULT NULL,  `invites` text DEFAULT NULL,  `changelog` text DEFAULT NULL,  `boletim` LONG NOT NULL,  `slots` int(11) NOT NULL,  `tagPermission` text DEFAULT NULL,  `tagPermissionPlus` text DEFAULT NULL,  `coins` int(11) NOT NULL,  `upgrades` text DEFAULT NULL,  `created` LONG NOT NULL, PRIMARY KEY(tag)) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE utf8_bin;", new Object[0]);
  }
  
  public List<String[]> getLeaderBoard(String table, String... columns) {
    List<String[]> result = (List)new ArrayList<>();
    StringBuilder add = new StringBuilder(), select = new StringBuilder();
    for (String column : columns) {
      add.append("`").append(column).append("` + ");
      select.append("`").append(column).append("`, ");
    } 
    try (CachedRowSet rs = query("SELECT " + select + "`tag` FROM `" + table + "` ORDER BY " + add + " 0 DESC LIMIT 100", new Object[0])) {
      if (rs != null) {
        rs.beforeFirst();
        while (rs.next()) {
          long count = 0L;
          for (String column : columns)
            count += rs.getLong(column); 
          result.add(new String[] { rs.getString("tag"), StringUtils.formatNumber(count) });
        } 
      } 
    } catch (SQLException sQLException) {}
    return result;
  }
  
  public Connection getConnection() {
    if (!isConnected())
      openConnection(); 
    return this.connection;
  }
  
  public void closeConnection() {
    if (isConnected())
      try {
        this.connection.close();
      } catch (SQLException e) {
        LOGGER.log(Level.SEVERE, "Could not close MySQL connection: ", e);
      }  
  }
  
  public boolean isConnected() {
    try {
      return (this.connection != null && !this.connection.isClosed() && this.connection.isValid(5));
    } catch (SQLException e) {
      LOGGER.log(Level.SEVERE, "MySQL error: ", e);
      return false;
    } 
  }

  public void openConnection() {
    if (!isConnected())
      try {
        boolean bol = (this.connection == null);
        this.connection = DriverManager.getConnection("jdbc:mysql://" + this.host + ":" + this.port + "/" + this.database + "?verifyServerCertificate=false&useSSL=false&useUnicode=yes&characterEncoding=UTF-8", this.username, this.password);
        if (bol) {
          LOGGER.info("Conectado ao MySQL!");
          return;
        }
        LOGGER.info("Reconectado ao MySQL!");
      } catch (SQLException e) {
        LOGGER.log(Level.SEVERE, "Could not open MySQL connection: ", e);
      }
  }

  public void update(String sql, Object... vars) {
    try {
      PreparedStatement ps = prepareStatement(sql, vars);
      ps.execute();
      ps.close();
    } catch (SQLException e) {
      LOGGER.log(Level.WARNING, "Could not execute SQL: ", e);
    } 
  }
  
  public void execute(String sql, Object... vars) {
    this.executor.execute(() -> update(sql, vars));
  }
  
  public PreparedStatement prepareStatement(String query, Object... vars) {
    try {
      PreparedStatement ps = getConnection().prepareStatement(query);
      for (int i = 0; i < vars.length; i++)
        ps.setObject(i + 1, vars[i]); 
      return ps;
    } catch (SQLException e) {
      LOGGER.log(Level.WARNING, "Could not Prepare Statement: ", e);
      return null;
    } 
  }
  
  public CachedRowSet query(String query, Object... vars) {
    CachedRowSet rowSet = null;
    try {
      Future<CachedRowSet> future = this.executor.submit(() -> {
            try {
              PreparedStatement ps = prepareStatement(query, vars);
              ResultSet rs = ps.executeQuery();
              CachedRowSet crs = RowSetProvider.newFactory().createCachedRowSet();
              crs.populate(rs);
              rs.close();
              ps.close();
              if (crs.next())
                return crs; 
            } catch (Exception e) {
              LOGGER.log(Level.WARNING, "Could not Execute Query: ", e);
            } 
            return null;
          });
      if (future.get() != null)
        rowSet = future.get(); 
    } catch (Exception e) {
      LOGGER.log(Level.WARNING, "Could not Call FutureTask: ", e);
    } 
    return rowSet;
  }
}
