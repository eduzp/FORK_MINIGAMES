package mc.twilight.caixas.menus.fabricate;


import mc.twilight.core.libraries.menu.PlayerMenu;
import mc.twilight.caixas.Main;
import mc.twilight.caixas.lobby.BoxNPC;
import mc.twilight.caixas.menus.MenuBoxes;
import mc.twilight.core.player.Profile;
import mc.twilight.core.utils.BukkitUtils;
import mc.twilight.core.utils.enums.EnumSound;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;

public class MenuFabricateBox extends PlayerMenu implements Listener {
  
  private final BoxNPC npc;
  
  public MenuFabricateBox(Profile profile, BoxNPC npc) {
    super(profile.getPlayer(), "Fabricar Cápsulas Mágicas", 4);
    this.npc = npc;
    
    this.setItem(11, BukkitUtils.deserializeItemStack(
        "SKULL_ITEM:3 : 1 : nome>&aPacote I : desc>&7Comprar 1 Cápsulas Mágicas com\n&7os seus fragmentos misteriosos\n \n&fPreço: &b160 Fragmentos Misteriosos\n \n&eClique para comprar! : skin>eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvMWRlYmNmZTQxNDNhMTFlNjFkMzkzMTc5OWZiMzIyZTVhYTJhZTczMjc1YzUzYzJjNjc0MTYxNzhkMTQ5ZTE1MiJ9fX0="));
    this.setItem(13, BukkitUtils.deserializeItemStack(
        "SKULL_ITEM:3 : 5 : nome>&aPacote II : desc>&7Comprar 5 Cápsulas Mágicas com\n&7os seus fragmentos misteriosos\n \n&fPreço: &b450 Fragmentos Misteriosos\n \n&eClique para comprar! : skin>eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvMWRlYmNmZTQxNDNhMTFlNjFkMzkzMTc5OWZiMzIyZTVhYTJhZTczMjc1YzUzYzJjNjc0MTYxNzhkMTQ5ZTE1MiJ9fX0="));
    this.setItem(15, BukkitUtils.deserializeItemStack(
        "SKULL_ITEM:3 : 10 : nome>&aPacote III : desc>&7Comprar 10 Cápsulas Mágicas com\n&7os seus fragmentos misteriosos\n \n&fPreço: &b850 Fragmentos Misteriosos\n \n&eClique para comprar! : skin>eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvMWRlYmNmZTQxNDNhMTFlNjFkMzkzMTc5OWZiMzIyZTVhYTJhZTczMjc1YzUzYzJjNjc0MTYxNzhkMTQ5ZTE1MiJ9fX0="));
    
    this.setItem(31, BukkitUtils.deserializeItemStack("INK_SACK:1 : 1 : nome>&c" +
        (npc == null ? "Fechar" : "Voltar")));
    
    this.register(Main.getInstance());
    this.open();
  }
  
  @EventHandler
  public void onInventoryClick(InventoryClickEvent evt) {
    if (evt.getInventory().equals(this.getInventory())) {
      evt.setCancelled(true);
      if (evt.getWhoClicked() instanceof Player) {
        Player player = (Player) evt.getWhoClicked();
        Profile profile = Profile.getProfile(player.getName());
        if (profile == null) {
          player.closeInventory();
          return;
        }
        
        if (evt.getClickedInventory() != null && evt.getClickedInventory().equals(this.getInventory())) {
          ItemStack item = evt.getCurrentItem();
          if (item != null && item.getType() != Material.AIR) {
            if (evt.getSlot() == 11) {
              if (profile.getStats("zCaixas", "mystery_frags") < 160) {
                EnumSound.VILLAGER_NO.play(player, 0.5F, 1.0F);
                return;
              }
              
              EnumSound.CLICK.play(player, 0.5F, 2.0F);
              new MenuConfirmBuyBox(player, "Pacote I", 1, 160, npc);
            } else if (evt.getSlot() == 13) {
              if (profile.getStats("zCaixas", "mystery_frags") < 450) {
                EnumSound.VILLAGER_NO.play(player, 0.5F, 1.0F);
                return;
              }
              
              EnumSound.CLICK.play(player, 0.5F, 2.0F);
              new MenuConfirmBuyBox(player, "Pacote II", 5, 450, npc);
            } else if (evt.getSlot() == 15) {
              if (profile.getStats("zCaixas", "mystery_frags") < 850) {
                EnumSound.VILLAGER_NO.play(player, 0.5F, 1.0F);
                return;
              }
              
              EnumSound.CLICK.play(player, 0.5F, 2.0F);
              new MenuConfirmBuyBox(player, "Pacote III", 10, 850, npc);
            } else if (evt.getSlot() == 31) {
              EnumSound.CLICK.play(player, 0.5F, 2.0F);
              if (npc == null) {
                player.closeInventory();
                return;
              }
              new MenuBoxes(profile, npc);
            }
          }
        }
      }
    }
    
  }
}