package mc.twilight.cosmeticos.nms.v1_8_R3.companions.types;

import mc.twilight.cosmeticos.cosmetics.object.Companion;
import mc.twilight.cosmeticos.cosmetics.types.companions.CompanionStructure;
import mc.twilight.cosmeticos.cosmetics.types.companions.math.RelativeLocation;
import mc.twilight.cosmeticos.nms.NMS;
import mc.twilight.cosmeticos.nms.v1_8_R3.companions.CompanionSlime;
import mc.twilight.core.utils.BukkitUtils;

import static mc.twilight.cosmeticos.utils.MathUtils.EulerAngle;

public class MythicDragonEntity extends CompanionSlime {
  
  public MythicDragonEntity(Companion companion) {
    super(companion);
    this.maxFrames = companion.getCosmetic().getFrames().isEmpty() ? 0 : companion.getCosmetic().getFrames().get(0).size();
    this.maxIdleFrames = companion.getCosmetic().getIdleFrames().isEmpty() ? 0 : companion.getCosmetic().getIdleFrames().get(0).size();
    
    CompanionStructure l_leg_b = this.buildPart("l_leg_b", new RelativeLocation(1.29, 0.42, -0.3), true);
    l_leg_b.getStand().setItemInHand(BukkitUtils.deserializeItemStack("CARPET:3 : 1"));
    
    CompanionStructure l_horn = this.buildPart("l_horn", new RelativeLocation(1.7, 0.275, 0.5), true);
    l_horn.getStand().setHeadPose(EulerAngle(90, 90, 180));
    l_horn.getStand().setHelmet(BukkitUtils.deserializeItemStack("CARPET:3 : 1"));
    
    CompanionStructure l_wing6 = this.buildPart("l_wing6", new RelativeLocation(1.4, 0.8, 0.0), false);
    l_wing6.getStand().setHeadPose(EulerAngle(90, 90, 163));
    l_wing6.getStand().setHelmet(BukkitUtils.deserializeItemStack("BANNER:12 : 1"));
    
    CompanionStructure r_horn = this.buildPart("r_horn", new RelativeLocation(1.7, -0.25, 0.5), true);
    r_horn.getStand().setHeadPose(EulerAngle(90, 90, 180));
    r_horn.getStand().setHelmet(BukkitUtils.deserializeItemStack("CARPET:3 : 1"));
    
    CompanionStructure body2 = this.buildPart("body2", new RelativeLocation(0.3, 0.0, -0.35), false);
    body2.getStand().setHelmet(BukkitUtils.deserializeItemStack("STAINED_CLAY:14 : 1"));
    
    CompanionStructure l_wing1 = this.buildPart("l_wing1", new RelativeLocation(0.7, -0.15, 0.0), false);
    l_wing1.getStand().setHeadPose(EulerAngle(90, 70, 150));
    l_wing1.getStand().setHelmet(BukkitUtils.deserializeItemStack("BANNER:12 : 1"));
    
    CompanionStructure back = this.buildPart("back", new RelativeLocation(1.45, 0.0, -0.4), true);
    back.getStand().setHeadPose(EulerAngle(90, 90, 180));
    back.getStand().setHelmet(BukkitUtils.deserializeItemStack("CARPET:3 : 1"));
    
    CompanionStructure body = this.buildPart("body", new RelativeLocation(0.3, 0.0, 0.2), false);
    body.getStand().setHelmet(BukkitUtils.deserializeItemStack("STAINED_CLAY:14 : 1"));
    
    CompanionStructure l_wing5 = this.buildPart("l_wing5", new RelativeLocation(0.75, -0.15, 0.0), false);
    l_wing5.getStand().setHeadPose(EulerAngle(90, 90, 149));
    l_wing5.getStand().setHelmet(BukkitUtils.deserializeItemStack("BANNER:12 : 1"));
    
    CompanionStructure l_wing4 = this.buildPart("l_wing4", new RelativeLocation(1.4, 0.65, 0.0), false);
    l_wing4.getStand().setHeadPose(EulerAngle(90, 110, 165));
    l_wing4.getStand().setHelmet(BukkitUtils.deserializeItemStack("BANNER:12 : 1"));
    
    CompanionStructure l_wing3 = this.buildPart("l_wing3", new RelativeLocation(1.4, 0.65, 0.0), false);
    l_wing3.getStand().setHeadPose(EulerAngle(90, 70, 165));
    l_wing3.getStand().setHelmet(BukkitUtils.deserializeItemStack("BANNER:12 : 1"));
    
    CompanionStructure l_wing2 = this.buildPart("l_wing2", new RelativeLocation(0.7, -0.15, 0.0), false);
    l_wing2.getStand().setHeadPose(EulerAngle(90, 110, 150));
    l_wing2.getStand().setHelmet(BukkitUtils.deserializeItemStack("BANNER:12 : 1"));
    
    CompanionStructure head = this.buildPart("head", new RelativeLocation(0.4, 0.0, 0.5), false);
    head.getStand().setHelmet(BukkitUtils.deserializeItemStack("SKULL_ITEM:3 : 1 : skin>eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvYWMyMGZiYmUxNWQ2ZTU5NDZmOTUyYmMxYjA3NDQ4NmRkMzNlNDZiMjM2MWY3Y2Y2YjBkYTU5MzIzMDc0NCJ9fX0="));
    
    CompanionStructure r_wing1 = this.buildPart("r_wing1", new RelativeLocation(0.7, 0.15, 0.0), false);
    r_wing1.getStand().setHeadPose(EulerAngle(-90, -70, 30));
    r_wing1.getStand().setHelmet(BukkitUtils.deserializeItemStack("BANNER:12 : 1"));
    
    CompanionStructure r_wing3 = this.buildPart("r_wing3", new RelativeLocation(1.4, -0.65, 0.0), false);
    r_wing3.getStand().setHeadPose(EulerAngle(-90, -70, 15));
    r_wing3.getStand().setHelmet(BukkitUtils.deserializeItemStack("BANNER:12 : 1"));
    
    CompanionStructure r_wing2 = this.buildPart("r_wing2", new RelativeLocation(0.7, 0.15, 0.0), false);
    r_wing2.getStand().setHeadPose(EulerAngle(-90, -110, 30));
    r_wing2.getStand().setHelmet(BukkitUtils.deserializeItemStack("BANNER:12 : 1"));
    
    CompanionStructure r_wing5 = this.buildPart("r_wing5", new RelativeLocation(0.75, 0.15, 0.0), false);
    r_wing5.getStand().setHeadPose(EulerAngle(-90, -90, 31));
    r_wing5.getStand().setHelmet(BukkitUtils.deserializeItemStack("BANNER:12 : 1"));
    
    CompanionStructure r_wing4 = this.buildPart("r_wing4", new RelativeLocation(1.4, -0.65, 0.0), false);
    r_wing4.getStand().setHeadPose(EulerAngle(-90, -110, 15));
    r_wing4.getStand().setHelmet(BukkitUtils.deserializeItemStack("BANNER:12 : 1"));
    
    CompanionStructure r_wing6 = this.buildPart("r_wing6", new RelativeLocation(1.4, -0.8, 0.0), false);
    r_wing6.getStand().setHeadPose(EulerAngle(-90, -90, 17));
    r_wing6.getStand().setHelmet(BukkitUtils.deserializeItemStack("BANNER:12 : 1"));
    
    CompanionStructure r_leg_b = this.buildPart("r_leg_b", new RelativeLocation(1.29, 0.0, -0.3), true);
    r_leg_b.getStand().setItemInHand(BukkitUtils.deserializeItemStack("CARPET:3 : 1"));
    
    CompanionStructure l_leg = this.buildPart("l_leg", new RelativeLocation(1.29, 0.42, 0.4), true);
    l_leg.getStand().setItemInHand(BukkitUtils.deserializeItemStack("CARPET:3 : 1"));
    
    CompanionStructure tail1 = this.buildPart("tail1", new RelativeLocation(1.0, 0.0, -0.6), true);
    tail1.getStand().setHelmet(BukkitUtils.deserializeItemStack("STAINED_CLAY:14 : 1"));
    
    CompanionStructure r_leg = this.buildPart("r_leg", new RelativeLocation(1.29, 0.0, 0.4), true);
    r_leg.getStand().setItemInHand(BukkitUtils.deserializeItemStack("CARPET:3 : 1"));
    
    CompanionStructure tail2 = this.buildPart("tail2", new RelativeLocation(0.9, 0.0, -0.8), true);
    tail2.getStand().setHelmet(BukkitUtils.deserializeItemStack("STAINED_CLAY:14 : 1"));
    
    CompanionStructure tail3 = this.buildPart("tail3", new RelativeLocation(0.8, 0.0, -1.0), true);
    tail3.getStand().setHelmet(BukkitUtils.deserializeItemStack("STAINED_CLAY:14 : 1"));
    
    CompanionStructure tail4 = this.buildPart("tail4", new RelativeLocation(0.7, 0.0, -1.2), true);
    tail4.getStand().setHelmet(BukkitUtils.deserializeItemStack("STAINED_CLAY:14 : 1"));
    
    this.spawn();
  }
  
  @Override
  public void setCompanionName(String name) {
    ((CompanionStand) NMS.getInstance().getHandle(this.structures.get(12).getStand())).setCompanionName(name);
  }
}
