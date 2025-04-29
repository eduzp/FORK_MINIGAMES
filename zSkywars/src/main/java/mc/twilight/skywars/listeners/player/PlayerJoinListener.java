package mc.twilight.skywars.listeners.player;

import mc.twilight.core.Core;
import mc.twilight.core.nms.NMS;
import mc.twilight.core.player.Profile;
import mc.twilight.core.player.hotbar.Hotbar;
import mc.twilight.core.player.role.Role;
import mc.twilight.skywars.Language;
import mc.twilight.skywars.Main;
import mc.twilight.skywars.hook.SWCoreHook;
import mc.twilight.skywars.hook.mysteryboxes.MysteryBoxesHook;
import mc.twilight.skywars.utils.tagger.TagUtils;
import mc.twilight.core.titles.TitleManager;
import mc.twilight.core.utils.enums.EnumSound;
import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.chat.BaseComponent;
import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.HoverEvent;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

public class PlayerJoinListener implements Listener {
  
  @EventHandler
  public void onPlayerJoin(PlayerJoinEvent evt) {
    evt.setJoinMessage(null);
    
    Player player = evt.getPlayer();
    TagUtils.sendTeams(player);
    
    Profile profile = Profile.getProfile(player.getName());
    SWCoreHook.reloadScoreboard(profile);
    profile.setHotbar(Hotbar.getHotbarById("lobby"));
    profile.refresh();
    
    Bukkit.getScheduler().runTaskLaterAsynchronously(Main.getInstance(), () -> {
      TagUtils.setTag(evt.getPlayer());
      
      if (Role.getPlayerRole(player).isBroadcast()) {
        String broadcast = Language.lobby$broadcast
            .replace("{player}", Role.getPrefixed(player.getName()));
        Profile.listProfiles().forEach(pf -> {
          if (!pf.playingGame()) {
            Player players = pf.getPlayer();
            if (players != null) {
              players.sendMessage(broadcast);
            }
          }
        });
      }
    }, 5);
    
    Bukkit.getScheduler().runTaskLaterAsynchronously(Core.getInstance(), () -> {
      TitleManager.joinLobby(profile);
    }, 10);
    
    NMS.sendTitle(player, "", "", 0, 1, 0);
    if (Language.lobby$tab$enabled) {
      NMS.sendTabHeaderFooter(player, Language.lobby$tab$header, Language.lobby$tab$footer);
    }
    
    if (player.hasPermission("zskywars.cmd.skywars")) {
      if (Main.kMysteryBox) {
        if (MysteryBoxesHook.isUnsynced()) {
          TextComponent component = new TextComponent("");
          for (BaseComponent components : TextComponent
              .fromLegacyText(
                  " \n §6§lMYSTERY BOXES\n \n §7O zSkyWars aparentemente não está sincronizado com o kMysteryBox, para prosseguir basta clicar ")) {
            component.addExtra(components);
          }
          TextComponent click = new TextComponent("AQUI");
          click.setColor(ChatColor.GREEN);
          click.setBold(true);
          click.setClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, "/sw mb sync"));
          click.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, TextComponent
              .fromLegacyText("§7Clique aqui para sincronizar o zSkyWars com o kMysteryBoxes.")));
          component.addExtra(click);
          for (BaseComponent components : TextComponent.fromLegacyText("§7.\n ")) {
            component.addExtra(components);
          }
          
          player.spigot().sendMessage(component);
          EnumSound.ORB_PICKUP.play(player, 1.0F, 1.0F);
        }
      }
    }
  }
}
