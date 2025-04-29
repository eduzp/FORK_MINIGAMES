package mc.twilight.cosmeticos.nms.v1_8_R3.companions.types;

import mc.twilight.cosmeticos.cosmetics.object.Companion;
import mc.twilight.cosmeticos.cosmetics.types.companions.CompanionStructure;
import mc.twilight.cosmeticos.cosmetics.types.companions.math.RelativeLocation;
import mc.twilight.cosmeticos.nms.NMS;
import mc.twilight.cosmeticos.nms.v1_8_R3.companions.CompanionSlime;
import mc.twilight.core.utils.BukkitUtils;

import static mc.twilight.cosmeticos.utils.MathUtils.EulerAngle;

public class PenguinEntity extends CompanionSlime {
  
  public PenguinEntity(Companion companion) {
    super(companion);
    this.maxFrames = companion.getCosmetic().getFrames().isEmpty() ? 0 : companion.getCosmetic().getFrames().get(0).size();
    this.maxIdleFrames = companion.getCosmetic().getIdleFrames().isEmpty() ? 0 : companion.getCosmetic().getIdleFrames().get(0).size();
    
    CompanionStructure head = this.buildPart("head", new RelativeLocation(-0.05, 0.0, 0.1), true);
    head.getStand().setHelmet(BukkitUtils.deserializeItemStack("SKULL_ITEM:3 : 1 : skin>eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvZDNjNTdmYWNiYjNhNGRiN2ZkNTViNWMwZGM3ZDE5YzE5Y2IwODEzYzc0OGNjYzk3MTBjNzE0NzI3NTUxZjViOSJ9fX0="));
    
    CompanionStructure r_wing = this.buildPart("r_wing", new RelativeLocation(-0.65, -1.0, 0.35, 90.0F), false);
    r_wing.getStand().setRightArmPose(EulerAngle(-45, 180, 0));
    r_wing.getStand().setItemInHand(BukkitUtils.deserializeItemStack("COAL : 1"));
    
    CompanionStructure r_leg = this.buildPart("r_leg", new RelativeLocation(-0.3, 0.0, 0.5), true);
    r_leg.getStand().setItemInHand(BukkitUtils.deserializeItemStack("STONE_SLAB2 : 1"));
    
    CompanionStructure l_wing = this.buildPart("l_wing", new RelativeLocation(-0.65, 1.0, 0.4, 90.0F), false);
    l_wing.getStand().setRightArmPose(EulerAngle(-45, 0, 0));
    l_wing.getStand().setItemInHand(BukkitUtils.deserializeItemStack("COAL : 1"));
    
    CompanionStructure l_leg = this.buildPart("l_leg", new RelativeLocation(-0.3, 0.42, 0.5), true);
    l_leg.getStand().setItemInHand(BukkitUtils.deserializeItemStack("STONE_SLAB2 : 1"));
    
    CompanionStructure body2 = this.buildPart("body2", new RelativeLocation(-0.5, 0.0, 0.2), true);
    body2.getStand().setHelmet(BukkitUtils.deserializeItemStack("SNOW_BLOCK : 1"));
    
    CompanionStructure body = this.buildPart("body", new RelativeLocation(-1.3, 0.0, 0.1), false);
    body.getStand().setHelmet(BukkitUtils.deserializeItemStack("COAL_BLOCK : 1"));
    
    this.spawn();
  }
  
  @Override
  public void setCompanionName(String name) {
    ((CompanionStand) NMS.getInstance().getHandle(this.structures.get(0).getStand())).setCompanionName(name);
  }
}
