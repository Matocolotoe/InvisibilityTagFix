package fr.giovanni75.tagfix;

import net.minecraft.server.v1_8_R3.MobEffect;
import net.minecraft.server.v1_8_R3.ScoreboardTeam;
import net.minecraft.server.v1_8_R3.ScoreboardTeamBase;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftPlayer;
import org.bukkit.scheduler.BukkitRunnable;

public class InvisibilityTask extends BukkitRunnable {

	private static InvisibilityTask task = new InvisibilityTask();

	private static boolean hasInvisibility(CraftPlayer p) {
		for (MobEffect effect : p.getHandle().getEffects())
			if (effect.getEffectId() == 14)
				return true;
		return false;
	}

	public static void load(int period) {
		try {
			task.cancel();
		} catch (IllegalArgumentException | IllegalStateException ignored) {
			// Task wasn't running before
		}
		(task = new InvisibilityTask()).runTaskTimerAsynchronously(InvisibilityTagFix.getInstance(), 0, period);
	}

	@Override
	public void run() {
		for (CraftPlayer p : InvisibilityTagFix.getCurrentServer().getOnlinePlayers()) {
			final ScoreboardTeam team = InvisibilityTagFix.getTeam(p);
			if (team != null) // Might not have been loaded yet
				team.setNameTagVisibility(hasInvisibility(p) ? ScoreboardTeamBase.EnumNameTagVisibility.NEVER : ScoreboardTeamBase.EnumNameTagVisibility.ALWAYS);
		}
	}

}
