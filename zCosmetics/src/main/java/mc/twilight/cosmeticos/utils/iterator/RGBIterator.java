package mc.twilight.cosmeticos.utils.iterator;

import java.util.Iterator;

public class RGBIterator implements Iterator<String> {
  
  private int state;
  private int x, y, z;
  
  public RGBIterator() {
    this.x = this.y = this.z = 0;
  }
  
  @Override
  public boolean hasNext() {
    return true;
  }
  
  @Override
  public String next() {
    boolean hasNext = this.state == 0 ? z != 255 : this.state == 1 ? y != 0 : this.state == 2 ? y != 255 : x != 0;
    if (!hasNext) {
      this.state++;
      if (this.state > 3) {
        this.state = 0;
      }
      
      this.x = state != 1 && state != 3 ? 0 : 255;
      this.y = state != 1 && state != 3 ? 0 : 255;
      this.z = state != 1 && state != 3 ? 0 : 255;
    }
    
    String color = x + ":" + y + ":" + z;
    if (state == 0) {
      if (this.x < 255) {
        this.x += 5;
      }
      if (this.x == 255 && this.y < 255) {
        this.y += 5;
      }
      if (this.y == 255 && this.z < 255) {
        this.z += 5;
      }
    } else if (state == 1) {
      if (this.z > 0) {
        this.z -= 5;
      }
      if (this.z == 0 && this.x > 0) {
        this.x -= 5;
      }
      if (this.x == 0 && this.y > 0) {
        this.y -= 5;
      }
    } else if (state == 2) {
      if (this.z < 255) {
        this.z += 5;
      }
      if (this.z == 255 && this.x < 255) {
        this.x += 5;
      }
      if (this.x == 255 && this.y < 255) {
        this.y += 5;
      }
    } else if (state == 3) {
      if (this.z > 0) {
        this.z -= 5;
      }
      if (this.z == 0 && this.y > 0) {
        this.y -= 5;
      }
      if (this.y == 0 && this.x > 0) {
        this.x -= 5;
      }
    }
    
    return color;
  }
  
}
