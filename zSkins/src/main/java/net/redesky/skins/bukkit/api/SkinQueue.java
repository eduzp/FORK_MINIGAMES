package net.redesky.skins.bukkit.api;

import java.util.ArrayList;
import java.util.List;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitTask;
import net.redesky.skins.bukkit.Main;

public class SkinQueue {
   private static BukkitTask task;
   private static List<String> queue = new ArrayList();

   public static void addQueue(String name, String id) {
      if (!queue.contains(name + " : " + id)) {
         queue.add(name + " : " + id);
         if (task == null) {
            task = (new BukkitRunnable() {
               public void run() {
                  if (!SkinQueue.queue.isEmpty()) {
                     String[] splitter = ((String)SkinQueue.queue.get(0)).split(" : ");
                     SkinQueue.queue.remove(0);
                     SkinMethods.tryUpdateOriginalSkin(splitter[0], splitter[1]);
                  } else {
                     this.cancel();
                     SkinQueue.task = null;
                  }

               }
            }).runTaskTimer(Main.getInstance(), 0L, 200L);
         }

      }
   }
}
