package net.redesky.skins.bukkit.cmd;

import com.mojang.authlib.GameProfile;
import com.mojang.authlib.properties.Property;
import com.mojang.authlib.properties.PropertyMap;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.UUID;
import java.util.logging.Level;
import javax.sql.rowset.CachedRowSet;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import net.redesky.skins.Core;
import net.redesky.skins.Language;
import net.redesky.skins.bukkit.api.Group;
import net.redesky.skins.bukkit.api.SkinMethods;
import net.redesky.skins.bukkit.database.Database;
import net.redesky.skins.bukkit.utils.BukkitUtils;
import net.redesky.skins.bukkit.utils.JsonMessage;
import net.redesky.skins.plugin.CooldownStorage;
import net.redesky.skins.plugin.mojang.Mojang;
import net.redesky.skins.plugin.mojang.api.InvalidMojangException;

public class SkinCommand extends Commands {
   private int[] slots = new int[]{10, 11, 12, 13, 14, 15, 16, 19, 20, 21, 22, 23, 24, 25, 28, 29, 30, 31, 32, 33, 34, 37, 38, 39, 40, 41, 42, 43};

   public SkinCommand() {
      super("skin");
   }

   public boolean execute(CommandSender sender, String label, String[] args) {
      if (sender instanceof Player) {
         Player player = (Player)sender;
         if (args.length == 0) {
            this.openInventory(player);
            return true;
         } else {
            String arg = args[0];
            if (arg.equalsIgnoreCase("ajuda")) {
               player.sendMessage(Language.messages$command$skin$help);
            } else {
               String cooldown;
               if (arg.equalsIgnoreCase("atualizar")) {
                  if (!player.hasPermission("RedeSky.bypass.cooldown")) {
                     cooldown = CooldownStorage.getCooldown(player.getName());
                     if (!cooldown.isEmpty()) {
                        player.sendMessage(Language.messages$command$skin$cooldown.replace("{time}", cooldown));
                        return true;
                     }
                  }

                  player.sendMessage(Language.messages$command$skin$update);
                  CooldownStorage.reset(player.getName());
                  CooldownStorage.setCooldown(player.getName());
                  SkinMethods.setSlot(player.getUniqueId().toString(), 0);
                  SkinMethods.tryUpdateOriginalSkin(player.getName(), player.getUniqueId().toString());
                  player.sendMessage(Language.messages$command$skin$updated);
               } else {
                  if (!player.hasPermission("RedeSky.cmd.skin")) {
                     JsonMessage.send(player, Language.messages$command$skin$no_perm);
                     return true;
                  }

                  if (!player.hasPermission("RedeSky.bypass.cooldown")) {
                     cooldown = CooldownStorage.getCooldown(player.getName());
                     if (!cooldown.isEmpty()) {
                        player.sendMessage(Language.messages$command$skin$cooldown.replace("{time}", cooldown));
                        return true;
                     }
                  }

                  if (arg.length() > 16) {
                     player.sendMessage(Language.messages$command$skin$length);
                     return true;
                  }

                  player.sendMessage(Language.messages$command$skin$change.replace("{nick}", arg));

                  try {
                     Integer slot = SkinMethods.hasSkin(player.getUniqueId().toString(), arg);
                     String id = Mojang.getUUID(arg);
                     if (id == null) {
                        player.sendMessage(Language.messages$command$skin$exception.replace("{nick}", arg));
                        return true;
                     }

                     String property = Mojang.getSkinProperty(id);
                     if (property == null) {
                        player.sendMessage(Language.messages$command$skin$exception.replace("{nick}", arg));
                        return true;
                     }

                     if (arg.equals(player.getName())) {
                        CooldownStorage.reset(player.getName());
                        CooldownStorage.setCooldown(player.getName());
                        SkinMethods.setSlot(player.getUniqueId().toString(), 0);
                        SkinMethods.tryUpdateOriginalSkin(player.getName(), player.getUniqueId().toString());
                        player.sendMessage(Language.messages$command$skin$changed);
                        return true;
                     }

                     int currentId = SkinMethods.getCurrentId(player.getUniqueId().toString());
                     if (currentId > Group.getLimit(player) && slot == null) {
                        player.sendMessage(Language.messages$command$skin$limit);
                        return true;
                     }

                     SkinMethods.insertSkin(player.getUniqueId().toString(), arg, id, property, currentId);
                     SkinMethods.setSlot(player.getUniqueId().toString(), slot != null ? slot : currentId);
                     CooldownStorage.reset(player.getName());
                     CooldownStorage.setCooldown(player.getName());
                     player.sendMessage(Language.messages$command$skin$changed);
                  } catch (InvalidMojangException var11) {
                     player.sendMessage(Language.messages$command$skin$exception.replace("{nick}", arg));
                     return true;
                  }
               }
            }

            return true;
         }
      } else {
         return false;
      }
   }

   private void openInventory(Player player) {
      CachedRowSet rs = Database.getInstance().query("SELECT * FROM `playerskins` WHERE `owner` = ? AND `id` != ?", player.getUniqueId().toString(), 0);
      if (rs == null) {
         if (player.hasPermission("RedeSky.cmd.skin")) {
            Inventory inv = Bukkit.createInventory((InventoryHolder)null, Language.menu$skin$rows * 9, Language.menu$skin$title);
            inv.setItem(Language.menu$skin$items$empty$slot, BukkitUtils.deserializeItemStack(Language.menu$skin$items$empty$icon));
            inv.setItem(Language.menu$skin$items$setskin$slot, BukkitUtils.deserializeItemStack(Language.menu$skin$items$setskin$icon));
            inv.setItem(Language.menu$skin$items$update$slot, BukkitUtils.deserializeItemStack(Language.menu$skin$items$update$icon));
            inv.setItem(Language.menu$skin$items$help$slot, BukkitUtils.deserializeItemStack(Language.menu$skin$items$help$icon));
            player.openInventory(inv);
         } else {
            JsonMessage.send(player, Language.messages$command$skin$no_perm);
         }

      } else {
         int slot = 0;
         int currentId = SkinMethods.getSlot(player.getUniqueId().toString());
         Inventory inv = Bukkit.createInventory((InventoryHolder)null, Language.menu$skin$rows * 9, Language.menu$skin$title);

         try {
            rs.beforeFirst();

            while(rs.next()) {
               inv.setItem(this.slots[slot++], createSkull(currentId, rs.getString("id") + " : " + rs.getString("name") + " : " + rs.getString("value"), rs.getLong("lastused")));
            }
         } catch (SQLException var7) {
            Core.LOGGER.log(Level.WARNING, " [SkinCommand] openInventory(): ", var7);
         }

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
