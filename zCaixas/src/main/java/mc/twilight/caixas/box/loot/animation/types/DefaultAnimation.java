package mc.twilight.caixas.box.loot.animation.types;

import mc.twilight.caixas.Main;
import mc.twilight.caixas.box.Box;
import mc.twilight.caixas.box.action.BoxContent;
import mc.twilight.caixas.box.loot.animation.BoxAnimation;
import mc.twilight.caixas.box.loot.LootMenu;
import mc.twilight.core.nms.interfaces.entity.IArmorStand;
import mc.twilight.core.utils.StringUtils;
import mc.twilight.core.utils.enums.EnumSound;
import mc.twilight.core.utils.particles.ParticleEffect;
import org.bukkit.Location;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitTask;
import org.bukkit.util.EulerAngle;

public class DefaultAnimation extends BoxAnimation {
  
  @Override
  public void start(LootMenu menu) {
    super.start(menu);
    this.getLootChest().setHeadPose(new EulerAngle(0, 180, 0));
  }
  
  @Override
  public BukkitTask create(boolean open, long amount) {
    return open ? openTask(amount) : menuTask();
  }
  
  @Override
  public void complete() {
    super.complete();
    getLootChest().teleport(getLootChestLocation().clone().subtract(0, 3.2, 0));
  }
  
  private BukkitTask menuTask() {
    getLootChest().teleport(getLootChestLocation().clone().add(0, 3.2, 0));
    return new BukkitRunnable() {
      private int ticks = 160, count = 0;
      
      @Override
      public void run() {
        if (this.ticks == 0) {
          DefaultAnimation.super.complete();
          getLootChest().teleport(getLootChestLocation().clone().subtract(0, .4, 0));
          return;
        }
        
        /*
         * if (ticks > 10) {
         *           if (ticks % 2 == 0) {
         *             EnumSound.ZOMBIE_DEATH.play(getPlayer(), 1.0F, 0.3F);
         *           }
         *
         *           ParticleEffect.SMOKE_LARGE.display(0.7F, 1.0F, 0.7F, 0.1f, 90, getLootChestLocation(), getPlayer());
         *         }
         */
        
        Location location = getLootChestLocation().clone();
        if (ticks >= 80) {
          if (ticks == 80) {
            ParticleEffect.ENCHANTMENT_TABLE.display(0.0f, 0.0f, 0.0f, 1.0f, 150, getLootChest().getEyeLocation().clone().add(0.0d, 1.0d, 0.0d), getPlayer());
            EnumSound.ZOMBIE_UNFECT.play(getPlayer(), location, 2.0F, 0.0F);
          }
          
          if (this.count == 4) {
            EnumSound.NOTE_BASS_GUITAR.play(getPlayer(), location, 2.0F, 0.7F);
          } else if (this.count == 8) {
            EnumSound.NOTE_BASS_GUITAR.play(getPlayer(), location, 2.0F, 0.2F);
            EnumSound.NOTE_STICKS.play(getPlayer(), location, 2.0F, 1.0F);
            this.count = 0;
          }
          
          this.count++;
        }
        
        if (ticks >= 105) {
          location.subtract(0, 0.04, 0);
          location.setYaw(location.getYaw() - 45.0F);
          getLootChest().teleport(location);
          ParticleEffect.FIREWORKS_SPARK.display(0.0f, 0.0f, 0.0f, 0.0f, 1, getLootChest().getEyeLocation(), getPlayer());
          ParticleEffect.REDSTONE.display(0.0f, 0.0f, 0.0f, 5.0f, 1, getLootChest().getEyeLocation(), getPlayer());
          EnumSound.NOTE_PIANO.play(getPlayer(), 0.5F, 2.0F);
        } else if (ticks == 40) {
          ParticleEffect.FLAME.display(0.0f, 0.0f, 0.0f, 1.0f, 90, getLootChest().getEyeLocation(), getPlayer());
          ParticleEffect.SMOKE_LARGE.display(0.0f, 0.0f, 0.0f, 0.7f, 90, getLootChest().getEyeLocation(), getPlayer());
          ParticleEffect.LAVA.display(0.0f, 0.0f, 0.0f, 1.0f, 25, getLootChest().getEyeLocation(), getPlayer());
          EnumSound.EXPLODE.play(getPlayer(), getLootChest().getEyeLocation(), 2.0f, 1.0f);
        } else if (ticks > 0 && ticks <= 20) {
          ParticleEffect.CRIT_MAGIC.display(0.3f, 0.3f, 0.3f, 0.0f, 2, getLootChest().getEyeLocation(), getPlayer());
          ParticleEffect.EXPLOSION_LARGE.display(1.2f, 0.5f, 1.2f, 0.0f, 1, getLootChest().getEyeLocation(), getPlayer());
          if (ticks == 20) {
            EnumSound.CHICKEN_EGG_POP.play(getPlayer(), getLootChest().getEyeLocation(), 2.0F, 0.0F);
            EnumSound.ORB_PICKUP.play(getPlayer(), getLootChest().getEyeLocation(), 1.0F, 1.0F);
          }
        }
        
        this.ticks--;
      }
    }.runTaskTimer(Main.getInstance(), 0, 1);
  }
  
