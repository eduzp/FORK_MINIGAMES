package mc.twilight.utilidades.items.types;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import mc.twilight.utilidades.Main;
import mc.twilight.utilidades.items.object.AbstractItem;
import mc.twilight.utilidades.utils.BukkitUtils;
import mc.twilight.core.utils.enums.EnumSound;

import java.util.ArrayList;

public class Lançador extends AbstractItem {

    public static ArrayList<Player> antdamage = new ArrayList<>();

    public Lançador() {
        super("Lançador");

        this.register(Main.getInstance());
    }

    @EventHandler
    public void onPlayerInteract(PlayerInteractEvent evt) {
        Player player = evt.getPlayer();
        ItemStack item = player.getItemInHand();
        if (item.hasItemMeta() && item.getItemMeta().hasDisplayName()) {
            String display = item.getItemMeta().getDisplayName();
            if (display.equalsIgnoreCase("§aLançador")) {
                evt.setCancelled(true);
                EnumSound.FIREWORK_LAUNCH.play(player, 1.0F, 1.0F);
                antdamage.add(player);
                BukkitUtils.removeItem(player.getInventory(), item.getType(), 1);
                player.setVelocity(player.getLocation().getDirection().multiply(3).setY(1));
            }
        }
    }

    @EventHandler
    public void onDamage(EntityDamageEvent evt) {
        if (evt.getEntity() instanceof Player) {
            Player player = (Player) evt.getEntity();
            if (evt.getCause().equals(EntityDamageEvent.DamageCause.FALL) && antdamage.contains(player)) {
                evt.setCancelled(true);
                antdamage.remove(player);
            }
        }
    }

}
