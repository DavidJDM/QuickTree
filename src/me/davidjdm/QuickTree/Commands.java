package me.davidjdm.QuickTree;

import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandException;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.plugin.Plugin;

import net.md_5.bungee.api.ChatColor;
import net.minecraft.server.v1_12_R1.CommandExecute;

public class Commands extends CommandExecute implements Listener, CommandExecutor {
	
	public String command1 = "quicktree";
	
	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		Plugin plugin = Console.getPlugin(Console.class);
		FileConfiguration config = Console.getPlugin(Console.class).getConfig();
		if(!(sender instanceof Player)) {
			
			return false;
		}
		Player p = (Player) sender;
		
		if(p.hasPermission("quicktree") && command.getName().equalsIgnoreCase(command1)) {
			if(args.length > 0) {
				if(args[0].equalsIgnoreCase("toggle")) {
					
				}
				
				else if(p.hasPermission("quicktree.tool") && args[0].equalsIgnoreCase("tool")) {
					if(args.length > 1) {
						if(p.hasPermission("quicktree.tool.bind") && args[1].equalsIgnoreCase("bind")) {
							if(args.length > 2) {
								if(isValidMaterial(args[2].toString()) == true) {
									config.set("tool", args[2].toString());
									plugin.saveConfig();
									p.sendMessage(ChatColor.YELLOW + "[QuickTree]: " + ChatColor.GRAY + "Tool set to " + args[2]);
								}
								
								else {
									p.sendMessage(ChatColor.YELLOW + "[QuickTree]: " + ChatColor.RED + "Invalid tool name!\n\n" + "\n" + ChatColor.YELLOW + "Valid tools: \n"
											+ ChatColor.GRAY + "DIAMOND_AXE\n"
													+ "IRON_AXE\n"
													+ "GOLD_AXE\n"
													+ "STONE_AXE\n"
													+ "WOOD_AXE\n");
								}
							}
							
							else {
								p.sendMessage(ChatColor.YELLOW + "[QuickTree]: " + ChatColor.GRAY + "Usage /qt tool bind <toolname>");
							}
						}
						
						else if(p.hasPermission("quicktree.tool.toggle") && args[1].equalsIgnoreCase("toggle")) {
							if(config.getBoolean("tool-enable") == false) {
								config.set("tool-enable", true);
								plugin.saveConfig();
								p.sendMessage(ChatColor.YELLOW + "[QuickTree]: " + ChatColor.GRAY + "Tool Bind: " + ChatColor.GREEN + "ENABLED");
							}
							
							else if(config.getBoolean("tool-enable")) {
								config.set("tool-enable", false);
								plugin.saveConfig();
								p.sendMessage(ChatColor.YELLOW + "[QuickTree]: " + ChatColor.GRAY + "Tool Bind: " + ChatColor.RED + "DISABLED");
							}
							
							else {
								p.sendMessage(ChatColor.YELLOW + "[QuickTree]: " + ChatColor.RED + "Error in config.yml file! Set tool-enable to true or false.");
								
							}
						}
						
						else {
							if(p.hasPermission("quicktree.tool.toggle")) p.sendMessage(ChatColor.YELLOW + "[QuickTree]: " + ChatColor.RED + "Invalid syntax! Usage " + ChatColor.GRAY + "/qt tool <command>");
							else {
								p.sendMessage(ChatColor.YELLOW + "[QuickTree]: " + ChatColor.RED + "You do not have permission to use this command");
							}
						}
					}
					
					else {
						p.sendMessage(ChatColor.YELLOW + "[QuickTree]: " + ChatColor.GRAY + "Available tool commands\n" + ChatColor.YELLOW + "/qt tool toggle\n/qt tool bind " + ChatColor.GRAY + "<toolname>\n" + "");
					}
				}
				
				else if(p.hasPermission("quicktree.help") && args[0].equals("help")) {
					p.sendMessage(ChatColor.YELLOW + "[QuickTree]: Commands List\n"
							+ "/qt: " + ChatColor.GRAY + "General plugin command\n"
									+ ChatColor.YELLOW + "/qt help: " + ChatColor.GRAY + "Lists available plugin commands\n"
											+ ChatColor.YELLOW + "/qt tool: " + ChatColor.GRAY + "Displays tool commands\n"
													+ ChatColor.YELLOW + "/qt tool toggle: " + ChatColor.GRAY + "Toggles tool bind ON:OFF\n"
															+ ChatColor.YELLOW + "/qt tool bind <toolname>: " + ChatColor.GRAY + "Binds plugin to specified tool\n");
				}
				
				else {
					p.sendMessage(ChatColor.YELLOW + "[QuickTree]: " + ChatColor.RED + "You do not have permission to use this command");
				}
			}
			
			else {
				p.sendMessage(ChatColor.YELLOW + "[QuickTree]: " + ChatColor.GRAY + "Usage /qt help");
			}
		}
		
		else {
			if(command.getName().equalsIgnoreCase(command1)) {
				p.sendMessage(ChatColor.YELLOW + "[QuickTree]: " + ChatColor.RED + "You do not have permission to use this command");
			}
		}
		return true;
	}
	
	
	public boolean isValidMaterial(String toolName) {
		Material[] validToolNames = {Material.DIAMOND_AXE, Material.IRON_AXE, Material.GOLD_AXE, Material.STONE_AXE, Material.WOOD_AXE};
		
		
		try {
			for(Material tool : validToolNames) {
				if(toolName.equalsIgnoreCase(tool.toString())) {
					return true;
				}
			}
			return false;
		}
		
		catch(CommandException e) {
			return false;
		}
	}
}
