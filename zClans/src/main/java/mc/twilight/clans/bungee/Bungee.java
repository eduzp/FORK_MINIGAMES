package mc.twilight.clans.bungee;

import com.google.common.io.ByteArrayDataInput;
import com.google.common.io.ByteArrayDataOutput;
import com.google.common.io.ByteStreams;
import net.md_5.bungee.api.config.ServerInfo;
import net.md_5.bungee.api.connection.Server;
import net.md_5.bungee.api.event.PluginMessageEvent;
import net.md_5.bungee.api.plugin.Listener;
import net.md_5.bungee.api.plugin.Plugin;
import net.md_5.bungee.event.EventHandler;

public class Bungee extends Plugin implements Listener {
  public void onEnable() {
    getProxy().registerChannel("zClans");
    getProxy().getPluginManager().registerListener(this, this);
    getLogger().info("O plugin foi ativado.");
  }
  
  @EventHandler
  public void onPluginMessage(PluginMessageEvent evt) {
    if (evt.getTag().equals("zClans") && 
      evt.getSender() instanceof Server && evt.getReceiver() instanceof net.md_5.bungee.api.connection.ProxiedPlayer) {
      Server server = (Server)evt.getSender();
      ByteArrayDataInput in = ByteStreams.newDataInput(evt.getData());
      String subChannel = in.readUTF();
      String tag = in.readUTF();
      ByteArrayDataOutput out = ByteStreams.newDataOutput();
      out.writeUTF(subChannel);
      out.writeUTF(tag);
      if (subChannel.equals("Broadcast")) {
        out.writeUTF(in.readUTF());
        out.writeUTF(in.readUTF());
      } 
      for (ServerInfo info : getProxy().getServers().values()) {
        if (!info.getName().equals(server.getInfo().getName()))
          info.sendData("zClans", out.toByteArray()); 
      } 
    } 
  }
}
