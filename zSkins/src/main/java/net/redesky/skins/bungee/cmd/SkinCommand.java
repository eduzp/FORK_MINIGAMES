package net.redesky.skins.bungee.cmd;

import com.google.common.io.ByteArrayDataOutput;
import com.google.common.io.ByteStreams;
import javax.sql.rowset.CachedRowSet;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.redesky.skins.Language;
import net.redesky.skins.bungee.api.Group;
import net.redesky.skins.bungee.api.SkinMethods;
import net.redesky.skins.bungee.database.Database;
import net.redesky.skins.bungee.utils.JsonMessage;
import net.redesky.skins.plugin.CooldownStorage;
import net.redesky.skins.plugin.mojang.Mojang;
import net.redesky.skins.plugin.mojang.api.InvalidMojangException;

public class SkinCommand extends Commands {
   public SkinCommand() {
      super("skin");
   }

   public void execute(CommandSender sender, String[] args) {
      if (sender instanceof ProxiedPlayer) {
         ProxiedPlayer player = (ProxiedPlayer)sender;
         if (args.length == 0) {
            this.openInventory(player);
         } else {
            String arg = args[0];
            if (arg.equalsIgnoreCase("ajuda")) {
               player.sendMessage(TextComponent.fromLegacyText(Language.messages$command$skin$help));
            } else {
               String cooldown;
               if (arg.equalsIgnoreCase("atualizar")) {
                  cooldown = CooldownStorage.getCooldown(player.getName());
                  if (!player.hasPermission("RedeSky.bypass.cooldown") && !cooldown.isEmpty()) {
                     player.sendMessage(TextComponent.fromLegacyText(Language.messages$command$skin$cooldown.replace("{time}", cooldown)));
                     return;
                  }

                  player.sendMessage(TextComponent.fromLegacyText(Language.messages$command$skin$update));
                  CooldownStorage.reset(player.getName());
                  CooldownStorage.setCooldown(player.getName());
                  SkinMethods.setSlot(player.getUniqueId().toString(), 0);
                  SkinMethods.tryUpdateOriginalSkin(player.getName(), player.getUniqueId().toString());
                  player.sendMessage(TextComponent.fromLegacyText(Language.messages$command$skin$updated));
               } else {
                  if (!player.hasPermission("RedeSky.cmd.skin")) {
                     JsonMessage.send(player, Language.messages$command$skin$no_perm);
                     return;
                  }

                  cooldown = CooldownStorage.getCooldown(player.getName());
                  if (!player.hasPermission("RedeSky.bypass.cooldown") && !cooldown.isEmpty()) {
                     player.sendMessage(TextComponent.fromLegacyText(Language.messages$command$skin$cooldown.replace("{time}", cooldown)));
                     return;
                  }

                  if (arg.length() > 16) {
                     player.sendMessage(TextComponent.fromLegacyText(Language.messages$command$skin$length));
                     return;
                  }

                  player.sendMessage(TextComponent.fromLegacyText(Language.messages$command$skin$change.replace("{nick}", arg)));

                  try {
                     Integer slot = SkinMethods.hasSkin(player.getUniqueId().toString(), arg);
                     String id = Mojang.getUUID(arg);
                     if (id == null) {
                        player.sendMessage(TextComponent.fromLegacyText(Language.messages$command$skin$exception.replace("{nick}", arg)));
                        return;
                     }

                     String property = Mojang.getSkinProperty(id);
                     if (property == null) {
                        player.sendMessage(TextComponent.fromLegacyText(Language.messages$command$skin$exception.replace("{nick}", arg)));
                        return;
                     }

                     if (arg.equals(player.getName())) {
                        CooldownStorage.reset(player.getName());
                        CooldownStorage.setCooldown(player.getName());
                        SkinMethods.setSlot(player.getUniqueId().toString(), 0);
                        SkinMethods.tryUpdateOriginalSkin(player.getName(), player.getUniqueId().toString());
                        player.sendMessage(TextComponent.fromLegacyText(Language.messages$command$skin$changed));
                        return;
                     }

                     int currentId = SkinMethods.getCurrentId(player.getUniqueId().toString());
                     if (currentId > Group.getLimit(player) && slot == null) {
                        player.sendMessage(TextComponent.fromLegacyText(Language.messages$command$skin$limit));
                        return;
                     }

                     Integer current = SkinMethods.insertSkin(player.getUniqueId().toString(), arg, id, property, currentId);
                     SkinMethods.setSlot(player.getUniqueId().toString(), slot != null ? slot : (current != null ? current : currentId));
                     player.sendMessage(TextComponent.fromLegacyText(Language.messages$command$skin$changed));
                     CooldownStorage.reset(player.getName());
                     CooldownStorage.setCooldown(player.getName());
                  } catch (InvalidMojangException var11) {
                     player.sendMessage(TextComponent.fromLegacyText(Language.messages$command$skin$exception.replace("{nick}", arg)));
                     return;
                  }
               }
            }

         }
      }
   }

   private void openInventory(ProxiedPlayer player) {
      CachedRowSet rs = Database.getInstance().query("SELECT * FROM `playerskins` WHERE `owner` = ? AND `id` != ?", player.getUniqueId().toString(), 0);
      if (rs == null) {
         if (player.hasPermission("RedeSky.cmd.skin")) {
            ByteArrayDataOutput out = ByteStreams.newDataOutput();
            out.writeUTF("OpenEmptyMenu");

            try {
               out.writeUTF(player.getName());
               player.getServer().sendData("RedeSky", out.toByteArray());
            } catch (Exception var6) {
            }
         } else {
            JsonMessage.send(player, Language.messages$command$skin$no_perm);
         }

      } else {
         int currentSlot = SkinMethods.getSlot(player.getUniqueId().toString());
         ByteArrayDataOutput out = ByteStreams.newDataOutput();
         out.writeUTF("OpenMenu");

         try {
            rs.beforeFirst();
            out.writeUTF(player.getName());
            out.writeInt(currentSlot);

            while(rs.next()) {
               out.writeUTF(rs.getString("id") + " : " + rs.getString("name") + " : " + rs.getString("value") + " : " + rs.getLong("lastused"));
            }

            player.getServer().sendData("RedeSky", out.toByteArray());
         } catch (Exception var7) {
         }

      }
   }
}
