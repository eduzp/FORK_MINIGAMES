package mc.twilight.cosmeticos.cosmetics.types;

import mc.twilight.cosmeticos.cosmetics.Cosmetic;
import mc.twilight.cosmeticos.cosmetics.CosmeticType;
import mc.twilight.cosmeticos.hook.player.CUser;
import mc.twilight.core.utils.BukkitUtils;
import mc.twilight.core.utils.enums.EnumRarity;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.Arrays;
import java.util.List;

public class HatCosmetic extends Cosmetic {
  
  private final String icon;
  
  public HatCosmetic(String name, EnumRarity rarity, String texture) {
    super(name, rarity, CosmeticType.HAT);
    if (!texture.startsWith("eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUv")) {
      texture = "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUv" + texture;
    }
    this.icon = "SKULL_ITEM:3 : 1 : nome>" + name + " : desc>&7Divirta-se pelos nossos lobbies com\n&7estilo utilizando o chapeú " + name + " : skin>" + texture;
    Cosmetic.addCosmetic(this);
  }
  
  public static void setupHats() {
    new HatCosmetic("Minhoca", EnumRarity.COMUM, "YTc5MWEzZjFlOWUwYTM5OTE1YTc2NTM5NGI0ZmE4NDlhY2IzZTZhOWMyY2ZhODhhNWE5Y2RjYTZhMzY4ZTdmZSJ9fX0=");
    new HatCosmetic("Fantasma", EnumRarity.COMUM, "ZmMyYjcxMTdkZGRlZTU3ZTMyNzU2MThmYjc0YTk0ZmYyMzg4ZGZjOTk5MjQzNjEwMTlkZmY5ODM0ZTI3NzEifX19");
    new HatCosmetic("O Rei Zumbi", EnumRarity.EPICO, "MTU1ODJlZDUxMmE4Y2VhOWIxN2ZmYzMxY2ExMmI4NmYyYWJjY2Y4M2E2MjJmYjdkMTViNWYxNzFmNTFiYjY0In19fQ==");
    new HatCosmetic("Batman", EnumRarity.RARO, "ZjI1NmY3MTczNWVmNDU4NTgxYzlkYWNmMzk0MTg1ZWVkOWIzM2NiNmVjNWNkNTk0YTU3MTUzYThiNTY2NTYwIn19fQ==");
    new HatCosmetic("Pinguim", EnumRarity.EPICO, "N2I2OTkzOGU1N2Y0ZWU2ZjFiNDRhZjE4ZWEyNDJhNDM1MjU3NmU5OTg4ZTcyODAxNGY5NWI0ODYyZGFhOCJ9fX0=");
    new HatCosmetic("Wither", EnumRarity.RARO, "OTY0ZTFjM2UzMTVjOGQ4ZmZmYzM3OTg1YjY2ODFjNWJkMTZhNmY5N2ZmZDA3MTk5ZThhMDVlZmJlZjEwMzc5MyJ9fX0=");
    new HatCosmetic("Guardião", EnumRarity.RARO, "YzI1YWY5NjZhMzI2ZjlkOTg0NjZhN2JmODU4MmNhNGRhNjQ1M2RlMjcxYjNiYzllNTlmNTdhOTliNjM1MTFjNiJ9fX0=");
    new HatCosmetic("Smurf", EnumRarity.RARO, "YzVlOGVlYTFkYzMyZTMzODQ1MmNlNmJlN2QzZDIzZWQxNjNhYWI0ZmUxZmQ5YjQwYjExNGEyYTJlMmE3OSJ9fX0=");
    new HatCosmetic("Megaman", EnumRarity.RARO, "YjBjNTJlMTJmM2IwYTU4MzMyYzA3MDFjYTI1NWRkNGI0ZTE4Y2Y5OGU2MzAxZDc4MWY4YjVlMzYxOTk3ODFjOCJ9fX0=");
    new HatCosmetic("Luigi", EnumRarity.RARO, "ZmYxNTMzODcxZTQ5ZGRhYjhmMWNhODJlZGIxMTUzYTVlMmVkMzc2NGZkMWNlMDI5YmY4MjlmNGIzY2FhYzMifX19");
    new HatCosmetic("Mario", EnumRarity.RARO, "ZGJhOGQ4ZTUzZDhhNWE3NTc3MGI2MmNjZTczZGI2YmFiNzAxY2MzZGU0YTliNjU0ZDIxM2Q1NGFmOTYxNSJ9fX0=");
    new HatCosmetic("Robocop", EnumRarity.COMUM, "YjQyYmRkYjgzNjkzNTExMDg1OGM2YWQyMTE0NjZjNzZjYjFiNDQ3M2FlNGFhODViOWE1MjM4NzIzMjIyNmY5In19fQ==");
    new HatCosmetic("Pato Donald", EnumRarity.RARO, "NGIxMjczMjViZmJkMmUxYTk5Y2MwZTZhMWY2OGExYWFmNzhiYTgyMTM5MGEwZjI4NzlkODAzZGIyZWFlNiJ9fX0=");
    new HatCosmetic("Link", EnumRarity.RARO, "NmJiMmU2OWIzODcwZmUyZjRmMWJhMTRhOGY5Y2E4YWNjOWE3NTIwZTRlNGE5Nzg0YzE5YTNhMGM5NDQ2ZTRkIn19fQ==");
    new HatCosmetic("Astronauta", EnumRarity.COMUM, "M2U4YWFkNjczMTU3YzkyMzE3YTg4YjFmODZmNTI3MWYxY2Q3Mzk3ZDdmYzhlYzMyODFmNzMzZjc1MTYzNCJ9fX0=");
    new HatCosmetic("Urso Polar", EnumRarity.RARO, "YjExN2JiZTRiOWU4NmNiZmMyYTI4MTI2ZWM3OThjMzNmNWNmYjgxMjNmNTMzY2I5ZjYxZjQ4OGQ4NTE1NiJ9fX0=");
    new HatCosmetic("Cabeça de Abóbora", EnumRarity.COMUM, "Y2NlZDRiY2ZkMjExNjQ2NGRlZGYxNTdiZmM2MmRiMjZjOTU3YTlhNmFjOGJiYzUyNTYzNDY3MDg1YmUyMyJ9fX0===");
    new HatCosmetic("Tigre", EnumRarity.COMUM, "ZmM0MjYzODc0NDkyMmI1ZmNmNjJjZDliZjI3ZWVhYjkxYjJlNzJkNmM3MGU4NmNjNWFhMzg4Mzk5M2U5ZDg0In19fQ==");
    new HatCosmetic("Alto-Falante", EnumRarity.COMUM, "NTM1OTZmZDdiZTZiNzQ2M2U3ZDJjYzQ1ODQ0NWU2YTBhOWExODNkOGY0Y2ZjYzgwMTE4YmE3MTNhN2RkMTgifX19");
    new HatCosmetic("Pokebola", EnumRarity.EPICO, "ZDQzZDRiN2FjMjRhMWQ2NTBkZGY3M2JkMTQwZjQ5ZmMxMmQyNzM2ZmMxNGE4ZGMyNWMwZjNmMjlkODVmOGYifX19");
    new HatCosmetic("Cubo Mágico", EnumRarity.EPICO, "OGY5MjMzYzEyNDdlMDNlOWZkMjc3NDI3MzdlNzllNGNjZWJkMjI1YTliMDU5ZDU5NmQ1Y2QzNGUyNmYyMTY1In19fQ==");
    new HatCosmetic("Bola de Futebol", EnumRarity.DIVINO, "NTNjNTgxMGI5NzU0ZDJlMjc3ZTU1MWY2YThlNmFjOWIxM2VjN2ZhNmRjMDAwYjNlYWRhNWM0ODZlMWNkOTlmNSJ9fX0=");
    new HatCosmetic("Televisão", EnumRarity.EPICO, "ZmU4MWQ3ZmU4MWI0MTBjOGQ0MTJiMmZiMDc2YmUwMGFjY2IyNDcxM2NiYTkxYTc4NTI3OWY3OTBhOGVmYTk4MCJ9fX0=");
    new HatCosmetic("Galinha", EnumRarity.RARO, "MTYzODQ2OWE1OTljZWVmNzIwNzUzNzYwMzI0OGE5YWIxMWZmNTkxZmQzNzhiZWE0NzM1YjM0NmE3ZmFlODkzIn19fQ==");
    new HatCosmetic("Boneco de Neve", EnumRarity.EPICO, "OThlMzM0ZTRiZWUwNDI2NDc1OWE3NjZiYzE5NTVjZmFmM2Y1NjIwMTQyOGZhZmVjOGQ0YmYxYmIzNmFlNiJ9fX0=");
    new HatCosmetic("Planeta Terra", EnumRarity.DIVINO, "MmUyY2M0MjAxNWU2Njc4ZjhmZDQ5Y2NjMDFmYmY3ODdmMWJhMmMzMmJjZjU1OWEwMTUzMzJmYzVkYjUwIn19fQ==");
    new HatCosmetic("Bola de Basquete", EnumRarity.EPICO, "M2NlYmZmOTBiNWM5YzU5Y2EzNTQyZmQyNTUxZWUzOTk5ZjhlOGJmZmVmMTA4NWJjYzg3YmYyZGNkNjE3ZmU0MSJ9fX0=");
    new HatCosmetic("Câmera", EnumRarity.EPICO, "MTQ0MjJhODJjODk5YTljMTQ1NDM4NGQzMmNjNTRjNGFlN2ExYzRkNzI0MzBlNmU0NDZkNTNiOGIzODVlMzMwIn19fQ==");
    new HatCosmetic("Esfera do Dragão", EnumRarity.DIVINO, "NDkyOTlkYzAyYzM1ZjFiYzFhNjg5NWQ3ZmMyOGRlNzdjYTg5MGQwNjYzY2VjNWRjZDZlYTg0NjBhZjExMjEifX19");
    new HatCosmetic("Ovelha", EnumRarity.COMUM, "ZjMxZjljY2M2YjNlMzJlY2YxM2I4YTExYWMyOWNkMzNkMThjOTVmYzczZGI4YTY2YzVkNjU3Y2NiOGJlNzAifX19");
    new HatCosmetic("Boneco de Neve Natalino", EnumRarity.DIVINO, "ZTI3Njk1YmVlMmJhYmY5YmFlZjExYWUxOWZlNWY4MTdmYTA4OGNkNTJjNGM0NzNjYzdiNDQxZDVjM2Y1MGZlIn19fQ==");
    new HatCosmetic("Papai Noel", EnumRarity.DIVINO, "MTRlNDI0YjE2NzZmZWVjM2EzZjhlYmFkZTllN2Q2YTZmNzFmNzc1NmE4NjlmMzZmN2RmMGZjMTgyZDQzNmUifX19");
    new HatCosmetic("Mamãe Noel", EnumRarity.DIVINO, "NTI3NTE4NDE3ZjM4NGQ2YWU1YzU0ODg2Mjg0YjQ4NDM4ZGU3YmE5ZTdkYTYzZjExOTM1NTc4NThiZmVlZjg2YyJ9fX0=");
    new HatCosmetic("Rudolf a Rena", EnumRarity.DIVINO, "NDQ5MjdjZTViYTIyYWQxZTc1N2Q2YTMzM2UyNzViMzZkYTFhODQzNmZjZWYwNzczNDBhYjUzZTNmYiJ9fX0=");
    new HatCosmetic("Aldeão Natalino", EnumRarity.DIVINO, "YTg3YmRhNzkzMTY4MzNhYWFjMzQ2YWMxY2ZjYTgyNDI1NGMzMjJhMTA1YjZjODU4YTljNWE3OGU3NTQ2NmY4In19fQ==");
    new HatCosmetic("Herobrine Natalino", EnumRarity.DIVINO, "Mjc2ZDg2ZGVlMzI5M2UyODE1ZjM1Njk3YTZhMzA3MDE2OWQ4YzFhYzYyZmI0ZGNkNWU3YWNmYTgzZDY2ZmU0In19fQ==");
    new HatCosmetic("Homem Biscoito", EnumRarity.DIVINO, "MzAyM2IxZGQ5MWQyYjM2Y2FkZTU2NjVjM2Y3ODk3ZmNiOGRlMWFlNjE5YTRlOTYxODU2MzdiMTliZGNmZjQ3In19fQ==");
    new HatCosmetic("Casa de Doces", EnumRarity.DIVINO, "NDBmNDg1NTkzNjc3YWFiMjhkYmNhNDViMTAyMmYyMWIxYmM3ODk2YmM5ZmU0NDA2YWJlNGQ2MDcwNTc4MjYifX19");
    new HatCosmetic("Presente Vermelho", EnumRarity.EPICO, "NmNlZjlhYTE0ZTg4NDc3M2VhYzEzNGE0ZWU4OTcyMDYzZjQ2NmRlNjc4MzYzY2Y3YjFhMjFhODViNyJ9fX0=");
    new HatCosmetic("Presente Laranja", EnumRarity.EPICO, "OTI4ZTY5MmQ4NmUyMjQ0OTc5MTVhMzk1ODNkYmUzOGVkZmZkMzljYmJhNDU3Y2M5NWE3YWMzZWEyNWQ0NDUifX19");
    new HatCosmetic("Presente Roxo", EnumRarity.EPICO, "NGFjYjNjMWUxYjM0Zjg3MzRhZWRmYWJkMWUxZjVlMGIyODBiZWY5MjRmYjhiYmYzZTY5MmQyNTM4MjY2ZjQifX19");
    new HatCosmetic("Presente Rosa", EnumRarity.EPICO, "MTBjNzVhMDViMzQ0ZWEwNDM4NjM5NzRjMTgwYmE4MTdhZWE2ODY3OGNiZWE1ZTRiYTM5NWY3NGQ0ODAzZDFkIn19fQ==");
    new HatCosmetic("Presente Verde", EnumRarity.EPICO, "ZDA4Y2U3ZGViYTU2YjcyNmE4MzJiNjExMTVjYTE2MzM2MTM1OWMzMDQzNGY3ZDVlM2MzZmFhNmZlNDA1MiJ9fX0=");
    new HatCosmetic("Presente Azul", EnumRarity.EPICO, "NDNlMTY1ODU3YzRjMmI3ZDZkOGQ3MGMxMDhjYzZkNDY0ZjdmMzBlMDRkOTE0NzMxYjU5ZTMxZDQyNWEyMSJ9fX0=");
    new HatCosmetic("Presente Amarelo", EnumRarity.EPICO, "YTNlNThlYTdmMzExM2NhZWNkMmIzYTZmMjdhZjUzYjljYzljZmVkN2IwNDNiYTMzNGI1MTY4ZjEzOTFkOSJ9fX0=");
    new HatCosmetic("Presente Cinza", EnumRarity.EPICO, "YWMzODIxZDRmNjFiMTdmODJmMGQ3YThlNTMxMjYwOGZmNTBlZGUyOWIxYjRkYzg5ODQ3YmU5NDI3ZDM2In19fQ==");
    new HatCosmetic("Elfo", EnumRarity.DIVINO, "MmI2MDgxYWFjZmQzNDdjMTI2YWI0ZTkwOTA5ZmRkMzdhNGRlYjVjYTE5MTg0ZWI0Yzk1OTE3MzRmODU4NjgifX19");
    new HatCosmetic("Elfa", EnumRarity.DIVINO, "ZmM2ZTc5ZDE3MTc5MDJmNzFkYzU1M2M1MzczZmYyZTE3YmFlNjE5YjM5ZjhkZGJkZDIzMzgzZGQ1NWIzOTYifX19");
    new HatCosmetic("Princesa Elfa", EnumRarity.DIVINO, "NGRhMWJhYTlhMTEwMjU5YmIwYzgyMTZlYWFlZDgxZTZhYzQ1M2Y5YzUwZDg2ODVjNzJlODVkNjNiZDU4ZWNhIn19fQ==");
    new HatCosmetic("Sorvete", EnumRarity.EPICO, "OTUzNjZjYTE3OTc0ODkyZTRmZDRjN2I5YjE4ZmViMTFmMDViYTJlYzQ3YWE1MDM1YzgxYTk1MzNiMjgifX19");
    new HatCosmetic("Homem Aranha", EnumRarity.EPICO, "ZTc0MGM5ZjkxYmM5YmFiYzYzM2JmYWQ0NWNhNWFkOWU5ZDYyZDk0YjdkNDM1OTM2OWYyNTBhOGI2NjA4In19fQ==");
    new HatCosmetic("Venom", EnumRarity.EPICO, "MWJhNzQ0YTA2MTQ0MzFlMWU2OTUwZWZhYjY4ZDNjYzNmMjQ5ZmJiNWQ5N2ZkMDY3ZmZjNTg4NWQ4YjRmMWI4MSJ9fX0=");
    new HatCosmetic("Macaco", EnumRarity.RARO, "MjFlOTkzMzdkZGMyYzhkMTRjZjlmNTI3ZTk3MjI4NTEzMzU1OGM5NTM3NjVkZTRkZDVkN2E5MTlhOTg4ODIifX19");
    new HatCosmetic("Michelangelo", EnumRarity.EPICO, "ZjJlYjVlNTRiZTU2YzE0NGNkZjQzNGUyOWMxNTdiMTk4Zjk0YTkxNzZiYWU4OGRkNjVmMDgxODA1MzQyIn19fQ==");
    new HatCosmetic("Raphael", EnumRarity.EPICO, "MzU5Yzc3NjQzN2U5OTRhYWNmYWI2MWNkYjVkZmQ2ZTdiOTNiZDkyZjUxMzUzMzk4ZjRhYmNmNzU2ZmEifX19");
    new HatCosmetic("Leonardo", EnumRarity.EPICO, "ZWYszMjJjMzY3Y2JhZjI5OTYxNzlkZWQzOGM0Zjk2MmQ1NjljMmNmYzY3MTkwNjQ0N2NmMzRhMDNiNjQ5ZWM1In19fQ====");
    new HatCosmetic("Donatello", EnumRarity.EPICO, "ODI5Y2NlYWEyNWJhZTdjMWU1YWIyOWM5ZjU4YjJlMTE1NWMxZTJkNTNmZWU5ODVlNzY0MTI5YzA1Njk4In19fQ==");
    new HatCosmetic("Ender Dragon", EnumRarity.DIVINO, "NzRlY2MwNDA3ODVlNTQ2NjNlODU1ZWYwNDg2ZGE3MjE1NGQ2OWJiNGI3NDI0YjczODFjY2Y5NWIwOTVhIn19fQ==");
    new HatCosmetic("Herobrine", EnumRarity.DIVINO, "OThiN2NhM2M3ZDMxNGE2MWFiZWQ4ZmMxOGQ3OTdmYzMwYjZlZmM4NDQ1NDI1YzRlMjUwOTk3ZTUyZTZjYiJ9fX0=");
    new HatCosmetic("Rosa Colorida", EnumRarity.COMUM, "NzIxMDM1MjdmYWE0NWFkZWQ1YTNhMTY4MjkwMGRhNTQ2MDFkMzBlYjEyNTdhOGY0ODYxOTMyMmZlNTcyMWQ4MCJ9fX0==");
    new HatCosmetic("Alex", EnumRarity.DIVINO, "ewogICJ0aW1lc3RhbXAiIDogMTYxMDc0NzE5NDE3OCwKICAicHJvZmlsZUlkIiA6ICJmMTA0NzMxZjljYTU0NmI0OTkzNjM4NTlkZWY5N2NjNiIsCiAgInByb2ZpbGVOYW1lIiA6ICJ6aWFkODciLAogICJzaWduYXR1cmVSZXF1aXJlZCIgOiB0cnVlLAogICJ0ZXh0dXJlcyIgOiB7CiAgICAiU0tJTiIgOiB7CiAgICAgICJ1cmwiIDogImh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvZTNjODM5Mzk2NDhhMjA1N2I0MWM4NmU0MjcyN2YxYzE2ZDgwMzFlM2I4ODRhY2EwZjY3OTEwY2UwZjM4N2E4NyIsCiAgICAgICJtZXRhZGF0YSIgOiB7CiAgICAgICAgIm1vZGVsIiA6ICJzbGltIgogICAgICB9CiAgICB9CiAgfQp9");
    new HatCosmetic("Alex - Professor", EnumRarity.EPICO, "YWU1NTkwZWE2MTQzZTNiMjhmZmI0YmI3MWFhZWNmZDBhMjYzNjhmMTFmMGZlNDdkNjI1ZTYwZWUwZTBkY2JkZCJ9fX0=");
    new HatCosmetic("Alex - Padeiro", EnumRarity.RARO, "Njg2NzAyMGE5OTA1MjNlZDkzOWJlODIyMDBlYzk2OTc2MDhlMTBiNzZkODljYWY0MjIwNzAyOTA2ZmI1ZDQzNSJ9fX0=");
    new HatCosmetic("Alex - Natalino", EnumRarity.RARO, "MmM2ZTZiMWU3M2NiODJmZGUwYWY1N2E3NzU5NjJkN2I1YmJmYTU5ZTg4ZWI2ZDJlYzgzOWVjYzRmOThkZjFkZSJ9fX0=");
    new HatCosmetic("Alex - Bruxa", EnumRarity.RARO, "YzQ2M2ZlYTM1ODNmMTI2M2RkZDNlZmRjODI1NjIxZWFmODRjODczZTczYTIyOWZmMjU4OTUyOGI0NWQxODQifX19");
  }
  
  @Override
  public void onSelect(CUser user) {
    user.selectCosmetic(Cosmetic.NONE_CLOTHES, 1);
    user.selectCosmetic(Cosmetic.NONE_ANIMATED_HAT);
    user.selectCosmetic(Cosmetic.NONE_BANNER);
    user.handleClothes();
    user.handleAnimatedHat();
    user.handleBanner();
    user.handleHat();
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
