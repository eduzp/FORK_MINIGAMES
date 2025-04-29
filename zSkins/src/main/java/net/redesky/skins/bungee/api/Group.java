package net.redesky.skins.bungee.api;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.config.Configuration;
import net.redesky.skins.bungee.Main;

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
      Configuration config = Main.getInstance().getConfig();
      Iterator var1 = config.getStringList("groups").iterator();

      while(var1.hasNext()) {
         String group = (String)var1.next();
         groups.add(new Group(group.split(" > ")[0], Integer.parseInt(group.split(" > ")[1])));
      }

   }

   public static int getLimit(ProxiedPlayer player) {
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
