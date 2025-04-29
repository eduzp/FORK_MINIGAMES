package mc.twilight.bedwars.listeners.entity;

import mc.twilight.core.Core;
import mc.twilight.bedwars.Language;
import mc.twilight.bedwars.Main;
import mc.twilight.bedwars.game.BedWars;
import mc.twilight.bedwars.game.BedWarsTeam;
import mc.twilight.core.game.GameState;
import mc.twilight.core.libraries.npclib.NPCLibrary;
import mc.twilight.core.player.Profile;
import mc.twilight.core.player.enums.BloodAndGore;
import mc.twilight.core.player.role.Role;
import mc.twilight.core.utils.StringUtils;
import org.bukkit.*;
import org.bukkit.craftbukkit.v1_8_R3.CraftWorld;
import org.bukkit.entity.*;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.*;
import org.bukkit.event.entity.CreatureSpawnEvent.SpawnReason;
import org.bukkit.event.entity.EntityDamageEvent.DamageCause;
import org.bukkit.potion.PotionEffectType;

import java.util.ArrayList;
import java.util.List;

@SuppressWarnings("deprecation")
public class EntityListener implements Listener {
  
  public static final List<Player> PROTECTED_BY_KEYS = new ArrayList<>();
  
  public static final List<Entity> PROTECT_EVERY = new ArrayList<>();
  
  @EventHandler(priority = EventPriority.HIGH)
  public void onEntityDamageByEntity(EntityDamageByEntityEvent evt) {
    if (evt.isCancelled()) {
      return;
    }
    
    if (evt.getEntity() instanceof Player) {
      Player player = (Player) evt.getEntity();
      
      BedWars game = null;
      Profile profile = Profile.getProfile(player.getName());
      if (profile == null || (game = profile.getGame(BedWars.class)) == null || game.getState() != GameState.EMJOGO || game.isSpectator(player)) {
        evt.setCancelled(true);
        return;
      }
      
      BedWarsTeam team = game.getTeam(player);
      
      Player damager = null;
      Profile profile2 = null;
      if (evt.getDamager() instanceof Player) {
        damager = (Player) evt.getDamager();
        profile2 = Profile.getProfile(damager.getName());
        boolean visibility = player.hasPotionEffect(PotionEffectType.INVISIBILITY);
        if (visibility) {
          player.removePotionEffect(PotionEffectType.INVISIBILITY);
          profile.getGame(BedWars.class).updateTags();
        }
        if (profile2 == null || profile2.getGame() == null || !profile2.getGame().equals(game) || game.isSpectator(damager) || (team != null && team.hasMember(damager))
            || damager.equals(player)) {
          evt.setCancelled(true);
          return;
        }
        
        if (profile.getPreferencesContainer().getBloodAndGore() == BloodAndGore.ATIVADO) {
          player.playEffect(player.getLocation(), Effect.STEP_SOUND, Material.REDSTONE_BLOCK);
        }
        if (profile2.getPreferencesContainer().getBloodAndGore() == BloodAndGore.ATIVADO) {
          damager.playEffect(player.getLocation(), Effect.STEP_SOUND, Material.REDSTONE_BLOCK);
        }
      }
      
      if (evt.getDamager() instanceof Projectile) {
        Projectile proj = (Projectile) evt.getDamager();
        if (proj.hasMetadata("BEDWARS_FIREBALL")) {
          evt.setDamage(8.0);
        }
        
        if (proj.getShooter() instanceof Player) {
          damager = (Player) proj.getShooter();
          profile2 = Profile.getProfile(damager.getName());
          if (profile2 == null || profile2.getGame() == null || !profile2.getGame().equals(game) || game.isSpectator(damager)
              || (!damager.equals(player) && team != null && team.hasMember(damager))) {
            evt.setCancelled(true);
            return;
          }
          
          if (profile.getPreferencesContainer().getBloodAndGore() == BloodAndGore.ATIVADO) {
            player.playEffect(player.getLocation(), Effect.STEP_SOUND, Material.REDSTONE_BLOCK);
          }
          if (profile2.getPreferencesContainer().getBloodAndGore() == BloodAndGore.ATIVADO) {
            damager.playEffect(player.getLocation(), Effect.STEP_SOUND, Material.REDSTONE_BLOCK);
          }
          
          if (proj instanceof Arrow) {
            Player finalDamager = damager;
            Bukkit.getScheduler().scheduleSyncDelayedTask(Main.getInstance(), () -> finalDamager
                    .sendMessage(Language.ingame$messages$bow$hit.replace("{name}", Role.getColored(player.getName())).replace("{hp}", StringUtils.formatNumber(player.getHealth()))),
                5L);
          }
        }
      }
      
      if (damager != null) {
        profile.setHit(damager.getName());
      }
    } else if (evt.getEntity() instanceof Fireball && evt.getDamager() instanceof Player) {
      Player damager = (Player) evt.getDamager();
      Profile profile = Profile.getProfile(damager.getName());
      if (profile == null || profile.getGame() == null || profile.getGame().isSpectator(damager)) {
        evt.setCancelled(true);
      }
      
      damager = null;
      profile = null;
    }
  }
  
