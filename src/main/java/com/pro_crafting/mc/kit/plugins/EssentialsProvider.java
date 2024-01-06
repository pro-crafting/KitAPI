package com.pro_crafting.mc.kit.plugins;

import com.earth2me.essentials.CommandSource;
import com.earth2me.essentials.Essentials;
import com.earth2me.essentials.IEssentials;
import com.earth2me.essentials.Kit;
import com.earth2me.essentials.textreader.IText;
import com.earth2me.essentials.textreader.KeywordReplacer;
import com.earth2me.essentials.textreader.SimpleTextInput;
import com.pro_crafting.mc.kit.KitProvider;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.Plugin;

public class EssentialsProvider implements KitProvider
{
	public boolean existsKit(String kitName) {
		kitName = kitName.toLowerCase();
		return this.getPlugin().getSettings().getKit(kitName) != null;
	}
	
	private Essentials getPlugin()
	{
		Plugin plugin = Bukkit.getPluginManager().getPlugin("Essentials");
		 
	    if (!(plugin instanceof IEssentials)) {
	        return null; 
	    }
	    
	    return (Essentials) plugin;
	}

	public ItemStack[] getItems(String kitName) {
		Essentials plugin =  this.getPlugin();
		
		kitName = kitName.toLowerCase();

		List<String> items = null;
		try {
			items = new Kit(kitName, plugin).getItems();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		IText input = new SimpleTextInput(items);
        IText output = new KeywordReplacer(input, new CommandSource(Bukkit.getConsoleSender()), plugin);

        List<ItemStack> ret = new ArrayList<ItemStack>();
        for (String kitItem : output.getLines())
        {
        	final String[] parts = kitItem.split(" +");
            try {
				final ItemStack parseStack = plugin.getItemDb().get(parts[0], parts.length > 1 ? Integer.parseInt(parts[1]) : 1);
				ret.add(parseStack);
			} catch (Exception e) {
				e.printStackTrace();
			}
        }
        
		return ret.toArray(new ItemStack[0]);
	}

	public void distribute(String kitName, Player player) {
		player.getInventory().addItem(getItems(kitName));
	}
	
	public String getName() {
		return "Essentials";
	}
}
