package mc.twilight.cosmeticos.cmd;

import mc.twilight.cosmeticos.Language;
import mc.twilight.cosmeticos.Main;
import mc.twilight.cosmeticos.cosmetics.Cosmetic;
import mc.twilight.cosmeticos.cosmetics.CosmeticType;
import mc.twilight.cosmeticos.hook.Users;
import mc.twilight.cosmeticos.hook.mysteryboxes.MysteryBoxesHook;
import mc.twilight.cosmeticos.hook.player.CUser;
import mc.twilight.core.utils.StringUtils;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class CosmeticsCommand extends Commands {
  
  protected CosmeticsCommand() {
    super("zcosmeticos", "zcs");
  }
  
  @Override
  public void perform(CommandSender sender, String label, String[] args) {
    if (!sender.hasPermission("zcosmeticos.cmd.kcs")) {
      sender.sendMessage("§6zCosmeticos §bv" + Main.getInstance().getDescription().getVersion() + " §7Feito por eduzp.");
      return;
    }
    
    if (args.length == 0) {
      this.sendHelp(sender);
      return;
    }
    
    String subCommand = args[0];
    if (sender instanceof Player && subCommand.equalsIgnoreCase("atualizar")) {
      Player player = (Player) sender;
      player.sendMessage("§aO plugin já se encontra em sua última versão.");
    } else if (subCommand.equalsIgnoreCase("mb")) {
      if (args.length == 1) {
        sender.sendMessage("§cUtilize /zcs mb sync");
        return;
      }
      
      String action = args[1];
      if (action.equalsIgnoreCase("sync")) {
        sender.sendMessage("§aSincronizando cosméticos...");
        MysteryBoxesHook.sync(sender);
      } else if ((!(sender instanceof Player)) && action.equalsIgnoreCase("give")) {
        if (args.length <= 3) {
          return;
        }
        
        CUser user = Users.getByName(args[2]);
        if (user == null) {
          return;
        }
        
        Cosmetic cosmetic = Cosmetic.findById(args[3]);
        if (user.hasCosmetic(cosmetic)) {
          user.getProfile().addCoins("zCoreTheBridge", Language.settings$coins$duplicated);
          user.getProfile().addCoins("zCoreSkyWars", Language.settings$coins$duplicated);
          user.getProfile().addCoins("zCoreBedWars", Language.settings$coins$duplicated);
          user.getProfile().addCoins("zCoreMurder", Language.settings$coins$duplicated);
          user.getProfile().addStats("kMysteryBox", 50, "mystery_frags");
          user.getPlayer().sendMessage(
              "§aVocê recebeu §6" + Language.settings$coins$duplicated + " Coins §apor já possuir " + cosmetic.getRarity().getTagged() + " " + cosmetic.getName() + "§a!");
        } else {
          user.addCosmetic(cosmetic);
          user.getPlayer().sendMessage("§aVocê recebeu " + cosmetic.getRarity().getTagged() + " " + cosmetic.getName() + " §aatravés de uma Cápsula Mágica!");
        }
      } else {
        sender.sendMessage("§cUtilize /zcs mb sync");
      }
    } else if (subCommand.equalsIgnoreCase("ids")) {
      CosmeticType type;
      try {
        if (args.length == 1) {
          throw new Exception();
        }
        type = CosmeticType.valueOf(args[1].toUpperCase());
      } catch (Exception ex) {
        sender.sendMessage("§cUtilize /zcs ids [" + StringUtils.join(CosmeticType.values(), "/") + "]");
        return;
      }
      
      StringBuilder sb = new StringBuilder(" \n§7Lista de IDs:\n");
      for (Cosmetic cosmetic : Cosmetic.listCosmetics(type)) {
        sb.append("§7ID: §f").append(cosmetic.getUniqueId()).append(" §f| §7Nome: §f").append(cosmetic.getName()).append("\n");
      }
      sb.append(" ");
      sender.sendMessage(sb.toString());
    } else if (subCommand.equalsIgnoreCase("give")) {
      Player target;
      CUser user;
      int id;
      CosmeticType type = null;
      try {
        if (args.length <= 3) {
          throw new Exception();
        }
        target = Bukkit.getPlayerExact(args[1]);
        if (target == null || (user = Users.getByName(target.getName())) == null) {
          sender.sendMessage("§cJogador não encontrado.");
          return;
        }
        type = CosmeticType.valueOf(args[2].toUpperCase());
        id = Integer.parseInt(args[3]);
      } catch (Exception ex) {
        sender.sendMessage("§cUtilize /zcs give [jogador] [" + StringUtils.join(CosmeticType.values(), "/") + " [id]");
        return;
      }
      
      int copy = id;
      Cosmetic cosmetic = Cosmetic.listCosmetics(type).stream().filter(c -> c.getUniqueId() == copy).findFirst().orElse(null);
      if (cosmetic != null) {
        if (user.hasCosmetic(cosmetic)) {
          sender.sendMessage("§aO Jogador já possui este cosmético.");
        } else {
          user.addCosmetic(cosmetic);
          sender.sendMessage("§aO cosmético §f\"" + cosmetic.getName() + "\" §afoi dado ao Jogador.");
        }
      } else {
        if (id == 0) {
          Cosmetic.listCosmetics(type).forEach(user::addCosmetic);
          sender.sendMessage("§aTodos os cosméticos desta categoria foram dados ao Jogador.");
        } else {
          sender.sendMessage("§cCosmético não encontrado.");
        }
      }
    }
  }
  
  private void sendHelp(CommandSender sender) {
    sender.sendMessage(" " + (sender instanceof Player ?
        "\n§6/zcs atualizar §f- §7Atualizar o zCosmeticos." :
        "") + "\n§6/zcs mb sync §f- §7Sincronizar cosméticos com o kMysteryBox.\n§6/zcs ids [tipo de cosmético] §f- §7Lista os IDs de cada cosmético da categoria.\n§6/zcs give [jogador] [tipo de cosmético] [id] §f- §7Autoriza um cosmético ao usuário.\n ");
  }
}
