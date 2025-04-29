package mc.twilight.caixas.box.animation;

import mc.twilight.caixas.Main;
import mc.twilight.caixas.api.MysteryBoxAPI;
import mc.twilight.caixas.box.action.BoxContent;
import mc.twilight.caixas.box.interfaces.OpeningCallback;
import mc.twilight.caixas.cosmetics.CosmeticType;
import mc.twilight.caixas.cosmetics.types.Opener;
import mc.twilight.caixas.hook.container.MysteryBoxQueueContainer;
import mc.twilight.caixas.lobby.BoxNPC;
import mc.twilight.caixas.utils.PlayerUtils;
import mc.twilight.core.nms.NMS;
import mc.twilight.core.nms.interfaces.entity.IArmorStand;
import mc.twilight.core.player.Profile;
import mc.twilight.core.utils.BukkitUtils;
import mc.twilight.core.utils.StringUtils;
import mc.twilight.core.utils.enums.EnumRarity;
import mc.twilight.core.utils.enums.EnumSound;
import mc.twilight.core.utils.particles.ParticleEffect;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockState;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.ArrayList;
import java.util.List;

public class LootChestsOpener extends Opener {
  
  private static IArmorStand stand;
  
  public LootChestsOpener() {
    super(1, CosmeticType.OPENING_ANIMATION, "", "LootChest", "DIAMOND_SWORD : 1 : esconder>tudo", 75, EnumRarity.RARO);
  }
  
