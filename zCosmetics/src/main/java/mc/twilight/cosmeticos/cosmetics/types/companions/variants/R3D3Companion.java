package mc.twilight.cosmeticos.cosmetics.types.companions.variants;

import mc.twilight.cosmeticos.cosmetics.types.CompanionCosmetic;
import mc.twilight.cosmeticos.cosmetics.types.companions.CompanionAnimation;
import mc.twilight.cosmeticos.cosmetics.types.companions.name.CompanionNames;
import mc.twilight.cosmeticos.utils.MathUtils;
import mc.twilight.core.utils.enums.EnumRarity;

import java.util.Collections;

public class R3D3Companion extends CompanionCosmetic {
  
  public R3D3Companion() {
    super("R3D3", 3, EnumRarity.DIVINO,
        "SKULL_ITEM:3 : 1 : nome>R3D3 : desc>&7Divirta-se pelos nossos lobbies\n&7acompanhado de um R3D3! : skin>eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvN2NlYmM5Nzc5OGMyZTM2MDU1MWNhYjNkZDVkYjZkNTM0OTdmZTYzMDQwOTQxYzlhYzQ5MWE1OWNiZjM4M2E3YSJ9fX0=");
    
    this.frames.createKeyFrame(0, Collections.singletonList(new CompanionAnimation(MathUtils.EulerAngle(0, 0, 0), "head", CompanionAnimation.MovementType.HEAD)));
    this.frames.createKeyFrame(1, Collections.singletonList(new CompanionAnimation(MathUtils.EulerAngle(0, 180, 0), "head", CompanionAnimation.MovementType.HEAD)));
    this.frames.createKeyFrame(2, Collections.singletonList(new CompanionAnimation(MathUtils.EulerAngle(0, 360, 0), "head", CompanionAnimation.MovementType.HEAD)));
    
    this.frames.addFrames(0, 0, 1, 2);
  }
  
  @Override
  public CompanionNames getNameEnum() {
    return CompanionNames.R3D3;
  }
}
