package mc.twilight.cosmeticos.cosmetics.types.clothes;

import mc.twilight.cosmeticos.cosmetics.types.ClothesCosmetic;
import mc.twilight.core.utils.BukkitUtils;
import mc.twilight.core.utils.enums.EnumRarity;

import java.util.Arrays;

public class LeatherClothes extends ClothesCosmetic {
  
  public LeatherClothes() {
    super("Roupa de Couro", EnumRarity.COMUM, "LEATHER_HELMET : 1 : nome>Roupa de Couro : desc>&7Caminhe com estilo em nossos\n&7lobbies com a roupa de Couro.");
    this.items = Arrays.asList(
        BukkitUtils.deserializeItemStack("LEATHER_HELMET : 1 : nome>&aCapacete de Couro"),
        BukkitUtils.deserializeItemStack("LEATHER_CHESTPLATE : 1 : nome>&aPeitoral de Couro"),
        BukkitUtils.deserializeItemStack("LEATHER_LEGGINGS : 1 : nome>&aCalças de Couro"),
        BukkitUtils.deserializeItemStack("LEATHER_BOOTS : 1 : nome>&aBotas de Couro"));
  }
}
