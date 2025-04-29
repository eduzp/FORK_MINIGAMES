package mc.twilight.cosmeticos.cosmetics.types;

import mc.twilight.cosmeticos.cosmetics.Cosmetic;
import mc.twilight.cosmeticos.cosmetics.CosmeticType;
import mc.twilight.cosmeticos.hook.player.CUser;
import mc.twilight.core.utils.BukkitUtils;
import mc.twilight.core.utils.enums.EnumRarity;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Base64;
import java.util.List;

public class AnimatedHatCosmetic extends Cosmetic {
  
  private final String icon;
  private final List<String> frames;
  private final int ticksPerFrame;
  
  public AnimatedHatCosmetic(String name, EnumRarity rarity, String icon, int ticksPerFrame) {
    super(name, rarity, CosmeticType.ANIMATED_HAT);
    this.icon = icon;
    this.frames = new ArrayList<>();
    this.ticksPerFrame = ticksPerFrame;
    Cosmetic.addCosmetic(this);
  }
  
  public static void setupAnimatedHats() {
    String textureId = "42737e99e4c0596a3712e7711baecae8d1ddb774ac1cf531896862380753e16";
    AnimatedHatCosmetic love = new AnimatedHatCosmetic("Apaixonado", EnumRarity.COMUM,
        "SKULL_ITEM:3 : 1 : nome>Apaixonado : desc>&7Divirta-se pelos nossos lobbies\n&7esboçando a reação Apaixonado! : skin>" + Base64.getEncoder()
            .encodeToString(("{\"textures\":{\"SKIN\":{\"url\":\"http://textures.minecraft.net/texture/" + textureId + "\"}}}").getBytes()), 2);
    love.addTextureId("a080c9bfc64aeb5aed2acaa885d6fcbbd5e5ddf468956d3f1b1e455774d48893");
    love.addTextureId("a4d678bb120fbd3beacaf36bdb117766a63d7c2d96a6e85a8ef5a2b13e166");
    love.addTextureId("42737e99e4c0596a3712e7711baecae8d1ddb774ac1cf531896862380753e16");
    love.addTextureId("a4d678bb120fbd3beacaf36bdb117766a63d7c2d96a6e85a8ef5a2b13e166");
    love.addTextureId("a080c9bfc64aeb5aed2acaa885d6fcbbd5e5ddf468956d3f1b1e455774d48893");
    love.addTextureId("a4d678bb120fbd3beacaf36bdb117766a63d7c2d96a6e85a8ef5a2b13e166");
    love.addTextureId("42737e99e4c0596a3712e7711baecae8d1ddb774ac1cf531896862380753e16");
    love.addTextureId("a4d678bb120fbd3beacaf36bdb117766a63d7c2d96a6e85a8ef5a2b13e166");
    love.addTextureId("a080c9bfc64aeb5aed2acaa885d6fcbbd5e5ddf468956d3f1b1e455774d48893");
    
    textureId = "01b9def55876c41c17c815f88115f02c95f89620fbed6a6cb2d38d46fe05";
    AnimatedHatCosmetic happy = new AnimatedHatCosmetic("Alegre", EnumRarity.RARO,
        "SKULL_ITEM:3 : 1 : nome>Alegre : desc>&7Divirta-se pelos nossos lobbies\n&7esboçando a reação Alegre! : skin>" + Base64.getEncoder()
            .encodeToString(("{\"textures\":{\"SKIN\":{\"url\":\"http://textures.minecraft.net/texture/" + textureId + "\"}}}").getBytes()), 1);
    happy.addTextureId("ef66c7485ccb853a6538122e45c8a4821fbe097f96a6060feb981f7a2bba890");
    happy.addTextureId("a5d43eb0ec5f6de1d469b69680978a6dd7117772ee0d82ffdf08749e84df7ed");
    for (int i = 0; i < 7; i++) {
      happy.addTextureId("473e72cc371de25f3305665769dd7e9ff1161695252e799612580deeedd3");
    }
    for (int i = 0; i < 15; i++) {
      happy.addTextureId("01b9def55876c41c17c815f88115f02c95f89620fbed6a6cb2d38d46fe05");
    }
    
    textureId = "fe3e22761c76b4f8fad89dbc80f3af203e7b8211238011be7ffb80261d9c64";
    AnimatedHatCosmetic cry = new AnimatedHatCosmetic("Chorando", EnumRarity.EPICO,
        "SKULL_ITEM:3 : 1 : nome>Chorando : desc>&7Divirta-se pelos nossos lobbies\n&7esboçando a reação Chorando! : skin>" + Base64.getEncoder()
            .encodeToString(("{\"textures\":{\"SKIN\":{\"url\":\"http://textures.minecraft.net/texture/" + textureId + "\"}}}").getBytes()), 1);
    cry.addTextureId("d6a4b88a297c78f9dd53ee8c7164dda0ded7e988382555bc7d89c901922b32e");
    cry.addTextureId("196b8e272c54a422d9df36d85caff26624c733e7b3f6040d3e4c9cd6e");
    cry.addTextureId("dec9aa9b3f46195ae9c7fea7c61148764a41e0d68dae41e82868d792b3c");
    cry.addTextureId("be29dadb60c9096fab92ffa7749e30462e14a8afaf6de938d9c0a4d78781");
    cry.addTextureId("c8aba1f49fbf8829859ddd8f7e5918155e7ddc78919768b6e6c536e5278c31");
    cry.addTextureId("1073ba3f1ca1d1e4f7e1ec742ddcff8fb0d962bc5662d127622a3726e3bb66");
    cry.addTextureId("952dcdb13f732342ef37cbf0902960984992f5e67289373054b00c2a1f7");
    cry.addTextureId("4b0f2f3d3499959e97d27e610bcfd90dbf8df5e1cf4b98f259284f2e355728");
    cry.addTextureId("b3f2bbd15ea14faf22b0698ec032f64eb13db379c8ee1ef78a9b27f2ae6d8662");
    cry.addTextureId("fe3e22761c76b4f8fad89dbc80f3af203e7b8211238011be7ffb80261d9c64");
    cry.addTextureId("732fe121a63eaabd99ced6d1acc91798652d1ee8084d2f9127d8a315cad5ce4");
    cry.addTextureId("b3f2bbd15ea14faf22b0698ec032f64eb13db379c8ee1ef78a9b27f2ae6d8662");
    cry.addTextureId("3864b0925afc2d31af69ae124d05c9ca31ce25f9d2e97b569b90ca236a3e");
    cry.addTextureId("4b0f2f3d3499959e97d27e610bcfd90dbf8df5e1cf4b98f259284f2e355728");
    cry.addTextureId("b3f2bbd15ea14faf22b0698ec032f64eb13db379c8ee1ef78a9b27f2ae6d8662");
    cry.addTextureId("fe3e22761c76b4f8fad89dbc80f3af203e7b8211238011be7ffb80261d9c64");
    cry.addTextureId("732fe121a63eaabd99ced6d1acc91798652d1ee8084d2f9127d8a315cad5ce4");
    cry.addTextureId("b3f2bbd15ea14faf22b0698ec032f64eb13db379c8ee1ef78a9b27f2ae6d8662");
    cry.addTextureId("3864b0925afc2d31af69ae124d05c9ca31ce25f9d2e97b569b90ca236a3e");
    cry.addTextureId("4b0f2f3d3499959e97d27e610bcfd90dbf8df5e1cf4b98f259284f2e355728");
    cry.addTextureId("b3f2bbd15ea14faf22b0698ec032f64eb13db379c8ee1ef78a9b27f2ae6d8662");
    cry.addTextureId("fe3e22761c76b4f8fad89dbc80f3af203e7b8211238011be7ffb80261d9c64");
    cry.addTextureId("732fe121a63eaabd99ced6d1acc91798652d1ee8084d2f9127d8a315cad5ce4");
    cry.addTextureId("b3f2bbd15ea14faf22b0698ec032f64eb13db379c8ee1ef78a9b27f2ae6d8662");
    cry.addTextureId("3864b0925afc2d31af69ae124d05c9ca31ce25f9d2e97b569b90ca236a3e");
    cry.addTextureId("4b0f2f3d3499959e97d27e610bcfd90dbf8df5e1cf4b98f259284f2e355728");
    cry.addTextureId("b3f2bbd15ea14faf22b0698ec032f64eb13db379c8ee1ef78a9b27f2ae6d8662");
    cry.addTextureId("fe3e22761c76b4f8fad89dbc80f3af203e7b8211238011be7ffb80261d9c64");
    cry.addTextureId("732fe121a63eaabd99ced6d1acc91798652d1ee8084d2f9127d8a315cad5ce4");
    cry.addTextureId("b3f2bbd15ea14faf22b0698ec032f64eb13db379c8ee1ef78a9b27f2ae6d8662");
    
    textureId = "e95b20fb1fcfbef222062dd43eecbcb3871c528665f8ed675f42fc6e589a0b7";
    AnimatedHatCosmetic angry = new AnimatedHatCosmetic("Estressado", EnumRarity.EPICO,
        "SKULL_ITEM:3 : 1 : nome>Estressado : desc>&7Divirta-se pelos nossos lobbies\n&7esboçando a reação Estressado! : skin>" + Base64.getEncoder()
            .encodeToString(("{\"textures\":{\"SKIN\":{\"url\":\"http://textures.minecraft.net/texture/" + textureId + "\"}}}").getBytes()), 1);
    angry.addTextureId("7d41407363bcb46837538a63fdf7055278d42dc4aac639ed5794533bbd770");
    angry.addTextureId("47bbf6d9f4c57556eef816c50eb75f9d158f53954957aabe6c2e14ffa6c90");
    angry.addTextureId("a750127f1c3c71f6a5f5e9917a825e9235e1959b258ff29b6ff9771cb44");
    angry.addTextureId("e95b20fb1fcfbef222062dd43eecbcb3871c528665f8ed675f42fc6e589a0b7");
    angry.addTextureId("275c46184f9a85351d6ba618f8d1655cb5b71d6fc6ed3ccc462d916d376a8db");
    angry.addTextureId("fa151ceb66b3412775e9d44879046a398dbdb7dfcb0af571b7a03e72d9fbf1");
    angry.addTextureId("d82738fc82bedaf3029612f1ec92fe0cf848e541c8e30dcf41efc04bea30ba");
    for (int i = 0; i < 5; i++) {
      angry.addTextureId("fa151ceb66b3412775e9d44879046a398dbdb7dfcb0af571b7a03e72d9fbf1");
      angry.addTextureId("7f8db8cf241f2565c5bd495a0695b7cac9370c8bfd732d6d874e62fb12f3da");
      angry.addTextureId("fa151ceb66b3412775e9d44879046a398dbdb7dfcb0af571b7a03e72d9fbf1");
      angry.addTextureId("d82738fc82bedaf3029612f1ec92fe0cf848e541c8e30dcf41efc04bea30ba");
    }
    
    textureId = "6313411e97963d104322218967a85a5d691330bad5f7192e3781d9565ebbdf";
    AnimatedHatCosmetic tdfw = new AnimatedHatCosmetic("TDFW", EnumRarity.DIVINO,
        "SKULL_ITEM:3 : 1 : nome>TDFW : desc>&7Divirta-se pelos nossos lobbies\n&7esboçando a reação TDFW! : skin>" + Base64.getEncoder()
            .encodeToString(("{\"textures\":{\"SKIN\":{\"url\":\"http://textures.minecraft.net/texture/" + textureId + "\"}}}").getBytes()), 3);
    
    tdfw.addTextureId("72bb8ba79648718fe80687ed4df2b9e284e732583e05658e227efd7fdf80f4");
    tdfw.addTextureId("29b5b1f2c92a1283456f608b29ec3617191aba2bd31bd4b4b08e6cba6806227");
    tdfw.addTextureId("7959ef5fabb3f83fb19bba6ca67bb97758eec60235cf46e71d834b237337c4");
    tdfw.addTextureId("6313411e97963d104322218967a85a5d691330bad5f7192e3781d9565ebbdf");
    tdfw.addTextureId("fa3f7f2f6970d32db284261520c8c441fe4b3268ac0c99aeb4a5248656bd");
    
    
    textureId = "4c3b089e446f065dd9059519c85c45aebb53891be3c3a7ed5b5eb61a96747";
    AnimatedHatCosmetic surprised = new AnimatedHatCosmetic("Espantado", EnumRarity.DIVINO,
        "SKULL_ITEM:3 : 1 : nome>Espantado : desc>&7Divirta-se pelos nossos lobbies\n&7esboçando a reação Espantado! : skin>" + Base64.getEncoder()
            .encodeToString(("{\"textures\":{\"SKIN\":{\"url\":\"http://textures.minecraft.net/texture/" + textureId + "\"}}}").getBytes()), 1);
    surprised.addTextureId("6b7f24bb6a4585de8f42303161bded5c8398ce375631be149460d6835aec44e");
    surprised.addTextureId("33c760f660d447846ab6b3d5a914c4b01f10672b63d4311d468b6dc28ba0e3");
    surprised.addTextureId("382d15e94182206025973ff1928f4456bf7abaff737942d54b1c5699892c");
    surprised.addTextureId("9d641bd33180c53dcc77e3d4c665935e63011d87ae9796a2ae7bd334cd64");
    for (int i = 0; i < 8; i++) {
      surprised.addTextureId("4c3b089e446f065dd9059519c85c45aebb53891be3c3a7ed5b5eb61a96747");
    }
    
    AnimatedHatCosmetic wink = new AnimatedHatCosmetic("Piscadela", EnumRarity.DIVINO, "SKULL_ITEM:3 : 1 : nome>Piscadela : desc>&7Divirta-se pelos nossos lobbies\n&7esboçando a reação Piscadela! : skin>" + Base64.getEncoder()
        .encodeToString(("{\"textures\":{\"SKIN\":{\"url\":\"http://textures.minecraft.net/texture/"
            + textureId + "\"}}}").getBytes()), 2);
    
    wink.addTextureId("ef66c7485ccb853a6538122e45c8a4821fbe097f96a6060feb981f7a2bba890");
    wink.addTextureId("a5d43eb0ec5f6de1d469b69680978a6dd7117772ee0d82ffdf08749e84df7ed");
    for (int i = 0; i < 2; i++) {
      wink.addTextureId("473e72cc371de25f3305665769dd7e9ff1161695252e799612580deeedd3");
    }
    for (int i = 0; i < 4; i++) {
      wink.addTextureId("f4ea2d6f939fefeff5d122e63dd26fa8a427df90b2928bc1fa89a8252a7e");
    }
    
    textureId = "f4ea2d6f939fefeff5d122e63dd26fa8a427df90b2928bc1fa89a8252a7e";
    AnimatedHatCosmetic cool = new AnimatedHatCosmetic("Legal", EnumRarity.DIVINO, "SKULL_ITEM:3 : 1 : nome>Legal : desc>&7Divirta-se pelos nossos lobbies\n&7esboçando a reação Legal! : skin>" + Base64.getEncoder()
        .encodeToString(("{\"textures\":{\"SKIN\":{\"url\":\"http://textures.minecraft.net/texture/"
            + textureId + "\"}}}").getBytes()), 2);
    
    cool.addTextureId("a21e6dbfd74a1859ddbae3380fc1ab71f2389745945fc92329b164635bd14f");
    cool.addTextureId("3733db9a94bfe15cdbb7ca5832c85cfada98ad2c839934766bdc41f977b5c163");
    for (int i = 0; i < 4; i++) {
      cool.addTextureId("766b3eef3c726ecb816c43839189eeb8e36382e3e5fe41128372785185a322");
    }
    
    textureId = "207eef91a453a5151487c9d6b9d4c434db7f8a02a4caf18ef6f3358677f6";
    AnimatedHatCosmetic cheeky = new AnimatedHatCosmetic("Dando Língua", EnumRarity.DIVINO, "SKULL_ITEM:3 : 1 : nome>Dando Língua : desc>&7Divirta-se pelos nossos lobbies\n&7esboçando a reação Dando Língua! : skin>" + Base64.getEncoder()
        .encodeToString(("{\"textures\":{\"SKIN\":{\"url\":\"http://textures.minecraft.net/texture/"
            + textureId + "\"}}}").getBytes()), 1);
    
    for (int i = 0; i < 4; i++) {
      cheeky.addTextureId("319ec094258842725e41f985346a7f824af6fc6cf13fbe949c9c465a30bc99");
    }
    cheeky.addTextureId("b7d533e65f2cae97afe334c81ecc97e2fa5b3e5d3ecf8b91bc39a5adb2e79a");
    cheeky.addTextureId("35a46f8334e49d273384eb72b2ac15e24a640d7648e4b28c348efce93dc97ab");
    cheeky.addTextureId("1d977753c3db742865ccf14c5c3f482eaf5721414750e6d3be96e1ae7c8291c4");
    cheeky.addTextureId("c4188e6d90f7769dae3a7277e2490d01b8017d74a725fd3aebbc82a911aa4e");
    cheeky.addTextureId("1d977753c3db742865ccf14c5c3f482eaf5721414750e6d3be96e1ae7c8291c4");
    cheeky.addTextureId("447dcf9dd283ad6d83942b6607a7ce45bee9cdfeefb849da29d661d03e7938");
    cheeky.addTextureId("de355559f4cd56118b4bc8b4697b625e1845b635790c07bf4924c8c7673a2e4");
    cheeky.addTextureId("207eef91a453a5151487c9d6b9d4c434db7f8a02a4caf18ef6f3358677f6");
    cheeky.addTextureId("c4188e6d90f7769dae3a7277e2490d01b8017d74a725fd3aebbc82a911aa4e");
    cheeky.addTextureId("1d977753c3db742865ccf14c5c3f482eaf5721414750e6d3be96e1ae7c8291c4");
    cheeky.addTextureId("447dcf9dd283ad6d83942b6607a7ce45bee9cdfeefb849da29d661d03e7938");
    cheeky.addTextureId("de355559f4cd56118b4bc8b4697b625e1845b635790c07bf4924c8c7673a2e4");
    cheeky.addTextureId("207eef91a453a5151487c9d6b9d4c434db7f8a02a4caf18ef6f3358677f6");
    for (int i = 0; i < 10; i++) {
      cheeky.addTextureId("c4188e6d90f7769dae3a7277e2490d01b8017d74a725fd3aebbc82a911aa4e");
    }
    
    textureId = "30e78285d5aee0b28787ad88a5d58fb05ccf22918daa516ead85a6bf4fe068";
    AnimatedHatCosmetic rip = new AnimatedHatCosmetic("Rip", EnumRarity.DIVINO, "SKULL_ITEM:3 : 1 : nome>Rip : desc>&7Divirta-se pelos nossos lobbies\n&7esboçando a reação Rip! : skin>" + Base64.getEncoder()
        .encodeToString(("{\"textures\":{\"SKIN\":{\"url\":\"http://textures.minecraft.net/texture/"
            + textureId + "\"}}}").getBytes()), 2);
    
    rip.addTextureId("30e78285d5aee0b28787ad88a5d58fb05ccf22918daa516ead85a6bf4fe068");
    rip.addTextureId("63611b5724e091854e79926fd11e486bfd0f99042721c3b34177f818639c19d");
    rip.addTextureId("83e0621b45d3a326d236293cd8ea49ae74d52e56fc8d1d133e7fc8bcf2a5988");
    rip.addTextureId("6e16a7ae186c3cfeac364eac0e83d3528741c3dd9ef8277080e03deabc714");
    rip.addTextureId("20ec3a80ed35bd9beb7d20cb75f1ecd5b8ab0d576f1db699f7def13131fbc5");
    rip.addTextureId("b03badcc9fb966c87e0dc1332d735b2b587c2602d35fecb44ba6ed94ceb4");
    rip.addTextureId("439c3df7a628af8d751ecca197642cdc1a07c30e3289b2d3261f7a65cf395b");
    
    textureId = "927ebbf5c2535fe6b5cef8b8a7e1e7067a39ed21ba547f83fce4472184d80c7";
    AnimatedHatCosmetic relax = new AnimatedHatCosmetic("Relaxado", EnumRarity.DIVINO, "SKULL_ITEM:3 : 1 : nome>Relaxado : desc>&7Divirta-se pelos nossos lobbies\n&7esboçando a reação Relaxado! : skin>" + Base64.getEncoder()
        .encodeToString(("{\"textures\":{\"SKIN\":{\"url\":\"http://textures.minecraft.net/texture/"
            + textureId + "\"}}}").getBytes()), 3);
    
    relax.addTextureId("927ebbf5c2535fe6b5cef8b8a7e1e7067a39ed21ba547f83fce4472184d80c7");
    relax.addTextureId("fd2dc4db780294919517306964d65ea078b47b823fda5628be48b6ae61c");
    relax.addTextureId("e744e61b46a5f749ccaa2bf8132981b8986234359f9a2924f547ec0111e4375");
    relax.addTextureId("f673dd2ced78d89dac845d395443d6d39bcfe9c74c71a652029487b9281");
    relax.addTextureId("720f158ea771adf0862e7c1a19f01409cddd6edfd64385682ed7bc653eda3");
    relax.addTextureId("b6d6581ee0ec93ca9d5f4afbf6e28f5a9582a896ccccb9e7c17e6419e597e27");
    relax.addTextureId("e08876a49b1abbad149724be3eae35aa6305c529e384c118ba381a81e2df59e");
    
    for (int i = 0; i < 2; ++i) {
      relax.addTextureId("927ebbf5c2535fe6b5cef8b8a7e1e7067a39ed21ba547f83fce4472184d80c7");
      relax.addTextureId("b6d6581ee0ec93ca9d5f4afbf6e28f5a9582a896ccccb9e7c17e6419e597e27");
    }
    
    relax.addTextureId("cc86703cc5839d413e393f173dde4fb71cfc965e1d254ae7d7bb38bf0a233d5");
    relax.addTextureId("762c3a6265418977a564fa9376fb5b1a87f9f8b8052c63a2d51817691e4223a");
  }
  
