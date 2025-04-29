package net.redesky.skins.bukkit.api;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import net.redesky.skins.bukkit.Main;

public class Group {
   private String permission;
   private int skinsLimit;
   private static List<Group> groups = new ArrayList();

   public Group(String permission, int skinsLimit) {
      this.permission = permission;
      this.skinsLimit = skinsLimit;
   }

   public String getPermission() {
      return this.permission;
   }

   public int getSkinsLimit() {
      return this.skinsLimit;
   }

   public static void makeGroups() {
      FileConfiguration config = Main.getInstance().getConfig();
      Iterator var1 = config.getStringList("groups").iterator();

      while(var1.hasNext()) {
         String group = (String)var1.next();
         groups.add(new Group(group.split(" > ")[0], Integer.parseInt(group.split(" > ")[1])));
      }

   }

   public static int getLimit(Player player) {
      Iterator var1 = groups.iterator();

      Group group;
      do {
         if (!var1.hasNext()) {
            return 1;
         }

         group = (Group)var1.next();
      } while(!player.hasPermission(group.getPermission()));

      return group.getSkinsLimit();
   }
}
