package net.redesky.skins.plugin.reflection;

import java.util.Arrays;

public class MinecraftReflection {
   public static final MinecraftVersion VERSION = MinecraftVersion.getCurrentVersion();
   public static String NMU_PREFIX = "";
   public static String OBC_PREFIX;
   public static String NMS_PREFIX;
   private static Class<?> craftItemStack;
   private static Class<?> block;
   private static Class<?> blocks;
   private static Class<?> entity;
   private static Class<?> entityHuman;
   private static Class<?> enumDirection;
   private static Class<?> enumProtocol;
   private static Class<?> enumGamemode;
   private static Class<?> enumPlayerInfoAction;
   private static Class<?> enumTitleAction;
   private static Class<?> nbtTagCompound;
   private static Class<?> channel;
   private static Class<?> playerInfoData;
   private static Class<?> serverPing;
   private static Class<?> serverData;
   private static Class<?> serverPingPlayerSample;
   private static Class<?> serverConnection;
   private static Class<?> world;
   private static Class<?> worldServer;
   private static Class<?> blockPosition;
   private static Class<?> iBlockData;
   private static Class<?> vector3F;
   private static Class<?> iChatBaseComponent;
   private static Class<?> chatSerializer;
   private static Class<?> itemStack;
   private static Class<?> gameProfile;
   private static Class<?> propertyMap;
   private static Class<?> property;
   private static Class<?> dataWatcher;
   private static Class<?> dataWatcherObject;
   private static Class<?> dataWatcherSerializer;
   private static Class<?> dataWatcherRegistry;
   private static Class<?> watchableObject;

   public static Class getServerPing() {
      if (serverPing == null) {
         serverPing = getMinecraftClass("ServerPing");
      }

      return serverPing;
   }

   public static Class getServerData() {
      if (serverData == null) {
         serverData = getMinecraftClass("ServerPing$ServerData");
      }

      return serverData;
   }

   public static Class getServerPingPlayerSample() {
      if (serverPingPlayerSample == null) {
         serverPingPlayerSample = getMinecraftClass("ServerPing$ServerPingPlayerSample");
      }

      return serverPingPlayerSample;
   }

   public static Class getEnumDirectionClass() {
      if (enumDirection == null) {
         enumDirection = getMinecraftClass("EnumDirection");
      }

      return enumDirection;
   }

   public static Class getEnumProtocolDirectionClass() {
      return getMinecraftClass(true, "EnumProtocolDirection");
   }

   public static Class getEnumProtocolClass() {
      if (enumProtocol == null) {
         enumProtocol = getMinecraftClass("EnumProtocol");
      }

      return enumProtocol;
   }

   public static Class getEnumGamemodeClass() {
      if (enumGamemode == null) {
         enumGamemode = getMinecraftClass("EnumGamemode", "WorldSettings$EnumGamemode");
      }

      return enumGamemode;
   }

   public static Class getEnumPlayerInfoActionClass() {
      if (enumPlayerInfoAction == null) {
         enumPlayerInfoAction = getMinecraftClass(Integer.TYPE, "PacketPlayOutPlayerInfo$EnumPlayerInfoAction");
      }

      return enumPlayerInfoAction;
   }

   public static Class getEnumTitleAction() {
      if (enumTitleAction == null) {
         enumTitleAction = getMinecraftClass(Integer.TYPE, "PacketPlayOutTitle$EnumTitleAction");
      }

      return enumTitleAction;
   }

   public static Class<?> getNBTTagCompoundClass() {
      if (nbtTagCompound == null) {
         nbtTagCompound = getMinecraftClass("NBTTagCompound");
      }

      return nbtTagCompound;
   }

   public static Class<?> getDataWatcherClass() {
      if (dataWatcher == null) {
         dataWatcher = getMinecraftClass("DataWatcher");
      }

      return dataWatcher;
   }

   public static Class<?> getDataWatcherObjectClass() {
      if (dataWatcherObject == null) {
         dataWatcherObject = getMinecraftClass(Integer.TYPE, "DataWatcherObject");
      }

      return dataWatcherObject;
   }

   public static Class<?> getDataWatcherSerializerClass() {
      if (dataWatcherSerializer == null) {
         dataWatcherSerializer = getMinecraftClass(null, "DataWatcherSerializer");
      }

      return dataWatcherSerializer;
   }

   public static Class<?> getDataWatcherRegistryClass() {
      if (dataWatcherRegistry == null) {
         dataWatcherRegistry = getMinecraftClass(null, "DataWatcherRegistry");
      }

      return dataWatcherRegistry;
   }

   public static Class<?> getWatchableObjectClass() {
      if (watchableObject == null) {
         watchableObject = getMinecraftClass("DataWatcher$Item", "DataWatcher$WatchableObject", "WatchableObject");
      }

      return watchableObject;
   }

   public static Class<?> getBlock() {
      if (block == null) {
         block = getMinecraftClass("Block");
      }

      return block;
   }

   public static Class getBlocks() {
      if (blocks == null) {
         blocks = getMinecraftClass("Blocks");
      }

      return blocks;
   }

   public static Class<?> getChannelClass() {
      if (channel == null) {
         channel = getMinecraftUtilClass("io.netty.channel.Channel");
      }

      return channel;
   }

   public static Class<?> getEntityClass() {
      if (entity == null) {
         entity = getMinecraftClass("Entity");
      }

      return entity;
   }

