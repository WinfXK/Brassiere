package cn.winfxk.brassiere.team;

import cn.winfxk.brassiere.Activate;
import cn.winfxk.brassiere.MyPlayer;

import cn.nukkit.utils.Config;

/**
 * @author Winfxk
 */
public class TeamApi {
	private static Activate ac;
	private static TeamMag mag;
	public static TeamApi api;

	/**
	 * 组对相关的API
	 *
	 * @param mag
	 */
	public TeamApi(TeamMag mag) {
		TeamApi.mag = mag;
		ac = Activate.getActivate();
		api = this;
	}

	/**
	 * 获取玩家所在的队伍
	 *
	 * @param player
	 * @return
	 */
	public static Team getTeam(String player) {
		if (ac.isPlayers(player)) {
			MyPlayer myPlayer = ac.getPlayers(player);
			if (myPlayer != null)
				return myPlayer.getTeam();
		}
		Config config = MyPlayer.getConfig(player);
		String teamID = config.getString("Team");
		if (teamID == null || teamID.isEmpty() || !mag.isTeam(teamID))
			return null;
		return mag.getTeam(teamID);
	}

	/**
	 * 判断玩家是否在一个队伍里面
	 *
	 * @param player
	 * @return
	 */
	public static boolean isJoinTeam(String player) {
		if (player == null || player.isEmpty())
			return false;
		if (ac.isPlayers(player)) {
			MyPlayer myPlayer = ac.getPlayers(player);
			if (myPlayer != null)
				return myPlayer.isTeam();
		}
		Config config = MyPlayer.getConfig(player);
		String teamID = config.getString("Team");
		if (teamID == null || teamID.isEmpty() || !mag.isTeam(teamID))
			return false;
		return mag.getTeam(teamID).exist(player);
	}
}
