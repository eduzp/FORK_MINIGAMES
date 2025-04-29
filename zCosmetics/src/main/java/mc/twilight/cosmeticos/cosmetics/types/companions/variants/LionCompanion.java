package mc.twilight.cosmeticos.cosmetics.types.companions.variants;

import mc.twilight.cosmeticos.cosmetics.types.CompanionCosmetic;
import mc.twilight.cosmeticos.cosmetics.types.companions.CompanionAnimation;
import mc.twilight.cosmeticos.cosmetics.types.companions.name.CompanionNames;
import mc.twilight.cosmeticos.utils.MathUtils;
import mc.twilight.core.utils.enums.EnumRarity;

import java.util.Arrays;

public class LionCompanion extends CompanionCosmetic {
  
  public LionCompanion() {
    super("Leão", 5, EnumRarity.DIVINO,
        "SKULL_ITEM:3 : 1 : nome>Leão : desc>&7Divirta-se pelos nossos lobbies\n&7acompanhado de um Leão! : skin>eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvODU1YjIyZDUxYjFjYjQ4NDljZjNiZWRlMjg2MDk0ZTMxZWE2MTgzMmExNTM2NWY4YTA2OGZjNTVmNGMzNjg5ZCJ9fX0=");
    
    this.frames.createKeyFrame(0, Arrays.asList(
        new CompanionAnimation(MathUtils.EulerAngle(0, 0, 0), "front_Left", CompanionAnimation.MovementType.HEAD),
        new CompanionAnimation(MathUtils.EulerAngle(0, 0, 0), "back_Left", CompanionAnimation.MovementType.HEAD),
        new CompanionAnimation(MathUtils.EulerAngle(0, 0, 0), "front_Right", CompanionAnimation.MovementType.HEAD),
        new CompanionAnimation(MathUtils.EulerAngle(0, 0, 0), "back_Right", CompanionAnimation.MovementType.HEAD),
        new CompanionAnimation(MathUtils.EulerAngle(183, 500, 14), "tail", CompanionAnimation.MovementType.ARM)));
    this.frames.createKeyFrame(1, Arrays.asList(
        new CompanionAnimation(MathUtils.EulerAngle(0, 0, 0), "front_Left", CompanionAnimation.MovementType.HEAD),
        new CompanionAnimation(MathUtils.EulerAngle(-35, 0, 0), "back_Left", CompanionAnimation.MovementType.HEAD),
        new CompanionAnimation(MathUtils.EulerAngle(-35, 0, 0), "front_Right", CompanionAnimation.MovementType.HEAD),
        new CompanionAnimation(MathUtils.EulerAngle(0, 0, 0), "back_Right", CompanionAnimation.MovementType.HEAD),
        new CompanionAnimation(MathUtils.EulerAngle(183, 500, 0), "tail", CompanionAnimation.MovementType.ARM)));
    this.frames.createKeyFrame(2, Arrays.asList(
        new CompanionAnimation(MathUtils.EulerAngle(-35, 0, 0), "front_Left", CompanionAnimation.MovementType.HEAD),
        new CompanionAnimation(MathUtils.EulerAngle(0, 0, 0), "back_Left", CompanionAnimation.MovementType.HEAD),
        new CompanionAnimation(MathUtils.EulerAngle(0, 0, 0), "front_Right", CompanionAnimation.MovementType.HEAD),
        new CompanionAnimation(MathUtils.EulerAngle(-35, 0, 0), "back_Right", CompanionAnimation.MovementType.HEAD),
        new CompanionAnimation(MathUtils.EulerAngle(183, 500, 29), "tail", CompanionAnimation.MovementType.ARM)));
    
    this.frames.addFrames(0, 0, 1, 2, 2, 1, 0);
    this.frames.addFrames(1, 0, 1, 2, 2, 1, 0);
    this.frames.addFrames(2, 0, 1, 2, 2, 1, 0);
    this.frames.addFrames(3, 0, 1, 2, 2, 1, 0);
    this.frames.addFrames(4, 0, 1, 2, 2, 1, 0);
  }
  
  @Override
  public CompanionNames getNameEnum() {
    return CompanionNames.LION;
  }
}
