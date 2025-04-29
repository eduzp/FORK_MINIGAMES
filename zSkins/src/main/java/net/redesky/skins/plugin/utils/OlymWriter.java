package net.redesky.skins.plugin.utils;

import com.sun.xml.internal.ws.spi.db.BindingContextFactory;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.nio.charset.StandardCharsets;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.logging.Level;

public class OlymWriter {
   private File file;
   private String header;
   private Map<String, Object> keys;

   public OlymWriter(File file) {
      this(file, "");
   }

   public OlymWriter(File file, String header) {
      this.keys = new LinkedHashMap();
      this.file = file;
      this.header = header;
   }

   public void write() {
      try {
         Writer fw = new OutputStreamWriter(new FileOutputStream(this.file), StandardCharsets.UTF_8);
         fw.append(this.toSaveString());
         fw.close();
      } catch (IOException var2) {
         var2.printStackTrace();
      }

   }

   public void set(String path, OlymWriter.YamlEntry entry) {
      String[] splitter = path.split("\\.");
      Map<String, Object> currentMap = this.keys;

      for(int slot = 0; slot < splitter.length; ++slot) {
         String p = splitter[slot];
         if (slot + 1 == splitter.length) {
            currentMap.put(p, entry);
         } else if (currentMap.containsKey(p)) {
            currentMap = (Map)currentMap.get(p);
         } else {
            currentMap.put(p, new LinkedHashMap());
            currentMap = (Map)currentMap.get(p);
         }
      }

   }

   public String toSaveString() {
      StringBuilder join = new StringBuilder();
      if (!this.header.isEmpty()) {
         String[] var2 = this.header.split("\n");
         int var3 = var2.length;

         for(int var4 = 0; var4 < var3; ++var4) {
            String split = var2[var4];
            String[] var6 = StringUtils.split(split, 100, false);
            int var7 = var6.length;

            for(int var8 = 0; var8 < var7; ++var8) {
               String annotation = var6[var8];
               join.append("# " + annotation + "\n");
            }
         }
      }

      Iterator var10 = this.keys.entrySet().iterator();

      while(var10.hasNext()) {
         Entry<String, Object> entry = (Entry)var10.next();
         join.append(this.toSaveString((String)entry.getKey(), entry.getValue(), 0));
      }

      return join.toString();
   }

   private String toSaveString(String key, Object object, int spaces) {
      StringBuilder join = new StringBuilder();
      String[] var6;
      if (object instanceof OlymWriter.YamlEntry) {
         OlymWriter.YamlEntry ye = (OlymWriter.YamlEntry)object;
         if (!ye.getAnnotation().isEmpty()) {
            var6 = ye.getAnnotation().split("\n");
            int var7 = var6.length;

            for(int var8 = 0; var8 < var7; ++var8) {
               String split = var6[var8];
               String[] var10 = StringUtils.split(split, 100, false);
               int var11 = var10.length;

               for(int var12 = 0; var12 < var11; ++var12) {
                  String annotation = var10[var12];
                  join.append(this.repeat(spaces) + "# " + annotation + "\n");
               }
            }
         }

         object = ye.getValue();
      }

      join.append(this.repeat(spaces) + key + ":");
      if (object instanceof String) {
         join.append(" '" + object.toString().replace("'", "''") + "'\n");
      } else if (object instanceof Integer) {
         join.append(" " + object + "\n");
      } else if (object instanceof Double) {
         join.append(" " + object + "\n");
      } else if (object instanceof Long) {
         join.append(" " + object + "\n");
      } else if (object instanceof Boolean) {
         join.append(" " + object + "\n");
      } else {
         Iterator var15;
         if (object instanceof List) {
            join.append("\n");
            var15 = ((List)object).iterator();

            while(var15.hasNext()) {
               Object obj = var15.next();
               if (obj instanceof Integer) {
                  join.append(this.repeat(spaces) + "- " + obj.toString() + "\n");
               } else {
                  join.append(this.repeat(spaces) + "- '" + obj.toString().replace("'", "''") + "'\n");
               }
            }
         } else if (object instanceof Map) {
            join.append("\n");
            var15 = ((Map)object).entrySet().iterator();

            while(var15.hasNext()) {
               Entry<String, Object> entry = (Entry)var15.next();
               join.append(this.toSaveString((String)entry.getKey(), entry.getValue(), spaces + 1));
            }
         } else if (object instanceof InputStream) {
            join.append("\n");

            try {
               BufferedReader reader = new BufferedReader(new InputStreamReader((InputStream)object, "UTF-8"));
               var6 = null;

               String line;
               while((line = reader.readLine()) != null) {
                  join.append(this.repeat(spaces + 1) + line + "\n");
               }
            } catch (IOException var14) {
               BindingContextFactory.LOGGER.log(Level.SEVERE, "Cant read input stream from \"" + key + "\":", var14);
            }
         }
      }

      return join.toString();
   }

   private String repeat(int spaces) {
      StringBuilder sb = new StringBuilder();

      for(int i = 0; i < spaces; ++i) {
         sb.append(" ");
      }

      return sb.toString();
   }

   @Retention(RetentionPolicy.RUNTIME)
   @Target({ElementType.FIELD})
   public @interface YamlEntryInfo {
      String annotation() default "";
   }

   public static class YamlEntry {
      private String annotation;
      private Object value;

      public YamlEntry(Object[] array) {
         this.annotation = (String)array[0];
         this.value = array[1];
      }

      public String getAnnotation() {
         return this.annotation;
      }

      public Object getValue() {
         return this.value;
      }
   }
}