  public static AnimatedHatCosmetic getById(long id) {
    return Cosmetic.listCosmetics(AnimatedHatCosmetic.class).stream().filter(c -> c.getUniqueId() == id).findFirst().orElse(null);
  }
  
  private void addTextureId(String textureId) {
    this.frames.add(Base64.getEncoder().encodeToString(("{\"textures\":{\"SKIN\":{\"url\":\"http://textures.minecraft.net/texture/" + textureId + "\"}}}").getBytes()));
  }
  
  @Override
  public void onSelect(CUser user) {
    user.selectCosmetic(Cosmetic.NONE_CLOTHES, 1);
    user.selectCosmetic(Cosmetic.NONE_HAT);
    user.selectCosmetic(Cosmetic.NONE_BANNER);
    user.handleClothes();
    user.handleHat();
    user.handleBanner();
    user.handleAnimatedHat();
  }
  
  public int getTicksPerFrame() {
    return this.ticksPerFrame;
  }
  
  public List<String> listFrames() {
    return this.frames;
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
  
  public static class AnimatedHatSlider {
    
    private final AnimatedHatCosmetic cosmetic;
    private int ticks = 0;
    private int intervalTick = 0;
    private int currentFrame = 0;
    private boolean up;
    public AnimatedHatSlider(AnimatedHatCosmetic cosmetic) {
      this.cosmetic = cosmetic;
    }
    
    public String next() {
      String next = null;
      
      if (this.ticks < this.cosmetic.getTicksPerFrame()) {
        this.ticks++;
      } else {
        this.ticks = 0;
        next = this.cosmetic.listFrames().get(currentFrame);
        
        if (this.up) {
          if (this.currentFrame >= this.cosmetic.listFrames().size() - 1) {
            this.up = false;
          } else {
            this.currentFrame++;
          }
        } else {
          if (this.currentFrame <= 0) {
            if (this.intervalTick >= 20 / this.cosmetic.getTicksPerFrame()) {
              this.up = true;
              this.intervalTick = 0;
            } else {
              this.intervalTick++;
            }
          } else {
            this.currentFrame--;
          }
        }
      }
      
      return next;
    }
  }
}