  private BukkitTask openTask(long amount) {
    return new BukkitRunnable() {
      private int ticks = 160, count = 0;
      private BoxContent content;
      
      @Override
      public void run() {
        if (ticks == -40) {
          DefaultAnimation.this.cancel();
          Box chest = menu.getChest();
          menu.cancel();
          new LootMenu(player, chest, this.content);
          return;
        }
        
        Location location = getLootChestLocation().clone();
        if (ticks >= 80) {
          if (ticks == 80) {
            ParticleEffect.ENCHANTMENT_TABLE.display(0.0f, 0.0f, 0.0f, 1.0f, 150, getLootChest().getEyeLocation().clone().add(0.0d, 1.0d, 0.0d), getPlayer());
            EnumSound.ZOMBIE_UNFECT.play(getPlayer(), getLootChestLocation(), 2.0F, 0.0F);
          }
          
          if (this.count == 4) {
            EnumSound.NOTE_BASS_GUITAR.play(getPlayer(), getLootChestLocation(), 2.0F, 0.7F);
          } else if (this.count == 8) {
            EnumSound.NOTE_BASS_GUITAR.play(getPlayer(), getLootChestLocation(), 2.0F, 0.2F);
            EnumSound.NOTE_STICKS.play(getPlayer(), getLootChestLocation(), 2.0F, 1.0F);
            this.count = 0;
          }
          
          this.count++;
        }
        
        if (ticks >= 105) {
          location.add(0, 0.02, 0);
          location.setYaw(location.getYaw() + 45.0F);
          getLootChest().teleport(location);
          ParticleEffect.FIREWORKS_SPARK.display(0.0f, 0.0f, 0.0f, 0.0f, 1, getLootChest().getEyeLocation(), getPlayer());
          ParticleEffect.REDSTONE.display(0.0f, 0.0f, 0.0f, 5.0f, 1, getLootChest().getEyeLocation(), getPlayer());
          EnumSound.NOTE_PIANO.play(getPlayer(), 0.5F, 2.0F);
        } else if (ticks == 40) {
          ParticleEffect.FLAME.display(0.0f, 0.0f, 0.0f, 1.0f, 90, getLootChest().getEyeLocation(), getPlayer());
          ParticleEffect.SMOKE_LARGE.display(0.0f, 0.0f, 0.0f, 0.7f, 90, getLootChest().getEyeLocation(), getPlayer());
          ParticleEffect.LAVA.display(0.0f, 0.0f, 0.0f, 1.0f, 25, getLootChest().getEyeLocation(), getPlayer());
          EnumSound.EXPLODE.play(getPlayer(), getLootChest().getEyeLocation(), 2.0f, 1.0f);
          if (amount > 1) {
            this.ticks = -30;
            this.content = BoxContent.ALL;
          } else {
            this.content = menu.getChest().getRandomReward();
            if (this.content != null) {
              ((IArmorStand) getLootChest()).setName(StringUtils.formatColors(this.content.getName()));
            }
          }
        } else if (ticks > 0 && ticks <= 20) {
          ParticleEffect.CRIT_MAGIC.display(0.3f, 0.3f, 0.3f, 0.0f, 2, getLootChest().getEyeLocation(), getPlayer());
          ParticleEffect.EXPLOSION_LARGE.display(1.2f, 0.5f, 1.2f, 0.0f, 1, getLootChest().getEyeLocation(), getPlayer());
          if (ticks == 20) {
            EnumSound.CHICKEN_EGG_POP.play(getPlayer(), getLootChest().getEyeLocation(), 2.0F, 0.0F);
            EnumSound.ORB_PICKUP.play(getPlayer(), getLootChest().getEyeLocation(), 1.0F, 1.0F);
          }
        }
        
        this.ticks--;
      }
    }.runTaskTimer(Main.getInstance(), 0, 1);
  }
}