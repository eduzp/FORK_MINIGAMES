package mc.twilight.bedwars.cmd.bw;

import mc.twilight.bedwars.cmd.SubCommand;
import mc.twilight.bedwars.cosmetics.Cosmetic;
import mc.twilight.bedwars.hook.mb.MysteryBoxesHook;
import mc.twilight.core.player.Profile;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class MysteryBoxCommand extends SubCommand {
  
  public MysteryBoxCommand() {
    super("mb", "mb sync", "Sincronizar cosméticos com o kMysteryBox.", false);
  }
  
  @Override
  public void perform(CommandSender sender, String[] args) {
    if (args.length == 0) {
      sender.sendMessage("§cUtilize /bw " + this.getUsage());
      return;
    }
    
    String action = args[0];
    if (action.equalsIgnoreCase("sync")) {
      sender.sendMessage("§aSincronizando cosméticos...");
      MysteryBoxesHook.sync(sender);
    } else if ((!(sender instanceof Player)) && action.equalsIgnoreCase("give")) {
      if (args.length <= 2) {
        return;
      }
      
      Profile profile = Profile.getProfile(args[1]);
      if (profile == null) {
        return;
      }
      
      Cosmetic cosmetic = Cosmetic.findById(args[2]);
      if (cosmetic.has(profile)) {
        double coins = cosmetic.getCoins();
        if (coins <= 0) {
          coins = 100;
        }
        coins /= 10;
        if (coins < 100) {
          coins += 100;
        }
        profile.addStats("kMysteryBox", 50, "mystery_frags");
        profile.addCoins("zCoreBedWars", coins);
        profile.getPlayer().sendMessage("§aVocê recebeu §6" + coins + " Coins §apor já possuir " + cosmetic.getRarity().getTagged() + " " + cosmetic.getName() + "§a!");
      } else {
        cosmetic.give(profile);
        profile.getPlayer().sendMessage("§aVocê recebeu " + cosmetic.getRarity().getTagged() + " " + cosmetic.getName() + " §aatravés de uma Cápsula Mágica!");
      }
    } else {
      sender.sendMessage("§cUtilize /bw " + this.getUsage());
    }
  }
  
}