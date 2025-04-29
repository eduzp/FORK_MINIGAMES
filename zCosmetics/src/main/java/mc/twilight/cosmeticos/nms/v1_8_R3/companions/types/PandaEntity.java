package mc.twilight.cosmeticos.nms.v1_8_R3.companions.types;

import mc.twilight.cosmeticos.cosmetics.object.Companion;
import mc.twilight.cosmeticos.cosmetics.types.companions.CompanionStructure;
import mc.twilight.cosmeticos.cosmetics.types.companions.math.RelativeLocation;
import mc.twilight.cosmeticos.nms.NMS;
import mc.twilight.cosmeticos.nms.v1_8_R3.companions.CompanionSlime;
import mc.twilight.core.utils.BukkitUtils;

import static mc.twilight.cosmeticos.utils.MathUtils.EulerAngle;

public class PandaEntity extends CompanionSlime {
  
  public PandaEntity(Companion companion) {
    super(companion);
    this.maxFrames = companion.getCosmetic().getFrames().get(0).size();
    
    CompanionStructure body = this.buildPart("body", new RelativeLocation(-1, 0, 0.3), false);
    body.getStand().setHelmet(BukkitUtils.deserializeItemStack(
        "SKULL_ITEM:3 : 1 : skin>eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvOTQ3YjY4ZWQwMjE2MzJmNDA4ZmMyMjNlZjc5NTdjMjQ3ODZhZTUwOWE4NGU2ZjE4YTM3MWE1NWMzZDhjZjkwOSJ9fX0="));
    
    CompanionStructure backLeft = this.buildPart("backLeft", new RelativeLocation(-1, 0.2, -1), true);
    backLeft.getStand().setRightArmPose(EulerAngle(190, 500, 13));
    backLeft.getStand().setItemInHand(BukkitUtils.deserializeItemStack("COAL_BLOCK : 1"));
    
    CompanionStructure backRight = this.buildPart("backRight", new RelativeLocation(-1, -0.2, -1), true);
    backRight.getStand().setRightArmPose(EulerAngle(190, 500, 13));
    backRight.getStand().setItemInHand(BukkitUtils.deserializeItemStack("COAL_BLOCK : 1"));
    
    CompanionStructure frontLeft = this.buildPart("frontLeft", new RelativeLocation(-1, 0.2, 0), true);
    frontLeft.getStand().setRightArmPose(EulerAngle(190, 500, 13));
    frontLeft.getStand().setItemInHand(BukkitUtils.deserializeItemStack("COAL_BLOCK : 1"));
    
    CompanionStructure frontRight = this.buildPart("frontRight", new RelativeLocation(-1, -0.2, 0), true);
    frontRight.getStand().setRightArmPose(EulerAngle(190, 500, 13));
    frontRight.getStand().setItemInHand(BukkitUtils.deserializeItemStack("COAL_BLOCK : 1"));
    
    CompanionStructure backSlab = this.buildPart("backSlab", new RelativeLocation(-.95, 0, -.9325), false);
    backSlab.getStand().setHeadPose(EulerAngle(90, 0, 0));
    backSlab.getStand().setHelmet(BukkitUtils.deserializeItemStack("STEP:7 : 1"));
    
    CompanionStructure frontSlab = this.buildPart("frontSlab", new RelativeLocation(-.95, 0, 0), false);
    frontSlab.getStand().setHeadPose(EulerAngle(90, 0, 0));
    frontSlab.getStand().setHelmet(BukkitUtils.deserializeItemStack("STEP:7 : 1"));
    
    CompanionStructure middleBelly = this.buildPart("middleBelly", new RelativeLocation(-.95, 0, -.625), false);
    middleBelly.getStand().setHeadPose(EulerAngle(90, 0, 0));
    middleBelly.getStand().setHelmet(BukkitUtils.deserializeItemStack("COAL_BLOCK : 1"));
    
    CompanionStructure tail = this.buildPart("tail", new RelativeLocation(-.25, -.05, -1.15), true);
    tail.getStand().setRightArmPose(EulerAngle(183, 500, 13));
    tail.getStand().setItemInHand(BukkitUtils.deserializeItemStack("COAL_BLOCK : 1"));
    
    this.spawn();
  }
  
  @Override
  public void setCompanionName(String name) {
    ((CompanionStand) NMS.getInstance().getHandle(this.structures.get(0).getStand())).setCompanionName(name);
  }
}
