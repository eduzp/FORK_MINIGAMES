package mc.twilight.skywars.cosmetics.types;

import mc.twilight.core.cash.CashManager;
import mc.twilight.core.game.GameState;
import mc.twilight.core.libraries.npclib.api.npc.NPC;
import mc.twilight.core.player.Profile;
import mc.twilight.core.player.role.Role;
import mc.twilight.core.plugin.config.KConfig;
import mc.twilight.core.plugin.logger.KLogger;
import mc.twilight.skywars.Language;
import mc.twilight.skywars.Main;
import mc.twilight.skywars.container.SelectedContainer;
import mc.twilight.skywars.cosmetics.Cosmetic;
import mc.twilight.skywars.cosmetics.CosmeticType;
import mc.twilight.skywars.game.AbstractSkyWars;
import mc.twilight.core.utils.BukkitUtils;
import mc.twilight.core.utils.StringUtils;
import mc.twilight.core.utils.enums.EnumRarity;
import mc.twilight.core.utils.particles.ParticleEffect;
import net.minecraft.server.v1_8_R3.EntityFishingHook;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.entity.Projectile;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.ProjectileHitEvent;
import org.bukkit.event.entity.ProjectileLaunchEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class ProjectileEffect extends Cosmetic implements Listener {

  public static final KLogger LOGGER = ((KLogger) Main.getInstance().getLogger()).getModule("PROJECTILE_EFFECT");
  private final ArrayList<Projectile> projectiles = new ArrayList<>();
  private final String name;
  private final String icon;
  private final ParticleEffect particle;

  public ProjectileEffect(long id, EnumRarity rarity, double coins, long cash, String permission, String name, String icon, ParticleEffect particle) {
    super(id, CosmeticType.PROJECTILE_EFFECT, coins, permission);
    this.name = name;
    this.icon = icon;
    this.particle = particle;
    this.rarity = rarity;
    this.cash = cash;

    Bukkit.getPluginManager().registerEvents(this, Main.getInstance());
  }

  public static void setupProjectileEffects() {
    KConfig config = Main.getInstance().getConfig("loja/animações", "projectileeffects");

    for (String key : config.getKeys(false)) {
      long id = config.getInt(key + ".id");
      double coins = config.getDouble(key + ".coins");
      if (!config.contains(key + ".cash")) {
        config.set(key + ".cash", getAbsentProperty("projectileffects", key + ".cash"));
      }
      long cash = config.getInt(key + ".cash", 0);
      String permission = config.getString(key + ".permission");
      String name = config.getString(key + ".name");
      String icon = config.getString(key + ".icon");
      if (!config.contains(key + ".rarity")) {
        config.set(key + ".rarity", getAbsentProperty("projectileeffects", key + ".rarity"));
      }
      ParticleEffect particle;
      try {
        particle = ParticleEffect.valueOf(config.getString(key + ".particle"));
      } catch (Exception ex) {
        Bukkit.getScheduler().scheduleSyncDelayedTask(Main.getInstance(), () -> LOGGER.warning("A partícula \"" + config.getString(key + ".particle") + "\" nao foi encontrada."));
        continue;
      }

      new ProjectileEffect(id, EnumRarity.fromName(config.getString(key + ".rarity")), coins, cash, permission, name, icon, particle);
    }
  }

  @Override
  public String getName() {
    return this.name;
  }

  public void preview(Player viewer, Location location, Entity entity) {
    projectiles.add((Projectile) entity);
    new BukkitRunnable() {
      @Override
      public void run() {
        if (projectiles.contains(entity)) {
          if (entity.getLocation().getBlock() != null) {
            this.cancel();
          }
          Location loc = entity.getLocation();
          loc.setPitch(location.getPitch());
          loc.setYaw(location.getYaw());
          if (viewer.getWorld() == loc.getWorld()) {
            getParticle().display(0.1F, 0.1F, 0.1F, 1.0F, 5, loc, viewer);
          }
        } else {
          this.cancel();
        }
      }
    }.runTaskTimer(Main.getInstance(), 0, 1);
  }

  @EventHandler
  public void onProjectileLaunch(ProjectileLaunchEvent evt) {
    if (!(evt.getEntity().getShooter() instanceof Player)) {
      return;
    }
    if (evt.getEntity().getShooter() instanceof NPC) {
      return;
    }
    if (evt.getEntity() instanceof EntityFishingHook) {
      return;
    }
    Player player = (Player) evt.getEntity().getShooter();
    Profile profile = Profile.getProfile(player.getName());
    if (profile != null) {
      AbstractSkyWars game = profile.getGame(AbstractSkyWars.class);
      if (game != null && game.getState() == GameState.EMJOGO && !game.isSpectator(player) && isSelected(profile) && canBuy(player) && has(profile)) {
        projectiles.add(evt.getEntity());
        new BukkitRunnable() {
          @Override
          public void run() {
            if (projectiles.contains(evt.getEntity())) {
              if (evt.getEntity().getLocation().getBlock() != null) {
                this.cancel();
              }
              Map<Player, Location> LOCATION_MAP = new HashMap<>();
              Bukkit.getOnlinePlayers().forEach(id -> LOCATION_MAP.put(id, id.getLocation()));
              Location loc = evt.getEntity().getLocation();
              for (Map.Entry<Player, Location> entry : LOCATION_MAP.entrySet()) {
                if (entry.getKey().isOnline()) {
                  if (entry.getValue().getWorld() == loc.getWorld() && entry.getValue().distanceSquared(loc) <= 16 * 16) {
                    getParticle().display(0.1F, 0.1F, 0.1F, 1.0F, 5, loc, entry.getKey());
                  }
                }
              }
            } else {
              this.cancel();
            }
          }
        }.runTaskTimer(Main.getInstance(), 0, 1);
      }
    }
  }

  @EventHandler
  public void onProjectileHit(ProjectileHitEvent evt) {
    if (projectiles.contains(evt.getEntity())) {
      evt.getEntity().remove();
      projectiles.remove(evt.getEntity());
    }
  }

  public ParticleEffect getParticle() {
    return this.particle;
  }

  @Override
  public EnumRarity getRarity() {
    return this.rarity;
  }

  @Override
  public ItemStack getIcon(Profile profile) {
    double coins = profile.getCoins("zCoreSkyWars");
    long cash = profile.getStats("zCoreProfile", "cash");
    boolean has = this.has(profile);
    boolean canBuy = this.canBuy(profile.getPlayer());
    boolean isSelected = this.isSelected(profile);
    if (isSelected && !canBuy) {
      isSelected = false;
      profile.getAbstractContainer("zCoreSkyWars", "selected", SelectedContainer.class).setSelected(getType(), 0);
    }

    Role role = Role.getRoleByPermission(this.getPermission());
    String color = has ?
        (isSelected ? Language.cosmetics$color$selected : Language.cosmetics$color$unlocked) :
        (coins >= this.getCoins() || (CashManager.CASH && cash >= this.getCash())) && canBuy ? Language.cosmetics$color$canbuy : Language.cosmetics$color$locked;
    String desc = (has && canBuy ?
        Language.cosmetics$projectile_effect$icon$has_desc$start.replace("{has_desc_status}", isSelected ? Language.cosmetics$icon$has_desc$selected : Language.cosmetics$icon$has_desc$select) :
        canBuy ?
            Language.cosmetics$projectile_effect$icon$buy_desc$start
                .replace("{buy_desc_status}", (coins >= this.getCoins() || (CashManager.CASH && cash >= this.getCash())) ? Language.cosmetics$icon$buy_desc$click_to_buy : Language.cosmetics$icon$buy_desc$enough) :
            Language.cosmetics$projectile_effect$icon$perm_desc$start
                .replace("{perm_desc_status}", (role == null ? Language.cosmetics$icon$perm_desc$common : Language.cosmetics$icon$perm_desc$role.replace("{role}", role.getName()))))
        .replace("{name}", this.name).replace("{rarity}", this.getRarity().getName()).replace("{coins}", StringUtils.formatNumber(this.getCoins())).replace("{cash}", StringUtils.formatNumber(this.getCash()));
    ItemStack item = BukkitUtils.deserializeItemStack(this.icon + " : nome>" + color + this.name + " : desc>" + desc);
    if (isSelected) {
      BukkitUtils.putGlowEnchantment(item);
    }

    return item;
  }
}
