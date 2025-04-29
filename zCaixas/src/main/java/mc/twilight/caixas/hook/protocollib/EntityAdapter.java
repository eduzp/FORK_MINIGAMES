package mc.twilight.caixas.hook.protocollib;

import com.comphenix.protocol.PacketType;
import com.comphenix.protocol.events.PacketAdapter;
import com.comphenix.protocol.events.PacketEvent;
import mc.twilight.caixas.Main;

import static mc.twilight.caixas.nms.NMS.ATTACHED_ENTITY;

public class EntityAdapter extends PacketAdapter {
  
  public EntityAdapter() {
    super(params().plugin(Main.getInstance()).types(PacketType.Play.Server.SPAWN_ENTITY, PacketType.Play.Server.SPAWN_ENTITY_LIVING));
  }
  
  @Override
  public void onPacketSending(PacketEvent evt) {
    String owner = ATTACHED_ENTITY.getOrDefault(evt.getPacket().getIntegers().read(0), null);
    if (owner == null || owner.equals(evt.getPlayer().getName())) {
      return;
    }
    
    evt.setCancelled(true);
  }
}