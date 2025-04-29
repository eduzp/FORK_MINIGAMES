package mc.twilight.cosmeticos.cosmetics.types.companions.variants;

import mc.twilight.cosmeticos.cosmetics.types.CompanionCosmetic;
import mc.twilight.cosmeticos.cosmetics.types.companions.CompanionAnimation;
import mc.twilight.cosmeticos.cosmetics.types.companions.name.CompanionNames;
import mc.twilight.cosmeticos.utils.MathUtils;
import mc.twilight.core.utils.enums.EnumRarity;

import java.util.Arrays;

public class MiniMeCompanion extends CompanionCosmetic {
  
  public MiniMeCompanion() {
    super("MiniMe", 5, EnumRarity.DIVINO,
        "SKULL_ITEM:3 : 1 : nome>MiniMe : desc>&7Divirta-se pelos nossos lobbies\n&7acompanhado de um MiniMe! : skin>eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvYTExNmU2OWE4NDVlMjI3ZjdjYTFmZGRlOGMzNTdjOGM4MjFlYmQ0YmE2MTkzODJlYTRhMWY4N2Q0YWU5NCJ9fX0=");
    
    this.frames.createKeyFrame(0, Arrays.asList(
        new CompanionAnimation(MathUtils.EulerAngle(0, 0, 0), "body", CompanionAnimation.MovementType.LEG2),
        new CompanionAnimation(MathUtils.EulerAngle(0, 0, 0), "body", CompanionAnimation.MovementType.LEG),
        new CompanionAnimation(MathUtils.EulerAngle(0, 0, 0), "body", CompanionAnimation.MovementType.ARM2),
        new CompanionAnimation(MathUtils.EulerAngle(0, 0, 0), "body", CompanionAnimation.MovementType.ARM)));
    this.frames.createKeyFrame(1, Arrays.asList(
        new CompanionAnimation(MathUtils.EulerAngle(45, 0, 0), "body", CompanionAnimation.MovementType.LEG2),
        new CompanionAnimation(MathUtils.EulerAngle(-45, 0, 0), "body", CompanionAnimation.MovementType.LEG),
        new CompanionAnimation(MathUtils.EulerAngle(-45, 0, 0), "body", CompanionAnimation.MovementType.ARM2),
        new CompanionAnimation(MathUtils.EulerAngle(45, 0, 0), "body", CompanionAnimation.MovementType.ARM)));
    this.frames.createKeyFrame(2, Arrays.asList(
        new CompanionAnimation(MathUtils.EulerAngle(-45, 0, 0), "body", CompanionAnimation.MovementType.LEG2),
        new CompanionAnimation(MathUtils.EulerAngle(45, 0, 0), "body", CompanionAnimation.MovementType.LEG),
        new CompanionAnimation(MathUtils.EulerAngle(45, 0, 0), "body", CompanionAnimation.MovementType.ARM2),
        new CompanionAnimation(MathUtils.EulerAngle(-45, 0, 0), "body", CompanionAnimation.MovementType.ARM)));
    
    this.frames.addFrames(0, 0, 1, 2, 2, 1, 0);
    this.frames.addFrames(1, 0, 1, 2, 2, 1, 0);
    this.frames.addFrames(2, 0, 1, 2, 2, 1, 0);
    this.frames.addFrames(3, 0, 1, 2, 2, 1, 0);
  }
  
  @Override
  public CompanionNames getNameEnum() {
    return CompanionNames.MINIME;
  }
}
