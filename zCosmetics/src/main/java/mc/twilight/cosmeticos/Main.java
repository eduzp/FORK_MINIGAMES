package mc.twilight.cosmeticos;

import mc.twilight.cosmeticos.cmd.Commands;
import mc.twilight.cosmeticos.cosmetics.Cosmetic;
import mc.twilight.cosmeticos.hook.table.CosmeticsTable;
import mc.twilight.cosmeticos.listeners.Listeners;
import mc.twilight.cosmeticos.nms.NMS;
import mc.twilight.core.database.data.DataTable;
import mc.twilight.core.libraries.MinecraftVersion;
import mc.twilight.core.plugin.KPlugin;
import org.bukkit.Bukkit;

import java.io.File;
import java.io.FileInputStream;

public class Main extends KPlugin {
  
  public static boolean kMysteryBox;
  private static Main instance;
  private static boolean validInit;
  
  public static Main getInstance() {
    return instance;
  }
  
  @Override
  public void start() {
    instance = this;
  }
  
  @Override
  public void load() {
    DataTable.registerTable(new CosmeticsTable());
  }
  
  @Override
  public void enable() {
    saveDefaultConfig();
    if (!NMS.setupNMS()) {
      this.setEnabled(false);
      this.getLogger().warning("O plugin apenas funciona na versao 1_8_R3 (Atual: " + MinecraftVersion.getCurrentVersion().getVersion() + ")");
      return;
    }
    
    kMysteryBox = Bukkit.getPluginManager().getPlugin("kMysteryBox") != null;
  
    Cosmetic.setupCosmetics();
    Language.setupLanguage();
    
    Commands.setupCommands();
    Listeners.setupListeners();
    
    validInit = true;
    this.getLogger().info("O plugin foi ativado.");
  }
  
  @Override
  public void disable() {
    if (validInit) {
      File update = new File("plugins/zCosmeticos/update", "zCosmeticos.jar");
      if (update.exists()) {
        try {
          this.getFileUtils().deleteFile(new File("plugins/" + update.getName()));
          this.getFileUtils().copyFile(new FileInputStream(update), new File("plugins/" + update.getName()));
          this.getFileUtils().deleteFile(update.getParentFile());
          this.getLogger().info("Update aplicada.");
        } catch (Exception ex) {
          ex.printStackTrace();
        }
      }
    }
    this.getLogger().info("O plugin foi desativado.");
  }
}
