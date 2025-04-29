package mc.twilight.bedwars.menus;

import mc.twilight.bedwars.Language;
import mc.twilight.bedwars.Main;
import mc.twilight.bedwars.game.BedWars;
import mc.twilight.bedwars.game.enums.BedWarsMode;
import mc.twilight.core.libraries.menu.UpdatablePlayerMenu;
import mc.twilight.core.player.Profile;
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

public class MenuPlay extends UpdatablePlayerMenu {
  
  private BedWarsMode mode;
  
  public MenuPlay(Profile profile, BedWarsMode mode) {
    super(profile.getPlayer(), "Bed Wars " + mode.getName(), 3);
    this.mode = mode;
    
    this.update();
    this.register(Main.getInstance(), 20);
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
            if (evt.getSlot() == 12) {
              EnumSound.ITEM_PICKUP.play(this.player, 0.5F, 2.0F);
              BedWars game = BedWars.findRandom(this.mode);
              if (game != null) {
                this.player.sendMessage(Language.lobby$npc$play$connect);
                game.join(profile);
              }
            } else if (evt.getSlot() == 14) {
              EnumSound.CLICK.play(this.player, 0.5F, 2.0F);
              new MenuMapSelector(profile, this.mode);
            }
          }
        }
      }
    }
  }
  
  @Override
  public void update() {
    int players = this.mode.getSize() * 8;
    int waiting = BedWars.getWaiting(this.mode);
    int playing = BedWars.getPlaying(this.mode);
    
    this.setItem(12, BukkitUtils.deserializeItemStack("BED : 1 : nome>&aBed Wars " + this.mode.getName() + " : desc>&78 ilhas, " + players
        + " jogadores.\n \n&fEm espera: &7" + StringUtils.formatNumber(waiting) + "\n&fJogando: &7" + StringUtils.formatNumber(playing) + "\n \n&eClique para jogar!"));
    
    this.setItem(14, BukkitUtils.deserializeItemStack("MAP : 1 : nome>&aSelecione um Mapa : desc>&eClique para jogar em um mapa específico."));
  }
  
  public void cancel() {
    super.cancel();
    HandlerList.unregisterAll(this);
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
