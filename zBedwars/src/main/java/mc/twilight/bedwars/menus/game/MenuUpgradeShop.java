package mc.twilight.bedwars.menus.game;

import mc.twilight.core.Core;
import mc.twilight.bedwars.Main;
import mc.twilight.bedwars.game.BedWars;
import mc.twilight.bedwars.game.BedWarsTeam;
import mc.twilight.bedwars.game.improvements.Upgrade;
import mc.twilight.bedwars.game.improvements.Upgrades;
import mc.twilight.bedwars.game.improvements.traps.Trap;
import mc.twilight.bedwars.utils.PlayerUtils;
import mc.twilight.core.libraries.menu.PlayerMenu;
import mc.twilight.core.player.Profile;
import mc.twilight.core.plugin.config.KConfig;
import mc.twilight.core.utils.BukkitUtils;
import mc.twilight.core.utils.StringUtils;
import mc.twilight.core.utils.enums.EnumSound;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.HandlerList;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MenuUpgradeShop extends PlayerMenu {
  
  private Map<Integer, Upgrade> upgrades = new HashMap<>();
  
  public MenuUpgradeShop(Profile profile) {
    super(profile.getPlayer(), "Loja de Melhorias", 5);
    
    BedWarsTeam team = profile.getGame(BedWars.class).getTeam(player);
    for (Upgrade upgrade : Upgrades.listUpgrades()) {
      String stack = upgrade.getIcon();
      
      boolean maxTier = team.getTier(upgrade.getType()) >= upgrade.getMaxTier();
      int nextTier = maxTier ? upgrade.getMaxTier() : team.getTier(upgrade.getType()) + 1;
      String color = PlayerUtils.getCountFromMaterial(player.getInventory(), upgrade.getPrice(nextTier).getType()) < upgrade.getPrice(nextTier).getAmount() ? "&c" : "&a";
      stack = stack.replace("{color}", color);
      
      ItemStack icon = BukkitUtils.deserializeItemStack(stack.replace("{tier}", nextTier > 3 ? nextTier == 4 ? "IV" : "V" : StringUtils.repeat("I", nextTier)));
      ItemMeta meta = icon.getItemMeta();
      List<String> lore = meta.getLore();
      lore.add("");
      if (maxTier) {
        lore.add("§cMelhoria já está maximizada!");
      } else if (color.equals("&c")) {
        lore.add("§cVocê não possui Diamantes suficientes!");
      } else {
        lore.add("§eClique para comprar!");
      }
      meta.setLore(lore);
      icon.setItemMeta(meta);
      
      
      KConfig CONF = Main.getInstance().getConfig("upgrades");
      this.setItem(CONF.getInt("trap.slot"),
          BukkitUtils.deserializeItemStack(CONF.getString("trap.icon")));
      
      for (int i = 0; i < 9; i++) {
        if (i == 0 || i == 8) {
          this.setItem(18 + i, BukkitUtils.deserializeItemStack("STAINED_GLASS_PANE:7 : 1 : nome>&f"));
        } else {
          this.setItem(18 + i, BukkitUtils.deserializeItemStack("STAINED_GLASS_PANE:7 : 1 : nome>&8↑ &7Melhorias : desc>&8↓ &7Fila de Armadilhas"));
        }
      }
      
      for (int slot = 30; slot < 33; slot++) {
        int index = slot - 30;
        Trap trap = index < team.getTraps().size() ? team.getTraps().get(index) : null;
        if (trap == null) {
          int trapIndex = (index + 1);
          this.setItem(slot, BukkitUtils.deserializeItemStack(
              "STAINED_GLASS:7 : 1 : nome>&cArmadilha #" + trapIndex + ": Nenhuma! : desc>&7O " + (trapIndex == 1 ? "primeiro" : (trapIndex == 2 ? "segundo" : "terceiro")) + " inimigo a entrar\n&7em sua base irá ativar\n&7a armadilha!\n \n&7Comprar uma armadilha irá\n&7adicioná-la aqui. O custo\n&7varia de acordo com a\n&7quantia de armadilhas na fila.\n \n&7Próxima armadilha: §b" + (team
                  .getTraps().size() + 1) + " Diamante" + (team.getTraps().size() + 1 > 1 ? "s" : "")));
        } else {
          this.setItem(slot, BukkitUtils.deserializeItemStack(trap.getIcon().replace("{color}", "&a") + "\n \n&7Esta armadilha será ativada\n&7quando o " + (index == 0 ?
              "primeiro" :
              index == 1 ? "segundo" : "terceiro") + " oponente\n&7entrar em sua base."));
        }
      }
      
      this.setItem(upgrade.getSlot(), icon);
      this.upgrades.put(upgrade.getSlot(), upgrade);
    }
    
    this.open();
    this.register(Core.getInstance());
  }
  
  @EventHandler
  public void onInventoryClick(InventoryClickEvent evt) {
    if (evt.getInventory().equals(getInventory())) {
      evt.setCancelled(true);
      
      if (evt.getWhoClicked() instanceof Player && evt.getWhoClicked().equals(player)) {
        ItemStack item = evt.getCurrentItem();
        BedWars game;
        BedWarsTeam team;
        Profile profile = Profile.getProfile(player.getName());
        if (profile == null || (game = profile.getGame(BedWars.class)) == null || (team = game.getTeam(player)) == null) {
          player.closeInventory();
          return;
        }
        
        if (evt.getClickedInventory() != null && evt.getClickedInventory().equals(evt.getInventory()) && item != null && item.getType() != Material.AIR) {
          Upgrade upgrade = this.upgrades.get(evt.getSlot());
          KConfig CONF = Main.getInstance().getConfig("upgrades");
          
          if (upgrade != null) {
            boolean maxTier = team.getTier(upgrade.getType()) >= upgrade.getMaxTier();
            int nextTier = maxTier ? upgrade.getMaxTier() : team.getTier(upgrade.getType()) + 1;
            if (maxTier) {
              return;
            }
            
            if (PlayerUtils.getCountFromMaterial(player.getInventory(), upgrade.getPrice(nextTier).getType()) < upgrade.getPrice(nextTier).getAmount()) {
              player.sendMessage("§cVocê não possui recursos para realizar esta compra!");
              return;
            }
            
            PlayerUtils.removeItem(player.getInventory(), upgrade.getPrice(nextTier).getType(), upgrade.getPrice(nextTier).getAmount());
            team.setUpgrade(upgrade.getType());
            EnumSound.NOTE_PLING.play(player, 0.5F, 1.0F);
            
            for (Player member : team.listPlayers()) {
              team.refresh(member);
              Profile mclient = Profile.getProfile(member.getName());
              if (mclient != null) {
                member.sendMessage("§a" + player.getName() + " comprou §6" + StringUtils.stripColors(item.getItemMeta().getDisplayName()));
              }
            }
            
            new MenuUpgradeShop(profile);
          } else if (evt.getSlot() == CONF.getInt("trap.slot")) {
            EnumSound.CLICK.play(player, 0.5F, 2.0F);
            new MenuTrapsShop(profile);
          }
        }
      }
    }
  }
  
  public void cancel() {
    upgrades.clear();
    upgrades = null;
    HandlerList.unregisterAll(this);
  }
  
  @EventHandler
  public void onPlayerQuit(PlayerQuitEvent evt) {
    if (evt.getPlayer().equals(player)) {
      this.cancel();
    }
  }
  
  @EventHandler
  public void onInventoryClose(InventoryCloseEvent evt) {
    if (evt.getPlayer().equals(player) && evt.getInventory().equals(this.getInventory())) {
      this.cancel();
    }
  }
}
