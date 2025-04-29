package net.redesky.skins.bukkit.listeners;

import com.google.common.io.ByteArrayDataInput;
import com.google.common.io.ByteArrayDataOutput;
import com.google.common.io.ByteStreams;
import com.mojang.authlib.GameProfile;
import com.mojang.authlib.properties.Property;
import com.mojang.authlib.properties.PropertyMap;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.UUID;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.plugin.messaging.PluginMessageListener;
import net.redesky.skins.Language;
import net.redesky.skins.bukkit.Main;
import net.redesky.skins.bukkit.api.SkinMethods;
import net.redesky.skins.bukkit.api.Sound;
import net.redesky.skins.bukkit.utils.BukkitUtils;

public class BungeeSkinListener implements PluginMessageListener, Listener {
   private int[] slots = new int[]{10, 11, 12, 13, 14, 15, 16, 19, 20, 21, 22, 23, 24, 25, 28, 29, 30, 31, 32, 33, 34, 37, 38, 39, 40, 41, 42, 43};

   @EventHandler
   public void onInventoryClick(InventoryClickEvent evt) {
      if (evt.getWhoClicked() instanceof Player) {
         Player player = (Player)evt.getWhoClicked();
         ItemStack item = evt.getCurrentItem();
         Inventory inv = evt.getInventory();
         if (inv.getTitle().equals(Language.menu$skin$title) && evt.getClickedInventory() != null && evt.getClickedInventory().equals(inv) && item != null && item.getType() != Material.AIR) {
            evt.setCancelled(true);
            ByteArrayDataOutput out;
            if (evt.getSlot() == Language.menu$skin$items$setskin$slot) {
               player.closeInventory();
               out = ByteStreams.newDataOutput();
               out.writeUTF("SkinSet");
               player.sendPluginMessage(Main.getInstance(), "RedeSky", out.toByteArray());
            } else if (evt.getSlot() == Language.menu$skin$items$update$slot) {
               player.closeInventory();
               out = ByteStreams.newDataOutput();
               out.writeUTF("SkinUpdate");
               player.sendPluginMessage(Main.getInstance(), "RedeSky", out.toByteArray());
            } else if (evt.getSlot() == Language.menu$skin$items$help$slot) {
               player.closeInventory();
               out = ByteStreams.newDataOutput();
               out.writeUTF("SkinHelp");
               player.sendPluginMessage(Main.getInstance(), "RedeSky", out.toByteArray());
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

   public void onPluginMessageReceived(String channel, Player arg1, byte[] msg) {
      if (channel.equals("RedeSky")) {
         ByteArrayDataInput in = ByteStreams.newDataInput(msg);
         String subChannel = in.readUTF();
         Player player;
         if (subChannel.equalsIgnoreCase("OpenMenu")) {
            player = Bukkit.getPlayerExact(in.readUTF());
            if (player != null) {
               this.openMenu(player, in);
            }
         }

         if (subChannel.equalsIgnoreCase("OpenEmptyMenu")) {
            player = Bukkit.getPlayerExact(in.readUTF());
            if (player != null) {
               this.openEmptyMenu(player);
            }
         }
      }

   }

   private void openEmptyMenu(Player player) {
      Inventory inv = Bukkit.createInventory((InventoryHolder)null, Language.menu$skin$rows * 9, Language.menu$skin$title);
      inv.setItem(Language.menu$skin$items$empty$slot, BukkitUtils.deserializeItemStack(Language.menu$skin$items$empty$icon));
      inv.setItem(Language.menu$skin$items$setskin$slot, BukkitUtils.deserializeItemStack(Language.menu$skin$items$setskin$icon));
      inv.setItem(Language.menu$skin$items$update$slot, BukkitUtils.deserializeItemStack(Language.menu$skin$items$update$icon));
      inv.setItem(Language.menu$skin$items$help$slot, BukkitUtils.deserializeItemStack(Language.menu$skin$items$help$icon));
      player.openInventory(inv);
   }

   private void openMenu(Player player, ByteArrayDataInput in) {
      int slot = 0;
      int current = in.readInt();
      Inventory inv = Bukkit.createInventory((InventoryHolder)null, Language.menu$skin$rows * 9, Language.menu$skin$title);

      try {
         while(true) {
            String data = in.readUTF();
            inv.setItem(this.slots[slot++], createSkull(current, data, Long.valueOf(data.split(" : ")[3])));
         }
      } catch (Exception var7) {
         inv.setItem(Language.menu$skin$items$setskin$slot, BukkitUtils.deserializeItemStack(Language.menu$skin$items$setskin$icon));
         inv.setItem(Language.menu$skin$items$update$slot, BukkitUtils.deserializeItemStack(Language.menu$skin$items$update$icon));
         inv.setItem(Language.menu$skin$items$help$slot, BukkitUtils.deserializeItemStack(Language.menu$skin$items$help$icon));
         player.openInventory(inv);
      }
   }

   private static ItemStack createSkull(int current, String data, long last) {
      String id = data.split(" : ")[0];
      String name = data.split(" : ")[1];
      String value = data.split(" : ")[2];
      ItemStack head = new ItemStack(Material.SKULL_ITEM, 1, (short)3);
      ItemMeta meta = head.getItemMeta();
      meta.setDisplayName("§a" + name);
      meta.setLore(String.valueOf(current).equals(id) ? Arrays.asList(Language.menu$skin$items$description_using.replace("{tempo}", (new SimpleDateFormat("dd/MM/yyyy")).format(last)).split("\n")) : Arrays.asList(Language.menu$skin$items$description.replace("{tempo}", (new SimpleDateFormat("dd/MM/yyyy")).format(last)).split("\n")));
      head.setItemMeta(meta);
      GameProfile profile = new GameProfile(UUID.randomUUID(), (String)null);
      PropertyMap propertyMap = profile.getProperties();
      propertyMap.put("textures", new Property("textures", value));
      BukkitUtils.putProfileOnSkull((Object)profile, head);
      return head;
   }
}
