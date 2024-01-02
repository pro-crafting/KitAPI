package com.pro_crafting.mc.kit;

import com.pro_crafting.mc.kit.plugins.AdminCmdProvider;
import com.pro_crafting.mc.kit.plugins.CommandBookProvider;
import com.pro_crafting.mc.kit.plugins.EssentialsProvider;

import java.util.ArrayList;
import java.util.List;
import org.bukkit.Bukkit;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.ServicePriority;
import org.bukkit.plugin.ServicesManager;

public class KitAPI
{

	private static KitAPI instance;
	private List<KitProvider> kitProviders;
	private ServicesManager sm;

	public KitAPI() {
		instance = this;
	}

	public static KitAPI getInstance() {
		if (instance == null) {
			instance = new KitAPI();
		}
		return instance;
	}

	public void load() {
		this.kitProviders = new ArrayList<KitProvider>();
		this.sm = Bukkit.getServicesManager();
		this.loadKitPlugins();
	}

	private void loadKitPlugins()
	{
		hookKitPlugin("AdminCmd", AdminCmdProvider.class);
		hookKitPlugin("Essentials", EssentialsProvider.class);
		hookKitPlugin("CommandBook", CommandBookProvider.class);
	}
	
	private void hookKitPlugin(String name, Class<? extends KitProvider> hookClass)
	{
		Plugin plugin = Bukkit.getPluginManager().getPlugin(name);
		if (Bukkit.getPluginManager().getPlugin(name) != null)
		{
			try {
				KitProvider instance = hookClass.getConstructor().newInstance();
				this.kitProviders.add(instance);
				this.sm.register(KitProvider.class, instance, plugin, ServicePriority.Normal);
			} catch (Exception ex) {
				
			}
		}
	}
}
