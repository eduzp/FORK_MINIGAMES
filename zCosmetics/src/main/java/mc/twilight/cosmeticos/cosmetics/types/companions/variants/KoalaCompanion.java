package mc.twilight.cosmeticos.cosmetics.types.companions.variants;

import mc.twilight.cosmeticos.cosmetics.types.CompanionCosmetic;
import mc.twilight.cosmeticos.cosmetics.types.companions.CompanionAnimation;
import mc.twilight.cosmeticos.cosmetics.types.companions.name.CompanionNames;
import mc.twilight.cosmeticos.utils.MathUtils;
import mc.twilight.core.utils.enums.EnumRarity;

import java.util.Arrays;

public class KoalaCompanion extends CompanionCosmetic {
  
  public KoalaCompanion() {
    super("Coala", 5, EnumRarity.DIVINO,
        "SKULL_ITEM:3 : 1 : nome>Coala : desc>&7Divirta-se pelos nossos lobbies\n&7acompanhado de um Coala! : skin>eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvOGQ4N2U4MjlmMmQ0ODlmZjZlZTY2ODI2MzllMzM5YTIxMjNkMzQ0OWQyN2Y4YmRhNTE1MjhjNjA3NmZiOWYyYSJ9fX0=");
    
    this.frames.createKeyFrame(0, Arrays.asList(
        new CompanionAnimation(MathUtils.EulerAngle(0, 0, 0), "front_Left", CompanionAnimation.MovementType.HEAD),
        new CompanionAnimation(MathUtils.EulerAngle(0, 0, 0), "back_Left", CompanionAnimation.MovementType.HEAD),
        new CompanionAnimation(MathUtils.EulerAngle(0, 0, 0), "front_Right", CompanionAnimation.MovementType.HEAD),
        new CompanionAnimation(MathUtils.EulerAngle(0, 0, 0), "back_Right", CompanionAnimation.MovementType.HEAD)));
    this.frames.createKeyFrame(1, Arrays.asList(
        new CompanionAnimation(MathUtils.EulerAngle(0, 0, 0), "front_Left", CompanionAnimation.MovementType.HEAD),
        new CompanionAnimation(MathUtils.EulerAngle(-35, 0, 0), "back_Left", CompanionAnimation.MovementType.HEAD),
        new CompanionAnimation(MathUtils.EulerAngle(-35, 0, 0), "front_Right", CompanionAnimation.MovementType.HEAD),
        new CompanionAnimation(MathUtils.EulerAngle(0, 0, 0), "back_Right", CompanionAnimation.MovementType.HEAD)));
    this.frames.createKeyFrame(2, Arrays.asList(
        new CompanionAnimation(MathUtils.EulerAngle(-35, 0, 0), "front_Left", CompanionAnimation.MovementType.HEAD),
        new CompanionAnimation(MathUtils.EulerAngle(0, 0, 0), "back_Left", CompanionAnimation.MovementType.HEAD),
        new CompanionAnimation(MathUtils.EulerAngle(0, 0, 0), "front_Right", CompanionAnimation.MovementType.HEAD),
        new CompanionAnimation(MathUtils.EulerAngle(-35, 0, 0), "back_Right", CompanionAnimation.MovementType.HEAD)));
    
    this.frames.addFrames(0, 0, 1, 2, 2, 1, 0);
    this.frames.addFrames(1, 0, 1, 2, 2, 1, 0);
    this.frames.addFrames(2, 0, 1, 2, 2, 1, 0);
    this.frames.addFrames(3, 0, 1, 2, 2, 1, 0);
  }
  
  @Override
  public CompanionNames getNameEnum() {
    return CompanionNames.KOALA;
  }
}
