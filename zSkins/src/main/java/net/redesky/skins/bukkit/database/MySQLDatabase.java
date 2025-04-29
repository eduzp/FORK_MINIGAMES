package net.redesky.skins.bukkit.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.logging.Level;
import javax.sql.rowset.CachedRowSet;
import javax.sql.rowset.RowSetProvider;
import org.bukkit.configuration.file.FileConfiguration;
import net.redesky.skins.bukkit.Main;

public class MySQLDatabase extends Database {
   private Connection connection;
   private ExecutorService executor;
   private String host;
   private String port;
   private String database;
   private String username;
   private String password;

   public MySQLDatabase() {
      FileConfiguration config = Main.getInstance().getConfig();
      this.host = config.getString("database.mysql.host");
      this.port = config.get("database.mysql.porta").toString();
      this.database = config.getString("database.mysql.nome");
      this.username = config.getString("database.mysql.usuario");
      this.password = config.getString("database.mysql.senha");
      this.executor = Executors.newCachedThreadPool();
      this.openConnection();
      this.update("CREATE TABLE IF NOT EXISTS `playerskin` (id VARCHAR(36) NOT NULL,slot INTEGER NOT NULL, PRIMARY KEY(id)) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE utf8_bin;");
      this.update("CREATE TABLE IF NOT EXISTS `playerskins` (id INTEGER NOT NULL,owner VARCHAR(36) NOT NULL,player VARCHAR(36) NOT NULL,name VARCHAR(36) NOT NULL,value TEXT NOT NULL,signature TEXT NOT NULL,lastused LONG NOT NULL) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE utf8_bin;");
   }

   public Connection getConnection() {
      if (!this.isConnected()) {
         this.openConnection();
      }

      return this.connection;
   }

   public void closeConnection() {
      if (this.isConnected()) {
         try {
            this.connection.close();
         } catch (SQLException var2) {
            LOGGER.log(Level.SEVERE, "Could not close MySQL connection: ", var2);
         }
      }

   }

   public boolean isConnected() {
      try {
         return this.connection != null && !this.connection.isClosed() && this.connection.isValid(5);
      } catch (SQLException var2) {
         LOGGER.log(Level.SEVERE, "MySQL error: ", var2);
         return false;
      }
   }

   public void openConnection() {
      if (!this.isConnected()) {
         try {
            boolean bol = this.connection == null;
            this.connection = DriverManager.getConnection("jdbc:mysql://" + this.host + ":" + this.port + "/" + this.database + "?verifyServerCertificate=false&useSSL=false&useUnicode=yes&characterEncoding=UTF-8", this.username, this.password);
            if (bol) {
               LOGGER.info("Connected to MySQL!");
               return;
            }

            LOGGER.info("Reconnected on MySQL!");
         } catch (SQLException var2) {
            LOGGER.log(Level.SEVERE, "Could not open MySQL connection: ", var2);
         }
      }

   }

   public void update(String sql, Object... vars) {
      try {
         PreparedStatement ps = this.prepareStatement(sql, vars);
         ps.execute();
         ps.close();
      } catch (SQLException var4) {
         LOGGER.log(Level.WARNING, "Could not execute SQL: ", var4);
      }

   }

   public void execute(String sql, Object... vars) {
      this.executor.execute(() -> {
         this.update(sql, vars);
      });
   }

   public PreparedStatement prepareStatement(String query, Object... vars) {
      try {
         PreparedStatement ps = this.getConnection().prepareStatement(query);

         for(int i = 0; i < vars.length; ++i) {
            ps.setObject(i + 1, vars[i]);
         }

         return ps;
      } catch (SQLException var5) {
         LOGGER.log(Level.WARNING, "Could not Prepare Statement: ", var5);
         return null;
      }
   }

   public CachedRowSet query(final String query, final Object... vars) {
      CachedRowSet rowSet = null;

      try {
         Future<CachedRowSet> future = this.executor.submit(new Callable<CachedRowSet>() {
            public CachedRowSet call() {
               try {
                  PreparedStatement ps = MySQLDatabase.this.prepareStatement(query, vars);
                  ResultSet rs = ps.executeQuery();
                  CachedRowSet crs = RowSetProvider.newFactory().createCachedRowSet();
                  crs.populate(rs);
                  rs.close();
                  ps.close();
                  if (crs.next()) {
                     return crs;
                  }
               } catch (Exception var4) {
                  Database.LOGGER.log(Level.WARNING, "Could not Execute Query: ", var4);
               }

               return null;
            }
         });
         if (future.get() != null) {
            rowSet = (CachedRowSet)future.get();
         }
      } catch (Exception var5) {
         LOGGER.log(Level.WARNING, "Could not Call FutureTask: ", var5);
      }

      return rowSet;
   }
}
