package mc.twilight.bedwars.nms.entity;

import com.google.common.base.Predicate;
import mc.twilight.bedwars.Main;
import mc.twilight.bedwars.game.BedWars;
import mc.twilight.bedwars.game.BedWarsTeam;
import mc.twilight.bedwars.nms.NMS;
import mc.twilight.core.game.GameState;
import mc.twilight.core.nms.v1_8_R3.entity.EntityNPCPlayer;
import mc.twilight.core.player.Profile;
import net.minecraft.server.v1_8_R3.*;
import org.bukkit.Bukkit;
import org.bukkit.craftbukkit.v1_8_R3.CraftWorld;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;

import javax.annotation.Nullable;
import java.text.DecimalFormat;


public class EntityTeamSilverfish extends EntitySilverfish implements Listener {
  
  private static final DecimalFormat DECIMAL_FORMAT_2 = new DecimalFormat("##.##");
  
  private BedWars game;
  private BedWarsTeam team;
  
  public EntityTeamSilverfish(BedWars arena, BedWarsTeam team) {
    super(((CraftWorld) arena.getWorld()).getHandle());
    
    this.game = arena;
    this.team = team;
    
    NMS.clearPathfinderGoal(this);
    this.goalSelector.a(1, new PathfinderGoalFloat(this));
    this.goalSelector.a(4, new PathfinderGoalMeleeAttack(this, EntityHuman.class, 1.0D, false));
    
    this.targetSelector.a(1, new PathfinderGoalHurtByTarget(this, false));
    this.targetSelector.a(2, new EntityTeamSilverfish.PathfinderGoalNearestTeamTarget<>(this, EntityInsentient.class, 10, false, true, IMonster.e, team));
    
    this.setCustomName(team.getNameColor() + "Time " + team.getName());
    this.setCustomNameVisible(true);
    Bukkit.getPluginManager().registerEvents(this, Main.getInstance());
  }
  
  
  @EventHandler
  public void onEntityDamageByEntity(EntityDamageByEntityEvent evt) {
    if (!(evt.getDamager() instanceof Player)) {
      return;
    }
    Player player = (Player) evt.getDamager();
    if (evt.getEntity().equals(this.getBukkitEntity()) && team.listPlayers().contains(player)) {
      evt.setCancelled(true);
    }
    if (evt.getEntity().equals(this.getBukkitEntity()) && game.isSpectator(player)) {
      evt.setCancelled(true);
    }
  }
  
  @Override
  public void t_() {
    if (ticksLived >= 20 * 50) {
      dead = true;
      return;
    }
    double offset = 20 * 50 - this.ticksLived;
    this.setCustomName(team.getNameColor() + "Time " + team.getRawName() + " §7- §e" + DECIMAL_FORMAT_2.format(offset / 20).split("\\.")[0]);
    this.ticksLived++;
    super.t_();
  }
  
  public BedWarsTeam getTeam() {
    return team;
  }
  
  public BedWars getGame() {
    return game;
  }
  
  static class PathfinderGoalNearestTeamTarget<T extends EntityLiving> extends PathfinderGoalNearestAttackableTarget<T> {
    public PathfinderGoalNearestTeamTarget(final EntityCreature entityCreature, Class<T> oclass, int i, boolean flag, boolean flag1, final Predicate<? super T> predicate, BedWarsTeam team) {
      super(entityCreature, oclass, i, flag, flag1, predicate);
      this.c = new Predicate<T>() {
        
        public boolean a(T tO) {
          if (predicate != null && !predicate.apply(tO)) {
            return false;
            
          } else if (tO instanceof EntityCreeper) {
            return false;
          } else if (tO instanceof EntityNPCPlayer) {
            return false;
          } else {
            if (tO instanceof EntityHuman) {
              double dO = PathfinderGoalNearestTeamTarget.this.f();
              
              if (tO.isSneaking()) {
                dO *= 0.800000011920929D;
              }
              if (tO.isInvisible()) {
                float f = ((EntityHuman) tO).bY();
                if (f < 0.1F) {
                  f = 0.1F;
                }
                dO *= (double) (0.7F * f);
              }
              if ((double) tO.g(entityCreature) > dO) {
                return false;
              }
            }
            
            
            if (tO instanceof Player) {
              Player player = (Player) tO;
              Profile profile = Profile.getProfile(player.getName());
              if (profile != null) {
                if (profile.getGame() != null) {
                  if (profile.getGame().getState() == GameState.EMJOGO) {
                    if (team != null) {
                      if (!profile.getGame().isSpectator(player)) {
                        if (profile.getGame().getTeam(player) != null) {
                          if (profile.getGame().getTeam(player) != team) {
                            return PathfinderGoalNearestTeamTarget.this.a(tO, false);
                          }
                        }
                      }
                    }
                  }
                }
              }
            }
            return false;
          }
        }
        
        @Override
        public boolean apply(@Nullable T t) {
          return a(t);
        }
      };
    }
  }
}