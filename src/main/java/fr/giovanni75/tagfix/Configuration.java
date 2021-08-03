package fr.giovanni75.tagfix;

import org.bukkit.configuration.file.FileConfiguration;

public class Configuration {

	/**
	 * The permission required to execute the main command.
	 * @see CommandTagFix
	 */
	public static String PERMISSION;

	/**
	 * The message which is displayed to players who don't have the above permission.
	 * @see CommandTagFix
	 */
	public static String PERMISSION_MESSAGE;

	/**
	 * The frequency (in ticks) at which tags are refreshed.
	 * @see fr.giovanni75.tagfix.InvisibilityTask
	 */
	public static int REFRESH_FREQUENCY;

	/**
	 * Loads the above fields from the config.yml file.
	 * Called when the plugin gets enabled or reloaded.
	 */
	public static void load() {
		final FileConfiguration config = InvisibilityTagFix.getInstance().getConfig();
		PERMISSION = config.getString("permission");
		PERMISSION_MESSAGE = config.getString("permission-message");
		REFRESH_FREQUENCY = config.getInt("refresh-frequency");
	}

}
