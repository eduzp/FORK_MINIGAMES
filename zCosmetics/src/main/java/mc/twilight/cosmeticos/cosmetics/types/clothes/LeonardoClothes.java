package mc.twilight.cosmeticos.cosmetics.types.clothes;

import mc.twilight.cosmeticos.cosmetics.types.ClothesCosmetic;
import mc.twilight.core.utils.BukkitUtils;
import mc.twilight.core.utils.enums.EnumRarity;

import java.util.Arrays;

public class LeonardoClothes extends ClothesCosmetic {
  
  public LeonardoClothes() {
    super("Roupa do Leonardo", EnumRarity.EPICO, "SKULL_ITEM:3 : 1 : nome>Roupa do Leonardo : desc>&7Caminhe com estilo em nossos\n&7lobbies com a roupa do Leonardo. : skin>eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvZWYzMjJjMzY3Y2JhZjI5OTYxNzlkZWQzOGM0Zjk2MmQ1NjljMmNmYzY3MTkwNjQ0N2NmMzRhMDNiNjQ5ZWM1In19fQ==");
    this.items = Arrays.asList(
        BukkitUtils.deserializeItemStack("SKULL_ITEM:3 : 1 : nome>&aCapacete do Leonardo : skin>eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvZWYzMjJjMzY3Y2JhZjI5OTYxNzlkZWQzOGM0Zjk2MmQ1NjljMmNmYzY3MTkwNjQ0N2NmMzRhMDNiNjQ5ZWM1In19fQ=="),
        BukkitUtils.deserializeItemStack("LEATHER_CHESTPLATE : 1 : nome>&aPeitoral do Leonardo : pintar>YELLOW"),
        BukkitUtils.deserializeItemStack("LEATHER_LEGGINGS : 1 : nome>&aCalças do Leonardo : pintar>GREEN"),
        BukkitUtils.deserializeItemStack("LEATHER_BOOTS : 1 : nome>&aBotas do Leonardo : pintar>GREEN"));
  }
}
