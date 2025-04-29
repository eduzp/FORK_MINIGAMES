package mc.twilight.core.listeners;

import com.google.common.io.ByteArrayDataInput;
import com.google.common.io.ByteStreams;
import mc.twilight.core.menus.MenuSkins;
import mc.twilight.core.bukkit.BukkitParty;
import mc.twilight.core.bukkit.BukkitPartyManager;
import mc.twilight.core.nms.NMS;
import mc.twilight.core.party.PartyPlayer;
import mc.twilight.core.player.Profile;
import mc.twilight.core.player.fake.FakeManager;
import mc.twilight.core.utils.enums.EnumSound;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import static mc.twilight.core.party.PartyRole.MEMBER;

public class PluginMessageListener implements org.bukkit.plugin.messaging.PluginMessageListener {

  @Override
  public void onPluginMessageReceived(String channel, Player receiver, byte[] data) {
    if (channel.equals("zCore")) {
      ByteArrayDataInput in = ByteStreams.newDataInput(data);
      String subChannel = in.readUTF();

      switch (subChannel) {
        case "FAKE": {
          Player player = Bukkit.getPlayerExact(in.readUTF());
          if (player != null) {
            String fakeName = in.readUTF();
            String roleName = in.readUTF();
            String skin = in.readUTF();
            FakeManager.applyFake(player, fakeName, roleName, skin);
            NMS.refreshPlayer(player);
          }
          break;
        }

        case "FAKE_BOOK": {
          Player player = Bukkit.getPlayerExact(in.readUTF());
          if (player != null) {
            try {
              String sound = in.readUTF();
              EnumSound.valueOf(sound).play(player, 1.0F, sound.contains("VILL") ? 1.0F : 2.0F);
            } catch (Exception ignore) {
            }
            FakeManager.sendRole(player);
          }
          break;
        }

        case "SEND_PARTY": {
          Player player = Bukkit.getPlayerExact(in.readUTF());
          Player leader = Bukkit.getPlayerExact(in.readUTF());
          if (player != null && leader != null) {
            Profile profile = Profile.getProfile(player.getName());
            Profile pLeader = Profile.getProfile(leader.getName());
            if (pLeader.getGame() == null) return;

            if (profile.getGame() != null) {
              profile.getGame().leave(profile, null);
            }
            pLeader.getGame().join(profile);
          }
          break;
        }

        case "SKIN_MENU": {
          Player player = Bukkit.getPlayerExact(in.readUTF());
          if (player != null) {
            new MenuSkins(Profile.getProfile(player.getName()));
          }
          break;
        }

        case "UPDATE_SKIN": {
          Player player = Bukkit.getPlayerExact(in.readUTF());
          if (player != null) {
            String leader = in.readUTF();
            Profile profile = Profile.getProfile(player.getName());
            if (profile.getSkinListContainer().getSkins().size() > 20) {
              player.sendMessage("§cVocê atingiu o limite máximo de skins.");
              return;
            }

            profile.getSkinListContainer().addSkin(leader);
            player.sendMessage("§aSua skin foi atualizada, relogue para ela aparecer.");
            profile.getSkinsContainer().setSkin(leader);
          }
          break;
        }

        case "FAKE_BOOK2": {
          Player player = Bukkit.getPlayerExact(in.readUTF());
          if (player != null) {
            String roleName = in.readUTF();
            String sound = in.readUTF();
            EnumSound.valueOf(sound).play(player, 1.0F, sound.contains("VILL") ? 1.0F : 2.0F);
            FakeManager.sendSkin(player, roleName);
          }
          break;
        }

        case "PARTY": {
          try {
            JSONObject changes = (JSONObject) new JSONParser().parse(in.readUTF());
            String leader = changes.get("leader").toString();
            boolean delete = changes.containsKey("delete");
            BukkitParty party = BukkitPartyManager.getLeaderParty(leader);

            if (party == null) {
              if (delete) return;
              party = BukkitPartyManager.createParty(leader, 0);
            }

            if (delete) {
              party.delete();
              return;
            }

            if (changes.containsKey("newLeader")) {
              party.transfer(changes.get("newLeader").toString());
            }

            if (changes.containsKey("remove")) {
              party.listMembers().removeIf(pp -> pp.getName().equalsIgnoreCase(changes.get("remove").toString()));
            }

            for (Object object : (JSONArray) changes.get("members")) {
              if (!party.isMember(object.toString())) {
                party.listMembers().add(new PartyPlayer(object.toString(), MEMBER));
              }
            }
          } catch (ParseException ignore) {
          }
          break;
        }
      }
    }
  }
}
