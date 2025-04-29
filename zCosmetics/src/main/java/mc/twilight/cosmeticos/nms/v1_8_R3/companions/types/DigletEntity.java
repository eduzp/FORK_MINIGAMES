package mc.twilight.cosmeticos.nms.v1_8_R3.companions.types;

import mc.twilight.cosmeticos.cosmetics.object.Companion;
import mc.twilight.cosmeticos.cosmetics.types.companions.CompanionStructure;
import mc.twilight.cosmeticos.cosmetics.types.companions.math.RelativeLocation;
import mc.twilight.cosmeticos.nms.NMS;
import mc.twilight.cosmeticos.nms.v1_8_R3.companions.CompanionSlime;
import mc.twilight.core.utils.BukkitUtils;

public class DigletEntity extends CompanionSlime {
  
  public DigletEntity(Companion companion) {
    super(companion);
    this.maxFrames = companion.getCosmetic().getFrames().isEmpty() ? 0 : companion.getCosmetic().getFrames().get(0).size();
    this.maxIdleFrames = companion.getCosmetic().getIdleFrames().isEmpty() ? 0 : companion.getCosmetic().getIdleFrames().get(0).size();
    
    CompanionStructure buik1 = this.buildPart("buik1", new RelativeLocation(-0.7, 0.0, 0.0), true);
    buik1.getStand().setHelmet(BukkitUtils.deserializeItemStack("SKULL_ITEM:3 : 1 : skin>eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvYmY0NTY1YmFjNDFlZDYzNzY0NWI2M2RkN2VmNmE3ZGVjMjRkZGE4MDJhN2I0MzhjODZlMWRiNzQzMGQwNjAifX19"));
    
    CompanionStructure hoofd = this.buildPart("hoofd", new RelativeLocation(-0.3, 0.0, 0.0), true);
    hoofd.getStand().setHelmet(BukkitUtils.deserializeItemStack("SKULL_ITEM:3 : 1 : skin>eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvNTRkZTlmNzI3OTdkMTY5NTk4YzEwZGJmMzE5MmJhN2Q5OWZlZjg3YmRjZmM5OWViZTk3NjYyNDI0NWU5OTgifX19"));
    
    this.spawn();
  }
  
  @Override
  public void setCompanionName(String name) {
    ((CompanionStand) NMS.getInstance().getHandle(this.structures.get(1).getStand())).setCompanionName(name);
  }
}
