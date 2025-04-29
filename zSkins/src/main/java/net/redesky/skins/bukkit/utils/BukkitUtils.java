package net.redesky.skins.bukkit.utils;

import com.mojang.authlib.GameProfile;
import com.mojang.authlib.properties.Property;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.Map.Entry;
import org.bukkit.Bukkit;
import org.bukkit.Color;
import org.bukkit.FireworkEffect;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.BookMeta;
import org.bukkit.inventory.meta.EnchantmentStorageMeta;
import org.bukkit.inventory.meta.FireworkEffectMeta;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.LeatherArmorMeta;
import org.bukkit.inventory.meta.PotionMeta;
import org.bukkit.inventory.meta.SkullMeta;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import net.redesky.skins.plugin.reflection.Accessors;
import net.redesky.skins.plugin.reflection.ConstructorAccessor;
import net.redesky.skins.plugin.reflection.FieldAccessor;
import net.redesky.skins.plugin.reflection.MethodAccessor;
import net.redesky.skins.plugin.reflection.MinecraftReflection;
import net.redesky.skins.plugin.utils.StringUtils;

public class BukkitUtils {
   public static final List<FieldAccessor<Color>> COLORS = new ArrayList();
   public static final MethodAccessor GET_PROFILE;
   public static final FieldAccessor<GameProfile> SKULL_META_PROFILE;
   private static Map<Class<?>, MethodAccessor> getHandleCache;
   private static Class<?> NBTagList;
   private static Class<?> NBTagString;
   private static ConstructorAccessor<?> constructorTagList;
   private static ConstructorAccessor<?> constructorTagString;
   private static MethodAccessor getTag;
   private static MethodAccessor setCompound;
   private static MethodAccessor addList;
   private static MethodAccessor asNMSCopy;
   private static MethodAccessor asCraftMirror;

   public static Object getHandle(Object target) {
      try {
         Class<?> clazz = target.getClass();
         MethodAccessor accessor = (MethodAccessor)getHandleCache.get(clazz);
         if (accessor == null) {
            accessor = Accessors.getMethod(clazz, "getHandle");
            getHandleCache.put(clazz, accessor);
         }

         return accessor.invoke(target);
      } catch (Exception var3) {
         throw new IllegalArgumentException("Cannot find method getHandle() for " + target + ".");
      }
   }

   public static void openBook(Player player, ItemStack book) {
      Object entityPlayer = getHandle(player);
      ItemStack old = player.getInventory().getItemInHand();

      try {
         player.getInventory().setItemInHand(book);
         Accessors.getMethod(entityPlayer.getClass(), "openBook").invoke(entityPlayer, asNMSCopy(book));
      } catch (Exception var5) {
         var5.printStackTrace();
      }

      player.getInventory().setItemInHand(old);
      player.updateInventory();
   }

