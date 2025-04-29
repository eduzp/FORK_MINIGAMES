package mc.twilight.bedwars.menus.cosmetics;

import mc.twilight.core.Core;
import mc.twilight.bedwars.hook.container.SelectedContainer;
import mc.twilight.bedwars.cosmetics.Cosmetic;
import mc.twilight.bedwars.cosmetics.CosmeticType;
import mc.twilight.bedwars.cosmetics.object.AbstractPreview;
import mc.twilight.bedwars.cosmetics.object.preview.CagePreview;
import mc.twilight.bedwars.cosmetics.object.preview.KillEffectPreview;
import mc.twilight.bedwars.cosmetics.types.Cage;
import mc.twilight.bedwars.cosmetics.types.DeathCry;
import mc.twilight.bedwars.cosmetics.types.DeathMessage;
import mc.twilight.bedwars.cosmetics.types.KillEffect;
import mc.twilight.bedwars.menus.MenuShop;
import mc.twilight.bedwars.menus.cosmetics.animations.MenuAnimations;
import mc.twilight.core.cash.CashManager;
import mc.twilight.core.libraries.menu.PagedPlayerMenu;
import mc.twilight.core.player.Profile;
import mc.twilight.core.player.role.Role;
import mc.twilight.core.utils.BukkitUtils;
import mc.twilight.core.utils.StringUtils;
import mc.twilight.core.utils.enums.EnumSound;
import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.HandlerList;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.inventory.ItemStack;

import java.util.*;

public class MenuCosmetics<T extends Cosmetic> extends PagedPlayerMenu {
  
  private Class<T> cosmeticClass;
  private Map<ItemStack, T> cosmetics = new HashMap<>();
  public MenuCosmetics(Profile profile, String name, Class<T> cosmeticClass) {
    super(profile.getPlayer(), "Bed Wars - " + name, (Cosmetic.listByType(cosmeticClass).size() / 7) + 4);
    this.cosmeticClass = cosmeticClass;
    this.previousPage = (this.rows * 9) - 9;
    this.nextPage = (this.rows * 9) - 1;
    this.onlySlots(10, 11, 12, 13, 14, 15, 16, 19, 20, 21, 22, 23, 24, 25, 28, 29, 30, 31, 32, 33, 34);
    
    String desc = "§7Para a Loja.";
    if (Objects.requireNonNull(Cosmetic.listByType(cosmeticClass).stream().findFirst().orElse(null)).getType().toString().contains("EFFECT")) {
      desc = "§7Para Animações.";
    }
    this.removeSlotsWith(BukkitUtils.deserializeItemStack("INK_SACK:1 : 1 : nome>&cVoltar : desc>" + desc), (this.rows * 9) - 5);
    
    List<ItemStack> items = new ArrayList<>();
    List<T> cosmetics = Cosmetic.listByType(cosmeticClass);
    for (T cosmetic : cosmetics) {
      if (cosmetic.getCash() == 0 && cosmetic.getCoins() == 0 && cosmetic.getId() == 0) {
        if (!cosmetic.has(profile)) {
          cosmetic.give(profile);
        }
      }
      ItemStack icon = cosmetic.getIcon(profile);
      items.add(icon);
      this.cosmetics.put(icon, cosmetic);
    }
    
    this.setItems(items);
    cosmetics.clear();
    items.clear();
    
    this.register(Core.getInstance());
    this.open();
  }
  
