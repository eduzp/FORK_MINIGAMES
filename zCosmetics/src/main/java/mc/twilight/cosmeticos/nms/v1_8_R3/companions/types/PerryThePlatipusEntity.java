package mc.twilight.cosmeticos.nms.v1_8_R3.companions.types;

import mc.twilight.cosmeticos.cosmetics.object.Companion;
import mc.twilight.cosmeticos.cosmetics.types.companions.CompanionStructure;
import mc.twilight.cosmeticos.cosmetics.types.companions.math.RelativeLocation;
import mc.twilight.cosmeticos.nms.NMS;
import mc.twilight.cosmeticos.nms.v1_8_R3.companions.CompanionSlime;
import mc.twilight.core.utils.BukkitUtils;

import static mc.twilight.cosmeticos.utils.MathUtils.EulerAngle;

public class PerryThePlatipusEntity extends CompanionSlime {
  
  public PerryThePlatipusEntity(Companion companion) {
    super(companion);
    this.maxFrames = companion.getCosmetic().getFrames().isEmpty() ? 0 : companion.getCosmetic().getFrames().get(0).size();
    this.maxIdleFrames = companion.getCosmetic().getIdleFrames().isEmpty() ? 0 : companion.getCosmetic().getIdleFrames().get(0).size();
    
    CompanionStructure head = this.buildPart("head", new RelativeLocation(-0.95, 0.0, 0.0), false);
    head.getStand().setHelmet(BukkitUtils.deserializeItemStack("SKULL_ITEM:3 : 1 : skin>eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvN2U3YzE4MWExOWYzNjdmMWFjOTQ5NWEyYmU3NjFhZDBkOTE1NzRiNzVlZDc5OGY3NjVhMWVlNmJlNDFhODcxIn19fQ=="));
    
    CompanionStructure front_Left = this.buildPart("front_Left", new RelativeLocation(-1.0, 0.1, -0.2), true);
    front_Left.getStand().setRightArmPose(EulerAngle(180, 500, 25));
    front_Left.getStand().setItemInHand(BukkitUtils.deserializeItemStack("SKULL_ITEM:3 : 1 : skin>eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvZTZkOTI2ZmI1NzRlMjI5MzAyMjJhOWMzNTNjMGM1YjRiNDVjMTc5NjIxZDEzZWY0NzUzZThiYzk0YjEzYjcifX19"));
    
    CompanionStructure front_Right = this.buildPart("front_Right", new RelativeLocation(-1.0, -0.3, -0.2), true);
    front_Right.getStand().setRightArmPose(EulerAngle(180, 500, 25));
    front_Right.getStand().setItemInHand(BukkitUtils.deserializeItemStack("SKULL_ITEM:3 : 1 : skin>eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvZTZkOTI2ZmI1NzRlMjI5MzAyMjJhOWMzNTNjMGM1YjRiNDVjMTc5NjIxZDEzZWY0NzUzZThiYzk0YjEzYjcifX19"));
    
    CompanionStructure tail = this.buildPart("tail", new RelativeLocation(-0.5, 0.7, -1.75), false);
    tail.getStand().setRightArmPose(EulerAngle(0, -22.5, 90));
    tail.getStand().setItemInHand(BukkitUtils.deserializeItemStack("BANNER:14 : 1"));
    
    CompanionStructure body2 = this.buildPart("body2", new RelativeLocation(-1.35, 0.0, -0.75), false);
    body2.getStand().setHelmet(BukkitUtils.deserializeItemStack("SKULL_ITEM:3 : 1 : skin>eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvZTZkOTI2ZmI1NzRlMjI5MzAyMjJhOWMzNTNjMGM1YjRiNDVjMTc5NjIxZDEzZWY0NzUzZThiYzk0YjEzYjcifX19"));
    
    CompanionStructure body = this.buildPart("body", new RelativeLocation(-1.35, 0.0, -0.25), false);
    body.getStand().setHelmet(BukkitUtils.deserializeItemStack("SKULL_ITEM:3 : 1 : skin>eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvZTZkOTI2ZmI1NzRlMjI5MzAyMjJhOWMzNTNjMGM1YjRiNDVjMTc5NjIxZDEzZWY0NzUzZThiYzk0YjEzYjcifX19"));
    
    CompanionStructure back_Left = this.buildPart("back_Left", new RelativeLocation(-1.0, 0.1, -1.0), true);
    back_Left.getStand().setRightArmPose(EulerAngle(180, 500, 25));
    back_Left.getStand().setItemInHand(BukkitUtils.deserializeItemStack("SKULL_ITEM:3 : 1 : skin>eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvZTZkOTI2ZmI1NzRlMjI5MzAyMjJhOWMzNTNjMGM1YjRiNDVjMTc5NjIxZDEzZWY0NzUzZThiYzk0YjEzYjcifX19"));
    
    CompanionStructure back_Right = this.buildPart("back_Right", new RelativeLocation(-1.0, -0.3, -1.0), true);
    back_Right.getStand().setRightArmPose(EulerAngle(180, 500, 25));
    back_Right.getStand().setItemInHand(BukkitUtils.deserializeItemStack("SKULL_ITEM:3 : 1 : skin>eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvZTZkOTI2ZmI1NzRlMjI5MzAyMjJhOWMzNTNjMGM1YjRiNDVjMTc5NjIxZDEzZWY0NzUzZThiYzk0YjEzYjcifX19"));
    
    this.spawn();
  }
  
  @Override
  public void setCompanionName(String name) {
    ((CompanionStand) NMS.getInstance().getHandle(this.structures.get(0).getStand())).setCompanionName(name);
  }
}
