package mc.twilight.cosmeticos.cosmetics.types.companions.variants;

import mc.twilight.cosmeticos.cosmetics.types.CompanionCosmetic;
import mc.twilight.cosmeticos.cosmetics.types.companions.CompanionAnimation;
import mc.twilight.cosmeticos.cosmetics.types.companions.name.CompanionNames;
import mc.twilight.cosmeticos.utils.MathUtils;
import mc.twilight.core.utils.enums.EnumRarity;

import java.util.Arrays;

public class PandaCompanion extends CompanionCosmetic {
  
  public PandaCompanion() {
    super("Panda", 5, EnumRarity.DIVINO,
        "SKULL_ITEM:3 : 1 : nome>Panda : desc>&7Divirta-se pelos nossos lobbies\n&7acompanhado de um Panda! : skin>eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvOTQ3YjY4ZWQwMjE2MzJmNDA4ZmMyMjNlZjc5NTdjMjQ3ODZhZTUwOWE4NGU2ZjE4YTM3MWE1NWMzZDhjZjkwOSJ9fX0=");
    
    this.frames.createKeyFrame(0, Arrays
        .asList(new CompanionAnimation(MathUtils.EulerAngle(190, 500, 13), "frontLeft", CompanionAnimation.MovementType.ARM),
            new CompanionAnimation(MathUtils.EulerAngle(190, 500, 13), "backLeft", CompanionAnimation.MovementType.ARM),
            new CompanionAnimation(MathUtils.EulerAngle(190, 500, 13), "frontRight", CompanionAnimation.MovementType.ARM),
            new CompanionAnimation(MathUtils.EulerAngle(190, 500, 13), "backRight", CompanionAnimation.MovementType.ARM),
            new CompanionAnimation(MathUtils.EulerAngle(183, 500, 13), "tail", CompanionAnimation.MovementType.ARM)));
    
    this.frames.createKeyFrame(1, Arrays
        .asList(new CompanionAnimation(MathUtils.EulerAngle(215, 525, 13), "frontLeft", CompanionAnimation.MovementType.ARM),
            new CompanionAnimation(MathUtils.EulerAngle(190, 500, 13), "backLeft", CompanionAnimation.MovementType.ARM),
            new CompanionAnimation(MathUtils.EulerAngle(190, 500, 13), "frontRight", CompanionAnimation.MovementType.ARM),
            new CompanionAnimation(MathUtils.EulerAngle(215, 525, 13), "backRight", CompanionAnimation.MovementType.ARM),
            new CompanionAnimation(MathUtils.EulerAngle(183, 500, 29), "tail", CompanionAnimation.MovementType.ARM)));
    
    this.frames.createKeyFrame(2, Arrays
        .asList(new CompanionAnimation(MathUtils.EulerAngle(190, 500, 13), "frontLeft", CompanionAnimation.MovementType.ARM),
            new CompanionAnimation(MathUtils.EulerAngle(215, 525, 13), "backLeft", CompanionAnimation.MovementType.ARM),
            new CompanionAnimation(MathUtils.EulerAngle(215, 525, 13), "frontRight", CompanionAnimation.MovementType.ARM),
            new CompanionAnimation(MathUtils.EulerAngle(190, 500, 13), "backRight", CompanionAnimation.MovementType.ARM),
            new CompanionAnimation(MathUtils.EulerAngle(183, 500, 0), "tail", CompanionAnimation.MovementType.ARM)));
    
    this.frames.createKeyFrame(3, Arrays
        .asList(new CompanionAnimation(MathUtils.EulerAngle(190, 500, 13), "frontLeft", CompanionAnimation.MovementType.ARM),
            new CompanionAnimation(MathUtils.EulerAngle(190, 500, 13), "backLeft", CompanionAnimation.MovementType.ARM),
            new CompanionAnimation(MathUtils.EulerAngle(190, 500, 13), "frontRight", CompanionAnimation.MovementType.ARM),
            new CompanionAnimation(MathUtils.EulerAngle(190, 500, 13), "backRight", CompanionAnimation.MovementType.ARM),
            new CompanionAnimation(MathUtils.EulerAngle(183, 500, 13), "tail", CompanionAnimation.MovementType.ARM)));
    
    this.frames.addFrames(0, 1, 2, 3);
    this.frames.addFrames(1, 1, 2, 3);
    this.frames.addFrames(2, 1, 2, 3);
    this.frames.addFrames(3, 1, 2, 3);
    this.frames.addFrames(4, 1, 2, 3);
  }
  
  @Override
  public CompanionNames getNameEnum() {
    return CompanionNames.PANDA;
  }
}
