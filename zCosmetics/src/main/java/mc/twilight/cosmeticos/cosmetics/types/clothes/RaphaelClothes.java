package mc.twilight.cosmeticos.cosmetics.types.clothes;

import mc.twilight.cosmeticos.cosmetics.types.ClothesCosmetic;
import mc.twilight.core.utils.BukkitUtils;
import mc.twilight.core.utils.enums.EnumRarity;

import java.util.Arrays;

public class RaphaelClothes extends ClothesCosmetic {
  
  public RaphaelClothes() {
    super("Roupa do Raphael", EnumRarity.EPICO, "SKULL_ITEM:3 : 1 : nome>Roupa do Raphael : desc>&7Caminhe com estilo em nossos\n&7lobbies com a roupa do Raphael. : skin>eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvMzU5Yzc3NjQzN2U5OTRhYWNmYWI2MWNkYjVkZmQ2ZTdiOTNiZDkyZjUxMzUzMzk4ZjRhYmNmNzU2ZmEifX19");
    this.items = Arrays.asList(
        BukkitUtils.deserializeItemStack("SKULL_ITEM:3 : 1 : nome>&aCapacete do Raphael : skin>eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvMzU5Yzc3NjQzN2U5OTRhYWNmYWI2MWNkYjVkZmQ2ZTdiOTNiZDkyZjUxMzUzMzk4ZjRhYmNmNzU2ZmEifX19"),
        BukkitUtils.deserializeItemStack("LEATHER_CHESTPLATE : 1 : nome>&aPeitoral do Raphael : pintar>YELLOW"),
        BukkitUtils.deserializeItemStack("LEATHER_LEGGINGS : 1 : nome>&aCalças do Raphael : pintar>GREEN"),
        BukkitUtils.deserializeItemStack("LEATHER_BOOTS : 1 : nome>&aBotas do Raphael : pintar>GREEN"));
  }
}
