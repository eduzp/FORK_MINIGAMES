package mc.twilight.cosmeticos.cosmetics.types.clothes;

import mc.twilight.cosmeticos.cosmetics.types.ClothesCosmetic;
import mc.twilight.core.utils.BukkitUtils;
import mc.twilight.core.utils.enums.EnumRarity;

import java.util.Arrays;

public class DiamondClothes extends ClothesCosmetic {
  
  public DiamondClothes() {
    super("Roupa de Diamante", EnumRarity.RARO, "DIAMOND_HELMET : 1 : nome>Roupa de Diamante : desc>&7Caminhe com estilo em nossos\n&7lobbies com a roupa de Diamante.");
    this.items = Arrays.asList(
        BukkitUtils.deserializeItemStack("DIAMOND_HELMET : 1 : nome>&aCapacete de Diamante"),
        BukkitUtils.deserializeItemStack("DIAMOND_CHESTPLATE : 1 : nome>&aPeitoral de Diamante"),
        BukkitUtils.deserializeItemStack("DIAMOND_LEGGINGS : 1 : nome>&aCalças de Diamante"),
        BukkitUtils.deserializeItemStack("DIAMOND_BOOTS : 1 : nome>&aBotas de Diamante"));
  }
}
