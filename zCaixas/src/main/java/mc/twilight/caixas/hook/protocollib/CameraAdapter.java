package mc.twilight.caixas.hook.protocollib;

import com.comphenix.protocol.PacketType;
import com.comphenix.protocol.events.PacketAdapter;
import com.comphenix.protocol.events.PacketEvent;
import mc.twilight.caixas.Main;
import mc.twilight.caixas.nms.NMS;

public class CameraAdapter extends PacketAdapter {
  
  public CameraAdapter() {
    super(params().plugin(Main.getInstance()).types(PacketType.Play.Server.CAMERA));
  }
  
  @Override
  public void onPacketSending(PacketEvent evt) {
    evt.getPacket().getIntegers().write(0, NMS.BLOCK_CAMERA.getOrDefault(evt.getPlayer().getName(), evt.getPacket().getIntegers().read(0)));
  }
}
