package fr.giovanni75.tagfix;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabExecutor;

import java.util.Collections;
import java.util.List;

public class CommandTagFix implements TabExecutor {

	private static final List<String> completions = Collections.singletonList("reload");

	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		if (sender.hasPermission(Configuration.PERMISSION)) {
			if (args.length != 0 && args[0].equalsIgnoreCase("reload")) {
				Configuration.load();
				InvisibilityTask.load();
				sender.sendMessage("§b[InvisibilityTagFix] §fSuccessfully reloaded configuration.");
			} else {
				sender.sendMessage("§7-----------------------------------------------------");
				sender.sendMessage("§b[InvisibilityTagFix] §f§lVersion v" + InvisibilityTagFix.getCurrentVersion());
				sender.sendMessage("§oCorrectly hides nametags of invisible players on Minecraft 1.8");
				sender.sendMessage("§ohttps://www.spigotmc.org/resources/invisibilitytagfix.91316/");
				sender.sendMessage("§7-----------------------------------------------------");
			}
		} else {
			sender.sendMessage(Configuration.PERMISSION_MESSAGE);
		}
		return true;
	}

	@Override
	public List<String> onTabComplete(CommandSender sender, Command command, String label, String[] args) {
		return completions;
	}

}