  @Override
  public void executeMultiples(IArmorStand capsule, Block block, Profile profile, Location location, OpeningCallback callback) {
    Location pos = capsule.getEntity().getLocation();
    Player player = profile.getPlayer();
    stand = NMS.createArmorStand(pos.clone().add(0, 2.2, 0), null, null);
    stand.getEntity().setHelmet(BukkitUtils.deserializeItemStack("SKULL_ITEM:3 : 1 : skin>eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvMWRlYmNmZTQxNDNhMTFlNjFkMzkzMTc5OWZiMzIyZTVhYTJhZTczMjc1YzUzYzJjNjc0MTYxNzhkMTQ5ZTE1MiJ9fX0="));
    stand.getEntity().setGravity(false);
    stand.getEntity().setSmall(false);
    Material oldItem = block.getType();
    Location oldBLoc = block.getLocation();
    block.setType(Material.AIR);
    EnumSound.GLASS.play(player.getWorld(), location, 1.0f, 1.0f);
  
    ItemStack oldHelmet = capsule.getEntity().getHelmet();
    capsule.getEntity().setHelmet(null);
    new BukkitRunnable() {
      private int time = 160;
      private int frame = 0;
      
      @Override
      public void run() {
        if (this.time == 0) {
          Profile user = Profile.getProfile(player.getName());
          List<BoxContent> rewards = new ArrayList<>();
          profile.addStats("zCaixas", -MysteryBoxAPI.getMysteryBoxes(profile), "magic");
          for (int i = 0; i < MysteryBoxAPI.getMysteryBoxes(profile); i++) {
            BoxContent cosmetic = BoxContent.getRandom();
            if (cosmetic == null) {
              return;
            }
            
            stand.setName(StringUtils.formatColors(cosmetic.getName()));
            if (cosmetic.canBeDispatched()) {
              cosmetic.dispatch(user);
            } else {
              user.getAbstractContainer("zCaixas", "queueRewards", MysteryBoxQueueContainer.class).addToQueue(cosmetic);
            }
            rewards.add(cosmetic);
          }
          PlayerUtils.createBook(player, rewards);
          
          Bukkit.getScheduler().scheduleSyncDelayedTask(Main.getInstance(), () -> {
            stand.getEntity().remove();
            oldBLoc.getBlock().setType(oldItem);
            BlockState state = oldBLoc.getBlock().getState();
            if (state.getType().equals(Material.STAINED_GLASS)) {
              state.getData().setData((byte) 2);
            }
            state.update(true);
            
            capsule.getEntity().setHelmet(oldHelmet);
            callback.finish();
            cancel();
          }, 33);
          this.time = -832329382;
        } else {
          Location location = stand.getEntity().getLocation().clone();
          if (this.time >= 80) {
            if (this.time == 80) {
              for (Player player : Bukkit.getOnlinePlayers()) {
                ParticleEffect.ENCHANTMENT_TABLE.display(0.0F, 0.0F, 0.0F, 1.0F, 150, location.clone().add(0.0D, 2.5D, 0.0D), player);
              }
              EnumSound.ZOMBIE_UNFECT.play(player.getWorld(), location, 2.0F, 0.0F);
            }
            
            if (this.frame == 4) {
              EnumSound.NOTE_BASS.play(player.getWorld(), location, 2.0F, 0.7F);
            } else if (this.frame == 8) {
              EnumSound.NOTE_BASS_GUITAR.play(player.getWorld(), location, 2.0F, 0.2F);
              EnumSound.NOTE_STICKS.play(player.getWorld(), location, 2.0F, 1.0F);
              this.frame = 0;
            }
            
            ++this.frame;
          }
          
          if (this.time >= 105) {
            location.subtract(0.0D, 0.05D, 0.0D);
            location.setYaw(location.getYaw() - 45.0F);
            stand.getEntity().teleport(location);
            
            for (Player player : Bukkit.getOnlinePlayers()) {
              ParticleEffect.FIREWORKS_SPARK.display(0.0F, 0.0F, 0.0F, 0.0F, 1, location.clone().add(0, 2.5, 0), player);
              ParticleEffect.REDSTONE.display(0.0F, 0.0F, 0.0F, 5.0F, 1, location.clone().add(0, 2.5, 0), player);
            }
            EnumSound.NOTE_PIANO.play(player.getWorld(), pos, 0.5F, 2.0F);
          } else if (this.time == 40) {
            for (Player player : Bukkit.getOnlinePlayers()) {
              ParticleEffect.FLAME.display(0.0F, 0.0F, 0.0F, 1.0F, 90, location.clone().add(0, 2.5, 0), player);
              ParticleEffect.SMOKE_LARGE.display(0.0F, 0.0F, 0.0F, 0.7F, 90, location.clone().add(0, 2.5, 0), player);
              ParticleEffect.LAVA.display(0.0F, 0.0F, 0.0F, 1.0F, 25, location.clone().add(0, .5, 0), player);
            }
            EnumSound.EXPLODE.play(pos.getWorld(), location, 2.0F, 1.0F);
          } else if (this.time > 0 && this.time <= 20) {
            for (Player player : Bukkit.getOnlinePlayers()) {
              ParticleEffect.CRIT_MAGIC.display(0.3F, 0.3F, 0.3F, 0.0F, 2, location.clone().add(0, 2.5, 0), player);
              ParticleEffect.EXPLOSION_LARGE.display(1.2F, 0.5F, 1.2F, 0.0F, 1, location.clone().add(0, 2.5, 0), player);
            }
            if (this.time == 20) {
              EnumSound.CHICKEN_EGG_POP.play(pos.getWorld(), location, 2.0F, 0.0F);
              EnumSound.ORB_PICKUP.play(pos.getWorld(), location, 1.0F, 1.0F);
            }
          }
          
          --this.time;
        }
      }
    }.runTaskTimer(Main.getInstance(), 0, 1);
  }
  
