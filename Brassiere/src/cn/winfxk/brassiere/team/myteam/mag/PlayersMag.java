package cn.winfxk.brassiere.team.myteam.mag;

import cn.nukkit.Player;
import cn.nukkit.form.response.FormResponse;
import cn.winfxk.brassiere.form.FormBase;
import cn.winfxk.brassiere.team.Team;

/**
 * @author Winfxk
 */
public class PlayersMag extends FormBase {
	private Team team;

	/**
	 * 队伍成员管理
	 *
	 * @param player
	 */
	public PlayersMag(Player player, Team team) {
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
