package mc.twilight.cosmeticos.cosmetics.types.clothes;

import mc.twilight.cosmeticos.cosmetics.types.ClothesCosmetic;
import mc.twilight.core.utils.BukkitUtils;
import mc.twilight.core.utils.enums.EnumRarity;

import java.util.Arrays;

public class SpidermanClothes extends ClothesCosmetic {
  
  public SpidermanClothes() {
    super("Roupa do Homem Aranha", EnumRarity.EPICO, "SKULL_ITEM:3 : 1 : nome>Roupa do Homem Aranha : desc>&7Caminhe com estilo em nossos\n&7lobbies com a roupa do Homem Aranha. : skin>eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvZTc0MGM5ZjkxYmM5YmFiYzYzM2JmYWQ0NWNhNWFkOWU5ZDYyZDk0YjdkNDM1OTM2OWYyNTBhOGI2NjA4In19fQ==");
    this.items = Arrays.asList(
        BukkitUtils.deserializeItemStack("SKULL_ITEM:3 : 1 : nome>&aCapacete do Homem Aranha : skin>eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvZTc0MGM5ZjkxYmM5YmFiYzYzM2JmYWQ0NWNhNWFkOWU5ZDYyZDk0YjdkNDM1OTM2OWYyNTBhOGI2NjA4In19fQ=="),
        BukkitUtils.deserializeItemStack("LEATHER_CHESTPLATE : 1 : nome>&aPeitoral do Homem Aranha : pintar>RED"),
        BukkitUtils.deserializeItemStack("LEATHER_LEGGINGS : 1 : nome>&aCalças do Homem Aranha : pintar>BLUE"),
        BukkitUtils.deserializeItemStack("LEATHER_BOOTS : 1 : nome>&aBotas do Homem Aranha : pintar>RED"));
  }
}
