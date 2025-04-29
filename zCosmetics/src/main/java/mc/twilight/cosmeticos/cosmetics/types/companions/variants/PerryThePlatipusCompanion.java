package mc.twilight.cosmeticos.cosmetics.types.companions.variants;

import mc.twilight.cosmeticos.cosmetics.types.CompanionCosmetic;
import mc.twilight.cosmeticos.cosmetics.types.companions.CompanionAnimation;
import mc.twilight.cosmeticos.cosmetics.types.companions.name.CompanionNames;
import mc.twilight.cosmeticos.utils.MathUtils;
import mc.twilight.core.utils.enums.EnumRarity;

import java.util.Arrays;

public class PerryThePlatipusCompanion extends CompanionCosmetic {
  
  public PerryThePlatipusCompanion() {
    super("PerryThePlatipus", 8, EnumRarity.DIVINO,
        "SKULL_ITEM:3 : 1 : nome>PerryThePlatipus : desc>&7Divirta-se pelos nossos lobbies\n&7acompanhado de um PerryThePlatipus! : skin>eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvN2U3YzE4MWExOWYzNjdmMWFjOTQ5NWEyYmU3NjFhZDBkOTE1NzRiNzVlZDc5OGY3NjVhMWVlNmJlNDFhODcxIn19fQ==");
    
    this.frames.createKeyFrame(0, Arrays.asList(
        new CompanionAnimation(MathUtils.EulerAngle(180, 500, 25), "front_Left", CompanionAnimation.MovementType.ARM),
        new CompanionAnimation(MathUtils.EulerAngle(180, 500, 25), "back_Left", CompanionAnimation.MovementType.ARM),
        new CompanionAnimation(MathUtils.EulerAngle(180, 500, 25), "front_Right", CompanionAnimation.MovementType.ARM),
        new CompanionAnimation(MathUtils.EulerAngle(180, 500, 25), "back_Right", CompanionAnimation.MovementType.ARM)));
    this.frames.createKeyFrame(1, Arrays.asList(
        new CompanionAnimation(MathUtils.EulerAngle(180, 500, 25), "front_Left", CompanionAnimation.MovementType.ARM),
        new CompanionAnimation(MathUtils.EulerAngle(205, 525, 25), "back_Left", CompanionAnimation.MovementType.ARM),
        new CompanionAnimation(MathUtils.EulerAngle(205, 525, 25), "front_Right", CompanionAnimation.MovementType.ARM),
        new CompanionAnimation(MathUtils.EulerAngle(180, 500, 25), "back_Right", CompanionAnimation.MovementType.ARM)));
    this.frames.createKeyFrame(2, Arrays.asList(
        new CompanionAnimation(MathUtils.EulerAngle(205, 525, 25), "front_Left", CompanionAnimation.MovementType.ARM),
        new CompanionAnimation(MathUtils.EulerAngle(180, 500, 25), "back_Left", CompanionAnimation.MovementType.ARM),
        new CompanionAnimation(MathUtils.EulerAngle(180, 500, 25), "front_Right", CompanionAnimation.MovementType.ARM),
        new CompanionAnimation(MathUtils.EulerAngle(205, 525, 25), "back_Right", CompanionAnimation.MovementType.ARM)));
    
    this.frames.addFrames(0, 0, 1, 2, 2, 1, 0);
    this.frames.addFrames(1, 0, 1, 2, 2, 1, 0);
    this.frames.addFrames(2, 0, 1, 2, 2, 1, 0);
    this.frames.addFrames(3, 0, 1, 2, 2, 1, 0);
  }
  
  @Override
  public CompanionNames getNameEnum() {
    return CompanionNames.PERRYTHEPLATIPUS;
  }
}
