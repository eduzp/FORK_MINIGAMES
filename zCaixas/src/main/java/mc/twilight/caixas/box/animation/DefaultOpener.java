package mc.twilight.caixas.box.animation;

import com.mojang.authlib.GameProfile;
import com.mojang.authlib.properties.Property;
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
import mc.twilight.core.utils.enums.EnumSound;
import mc.twilight.core.utils.particles.ParticleEffect;
import org.bukkit.Bukkit;
import org.bukkit.Effect;
import org.bukkit.Location;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.material.Chest;
import org.bukkit.material.EnderChest;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitTask;
import org.bukkit.util.EulerAngle;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.UUID;

public class DefaultOpener extends Opener {
  
  private static final GameProfile chest;
  
  static {
    chest = new GameProfile(UUID.randomUUID(), null);
    chest.getProperties().put("textures", new Property("textures",
        "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvMWRlYmNmZTQxNDNhMTFlNjFkMzkzMTc5OWZiMzIyZTVhYTJhZTczMjc1YzUzYzJjNjc0MTYxNzhkMTQ5ZTE1MiJ9fX0==="));
  }
  
  public DefaultOpener() {
    super(0, CosmeticType.OPENING_ANIMATION,
        "", "Padrão", "IRON_PICKAXE : 1 : esconder>tudo", 0, null);
  }
  
