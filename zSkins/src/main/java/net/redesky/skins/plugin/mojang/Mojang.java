package net.redesky.skins.plugin.mojang;

import com.google.common.base.Charsets;
import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.TimeUnit;
import net.redesky.skins.plugin.mojang.api.InvalidMojangException;
import net.redesky.skins.plugin.mojang.api.MineToolsAPI;
import net.redesky.skins.plugin.mojang.api.MojangAPI;

public abstract class Mojang {
   private static final List<Mojang> MOJANGAPIS = new ArrayList();
   private static final Cache<String, String> CACHED_UUID;
   private static final Cache<String, String> CACHED_PROPERTY;

   public abstract String fastId(String var1);

   public abstract String fastSkinProperty(String var1);

   public abstract boolean getResponse();

   public static String getUUID(String name) throws InvalidMojangException {
      String id = (String)CACHED_UUID.getIfPresent(name);
      if (id != null) {
         return id;
      } else {
         Iterator var2 = MOJANGAPIS.iterator();

         Mojang api;
         do {
            if (!var2.hasNext()) {
               throw new InvalidMojangException("No one api can get UUID from " + name);
            }

            api = (Mojang)var2.next();
            id = api.fastId(name);
         } while(!api.getResponse());

         if (id != null) {
            CACHED_UUID.put(name, id);
         }

         return id;
      }
   }

   public static String getSkinProperty(String id) throws InvalidMojangException {
      String property = (String)CACHED_PROPERTY.getIfPresent(id);
      if (property != null) {
         return property;
      } else {
         Iterator var2 = MOJANGAPIS.iterator();

         Mojang api;
         do {
            if (!var2.hasNext()) {
               throw new InvalidMojangException("No one api can get Skin Properties from " + id);
            }

            api = (Mojang)var2.next();
            property = api.fastSkinProperty(id);
         } while(!api.getResponse());

         if (property != null) {
            CACHED_PROPERTY.put(id, property);
         }

         return property;
      }
   }

   public static UUID getOfflineUUID(String name) {
      return UUID.nameUUIDFromBytes((new String("OfflinePlayer:" + name)).getBytes(Charsets.UTF_8));
   }

   public static String parseUUID(String withoutDashes) {
      return withoutDashes.substring(0, 8) + '-' + withoutDashes.substring(8, 12) + '-' + withoutDashes.substring(12, 16) + '-' + withoutDashes.substring(16, 20) + '-' + withoutDashes.substring(20, 32);
   }

   static {
      MOJANGAPIS.add(new MojangAPI());
      MOJANGAPIS.add(new MineToolsAPI());
      CACHED_UUID = CacheBuilder.newBuilder().expireAfterWrite(2L, TimeUnit.HOURS).build();
      CACHED_PROPERTY = CacheBuilder.newBuilder().expireAfterWrite(2L, TimeUnit.HOURS).build();
   }
}
