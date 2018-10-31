package me.davidjdm.QuickTree;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.plugin.PluginDescriptionFile;
import org.bukkit.plugin.java.JavaPlugin;

import net.md_5.bungee.api.ChatColor;

/**
 * Console class enables plugin and registers Events class
 * @author DavidJDM
 * Date: 10/18/2018
 * File: Console.java
 */
public class Console extends JavaPlugin {
	PluginDescriptionFile pdfFile = this.getDescription(); //gets plugin description
	private Commands commands = new Commands();
	
	@Override
	public void onEnable() {
		getConfig().options().copyDefaults(true);
		getConfig().options().header("Developed by DavidJDM\nPlugin Version: 1.2\n\nIf you have questions or any errors, please post on plugin discussion page\nhttps://www.spigotmc.org/threads/quicktreechop.343556\nPlease leave a review! https://www.spigotmc.org/resources/quicktreechop.61661/reviews\n\n\nAccurate tool names: 'DIAMOND_AXE' 'IRON_AXE' 'GOLDEN_AXE' 'STONE_AXE' 'WOODEN_AXE'");
		saveConfig();
		reloadConfig();
		
		Bukkit.getPluginManager().registerEvents(new Events(), this); //allows the events class to be implemented onEnable()
		getServer().getConsoleSender().sendMessage(ChatColor.GREEN + "[" + pdfFile.getVersion() + "] " + getName() + " has been enabled!"); //prints a message in server console
		
		getCommand(commands.command1).setExecutor(commands);
	}
	
	@Override
	public void onDisable() {
		getServer().getConsoleSender().sendMessage(ChatColor.GREEN + "[" + pdfFile.getVersion() + "] " + getName() + " has been disabled!"); //prints a message in server console
	}

	public boolean onCommand(CommandSender sender, Command command, String label, String[] args, Object e) {
		sender.sendMessage("" + sender);
		sender.sendMessage("" + command);
		sender.sendMessage("" + label);
		sender.sendMessage("" + args);
		sender.sendMessage("" + e);
		if(label.equalsIgnoreCase("quicktree")) {
			sender.sendMessage("firstpart worked");
			if(sender.hasPermission(command.getPermission())) {
				sender.sendMessage(ChatColor.DARK_GREEN + "[" + pdfFile.getVersion() + "] " + ChatColor.DARK_GREEN + pdfFile.getName() + " plugin commands:"
						+ ChatColor.GREEN + "\n/quicktree: " + ChatColor.GRAY + "Displays plugin commands"
								+ ChatColor.GREEN + "\n/quicktreetoggle: " + ChatColor.GRAY + "Toggles plugin On/Off");
				return true;
			}
		}
		return false;
	}
	
	
	
	
	
	
	
	
	
	
	public static void destroyTree(BlockBreakEvent event, boolean loop) {
		Block b = event.getBlock();
		Block b2;
		int thickTreeRadius = 0;
		int treeHeight = 2;

		if(b.getType().equals(Material.LOG) || b.getType().equals(Material.LOG_2)) {
			while(loop == true) {
				if(b.getRelative(BlockFace.UP).getType().equals(Material.LOG) || b.getRelative(BlockFace.UP).getType().equals(Material.LOG_2)) { //move up the tree and count its height
					b = b.getRelative(BlockFace.UP);
					treeHeight++;
				}
		
				else if(b.getRelative(BlockFace.UP).getType().equals(Material.LEAVES) || b.getRelative(BlockFace.UP).getType().equals(Material.LEAVES_2)) { //check if leaves are reached and add them to the height of the tree
					loop = false;
					b2 = b.getRelative(BlockFace.UP);
					while(b2.getRelative(BlockFace.NORTH).getType().equals(Material.LOG) || b2.getRelative(BlockFace.NORTH).getType().equals(Material.LOG_2) || b2.getRelative(BlockFace.NORTH).getType().equals(Material.LEAVES) || b2.getRelative(BlockFace.NORTH).getType().equals(Material.LEAVES_2)) {
						b2 = b2.getRelative(BlockFace.NORTH);
						thickTreeRadius++;
					}
					thickTreeRadius += 2;
					b = b.getRelative(BlockFace.UP);
					
					while(b.getRelative(BlockFace.UP).getType().equals(Material.LEAVES) || b.getRelative(BlockFace.UP).getType().equals(Material.LEAVES_2)) {
						b = b.getRelative(BlockFace.UP);
						treeHeight++;
					}
		
					b.breakNaturally();
					
					for(int i = 0; i <= treeHeight; i++) {
						
						b.breakNaturally();
						for (int x = -(thickTreeRadius); x <= thickTreeRadius; x++)
						{
						    for (int z = -(thickTreeRadius); z <= thickTreeRadius; z++)
						    {
						        if (b.getRelative(x,0,z).getType() == Material.LOG || b.getRelative(x, 0, z).getType() == Material.LOG_2 || b.getRelative(x, 0, z).getType() == Material.LEAVES || b.getRelative(x, 0, z).getType() == Material.LEAVES_2)
						        {
						        	b.getRelative(x,0,z).breakNaturally();
						        	
						        }
						    }
						}
						b = b.getRelative(BlockFace.DOWN);
					}
				}
				
				else {
					loop = false;
				}
			}
		}
	}
}
