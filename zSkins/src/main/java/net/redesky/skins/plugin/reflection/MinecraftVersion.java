package net.redesky.skins.plugin.reflection;

import com.google.common.base.Objects;
import org.bukkit.Bukkit;
import org.bukkit.Server;

public class MinecraftVersion {
   private int major;
   private int minor;
   private int build;
   private int compareId;
   private static MinecraftVersion currentVersion;

   public MinecraftVersion(Server server) {
      this(extractVersion(server));
   }

   public MinecraftVersion(String version) {
      int[] numbers = new int[3];

      try {
         numbers = this.parseVersion(version);
      } catch (NumberFormatException var4) {
         throw var4;
      }

      this.major = numbers[0];
      this.minor = numbers[1];
      this.build = numbers[2];
      this.compareId = Integer.parseInt(String.valueOf(this.major + "" + this.minor + "" + this.build));
   }

   public MinecraftVersion(int major, int minor, int build) {
      this.major = major;
      this.minor = minor;
      this.build = build;
      this.compareId = Integer.parseInt(String.valueOf(major + "" + minor + "" + build));
   }

   public int getMajor() {
      return this.major;
   }

   public int getMinor() {
      return this.minor;
   }

   public int getBuild() {
      return this.build;
   }

   public int getCompareId() {
      return this.compareId;
   }

   public boolean lowerThan(MinecraftVersion version) {
      return this.compareId < version.getCompareId();
   }

   public boolean newerThan(MinecraftVersion version) {
      return this.compareId > version.getCompareId();
   }

   public boolean inRange(MinecraftVersion latest, MinecraftVersion olded) {
      return this.compareId <= latest.getCompareId() && this.compareId >= olded.getCompareId();
   }

   private int[] parseVersion(String version) {
      String[] elements = version.split("\\.");
      int[] numbers = new int[3];
      if (elements.length > 1 && version.split("R").length >= 1) {
         for(int i = 0; i < 2; ++i) {
            numbers[i] = Integer.parseInt(elements[i]);
         }

         numbers[2] = Integer.parseInt(version.split("R")[1]);
         return numbers;
      } else {
         throw new IllegalStateException("Corrupt MC Server version: " + version);
      }
   }

   public String getVersion() {
      return String.format("v%s_%s_R%s", this.major, this.minor, this.build);
   }

   public boolean equals(Object obj) {
      if (obj != null && obj instanceof MinecraftVersion) {
         if (obj == this) {
            return true;
         } else {
            MinecraftVersion other = (MinecraftVersion)obj;
            return this.getMajor() == other.getMajor() && this.getMinor() == other.getMinor() && this.getBuild() == other.getBuild();
         }
      } else {
         return false;
      }
   }

   public int hashCode() {
      return Objects.hashCode(new Object[]{this.getMajor(), this.getMinor(), this.getBuild()});
   }

   public String toString() {
      return String.format("%s", this.getVersion());
   }

   public static String extractVersion(Server server) {
      return extractVersion(server.getClass().getPackage().getName().split("\\.")[3]);
   }

   public static String extractVersion(String version) {
      return version.replace('_', '.').replace("v", "");
   }

   public static MinecraftVersion fromServer(Server server) {
      return new MinecraftVersion(extractVersion(server));
   }

   public static MinecraftVersion getCurrentVersion() {
      if (currentVersion == null) {
         currentVersion = fromServer(Bukkit.getServer());
      }

      return currentVersion;
   }
}
