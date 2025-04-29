package mc.twilight.utilidades.cmd.utils;

import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftPlayer;
import org.bukkit.entity.Player;
import mc.twilight.utilidades.cmd.Commands;

public class PingCommand extends Commands {

    public PingCommand() {
        super("ping");
    }

    @Override
    public void perform(CommandSender sender, String label, String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage("§cApenas jogadores podem executar este comando.");
            return;
        }
        Player player = (Player) sender;
        if (args.length == 0) {
            player.sendMessage("§aSeu ping é de " + ((CraftPlayer) player).getHandle().ping + "§ams!");
            return;
        }
        Player target = Bukkit.getPlayerExact(args[0]);
        if (target == null || !target.isOnline()) {
            player.sendMessage("§cUsuário não encontrado.");
            return;
        }
        player.sendMessage("§aO ping de " + target.getName() + " é de " + ((CraftPlayer) target).getHandle().ping + "§ams!");
    }
}
