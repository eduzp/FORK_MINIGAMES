package mc.twilight.caixas.cmd;

import mc.twilight.caixas.Main;
import mc.twilight.caixas.cmd.mb.*;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.*;
import java.util.stream.Collectors;

public class MysteryBoxCommand extends Commands {
  
  private final List<SubCommand> commands = new ArrayList<>();
  
  public MysteryBoxCommand() {
    super("mb", "mysteryboxes");
    
    this.commands.add(new UpdateCommand());
    this.commands.add(new ReloadCommand());
    this.commands.add(new GiveCommand());
    this.commands.add(new SetLocationCommand());
    this.commands.add(new BoxNPCCommand());
  }
  
  @Override
  public void perform(CommandSender sender, String label, String[] args) {
    if (!sender.hasPermission("zcaixas.cmd.mysteryboxes")) {
      sender.sendMessage("§6zCaixases §bv" + Main.getInstance().getDescription().getVersion() + " §7Feito por eduzp.");
      return;
    }
    
    if (args.length == 0) {
      this.sendHelp(sender, 1);
      return;
    }
    
    try {
      this.sendHelp(sender, Integer.parseInt(args[0]));
    } catch (Exception ex) {
      SubCommand subCommand = this.commands.stream().filter(sc -> sc.getName().equalsIgnoreCase(args[0])).findFirst().orElse(null);
      if (subCommand == null) {
        this.sendHelp(sender, 1);
        return;
      }
      
      List<String> list = new ArrayList<>(Arrays.asList(args));
      list.remove(0);
      if (subCommand.onlyForPlayer()) {
        if (!(sender instanceof Player)) {
          sender.sendMessage("§cEsse comando pode ser utilizado apenas pelos jogadores.");
          return;
        }
        
        subCommand.perform((Player) sender, list.toArray(new String[0]));
      } else {
        subCommand.perform(sender, list.toArray(new String[0]));
      }
    }
  }
  
  private void sendHelp(CommandSender sender, int page) {
    List<SubCommand> commands = this.commands.stream().filter(subcommand -> sender instanceof Player || !subcommand.onlyForPlayer()).collect(Collectors.toList());
    Map<Integer, StringBuilder> pages = new HashMap<>();
    
    int pagesCount = (commands.size() + 6) / 7;
    for (int index = 0; index < commands.size(); index++) {
      int currentPage = (index + 7) / 7;
      if (!pages.containsKey(currentPage)) {
        pages.put(currentPage, new StringBuilder(" \n§eAjuda - " + currentPage + "/" + pagesCount + "\n \n"));
      }
      
      pages.get(currentPage).append("§6/mb ").append(commands.get(index).getUsage()).append(" §f- §7").append(commands.get(index).getDescription()).append("\n");
    }
    
    StringBuilder sb = pages.get(page);
    if (sb == null) {
      sender.sendMessage("§cPágina não encontrada.");
      return;
    }
    
    sb.append(" ");
    sender.sendMessage(sb.toString());
  }
}
