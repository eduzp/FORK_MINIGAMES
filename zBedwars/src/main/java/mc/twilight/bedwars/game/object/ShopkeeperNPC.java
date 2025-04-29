package mc.twilight.bedwars.game.object;

import mc.twilight.bedwars.Language;
import mc.twilight.bedwars.cosmetics.CosmeticType;
import mc.twilight.bedwars.cosmetics.types.ShopkeeperSkin;
import mc.twilight.bedwars.game.BedWarsTeam;
import mc.twilight.bedwars.hook.container.SelectedContainer;
import mc.twilight.bedwars.listeners.entity.EntityListener;
import mc.twilight.bedwars.lobby.trait.NPCSkinTrait;
import mc.twilight.core.libraries.holograms.HologramLibrary;
import mc.twilight.core.libraries.holograms.api.Hologram;
import mc.twilight.core.libraries.npclib.NPCLibrary;
import mc.twilight.core.libraries.npclib.api.npc.NPC;
import mc.twilight.core.player.Profile;
import mc.twilight.core.utils.BukkitUtils;
import org.bukkit.Location;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.LivingEntity;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.Collectors;

public class ShopkeeperNPC {
  
  protected String type;
  protected Location location;
  protected BedWarsTeam team;
  
  private NPC npc;
  private Hologram hologram;
  
  public ShopkeeperNPC(String type, Location location, BedWarsTeam team) {
    this.type = type;
    this.location = location;
    this.team = team;
    if (!this.location.getChunk().isLoaded()) {
      this.location.getChunk().load(true);
    }
  
    this.spawn();
  }
  
  public void update() {
    if (this.hologram == null) {
      return;
    }
    
    List<String> list = new ArrayList<>(type.equals("items") ? Language.ingame$npc$shop$item$hologram : Language.ingame$npc$shop$upgrade$hologram);
    for (int index = list.size(); index > 0; index--) {
      this.hologram.updateLine(list.size() - (index - 1), list.get(index - 1));
    }
  }
  
  public void spawn() {
    if (this.npc != null) {
      this.npc.destroy();
      this.npc = null;
    }
    ShopkeeperSkin skin = null;
    List<Profile> profiles = this.team.listPlayers().stream().map(player -> Profile.getProfile(player.getName()))
        .filter(profile -> profile.getAbstractContainer("zCoreBedWars", "selected", SelectedContainer.class).getSelected(CosmeticType.SHOPKEEPERSKIN, ShopkeeperSkin.class) != null)
        .collect(Collectors.toList());
    if (profiles.size() > 0) {
      skin = profiles.get(ThreadLocalRandom.current().nextInt(profiles.size())).getAbstractContainer("zCoreBedWars", "selected", SelectedContainer.class)
          .getSelected(CosmeticType.SHOPKEEPERSKIN, ShopkeeperSkin.class);
    }
    this.npc = NPCLibrary.createNPC(EntityType.PLAYER, "§8[NPC] ");
    this.npc.data().set("shopkeeper-type", type);
    this.npc.data().set(NPC.PROTECTED_KEY, true);
    if (skin == null) {
      this.npc.addTrait(new NPCSkinTrait(this.npc, "ewogICJ0aW1lc3RhbXAiIDogMTYyMzI1Mzg3NzQwNSwKICAicHJvZmlsZUlkIiA6ICJhMDVkZWVjMDdhMGU0MDc2ODdjYmRjNWRjYWNhODU4NiIsCiAgInByb2ZpbGVOYW1lIiA6ICJWaWxsYWdlciIsCiAgInNpZ25hdHVyZVJlcXVpcmVkIiA6IHRydWUsCiAgInRleHR1cmVzIiA6IHsKICAgICJTS0lOIiA6IHsKICAgICAgInVybCIgOiAiaHR0cDovL3RleHR1cmVzLm1pbmVjcmFmdC5uZXQvdGV4dHVyZS83YWY3YzA3ZDFkZWQ2MWIxZDMzMTI2ODViMzJlNDU2OGZmZGRhNzYyZWM4ZDgwODg5NWNjMzI5YTkzZDYwNmUwIgogICAgfQogIH0KfQ==", "yeeouiiK7vV/9o4BpBUz/UMoRWqkLE9GeLU/fbcXWFBycWw/z2CxX8UXdXrmg8IDKOA9ELTeeQQcuejo7dC0tsOGhgyeWPJR5jn8H3w24ZmeG154cQMvtTQW/fKuCcwFxj807VSBtGoEYqYjgaxYmou32dBCnPrB+L7RUf/kNO7wPhTj1zykwuhdXBNHS8G05ghFXmr9SN1CoctUhR5aK++CESn5qLVMhZAA81eCd29v0Tx65Kq6gSQpBU/dElFaVhCaQph5j/0XgDfYy5TOwK4tvhA18vpbHPGRXFg0Da873d0U5g9JqFcMMzD3DWq+J2XdhASyDjX4qlrn+0ShKtS2FIeN+soksLH+62pSuF+cNmvltPLktSlyp5tf6gy6Eox/MH6GANJ5O+NCDxxvqNli8z14zfM62nHJMIeDXGYyeZLyf35n9bHbIZnVmPjgp+/g0xyDq02jGOJvWmzlZe+WdhnDc6HeIIJFSkoY8GBUkPqhMTmCge6FhcahWQFxO0AXZysQevl+DlvwRkmcBTH3PgW6ryebcDWxZ7LdH6tg9RCO/aKwoi1fw06NczAOtLrz4zN1jvJD8tzaNtID78nYVPSCJWysMY5NnEVQdE6DglHs13Xe1kJtMK1Xs75j9WbTfrulLPVlbGAE4//odVKB7jhi2xtA7VUr45Y7I3Y="));
    } else {
      this.npc.addTrait(new NPCSkinTrait(this.npc, skin.getValue(), skin.getSignature()));
    }
    this.npc.spawn(this.location);
    EntityListener.PROTECT_EVERY.add(this.npc.getEntity());
    double height = 0.5;
    if (this.npc.getEntity() instanceof LivingEntity) {
      height = ((LivingEntity) this.npc.getEntity()).getEyeHeight() - 1.0;
    }
    this.hologram = HologramLibrary.createHologram(location.clone().add(0, height, 0));
    List<String> list = new ArrayList<>(type.equals("items") ? Language.ingame$npc$shop$item$hologram : Language.ingame$npc$shop$upgrade$hologram);
    for (int index = (list.size()); index > 0; index--) {
      this.hologram.withLine(list.get(index - 1));
    }
  }
  
  public void destroy() {
    this.type = null;
    this.location = null;
  
    EntityListener.PROTECT_EVERY.remove(this.npc.getEntity());
    this.npc.destroy();
    this.npc = null;
    HologramLibrary.removeHologram(this.hologram);
    this.hologram = null;
  }
  
  public String getType() {
    return this.type;
  }
  
  public Location getLocation() {
    return this.location;
  }
}
