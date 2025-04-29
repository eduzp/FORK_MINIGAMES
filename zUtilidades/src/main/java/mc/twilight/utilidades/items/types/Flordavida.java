package mc.twilight.utilidades.items.types;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import mc.twilight.utilidades.Main;
import mc.twilight.utilidades.items.object.AbstractItem;
import mc.twilight.utilidades.utils.BukkitUtils;
import mc.twilight.core.utils.enums.EnumSound;

public class Flordavida extends AbstractItem {

    public Flordavida() {
        super("Flor da vida");

        this.register(Main.getInstance());
    }

    @EventHandler
    public void onPlayerInteract(PlayerInteractEvent evt) {
        Player player = evt.getPlayer();
        ItemStack item = player.getItemInHand();
        if (item.hasItemMeta() && item.getItemMeta().hasDisplayName()) {
            String display = item.getItemMeta().getDisplayName();
            if (display.equalsIgnoreCase("§aFlor da Vida")) {
                evt.setCancelled(true);
                if (player.getHealth() + 4 > 32) {
                    return;
                }
                if (player.getHealth() != 15 && !String.valueOf(player.getHealth()).contains("-")) {
                    player.addPotionEffect(new PotionEffect(PotionEffectType.HEALTH_BOOST, 1200, 2));
                    player.addPotionEffect(new PotionEffect(PotionEffectType.SATURATION, 3000, 250));
                    player.setHealth(player.getHealth() + 4);
                    BukkitUtils.removeItem(player.getInventory(), item.getType(), 1);
                    EnumSound.ORB_PICKUP.play(player, 1.0F, 1.0F);
                }
            }
        }
    }

}
