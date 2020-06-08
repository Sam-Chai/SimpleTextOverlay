package simpletextoverlay.util;

import net.minecraft.inventory.IInventory;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;


public class EntityHelper {

    public static int getItemCountInInventory(final IInventory inventory, final Item item) {
        return getItemCountInInventory(inventory, item, -1);
    }

    public static int getItemCountInInventory(final IInventory inventory, final Item item, final int itemDamage) {
        final int inventorySize = inventory.getSizeInventory();
        int count = 0;

        for (int slot = 0; slot < inventorySize; slot++) {
            final ItemStack itemStack = inventory.getStackInSlot(slot);

            if (itemStack.getItem() == item && (itemDamage == -1 || itemDamage == itemStack.getItemDamage())) {
                count += itemStack.getCount();
            }
        }

        return count;
    }

}
