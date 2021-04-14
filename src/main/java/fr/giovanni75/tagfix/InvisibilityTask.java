package fr.giovanni75.tagfix;

import net.minecraft.server.v1_8_R3.ScoreboardTeam;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitRunnable;

import static net.minecraft.server.v1_8_R3.ScoreboardTeamBase.EnumNameTagVisibility.ALWAYS;
import static net.minecraft.server.v1_8_R3.ScoreboardTeamBase.EnumNameTagVisibility.NEVER;

public class InvisibilityTask extends BukkitRunnable {

	private boolean hasInvisibility(Player p) {
		for (PotionEffect effect : p.getActivePotionEffects())
			if (effect.getType() == PotionEffectType.INVISIBILITY)
				return true;
		return false;
	}

	@Override
	public void run() {
		for (Player p : Bukkit.getOnlinePlayers()) {
			final ScoreboardTeam team = InvisibilityTagFix.board.getTeam(p.getUniqueId().toString().substring(0, 16));
			if (team != null) // Might not have been loaded yet
				team.setNameTagVisibility(hasInvisibility(p) ? NEVER : ALWAYS);
		}
	}

}
