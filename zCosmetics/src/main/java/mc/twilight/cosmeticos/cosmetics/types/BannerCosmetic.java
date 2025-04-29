package mc.twilight.cosmeticos.cosmetics.types;

import mc.twilight.cosmeticos.cosmetics.Cosmetic;
import mc.twilight.cosmeticos.cosmetics.CosmeticType;
import mc.twilight.cosmeticos.hook.player.CUser;
import mc.twilight.core.utils.BukkitUtils;
import mc.twilight.core.utils.enums.EnumRarity;
import org.bukkit.DyeColor;
import org.bukkit.block.banner.Pattern;
import org.bukkit.block.banner.PatternType;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.BannerMeta;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class BannerCosmetic extends Cosmetic {
  
  public String item;
  public DyeColor color;
  public List<Pattern> patterns;
  
  public BannerCosmetic(String name, EnumRarity rarity, DyeColor color) {
    super(name, rarity, CosmeticType.BANNER);
    this.color = color;
    this.patterns = new ArrayList<>();
    this.item = "BANNER : 1 : nome>" + name + " : desc>&7Divirta-se pelos nossos lobbies com\n&7estilo utilizando o banner " + name + " : esconder>tudo";
    Cosmetic.addCosmetic(this);
  }
  
  public static ItemStack getMainIcon(CUser user) {
    final ItemStack[] stack = new ItemStack[1];
    Cosmetic.listCosmetics(CosmeticType.BANNER).stream().findFirst().ifPresent(T -> {
      BannerCosmetic cosmetic = (BannerCosmetic) T;
      long max = Cosmetic.listCosmetics(BannerCosmetic.class).size();
      long have = Cosmetic.listCosmetics(BannerCosmetic.class).stream().filter(user::hasCosmetic).count();
      Cosmetic selected = user.getCosmetic(CosmeticType.BANNER);
      String name = selected.getName();
      String color = have == max ? "&a" : have > (max / 2) ? "&7" : "&c";
      
      stack[0] = BukkitUtils.deserializeItemStack("BANNER : 1 : nome>&aBanners : esconder>tudo : desc>&7Escolha banners com diversos\n&7desenhos diferentes para usar\n&7nos lobbies\n \n" +
          "&fDesbloqueado: " + color + have + "/" + max + "\n&fSelecionado atualmente:\n&a" + name + "\n \n&eClique para abrir!");
      BannerMeta meta = (BannerMeta) stack[0].getItemMeta();
      meta.setBaseColor(cosmetic.color);
      meta.setPatterns(cosmetic.patterns);
      stack[0].setItemMeta(meta);
    });
    return stack[0];
  }
  
  public static void setupBanners() {
    BannerCosmetic MOJANG = new BannerCosmetic("Mojang", EnumRarity.COMUM, DyeColor.RED);
    MOJANG.addPattern(new Pattern(DyeColor.WHITE, PatternType.MOJANG));
    
    BannerCosmetic CREEPER = new BannerCosmetic("Creeper", EnumRarity.COMUM, DyeColor.LIME);
    CREEPER.addPattern(new Pattern(DyeColor.BLACK, PatternType.CREEPER));
    
    BannerCosmetic SKULL = new BannerCosmetic("Caveira", EnumRarity.COMUM, DyeColor.WHITE);
    SKULL.addPattern(new Pattern(DyeColor.BLACK, PatternType.SKULL));
    
    BannerCosmetic REINDEER = new BannerCosmetic("Rena", EnumRarity.RARO, DyeColor.BROWN);
    REINDEER.addPattern(new Pattern(DyeColor.RED, PatternType.TRIANGLES_BOTTOM));
    REINDEER.addPattern(new Pattern(DyeColor.BLACK, PatternType.CREEPER));
    REINDEER.addPattern(new Pattern(DyeColor.WHITE, PatternType.SKULL));
    REINDEER.addPattern(new Pattern(DyeColor.BROWN, PatternType.RHOMBUS_MIDDLE));
    REINDEER.addPattern(new Pattern(DyeColor.BROWN, PatternType.STRIPE_SMALL));
    REINDEER.addPattern(new Pattern(DyeColor.SILVER, PatternType.STRIPE_MIDDLE));
    REINDEER.addPattern(new Pattern(DyeColor.SILVER, PatternType.HALF_HORIZONTAL));
    REINDEER.addPattern(new Pattern(DyeColor.BLACK, PatternType.CURLY_BORDER));
    REINDEER.addPattern(new Pattern(DyeColor.BLACK, PatternType.TRIANGLE_TOP));
    REINDEER.addPattern(new Pattern(DyeColor.BLACK, PatternType.CIRCLE_MIDDLE));
    REINDEER.addPattern(new Pattern(DyeColor.BLACK, PatternType.BORDER));
    REINDEER.addPattern(new Pattern(DyeColor.BLACK, PatternType.CURLY_BORDER));
    
    BannerCosmetic SANTA = new BannerCosmetic("Papai noel", EnumRarity.RARO, DyeColor.WHITE);
    SANTA.addPattern(new Pattern(DyeColor.RED, PatternType.RHOMBUS_MIDDLE));
    SANTA.addPattern(new Pattern(DyeColor.ORANGE, PatternType.CREEPER));
    SANTA.addPattern(new Pattern(DyeColor.WHITE, PatternType.STRIPE_MIDDLE));
    SANTA.addPattern(new Pattern(DyeColor.RED, PatternType.HALF_HORIZONTAL));
    SANTA.addPattern(new Pattern(DyeColor.LIGHT_BLUE, PatternType.STRIPE_TOP));
    SANTA.addPattern(new Pattern(DyeColor.LIGHT_BLUE, PatternType.CURLY_BORDER));
    
    BannerCosmetic HOLIDAY_WREATH = new BannerCosmetic("Coroa de Natal", EnumRarity.RARO, DyeColor.LIME);
    HOLIDAY_WREATH.addPattern(new Pattern(DyeColor.YELLOW, PatternType.CREEPER));
    HOLIDAY_WREATH.addPattern(new Pattern(DyeColor.RED, PatternType.SKULL));
    HOLIDAY_WREATH.addPattern(new Pattern(DyeColor.GREEN, PatternType.FLOWER));
    HOLIDAY_WREATH.addPattern(new Pattern(DyeColor.GREEN, PatternType.CIRCLE_MIDDLE));
    HOLIDAY_WREATH.addPattern(new Pattern(DyeColor.LIME, PatternType.CIRCLE_MIDDLE));
    
    BannerCosmetic HEART = new BannerCosmetic("Coração", EnumRarity.RARO, DyeColor.WHITE);
    HEART.addPattern(new Pattern(DyeColor.RED, PatternType.RHOMBUS_MIDDLE));
    HEART.addPattern(new Pattern(DyeColor.RED, PatternType.RHOMBUS_MIDDLE));
    HEART.addPattern(new Pattern(DyeColor.WHITE, PatternType.TRIANGLE_TOP));
    HEART.addPattern(new Pattern(DyeColor.RED, PatternType.GRADIENT_UP));
    HEART.addPattern(new Pattern(DyeColor.RED, PatternType.GRADIENT));
    
    BannerCosmetic GUITAR = new BannerCosmetic("Guitarra", EnumRarity.RARO, DyeColor.BLACK);
    GUITAR.addPattern(new Pattern(DyeColor.PURPLE, PatternType.STRIPE_TOP));
    GUITAR.addPattern(new Pattern(DyeColor.PURPLE, PatternType.CROSS));
    GUITAR.addPattern(new Pattern(DyeColor.BLACK, PatternType.HALF_HORIZONTAL_MIRROR));
    GUITAR.addPattern(new Pattern(DyeColor.BLACK, PatternType.STRAIGHT_CROSS));
    GUITAR.addPattern(new Pattern(DyeColor.PURPLE, PatternType.BORDER));
    GUITAR.addPattern(new Pattern(DyeColor.PURPLE, PatternType.CURLY_BORDER));
    
    BannerCosmetic DEADPOOL = new BannerCosmetic("Deadpool", EnumRarity.RARO, DyeColor.WHITE);
    DEADPOOL.addPattern(new Pattern(DyeColor.BLACK, PatternType.HALF_HORIZONTAL));
    DEADPOOL.addPattern(new Pattern(DyeColor.BLACK, PatternType.HALF_HORIZONTAL_MIRROR));
    DEADPOOL.addPattern(new Pattern(DyeColor.WHITE, PatternType.STRIPE_MIDDLE));
    DEADPOOL.addPattern(new Pattern(DyeColor.RED, PatternType.STRIPE_CENTER));
    DEADPOOL.addPattern(new Pattern(DyeColor.BLACK, PatternType.CURLY_BORDER));
    DEADPOOL.addPattern(new Pattern(DyeColor.RED, PatternType.TRIANGLE_TOP));
    DEADPOOL.addPattern(new Pattern(DyeColor.RED, PatternType.TRIANGLE_BOTTOM));
    
    BannerCosmetic POKEBALL = new BannerCosmetic("Pokebola", EnumRarity.EPICO, DyeColor.BLACK);
    POKEBALL.addPattern(new Pattern(DyeColor.RED, PatternType.HALF_HORIZONTAL));
    POKEBALL.addPattern(new Pattern(DyeColor.RED, PatternType.HALF_HORIZONTAL));
    POKEBALL.addPattern(new Pattern(DyeColor.RED, PatternType.STRIPE_TOP));
    POKEBALL.addPattern(new Pattern(DyeColor.WHITE, PatternType.STRIPE_BOTTOM));
    POKEBALL.addPattern(new Pattern(DyeColor.BLACK, PatternType.STRIPE_MIDDLE));
    POKEBALL.addPattern(new Pattern(DyeColor.WHITE, PatternType.CIRCLE_MIDDLE));
    
    BannerCosmetic SNOW_BUNNY = new BannerCosmetic("Coelho", EnumRarity.EPICO, DyeColor.WHITE);
    SNOW_BUNNY.addPattern(new Pattern(DyeColor.BLACK, PatternType.CIRCLE_MIDDLE));
    SNOW_BUNNY.addPattern(new Pattern(DyeColor.WHITE, PatternType.BORDER));
    SNOW_BUNNY.addPattern(new Pattern(DyeColor.WHITE, PatternType.FLOWER));
    SNOW_BUNNY.addPattern(new Pattern(DyeColor.LIGHT_BLUE, PatternType.TRIANGLE_TOP));
    SNOW_BUNNY.addPattern(new Pattern(DyeColor.WHITE, PatternType.CROSS));
    SNOW_BUNNY.addPattern(new Pattern(DyeColor.LIGHT_BLUE, PatternType.CURLY_BORDER));
    SNOW_BUNNY.addPattern(new Pattern(DyeColor.WHITE, PatternType.TRIANGLES_BOTTOM));
    
    BannerCosmetic HOLIDAY_TREE = new BannerCosmetic("Arvore de Natal", EnumRarity.EPICO, DyeColor.GREEN);
    HOLIDAY_TREE.addPattern(new Pattern(DyeColor.GREEN, PatternType.BRICKS));
    HOLIDAY_TREE.addPattern(new Pattern(DyeColor.LIGHT_BLUE, PatternType.TRIANGLE_BOTTOM));
    HOLIDAY_TREE.addPattern(new Pattern(DyeColor.BROWN, PatternType.STRAIGHT_CROSS));
    HOLIDAY_TREE.addPattern(new Pattern(DyeColor.LIGHT_BLUE, PatternType.STRIPE_MIDDLE));
    HOLIDAY_TREE.addPattern(new Pattern(DyeColor.LIME, PatternType.TRIANGLE_TOP));
    HOLIDAY_TREE.addPattern(new Pattern(DyeColor.WHITE, PatternType.RHOMBUS_MIDDLE));
    HOLIDAY_TREE.addPattern(new Pattern(DyeColor.GREEN, PatternType.FLOWER));
    HOLIDAY_TREE.addPattern(new Pattern(DyeColor.GREEN, PatternType.CIRCLE_MIDDLE));
    HOLIDAY_TREE.addPattern(new Pattern(DyeColor.LIME, PatternType.MOJANG));
    HOLIDAY_TREE.addPattern(new Pattern(DyeColor.LIGHT_BLUE, PatternType.CURLY_BORDER));
    
    BannerCosmetic DINO = new BannerCosmetic("Dinossauro", EnumRarity.COMUM, DyeColor.BROWN);
    DINO.addPattern(new Pattern(DyeColor.LIME, PatternType.CURLY_BORDER));
    DINO.addPattern(new Pattern(DyeColor.BROWN, PatternType.SQUARE_BOTTOM_RIGHT));
    DINO.addPattern(new Pattern(DyeColor.BROWN, PatternType.STRIPE_RIGHT));
    DINO.addPattern(new Pattern(DyeColor.BLUE, PatternType.FLOWER));
    DINO.addPattern(new Pattern(DyeColor.LIME, PatternType.STRIPE_MIDDLE));
    DINO.addPattern(new Pattern(DyeColor.BROWN, PatternType.TRIANGLE_TOP));
    DINO.addPattern(new Pattern(DyeColor.LIME, PatternType.MOJANG));
    
    BannerCosmetic PENGUIN = new BannerCosmetic("Pinguim", EnumRarity.RARO, DyeColor.BLACK);
    PENGUIN.addPattern(new Pattern(DyeColor.WHITE, PatternType.FLOWER));
    PENGUIN.addPattern(new Pattern(DyeColor.BLACK, PatternType.HALF_HORIZONTAL_MIRROR));
    PENGUIN.addPattern(new Pattern(DyeColor.BLACK, PatternType.CIRCLE_MIDDLE));
    PENGUIN.addPattern(new Pattern(DyeColor.WHITE, PatternType.TRIANGLE_BOTTOM));
    PENGUIN.addPattern(new Pattern(DyeColor.ORANGE, PatternType.RHOMBUS_MIDDLE));
    
    BannerCosmetic PUG = new BannerCosmetic("Pug", EnumRarity.EPICO, DyeColor.ORANGE);
    PUG.addPattern(new Pattern(DyeColor.WHITE, PatternType.BRICKS));
    PUG.addPattern(new Pattern(DyeColor.BROWN, PatternType.STRIPE_BOTTOM));
    PUG.addPattern(new Pattern(DyeColor.ORANGE, PatternType.BORDER));
    PUG.addPattern(new Pattern(DyeColor.BROWN, PatternType.TRIANGLE_BOTTOM));
    PUG.addPattern(new Pattern(DyeColor.PINK, PatternType.RHOMBUS_MIDDLE));
    PUG.addPattern(new Pattern(DyeColor.BLACK, PatternType.CREEPER));
    PUG.addPattern(new Pattern(DyeColor.BLACK, PatternType.FLOWER));
    PUG.addPattern(new Pattern(DyeColor.BROWN, PatternType.SKULL));
    PUG.addPattern(new Pattern(DyeColor.ORANGE, PatternType.STRIPE_TOP));
    PUG.addPattern(new Pattern(DyeColor.ORANGE, PatternType.TRIANGLE_TOP));
    PUG.addPattern(new Pattern(DyeColor.BROWN, PatternType.CIRCLE_MIDDLE));
    
    BannerCosmetic SKULL_KING = new BannerCosmetic("Rei Caveira", EnumRarity.RARO, DyeColor.BLACK);
    SKULL_KING.addPattern(new Pattern(DyeColor.WHITE, PatternType.CURLY_BORDER));
    SKULL_KING.addPattern(new Pattern(DyeColor.WHITE, PatternType.STRIPE_CENTER));
    SKULL_KING.addPattern(new Pattern(DyeColor.BLACK, PatternType.STRIPE_BOTTOM));
    SKULL_KING.addPattern(new Pattern(DyeColor.WHITE, PatternType.CREEPER));
    SKULL_KING.addPattern(new Pattern(DyeColor.YELLOW, PatternType.STRIPE_TOP));
    SKULL_KING.addPattern(new Pattern(DyeColor.BLACK, PatternType.TRIANGLES_TOP));
    
    BannerCosmetic ALEX = new BannerCosmetic("Alex", EnumRarity.DIVINO, DyeColor.WHITE);
    ALEX.addPattern(new Pattern(DyeColor.ORANGE, PatternType.GRADIENT));
    ALEX.addPattern(new Pattern(DyeColor.ORANGE, PatternType.STRIPE_TOP));
    ALEX.addPattern(new Pattern(DyeColor.ORANGE, PatternType.DIAGONAL_LEFT));
    ALEX.addPattern(new Pattern(DyeColor.WHITE, PatternType.STRIPE_BOTTOM));
    ALEX.addPattern(new Pattern(DyeColor.LIME, PatternType.SQUARE_BOTTOM_RIGHT));
    
    BannerCosmetic SKY_BLOCK = new BannerCosmetic("Sky Block", EnumRarity.COMUM, DyeColor.CYAN);
    SKY_BLOCK.addPattern(new Pattern(DyeColor.BROWN, PatternType.STRAIGHT_CROSS));
    SKY_BLOCK.addPattern(new Pattern(DyeColor.LIME, PatternType.STRIPE_MIDDLE));
    SKY_BLOCK.addPattern(new Pattern(DyeColor.BROWN, PatternType.HALF_HORIZONTAL_MIRROR));
    SKY_BLOCK.addPattern(new Pattern(DyeColor.SILVER, PatternType.STRIPE_BOTTOM));
    SKY_BLOCK.addPattern(new Pattern(DyeColor.GREEN, PatternType.STRIPE_TOP));
    SKY_BLOCK.addPattern(new Pattern(DyeColor.CYAN, PatternType.BORDER));
    
    BannerCosmetic NETHER_PORTAL = new BannerCosmetic("Portal do Nether", EnumRarity.EPICO, DyeColor.PURPLE);
    NETHER_PORTAL.addPattern(new Pattern(DyeColor.MAGENTA, PatternType.STRIPE_SMALL));
    NETHER_PORTAL.addPattern(new Pattern(DyeColor.PURPLE, PatternType.BRICKS));
    NETHER_PORTAL.addPattern(new Pattern(DyeColor.MAGENTA, PatternType.CURLY_BORDER));
    NETHER_PORTAL.addPattern(new Pattern(DyeColor.BLACK, PatternType.BORDER));
    
    BannerCosmetic DUCK = new BannerCosmetic("Unbê", EnumRarity.COMUM, DyeColor.YELLOW);
    DUCK.addPattern(new Pattern(DyeColor.BLACK, PatternType.STRIPE_LEFT));
    DUCK.addPattern(new Pattern(DyeColor.BLACK, PatternType.STRIPE_RIGHT));
    DUCK.addPattern(new Pattern(DyeColor.ORANGE, PatternType.HALF_HORIZONTAL_MIRROR));
    DUCK.addPattern(new Pattern(DyeColor.YELLOW, PatternType.BORDER));
    DUCK.addPattern(new Pattern(DyeColor.YELLOW, PatternType.STRIPE_BOTTOM));
    DUCK.addPattern(new Pattern(DyeColor.ORANGE, PatternType.STRIPE_MIDDLE));
    
    BannerCosmetic CHICKEN = new BannerCosmetic("Galinha", EnumRarity.EPICO, DyeColor.WHITE);
    CHICKEN.addPattern(new Pattern(DyeColor.BLACK, PatternType.STRIPE_TOP));
    CHICKEN.addPattern(new Pattern(DyeColor.WHITE, PatternType.STRIPE_CENTER));
    CHICKEN.addPattern(new Pattern(DyeColor.ORANGE, PatternType.STRIPE_MIDDLE));
    CHICKEN.addPattern(new Pattern(DyeColor.RED, PatternType.HALF_HORIZONTAL_MIRROR));
    CHICKEN.addPattern(new Pattern(DyeColor.WHITE, PatternType.BORDER));
    CHICKEN.addPattern(new Pattern(DyeColor.YELLOW, PatternType.STRIPE_MIDDLE));
    CHICKEN.addPattern(new Pattern(DyeColor.WHITE, PatternType.STRIPE_BOTTOM));
    
    BannerCosmetic ASSASSINS_CREED = new BannerCosmetic("Assassins Creed", EnumRarity.DIVINO, DyeColor.BLACK);
    ASSASSINS_CREED.addPattern(new Pattern(DyeColor.WHITE, PatternType.HALF_HORIZONTAL_MIRROR));
    ASSASSINS_CREED.addPattern(new Pattern(DyeColor.WHITE, PatternType.RHOMBUS_MIDDLE));
    ASSASSINS_CREED.addPattern(new Pattern(DyeColor.BLACK, PatternType.CURLY_BORDER));
    ASSASSINS_CREED.addPattern(new Pattern(DyeColor.BLACK, PatternType.STRIPE_BOTTOM));
    ASSASSINS_CREED.addPattern(new Pattern(DyeColor.BLACK, PatternType.CIRCLE_MIDDLE));
    ASSASSINS_CREED.addPattern(new Pattern(DyeColor.BLACK, PatternType.TRIANGLE_BOTTOM));
    
    BannerCosmetic UCHIHA = new BannerCosmetic("Uchiha", EnumRarity.EPICO, DyeColor.BLACK);
    UCHIHA.addPattern(new Pattern(DyeColor.WHITE, PatternType.CIRCLE_MIDDLE));
    UCHIHA.addPattern(new Pattern(DyeColor.WHITE, PatternType.FLOWER));
    UCHIHA.addPattern(new Pattern(DyeColor.BLACK, PatternType.STRIPE_BOTTOM));
    UCHIHA.addPattern(new Pattern(DyeColor.BLACK, PatternType.STRIPE_BOTTOM));
    UCHIHA.addPattern(new Pattern(DyeColor.BLACK, PatternType.STRIPE_BOTTOM));
    UCHIHA.addPattern(new Pattern(DyeColor.WHITE, PatternType.STRAIGHT_CROSS));
    UCHIHA.addPattern(new Pattern(DyeColor.RED, PatternType.HALF_HORIZONTAL));
    UCHIHA.addPattern(new Pattern(DyeColor.BLACK, PatternType.CURLY_BORDER));
    UCHIHA.addPattern(new Pattern(DyeColor.BLACK, PatternType.STRIPE_TOP));
    UCHIHA.addPattern(new Pattern(DyeColor.BLACK, PatternType.STRIPE_TOP));
    UCHIHA.addPattern(new Pattern(DyeColor.BLACK, PatternType.BORDER));
    
    BannerCosmetic BRAZIL_FLAG = new BannerCosmetic("Bandeira do Brazil", EnumRarity.COMUM, DyeColor.LIME);
    BRAZIL_FLAG.addPattern(new Pattern(DyeColor.YELLOW, PatternType.RHOMBUS_MIDDLE));
    BRAZIL_FLAG.addPattern(new Pattern(DyeColor.BLUE, PatternType.CIRCLE_MIDDLE));
    
    BannerCosmetic USA_FLAG = new BannerCosmetic("Bandeira do Estados Unidos", EnumRarity.EPICO, DyeColor.RED);
    USA_FLAG.addPattern(new Pattern(DyeColor.WHITE, PatternType.STRIPE_SMALL));
    USA_FLAG.addPattern(new Pattern(DyeColor.BLUE, PatternType.SQUARE_TOP_LEFT));
    
    BannerCosmetic CHINA_FLAG = new BannerCosmetic("Bandeira da China", EnumRarity.RARO, DyeColor.WHITE);
    CHINA_FLAG.addPattern(new Pattern(DyeColor.RED, PatternType.CIRCLE_MIDDLE));
    CHINA_FLAG.addPattern(new Pattern(DyeColor.RED, PatternType.CIRCLE_MIDDLE));
    CHINA_FLAG.addPattern(new Pattern(DyeColor.RED, PatternType.CIRCLE_MIDDLE));
    
    BannerCosmetic DERP = new BannerCosmetic("Derp", EnumRarity.EPICO, DyeColor.BLACK);
    DERP.addPattern(new Pattern(DyeColor.WHITE, PatternType.RHOMBUS_MIDDLE));
    DERP.addPattern(new Pattern(DyeColor.BLACK, PatternType.FLOWER));
    DERP.addPattern(new Pattern(DyeColor.BLACK, PatternType.BRICKS));
    DERP.addPattern(new Pattern(DyeColor.BLACK, PatternType.MOJANG));
    DERP.addPattern(new Pattern(DyeColor.BLACK, PatternType.STRIPE_BOTTOM));
    DERP.addPattern(new Pattern(DyeColor.BLACK, PatternType.MOJANG));
    DERP.addPattern(new Pattern(DyeColor.BLACK, PatternType.BRICKS));
    DERP.addPattern(new Pattern(DyeColor.BLACK, PatternType.FLOWER));
    DERP.addPattern(new Pattern(DyeColor.BLACK, PatternType.FLOWER));
    DERP.addPattern(new Pattern(DyeColor.BLACK, PatternType.MOJANG));
    DERP.addPattern(new Pattern(DyeColor.BLACK, PatternType.BRICKS));
    DERP.addPattern(new Pattern(DyeColor.BLACK, PatternType.TRIANGLE_TOP));
  }
  
  public void addPattern(Pattern pattern) {
    this.patterns.add(pattern);
  }
  
  @Override
  public void onSelect(CUser user) {
    user.selectCosmetic(Cosmetic.NONE_CLOTHES, 1);
    user.selectCosmetic(Cosmetic.NONE_HAT);
    user.selectCosmetic(Cosmetic.NONE_ANIMATED_HAT);
    user.handleAnimatedHat();
    user.handleHat();
    user.handleBanner();
  }
  
  public ItemStack getIcon(String color, String... loreList) {
    return this.getIcon(color, Arrays.asList(loreList));
  }
  
  public ItemStack getIcon(String color, List<String> loreList) {
    ItemStack icon = BukkitUtils.deserializeItemStack(this.item);
    BannerMeta meta = (BannerMeta) icon.getItemMeta();
    meta.setBaseColor(this.color);
    meta.setPatterns(this.patterns);
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
