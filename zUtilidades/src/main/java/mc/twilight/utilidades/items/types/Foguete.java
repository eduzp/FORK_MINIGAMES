package mc.twilight.utilidades.items.types;

import org.bukkit.Color;
import org.bukkit.FireworkEffect;
import org.bukkit.entity.Firework;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.FireworkMeta;
import mc.twilight.utilidades.Main;
import mc.twilight.utilidades.items.object.AbstractItem;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Foguete extends AbstractItem {

    public Foguete() {
        super("Foguete");

        this.register(Main.getInstance());
    }

    @EventHandler
    public void onPlayerInteract(PlayerInteractEvent evt) {
        Player player = evt.getPlayer();
        ItemStack item = player.getItemInHand();
        if (item.hasItemMeta() && item.getItemMeta().hasDisplayName()) {
            String display = item.getItemMeta().getDisplayName();
            if (display.equalsIgnoreCase("§aFoguete I")) {
                evt.setCancelled(true);
                Firework(player, "45000");
            } else if (display.equalsIgnoreCase("§aFoguete II")) {
                evt.setCancelled(true);
                Firework(player, "35000");
            } else if (display.equalsIgnoreCase("§aFoguete III")) {
                evt.setCancelled(true);
                Firework(player, "25000");
            }
        }
    }

    private static final DecimalFormat df = new DecimalFormat("###.#");
    public static ArrayList<Player> antdamage = new ArrayList<>();
    private static Map<String, Long> firework = new HashMap<>();

    public void Firework(Player player, String delay) {
        long start =
                firework.containsKey(player.getName()) ? firework.get(player.getName()) : 0;
        if (start > System.currentTimeMillis()) {
            double time = (start - System.currentTimeMillis()) / 1000.0;
            if (time > 0.1) {
                String timeString = df.format(time).replace(",", ".");
                if (timeString.endsWith("0")) {
                    timeString = timeString.substring(0, timeString.lastIndexOf("."));
                }

                player.sendMessage("§cAguarde mais " + timeString + "§cs §cpara utilizar o foguete novamente.");
                return;
            }
        }
        Firework f1 =
                (Firework) player.getWorld().spawn(player.getLocation(), Firework.class);
        FireworkMeta fm1 = f1.getFireworkMeta();
        fm1.addEffect(FireworkEffect.builder().withColor(Color.fromRGB(94, 245, 81))
                .flicker(true).withFade(Color.NAVY).with(FireworkEffect.Type.CREEPER).build());
        fm1.setPower(1);
        f1.setFireworkMeta(fm1);
        Firework f2 =
                (Firework) player.getWorld().spawn(player.getLocation(), Firework.class);
        FireworkMeta fm2 = f2.getFireworkMeta();
        fm2.addEffect(FireworkEffect.builder().withColor(Color.ORANGE).withColor(Color.LIME)
                .withColor(Color.TEAL).flicker(true).withFade(Color.BLUE)
                .with(FireworkEffect.Type.BURST).build());
        fm2.setPower(1);
        f2.setFireworkMeta(fm2);
        Firework f21 =
                (Firework) player.getWorld().spawn(player.getLocation(), Firework.class);
        FireworkMeta fm21 = f21.getFireworkMeta();
        fm21.addEffect(FireworkEffect.builder().withColor(Color.FUCHSIA)
                .withColor(Color.OLIVE).withColor(Color.MAROON).flicker(true)
                .withFade(Color.RED).with(FireworkEffect.Type.STAR).build());
        fm21.setPower(1);
        f21.setFireworkMeta(fm21);
        try {
            Thread.sleep(85L);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        antdamage.add(player);
        firework.put(player.getName(), System.currentTimeMillis() + Integer.parseInt(delay));
        Firework f =
                (Firework) player.getWorld().spawn(player.getLocation(), Firework.class);
        f.setPassenger(player);
        FireworkMeta fm = f.getFireworkMeta();
        fm.addEffect(FireworkEffect.builder().withColor(Color.AQUA).withColor(Color.RED)
                .withColor(Color.WHITE).flicker(true).withFade(Color.FUCHSIA)
                .with(FireworkEffect.Type.BALL_LARGE).build());
        fm.setPower(2);
        f.setFireworkMeta(fm);
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
