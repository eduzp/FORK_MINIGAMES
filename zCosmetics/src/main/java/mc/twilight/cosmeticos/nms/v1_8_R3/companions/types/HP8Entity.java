package mc.twilight.cosmeticos.nms.v1_8_R3.companions.types;

import mc.twilight.cosmeticos.cosmetics.object.Companion;
import mc.twilight.cosmeticos.cosmetics.types.companions.CompanionStructure;
import mc.twilight.cosmeticos.cosmetics.types.companions.math.RelativeLocation;
import mc.twilight.cosmeticos.nms.NMS;
import mc.twilight.cosmeticos.nms.v1_8_R3.companions.CompanionSlime;
import mc.twilight.core.utils.BukkitUtils;

import static mc.twilight.cosmeticos.utils.MathUtils.EulerAngle;

public class HP8Entity extends CompanionSlime {
  
  public HP8Entity(Companion companion) {
    super(companion);
    this.maxFrames = companion.getCosmetic().getFrames().get(0).size();
    
    CompanionStructure body = this.buildPart("body", new RelativeLocation(-1.1, 0.3, 0), false);
    body.getStand().setHeadPose(EulerAngle(0, 0, 269.863122));
    body.getStand().setHelmet(BukkitUtils.deserializeItemStack(
        "SKULL_ITEM:3 : 1 : skin>eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvYjc4N2RjNDQxNzJkNjg4NzQ3NmFhMTk3ZTBmZDM1ZDY3ZGMzN2MzNzlmYmQyMmU2NDZmMWFmMTczYWQwNmE2NCJ9fX0="));
    
    CompanionStructure head = this.buildPart("head", new RelativeLocation(-0.1, 0, 0), true);
    head.getStand().setHelmet(BukkitUtils.deserializeItemStack(
        "SKULL_ITEM:3 : 1 : skin>eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvNzZjNjhlODJhZDMyMmU1OWZhNDUwN2M0YTM4MWIxNmEwMzRmMDI2ZmRkNmQ3OTNiYTZkMGFkNDFlNjUwZGYyMCJ9fX0="));
    
    this.spawn();
  }
  
  @Override
  public void setCompanionName(String name) {
    ((CompanionStand) NMS.getInstance().getHandle(this.structures.get(1).getStand())).setCompanionName(name);
  }
}
