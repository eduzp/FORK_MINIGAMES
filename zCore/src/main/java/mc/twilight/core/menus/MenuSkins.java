package mc.twilight.core.menus;

import mc.twilight.core.database.data.container.SkinsContainer;
import mc.twilight.core.Core;
import mc.twilight.core.libraries.menu.PlayerMenu;
import mc.twilight.core.listeners.Listeners;
import mc.twilight.core.player.Profile;
import mc.twilight.core.utils.BukkitUtils;
import mc.twilight.core.utils.enums.EnumSound;
import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.chat.BaseComponent;
import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.ClickEvent.Action;
import net.md_5.bungee.api.chat.HoverEvent;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.HandlerList;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.inventory.ItemStack;

import java.text.SimpleDateFormat;
import java.util.*;

public class MenuSkins extends PlayerMenu {
   private static final SimpleDateFormat SDF = new SimpleDateFormat("d 'de' MMMM. yyyy 'às' HH:mm", Locale.forLanguageTag("pt-BR"));
   private Map<ItemStack, String> skins = new HashMap();
   public static Map<String, String> LIBRARY = new HashMap<>();

   public MenuSkins(Profile profile) {
      super(profile.getPlayer(), "Skins", 5);

      mc.twilight.core.database.data.container.SkinsContainer skinsContainer = profile.getSkinsContainer();
      String selectedSkin = skinsContainer == null ? "Default" : skinsContainer.getSkin();
      List<ItemStack> items = new ArrayList();
      Map<String, String> skinsList = profile.getSkinListContainer().getSkins();

      if (selectedSkin == null){

         this.setItem(29, BukkitUtils.putProfileOnSkull(this.player, BukkitUtils.deserializeItemStack("SKULL_ITEM:3 : 1 : nome>&aSua Skin : desc>&7Skin do Player: §aPadrão")));//29
      } else {
         this.setItem(29, BukkitUtils.putProfileOnSkull(this.player, BukkitUtils.deserializeItemStack("SKULL_ITEM:3 : 1 : nome>&aSua Skin : desc>&7Skin do Player: §a" + selectedSkin)));//29
      }

      this.setItem(30, BukkitUtils.deserializeItemStack("SKULL_ITEM:3 : 1 : nome>&aAjuda : desc>&7As ações disponíveis nesse menu também\n&7podem ser realizadas por comandos.\n \n&fComando: &7/skin ajuda\n \n&eClique para ver mais! : skin>eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvNzE4MDc5ZDU4NDc0MTZhYmY0NGU4YzJmZWMyY2NkNDRmMDhkNzM2Y2E4ZTUxZjk1YTQzNmQ4NWY2NDNmYmMifX19"));//30
      this.setItem(40, BukkitUtils.deserializeItemStack("ARROW : 1 : nome>&cVoltar"));
      this.setItem(33, BukkitUtils.deserializeItemStack("BOOK_AND_QUILL : 1 : nome>&aEscolher uma Skin : desc>&7Você pode escolher uma nova skin\n&7para ser utilizada em sua conta.\n \n&fComando: &7/skin [nome]\n \n&eClique para escolher!"));//31
      this.setItem(32, BukkitUtils.deserializeItemStack("389 : 1 : nome>&aAtualizar sua Skin : desc>&7Restaura a sua skin para a skin\n&7da sua conta do Minecraft.\n \n&fComando: &7/skin atualizar\n \n&eClique para atualizar!"));//32


      this.setItems(items);
      items.clear();
      skinsList.clear();
      this.register(Core.getInstance());
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
                  String skinName = this.skins.get(item);
                  if (evt.getSlot() == 33) {
                     this.player.closeInventory();
                     if (!this.player.hasPermission("skins.update")) {
                        this.player.sendMessage("§cVocê precisa ter algum plano VIP para executar este comando.");
                        return;
                     }

                     Listeners.SKINS.add(this.player.getName());
                     TextComponent component = new TextComponent("");
                     BaseComponent[] var6 = TextComponent.fromLegacyText("\n§aDigite a sua nova skin no chat ou clique ");
                     int var7 = var6.length;

                     int var8;
                     for(var8 = 0; var8 < var7; ++var8) {
                        BaseComponent components = var6[var8];
                        component.addExtra(components);
                     }

                     TextComponent click = new TextComponent("AQUI");
                     click.setColor(ChatColor.GREEN);
                     click.setBold(true);
                     click.setClickEvent(new ClickEvent(Action.RUN_COMMAND, "skin:cancel"));
                     click.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, TextComponent.fromLegacyText("§7Clique aqui para cancelar a troca de skin.")));
                     component.addExtra(click);
                     BaseComponent[] var14 = TextComponent.fromLegacyText("§a para cancelar.\n ");
                     var8 = var14.length;

                     for(int var15 = 0; var15 < var8; ++var15) {
                        BaseComponent components = var14[var15];
                        component.addExtra(components);
                     }



                     this.player.spigot().sendMessage(component);
                  } else if (evt.getSlot() == 32) {
                     this.player.closeInventory();
                     this.player.chat("/skin atualizar");

                  } else if (evt.getSlot() == 40) {
                     EnumSound.ENDERMAN_TELEPORT.play(this.player, 0.5F, 2.0F);
                     new MenuProfile(profile);
                  } else if (evt.getSlot() == 30) {
                     this.player.closeInventory();
                     this.player.chat("/skin ajuda");
                  } else if (skinName != null) {
                     mc.twilight.core.database.data.container.SkinsContainer container = profile.getSkinsContainer();
                     mc.twilight.core.database.data.container.SkinsContainer listContainer = profile.getSkinListContainer();
                     if (evt.getClick().equals(ClickType.SHIFT_RIGHT) && !container.getSkin().equals(skinName)) {
                        listContainer.removeSkin(skinName);
                        this.player.sendMessage("§cSkin removida com sucesso.");
                        new MenuSkins(profile);
                     } else if (!container.getSkin().equals(skinName)) {
                        this.player.chat("/skin " + skinName);
                        container.setSkin(skinName);
                        this.player.closeInventory();
                     }
                  }
               }
            }
         }
      }

   }

   public void cancel() {
      HandlerList.unregisterAll(this);
      this.skins.clear();
      this.skins = null;
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
