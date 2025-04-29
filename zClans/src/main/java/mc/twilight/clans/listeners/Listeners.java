package mc.twilight.clans.listeners;

import mc.twilight.clans.Main;
import mc.twilight.clans.api.ClanAPI;
import mc.twilight.clans.clan.Clan;
import mc.twilight.clans.listeners.objects.ClanCreating;
import mc.twilight.core.database.data.DataContainer;
import mc.twilight.core.player.Profile;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.plugin.Plugin;

public class Listeners implements Listener {
  public static void setupListeners() {
    Bukkit.getPluginManager().registerEvents(new Listeners(), (Plugin)Main.getInstance());
    Bukkit.getPluginManager().registerEvents(new AsyncChatListeners(), (Plugin)Main.getInstance());
  }
  
  @EventHandler
  public void onPlayerJoin(PlayerJoinEvent evt) {
    Player player = evt.getPlayer();
    Profile profile = Profile.getProfile(player.getName());
    if (profile != null) {
      DataContainer container = profile.getDataContainer("zCoreProfile", "clan");
      if (ClanAPI.getClanByPlayer(player) != null) {
        container.set(((ClanAPI.getClanByPlayer(player)).tagPermissionPlus ? "§6" : ((ClanAPI.getClanByPlayer(player)).tagPermission ? "§7" : "§8")) + "[" + ClanAPI.getClanByPlayer(player).getTag() + "]");
      } else {
        container.set("");
      } 
    } 
  }
  
  @EventHandler
  public void onPlayerQuit(PlayerQuitEvent evt) {
    ChatHandler.INVITE.clearCreating(evt.getPlayer());
  }
  
  @EventHandler(priority = EventPriority.HIGHEST)
  public void onAsyncPlayerChatC(AsyncPlayerChatEvent evt) {
    Player player = evt.getPlayer();
    if (ChatHandler.INVITE.isCreating(player)) {
      evt.setCancelled(true);
      if (evt.getMessage().equalsIgnoreCase("cancelar")) {
        ChatHandler.INVITE.clearCreating(player);
        player.sendMessage("§cOperação cancelada.");
        return;
      } 
      String name = evt.getMessage();
      Player target = Bukkit.getPlayerExact(name);
      if (target == null) {
        player.sendMessage("§cEste usuário não está online.");
        player.sendMessage(" \n§aQuem você deseja convidar?\n§7Digite o nome do usuário ou §lCANCELAR §7para cancelar.\n ");
        return;
      } 
      if (Clan.getClan(target) != null) {
        player.sendMessage("§cEste usuário já possui um clan.");
        player.sendMessage(" \n§aQuem você deseja convidar?\n§7Digite o nome do usuário ou §lCANCELAR §7para cancelar.\n ");
        return;
      } 
      ChatHandler.INVITE.clearCreating(player);
      player.performCommand("clan convidar " + name);
    } 
  }
  
  @EventHandler(priority = EventPriority.MONITOR)
  public void onPlayerCommandPreprocess(PlayerCommandPreprocessEvent evt) {
    if (ChatHandler.INVITE.isCreating(evt.getPlayer())) {
      evt.setCancelled(true);
      evt.getPlayer().sendMessage(" \n§aQuem você deseja convidar?\n§7Digite o nome do usuário ou §lCANCELAR §7para cancelar.\n ");
    } 
  }
  
  @EventHandler
  public void onPlayerQuitt(PlayerQuitEvent evt) {
    ChatHandler.CREATING.clearCreating(evt.getPlayer());
    ClanCreating.deleteClan(evt.getPlayer().getName());
  }
}
