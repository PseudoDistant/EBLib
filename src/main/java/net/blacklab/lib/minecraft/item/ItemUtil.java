package net.blacklab.lib.minecraft.item;

import net.minecraft.entity.EquipmentSlot;
import net.minecraft.item.ArmorItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

@SuppressWarnings("ALL")
public class ItemUtil {
	
	public static Item getItemByStringId(String id){
		return Registry.ITEM.get(new Identifier(id));
	}

	public static boolean isHelm(ItemStack stack){
		if(stack!=null){
			if(stack.getItem() instanceof ArmorItem){
				if(((ArmorItem)stack.getItem()).getSlotType() == EquipmentSlot.HEAD){
					return true;
				}
			}
		}
		return false;
	}
	
	public static int getFoodAmount(ItemStack pStack) {
		if (pStack == null) {
			return -1;
		}
		if (pStack.getItem().isFood()) {
			return pStack.getItem().getFoodComponent().getHunger();
		}
		return -1;
	}
}
