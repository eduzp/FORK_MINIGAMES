package mc.twilight.caixas.utils;

import mc.twilight.core.libraries.holograms.api.Hologram;
import mc.twilight.core.libraries.holograms.api.HologramLine;
import mc.twilight.caixas.box.action.BoxContent;
import mc.twilight.core.nms.interfaces.entity.ISlime;
import mc.twilight.core.utils.BukkitUtils;
import mc.twilight.core.utils.StringUtils;
import net.md_5.bungee.api.chat.BaseComponent;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.chat.ComponentSerializer;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.BookMeta;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

public class PlayerUtils {
  
  public static void createBook(Player player, List<BoxContent> rewardList) {
    List<List<BoxContent>> list = new ArrayList<>();
    List<BoxContent> current = new ArrayList<>();
    for (int i = 0; i < rewardList.size(); i++) {
      if (current.size() == 9) {
        list.add(current);
        current = new ArrayList<>();
      }
      
      BoxContent reward = rewardList.get(i);
      current.add(reward);
      if (i + 1 == rewardList.size()) {
        list.add(current);
      }
    }
    
    boolean first = true;
    List<String> components = new ArrayList<>();
    for (List<BoxContent> rewards : list) {
      TextComponent start = new TextComponent(first ? "§5§lVOCÊ RECEBEU:\n \n" : "");
      for (BoxContent reward : rewards) {
        String name = StringUtils.formatColors(reward.getName());
        StringBuilder builder = new StringBuilder(StringUtils.stripColors(name.split(" ")[0]));
        builder.append(" (").append(name.split("- ")[1]);
        for (BaseComponent extra : TextComponent.fromLegacyText(" §0▪ " + builder + "\n")) {
          start.addExtra(extra);
        }
      }
      components.add(ComponentSerializer.toString(start));
      first = false;
    }
    
    ItemStack b = new ItemStack(Material.WRITTEN_BOOK);
    BookMeta meta = (BookMeta) b.getItemMeta();
    meta.setAuthor("Maxteer");
    meta.setTitle("Prêmios");
    b.setItemMeta(meta);
    b = BukkitUtils.setNBTList(b, "pages", components);
    BukkitUtils.openBook(player, b);
  }
  
  public static Entity getLookingFor(Player player, List<Hologram> holograms) {
    return getLookingFor(player, holograms, 4);
  }
  
  public static Entity getLookingFor(Player player, List<Hologram> holograms, int distance) {
    List<ISlime> entities = new ArrayList<>();
    holograms.forEach(hologram -> hologram.getLines().stream().filter(line -> line.getSlime() != null).map(HologramLine::getSlime).forEach(entities::add));
    for (Block targetBlock : player.getLineOfSight((HashSet<Byte>) null, distance)) {
      Location blockLocation = targetBlock.getLocation().add(0.5, 0, 0.5);
      for (ISlime slime : entities) {
        if (Math.abs(slime.getEntity().getLocation().getX() - blockLocation.getX()) <= 0.6) {
          if (Math.abs(slime.getEntity().getLocation().getY() - blockLocation.getY()) <= 0.6) {
            if (Math.abs(slime.getEntity().getLocation().getZ() - blockLocation.getZ()) <= 0.6) {
              return slime.getEntity();
            }
          }
        }
      }
    }
    
    return null;
  }
}
