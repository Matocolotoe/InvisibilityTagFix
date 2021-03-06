package fr.giovanni75.tagfix;

import net.minecraft.server.v1_8_R3.MinecraftServer;
import net.minecraft.server.v1_8_R3.Scoreboard;
import net.minecraft.server.v1_8_R3.ScoreboardTeam;
import org.bukkit.Bukkit;
import org.bukkit.craftbukkit.v1_8_R3.CraftServer;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.HashMap;

public class InvisibilityTagFix extends JavaPlugin implements Listener {

	private static InvisibilityTagFix instance;
	private static String version;

	/**
	 * An object representing the server on which the plugin is currently running. It is used to retrieve the scoreboard as
	 * well as a few properties such as online players and their active potion effects, without processing the data twice.
	 */
	private static final MinecraftServer server = MinecraftServer.getServer();

	/**
	 * The main scoreboard of the server.
	 * Must be initialized after the plugin is completely enabled.
	 */
	private static Scoreboard scoreboard;

	/**
	 * A map used to store the teams associated with players.
	 * Avoids having to build the corresponding string every tick.
	 */
	private static final HashMap<Player, ScoreboardTeam> teams = new HashMap<>();

	static CraftServer getCurrentServer() {
		return server.server;
	}

	static String getCurrentVersion() {
		return version;
	}

	static InvisibilityTagFix getInstance() {
		return instance;
	}

	static ScoreboardTeam getTeam(Player p) {
		return teams.get(p);
	}

	/**
	 * Deletes the unique scoreboard team associated with a player.
	 * Called when a player quits the server or when the plugin gets disabled.
	 */
	private static void removePlayerTeam(Player p) {
		final ScoreboardTeam team = teams.get(p);
		if (team != null)
			scoreboard.removeTeam(team);
	}

	@Override
	public void onEnable() {
		instance = this;
		scoreboard = server.getWorldServer(0).getScoreboard();
		version = Bukkit.getPluginManager().getPlugin("InvisibilityTagFix").getDescription().getVersion();

		saveDefaultConfig();
		Configuration.load();

		Bukkit.getPluginManager().registerEvents(this, this);
		getCommand("itf").setExecutor(new CommandTagFix());
		getCommand("itf").setTabCompleter(new CommandTagFix());
	}

	@Override
	public void onDisable() {
		for (Player p : Bukkit.getOnlinePlayers())
			removePlayerTeam(p);
	}

	@EventHandler(priority = EventPriority.MONITOR)
	public void onJoin(PlayerJoinEvent e) {
		final String name = e.getPlayer().getUniqueId().toString().substring(0, 16);

		ScoreboardTeam team = scoreboard.getTeam(name);
		if (team == null)
			team = scoreboard.createTeam(name);

		scoreboard.addPlayerToTeam(e.getPlayer().getName(), name);
		teams.put(e.getPlayer(), team);
	}

	@EventHandler(priority = EventPriority.MONITOR)
	public void onQuit(PlayerQuitEvent e) {
		removePlayerTeam(e.getPlayer());
		teams.remove(e.getPlayer());
	}

}
