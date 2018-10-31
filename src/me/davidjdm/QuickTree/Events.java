package me.davidjdm.QuickTree;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.plugin.Plugin;

import net.md_5.bungee.api.ChatColor;

/**
 * Events class that listens for certain events
 * @author DavidJDM
 * Date: 10/18/2018
 * File: Events.java
 */
public class Events implements Listener {
	private boolean loop;
	private Plugin plugin = Console.getPlugin(Console.class);
	
	/**
	 * isTree class checks if block broken is part of a tree, if true then the whole tree is destroyed.
	 * @param event - block broken by player
	 */
	
	@EventHandler
	public void logBreak(BlockBreakEvent event) {
		Player p = event.getPlayer();
		
		String itemInHand = p.getInventory().getItemInMainHand().getType().toString().toLowerCase();
		boolean enable = plugin.getConfig().getBoolean("tool-enable");
		if(p.hasPermission("quicktree.use")) {
			if(enable == true) {
				if(plugin.getConfig().getString("tool", ChatColor.RED + plugin.getName() + " tool bind config error!").toLowerCase().equals(itemInHand)) {
					loop = true;
					Console.destroyTree(event, loop);
				}
				
				else {
					loop = false;
				}
			}
			
			else {
				loop = true;
				Console.destroyTree(event, loop);
			}
		}
	}

}
