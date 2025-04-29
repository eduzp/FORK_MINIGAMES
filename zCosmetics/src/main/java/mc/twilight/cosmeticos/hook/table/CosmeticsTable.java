package mc.twilight.cosmeticos.hook.table;

import mc.twilight.core.database.Database;
import mc.twilight.core.database.data.DataContainer;
import mc.twilight.core.database.data.DataTable;
import mc.twilight.core.database.data.interfaces.DataTableInfo;

import java.util.LinkedHashMap;
import java.util.Map;

@DataTableInfo(
    name = "zCosmeticos",
    create = "CREATE TABLE IF NOT EXISTS `zCosmeticos` (`name` VARCHAR(32), `cosmetics` TEXT, `selected` TEXT, `petSettings` TEXT, `companionsNames` TEXT, PRIMARY KEY(`name`)) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE utf8_bin;",
    select = "SELECT * FROM `zCosmeticos` WHERE LOWER(`name`) = ?",
    insert = "INSERT INTO `zCosmeticos` VALUES (?, ?, ?, ?, ?)",
    update = "UPDATE `zCosmeticos` SET `cosmetics` = ?, `selected` = ?, `petSettings` = ?, `companionsNames` = ? WHERE LOWER(`name`) = ?"
)
public class CosmeticsTable extends DataTable {
  
  public void init(Database database) {
  }
  
  public Map<String, DataContainer> getDefaultValues() {
    Map<String, DataContainer> defaultValues = new LinkedHashMap<>();
    defaultValues.put("cosmetics", new DataContainer("{}"));
    defaultValues.put("selected", new DataContainer("{}"));
    defaultValues.put("petSettings", new DataContainer("{}"));
    defaultValues.put("companionsNames", new DataContainer("{}"));
    return defaultValues;
  }
}