   public static Class<?> getEntityHumanClass() {
      if (entityHuman == null) {
         entityHuman = getMinecraftClass("EntityHuman");
      }

      return entityHuman;
   }

   public static Class<?> getPlayerInfoDataClass() {
      if (playerInfoData == null) {
         playerInfoData = getMinecraftClass("PacketPlayOutPlayerInfo$PlayerInfoData");
      }

      return playerInfoData;
   }

   public static Class<?> getServerConnectionClass() {
      if (serverConnection == null) {
         serverConnection = getMinecraftClass("ServerConnection");
      }

      return serverConnection;
   }

   public static Class<?> getWorldClass() {
      if (world == null) {
         world = getMinecraftClass("World");
      }

      return world;
   }

   public static Class<?> getWorldServerClass() {
      if (worldServer == null) {
         worldServer = getMinecraftClass("WorldServer");
      }

      return worldServer;
   }

   public static Class getIChatBaseComponent() {
      if (iChatBaseComponent == null) {
         iChatBaseComponent = getMinecraftClass("IChatBaseComponent");
      }

      return iChatBaseComponent;
   }

   public static Class<?> getChatSerializer() {
      if (chatSerializer == null) {
         chatSerializer = getMinecraftClass("IChatBaseComponent$ChatSerializer", "ChatSerializer");
      }

      return chatSerializer;
   }

   public static Class<?> getCraftItemStackClass() {
      if (craftItemStack == null) {
         craftItemStack = getCraftBukkitClass("inventory.CraftItemStack");
      }

      return craftItemStack;
   }

   public static Class<?> getItemStackClass() {
      if (itemStack == null) {
         itemStack = getMinecraftClass("ItemStack");
      }

      return itemStack;
   }

   public static Class<?> getBlockPositionClass() {
      if (blockPosition == null) {
         blockPosition = getMinecraftClass("BlockPosition");
      }

      return blockPosition;
   }

   public static Class<?> getIBlockDataClass() {
      if (iBlockData == null) {
         iBlockData = getMinecraftClass("IBlockData");
      }

      return iBlockData;
   }

   public static Class<?> getVector3FClass() {
      if (vector3F == null) {
         vector3F = getMinecraftClass("Vector3f");
      }

      return vector3F;
   }

   public static Class<?> getGameProfileClass() {
      if (gameProfile == null) {
         gameProfile = getMinecraftUtilClass("com.mojang.authlib.GameProfile");
      }

      return gameProfile;
   }

   public static Class<?> getPropertyMapClass() {
      if (propertyMap == null) {
         propertyMap = getMinecraftUtilClass("com.mojang.authlib.properties.PropertyMap");
      }

      return propertyMap;
   }

   public static Class<?> getPropertyClass() {
      if (property == null) {
         property = getMinecraftUtilClass("com.mojang.authlib.properties.Property");
      }

      return property;
   }

   public static boolean is(Class<?> clazz, Object object) {
      return clazz != null && object != null ? clazz.isAssignableFrom(object.getClass()) : false;
   }

   public static Class<?> getClass(String name) {
      try {
         return MinecraftReflection.class.getClassLoader().loadClass(name);
      } catch (Exception var2) {
         throw new IllegalArgumentException("Cannot find class " + name);
      }
   }

   public static Class<?> getCraftBukkitClass(String... names) {
      return getCraftBukkitClass(false, names);
   }

   public static Class<?> getCraftBukkitClass(Object canNull, String... names) {
      String[] var2 = names;
      int var3 = names.length;
      int var4 = 0;

      while(var4 < var3) {
         String name = var2[var4];

         try {
            return getClass(OBC_PREFIX + name);
         } catch (Exception var7) {
            ++var4;
         }
      }

      if (canNull instanceof Boolean && !(Boolean)canNull) {
         throw new IllegalArgumentException("Cannot find CraftBukkit Class from names " + Arrays.asList(names) + ".");
      } else {
         return canNull != null && canNull.getClass().equals(Class.class) ? (Class)canNull : null;
      }
   }

   public static Class<?> getMinecraftClass(String... names) {
      return getMinecraftClass(false, names);
   }

   public static Class<?> getMinecraftClass(Object canNull, String... names) {
      String[] var2 = names;
      int var3 = names.length;
      int var4 = 0;

      while(var4 < var3) {
         String name = var2[var4];

         try {
            return getClass(NMS_PREFIX + name);
         } catch (Exception var7) {
            ++var4;
         }
      }

      if (canNull instanceof Boolean && !(Boolean)canNull) {
         throw new IllegalArgumentException("Cannot find MinecraftServer Class from names " + Arrays.asList(names) + ".");
      } else {
         return canNull != null && canNull.getClass().equals(Class.class) ? (Class)canNull : null;
      }
   }

   public static Class<?> getMinecraftUtilClass(String name) {
      try {
         return getClass(NMU_PREFIX + name);
      } catch (Exception var2) {
         throw new IllegalArgumentException("Cannot find MinecraftUtil Class from name " + name + ".");
      }
   }

   static {
      try {
         getClass("net.minecraft.util.com.google.common.collect.ImmutableList");
         NMU_PREFIX = "net.minecraft.util.";
      } catch (Exception var1) {
      }

      OBC_PREFIX = "org.bukkit.craftbukkit." + VERSION.getVersion() + ".";
      NMS_PREFIX = OBC_PREFIX.replace("org.bukkit.craftbukkit", "net.minecraft.server");
   }
}
