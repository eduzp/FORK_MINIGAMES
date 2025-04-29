package mc.twilight.cosmeticos.nms.v1_8_R3.companions.types;

import mc.twilight.cosmeticos.cosmetics.object.Companion;
import mc.twilight.cosmeticos.cosmetics.types.companions.CompanionStructure;
import mc.twilight.cosmeticos.cosmetics.types.companions.math.RelativeLocation;
import mc.twilight.cosmeticos.nms.NMS;
import mc.twilight.cosmeticos.nms.v1_8_R3.companions.CompanionSlime;
import mc.twilight.core.utils.BukkitUtils;

import static mc.twilight.cosmeticos.utils.MathUtils.EulerAngle;

public class DuckEntity extends CompanionSlime {
  
  public DuckEntity(Companion companion) {
    super(companion);
    this.maxFrames = companion.getCosmetic().getFrames().get(0).size();
    this.maxIdleFrames = companion.getCosmetic().getIdleFrames().get(0).size();
    
    CompanionStructure body = this.buildPart("body", new RelativeLocation(-0.149, 0, 0.2), true);
    body.getStand().setHeadPose(EulerAngle(180, 0, 0));
    body.getStand().setHelmet(BukkitUtils.deserializeItemStack("GOLD_BLOCK : 1"));
    
    CompanionStructure head = this.buildPart("head", new RelativeLocation(-0.2, 0, 0.5), true);
    head.getStand().setHelmet(BukkitUtils.deserializeItemStack(
        "SKULL_ITEM:3 : 1 : skin>eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvNzUyMDcxN2FmZjJhNjYwYjI5NDExYzAxNGI4ODkxOGI3MmQxMWQwODUzYWMyYzZmZjRlZWUyODg4ZTMzYTcifX19"));
    
    CompanionStructure leftLeg = this.buildPart("leftLeg", new RelativeLocation(-0.3, 0.42, 0.3), true);
    leftLeg.getStand().setItemInHand(BukkitUtils.deserializeItemStack("STONE_SLAB2 : 1"));
    
    CompanionStructure rightLeg = this.buildPart("rightLeg", new RelativeLocation(-0.3, 0, 0.3), true);
    rightLeg.getStand().setItemInHand(BukkitUtils.deserializeItemStack("STONE_SLAB2 : 1"));
    
    CompanionStructure leftWing = this.buildPart("leftWing", new RelativeLocation(-1.2, 1.1, 0.3), false);
    leftWing.getStand().setRightArmPose(EulerAngle(-90, 90, 0));
    leftWing.getStand().setItemInHand(BukkitUtils.deserializeItemStack("INK_SACK:11 : 1"));
    
    CompanionStructure rightWing = this.buildPart("rightWing", new RelativeLocation(-1.2, 0.65, 0.3), false);
    rightWing.getStand().setRightArmPose(EulerAngle(-90, 90, 0));
    rightWing.getStand().setItemInHand(BukkitUtils.deserializeItemStack("INK_SACK:11 : 1"));
    
    this.spawn();
  }
  
  @Override
  public void setCompanionName(String name) {
    ((CompanionStand) NMS.getInstance().getHandle(this.structures.get(1).getStand())).setCompanionName(name);
  }
}
