package mc.twilight.cosmeticos.nms.v1_8_R3.companions.types;

import mc.twilight.cosmeticos.cosmetics.object.Companion;
import mc.twilight.cosmeticos.cosmetics.types.companions.CompanionStructure;
import mc.twilight.cosmeticos.cosmetics.types.companions.math.RelativeLocation;
import mc.twilight.cosmeticos.nms.NMS;
import mc.twilight.cosmeticos.nms.v1_8_R3.companions.CompanionSlime;
import mc.twilight.core.utils.BukkitUtils;

public class MiniMeEntity extends CompanionSlime {
  
  public MiniMeEntity(Companion companion) {
    super(companion);
    this.maxFrames = companion.getCosmetic().getFrames().isEmpty() ? 0 : companion.getCosmetic().getFrames().get(0).size();
    this.maxIdleFrames = companion.getCosmetic().getIdleFrames().isEmpty() ? 0 : companion.getCosmetic().getIdleFrames().get(0).size();
    
    CompanionStructure body = this.buildPart("body", new RelativeLocation(0.0, 0.0, 0.0), true);
    body.getStand().setVisible(true);
    body.getStand().setBasePlate(false);
    body.getStand().setHelmet(BukkitUtils.putProfileOnSkull(companion.getCompanionOwner(), BukkitUtils.deserializeItemStack("SKULL_ITEM:3 : 1")));
    body.getStand().setChestplate(BukkitUtils.deserializeItemStack("LEATHER_CHESTPLATE : 1"));
    body.getStand().setLeggings(BukkitUtils.deserializeItemStack("LEATHER_LEGGINGS : 1"));
    body.getStand().setBoots(BukkitUtils.deserializeItemStack("LEATHER_BOOTS : 1"));
    
    this.spawn();
  }
  
  @Override
  public void setCompanionName(String name) {
    ((CompanionStand) NMS.getInstance().getHandle(this.structures.get(0).getStand())).setCompanionName(name);
  }
}