  public static void doTheThingMultiples(IArmorStand capsule, Player player, Location location, int numberOfArmorstands, double speed, double radius, OpeningCallback callback, long oldBoxes) {
    ItemStack old = capsule.getEntity().getHelmet();
    capsule.getEntity().setHelmet(null);
    Location enderChest = location.clone();
    Location loc = location.getBlock().getLocation().clone().add(0, -1.5, 0);
    UUID id = player.getUniqueId();
    new BukkitRunnable() {
      final List<IArmorStand> armorstands = new ArrayList<>();
      final int numOfAS = numberOfArmorstands;
        int ran = 0;
        int endAtRun = -1;
      final double PI_2 = Math.PI * 2;
        final double rad = radius;
        final double spd = speed;
        double currentOffset = 0;
        final double asOffset = PI_2 / numOfAS;
      boolean isRandomed = false;
      IArmorStand winHologram;
      boolean winning = false, ended = false;
      
      @Override
      public void run() {
        double rotate = 0;
        boolean revert = false;
        if (enderChest.getBlock().getState().getData() instanceof EnderChest) {
          EnderChest ender_chest = (EnderChest) enderChest.getBlock().getState().getData();
          if (ender_chest.getFacing() == BlockFace.NORTH) {
            rotate = 180.0;
          } else if (ender_chest.getFacing() == BlockFace.EAST) {
            rotate = -90.0;
            revert = true;
          } else if (ender_chest.getFacing() == BlockFace.SOUTH) {
            rotate = 0.0;
          } else if (ender_chest.getFacing() == BlockFace.WEST) {
            rotate = 90.0;
            revert = true;
          }
        } else if (enderChest.getBlock().getState().getData() instanceof Chest) {
          Chest chest = (Chest) enderChest.getBlock().getState().getData();
          if (chest.getFacing() == BlockFace.NORTH) {
            rotate = 180.0;
          } else if (chest.getFacing() == BlockFace.EAST) {
            rotate = -90.0;
            revert = true;
          } else if (chest.getFacing() == BlockFace.SOUTH) {
            rotate = 0.0;
          } else if (chest.getFacing() == BlockFace.WEST) {
            rotate = 90.0;
            revert = true;
          }
        }
        
        NMS.playChestAction(enderChest, true);
        Location pLoc = loc.clone().add(0.5, 1, 0.5);
        if (armorstands.size() == numOfAS || ended) {
          ended = true;
          if (endAtRun == -1 && !winning) {
            endAtRun = 80;
            winning = true;
          }
          
          if (ran >= endAtRun) {
            if (endAtRun == 80) {
              endAtRun = (numOfAS - 1) * 4;
              ParticleEffect.CRIT_MAGIC.display(0.3f, 0.3f, 0.3f, 0.0f, 2, winHologram.getEntity().getEyeLocation(), 16);
              ParticleEffect.EXPLOSION_NORMAL.display(1.2f, 0.5f, 1.2f, 0.0f, 1, winHologram.getEntity().getEyeLocation(), 16);
            }
            if (endAtRun <= 0) {
              if (endAtRun == -1) {
                winHologram.getEntity().teleport(winHologram.getEntity().getLocation().getBlock().getLocation().clone().add(0.5, 0, 0.5));
              }
              winHologram.getEntity().teleport(winHologram.getEntity().getLocation().clone().subtract(0, 0.1, 0));
              if (endAtRun == -5) {
                IArmorStand stand = NMS.createArmorStand(winHologram.getEntity().getLocation().clone().add(0, 0.5, 0), "§k" + StringUtils.stripColors("agijqawui4u823q48asugduasuguasuhgdagdj"), null);
                stand.getEntity().setGravity(false);
                cancel();
                new BukkitRunnable() {
                  int count = 80;
                  @Override
                  public void run() {
                    if (count == 0) {
                      NMS.playChestAction(loc.clone().add(0, 1.5, 0), false);
                      stand.killEntity();
                      winHologram.killEntity();
                      armorstands.clear();
                      callback.finish();
                      this.cancel();
                      return;
                    }
                    
                    NMS.playChestAction(enderChest, true);
                    if (count == 65) {
                      ParticleEffect.FLAME.display(0.0f, 0.0f, 0.0f, 1.0f, 90, winHologram.getEntity().getEyeLocation(), 16);
                      ParticleEffect.SMOKE_LARGE.display(0.0f, 0.0f, 0.0f, 0.7f, 90, winHologram.getEntity().getEyeLocation(), 16);
                      ParticleEffect.LAVA.display(0.0f, 0.0f, 0.0f, 1.0f, 25, winHologram.getEntity().getEyeLocation(), 16);
                      EnumSound.EXPLODE.play(loc.getWorld(), (winHologram.getEntity()).getLocation(), 0.3f, 1.0f);
                      Profile user = Profile.getProfile(player.getName());
                      if (Bukkit.getPlayer(id) != null && user != null) {
                        List<BoxContent> rewardList = new ArrayList<>();
                        for (int i = 0; i < oldBoxes; i++) {
                          BoxContent cosmetic = BoxContent.getRandom();
                          if (cosmetic == null) {
                            return;
                          }
                          
                          if (cosmetic.canBeDispatched()) {
                            cosmetic.dispatch(user);
                          } else {
                            user.getAbstractContainer("zCaixas", "queueRewards", MysteryBoxQueueContainer.class).addToQueue(cosmetic);
                          }
                          stand.setName(StringUtils.formatColors(cosmetic.getName()));
                          rewardList.add(cosmetic);
                        }
                        PlayerUtils.createBook(player, rewardList);
                        capsule.getEntity().setHelmet(old);
                      }
                    }
                    if (count >= 65 && count % 2 == 0) {
                      EnumSound.NOTE_PLING.play(loc.getWorld(), winHologram.getEntity().getLocation(), 0.2f, 1.0f);
                      EnumSound.NOTE_SNARE_DRUM.play(loc.getWorld(), winHologram.getEntity().getLocation(), 1.0f, 1.0f);
                    }
                    count--;
                  }
                }.runTaskTimer(Main.getInstance(), 0, 1);
              }
              endAtRun--;
              return;
            }
            if (endAtRun % 4 == 0) {
              IArmorStand random = armorstands.get(new Random().nextInt(armorstands.size()));
              while (random.equals(winHologram)) {
                random = armorstands.get(new Random().nextInt(armorstands.size()));
              }
              random.killEntity();
              EnumSound.LAVA_POP.play(random.getEntity().getWorld(), random.getEntity().getLocation(), 1.0f, 1.0f);
              random.getEntity().getWorld().playEffect(random.getEntity().getLocation(), Effect.SMOKE, 1000);
              armorstands.remove(random);
            }
            endAtRun--;
            return;
          }
        }
        if (armorstands.size() < numOfAS) {
          if (Math.toRadians(currentOffset) >= asOffset * armorstands.size()) {
            IArmorStand a = NMS.createArmorStand(pLoc, "", null);
            ArmorStand hd = a.getEntity();
            hd.setGravity(false);
            hd.setHeadPose(new EulerAngle(0, rotate, 0));
            hd.setHelmet(BukkitUtils.putProfileOnSkull(chest, BukkitUtils.deserializeItemStack("SKULL_ITEM:3")));
            
            armorstands.add(a);
            return;
          }
        }
        if (armorstands.size() == numOfAS) {
          if (!isRandomed) {
            winHologram = armorstands.get(armorstands.size() - 3);
            isRandomed = true;
          }
        }
        int asNum = 0;
        double cO = Math.toRadians(currentOffset % 360d);
        for (double d = 0; d < PI_2; d += PI_2 / numOfAS) {
          if (asNum >= armorstands.size()) {
            break;
          }
          double a = d + cO, cos = Math.cos(a), sin = Math.sin(a);
          IArmorStand as = armorstands.get(asNum++);
          Location l = pLoc.clone().add((!revert ? cos * rad : 0), -sin * rad, (revert ? cos * rad : 0));
          as.getEntity().teleport(l);
        }
        currentOffset += spd;
        ran++;
        if (ran % 5 == 0) {
          EnumSound.CLICK.play(loc.getWorld(), loc, 0.04f, 1.0f);
          EnumSound.NOTE_PLING.play(loc.getWorld(), loc, 0.2f, 1.0f);
          EnumSound.NOTE_BASS_DRUM.play(loc.getWorld(), loc, 1.0f, 1.0f);
        }
      }
    }.runTaskTimer(Main.getInstance(), 0, 1);
  }
  
