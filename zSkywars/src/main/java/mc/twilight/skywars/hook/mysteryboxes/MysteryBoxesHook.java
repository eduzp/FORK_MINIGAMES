package mc.twilight.skywars.hook.mysteryboxes;

import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.model.Filters;
import mc.twilight.caixas.api.MysteryBoxAPI;
import mc.twilight.core.database.Database;
import mc.twilight.core.database.HikariDatabase;
import mc.twilight.core.database.MongoDBDatabase;
import mc.twilight.core.database.MySQLDatabase;
import mc.twilight.skywars.Main;
import mc.twilight.skywars.cosmetics.Cosmetic;
import org.bson.Document;
import org.bukkit.command.CommandSender;

import javax.sql.rowset.CachedRowSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import static com.mongodb.client.model.Projections.fields;
import static com.mongodb.client.model.Projections.include;

public class MysteryBoxesHook {
  
  private static Boolean unsynced;
  
  public static void sync(CommandSender sender) {
    if (Database.getInstance() instanceof MongoDBDatabase) {
      MongoDBDatabase database = (MongoDBDatabase) Database.getInstance();
      MongoCollection<Document> collection = database.getDatabase().getCollection("kMysteryBoxContent");
      database.getExecutor().execute(() -> {
        collection.deleteMany(Filters.eq("dependency", "zSkyWars"));
        
        List<Document> documents = new ArrayList<>();
        Cosmetic.listCosmetics().forEach(cosmetic -> {
          documents.add(
              new Document("_id", cosmetic.getLootChestsID()).append("name", "&f" + cosmetic.getName() + " &7(Sky Wars - " + cosmetic.getType().getName(cosmetic.getIndex()) + ")")
                  .append("rarity", cosmetic.getRarity().name())
                  .append("action", "sw mb give {player} " + cosmetic.getLootChestsID() + " ; sw mb give {player} " + cosmetic.getLootChestsID()).append("dependency", "zSkyWars"));
        });
        
        collection.insertMany(documents);
        MysteryBoxAPI.reloadContents();
        sender.sendMessage("§aA sincronização terminou!");
      });
    } else {
      if (Database.getInstance() instanceof MySQLDatabase) {
        ((MySQLDatabase) Database.getInstance()).update("DELETE FROM `kMysteryBoxContent` WHERE `dependency` = ?", "zSkyWars");
      } else {
        ((HikariDatabase) Database.getInstance()).update("DELETE FROM `kMysteryBoxContent` WHERE `dependency` = ?", "zSkyWars");
      }
      
      StringBuilder query = new StringBuilder("INSERT INTO `kMysteryBoxContent` VALUES ");
      Cosmetic.listCosmetics().forEach(
          cosmetic -> query.append("('").append(cosmetic.getLootChestsID()).append("', '").append("&f").append(cosmetic.getName()).append(" &7(Sky Wars - ")
              .append(cosmetic.getType().getName(cosmetic.getIndex())).append(")', '").append(cosmetic.getRarity().name()).append("', 'sw mb give {player} ")
              .append(cosmetic.getLootChestsID()).append(" ; sw mb give {player} ").append(cosmetic.getLootChestsID()).append("', 'zSkyWars'),"));
      
      query.deleteCharAt(query.length() - 1);
      if (Database.getInstance() instanceof MySQLDatabase) {
        ((MySQLDatabase) Database.getInstance()).update(query.toString());
      } else {
        ((HikariDatabase) Database.getInstance()).update(query.toString());
      }
      MysteryBoxAPI.reloadContents();
      sender.sendMessage("§aA sincronização terminou!");
    }
    unsynced = false;
  }
  
  public static boolean isUnsynced() {
    if (unsynced == null) {
      if (Main.kMysteryBox) {
        List<String> verified = new ArrayList<>();
        if (Database.getInstance() instanceof MongoDBDatabase) {
          MongoDBDatabase database = (MongoDBDatabase) Database.getInstance();
          try {
            MongoCursor<Document> rs =
                database.getExecutor().submit(() -> database.getDatabase().getCollection("kMysteryBoxContent").find(new Document("dependency", "zSkyWars")).projection(fields(include("_id"))).cursor()).get();
            while (rs.hasNext()) {
              Document document = rs.next();
              if (Cosmetic.findById(document.getString("_id")) == null) {
                unsynced = true;
                return unsynced;
              }
              verified.add(document.getString("_id"));
            }
          } catch (Exception ignore) {
          }
        } else {
          CachedRowSet rs;
          if (Database.getInstance() instanceof MySQLDatabase) {
            rs = ((MySQLDatabase) Database.getInstance()).query("SELECT * FROM `kMysteryBoxContent` WHERE `dependency` = ?", "zSkyWars");
          } else {
            rs = ((HikariDatabase) Database.getInstance()).query("SELECT * FROM `kMysteryBoxContent` WHERE `dependency` = ?", "zSkyWars");
          }
          
          try {
            if (rs != null) {
              rs.beforeFirst();
              while (rs.next()) {
                if (Cosmetic.findById(rs.getString("id")) == null) {
                  unsynced = true;
                  return unsynced;
                }
                
                verified.add(rs.getString("id"));
              }
            }
          } catch (SQLException ignore) {
          } finally {
            if (rs != null) {
              try {
                rs.close();
              } catch (SQLException ignore) {
              }
            }
          }
        }
        unsynced = Cosmetic.listCosmetics().stream().anyMatch(c -> !verified.contains(c.getLootChestsID()));
        verified.clear();
        return unsynced;
      }
      
      unsynced = false;
    }
    
    return unsynced;
  }
  
}
