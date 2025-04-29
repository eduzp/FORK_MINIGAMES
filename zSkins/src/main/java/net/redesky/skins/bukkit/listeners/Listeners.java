package net.redesky.skins.bukkit.listeners;

import com.mojang.authlib.GameProfile;
import com.mojang.authlib.properties.Property;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;
import org.bukkit.event.player.PlayerLoginEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import net.redesky.skins.Language;
import net.redesky.skins.bukkit.Main;
import net.redesky.skins.bukkit.api.SkinMethods;
import net.redesky.skins.bukkit.api.Sound;
import net.redesky.skins.bukkit.utils.JsonMessage;
import net.redesky.skins.plugin.CooldownStorage;
import net.redesky.skins.plugin.reflection.Accessors;

public class Listeners implements Listener {
   private static List<UUID> nicking = new ArrayList();

   public static void makeListeners() {
      Bukkit.getPluginManager().registerEvents(new Listeners(), Main.getInstance());
   }

   public static void setNicking(Player player) {
      nicking.add(player.getUniqueId());
   }

   public static boolean isNicking(Player player) {
      return nicking.contains(player.getUniqueId());
   }

   @EventHandler(
      priority = EventPriority.MONITOR
   )
   public void onPlayerLogin(PlayerLoginEvent evt) {
      Player player = evt.getPlayer();
      Property textures = SkinMethods.getOrCreateSkinForPlayer(player.getName(), player.getUniqueId().toString());
      GameProfile profile = (GameProfile)Accessors.getMethod(player.getClass(), GameProfile.class, "getProfile").invoke(player);
      profile.getProperties().clear();
      profile.getProperties().put("textures", textures);
   }

   @EventHandler
   public void onPlayerQuit(PlayerQuitEvent evt) {
      nicking.remove(evt.getPlayer().getUniqueId());
   }

   @EventHandler
   public void onInventoryClick(InventoryClickEvent evt) {
      if (evt.getWhoClicked() instanceof Player) {
         Player player = (Player)evt.getWhoClicked();
         ItemStack item = evt.getCurrentItem();
         Inventory inv = evt.getInventory();
         if (inv.getTitle().equals(Language.menu$skin$title) && evt.getClickedInventory() != null && evt.getClickedInventory().equals(inv) && item != null && item.getType() != Material.AIR) {
            evt.setCancelled(true);
            if (evt.getSlot() == Language.menu$skin$items$setskin$slot) {
               player.closeInventory();
               if (!player.hasPermission("RedeSkySkins.bypass.cooldown")) {
                  String cooldown = CooldownStorage.getCooldown(player.getName());
                  if (!cooldown.isEmpty()) {
                     player.sendMessage(Language.messages$command$skin$cooldown.replace("{time}", cooldown));
                     return;
                  }
               }

               if (!player.hasPermission("RedeSkySkins.cmd.skin")) {
                  JsonMessage.send(player, Language.messages$command$skin$no_perm);
                  return;
               }

               setNicking(player);
               JsonMessage.send(player, Language.messages$command$skin$event);
            } else if (evt.getSlot() == Language.menu$skin$items$update$slot) {
               player.closeInventory();
               player.chat("/skin atualizar");
            } else if (evt.getSlot() == Language.menu$skin$items$help$slot) {
               player.closeInventory();
               player.chat("/skin ajuda");
            } else if (item.getType() == Material.SKULL_ITEM) {
               int currentId = SkinMethods.getSlot(player.getUniqueId().toString());
               int id = evt.getSlot() > 18 ? evt.getSlot() - 11 : (evt.getSlot() > 27 ? evt.getSlot() - 13 : (evt.getSlot() > 36 ? evt.getSlot() - 15 : evt.getSlot() - 9));
               if (evt.getClick() == ClickType.SHIFT_RIGHT) {
                  if (id == currentId) {
                     return;
                  }

                  Sound.BURP.play(player, 1.0F, 1.0F);
                  SkinMethods.removeSkin(id, player.getUniqueId().toString());
                  player.closeInventory();
                  player.sendMessage(Language.messages$command$skin$removed);
               } else {
                  if (id == currentId) {
                     return;
                  }

                  Sound.NOTE_PIANO.play(player, 1.0F, 1.0F);
                  player.closeInventory();
                  SkinMethods.setSlot(player.getUniqueId().toString(), id);
                  player.sendMessage(Language.messages$command$skin$changed);
               }
            }
         }
      }

   }

   @EventHandler(
      priority = EventPriority.MONITOR
   )
   public void AsyncPlayerChat(AsyncPlayerChatEvent evt) {
      Player player = evt.getPlayer();
      if (isNicking(player)) {
         evt.setCancelled(true);
         nicking.remove(player.getUniqueId());
         if (evt.getMessage().equals("event.cancel_skin")) {
            player.sendMessage(Language.messages$command$skin$event_cancelled);
         } else {
            player.chat("/skin " + evt.getMessage());
         }
      } else {
         if (evt.getMessage().equals("event.cancel_skin")) {
            evt.setCancelled(true);
         }

      }
   }

   @EventHandler(
      priority = EventPriority.MONITOR
   )
   public void onPlayerCommandPreprocess(PlayerCommandPreprocessEvent evt) {
      Player player = evt.getPlayer();
      if (isNicking(evt.getPlayer())) {
         evt.setCancelled(true);
         JsonMessage.send(player, Language.messages$command$skin$event);
      }

   }
}
