package mc.twilight.core.cmd;

import mc.twilight.core.menus.MenuSkins;
import mc.twilight.core.player.Profile;
import mc.twilight.core.utils.Validator;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.Map;

public class SkinCommand extends Commands {
   public static Map<String, String> LIBRARY = new HashMap();

   public SkinCommand() {
      super("skin", "skins");
   }

   public void perform(CommandSender sender, String label, String[] args) {
      if (!(sender instanceof Player)) {
         sender.sendMessage("§cApenas jogadores podem utilizar este comando.");
      } else {
         Player player = (Player)sender;
         Profile profile = Profile.getProfile(player.getName());
         if (args.length == 0) {
            new MenuSkins(profile);
         } else {
            String name = args[0];
            if (name.equalsIgnoreCase("ajuda")) {
               player.sendMessage("");
               player.sendMessage("§eAjuda");
               player.sendMessage("");
               player.sendMessage("§6/skin [nome] §f- §7Atualizar sua skin para a de um outro jogador.");
               player.sendMessage("§6/skin atualizar §f- §7Retornar para a skin da sua conta do Minecraft.");
               player.sendMessage("");
            } else if (name.equalsIgnoreCase("library")) {
               if (args.length < 2) {
                  player.sendMessage("§cUtilize /skin library [nome]");
               } else {
                  String librarySkin = args[1];
                  if (LIBRARY.containsKey(librarySkin)) {
                     player.sendMessage("§aAtualizando sua skin...");
                     if (profile.getSkinListContainer().getSkins().size() > 20) {
                        player.sendMessage("§cVocê atingiu o limite máximo de skins.");
                        return;
                     }

                     profile.getSkinListContainer().addSkin(LIBRARY.get(librarySkin));
                     player.sendMessage("§aSua skin foi atualizada, relogue para ela aparecer.");
                     profile.getSkinsContainer().setSkin(LIBRARY.get(librarySkin));
                  }

               }
            } else if (name.equalsIgnoreCase("atualizar")) {
               player.sendMessage("§aAtualizando sua skin...");
               if (profile.getSkinListContainer().getSkins().size() > 20) {
                  player.sendMessage("§cVocê atingiu o limite máximo de skins.");
               } else {
                  profile.getSkinListContainer().addSkin(player.getName());
                  player.sendMessage("§aSua skin foi atualizada, relogue para ela aparecer.");
                  profile.getSkinsContainer().setSkin(player.getName());
               }
            } else if (!player.hasPermission("skins.update")) {
               player.sendMessage("§cVocê precisa ter algum plano VIP para executar este comando.");
            } else if (!Validator.isValidUsername(name)) {
               player.sendMessage("§cUsuário inválido.");
            } else {
               player.sendMessage("§aAtualizando sua skin...");
               if (profile.getSkinListContainer().getSkins().size() > 20) {
                  player.sendMessage("§cVocê atingiu o limite máximo de skins.");
               } else {
                  profile.getSkinListContainer().addSkin(name);
                  player.sendMessage("§aSua skin foi atualizada, relogue para ela aparecer.");
                  profile.getSkinsContainer().setSkin(name);
               }
            }
         }
      }
   }

   static {
      LIBRARY.put("Tanjiro", "Tanjiro");
      LIBRARY.put("Kakashi", "Kakashi");
      LIBRARY.put("Obito", "Obito");
      LIBRARY.put("Luffy", "Luffy");
      LIBRARY.put("Sasuke", "Sasuke");
   }
}
