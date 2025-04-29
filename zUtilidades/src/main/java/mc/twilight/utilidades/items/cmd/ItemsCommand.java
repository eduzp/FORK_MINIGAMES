package mc.twilight.utilidades.items.cmd;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import mc.twilight.utilidades.cmd.Commands;
import mc.twilight.core.player.Profile;
import mc.twilight.core.utils.BukkitUtils;

public class ItemsCommand extends Commands {

    public ItemsCommand() {
        super("items");
    }

    @Override
    public void perform(CommandSender sender, String label, String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage("§cApenas jogadores podem utilizar este comando.");
            return;
        }
        Player player = (Player) sender;
        if (!player.hasPermission("zutilidades.cmd.items")) {
            player.sendMessage("§cVocê não tem permissão para executar este comando.");
            return;
        }
        Profile.getProfile(player.getName()).setHotbar(null);
        player.sendMessage("§eUtilize /lobbyitems para ter os itens do lobby novamente.");
        player.getInventory().clear();
        player.getInventory().setItem(0, BukkitUtils.deserializeItemStack("FIREWORK : 64 : nome>&aLançador"));
        player.getInventory().setItem(1, BukkitUtils.deserializeItemStack("38 : 64 : nome>&aFlor da Vida"));
        player.getInventory().setItem(2, BukkitUtils.deserializeItemStack("373:8203 : 64 : nome>&aSuper Pulo"));
        player.getInventory().setItem(3, BukkitUtils.deserializeItemStack("TNT : 64 : nome>&aTnt"));
        player.getInventory().setItem(4, BukkitUtils.deserializeItemStack("FIREWORK : 1 : nome>&aFoguete I"));
        player.getInventory().setItem(5, BukkitUtils.deserializeItemStack("FIREWORK : 1 : nome>&aFoguete II"));
        player.getInventory().setItem(6, BukkitUtils.deserializeItemStack("FIREWORK : 1 : nome>&aFoguete III"));
    }
}
