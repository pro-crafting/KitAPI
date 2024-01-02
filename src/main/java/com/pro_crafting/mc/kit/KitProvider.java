package com.pro_crafting.mc.kit;

import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public interface KitProvider 
{
	boolean existsKit(String kitName);
	ItemStack[] getItems(String kitName);
	void distribute(String kitName, Player player);
	String getName();
}
