package mc.twilight.cosmeticos.cosmetics.types.gadgets;

import mc.twilight.cosmeticos.Main;
import mc.twilight.cosmeticos.cosmetics.object.GadgetCooldown;
import mc.twilight.cosmeticos.cosmetics.types.GadgetsCosmetic;
import mc.twilight.cosmeticos.hook.player.CUser;
import mc.twilight.core.libraries.npclib.npc.ai.NPCHolder;
import mc.twilight.core.utils.CubeID;
import mc.twilight.core.utils.enums.EnumRarity;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.metadata.FixedMetadataValue;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.Vector;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Trampoline extends GadgetsCosmetic {
  
  public Trampoline() {
    super("Trampolim", EnumRarity.COMUM, "SLIME_BLOCK : 1 : nome>Trampolim : desc>&7Invoque um Pula-Pula no lobby.", "SLIME_BLOCK : 1 : nome>&6Engenhoca: &aTrampolim");
    this.registerListener();
  }
  
  @EventHandler(priority = EventPriority.MONITOR)
  public void onPlayerMove(PlayerMoveEvent evt) {
    Player player = evt.getPlayer();
    Block block = evt.getTo().getBlock().getRelative(BlockFace.DOWN);
    if (block.hasMetadata("TRAMPOLINE") && block.getType() == Material.WOOL && block.getData() == (byte) 15) {
      player.setVelocity(new Vector(0, 3, 0));
    }
  }
  
  @Override
  public void onClick(CUser user) {
    Player player = user.getPlayer();
    String cooldown = GadgetCooldown.getCooldown(player);
    if (!cooldown.isEmpty()) {
      player.sendMessage("§cAguarde " + cooldown + " para utilizar novamente uma Engenhoca!");
      return;
    }
    
    if (!player.isOnGround()) {
      player.sendMessage("§cVocê precisa estar no chão para utilizar o Trampolim!");
      return;
    }
    
    Location location = player.getLocation().clone();
    Location cube1 = player.getLocation().clone().add(-3.0D, 0.0D, -3.0D);
    Location cube2 = player.getLocation().clone().add(3.0D, 20.0D, 3.0D);
    Block stairs1 = player.getLocation().clone().getBlock().getRelative(-4, 1, 0);
    Block stairs2 = player.getLocation().clone().getBlock().getRelative(-5, 0, 0);
    CubeID cubeid = new CubeID(cube1, cube2);
    boolean empty = true;
    for (Block block : cubeid) {
      if (block.getType() != Material.AIR) {
        empty = false;
        break;
      }
    }
    for (Entity entity : player.getNearbyEntities(3.0, 3.0, 3.0)) {
      if (entity.hasMetadata("NPC")) {
        empty = false;
        break;
      }
    }
    
    if (empty && stairs1.getType() == Material.AIR && stairs2.getType() == Material.AIR) {
      GadgetCooldown.setCooldown(player, 35);
      
      Map<Block, String> blocks = new HashMap<>();
      spawnTrampoline(location, blocks);
      player.teleport(location.clone().add(0, 5.0, 0));
      getNearbyEntities(location, 3.0).forEach(le -> le.teleport(le.getLocation().add(0, 2.0, 0.0)));
      
      new BukkitRunnable() {
        @Override
        public void run() {
          blocks.forEach((block, data) -> {
            block.setType(Material.matchMaterial(data.split(":")[0]));
            block.setData(Byte.parseByte(data.split(":")[1]));
            block.removeMetadata("TRAMPOLINE", Main.getInstance());
          });
          blocks.clear();
        }
      }.runTaskLater(Main.getInstance(), 300L);
    } else {
      player.sendMessage("§cNão é possível invocar um trampolim neste local.");
    }
  }
  
  private void spawnTrampoline(Location location, Map<Block, String> blocks) {
    this.setBlock(blocks, location.clone().add(3, 0, 3), Material.FENCE, (byte) 0);
    this.setBlock(blocks, location.clone().add(-3, 0, 3), Material.FENCE, (byte) 0);
    this.setBlock(blocks, location.clone().add(3, 0, -3), Material.FENCE, (byte) 0);
    this.setBlock(blocks, location.clone().add(-3, 0, -3), Material.FENCE, (byte) 0);
    this.setBlock(blocks, location.clone().add(3, 1, 3), Material.WOOL, (byte) 11);
    this.setBlock(blocks, location.clone().add(2, 1, 3), Material.WOOL, (byte) 11);
    this.setBlock(blocks, location.clone().add(1, 1, 3), Material.WOOL, (byte) 11);
    this.setBlock(blocks, location.clone().add(0, 1, 3), Material.WOOL, (byte) 11);
    this.setBlock(blocks, location.clone().add(-1, 1, 3), Material.WOOL, (byte) 11);
    this.setBlock(blocks, location.clone().add(-2, 1, 3), Material.WOOL, (byte) 11);
    this.setBlock(blocks, location.clone().add(-3, 1, 3), Material.WOOL, (byte) 11);
    this.setBlock(blocks, location.clone().add(3, 1, 2), Material.WOOL, (byte) 11);
    this.setBlock(blocks, location.clone().add(3, 1, 1), Material.WOOL, (byte) 11);
    this.setBlock(blocks, location.clone().add(3, 1, 0), Material.WOOL, (byte) 11);
    this.setBlock(blocks, location.clone().add(3, 1, -1), Material.WOOL, (byte) 11);
    this.setBlock(blocks, location.clone().add(3, 1, -2), Material.WOOL, (byte) 11);
    this.setBlock(blocks, location.clone().add(-3, 1, 2), Material.WOOL, (byte) 11);
    this.setBlock(blocks, location.clone().add(-3, 1, 1), Material.WOOL, (byte) 11);
    this.setBlock(blocks, location.clone().add(-3, 1, 0), Material.WOOL, (byte) 11);
    this.setBlock(blocks, location.clone().add(-3, 1, -1), Material.WOOL, (byte) 11);
    this.setBlock(blocks, location.clone().add(-3, 1, -2), Material.WOOL, (byte) 11);
    this.setBlock(blocks, location.clone().add(3, 1, -3), Material.WOOL, (byte) 11);
    this.setBlock(blocks, location.clone().add(2, 1, -3), Material.WOOL, (byte) 11);
    this.setBlock(blocks, location.clone().add(1, 1, -3), Material.WOOL, (byte) 11);
    this.setBlock(blocks, location.clone().add(0, 1, -3), Material.WOOL, (byte) 11);
    this.setBlock(blocks, location.clone().add(-1, 1, -3), Material.WOOL, (byte) 11);
    this.setBlock(blocks, location.clone().add(-2, 1, -3), Material.WOOL, (byte) 11);
    this.setBlock(blocks, location.clone().add(-3, 1, -3), Material.WOOL, (byte) 11);
    this.setBlock(blocks, location.clone().add(2, 1, 2), Material.WOOL, (byte) 15);
    this.setBlock(blocks, location.clone().add(1, 1, 2), Material.WOOL, (byte) 15);
    this.setBlock(blocks, location.clone().add(0, 1, 2), Material.WOOL, (byte) 15);
    this.setBlock(blocks, location.clone().add(-1, 1, 2), Material.WOOL, (byte) 15);
    this.setBlock(blocks, location.clone().add(-2, 1, 2), Material.WOOL, (byte) 15);
    this.setBlock(blocks, location.clone().add(2, 1, 1), Material.WOOL, (byte) 15);
    this.setBlock(blocks, location.clone().add(1, 1, 1), Material.WOOL, (byte) 15);
    this.setBlock(blocks, location.clone().add(0, 1, 1), Material.WOOL, (byte) 15);
    this.setBlock(blocks, location.clone().add(-1, 1, 1), Material.WOOL, (byte) 15);
    this.setBlock(blocks, location.clone().add(-2, 1, 1), Material.WOOL, (byte) 15);
    this.setBlock(blocks, location.clone().add(2, 1, 0), Material.WOOL, (byte) 15);
    this.setBlock(blocks, location.clone().add(1, 1, 0), Material.WOOL, (byte) 15);
    this.setBlock(blocks, location.clone().add(0, 1, 0), Material.WOOL, (byte) 15);
    this.setBlock(blocks, location.clone().add(-1, 1, 0), Material.WOOL, (byte) 15);
    this.setBlock(blocks, location.clone().add(-2, 1, 0), Material.WOOL, (byte) 15);
    this.setBlock(blocks, location.clone().add(2, 1, -1), Material.WOOL, (byte) 15);
    this.setBlock(blocks, location.clone().add(1, 1, -1), Material.WOOL, (byte) 15);
    this.setBlock(blocks, location.clone().add(0, 1, -1), Material.WOOL, (byte) 15);
    this.setBlock(blocks, location.clone().add(-1, 1, -1), Material.WOOL, (byte) 15);
    this.setBlock(blocks, location.clone().add(-2, 1, -1), Material.WOOL, (byte) 15);
    this.setBlock(blocks, location.clone().add(2, 1, -2), Material.WOOL, (byte) 15);
    this.setBlock(blocks, location.clone().add(1, 1, -2), Material.WOOL, (byte) 15);
    this.setBlock(blocks, location.clone().add(0, 1, -2), Material.WOOL, (byte) 15);
    this.setBlock(blocks, location.clone().add(-1, 1, -2), Material.WOOL, (byte) 15);
    this.setBlock(blocks, location.clone().add(-2, 1, -2), Material.WOOL, (byte) 15);
    this.setBlock(blocks, location.clone().add(-4, 1, 0), Material.WOOD_STAIRS, (byte) 0);
    this.setBlock(blocks, location.clone().add(-5, 0, 0), Material.WOOD_STAIRS, (byte) 0);
  }
  
  private void setBlock(Map<Block, String> blocks, Location location, Material material, byte data) {
    Block block = location.getBlock();
    blocks.put(block, block.getType() + ":" + block.getData());
    block.setType(material);
    block.setData(data);
    block.setMetadata("TRAMPOLINE", new FixedMetadataValue(Main.getInstance(), true));
  }
  
  private List<LivingEntity> getNearbyEntities(Location location, double range) {
    List<LivingEntity> entities = new ArrayList<>();
    for (Entity entity : location.getWorld().getEntities()) {
      if (entity instanceof LivingEntity && !(entity instanceof ArmorStand) && !(entity instanceof NPCHolder) && entity.getLocation().distance(location) <= range) {
        entities.add((LivingEntity) entity);
      }
    }
    
    return entities;
  }
}
