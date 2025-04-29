package mc.twilight.caixas.box.loot;

import com.google.common.collect.ImmutableList;
import mc.twilight.core.cash.CashManager;
import mc.twilight.core.libraries.holograms.HologramLibrary;
import mc.twilight.core.libraries.holograms.api.Hologram;
import mc.twilight.core.libraries.holograms.api.HologramLine;
import mc.twilight.caixas.menus.fabricate.MenuFabricateBox;
import mc.twilight.caixas.menus.open.ConfirmOpenMenu;
import mc.twilight.core.player.Profile;
import mc.twilight.core.utils.BukkitUtils;
import mc.twilight.core.utils.enums.EnumSound;
import org.bukkit.Location;
import org.bukkit.util.EulerAngle;

import java.util.ArrayList;
import java.util.List;

public class LootHolograms {
  
  private LootMenu menu;
  private List<Hologram> holograms;
  
  public LootHolograms(LootMenu menu) {
    this.menu = menu;
    this.holograms = new ArrayList<>();
  }
  
  public void destroy() {
    this.menu = null;
    this.holograms.forEach(HologramLibrary::removeHologram);
    this.holograms.clear();
    this.holograms = null;
  }
  
  protected void zoom() {
    int hologramId = this.getHologramId(menu.targeting.getHologram());
    if (hologramId == 1) {
      EnumSound.ORB_PICKUP.play(menu.player, 1.0F, 1.0F);
    } else if (hologramId == 2 || hologramId == 3) {
      EnumSound.VILLAGER_NO.play(menu.player, 1.0F, 1.0F);
    }
    
    for (HologramLine line : menu.targeting.getHologram().getLines()) {
      line.setLocation(
          line.getLocation().clone().add(hologramId == 0 ? -0.05 : hologramId == 1 ? 0.05 : hologramId == 2 ? 0.1 : -0.1, 0, hologramId == 0 ? -0.05 : hologramId == 1 ? -0.05 : 0));
    }
  }
  
  protected void zoomout() {
    int hologramId = this.getHologramId(menu.targeting.getHologram());
    
    for (HologramLine line : menu.targeting.getHologram().getLines()) {
      line.setLocation(
          line.getLocation().clone().add(hologramId == 0 ? 0.05 : hologramId == 1 ? -0.05 : hologramId == 2 ? -0.1 : 0.1, 0, hologramId == 0 ? 0.05 : hologramId == 1 ? 0.05 : 0));
    }
  }
  
  protected void updateLootChests() {
    if (!holograms.isEmpty() && holograms.get(0).isSpawned()) {
      long lootChests = menu.profile.getStats("zCaixas", "magic");
      holograms.get(0).updateLine(1, (lootChests > 0 ? "§a" : "§c") + lootChests + " restantes!");
    }
  }
  
  protected void createOpenHologram() {
    Hologram hologram = this.createAttachedHologram(menu.lootChest.clone().add(2, 0.5, 0));
    long lootChests = menu.profile.getStats("zCaixas", "magic");
    hologram.withLine((lootChests > 0 ? "§a" : "§c") + lootChests + " restantes!");
    hologram.withLine(menu.chest.getName());
    hologram.withLine("");
    hologram.withLine("");
    hologram.withLine("");
    hologram.withLine("");
    hologram.getLine(3).getArmor().getEntity().setHeadPose(new EulerAngle(0, 135, 0));
    hologram.getLine(3).getArmor().getEntity().setSmall(false);
    hologram.getLine(3).getArmor().getEntity().setHelmet(menu.getLootChest().getHelmet());
    hologram.getLine(3).setTouchable(player -> {
      if (player.equals(menu.player)) {
        long amount = menu.profile.getStats("zCaixas", "magic");
        if (amount < 1) {
          player.sendMessage("§cVocê não possui nenhuma " + menu.chest.getName() + "§c!");
          return;
        }
        
        if (amount == 1) {
          menu.open(menu.chest.getAnimation());
        } else {
          new ConfirmOpenMenu(menu.profile, menu);
        }
      }
    });
    this.holograms.add(hologram);
  }
  
  protected void createShopHologram() {
    Hologram hologram = this.createAttachedHologram(menu.lootChest.clone().add(-2, 0.5, 0));
    hologram.withLine("");
    hologram.withLine("§6§lLoja");
    hologram.withLine("");
    hologram.getLine(3).getArmor().getEntity().setHeadPose(new EulerAngle(0, 45, 0));
    hologram.getLine(3).getArmor().getEntity().setSmall(false);
    hologram.getLine(3).getArmor().getEntity().setHelmet(BukkitUtils.deserializeItemStack(
        "SKULL_ITEM:3 : 1 : skin>eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvYzk0ODdmMzNmNTUwMGQxZWQ1MGEwNTgxOGMyNmFlZmZiMzE0ZWRkZGNhYzY4MTU5OTZkZmEyNjkyYTE4MmNmYyJ9fX0="));
    hologram.getLine(3).setTouchable(player -> {
      if (player.equals(menu.player)) {
        if (!CashManager.CASH) {
          player.sendMessage("§cEm desenvolvimento.");
          return;
        }
        
        new MenuFabricateBox(Profile.getProfile(player.getName()), null);
      }
    });
    this.holograms.add(hologram);
  }
  
  protected void createCloseHologram(boolean right) {
    Hologram hologram = this.createAttachedHologram(menu.lootChest.clone().add(right ? -3 : 3, 0.5, -3));
    hologram.withLine("");
    hologram.withLine("§c§lFechar");
    hologram.withLine("");
    hologram.getLine(3).getArmor().getEntity().setHeadPose(new EulerAngle(0, right ? 270 : -270, 0));
    hologram.getLine(3).getArmor().getEntity().setSmall(false);
    hologram.getLine(3).getArmor().getEntity().setHelmet(BukkitUtils.deserializeItemStack(
        "SKULL_ITEM:3 : 1 : skin>eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvNDkxYmNjNzg0NGY0NDViYzBlOTAxMWZiZjMzNGJmYTk0Y2ZmZGFjODdhNDMwMGVhOTZlMWY5MDM2NzEwODY2MSJ9fX0="));
    hologram.getLine(3).setTouchable(player -> {
      if (player.equals(menu.player)) {
        menu.cancel();
      }
    });
    this.holograms.add(hologram);
  }
  
  private Hologram createAttachedHologram(Location location) {
    Hologram hologram = HologramLibrary.createHologram(location, false);
    hologram.setAttached(menu.player.getName());
    hologram.spawn();
    return hologram;
  }
  
  private int getHologramId(Hologram hologram) {
    int hologramId = 0;
    for (Hologram search : this.holograms) {
      if (search.equals(hologram)) {
        return hologramId;
      }
      
      hologramId++;
    }
    
    return hologramId;
  }
  
  public List<Hologram> listHolograms() {
    return ImmutableList.copyOf(this.holograms);
  }
}