   public static ItemStack deserializeItemStack(String item) {
      if (item != null && !item.isEmpty()) {
         item = StringUtils.formatColors(item).replace("\\n", "\n");
         String[] split = item.split(" : ");
         String mat = split[0].split(":")[0];
         ItemStack stack = new ItemStack(Material.matchMaterial(mat), 1);
         if (split[0].split(":").length > 1) {
            stack.setDurability((short)Integer.parseInt(split[0].split(":")[1]));
         }

         ItemMeta meta = stack.getItemMeta();
         BookMeta book = meta instanceof BookMeta ? (BookMeta)meta : null;
         SkullMeta skull = meta instanceof SkullMeta ? (SkullMeta)meta : null;
         PotionMeta potion = meta instanceof PotionMeta ? (PotionMeta)meta : null;
         FireworkEffectMeta effect = meta instanceof FireworkEffectMeta ? (FireworkEffectMeta)meta : null;
         LeatherArmorMeta armor = meta instanceof LeatherArmorMeta ? (LeatherArmorMeta)meta : null;
         EnchantmentStorageMeta enchantment = meta instanceof EnchantmentStorageMeta ? (EnchantmentStorageMeta)meta : null;
         if (split.length > 1) {
            stack.setAmount(Math.min(Integer.parseInt(split[1]), 64));
         }

         List<String> lore = new ArrayList();

         for(int i = 2; i < split.length; ++i) {
            String opt = split[i];
            if (opt.startsWith("nome>")) {
               meta.setDisplayName(StringUtils.formatColors(opt.split(">")[1]));
            } else {
               String[] flags;
               int var15;
               int var16;
               String pe;
               if (opt.startsWith("desc>")) {
                  flags = opt.split(">")[1].split("\n");
                  var15 = flags.length;

                  for(var16 = 0; var16 < var15; ++var16) {
                     pe = flags[var16];
                     lore.add(StringUtils.formatColors(pe));
                  }
               } else if (opt.startsWith("encantar>")) {
                  flags = opt.split(">")[1].split("\n");
                  var15 = flags.length;

                  for(var16 = 0; var16 < var15; ++var16) {
                     pe = flags[var16];
                     if (enchantment != null) {
                        enchantment.addStoredEnchant(Enchantment.getByName(pe.split(":")[0]), Integer.parseInt(pe.split(":")[1]), true);
                     } else {
                        meta.addEnchant(Enchantment.getByName(pe.split(":")[0]), Integer.parseInt(pe.split(":")[1]), true);
                     }
                  }
               } else if (!opt.startsWith("pintar>") || effect == null && armor == null) {
                  if (opt.startsWith("dono>") && skull != null) {
                     skull.setOwner(opt.split(">")[1]);
                  } else if (opt.startsWith("skin>") && skull != null) {
                     GameProfile gp = new GameProfile(UUID.randomUUID(), (String)null);
                     gp.getProperties().put("textures", new Property("textures", opt.split(">")[1]));
                     SKULL_META_PROFILE.set(skull, gp);
                  } else if (opt.startsWith("paginas>") && book != null) {
                     book.setPages(opt.split(">")[1].split("\\{pular}"));
                  } else if (opt.startsWith("autor>") && book != null) {
                     book.setAuthor(opt.split(">")[1]);
                  } else if (opt.startsWith("titulo>") && book != null) {
                     book.setTitle(opt.split(">")[1]);
                  } else if (opt.startsWith("efeito>") && potion != null) {
                     flags = opt.split(">")[1].split("\n");
                     var15 = flags.length;

                     for(var16 = 0; var16 < var15; ++var16) {
                        pe = flags[var16];
                        potion.addCustomEffect(new PotionEffect(PotionEffectType.getByName(pe.split(":")[0]), Integer.parseInt(pe.split(":")[2]), Integer.parseInt(pe.split(":")[1])), false);
                     }
                  } else if (opt.startsWith("esconder>")) {
                     flags = opt.split(">")[1].split("\n");
                     String[] var21 = flags;
                     var16 = flags.length;

                     for(int var22 = 0; var22 < var16; ++var22) {
                        String flag = var21[var22];
                        if (flag.equalsIgnoreCase("tudo")) {
                           meta.addItemFlags(ItemFlag.values());
                           break;
                        }

                        meta.addItemFlags(new ItemFlag[]{ItemFlag.valueOf(flag.toUpperCase())});
                     }
                  }
               } else {
                  flags = opt.split(">")[1].split("\n");
                  var15 = flags.length;

                  for(var16 = 0; var16 < var15; ++var16) {
                     pe = flags[var16];
                     if (pe.split(":").length > 2) {
                        if (armor != null) {
                           armor.setColor(Color.fromRGB(Integer.parseInt(pe.split(":")[0]), Integer.parseInt(pe.split(":")[1]), Integer.parseInt(pe.split(":")[2])));
                        } else if (effect != null) {
                           effect.setEffect(FireworkEffect.builder().withColor(Color.fromRGB(Integer.parseInt(pe.split(":")[0]), Integer.parseInt(pe.split(":")[1]), Integer.parseInt(pe.split(":")[2]))).build());
                        }
                     } else {
                        Iterator var18 = COLORS.iterator();

                        while(var18.hasNext()) {
                           FieldAccessor<Color> field = (FieldAccessor)var18.next();
                           if (field.getHandle().getName().equals(pe.toUpperCase())) {
                              if (armor != null) {
                                 armor.setColor((Color)field.get((Object)null));
                              } else if (effect != null) {
                                 effect.setEffect(FireworkEffect.builder().withColor((Color)field.get((Object)null)).build());
                              }
                              break;
                           }
                        }
                     }
                  }
               }
            }
         }

         if (!lore.isEmpty()) {
            meta.setLore(lore);
         }

         stack.setItemMeta(meta);
         return stack;
      } else {
         return new ItemStack(Material.AIR);
      }
   }

