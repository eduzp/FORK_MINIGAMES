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

public class SuperPulo extends AbstractItem {

    public SuperPulo() {
        super("Super Pulo");

        this.register(Main.getInstance());
    }

    @EventHandler
    public void onPlayerInteract(PlayerInteractEvent evt) {
        Player player = evt.getPlayer();
        ItemStack item = player.getItemInHand();
        if (item.hasItemMeta() && item.getItemMeta().hasDisplayName()) {
            String display = item.getItemMeta().getDisplayName();
            if (display.equalsIgnoreCase("§aSuper Pulo")) {
                evt.setCancelled(true);
                player.addPotionEffect(new PotionEffect(PotionEffectType.JUMP,700, 3));
                player.addPotionEffect(new PotionEffect(PotionEffectType.SPEED,700, 3));
                BukkitUtils.removeItem(player.getInventory(), item.getType(), 1);
                EnumSound.ENDERMAN_TELEPORT.play(player, 1.0F, 1.0F);
            }
        }
    }

}
