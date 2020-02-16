package cn.winfxk.brassiere.team.myteam.mag.captain;

import cn.nukkit.Player;
import cn.nukkit.form.response.FormResponse;
import cn.winfxk.brassiere.form.FormBase;
import cn.winfxk.brassiere.team.Team;

/**
 * @author Winfxk
 */
public class DissolveTeam extends FormBase {
	private Team team;

	/**
	 * 解散队伍
	 *
	 * @param player
	 */
	public DissolveTeam(Player player, Team team) {
		super(player);
		this.team = team;
	}

	@Override
	public boolean MakeMain() {
		return false;
	}

	@Override
	public boolean disMain(FormResponse data) {
		return false;
	}
}
