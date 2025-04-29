package mc.twilight.caixas.menus.fabricate;

import mc.twilight.core.libraries.menu.PlayerMenu;
import mc.twilight.caixas.Main;
import mc.twilight.caixas.lobby.BoxNPC;
import mc.twilight.core.player.Profile;
import mc.twilight.core.utils.BukkitUtils;
import mc.twilight.core.utils.enums.EnumSound;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.HandlerList;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.inventory.ItemStack;

public class MenuConfirmBuyBox extends PlayerMenu {
  
  private long amount;
  private final BoxNPC npc;
  private long price;
  
  public MenuConfirmBuyBox(Player player, String pack, long amount, long price, BoxNPC npc) {
    super(player, "Comprar " + pack + "?", 3);
    this.amount = amount;
    this.price = price;
    this.npc = npc;
    
    this.setItem(11, BukkitUtils.deserializeItemStack(
        "STAINED_GLASS_PANE:5 : 1 : nome>&aConfirmar : desc>&7Comprar " + pack + " por\n&b" + price + " fragmentos misteriosos&7."));
    this.setItem(15, BukkitUtils.deserializeItemStack("" +
        "STAINED_GLASS_PANE:14 : 1 : nome>&cCancelar : desc>&7Voltar para os Pacotes."));
    
    this.register(Main.getInstance());
    this.open();
  }
  
  @EventHandler
  public void onInventoryClick(InventoryClickEvent evt) {
    if (evt.getInventory().equals(this.getInventory())) {
      evt.setCancelled(true);
      if (evt.getWhoClicked().equals(this.player)) {
        Profile profile = Profile.getProfile(this.player.getName());
        if (profile == null) {
          this.player.closeInventory();
          return;
        }
        
        if (evt.getClickedInventory() != null && evt.getClickedInventory().equals(this.getInventory())) {
          ItemStack item = evt.getCurrentItem();
          if (item != null && item.getType() != Material.AIR) {
            if (evt.getSlot() == 11) {
              if (profile.getStats("zCaixas", "mystery_frags") < this.price) {
                EnumSound.VILLAGER_NO.play(this.player, 0.5F, 1.0F);
              } else {
                EnumSound.NOTE_PLING.play(this.player, 0.5F, 1.0F);
                
                profile.setStats("zCaixas",
                    profile.getStats("zCaixas", "mystery_frags") - this.price, "mystery_frags");
                profile.addStats("zCaixas", this.amount, "magic");
                this.player.sendMessage("§aVocê comprou §b§l" + this.amount + " Cápsulas Mágicas§a.");
              }
              
              new MenuFabricateBox(profile, npc);
            } else if (evt.getSlot() == 15) {
              EnumSound.ENDERMAN_TELEPORT.play(this.player, 0.5F, 1.0F);
              new MenuFabricateBox(profile, npc);
            }
          }
        }
      }
    }
  }
  
  public void cancel() {
    HandlerList.unregisterAll(this);
    this.price = 0;
    this.amount = 0;
  }
  
  @EventHandler
  public void onPlayerQuit(PlayerQuitEvent evt) {
    if (evt.getPlayer().equals(this.player)) {
      this.cancel();
    }
  }
  
  @EventHandler
  public void onInventoryClose(InventoryCloseEvent evt) {
    if (evt.getPlayer().equals(this.player) && evt.getInventory().equals(this.getInventory())) {
      this.cancel();
    }
  }
}
