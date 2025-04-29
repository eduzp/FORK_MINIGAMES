package mc.twilight.cosmeticos.cosmetics.types;

import mc.twilight.cosmeticos.cosmetics.Cosmetic;
import mc.twilight.cosmeticos.cosmetics.CosmeticType;
import mc.twilight.cosmeticos.hook.player.CUser;
import mc.twilight.core.utils.BukkitUtils;
import mc.twilight.core.utils.enums.EnumRarity;
import mc.twilight.core.utils.particles.ParticleEffect;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.Arrays;
import java.util.List;

public class ParticleCosmetic extends Cosmetic {
  
  public String icon;
  public ParticleEffect particleType;
  
  public ParticleCosmetic(String name, EnumRarity rarity, String icon, ParticleEffect particleType) {
    super(name, rarity, CosmeticType.PARTICLE);
    this.icon = icon;
    this.particleType = particleType;
    Cosmetic.addCosmetic(this);
  }
  
  public static void setupParticles() {
    new ParticleCosmetic("Chamas", EnumRarity.COMUM, "BLAZE_POWDER : 1 : nome>Chamas : desc>&7Divirta-se pelos nossos lobbies\n&7esboçando a partícula Chamas!",
        ParticleEffect.FLAME);
    new ParticleCosmetic("Aldeão Feliz", EnumRarity.RARO, "EMERALD : 1 : nome>Aldeão Feliz : desc>&7Divirta-se pelos nossos lobbies\n&7esboçando a partícula Aldeão Feliz!",
        ParticleEffect.VILLAGER_HAPPY);
    new ParticleCosmetic("Aldeão Zangado", EnumRarity.EPICO, "BLAZE_ROD : 1 : nome>Aldeão Zangado : desc>&7Divirta-se pelos nossos lobbies\n&7esboçando a partícula Aldeão Zangado!",
        ParticleEffect.VILLAGER_ANGRY);
    new ParticleCosmetic("Gotas de Lava", EnumRarity.EPICO, "LAVA_BUCKET : 1 : nome>Gotas de Lava : desc>&7Divirta-se pelos nossos lobbies\n&7esboçando a partícula Gotas de Lava!",
        ParticleEffect.DRIP_LAVA);
    new ParticleCosmetic("Música", EnumRarity.DIVINO, "2257 : 1 : esconder>tudo : nome>Música : desc>&7Divirta-se pelos nossos lobbies\n&7esboçando a partícula Música!",
        ParticleEffect.NOTE);
    new ParticleCosmetic("Letras", EnumRarity.DIVINO, "358 : 1 : esconder>tudo : nome>Letras : desc>&7Divirta-se pelos nossos lobbies\n&7esboçando a partícula Letras!",
        ParticleEffect.ENCHANTMENT_TABLE);
    new ParticleCosmetic("Gotas de Água", EnumRarity.EPICO, "WATER_BUCKET : 1 : nome>Gotas de Água : desc>&7Divirta-se pelos nossos lobbies\n&7esboçando a partícula Gotas de Água!",
        ParticleEffect.DRIP_WATER);
    new ParticleCosmetic("Corações", EnumRarity.DIVINO, "38 : 1 : nome>Corações : desc>&7Divirta-se pelos nossos lobbies\n&7esboçando a partícula Corações!",
        ParticleEffect.HEART);
    new ParticleCosmetic("Foguetes", EnumRarity.RARO, "FIREWORK : 1 : nome>Foguetes : desc>&7Divirta-se pelos nossos lobbies\n&7esboçando a partícula Foguetes!",
        ParticleEffect.FIREWORKS_SPARK);
    new ParticleCosmetic("Portal", EnumRarity.EPICO, "381 : 1 : nome>Portal : desc>&7Divirta-se pelos nossos lobbies\n&7esboçando a partícula Portal!",
        ParticleEffect.PORTAL);
    new ParticleCosmetic("Críticos", EnumRarity.EPICO, "32 : 1 : nome>Críticos : desc>&7Divirta-se pelos nossos lobbies\n&7esboçando a partícula Críticos!",
        ParticleEffect.CRIT);
    new ParticleCosmetic("Críticos Mágicos", EnumRarity.DIVINO, "310 : 1 : nome>Críticos Mágicos : desc>&7Divirta-se pelos nossos lobbies\n&7esboçando a partícula Críticos Mágicos!",
        ParticleEffect.CRIT_MAGIC);
  }
  
  @Override
  public void onSelect(CUser user) {
    user.handleParticle();
  }
  
  public ParticleEffect getParticleType() {
    return this.particleType;
  }
  
  public ItemStack getIcon(String color, String... loreList) {
    return this.getIcon(color, Arrays.asList(loreList));
  }
  
  public ItemStack getIcon(String color, List<String> loreList) {
    ItemStack icon = BukkitUtils.deserializeItemStack(this.icon);
    ItemMeta meta = icon.getItemMeta();
    meta.setDisplayName(color + meta.getDisplayName());
    List<String> currentLore = meta.getLore();
    currentLore.add("");
    currentLore.add("§fRaridade: " + this.getRarity().getName());
    currentLore.addAll(loreList);
    meta.setLore(currentLore);
    icon.setItemMeta(meta);
    return icon;
  }
}