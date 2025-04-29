package mc.twilight.caixas.hook.table;

import mc.twilight.core.database.Database;
import mc.twilight.core.database.HikariDatabase;
import mc.twilight.core.database.MySQLDatabase;
import mc.twilight.core.database.data.DataContainer;
import mc.twilight.core.database.data.DataTable;
import mc.twilight.core.database.data.interfaces.DataTableInfo;

import java.util.LinkedHashMap;
import java.util.Map;

@DataTableInfo(
    name = "zCaixas",
    create = "CREATE TABLE IF NOT EXISTS `zCaixas` (`name` VARCHAR(32), `magic` LONG, `queueRewards` TEXT, `mystery_frags` LONG, `alreadyRewarded` TEXT, `lastRewards` TEXT, `selected` TEXT, `cosmetics` TEXT, PRIMARY KEY(`name`)) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE utf8_bin;",
    select = "SELECT * FROM `zCaixas` WHERE LOWER(`name`) = ?",
    insert = "INSERT INTO `zCaixas` VALUES (?, ?, ?, ?, ?, ?, ?, ?)",
    update = "UPDATE `zCaixas` SET `magic` = ?, `queueRewards` = ?, `mystery_frags` = ?, `alreadyRewarded` = ?, `lastRewards` = ?, `selected` = ?, `cosmetics` = ? WHERE LOWER(`name`) = ?"
)
public class MysteryBoxTable extends DataTable {
  
  @Override
  public void init(Database database) {
    if (database instanceof MySQLDatabase) {
      ((MySQLDatabase) database).execute("CREATE TABLE IF NOT EXISTS `zCaixasContent` (`id` VARCHAR(32), `name` TEXT, `rarity` TEXT, `action` TEXT, `dependency` TEXT, PRIMARY KEY(`id`));");
    } else if (database instanceof HikariDatabase) {
      ((HikariDatabase) database).execute("CREATE TABLE IF NOT EXISTS `zCaixasContent` (`id` VARCHAR(32), `name` TEXT, `rarity` TEXT, `action` TEXT, `dependency` TEXT, PRIMARY KEY(`id`));");
    }
  }
  
  @Override
  public Map<String, DataContainer> getDefaultValues() {
    Map<String, DataContainer> defaultValues = new LinkedHashMap<>();
    defaultValues.put("magic", new DataContainer(0L));
    defaultValues.put("queueRewards", new DataContainer("[]"));
    defaultValues.put("mystery_frags", new DataContainer(0L));
    defaultValues.put("alreadyRewarded", new DataContainer("[]"));
    defaultValues.put("lastRewards", new DataContainer("[]"));
    defaultValues.put("selected", new DataContainer("{}"));
    defaultValues.put("cosmetics", new DataContainer("{}"));
    return defaultValues;
  }
}
