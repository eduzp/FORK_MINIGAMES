package mc.twilight.utilidades.cmd.utils;

import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.*;
import mc.twilight.utilidades.Language;
import mc.twilight.utilidades.cmd.Commands;


public class GamemodeCommand extends Commands {

    public GamemodeCommand() {
        super("gamemode", "gm");
    }

    @Override
    public void perform(CommandSender sender, String label, String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage("§cApenas jogadores podem utilizar este comando.");
            return;
        }

        Player player = (Player) sender;
        if (!player.hasPermission("zutilidades.cmd.gm") || !player.hasPermission("zutilidades.cmd.gamemode")) {
            player.sendMessage("§cVocê não possui permissão para utilizar este comando.");
            return;
        }

        if (args.length == 0) {
            player.sendMessage("§cUtilize /gamemode [modo] [player]");
            return;
        }

        if (args.length == 1) {
            if (args[0].equals("0") || args[0].equalsIgnoreCase("survival")) {
                player.setGameMode(GameMode.SURVIVAL);
                player.sendMessage(Language.gamemode$changed.replace("{mode}", "Sobrevivência"));
            } else if (args[0].equals("1") || args[0].equalsIgnoreCase("creative")) {
                player.setGameMode(GameMode.CREATIVE);
                player.sendMessage(Language.gamemode$changed.replace("{mode}", "Criativo"));
            } else if (args[0].equals("2") || args[0].equalsIgnoreCase("adventure")) {
                player.setGameMode(GameMode.SURVIVAL);
                player.sendMessage(Language.gamemode$changed.replace("{mode}", "Aventura"));
            } else if (args[0].equals("3") || args[0].equalsIgnoreCase("spectator")) {
                player.setGameMode(GameMode.SPECTATOR);
                player.sendMessage(Language.gamemode$changed.replace("{mode}", "Espectador"));
            } else {
                player.sendMessage("§cUtilize /gamemode [modo] [player]");
            }
        } else {
            Player target = Bukkit.getPlayerExact(args[1]);
            if (target == null || !target.isOnline()) {
                player.sendMessage("§cUsuário não encontrado.");
                return;
            }
            if (args[0].equals("0") || args[0].equalsIgnoreCase("survival")) {
                target.setGameMode(GameMode.SURVIVAL);
                target.sendMessage(Language.gamemode$changed.replace("{mode}", "Sobrevivência"));
                player.sendMessage(Language.gamemode$changed_target.replace("{player}", target.getName()).replace("{mode}", "Sobrevivência"));
            } else if (args[0].equals("1") || args[0].equalsIgnoreCase("creative")) {
                target.setGameMode(GameMode.CREATIVE);
                target.sendMessage(Language.gamemode$changed.replace("{mode}", "Criativo"));
                player.sendMessage(Language.gamemode$changed_target.replace("{player}", target.getName()).replace("{mode}", "Criativo"));
            } else if (args[0].equals("2") || args[0].equalsIgnoreCase("adventure")) {
                target.setGameMode(GameMode.SURVIVAL);
                target.sendMessage(Language.gamemode$changed.replace("{mode}", "Aventura"));
                player.sendMessage(Language.gamemode$changed_target.replace("{player}", target.getName()).replace("{mode}", "Aventura"));
            } else if (args[0].equals("3") || args[0].equalsIgnoreCase("spectator")) {
                target.setGameMode(GameMode.SPECTATOR);
                target.sendMessage(Language.gamemode$changed.replace("{mode}", "Espectador"));
                player.sendMessage(Language.gamemode$changed_target.replace("{player}", target.getName()).replace("{mode}", "Espectador"));
            } else {
                player.sendMessage("§cUtilize /gamemode [modo] [player]");
            }
        }
    }

}