  public static BukkitTask doTheThing(IArmorStand capsule, Player player, BoxContent cosmetic, Location location, int numberOfArmorstands, double speed, double radius, OpeningCallback callback) {
    ItemStack old = capsule.getEntity().getHelmet();
    capsule.getEntity().setHelmet(null);
    Location enderChest = location.clone();
    Location loc = location.getBlock().getLocation().clone().add(0, -1.5, 0);
    UUID id = player.getUniqueId();
    return new BukkitRunnable() {
      final List<IArmorStand> armorstands = new ArrayList<>();
      final int numOfAS = numberOfArmorstands;
      final double PI_2 = Math.PI * 2;
      final double rad = radius;
      final double spd = speed;
      final double asOffset = PI_2 / numOfAS;
      int ran = 0;
      int endAtRun = -1;
      double currentOffset = 0;
      boolean isRandomed = false;
      IArmorStand winHologram;
      boolean winning = false, ended = false;
      
      @Override
      public void run() {
        double rotate = 0;
        boolean revert = false;
        if (enderChest.getBlock().getState().getData() instanceof EnderChest) {
          EnderChest ender_chest = (EnderChest) enderChest.getBlock().getState().getData();
          if (ender_chest.getFacing() == BlockFace.NORTH) {
            rotate = 180.0;
          } else if (ender_chest.getFacing() == BlockFace.EAST) {
            rotate = -90.0;
            revert = true;
          } else if (ender_chest.getFacing() == BlockFace.SOUTH) {
            rotate = 0.0;
          } else if (ender_chest.getFacing() == BlockFace.WEST) {
            rotate = 90.0;
            revert = true;
          }
        } else if (enderChest.getBlock().getState().getData() instanceof Chest) {
          Chest chest = (Chest) enderChest.getBlock().getState().getData();
          if (chest.getFacing() == BlockFace.NORTH) {
            rotate = 180.0;
          } else if (chest.getFacing() == BlockFace.EAST) {
            rotate = -90.0;
            revert = true;
          } else if (chest.getFacing() == BlockFace.SOUTH) {
            rotate = 0.0;
          } else if (chest.getFacing() == BlockFace.WEST) {
            rotate = 90.0;
            revert = true;
          }
        }
        
        NMS.playChestAction(enderChest, true);
        Location pLoc = loc.clone().add(0.5, 1, 0.5);
        if (armorstands.size() == numOfAS || ended) {
          ended = true;
          if (endAtRun == -1 && !winning) {
            endAtRun = 80;
            winning = true;
          }
          
          if (ran >= endAtRun) {
            if (endAtRun == 80) {
              endAtRun = (numOfAS - 1) * 4;
              ParticleEffect.CRIT_MAGIC.display(0.3f, 0.3f, 0.3f, 0.0f, 2, winHologram.getEntity().getEyeLocation(), 16);
              ParticleEffect.EXPLOSION_NORMAL.display(1.2f, 0.5f, 1.2f, 0.0f, 1, winHologram.getEntity().getEyeLocation(), 16);
            }
            if (endAtRun <= 0) {
              if (endAtRun == -1) {
                winHologram.getEntity().teleport(winHologram.getEntity().getLocation().getBlock().getLocation().clone().add(0.5, 0, 0.5));
              }
              winHologram.getEntity().teleport(winHologram.getEntity().getLocation().clone().subtract(0, 0.1, 0));
              if (endAtRun == -5) {
                IArmorStand stand = NMS.createArmorStand(winHologram.getEntity().getLocation().clone().add(0, 0.5, 0), "§k" + StringUtils.stripColors("agijqawui4u823q48asugduasuguasuhgdagdj"), null);
                stand.getEntity().setGravity(false);
                this.cancel();
                new BukkitRunnable() {
                  int count = 80;
                  
                  @Override
                  public void run() {
                    if (count == 0) {
                      NMS.playChestAction(loc.clone().add(0, 1.5, 0), false);
                      stand.killEntity();
                      winHologram.killEntity();
                      armorstands.clear();
                      callback.finish();
                      this.cancel();
                      return;
                    }
                    
                    NMS.playChestAction(enderChest, true);
                    if (count == 65) {
                      ParticleEffect.FLAME.display(0.0f, 0.0f, 0.0f, 1.0f, 90, winHologram.getEntity().getEyeLocation(), 16);
                      ParticleEffect.SMOKE_LARGE.display(0.0f, 0.0f, 0.0f, 0.7f, 90, winHologram.getEntity().getEyeLocation(), 16);
                      ParticleEffect.LAVA.display(0.0f, 0.0f, 0.0f, 1.0f, 25, winHologram.getEntity().getEyeLocation(), 16);
                      EnumSound.EXPLODE.play(loc.getWorld(), winHologram.getEntity().getLocation(), 0.3f, 1.0f);
                      Profile user;
                      if (Bukkit.getPlayer(id) != null && (user = Profile.getProfile(player.getName())) != null) {

                        if (cosmetic.canBeDispatched()) {
                          cosmetic.dispatch(user);
                        } else {
                          user.getAbstractContainer("zCaixas", "queueRewards", MysteryBoxQueueContainer.class).addToQueue(cosmetic);
                        }
                        capsule.getEntity().setHelmet(old);
                      }
                      stand.setName(StringUtils.formatColors(cosmetic.getName()));
                    }
                    
                    if (count >= 65 && count % 2 == 0) {
                      EnumSound.NOTE_PLING.play(loc.getWorld(), winHologram.getEntity().getLocation(), 0.2f, 1.0f);
                      EnumSound.NOTE_SNARE_DRUM.play(loc.getWorld(), winHologram.getEntity().getLocation(), 1.0f, 1.0f);
                    }
                    count--;
                  }
                }.runTaskTimer(Main.getInstance(), 0, 1);
              }
              endAtRun--;
              return;
            }
            if (endAtRun % 4 == 0) {
              IArmorStand random = armorstands.get(new Random().nextInt(armorstands.size()));
              while (random.equals(winHologram)) {
                random = armorstands.get(new Random().nextInt(armorstands.size()));
              }
              random.killEntity();
              EnumSound.LAVA_POP.play(random.getEntity().getWorld(), random.getEntity().getLocation(), 1.0f, 1.0f);
              random.getEntity().getWorld().playEffect(random.getEntity().getLocation(), Effect.SMOKE, 1000);
              armorstands.remove(random);
            }
            endAtRun--;
            return;
          }
        }
        if (armorstands.size() < numOfAS) {
          if (Math.toRadians(currentOffset) >= asOffset * armorstands.size()) {
            IArmorStand a = NMS.createArmorStand(pLoc, "", null);
            ArmorStand hd = a.getEntity();
            hd.setGravity(false);
            hd.setHeadPose(new EulerAngle(0, rotate, 0));
            hd.setHelmet(BukkitUtils.putProfileOnSkull(chest, BukkitUtils.deserializeItemStack("SKULL_ITEM:3")));
            
            armorstands.add(a);
            return;
          }
        }
        if (armorstands.size() == numOfAS) {
          if (!isRandomed) {
            winHologram = armorstands.get(armorstands.size() - 3);
            isRandomed = true;
          }
        }
        int asNum = 0;
        double cO = Math.toRadians(currentOffset % 360d);
        for (double d = 0; d < PI_2; d += PI_2 / numOfAS) {
          if (asNum >= armorstands.size()) {
            break;
          }
          double a = d + cO, cos = Math.cos(a), sin = Math.sin(a);
          IArmorStand as = armorstands.get(asNum++);
          Location l = pLoc.clone().add((!revert ? cos * rad : 0), -sin * rad, (revert ? cos * rad : 0));
          as.getEntity().teleport(l);
        }
        currentOffset += spd * 1.0;
        ran++;
        if (ran % 5 == 0) {
          EnumSound.CLICK.play(loc.getWorld(), loc, 0.04f, 1.0f);
          EnumSound.NOTE_PLING.play(loc.getWorld(), loc, 0.2f, 1.0f);
          EnumSound.NOTE_BASS_DRUM.play(loc.getWorld(), loc, 1.0f, 1.0f);
        }
      }
    }.runTaskTimer(Main.getInstance(), 0, 1);
  }
  
  @Override
  public void execute(BoxNPC npc, Block block, IArmorStand capsule, Profile profile, BoxContent box, Location location, OpeningCallback callback) {
    profile.addStats("zCaixas", -1L, "magic");
    
    DefaultOpener.doTheThing(capsule, profile.getPlayer(), box, location, 7, 10.0, 1.3, callback);
  }
  
  @Override
  public void executeMultiples(IArmorStand capsule, Block block, Profile profile, Location location, OpeningCallback callback) {
    DefaultOpener.doTheThingMultiples(
        capsule, profile.getPlayer(), location, 7, 10.0, 1.3, callback, MysteryBoxAPI.getMysteryBoxes(profile));
  
    profile.addStats("zCaixas", -MysteryBoxAPI.getMysteryBoxes(profile), "magic");
  }
}
