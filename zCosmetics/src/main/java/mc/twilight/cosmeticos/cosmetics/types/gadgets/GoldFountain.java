package mc.twilight.cosmeticos.cosmetics.types.gadgets;

import mc.twilight.cosmeticos.Main;
import mc.twilight.cosmeticos.cosmetics.object.GadgetCooldown;
import mc.twilight.cosmeticos.cosmetics.types.GadgetsCosmetic;
import mc.twilight.cosmeticos.hook.player.CUser;
import mc.twilight.core.utils.BukkitUtils;
import mc.twilight.core.utils.enums.EnumRarity;
import mc.twilight.core.utils.enums.EnumSound;
import org.bukkit.entity.Item;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.ThreadLocalRandom;

public class GoldFountain extends GadgetsCosmetic {
  
  public GoldFountain() {
    super("Fonte Dourada", EnumRarity.EPICO, "GOLD_INGOT : 1 : nome>Fonte Dourada : desc>&7Que jeito melhor de exibir sua\n&7fortuna do que fazendo ela chover?",
        "GOLD_INGOT : 1 : nome>&6Engenhoca: &aFonte Dourada!");
  }
  
  @Override
  public void onClick(CUser user) {
    Player player = user.getPlayer();
    String cooldown = GadgetCooldown.getCooldown(player);
    if (!cooldown.isEmpty()) {
      player.sendMessage("§cAguarde " + cooldown + " para utilizar novamente uma Engenhoca!");
      return;
    }
    
    GadgetCooldown.setCooldown(player, 25);
    new BukkitRunnable() {
      private int time = 20;
      private final List<Item> items = new ArrayList<>();
      
      @Override
      public void run() {
        if (time == -30 || user.getProfile() == null || user.getProfile().playingGame()) {
          items.forEach(Item::remove);
          items.clear();
          cancel();
          return;
        }
        
        if (time > 0) {
          if (player.isOnline()) {
            int r = ThreadLocalRandom.current().nextInt(3);
            String material = r == 0 ? "GOLD_INGOT" : r == 1 ? "GOLD_BLOCK" : "GOLD_NUGGET";
            EnumSound.CHICKEN_EGG_POP.play(player.getWorld(), player.getLocation(), 1.0F, 1.0F);
            Item item =
                player.getWorld().dropItem(player.getLocation().clone().add(0.5, 1.7, 0.5), BukkitUtils.deserializeItemStack(material + " : 1 : nome>" + UUID.randomUUID()));
            item.setPickupDelay(999999999);
            items.add(item);
          }
        }
        
        --time;
      }
    }.runTaskTimer(Main.getInstance(), 0, 2);
  }
}
