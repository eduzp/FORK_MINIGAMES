package mc.twilight.clans.listeners;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import org.bukkit.entity.Player;

public class ChatHandler {
  public static final ChatHandler CREATING = new ChatHandler();
  
  public static final ChatHandler INVITE = new ChatHandler();
  
  private final Map<UUID, String[]> creating = (Map)new HashMap<>();
  
  public void setCreating(Player player) {
    this.creating.put(player.getUniqueId(), new String[2]);
  }
  
  public void clearCreating(Player player) {
    this.creating.remove(player.getUniqueId());
  }
  
  public boolean isCreating(Player player) {
    return this.creating.containsKey(player.getUniqueId());
  }
  
  public String[] getCreating(Player player) {
    return this.creating.get(player.getUniqueId());
  }
}
