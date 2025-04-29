package net.redesky.skins.bukkit.database;

import java.io.File;
import java.io.IOException;
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
import net.redesky.skins.bukkit.Main;

public class SQLiteDatabase extends Database {
   private File file = new File(Main.getInstance().getConfig().getString("database.sqlite.arquivo"));
   private Connection connection;
   private ExecutorService executor;

   public SQLiteDatabase() {
      if (!this.file.exists()) {
         this.file.getParentFile().mkdirs();

         try {
            this.file.createNewFile();
         } catch (IOException var2) {
            LOGGER.log(Level.WARNING, "Unexpected error while creating file SQLite: ", var2);
         }
      }

      this.executor = Executors.newCachedThreadPool();
      this.openConnection();
      this.update("CREATE TABLE IF NOT EXISTS `playerskin` (id VARCHAR(36) NOT NULL,slot INTEGER NOT NULL, PRIMARY KEY(id));");
      this.update("CREATE TABLE IF NOT EXISTS `playerskins` (id INTEGER NOT NULL,owner VARCHAR(36) NOT NULL,player VARCHAR(36) NOT NULL,name VARCHAR(36) NOT NULL,value TEXT NOT NULL,signature TEXT NOT NULL,lastused LONG NOT NULL);");
   }

   public void closeConnection() {
      if (this.isConnected()) {
         try {
            this.connection.close();
         } catch (SQLException var2) {
            LOGGER.log(Level.WARNING, "Could not close SQLite connection: ", var2);
         }
      }

   }

   public boolean isConnected() {
      try {
         return this.connection != null && !this.connection.isClosed();
      } catch (SQLException var2) {
         LOGGER.log(Level.WARNING, "SQLite error: ", var2);
         return false;
      }
   }

   public Connection getConnection() {
      if (!this.isConnected()) {
         this.openConnection();
      }

      return this.connection;
   }

   public void openConnection() {
      if (!this.isConnected()) {
         try {
            Class.forName("org.sqlite.JDBC");
            boolean bol = this.connection == null;
            this.connection = DriverManager.getConnection("jdbc:sqlite:" + this.file);
            if (bol) {
               LOGGER.info("Connected to SQLite!");
               return;
            }

            LOGGER.info("Reconnected on SQLite!");
         } catch (SQLException var2) {
            LOGGER.log(Level.WARNING, "Could not open SQLite connection: ", var2);
         } catch (ClassNotFoundException var3) {
            LOGGER.log(Level.SEVERE, "Could not find Driver of SQLite!");
         }
      }

   }

   public void update(String query, Object... vars) {
      try {
         PreparedStatement ps = this.prepareStatement(query, vars);
         ps.executeUpdate();
         ps.close();
      } catch (SQLException var4) {
         LOGGER.log(Level.WARNING, "Could not execute SQL: ", var4);
      }

   }

   public void execute(String query, Object... vars) {
      this.executor.execute(() -> {
         this.update(query, vars);
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
                  PreparedStatement ps = SQLiteDatabase.this.prepareStatement(query, vars);
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
