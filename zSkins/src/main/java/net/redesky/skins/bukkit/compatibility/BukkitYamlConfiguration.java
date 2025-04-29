package net.redesky.skins.bukkit.compatibility;

import com.sun.xml.internal.ws.spi.db.BindingContextFactory;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.List;
import java.util.Set;
import java.util.logging.Level;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.craftbukkit.libs.jline.internal.InputStreamReader;
import net.redesky.skins.plugin.compatibility.YamlConfiguration;

public class BukkitYamlConfiguration extends YamlConfiguration {
   private FileConfiguration config;

   public BukkitYamlConfiguration(File file) throws IOException {
      super(file);
      this.config = org.bukkit.configuration.file.YamlConfiguration.loadConfiguration(new InputStreamReader(new FileInputStream(file), "UTF-8"));
   }

   public boolean createSection(String path) {
      this.config.createSection(path);
      return this.save();
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
      return this.config.getDouble(path, def);
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
      return this.getKeys(flag);
   }

   public boolean reload() {
      try {
         this.config = org.bukkit.configuration.file.YamlConfiguration.loadConfiguration(new InputStreamReader(new FileInputStream(this.file), "UTF-8"));
         return true;
      } catch (IOException var2) {
         BindingContextFactory.LOGGER.log(Level.SEVERE, "Unexpected error ocurred reloading file " + this.file.getName() + ": ", var2);
         return false;
      }
   }

   public boolean save() {
      try {
         this.config.save(this.file);
         return true;
      } catch (IOException var2) {
         BindingContextFactory.LOGGER.log(Level.SEVERE, "Unexpected error ocurred saving file " + this.file.getName() + ": ", var2);
         return false;
      }
   }
}