  @Override
  public void execute(BoxNPC npc, Block block, IArmorStand capsule, Profile profile, BoxContent cosmetic, Location location, OpeningCallback callback) {
    Location pos = capsule.getEntity().getLocation();
    Player player = profile.getPlayer();
    stand = NMS.createArmorStand(pos.clone().add(0, 2.2, 0), null, null);
    stand.getEntity().setHelmet(BukkitUtils.deserializeItemStack("SKULL_ITEM:3 : 1 : skin>eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvMWRlYmNmZTQxNDNhMTFlNjFkMzkzMTc5OWZiMzIyZTVhYTJhZTczMjc1YzUzYzJjNjc0MTYxNzhkMTQ5ZTE1MiJ9fX0="));
    stand.getEntity().setGravity(false);
    stand.getEntity().setSmall(false);
    Material oldItem = block.getType();
    Location oldBLoc = block.getLocation();
    block.setType(Material.AIR);
    EnumSound.GLASS.play(player.getWorld(), location, 1.0f, 1.0f);
  
    ItemStack oldHelmet = capsule.getEntity().getHelmet();
    capsule.getEntity().setHelmet(null);
    new BukkitRunnable() {
      private int time = 160;
      private int frame = 0;
      
      @Override
      public void run() {
        if (this.time == 0) {
          Profile user = Profile.getProfile(player.getName());
          profile.addStats("zCaixas", -1L, "magic");

          
          // Executar o comando do Cosmético.
          if (cosmetic.canBeDispatched()) {
            cosmetic.dispatch(user);
          } else {
            user.getAbstractContainer("zCaixas", "queueRewards", MysteryBoxQueueContainer.class).addToQueue(cosmetic);
          }
          
          stand.setName(StringUtils.formatColors(cosmetic.getName()));
          Bukkit.getScheduler().scheduleSyncDelayedTask(Main.getInstance(), () -> {
            //stand.getEntity().getLocation().getBlock().setType(Material.AIR);
            stand.getEntity().remove();
            oldBLoc.getBlock().setType(oldItem);
            BlockState state = oldBLoc.getBlock().getState();
            if (state.getType().equals(Material.STAINED_GLASS)) {
              state.getData().setData((byte) 2);
            }
            state.update(true);
            
            capsule.getEntity().setHelmet(oldHelmet);
            callback.finish();
            this.cancel();
          }, 33);
          this.time = -832329382;
        } else {
          Location location = stand.getEntity().getLocation().clone();
          if (this.time >= 80) {
            if (this.time == 80) {
              for (Player player : Bukkit.getOnlinePlayers()) {
                ParticleEffect.ENCHANTMENT_TABLE.display(0.0F, 0.0F, 0.0F, 1.0F, 150, location.clone().add(0.0D, 2.5D, 0.0D), player);
              }
              EnumSound.ZOMBIE_UNFECT.play(player.getWorld(), location, 2.0F, 0.0F);
            }
            
            if (this.frame == 4) {
              EnumSound.NOTE_BASS.play(player.getWorld(), location, 2.0F, 0.7F);
            } else if (this.frame == 8) {
              EnumSound.NOTE_BASS_GUITAR.play(player.getWorld(), location, 2.0F, 0.2F);
              EnumSound.NOTE_STICKS.play(player.getWorld(), location, 2.0F, 1.0F);
              this.frame = 0;
            }
            
            ++this.frame;
          }
          
          if (this.time >= 105) {
            location.subtract(0.0D, 0.05D, 0.0D);
            location.setYaw(location.getYaw() - 45.0F);
            stand.getEntity().teleport(location);
            for (Player player : Bukkit.getOnlinePlayers()) {
              ParticleEffect.FIREWORKS_SPARK.display(0.0F, 0.0F, 0.0F, 0.0F, 1, location.clone().add(0, 2.5, 0), player);
              ParticleEffect.REDSTONE.display(0.0F, 0.0F, 0.0F, 5.0F, 1, location.clone().add(0, 2.5, 0), player);
            }
            EnumSound.NOTE_PIANO.play(player.getWorld(), pos, 0.5F, 2.0F);
          } else if (this.time == 40) {
            for (Player player : Bukkit.getOnlinePlayers()) {
              ParticleEffect.FLAME.display(0.0F, 0.0F, 0.0F, 1.0F, 90, location.clone().add(0, 2.5, 0), player);
              ParticleEffect.SMOKE_LARGE.display(0.0F, 0.0F, 0.0F, 0.7F, 90, location.clone().add(0, 2.5, 0), player);
              ParticleEffect.LAVA.display(0.0F, 0.0F, 0.0F, 1.0F, 25, location.clone().add(0, .5, 0), player);
            }
            EnumSound.EXPLODE.play(pos.getWorld(), location, 2.0F, 1.0F);
          } else if (this.time > 0 && this.time <= 20) {
            for (Player player : Bukkit.getOnlinePlayers()) {
              ParticleEffect.CRIT_MAGIC.display(0.3F, 0.3F, 0.3F, 0.0F, 2, location.clone().add(0, 2.5, 0), player);
              ParticleEffect.EXPLOSION_LARGE.display(1.2F, 0.5F, 1.2F, 0.0F, 1, location.clone().add(0, 2.5, 0), player);
            }
            if (this.time == 20) {
              EnumSound.CHICKEN_EGG_POP.play(pos.getWorld(), location, 2.0F, 0.0F);
              EnumSound.ORB_PICKUP.play(pos.getWorld(), location, 1.0F, 1.0F);
            }
          }
          
          --this.time;
        }
      }
    }.runTaskTimer(Main.getInstance(), 0, 1);
  }
  
}
