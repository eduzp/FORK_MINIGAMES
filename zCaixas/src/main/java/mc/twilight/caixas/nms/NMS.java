package mc.twilight.caixas.nms;

import mc.twilight.caixas.nms.entity.EntityCart;
import mc.twilight.caixas.nms.entity.EntityStand;
import mc.twilight.core.nms.interfaces.entity.IArmorStand;
import mc.twilight.core.reflection.Accessors;
import mc.twilight.core.reflection.acessors.FieldAccessor;
import net.minecraft.server.v1_8_R3.*;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftEntity;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftPlayer;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.CreatureSpawnEvent;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@SuppressWarnings({"rawtypes", "unchecked"})
public class NMS {
  
  public static final Map<String, Integer> BLOCK_CAMERA = new HashMap<>();
  public static final Map<Integer, String> ATTACHED_ENTITY = new HashMap<>();
  private static final FieldAccessor<Map> CLASS_TO_ID, CLASS_TO_NAME;
  
  static {
    CLASS_TO_ID = Accessors.getField(EntityTypes.class, "f", Map.class);
    CLASS_TO_NAME = Accessors.getField(EntityTypes.class, "d", Map.class);
    
    CLASS_TO_ID.get(null).put(EntityStand.class, 30);
    CLASS_TO_NAME.get(null).put(EntityStand.class, "MLC-ArmorStand");
    CLASS_TO_ID.get(null).put(EntityCart.class, 51);
    CLASS_TO_NAME.get(null).put(EntityCart.class, "MLC-Cart");
  }
  
  public static IArmorStand createAttachedArmorStand(String owner, Location location, String name) {
    EntityStand stand = new EntityStand(owner, location);
    ATTACHED_ENTITY.put(stand.getId(), owner);
    stand.setPosition(location.getX(), location.getY(), location.getZ());
    stand.setName(name);
    
    if (stand.world.addEntity(stand, CreatureSpawnEvent.SpawnReason.CUSTOM)) {
      return stand;
    }
    
    ATTACHED_ENTITY.remove(stand.getId());
    return null;
  }
  
  public static Entity createAttachedCart(String owner, Location location) {
    EntityCart cart = new EntityCart(owner, location);
    ATTACHED_ENTITY.put(cart.getId(), owner);
    
    cart.spawnIn(cart.world);
    cart.setPosition(location.getX(), location.getY(), location.getZ());
    if (cart.world.addEntity(cart, CreatureSpawnEvent.SpawnReason.CUSTOM)) {
      return cart.getBukkitEntity();
    }
    
    ATTACHED_ENTITY.remove(cart.getId());
    return null;
  }
  
  public static void sendFakeSpectator(Player player, Entity entity) {
    player.setGameMode(entity == null ? GameMode.ADVENTURE : GameMode.SPECTATOR);
    
    EntityPlayer ep = ((CraftPlayer) player).getHandle();
    if (entity != null) {
      BLOCK_CAMERA.put(player.getName(), entity.getEntityId());
      PacketPlayOutPlayerInfo packet = new PacketPlayOutPlayerInfo(PacketPlayOutPlayerInfo.EnumPlayerInfoAction.UPDATE_GAME_MODE, ep);
      FieldAccessor<Object> accessor = Accessors.getField(packet.getClass(), "b");
      List<PacketPlayOutPlayerInfo.PlayerInfoData> infoList = new ArrayList<>();
      infoList.add(packet.new PlayerInfoData(ep.getProfile(), ep.ping, WorldSettings.EnumGamemode.CREATIVE, ep.listName));
      accessor.set(packet, infoList);
      
      ep.playerConnection.sendPacket(packet);
    } else {
      BLOCK_CAMERA.remove(player.getName());
    }
    
    ep.playerConnection.sendPacket(new PacketPlayOutCamera(entity == null ? ep : ((CraftEntity) entity).getHandle()));
  }
}
