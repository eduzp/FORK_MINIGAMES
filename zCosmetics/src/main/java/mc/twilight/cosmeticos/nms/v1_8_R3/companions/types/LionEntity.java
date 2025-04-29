package mc.twilight.cosmeticos.nms.v1_8_R3.companions.types;

import mc.twilight.cosmeticos.cosmetics.object.Companion;
import mc.twilight.cosmeticos.cosmetics.types.companions.CompanionStructure;
import mc.twilight.cosmeticos.cosmetics.types.companions.math.RelativeLocation;
import mc.twilight.cosmeticos.nms.NMS;
import mc.twilight.cosmeticos.nms.v1_8_R3.companions.CompanionSlime;
import mc.twilight.core.utils.BukkitUtils;

import static mc.twilight.cosmeticos.utils.MathUtils.EulerAngle;

public class LionEntity extends CompanionSlime {
  
  public LionEntity(Companion companion) {
    super(companion);
    this.maxFrames = companion.getCosmetic().getFrames().isEmpty() ? 0 : companion.getCosmetic().getFrames().get(0).size();
    this.maxIdleFrames = companion.getCosmetic().getIdleFrames().isEmpty() ? 0 : companion.getCosmetic().getIdleFrames().get(0).size();
    
    CompanionStructure head = this.buildPart("head", new RelativeLocation(-1.0, 0.0, 0.245), false);
    head.getStand().setHelmet(BukkitUtils.deserializeItemStack("SKULL_ITEM:3 : 1 : skin>eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvODU1YjIyZDUxYjFjYjQ4NDljZjNiZWRlMjg2MDk0ZTMxZWE2MTgzMmExNTM2NWY4YTA2OGZjNTVmNGMzNjg5ZCJ9fX0="));
    
    CompanionStructure front_Left = this.buildPart("front_Left", new RelativeLocation(-0.7, 0.3, 0.0), true);
    front_Left.getStand().setHelmet(BukkitUtils.deserializeItemStack("SKULL_ITEM:3 : 1 : skin>eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvYWFlMzIwYWQ5YmFiNGE3OTQ2MjJiZWI0MzQ4ZTdjZmNjY2I4ZjFlNmJjNGJlYTFjMjlmMjI1N2MzOGM2In19fQ=="));
    
    CompanionStructure middle_Belly = this.buildPart("middle_Belly", new RelativeLocation(-0.5, 0.1, -0.688), true);
    middle_Belly.getStand().setHelmet(BukkitUtils.deserializeItemStack("SKULL_ITEM:3 : 1 : skin>eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvYzk5ZGI1MThmYTA2YWM5ODUxMWU1Yjk1YzE2NWQyN2IwZjIyYjkzM2MyYjg5Y2RmN2U4NzJmNTI3MWU3YzdlIn19fQ=="));
    
    CompanionStructure front_Right = this.buildPart("front_Right", new RelativeLocation(-0.7, -0.3, 0.0), true);
    front_Right.getStand().setHelmet(BukkitUtils.deserializeItemStack("SKULL_ITEM:3 : 1 : skin>eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvYWFlMzIwYWQ5YmFiNGE3OTQ2MjJiZWI0MzQ4ZTdjZmNjY2I4ZjFlNmJjNGJlYTFjMjlmMjI1N2MzOGM2In19fQ=="));
    
    CompanionStructure middle_Belly3 = this.buildPart("middle_Belly3", new RelativeLocation(-0.5, 0.1, -0.288), true);
    middle_Belly3.getStand().setHelmet(BukkitUtils.deserializeItemStack("SKULL_ITEM:3 : 1 : skin>eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvYzk5ZGI1MThmYTA2YWM5ODUxMWU1Yjk1YzE2NWQyN2IwZjIyYjkzM2MyYjg5Y2RmN2U4NzJmNTI3MWU3YzdlIn19fQ=="));
    
    CompanionStructure middle_Belly2 = this.buildPart("middle_Belly2", new RelativeLocation(-0.5, -0.1, -0.688), true);
    middle_Belly2.getStand().setHelmet(BukkitUtils.deserializeItemStack("SKULL_ITEM:3 : 1 : skin>eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvYzk5ZGI1MThmYTA2YWM5ODUxMWU1Yjk1YzE2NWQyN2IwZjIyYjkzM2MyYjg5Y2RmN2U4NzJmNTI3MWU3YzdlIn19fQ=="));
    
    CompanionStructure tail = this.buildPart("tail", new RelativeLocation(-0.4, -0.05, -1.15), true);
    tail.getStand().setRightArmPose(EulerAngle(183, 500, 13));
    tail.getStand().setItemInHand(BukkitUtils.deserializeItemStack("SKULL_ITEM:3 : 1 : skin>eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvYzk5ZGI1MThmYTA2YWM5ODUxMWU1Yjk1YzE2NWQyN2IwZjIyYjkzM2MyYjg5Y2RmN2U4NzJmNTI3MWU3YzdlIn19fQ=="));
    
    CompanionStructure middle_Belly4 = this.buildPart("middle_Belly4", new RelativeLocation(-0.5, -0.1, -0.288), true);
    middle_Belly4.getStand().setHelmet(BukkitUtils.deserializeItemStack("SKULL_ITEM:3 : 1 : skin>eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvYzk5ZGI1MThmYTA2YWM5ODUxMWU1Yjk1YzE2NWQyN2IwZjIyYjkzM2MyYjg5Y2RmN2U4NzJmNTI3MWU3YzdlIn19fQ=="));
    
    CompanionStructure back_Left = this.buildPart("back_Left", new RelativeLocation(-0.7, 0.3, -1.0), true);
    back_Left.getStand().setHelmet(BukkitUtils.deserializeItemStack("SKULL_ITEM:3 : 1 : skin>eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvYWFlMzIwYWQ5YmFiNGE3OTQ2MjJiZWI0MzQ4ZTdjZmNjY2I4ZjFlNmJjNGJlYTFjMjlmMjI1N2MzOGM2In19fQ=="));
    
    CompanionStructure back_Right = this.buildPart("back_Right", new RelativeLocation(-0.7, -0.3, -1.0), true);
    back_Right.getStand().setHelmet(BukkitUtils.deserializeItemStack("SKULL_ITEM:3 : 1 : skin>eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvYWFlMzIwYWQ5YmFiNGE3OTQ2MjJiZWI0MzQ4ZTdjZmNjY2I4ZjFlNmJjNGJlYTFjMjlmMjI1N2MzOGM2In19fQ=="));
    
    this.spawn();
  }
  
  @Override
  public void setCompanionName(String name) {
    ((CompanionStand) NMS.getInstance().getHandle(this.structures.get(0).getStand())).setCompanionName(name);
  }
}