   public static String serializeItemStack(ItemStack item) {
      StringBuilder sb = new StringBuilder(item.getType().name() + (item.getDurability() != 0 ? ":" + item.getDurability() : "") + " : " + item.getAmount());
      ItemMeta meta = item.getItemMeta();
      BookMeta book = meta instanceof BookMeta ? (BookMeta)meta : null;
      SkullMeta skull = meta instanceof SkullMeta ? (SkullMeta)meta : null;
      PotionMeta potion = meta instanceof PotionMeta ? (PotionMeta)meta : null;
      FireworkEffectMeta effect = meta instanceof FireworkEffectMeta ? (FireworkEffectMeta)meta : null;
      LeatherArmorMeta armor = meta instanceof LeatherArmorMeta ? (LeatherArmorMeta)meta : null;
      EnchantmentStorageMeta enchantment = meta instanceof EnchantmentStorageMeta ? (EnchantmentStorageMeta)meta : null;
      if (meta.hasDisplayName()) {
         sb.append(" : nome>").append(StringUtils.deformatColors(meta.getDisplayName()));
      }

      int size;
      if (meta.hasLore()) {
         sb.append(" : desc>");

         for(size = 0; size < meta.getLore().size(); ++size) {
            String line = (String)meta.getLore().get(size);
            sb.append(line).append(size + 1 == meta.getLore().size() ? "" : "\n");
         }
      }

      Iterator var14;
      StringBuilder var10000;
      if (meta.hasEnchants() || enchantment != null && enchantment.hasStoredEnchants()) {
         sb.append(" : encantar>");
         size = 0;
         var14 = (enchantment != null ? enchantment.getStoredEnchants() : meta.getEnchants()).entrySet().iterator();

         while(var14.hasNext()) {
            Entry<Enchantment, Integer> entry = (Entry)var14.next();
            int level = (Integer)entry.getValue();
            String name = ((Enchantment)entry.getKey()).getName();
            var10000 = sb.append(name).append(":").append(level);
            ++size;
            var10000.append(size == (enchantment != null ? enchantment.getStoredEnchants() : meta.getEnchants()).size() ? "" : "\n");
         }
      }

      if (skull != null && !skull.getOwner().isEmpty()) {
         sb.append(" : dono>").append(skull.getOwner());
      }

      if (book != null && book.hasPages()) {
         sb.append(" : paginas>").append(StringUtils.join((Collection)book.getPages(), "{pular}"));
      }

      if (book != null && book.hasTitle()) {
         sb.append(" : titulo>").append(book.getTitle());
      }

      if (book != null && book.hasAuthor()) {
         sb.append(" : autor>").append(book.getAuthor());
      }

      if (effect != null && effect.hasEffect() && !effect.getEffect().getColors().isEmpty() || armor != null && armor.getColor() != null) {
         Color color = effect != null ? (Color)effect.getEffect().getColors().get(0) : armor.getColor();
         sb.append(" : pintar>").append(color.getRed()).append(":").append(color.getGreen()).append(":").append(color.getBlue());
      }

      if (potion != null && potion.hasCustomEffects()) {
         sb.append(" : efeito>");
         size = 0;
         var14 = potion.getCustomEffects().iterator();

         while(var14.hasNext()) {
            PotionEffect pe = (PotionEffect)var14.next();
            var10000 = sb.append(pe.getType().getName()).append(":").append(pe.getAmplifier()).append(":").append(pe.getDuration());
            ++size;
            var10000.append(size == potion.getCustomEffects().size() ? "" : "\n");
         }
      }

      Iterator var17 = meta.getItemFlags().iterator();

      while(var17.hasNext()) {
         ItemFlag flag = (ItemFlag)var17.next();
         sb.append(" : esconder>").append(flag.name());
      }

      return StringUtils.deformatColors(sb.toString()).replace("\n", "\\n");
   }

