package mc.twilight.cosmeticos.nms.v1_8_R3.companions.types;

import mc.twilight.cosmeticos.cosmetics.object.Companion;
import mc.twilight.cosmeticos.cosmetics.types.companions.CompanionStructure;
import mc.twilight.cosmeticos.cosmetics.types.companions.math.RelativeLocation;
import mc.twilight.cosmeticos.nms.NMS;
import mc.twilight.cosmeticos.nms.v1_8_R3.companions.CompanionSlime;
import mc.twilight.core.utils.BukkitUtils;

import static mc.twilight.cosmeticos.utils.MathUtils.EulerAngle;

public class KoalaEntity extends CompanionSlime {
  
  public KoalaEntity(Companion companion) {
    super(companion);
    this.maxFrames = companion.getCosmetic().getFrames().isEmpty() ? 0 : companion.getCosmetic().getFrames().get(0).size();
    this.maxIdleFrames = companion.getCosmetic().getIdleFrames().isEmpty() ? 0 : companion.getCosmetic().getIdleFrames().get(0).size();
    
    CompanionStructure head = this.buildPart("head", new RelativeLocation(-0.8, 0.0, 0.3), false);
    head.getStand().setHelmet(BukkitUtils.deserializeItemStack("SKULL_ITEM:3 : 1 : skin>eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvOGQ4N2U4MjlmMmQ0ODlmZjZlZTY2ODI2MzllMzM5YTIxMjNkMzQ0OWQyN2Y4YmRhNTE1MjhjNjA3NmZiOWYyYSJ9fX0="));
    
    CompanionStructure front_Left = this.buildPart("front_Left", new RelativeLocation(-0.7, 0.3, 0.0), true);
    front_Left.getStand().setHelmet(BukkitUtils.deserializeItemStack("SKULL_ITEM:3 : 1 : skin>eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvODliODVkZThkODIyNWZjYmNlMzc5N2IxM2MxM2VlNDJhY2Q3YWFhZmFkZDg2MTkxM2Q0YWJjNzczODNmYmMifX19"));
    
    CompanionStructure front_Belly = this.buildPart("front_Belly", new RelativeLocation(-0.25, 0.0, -0.6), true);
    front_Belly.getStand().setHeadPose(EulerAngle(90, 0, 0));
    front_Belly.getStand().setHelmet(BukkitUtils.deserializeItemStack("SNOW_BLOCK : 1"));
    
    CompanionStructure front_Right = this.buildPart("front_Right", new RelativeLocation(-0.7, -0.3, 0.0), true);
    front_Right.getStand().setHelmet(BukkitUtils.deserializeItemStack("SKULL_ITEM:3 : 1 : skin>eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvODliODVkZThkODIyNWZjYmNlMzc5N2IxM2MxM2VlNDJhY2Q3YWFhZmFkZDg2MTkxM2Q0YWJjNzczODNmYmMifX19"));
    
    CompanionStructure back_Belly = this.buildPart("back_Belly", new RelativeLocation(-0.25, 0.0, -0.9), true);
    back_Belly.getStand().setHeadPose(EulerAngle(90, 0, 0));
    back_Belly.getStand().setHelmet(BukkitUtils.deserializeItemStack("SNOW_BLOCK : 1"));
    
    CompanionStructure front_Bellyup = this.buildPart("front_Bellyup", new RelativeLocation(-0.85, 0.0, -0.5), false);
    front_Bellyup.getStand().setHeadPose(EulerAngle(90, 0, 0));
    front_Bellyup.getStand().setHelmet(BukkitUtils.deserializeItemStack("SKULL_ITEM:3 : 1 : skin>eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvODliODVkZThkODIyNWZjYmNlMzc5N2IxM2MxM2VlNDJhY2Q3YWFhZmFkZDg2MTkxM2Q0YWJjNzczODNmYmMifX19"));
    
    CompanionStructure back_Bellyup = this.buildPart("back_Bellyup", new RelativeLocation(-0.85, 0.0, -1.0), false);
    back_Bellyup.getStand().setHeadPose(EulerAngle(90, 0, 0));
    back_Bellyup.getStand().setHelmet(BukkitUtils.deserializeItemStack("SKULL_ITEM:3 : 1 : skin>eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvODliODVkZThkODIyNWZjYmNlMzc5N2IxM2MxM2VlNDJhY2Q3YWFhZmFkZDg2MTkxM2Q0YWJjNzczODNmYmMifX19"));
    
    CompanionStructure back_Left = this.buildPart("back_Left", new RelativeLocation(-0.7, 0.3, -1.0), true);
    back_Left.getStand().setHelmet(BukkitUtils.deserializeItemStack("SKULL_ITEM:3 : 1 : skin>eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvODliODVkZThkODIyNWZjYmNlMzc5N2IxM2MxM2VlNDJhY2Q3YWFhZmFkZDg2MTkxM2Q0YWJjNzczODNmYmMifX19"));
    
    CompanionStructure back_Right = this.buildPart("back_Right", new RelativeLocation(-0.7, -0.3, -1.0), true);
    back_Right.getStand().setHelmet(BukkitUtils.deserializeItemStack("SKULL_ITEM:3 : 1 : skin>eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvODliODVkZThkODIyNWZjYmNlMzc5N2IxM2MxM2VlNDJhY2Q3YWFhZmFkZDg2MTkxM2Q0YWJjNzczODNmYmMifX19"));
    
    this.spawn();
  }
  
  @Override
  public void setCompanionName(String name) {
    ((CompanionStand) NMS.getInstance().getHandle(this.structures.get(0).getStand())).setCompanionName(name);
  }
}
