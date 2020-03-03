package cn.winfxk.brassiere.team.myteam.mag;

import cn.winfxk.brassiere.form.FormBase;
import cn.winfxk.brassiere.team.Team;

import cn.nukkit.Player;
import cn.nukkit.form.response.FormResponse;

/**
 * @author Winfxk
 *
 */
public class TeamSetting extends FormBase {
	private Team team;

	/**
	 * 组队设置
	 *
	 * @param player
	 * @param team
	 */
	public TeamSetting(Player player, Team team) {
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
