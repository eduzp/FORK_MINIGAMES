package net.redesky.skins.bungee.api;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;
import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.scheduler.ScheduledTask;
import net.redesky.skins.bungee.Main;

public class SkinQueue {
   private static ScheduledTask task;
   private static List<String> queue = new ArrayList();

   public static void addQueue(String name, String id) {
      if (!queue.contains(name + " : " + id)) {
         queue.add(name + " : " + id);
         if (task == null) {
            task = ProxyServer.getInstance().getScheduler().schedule(Main.getInstance(), new Runnable() {
               public void run() {
                  if (!SkinQueue.queue.isEmpty()) {
                     String[] splitter = ((String)SkinQueue.queue.get(0)).split(" : ");
                     SkinQueue.queue.remove(0);
                     SkinMethods.tryUpdateOriginalSkin(splitter[0], splitter[1]);
                  } else {
                     SkinQueue.task.cancel();
                     SkinQueue.task = null;
                  }

               }
            }, 0L, 10L, TimeUnit.SECONDS);
         }

      }
   }
}
