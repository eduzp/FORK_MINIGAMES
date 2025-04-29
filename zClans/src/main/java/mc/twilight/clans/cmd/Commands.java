package mc.twilight.clans.cmd;

import mc.twilight.clans.Main;
import java.util.Arrays;
import java.util.logging.Level;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.SimpleCommandMap;
import org.bukkit.entity.Player;

public abstract class Commands extends Command {
  public Commands(String name, String... aliases) {
    super(name);
    setAliases(Arrays.asList(aliases));
    try {
      SimpleCommandMap simpleCommandMap = (SimpleCommandMap)Bukkit.getServer().getClass().getDeclaredMethod("getCommandMap", new Class[0]).invoke(Bukkit.getServer(), new Object[0]);
      simpleCommandMap.register(getName(), "zclans", this);
    } catch (ReflectiveOperationException ex) {
      Main.getInstance().getLogger().log(Level.SEVERE, "Cannot register command: ", ex);
    } 
  }
  
  public static void setupCommands() {
    new ClanCommand();
    new ClanCoinsCommand();
    new Commands("zcl", new String[] { "zclans" }) {
        public void perform(CommandSender sender, String label, String[] args) {
          if (sender instanceof Player) {
            Player player = (Player)sender;
            if (!player.hasPermission("role.gerente")) {
              player.sendMessage("§fComando desconhecido.");
              return;
            } 
            if (args.length == 0) {
              player.sendMessage(" \n§6/zcl atualizar §f- §7Atualizar o zClans.\n ");
              return;
            } 
            String action = args[0];
            if (action.equalsIgnoreCase("atualizar")) {
              player.sendMessage("§aO plugin já se encontra em sua última versão.");
            } else {
              player.sendMessage(" \n§6/zcl atualizar §f- §7Atualizar o zClans.\n ");
            } 
          } else {
            sender.sendMessage("§cApenas jogadores podem utilizar este comando.");
          } 
        }
      };
  }
  
  public abstract void perform(CommandSender paramCommandSender, String paramString, String[] paramArrayOfString);
  
  public boolean execute(CommandSender sender, String commandLabel, String[] args) {
    perform(sender, commandLabel, args);
    return true;
  }
}
