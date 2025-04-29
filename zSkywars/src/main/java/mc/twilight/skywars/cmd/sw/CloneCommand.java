package mc.twilight.skywars.cmd.sw;

import mc.twilight.core.plugin.config.KConfig;
import mc.twilight.skywars.Main;
import mc.twilight.skywars.cmd.SubCommand;
import mc.twilight.skywars.game.AbstractSkyWars;
import org.bukkit.command.CommandSender;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

@SuppressWarnings("unchecked")
public class CloneCommand extends SubCommand {
  
  public CloneCommand() {
    super("clonar", "clonar [mundo] [novoMundo]", "Clonar uma sala.", false);
  }
  
  @Override
  public void perform(CommandSender sender, String[] args) {
    if (args.length <= 1) {
      sender.sendMessage("§cUtilize /sw " + this.getUsage());
      return;
    }
    
    AbstractSkyWars game = AbstractSkyWars.getByWorldName(args[0]);
    if (game == null) {
      sender.sendMessage("§cNão existe uma sala neste mundo.");
      return;
    }
    
    String newWorld = args[1];
    if (AbstractSkyWars.getByWorldName(newWorld) != null) {
      sender.sendMessage("§cJá existe uma sala no mundo \"" + newWorld + "\".");
      return;
    }
    
    sender.sendMessage("§aClonando sala...");
    KConfig config = Main.getInstance().getConfig("arenas", newWorld);
    for (String key : game.getConfig().getConfig().getKeys(false)) {
      Object value = game.getConfig().getConfig().get(key);
      if (value instanceof String) {
        value = ((String) value).replace(game.getGameName(), newWorld);
      } else if (value instanceof List) {
        List<String> list = new ArrayList<>();
        for (String v : (List<String>) value) {
          list.add(v.replace(game.getGameName(), newWorld));
        }
        value = list;
      }
      
      config.set(key, value);
    }
    
    Main.getInstance().getFileUtils().copyFiles(new File("plugins/zSkyWars/mundos", args[0]), new File("plugins/zSkyWars/mundos", newWorld));
    AbstractSkyWars.load(config.getFile(), () -> sender.sendMessage("§aSala foi clonada."));
  }
}
