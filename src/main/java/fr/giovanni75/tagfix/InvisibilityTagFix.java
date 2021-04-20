package fr.giovanni75.tagfix;

import net.minecraft.server.v1_8_R3.MinecraftServer;
import net.minecraft.server.v1_8_R3.Scoreboard;
import net.minecraft.server.v1_8_R3.ScoreboardTeam;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.logging.Level;

public class InvisibilityTagFix extends JavaPlugin implements Listener {

	static Scoreboard board;

	private void removePlayerTeam(Player p) {
		final ScoreboardTeam team = board.getTeam(p.getUniqueId().toString().substring(0, 16));
		if (team != null) board.removeTeam(team);
	}

	@Override
	public void onEnable() {
		board = MinecraftServer.getServer().getWorldServer(0).getScoreboard();
		Bukkit.getPluginManager().registerEvents(this, this);
		new InvisibilityTask().runTaskTimerAsynchronously(this, 0, 1);
	}

	@Override
	public void onDisable() {
		for (Player p : Bukkit.getOnlinePlayers())
			removePlayerTeam(p);
	}

	@EventHandler(priority = EventPriority.MONITOR)
	public void onJoin(PlayerJoinEvent e) {
		final String name = e.getPlayer().getUniqueId().toString().substring(0, 16);
		if (board.getTeam(name) == null) {
			board.createTeam(name);
		} else {
			Bukkit.getLogger().log(Level.WARNING, "The team associated to " + e.getPlayer().getName() + " already exists !");
		}
		board.addPlayerToTeam(e.getPlayer().getName(), name);
	}

	@EventHandler(priority = EventPriority.MONITOR)
	public void onQuit(PlayerQuitEvent e) {
		removePlayerTeam(e.getPlayer());
	}

}
