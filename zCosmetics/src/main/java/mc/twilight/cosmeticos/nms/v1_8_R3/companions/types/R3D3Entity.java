package mc.twilight.cosmeticos.nms.v1_8_R3.companions.types;

import mc.twilight.cosmeticos.cosmetics.object.Companion;
import mc.twilight.cosmeticos.cosmetics.types.companions.CompanionStructure;
import mc.twilight.cosmeticos.cosmetics.types.companions.math.RelativeLocation;
import mc.twilight.cosmeticos.nms.NMS;
import mc.twilight.cosmeticos.nms.v1_8_R3.companions.CompanionSlime;
import mc.twilight.core.utils.BukkitUtils;

public class R3D3Entity extends CompanionSlime {
  
  public R3D3Entity(Companion companion) {
    super(companion);
    this.maxFrames = companion.getCosmetic().getFrames().isEmpty() ? 0 : companion.getCosmetic().getFrames().get(0).size();
    this.maxIdleFrames = companion.getCosmetic().getIdleFrames().isEmpty() ? 0 : companion.getCosmetic().getIdleFrames().get(0).size();
    
    CompanionStructure head = this.buildPart("head", new RelativeLocation(-0.15, 0.0, 0.0), true);
    head.getStand().setHelmet(BukkitUtils.deserializeItemStack("SKULL_ITEM:3 : 1 : skin>eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvN2NlYmM5Nzc5OGMyZTM2MDU1MWNhYjNkZDVkYjZkNTM0OTdmZTYzMDQwOTQxYzlhYzQ5MWE1OWNiZjM4M2E3YSJ9fX0="));
    
    CompanionStructure body = this.buildPart("body", new RelativeLocation(-1.45, 0.0, 0.0), false);
    body.getStand().setHelmet(BukkitUtils.deserializeItemStack("SKULL_ITEM:3 : 1 : skin>eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvM2ZlNzgxNjhmN2U1YzEyMjk0YWRhYzU5ZjBiOTQ5N2I2YWVkYzRjMzIzMzE4Y2FiYWQ4ZGJkYmJiMjJiNiJ9fX0="));
    
    this.spawn();
  }
  
  @Override
  public void setCompanionName(String name) {
    ((CompanionStand) NMS.getInstance().getHandle(this.structures.get(0).getStand())).setCompanionName(name);
  }
}