  @EventHandler
  public void onInventoryClick(InventoryClickEvent evt) {
    if (evt.getInventory().equals(this.getCurrentInventory())) {
      evt.setCancelled(true);
      
      if (evt.getWhoClicked().equals(this.player)) {
        Profile profile = Profile.getProfile(this.player.getName());
        if (profile == null) {
          this.player.closeInventory();
          return;
        }
        
        if (evt.getClickedInventory() != null && evt.getClickedInventory().equals(this.getCurrentInventory())) {
          ItemStack item = evt.getCurrentItem();
          
          if (item != null && item.getType() != Material.AIR) {
            if (evt.getSlot() == this.previousPage) {
              EnumSound.CLICK.play(this.player, 0.5F, 2.0F);
              this.openPrevious();
            } else if (evt.getSlot() == this.nextPage) {
              EnumSound.CLICK.play(this.player, 0.5F, 2.0F);
              this.openNext();
            } else if (evt.getSlot() == (this.rows * 9) - 5) {
              EnumSound.CLICK.play(this.player, 0.5F, 2.0F);
              if (Objects.requireNonNull(Cosmetic.listByType(cosmeticClass).stream().findFirst().orElse(null)).getType().toString().contains("EFFECT")) {
                new MenuAnimations(profile);
                return;
              }
              new MenuShop(profile);
            } else {
              T cosmetic = this.cosmetics.get(item);
              if (cosmetic != null) {
                if (evt.isRightClick()) {
                  if (cosmetic.getType() == CosmeticType.DEATH_CRY) {
                    ((DeathCry) cosmetic).getSound().play(this.player, ((DeathCry) cosmetic).getVolume(), ((DeathCry) cosmetic).getSpeed());
                    return;
                  } else if (cosmetic.getType() == CosmeticType.KILL_EFFECT) {
                    if (!AbstractPreview.canDoKillEffect()) {
                      if (player.hasPermission("zbedwars.cmd.bedwars")) {
                        EnumSound.VILLAGER_NO.play(player, 1.0F, 1.0F);
                        player.sendMessage("§cSete as localizações da previsualização utilizando /bw preview killeffect");
                      }
                      return;
                    }
                    
                    new KillEffectPreview(profile, (KillEffect) cosmetic);
                    player.closeInventory();
                    return;
                  } else if (cosmetic.getType() == CosmeticType.DEATH_MESSAGE) {
                    StringBuilder message = new StringBuilder("\n §eMensagens que poderão ser exibidas ao abater seu oponente: \n");
                    for (String msg : ((DeathMessage) cosmetic).getMessages()) {
                      message.append("\n §8▪ ").append(StringUtils.formatColors(msg.replace("{name}", "§7Jogador").replace("{killer}", Role.getColored(player.getName()))));
                    }
                    message.append("\n \n");
                    player.sendMessage(message.toString());
                    
                    return;
                  } else if (cosmetic.getType() == CosmeticType.CAGE) {
                    if (!AbstractPreview.canDoCage()) {
                      if (player.hasPermission("zbedwars.cmd.bedwars")) {
                        EnumSound.VILLAGER_NO.play(player, 1.0F, 1.0F);
                        player.sendMessage("§cSete as localizações da previsualização utilizando /bw preview cage");
                      }
                      return;
                    }
                    
                    new CagePreview(profile, (Cage) cosmetic);
                    player.closeInventory();
                    return;
                  }
                  
                  
                }
                
                if (!cosmetic.has(profile)) {
                  if (!cosmetic.canBuy(this.player) || (profile.getCoins("zCoreBedWars") < cosmetic.getCoins() && (CashManager.CASH && profile
                      .getStats("zCoreProfile", "cash") < cosmetic.getCash()))) {
                    EnumSound.ENDERMAN_TELEPORT.play(this.player, 0.5F, 1.0F);
                    return;
                  }
                  
                  EnumSound.CLICK.play(this.player, 0.5F, 2.0F);
                  if (!CashManager.CASH || cosmetic.getCash() == 0) {
                    new MenuBuyCosmetic<>(profile, this.name.replace("Bed Wars - ", ""), cosmetic, this.cosmeticClass);
                  } else {
                    new MenuBuyCashCosmetic<>(profile, this.name.replace("Bed Wars - ", ""), cosmetic, this.cosmeticClass);
                  }
                  return;
                }
                
                if (!cosmetic.canBuy(this.player)) {
                  EnumSound.ENDERMAN_TELEPORT.play(this.player, 0.5F, 1.0F);
                  this.player.sendMessage("§cVocê não possui permissão suficiente para continuar.");
                  return;
                }
                
                EnumSound.ITEM_PICKUP.play(this.player, 0.5F, 2.0F);
                if (cosmetic.isSelected(profile)) {
                  profile.getAbstractContainer("zCoreBedWars", "selected", SelectedContainer.class).setSelected(cosmetic.getType(), 0);
                } else {
                  profile.getAbstractContainer("zCoreBedWars", "selected", SelectedContainer.class).setSelected(cosmetic);
                }
                
                new MenuCosmetics<>(profile, this.name.replace("Bed Wars - ", ""), this.cosmeticClass);
              }
            }
          }
        }
      }
    }
  }
  
  public void cancel() {
    HandlerList.unregisterAll(this);
    this.cosmeticClass = null;
    this.cosmetics.clear();
    this.cosmetics = null;
  }
  
  @EventHandler
  public void onPlayerQuit(PlayerQuitEvent evt) {
    if (evt.getPlayer().equals(this.player)) {
      this.cancel();
    }
  }
  
  @EventHandler
  public void onInventoryClose(InventoryCloseEvent evt) {
    if (evt.getPlayer().equals(this.player) && evt.getInventory().equals(this.getCurrentInventory())) {
      this.cancel();
    }
  }
}
