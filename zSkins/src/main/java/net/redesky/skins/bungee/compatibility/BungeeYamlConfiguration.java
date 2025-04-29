package net.redesky.skins.bungee.compatibility;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.List;
import java.util.Set;
import java.util.logging.Level;
import java.util.stream.Collectors;
import net.md_5.bungee.config.Configuration;
import net.redesky.skins.Core;
import net.redesky.skins.plugin.compatibility.YamlConfiguration;

public class BungeeYamlConfiguration extends YamlConfiguration {
   private Configuration config;

   public BungeeYamlConfiguration(File file) throws IOException {
      super(file);
      this.config = net.md_5.bungee.config.YamlConfiguration.getProvider(net.md_5.bungee.config.YamlConfiguration.class).load(new InputStreamReader(new FileInputStream(file), "UTF-8"));
   }

   public boolean createSection(String path) {
      return this.set(path, new HashMap());
   }

   public boolean set(String path, Object obj) {
      this.config.set(path, obj);
      return this.save();
   }

   public boolean contains(String path) {
      return this.config.contains(path);
   }

   public Object get(String path) {
      return this.config.get(path);
   }

   public int getInt(String path) {
      return this.getInt(path, 0);
   }

   public int getInt(String path, int def) {
      return this.config.getInt(path, def);
   }

   public double getDouble(String path) {
      return this.getDouble(path, 0.0D);
   }

   public double getDouble(String path, double def) {
      return this.config.getDouble(path, 0.0D);
   }

   public String getString(String path) {
      return this.config.getString(path);
   }

   public boolean getBoolean(String path) {
      return this.config.getBoolean(path);
   }

   public List<String> getStringList(String path) {
      return this.config.getStringList(path);
   }

   public Set<String> getKeys(boolean flag) {
      return (Set)this.config.getKeys().stream().collect(Collectors.toSet());
   }

   public boolean reload() {
      try {
         this.config = net.md_5.bungee.config.YamlConfiguration.getProvider(net.md_5.bungee.config.YamlConfiguration.class).load(new InputStreamReader(new FileInputStream(this.file), "UTF-8"));
         return true;
      } catch (IOException var2) {
         Core.LOGGER.log(Level.SEVERE, "Unexpected error ocurred reloading file " + this.file.getName() + ": ", var2);
         return false;
      }
   }

   public boolean save() {
      try {
         net.md_5.bungee.config.YamlConfiguration.getProvider(net.md_5.bungee.config.YamlConfiguration.class).save(this.config, this.file);
         return true;
      } catch (IOException var2) {
         Core.LOGGER.log(Level.SEVERE, "Unexpected error ocurred saving file " + this.file.getName() + ": ", var2);
         return false;
      }
   }
}
