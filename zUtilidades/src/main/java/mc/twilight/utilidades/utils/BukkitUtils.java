package mc.twilight.utilidades.utils;

import net.md_5.bungee.api.chat.BaseComponent;
import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.HoverEvent;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

public class BukkitUtils {

    public static int removeItem(Inventory inventory, Material mat, int quantity) {
        int rest = quantity;
        for (int i = 0; i < inventory.getSize(); i++) {
            ItemStack stack = inventory.getItem(i);
            if (stack == null || stack.getType() != mat) {
                continue;
            }

            if (rest >= stack.getAmount()) {
                rest -= stack.getAmount();
                inventory.clear(i);
            } else if (rest > 0) {
                stack.setAmount(stack.getAmount() - rest);
                rest = 0;
            } else {
                break;
            }
        }

        return quantity - rest;
    }

}
