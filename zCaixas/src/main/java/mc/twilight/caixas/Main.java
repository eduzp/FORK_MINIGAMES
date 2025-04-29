package mc.twilight.caixas;

import mc.twilight.core.database.data.DataTable;
import mc.twilight.core.libraries.MinecraftVersion;
import mc.twilight.caixas.cmd.Commands;
import mc.twilight.caixas.hook.MBCoreHook;
import mc.twilight.caixas.hook.table.MysteryBoxTable;
import mc.twilight.caixas.listeners.Listeners;
import mc.twilight.caixas.lobby.BoxNPC;
import mc.twilight.core.plugin.KPlugin;
import mc.twilight.core.utils.BukkitUtils;
import org.bukkit.Bukkit;
import org.bukkit.Location;

public class Main extends KPlugin {
  
  public static Main instance;
  public static boolean zCosmeticos;
  public static boolean validInit;
  private static Location lootLocation;
  
  public static Main getInstance() {
    return instance;
  }
  
  public static Location getLootChestsLocation() {
    return lootLocation;
  }
  
  public static void setLootChestsLocation(Location newLocation) {
    lootLocation = newLocation;
    getInstance().getConfig().set("menuLocation", BukkitUtils.serializeLocation(lootLocation));
    getInstance().saveConfig();
  }
  
  @Override
  public void start() {
    instance = this;
  }
  
  @Override
  public void load() {
    DataTable.registerTable(new MysteryBoxTable());
  }
  
  @Override
  public void enable() {
    zCosmeticos = Bukkit.getPluginManager().getPlugin("zCosmeticos") != null;
    
    saveDefaultConfig();
    lootLocation = Bukkit.getWorlds().get(0).getSpawnLocation().clone().add(0.5, 1, 0.5);
    if (getConfig().get("menuLocation") != null) {
      lootLocation = BukkitUtils.deserializeLocation(getConfig().getString("menuLocation")).add(0, 1, 0);
    }
    if (MinecraftVersion.getCurrentVersion().getCompareId() != 183) {
      this.setEnabled(false);
      this.getLogger().warning("O plugin apenas funciona na versao 1_8_R3 (Atual: " + MinecraftVersion.getCurrentVersion().getVersion() + ")");
      return;
    }
    
    MBCoreHook.setupHook();
    BoxNPC.setupBoxNPCs();
    Listeners.setupListeners();
    Language.setupLanguage();
    Commands.setupCommands();
    
    validInit = true;
    this.getLogger().info("O plugin foi ativado.");
  }
  
  @Override
  public void disable() {
    if (validInit) {
      BoxNPC.listNPCs().forEach(BoxNPC::destroy);
    }
    
    this.getLogger().info("O plugin foi desativado.");
  }
}
