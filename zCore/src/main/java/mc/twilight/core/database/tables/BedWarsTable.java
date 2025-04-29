package mc.twilight.core.database.tables;

import mc.twilight.core.database.Database;
import mc.twilight.core.database.HikariDatabase;
import mc.twilight.core.database.MySQLDatabase;
import mc.twilight.core.database.data.DataContainer;
import mc.twilight.core.database.data.DataTable;
import mc.twilight.core.database.data.interfaces.DataTableInfo;

import java.util.Calendar;
import java.util.LinkedHashMap;
import java.util.Map;

@DataTableInfo(name = "zCoreBedWars",
    create = "CREATE TABLE IF NOT EXISTS `zCoreBedWars` (`name` VARCHAR(32), `" +
        "1v1kills` LONG, `1v1deaths` LONG, `1v1games` LONG, `1v1bedsdestroyeds` LONG, `1v1bedslosteds` LONG, `1v1finalkills` LONG, `1v1finaldeaths` LONG, `1v1wins` LONG, `2v2kills` LONG, `2v2deaths` LONG, `2v2games` LONG, `2v2bedsdestroyeds` LONG, `2v2bedslosteds` LONG, `2v2finalkills` LONG, `2v2finaldeaths` LONG, `2v2wins` LONG, `4v4kills` LONG," +
        " `4v4deaths` LONG, `4v4games` LONG, `4v4bedsdestroyeds` LONG, `4v4bedslosteds` LONG, `4v4finalkills` LONG, `4v4finaldeaths` LONG, `4v4wins` LONG, `monthlykills` LONG, `monthlydeaths` LONG, `monthlyassists` LONG, `monthlybeds` LONG, `monthlywins` LONG, `monthlygames` LONG, `month` TEXT, `coins` DOUBLE," +
        " `lastmap` LONG, `cosmetics` TEXT, `selected` TEXT, `favorites` TEXT, PRIMARY KEY(`name`)) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE utf8_bin;",
    select = "SELECT * FROM `zCoreBedWars` WHERE LOWER(`name`) = ?",
    insert = "INSERT INTO `zCoreBedWars` VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)",
    update = "UPDATE `zCoreBedWars` SET `1v1kills` = ?, `1v1deaths` = ?, `1v1games` = ?, `1v1bedsdestroyeds` = ?, `1v1bedslosteds` = ?, `1v1finalkills` = ?, `1v1finaldeaths` = ?, `1v1wins` = ?`, `2v2kills` = ?, `2v2deaths` = ?, `2v2games` = ?, `2v2bedsdestroyeds` = ?, `2v2bedslosteds` = ?, `2v2finalkills` = ?, `2v2finaldeaths` = ?, `2v2wins` = ?`, `4v4kills` = ?, `4v4deaths` = ?, `4v4games` = ?, `4v4bedsdestroyeds` = ?, `4v4bedslosteds` = ?, `4v4finalkills` = ?, `4v4finaldeaths` = ?, `monthlykills` = ?, `montlhydeaths` = ?, `monthlyassists` = ?, `monthlybeds` = ?, `monthlywins` = ?, `monthlygames` = ?, `month` = ?, `4v4wins` = ?, `coins` = ?, `lastmap` = ?, `cosmetics` = ?, `selected` = ?, `kitconfig` = ? WHERE LOWER(`name`) = ?")
public class BedWarsTable extends DataTable {
  
  @Override
  public void init(Database database) {
    if (database instanceof MySQLDatabase) {
      if (((MySQLDatabase) database).query("SHOW COLUMNS FROM `zCoreBedWars` LIKE 'lastmap'") == null) {
        ((MySQLDatabase) database).execute(
            "ALTER TABLE `zCoreBedWars` ADD `lastmap` LONG DEFAULT 0 AFTER `coins`, ADD `favorites` TEXT AFTER `selected`");
      }
    } else if (database instanceof HikariDatabase) {
      if (((HikariDatabase) database).query("SHOW COLUMNS FROM `zCoreBedWars` LIKE 'lastmap'") == null) {
        ((HikariDatabase) database).execute(
            "ALTER TABLE `zCoreBedWars` ADD `lastmap` LONG DEFAULT 0 AFTER `coins`, ADD `favorites` TEXT AFTER `selected`");
      }
    }
  }
  
  public Map<String, DataContainer> getDefaultValues() {
    Map<String, DataContainer> defaultValues = new LinkedHashMap<>();
    for (String key : new String[]{"1v1", "2v2", "4v4"}) {
      defaultValues.put(key + "kills", new DataContainer(0L));
      defaultValues.put(key + "deaths", new DataContainer(0L));
      defaultValues.put(key + "games", new DataContainer(0L));
      defaultValues.put(key + "bedsdestroyeds", new DataContainer(0L));
      defaultValues.put(key + "bedslosteds", new DataContainer(0L));
      defaultValues.put(key + "finalkills", new DataContainer(0L));
      defaultValues.put(key + "finaldeaths", new DataContainer(0L));
      defaultValues.put(key + "wins", new DataContainer(0L));
    }
    for (String key : new String[]{"kills", "deaths",
        "assists", "beds", "wins", "games"}) {
      defaultValues.put("monthly" + key, new DataContainer(0L));
    }
    defaultValues.put("month", new DataContainer((Calendar.getInstance().get(Calendar.MONTH) + 1) + "/" +
        Calendar.getInstance().get(Calendar.YEAR)));
    defaultValues.put("coins", new DataContainer(0.0D));
    defaultValues.put("lastmap", new DataContainer(0L));
    defaultValues.put("cosmetics", new DataContainer("{}"));
    defaultValues.put("selected", new DataContainer("{}"));
    defaultValues.put("favorites", new DataContainer("{}"));
    return defaultValues;
  }
}