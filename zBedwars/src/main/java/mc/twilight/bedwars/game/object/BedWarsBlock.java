package mc.twilight.bedwars.game.object;

import org.bukkit.Material;

public class BedWarsBlock {
  
  private Material material;
  private byte data;
  
  public BedWarsBlock(Material material, byte data) {
    this.material = material;
    this.data = data;
  }
  
  public Material getMaterial() {
    return this.material;
  }
  
  public byte getData() {
    return this.data;
  }
  
  @Override
  public String toString() {
    return "BedWarsBlock{material=" + material + ", data=" + data + "}";
  }
}