  @EventHandler
  public void onEntityDamage(EntityDamageEvent evt) {
    if (evt.getEntity() instanceof Player) {
      Player player = (Player) evt.getEntity();
      if (PROTECT_EVERY.contains(player) && NPCLibrary.isNPC(evt.getEntity())) {
        evt.setCancelled(true);
        evt.setDamage(0.0D);
        return;
      }
      if (evt.getCause().equals(DamageCause.ENTITY_EXPLOSION)) {
        evt.setCancelled(true);
        evt.setDamage(0.0D);
        return;
      }
    if (PROTECTED_BY_KEYS.contains(player)) {
        evt.setCancelled(true);
        evt.setDamage(0.0D);
        PROTECTED_BY_KEYS.remove(player);
        return;
      }
      
      Profile profile = Profile.getProfile(player.getName());
      if (profile != null) {
        BedWars game = profile.getGame(BedWars.class);
        if (game == null) {
          evt.setCancelled(true);
          if (evt.getCause() == DamageCause.VOID) {
            player.teleport(Core.getLobby());
          }
        } else {
          if (game.getState() == GameState.AGUARDANDO || game.getState() == GameState.ENCERRADO) {
            evt.setCancelled(true);
          } else if (game.isSpectator(player)) {
            evt.setCancelled(true);
          } else if (player.getNoDamageTicks() > 0 && evt.getCause() == DamageCause.FALL) {
            evt.setCancelled(true);
          } else if (evt.getCause() == DamageCause.VOID) {
            evt.setDamage(player.getMaxHealth());
          }
        }
      }
    }
  }
  
  @EventHandler
  public void onProjectileHit(ProjectileHitEvent evt) {
    Projectile proj = evt.getEntity();
    Location explosionLocation = proj.getLocation();
    if (proj.hasMetadata("BEDWARS_FIREBALL")) {
      if (explosionLocation != null) {
        boolean flag = ((CraftWorld) explosionLocation.getWorld()).getHandle().getGameRules().getBoolean("mobGriefing");
        explosionLocation.getWorld().createExplosion(explosionLocation.getX(), explosionLocation.getY() + 0.98 / 2.0F, explosionLocation.getZ(), 1.0F, flag);
      }
    }
  }
  
  @EventHandler
  public void onCreatureSpawn(CreatureSpawnEvent evt) {
    evt.setCancelled(evt.getSpawnReason() != SpawnReason.CUSTOM);
  }
  
  @EventHandler
  public void onItemSpawn(ItemSpawnEvent evt) {
    BedWars server = BedWars.getByWorldName(evt.getEntity().getWorld().getName());
    if (server == null || server.getState() != GameState.EMJOGO || evt.getEntity().getItemStack().getType().name().contains("BED")) {
      evt.setCancelled(true);
    }
  }
  
  @EventHandler
  public void onFoodLevelChange(FoodLevelChangeEvent evt) {
    evt.setCancelled(true);
  }
}