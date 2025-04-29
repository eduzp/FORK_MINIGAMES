package mc.twilight.cosmeticos.nms.v1_8_R3.companions.types;

import mc.twilight.cosmeticos.cosmetics.object.Companion;
import mc.twilight.cosmeticos.cosmetics.types.companions.CompanionStructure;
import mc.twilight.cosmeticos.cosmetics.types.companions.math.RelativeLocation;
import mc.twilight.cosmeticos.nms.NMS;
import mc.twilight.cosmeticos.nms.v1_8_R3.companions.CompanionSlime;
import mc.twilight.core.utils.BukkitUtils;

import static mc.twilight.cosmeticos.utils.MathUtils.EulerAngle;

public class ChimpEntity extends CompanionSlime {
  
  public ChimpEntity(Companion companion) {
    super(companion);
    this.maxFrames = companion.getCosmetic().getFrames().isEmpty() ? 0 : companion.getCosmetic().getFrames().get(0).size();
    this.maxIdleFrames = companion.getCosmetic().getIdleFrames().isEmpty() ? 0 : companion.getCosmetic().getIdleFrames().get(0).size();
    
    CompanionStructure head = this.buildPart("head", new RelativeLocation(-0.4, 0.0, 0.0), false);
    head.getStand().setHelmet(BukkitUtils.deserializeItemStack("SKULL_ITEM:3 : 1 : skin>eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvNTQzOTI4ODFhMTNhNDJiMDhhZWI4ODhmMzk5ZTFkZWJkYWNlMDRiYzVhMGUwMWMyMjFjMGI1ZDExZDQwIn19fQ=="));
    
    CompanionStructure front_Left = this.buildPart("front_Left", new RelativeLocation(-0.7, 0.3, 0.0), true);
    front_Left.getStand().setHelmet(BukkitUtils.deserializeItemStack("SKULL_ITEM:3 : 1 : skin>eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvM2Y0NzQ0Zjk3ZDJjNGI5MDRiYTg3YzkzM2JkYzIzOTU3NDBiOWEyMzgwYTcyYjQ0MjU4MGMzOTJhY2M3Y2IifX19"));
    
    CompanionStructure front_Right = this.buildPart("front_Right", new RelativeLocation(-0.7, -0.3, 0.0), true);
    front_Right.getStand().setHelmet(BukkitUtils.deserializeItemStack("SKULL_ITEM:3 : 1 : skin>eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvM2Y0NzQ0Zjk3ZDJjNGI5MDRiYTg3YzkzM2JkYzIzOTU3NDBiOWEyMzgwYTcyYjQ0MjU4MGMzOTJhY2M3Y2IifX19"));
    
    CompanionStructure front_Rightup = this.buildPart("front_Rightup", new RelativeLocation(-0.3, -0.3, 0.0), true);
    front_Rightup.getStand().setHelmet(BukkitUtils.deserializeItemStack("SKULL_ITEM:3 : 1 : skin>eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvM2Y0NzQ0Zjk3ZDJjNGI5MDRiYTg3YzkzM2JkYzIzOTU3NDBiOWEyMzgwYTcyYjQ0MjU4MGMzOTJhY2M3Y2IifX19"));
    
    CompanionStructure Belly1 = this.buildPart("Belly1", new RelativeLocation(-0.95, 0.2, -0.688), false);
    Belly1.getStand().setHeadPose(EulerAngle(-15, 0, 0));
    Belly1.getStand().setHelmet(BukkitUtils.deserializeItemStack("SKULL_ITEM:3 : 1 : skin>eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvM2Y0NzQ0Zjk3ZDJjNGI5MDRiYTg3YzkzM2JkYzIzOTU3NDBiOWEyMzgwYTcyYjQ0MjU4MGMzOTJhY2M3Y2IifX19"));
    
    CompanionStructure Belly3 = this.buildPart("Belly3", new RelativeLocation(-0.85, 0.2, -0.344), false);
    Belly3.getStand().setHeadPose(EulerAngle(-15, 0, 0));
    Belly3.getStand().setHelmet(BukkitUtils.deserializeItemStack("SKULL_ITEM:3 : 1 : skin>eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvM2Y0NzQ0Zjk3ZDJjNGI5MDRiYTg3YzkzM2JkYzIzOTU3NDBiOWEyMzgwYTcyYjQ0MjU4MGMzOTJhY2M3Y2IifX19"));
    
    CompanionStructure back_Left = this.buildPart("back_Left", new RelativeLocation(-0.7, 0.3, -1.0), true);
    back_Left.getStand().setHelmet(BukkitUtils.deserializeItemStack("SKULL_ITEM:3 : 1 : skin>eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvM2Y0NzQ0Zjk3ZDJjNGI5MDRiYTg3YzkzM2JkYzIzOTU3NDBiOWEyMzgwYTcyYjQ0MjU4MGMzOTJhY2M3Y2IifX19"));
    
    CompanionStructure Belly2 = this.buildPart("Belly2", new RelativeLocation(-0.95, -0.2, -0.688), false);
    Belly2.getStand().setHeadPose(EulerAngle(-15, 0, 0));
    Belly2.getStand().setHelmet(BukkitUtils.deserializeItemStack("SKULL_ITEM:3 : 1 : skin>eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvM2Y0NzQ0Zjk3ZDJjNGI5MDRiYTg3YzkzM2JkYzIzOTU3NDBiOWEyMzgwYTcyYjQ0MjU4MGMzOTJhY2M3Y2IifX19"));
    
    CompanionStructure back_Right = this.buildPart("back_Right", new RelativeLocation(-0.7, -0.3, -1.0), true);
    back_Right.getStand().setHelmet(BukkitUtils.deserializeItemStack("SKULL_ITEM:3 : 1 : skin>eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvM2Y0NzQ0Zjk3ZDJjNGI5MDRiYTg3YzkzM2JkYzIzOTU3NDBiOWEyMzgwYTcyYjQ0MjU4MGMzOTJhY2M3Y2IifX19"));
    
    CompanionStructure Belly4 = this.buildPart("Belly4", new RelativeLocation(-0.85, -0.2, -0.344), false);
    Belly4.getStand().setHeadPose(EulerAngle(-15, 0, 0));
    Belly4.getStand().setHelmet(BukkitUtils.deserializeItemStack("SKULL_ITEM:3 : 1 : skin>eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvM2Y0NzQ0Zjk3ZDJjNGI5MDRiYTg3YzkzM2JkYzIzOTU3NDBiOWEyMzgwYTcyYjQ0MjU4MGMzOTJhY2M3Y2IifX19"));
    
    CompanionStructure front_Leftup = this.buildPart("front_Leftup", new RelativeLocation(-0.3, 0.3, 0.0), true);
    front_Leftup.getStand().setHelmet(BukkitUtils.deserializeItemStack("SKULL_ITEM:3 : 1 : skin>eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvM2Y0NzQ0Zjk3ZDJjNGI5MDRiYTg3YzkzM2JkYzIzOTU3NDBiOWEyMzgwYTcyYjQ0MjU4MGMzOTJhY2M3Y2IifX19"));
    
    this.spawn();
  }
  
  @Override
  public void setCompanionName(String name) {
    ((CompanionStand) NMS.getInstance().getHandle(this.structures.get(0).getStand())).setCompanionName(name);
  }
}
