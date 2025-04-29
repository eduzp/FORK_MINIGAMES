package mc.twilight.cosmeticos.cosmetics.types.companions.variants;

import mc.twilight.cosmeticos.cosmetics.types.CompanionCosmetic;
import mc.twilight.cosmeticos.cosmetics.types.companions.CompanionAnimation;
import mc.twilight.cosmeticos.cosmetics.types.companions.name.CompanionNames;
import mc.twilight.cosmeticos.utils.MathUtils;
import mc.twilight.core.utils.enums.EnumRarity;

import java.util.Collections;

public class PenguinCompanion extends CompanionCosmetic {
  
  public PenguinCompanion() {
    super("Pinguim", 8, EnumRarity.DIVINO,
        "SKULL_ITEM:3 : 1 : nome>Pinguim : desc>&7Divirta-se pelos nossos lobbies\n&7acompanhado de um Pinguim! : skin>eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvZDNjNTdmYWNiYjNhNGRiN2ZkNTViNWMwZGM3ZDE5YzE5Y2IwODEzYzc0OGNjYzk3MTBjNzE0NzI3NTUxZjViOSJ9fX0=");
    
    this.frames.createKeyFrame(0, Collections.singletonList(new CompanionAnimation(MathUtils.EulerAngle(0, 0, 0), "head", CompanionAnimation.MovementType.HEAD)));
    this.frames.createKeyFrame(1, Collections.singletonList(new CompanionAnimation(MathUtils.EulerAngle(0, 0, 15), "head", CompanionAnimation.MovementType.HEAD)));
    this.frames.createKeyFrame(2, Collections.singletonList(new CompanionAnimation(MathUtils.EulerAngle(0, 0, -15), "head", CompanionAnimation.MovementType.HEAD)));
    
    this.frames.addFrames(0, 0, 1, 0, 2, 0);
    
    this.frames.createIdleKeyFrame(0, Collections.singletonList(new CompanionAnimation(MathUtils.EulerAngle(0, 0, 0), "head", CompanionAnimation.MovementType.HEAD)));
    this.frames.createIdleKeyFrame(1, Collections.singletonList(new CompanionAnimation(MathUtils.EulerAngle(0, 0, 15), "head", CompanionAnimation.MovementType.HEAD)));
    this.frames.createIdleKeyFrame(2, Collections.singletonList(new CompanionAnimation(MathUtils.EulerAngle(0, 0, -15), "head", CompanionAnimation.MovementType.HEAD)));
    
    this.frames.addIdleFrames(0, 0, 1, 0, 2, 0);
  }
  
  @Override
  public CompanionNames getNameEnum() {
    return CompanionNames.PENGUIN;
  }
}