   public static ItemStack putProfileOnSkull(Player player, ItemStack head) {
      if (head != null && head.getItemMeta() instanceof SkullMeta) {
         ItemMeta meta = head.getItemMeta();
         SKULL_META_PROFILE.set(meta, (GameProfile)GET_PROFILE.invoke(player));
         head.setItemMeta(meta);
         return head;
      } else {
         return head;
      }
   }

   public static ItemStack putProfileOnSkull(Object profile, ItemStack head) {
      if (head != null && head.getItemMeta() instanceof SkullMeta) {
         ItemMeta meta = head.getItemMeta();
         SKULL_META_PROFILE.set(meta, (GameProfile)profile);
         head.setItemMeta(meta);
         return head;
      } else {
         return head;
      }
   }

   public static void putGlowEnchantment(ItemStack item) {
      ItemMeta meta = item.getItemMeta();
      meta.addEnchant(Enchantment.LURE, 1, true);
      meta.addItemFlags(new ItemFlag[]{ItemFlag.HIDE_ENCHANTS});
      item.setItemMeta(meta);
   }

   public static Object asNMSCopy(ItemStack item) {
      return asNMSCopy.invoke((Object)null, item);
   }

   public static ItemStack asCraftMirror(Object nmsItem) {
      return (ItemStack)asCraftMirror.invoke((Object)null, nmsItem);
   }

   public static ItemStack setNBTList(ItemStack item, String key, List<String> strings) {
      Object nmsStack = asNMSCopy(item);
      Object compound = getTag.invoke(nmsStack);
      Object compoundList = constructorTagList.newInstance();
      Iterator var6 = strings.iterator();

      while(var6.hasNext()) {
         String string = (String)var6.next();
         addList.invoke(compoundList, constructorTagString.newInstance(string));
      }

      setCompound.invoke(compound, key, compoundList);
      return asCraftMirror(nmsStack);
   }

   public static String serializeLocation(Location unserialized) {
      return unserialized.getWorld().getName() + "; " + unserialized.getX() + "; " + unserialized.getY() + "; " + unserialized.getZ() + "; " + unserialized.getYaw() + "; " + unserialized.getPitch();
   }

   public static Location deserializeLocation(String serialized) {
      String[] divPoints = serialized.split("; ");
      Location deserialized = new Location(Bukkit.getWorld(divPoints[0]), Double.parseDouble(divPoints[1]), Double.parseDouble(divPoints[2]), Double.parseDouble(divPoints[3]));
      deserialized.setYaw(Float.parseFloat(divPoints[4]));
      deserialized.setPitch(Float.parseFloat(divPoints[5]));
      return deserialized;
   }

   static {
      Field[] var0 = Color.class.getDeclaredFields();
      int var1 = var0.length;

      for(int var2 = 0; var2 < var1; ++var2) {
         Field field = var0[var2];
         if (field.getType().equals(Color.class)) {
            COLORS.add(new FieldAccessor(field));
         }
      }

      GET_PROFILE = Accessors.getMethod(MinecraftReflection.getCraftBukkitClass("entity.CraftPlayer"), GameProfile.class, 0);
      SKULL_META_PROFILE = Accessors.getField(MinecraftReflection.getCraftBukkitClass("inventory.CraftMetaSkull"), "profile", GameProfile.class);
      getHandleCache = new HashMap();
      NBTagList = MinecraftReflection.getMinecraftClass("NBTTagList");
      NBTagString = MinecraftReflection.getMinecraftClass("NBTTagString");
      constructorTagList = new ConstructorAccessor(NBTagList.getConstructors()[0]);
      constructorTagString = new ConstructorAccessor(NBTagString.getConstructors()[1]);
      getTag = Accessors.getMethod(MinecraftReflection.getItemStackClass(), "getTag");
      setCompound = Accessors.getMethod(MinecraftReflection.getNBTTagCompoundClass(), "set", String.class, NBTagList.getSuperclass());
      addList = Accessors.getMethod(NBTagList, "add");
      asNMSCopy = Accessors.getMethod(MinecraftReflection.getCraftItemStackClass(), "asNMSCopy");
      asCraftMirror = Accessors.getMethod(MinecraftReflection.getCraftItemStackClass(), "asCraftMirror");
   }
}
