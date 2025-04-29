package mc.twilight.clans.cmd;

import mc.twilight.clans.api.ClanAPI;
import mc.twilight.clans.clan.Clan;
import org.bukkit.command.CommandSender;

public class ClanCoinsCommand extends Commands {
  public ClanCoinsCommand() {
    super("clancoins", new String[] { "ccoins" });
  }
  
  public void perform(CommandSender sender, String label, String[] args) {
    if (!sender.hasPermission("role.gerente")) {
      sender.sendMessage("§fComando desconhecido.");
      return;
    } 
    if (args.length <= 1) {
      sender.sendMessage("\n§3/clancoins dar [CLAN] [quantia]\n§3/clancoins remover [CLAN] [quantia]\n");
      return;
    } 
    Clan clan = Clan.getByTag(args[1]);
    if (clan == null) {
      sender.sendMessage("§cEste clan não existe ou não foi encontrado.");
      return;
    } 
    String action = args[0];
    if (action.equalsIgnoreCase("dar")) {
      if (args.length < 3) {
        sender.sendMessage("§cUtilize /clancoins dar [CLAN] [quantia]");
        return;
      } 
      try {
        int coins = Integer.parseInt(args[2]);
        if (coins < 1)
          throw new Exception(); 
        ClanAPI.addCoins(clan, coins);
        sender.sendMessage("§aCoins adicionados.");
      } catch (Exception ex) {
        sender.sendMessage("§cOu você está usando numero inválidos ou este usuario não possui um clan.");
      } 
    } 
    if (action.equalsIgnoreCase("remover")) {
      if (args.length < 3) {
        sender.sendMessage("§cUtilize /clancoins remover [CLAN] [quantia]");
        return;
      } 
      try {
        int coins = Integer.parseInt(args[2]);
        if (coins < 1)
          throw new Exception(); 
        ClanAPI.removeCoins(clan, coins);
        sender.sendMessage("§aCoins removidos.");
      } catch (Exception ex) {
        sender.sendMessage("§cOu você está usando numero inválidos ou este usuario não possui um clan.");
      } 
    } 
  }
}
