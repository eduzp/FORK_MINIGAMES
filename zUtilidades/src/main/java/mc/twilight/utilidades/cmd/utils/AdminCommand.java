package mc.twilight.utilidades.cmd.utils;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import mc.twilight.utilidades.Main;
import mc.twilight.utilidades.cmd.Commands;
import mc.twilight.core.libraries.npclib.NPCLibrary;
import mc.twilight.core.player.Profile;
import mc.twilight.core.player.role.Role;
import mc.twilight.core.utils.BukkitUtils;
import mc.twilight.core.utils.StringUtils;
import mc.twilight.core.utils.enums.EnumSound;

public class AdminCommand extends Commands implements Listener {

    public static void applyCage(Location location, boolean big) {
        runCage((x, y, z) -> location.clone().add(x, y, z).getBlock().setType(Material.GLASS), 4, big ? 2 : 1);
    }

    public static void destroyCage(Location location) {
        runCage((x, y, z) -> location.clone().add(x, y, z).getBlock().setType(Material.AIR), 4, 4, 2, 1);
    }

    private static void runCage(CageRunnable cageRunnable, double height, double width) {
        runCage(cageRunnable, height, height, width, width);
    }

    private static void runCage(CageRunnable cageRunnable, double height, double heightIndex, double width, double widthInside) {
        for (double y = 0; y <= height; y++) {
            for (double x = -width; x <= width; x++) {
                for (double z = -width; z <= width; z++) {
                    if (y > 0 && y < heightIndex) {
                        if ((x > -widthInside && x < widthInside) && (z > -widthInside && z < widthInside)) {
                            continue;
                        }
                    }

                    cageRunnable.run(x, y, z);
                }
            }
        }
    }

    private static interface CageRunnable {
        public void run(double x, double y, double z);
    }

    public AdminCommand() {
        super("admin");
    }

    @Override
    public void perform(CommandSender sender, String label, String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage("§cApenas jogadores podem executar este comando.");
            return;
        }
        Player player = (Player) sender;
        if (!player.hasPermission("zutilidades.cmd.admin")) {
            player.sendMessage("§cVocê não tem permissão para executar este comando.");
            return;
        }
        player.sendMessage("§eUtilize /lobbyitems para ter os itens do lobby novamente.");
        Profile.getProfile(player.getName()).setHotbar(null);
        player.getInventory().clear();
        player.getInventory().setItem(0, BukkitUtils.deserializeItemStack("STICK : 1 : encantar>KNOCKBACK:2 : nome>&aTestar KnockBack"));
        Bukkit.getPluginManager().registerEvents(this, Main.getInstance());
    }
/*
    @EventHandler
    public void onPlayerInteractEntity(PlayerInteractEntityEvent evt) {
        if (NPCLibrary.getNPC(evt.getRightClicked()) != null) {
            return;
        }
        if (evt.getRightClicked() instanceof Player) {
            Player player = evt.getPlayer();
            Player target = (Player) evt.getRightClicked();
            if (player.getItemInHand() != null && player.getItemInHand().hasItemMeta() && player.getItemInHand().getItemMeta().hasDisplayName() && player.getItemInHand().getItemMeta().getDisplayName().equalsIgnoreCase("§aJail")) {
                if (target != null) {
                    if (Main.cage.get(target) != null) {
                        destroyCage(Main.cage.get(target));
                        Main.cage.remove(target);
                    } else {
                        applyCage(target.getLocation(), false);
                        Main.cage.put(target, target.getLocation());
                        target.teleport(Main.cage.get(target));
                    }
                }
            }
        }
    }

 */

}
