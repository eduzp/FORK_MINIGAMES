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
import org.bukkit.util.EulerAngle;

import java.util.ArrayList;
import java.util.List;

public class VillagerOpener extends Opener {
  
  public VillagerOpener() {
      super(2, CosmeticType.OPENING_ANIMATION, "", "Villager", "SKULL_ITEM:3 : 1", 75, EnumRarity.DIVINO);
  }
  
  @Override
  public void executeMultiples(IArmorStand capsule, Block block, Profile profile, Location location, OpeningCallback callback) {
    Location pos = capsule.getEntity().getLocation();
    Player player = profile.getPlayer();
    IArmorStand stand = NMS.createArmorStand(pos.clone().add(0, 0.4, 0), null, null);
    stand.getEntity().setHelmet(BukkitUtils.deserializeItemStack("IRON_BLOCK : 1"));
    stand.getEntity().setGravity(false);
    stand.getEntity().setSmall(true);
    Material oldItem = block.getType();
    Location oldBLoc = block.getLocation();
    block.setType(Material.ENDER_CHEST);
    IArmorStand armorStand = NMS.createArmorStand(block.getLocation().clone().add(-0.5, 0.5, -1.5), null, null);
    armorStand.getEntity().setHelmet(BukkitUtils.deserializeItemStack("SKULL_ITEM:3 : 1 : skin>eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvNDFiODMwZWI0MDgyYWNlYzgzNmJjODM1ZTQwYTExMjgyYmI1MTE5MzMxNWY5MTE4NDMzN2U4ZDM1NTU1ODMifX19"));
    armorStand.getEntity().setChestplate(BukkitUtils.deserializeItemStack("LEATHER_CHESTPLATE : 1"));
    armorStand.getEntity().setLeggings(BukkitUtils.deserializeItemStack("LEATHER_LEGGINGS : 1"));
    armorStand.getEntity().setBoots(BukkitUtils.deserializeItemStack("LEATHER_BOOTS : 1"));
    ItemStack e = new ItemStack(Material.BLAZE_ROD);
    BukkitUtils.putGlowEnchantment(e);
    Location a = armorStand.getEntity().getLocation().clone();
    armorStand.getEntity().teleport(a);
    armorStand.getEntity().setItemInHand(e);
    armorStand.getEntity().setGravity(false);
    armorStand.getEntity().setLeftLegPose(new EulerAngle(-22, 0, 1));
    armorStand.getEntity().setRightLegPose(new EulerAngle(-22, 0, 1));
    EnumSound.CHEST_OPEN.play(player, 1.0f, 1.0f);
  
    ItemStack oldHelmet = capsule.getEntity().getHelmet();
    capsule.getEntity().setHelmet(null);
    new BukkitRunnable() {
    
      private int time = 125;
      private int frame = 0;
      private int sound = 0;
      private BukkitRunnable task;
    
      @Override
      public void run() {
        if (this.time == 0) {
          armorStand.killEntity();
          Profile user = Profile.getProfile(player.getName());
          List<BoxContent> rewardList = new ArrayList<>();
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
            rewardList.add(cosmetic);
          }
          EnumSound.ENDERDRAGON_GROWL.play(player.getWorld(), location, 0.5f, 2.0f);
          PlayerUtils.createBook(player, rewardList);
          profile.addStats("zCaixas", -MysteryBoxAPI.getMysteryBoxes(profile), "magic");
          Bukkit.getScheduler().scheduleSyncDelayedTask(Main.getInstance(), () -> {
            NMS.playChestAction(block.getLocation(), false);
            stand.getEntity().remove();
            oldBLoc.getBlock().setType(oldItem);
            BlockState state = oldBLoc.getBlock().getState();
            state.getData().setData((byte) 2);
            state.update(true);
          
            capsule.getEntity().setHelmet(oldHelmet);
            callback.finish();
            this.cancel();
            task.cancel();
          }, 33);
          this.time = -832329382;
        } else {
          NMS.playChestAction(block.getLocation(), true);
          Location location = stand.getEntity().getLocation().clone();
          if (this.time >= 20) {
            if (this.sound == 4) {
              EnumSound.NOTE_BASS.play(player.getWorld(), location, 1.0F, 0.7F);
            } else if (this.sound == 8) {
              EnumSound.NOTE_BASS_GUITAR.play(player.getWorld(), location, 1.0F, 0.2F);
              EnumSound.NOTE_STICKS.play(player.getWorld(), location, 1.0F, 2.0F);
              this.sound = 0;
            }
          
            ++this.frame;
            if (this.frame >= 300) {
              this.frame = 0;
            }
            ++this.sound;
            ++this.frame;
          }
          Location locationn = null;
          if (task == null) {
            task = new BukkitRunnable() {
              private boolean tick = false;
            
              @Override
              public void run() {
                armorStand.getEntity().setLeftLegPose(new EulerAngle(-(tick ? 22 : 56), 0, 0.5));
                armorStand.getEntity().setRightLegPose(new EulerAngle(-(tick ? 22 : 56), 0, 0.5));
                tick = !tick;
              }
            };
            task.runTaskTimer(Main.getInstance(), 0, 15);
          }
          if (this.time == 37) {
            EnumSound.GLASS.play(player.getWorld(), location, 1.0f, 1.0f);
            locationn = armorStand.getEntity().getLocation().clone().add(-2, 0.5, 0);
            for (Player bb : Bukkit.getOnlinePlayers()) {
              ParticleEffect.SPELL_INSTANT.display(0.0f, 0.0f, 0.0f, 0.0f, 25, locationn, bb);
            }
            locationn.setYaw(a.getYaw() - (45.0F * 3));
          }
          if (this.time == 55) {
            EnumSound.GLASS.play(player.getWorld(), location, 1.0f, 1.0f);
            locationn = armorStand.getEntity().getLocation().clone().add(0, 0.5, 3F);
            for (Player bb : Bukkit.getOnlinePlayers()) {
              ParticleEffect.SPELL_INSTANT.display(0.0f, 0.0f, 0.0f, 0.0f, 25, locationn, bb);
            }
            locationn.setYaw(a.getYaw() + 45.0F * 3);
          }
          if (this.time == 95) {
            EnumSound.GLASS.play(player.getWorld(), location, 1.0f, 1.0f);
            locationn = armorStand.getEntity().getLocation().clone().add(2, 0, 0);
            for (Player bb : Bukkit.getOnlinePlayers()) {
              ParticleEffect.SPELL_INSTANT.display(0.0f, 0.0f, 0.0f, 0.0f, 25, locationn, bb);
            }
            locationn.setYaw(a.getYaw() + 45.0F);
          }
          if (locationn != null) {
            armorStand.getEntity().teleport(locationn);
          }
        
          if (this.time == 37) {
            stand.getEntity().getLocation().add(0, 0.5, 0);
            Bukkit.getScheduler().scheduleSyncDelayedTask(Main.getInstance(), () -> {
              stand.getEntity().setHelmet(BukkitUtils.deserializeItemStack("EMERALD_BLOCK : 1"));
              stand.getEntity().getLocation().subtract(0, 0.5, 0);
            });
          } else if (this.time == 55) {
            stand.getEntity().getLocation().add(0, 0.5, 0);
            Bukkit.getScheduler().scheduleSyncDelayedTask(Main.getInstance(), () -> {
              stand.getEntity().setHelmet(BukkitUtils.deserializeItemStack("DIAMOND_BLOCK : 1"));
              stand.getEntity().getLocation().subtract(0, 0.5, 0);
            });
          } else if (this.time == 105) {
            stand.getEntity().getLocation().add(0, 0.5, 0);
            Bukkit.getScheduler().scheduleSyncDelayedTask(Main.getInstance(), () -> {
              stand.getEntity().setHelmet(BukkitUtils.deserializeItemStack("GOLD_BLOCK : 1"));
              stand.getEntity().getLocation().subtract(0, 0.5, 0);
            });
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
    IArmorStand stand = NMS.createArmorStand(pos.clone().add(0, 0.4, 0), null, null);
    stand.getEntity().setHelmet(BukkitUtils.deserializeItemStack("IRON_BLOCK : 1"));
    stand.getEntity().setGravity(false);
    stand.getEntity().setSmall(true);
    Material oldItem = block.getType();
    Location oldBLoc = block.getLocation();
    block.setType(Material.ENDER_CHEST);
    IArmorStand armorStand = NMS.createArmorStand(block.getLocation().clone().add(-0.5, 0.5, -1.5), null, null);
    armorStand.getEntity().setHelmet(BukkitUtils.deserializeItemStack("SKULL_ITEM:3 : 1 : skin>eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvNDFiODMwZWI0MDgyYWNlYzgzNmJjODM1ZTQwYTExMjgyYmI1MTE5MzMxNWY5MTE4NDMzN2U4ZDM1NTU1ODMifX19"));
    armorStand.getEntity().setChestplate(BukkitUtils.deserializeItemStack("LEATHER_CHESTPLATE : 1"));
    armorStand.getEntity().setLeggings(BukkitUtils.deserializeItemStack("LEATHER_LEGGINGS : 1"));
    armorStand.getEntity().setBoots(BukkitUtils.deserializeItemStack("LEATHER_BOOTS : 1"));
    ItemStack e = new ItemStack(Material.BLAZE_ROD);
    BukkitUtils.putGlowEnchantment(e);
    Location a = armorStand.getEntity().getLocation().clone();
    armorStand.getEntity().teleport(a);
    armorStand.getEntity().setItemInHand(e);
    armorStand.getEntity().setGravity(false);
    armorStand.getEntity().setLeftLegPose(new EulerAngle(-22, 0, 1));
    armorStand.getEntity().setRightLegPose(new EulerAngle(-22, 0, 1));
    EnumSound.CHEST_OPEN.play(player, 1.0f, 1.0f);
    
    ItemStack oldHelmet = capsule.getEntity().getHelmet();
    capsule.getEntity().setHelmet(null);
    new BukkitRunnable() {
      
      private int time = 125;
      private int frame = 0;
      private int sound = 0;
      private BukkitRunnable task;
      
      @Override
      public void run() {
        if (this.time == 0) {
          armorStand.killEntity();
          Profile user = Profile.getProfile(player.getName());
          profile.addStats("zCaixas", -1L, "magic");
          
          if (cosmetic.canBeDispatched()) {
            cosmetic.dispatch(user);
          } else {
            user.getAbstractContainer("zCaixas", "queueRewards", MysteryBoxQueueContainer.class).addToQueue(cosmetic);
          }
  
          EnumSound.ENDERDRAGON_GROWL.play(player.getWorld(), location, 0.5f, 2.0f);
          stand.setName(StringUtils.formatColors(cosmetic.getName()));
          Bukkit.getScheduler().scheduleSyncDelayedTask(Main.getInstance(), () -> {
            NMS.playChestAction(block.getLocation(), false);
            stand.getEntity().remove();
            oldBLoc.getBlock().setType(oldItem);
            BlockState state = oldBLoc.getBlock().getState();
            state.getData().setData((byte) 2);
            state.update(true);
  
            capsule.getEntity().setHelmet(oldHelmet);
            callback.finish();
            this.cancel();
            task.cancel();
          }, 33);
          this.time = -832329382;
        } else {
          NMS.playChestAction(block.getLocation(), true);
          Location location = stand.getEntity().getLocation().clone();
          if (this.time >= 20) {
            if (this.sound == 4) {
              EnumSound.NOTE_BASS.play(player.getWorld(), location, 1.0F, 0.7F);
            } else if (this.sound == 8) {
              EnumSound.NOTE_BASS_GUITAR.play(player.getWorld(), location, 1.0F, 0.2F);
              EnumSound.NOTE_STICKS.play(player.getWorld(), location, 1.0F, 2.0F);
              this.sound = 0;
            }
  
            ++this.frame;
            if (this.frame >= 300) {
              this.frame = 0;
            }
            ++this.sound;
            ++this.frame;
          }
          Location locationn = null;
          if (task == null) {
            task = new BukkitRunnable() {
              private boolean tick = false;
    
              @Override
              public void run() {
                armorStand.getEntity().setLeftLegPose(new EulerAngle(-(tick ? 22 : 56), 0, 0.5));
                armorStand.getEntity().setRightLegPose(new EulerAngle(-(tick ? 22 : 56), 0, 0.5));
                tick = !tick;
              }
            };
            task.runTaskTimer(Main.getInstance(), 0, 15);
          }
          if (this.time == 37) {
            EnumSound.GLASS.play(player.getWorld(), location, 1.0f, 1.0f);
            locationn = armorStand.getEntity().getLocation().clone().add(-2, 0.5, 0);
            for (Player bb : Bukkit.getOnlinePlayers()) {
              ParticleEffect.SPELL_INSTANT.display(0.0f, 0.0f, 0.0f, 0.0f, 25, locationn, bb);
            }
            locationn.setYaw(a.getYaw() - (45.0F * 3));
          }
          if (this.time == 55) {
            EnumSound.GLASS.play(player.getWorld(), location, 1.0f, 1.0f);
            locationn = armorStand.getEntity().getLocation().clone().add(0, 0.5, 3F);
            for (Player bb : Bukkit.getOnlinePlayers()) {
              ParticleEffect.SPELL_INSTANT.display(0.0f, 0.0f, 0.0f, 0.0f, 25, locationn, bb);
            }
            locationn.setYaw(a.getYaw() + 45.0F * 3);
          }
          if (this.time == 95) {
            EnumSound.GLASS.play(player.getWorld(), location, 1.0f, 1.0f);
            locationn = armorStand.getEntity().getLocation().clone().add(2, 0, 0);
            for (Player bb : Bukkit.getOnlinePlayers()) {
              ParticleEffect.SPELL_INSTANT.display(0.0f, 0.0f, 0.0f, 0.0f, 25, locationn, bb);
            }
            locationn.setYaw(a.getYaw() + 45.0F);
          }
          if (locationn != null) {
            armorStand.getEntity().teleport(locationn);
          }
          
          if (this.time == 37) {
            stand.getEntity().getLocation().add(0, 0.5, 0);
            Bukkit.getScheduler().scheduleSyncDelayedTask(Main.getInstance(), () -> {
              stand.getEntity().setHelmet(BukkitUtils.deserializeItemStack("EMERALD_BLOCK : 1"));
              stand.getEntity().getLocation().subtract(0, 0.5, 0);
            });
          } else if (this.time == 55) {
            stand.getEntity().getLocation().add(0, 0.5, 0);
            Bukkit.getScheduler().scheduleSyncDelayedTask(Main.getInstance(), () -> {
              stand.getEntity().setHelmet(BukkitUtils.deserializeItemStack("DIAMOND_BLOCK : 1"));
              stand.getEntity().getLocation().subtract(0, 0.5, 0);
            });
          } else if (this.time == 105) {
            stand.getEntity().getLocation().add(0, 0.5, 0);
            Bukkit.getScheduler().scheduleSyncDelayedTask(Main.getInstance(), () -> {
              stand.getEntity().setHelmet(BukkitUtils.deserializeItemStack("GOLD_BLOCK : 1"));
              stand.getEntity().getLocation().subtract(0, 0.5, 0);
            });
          }
  
          --this.time;
        }
      }
    }.runTaskTimer(Main.getInstance(), 0, 1);
  }
  
}
