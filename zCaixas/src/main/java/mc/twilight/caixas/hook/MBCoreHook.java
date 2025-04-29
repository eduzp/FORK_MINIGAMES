package mc.twilight.caixas.hook;

import com.comphenix.protocol.ProtocolLibrary;
import mc.twilight.caixas.box.Box;
import mc.twilight.caixas.box.action.BoxContent;
import mc.twilight.caixas.cosmetics.Cosmetic;
import mc.twilight.caixas.hook.protocollib.CameraAdapter;
import mc.twilight.caixas.hook.protocollib.EntityAdapter;
import mc.twilight.caixas.hook.protocollib.HologramAdapter;

public class MBCoreHook {
  
  public static void setupHook() {
    ProtocolLibrary.getProtocolManager().addPacketListener(new HologramAdapter());
    ProtocolLibrary.getProtocolManager().addPacketListener(new EntityAdapter());
    ProtocolLibrary.getProtocolManager().addPacketListener(new CameraAdapter());
    
    Box.setupBoxes();
    BoxContent.setupRewards();
    
    Cosmetic.setupCosmetics();
  }
}
