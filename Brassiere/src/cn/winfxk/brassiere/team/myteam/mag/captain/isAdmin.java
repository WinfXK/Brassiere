package cn.winfxk.brassiere.team.myteam.mag.captain;

import cn.nukkit.Player;
import cn.nukkit.form.response.FormResponse;
import cn.winfxk.brassiere.form.FormBase;
import cn.winfxk.brassiere.team.Team;

/**
 * @author Winfxk
 */
public class isAdmin extends FormBase {
	private Team team;

	/**
	 * 队长点击的玩家已经是管理员
	 *
	 * @param player
	 * @param team
	 */
	public isAdmin(Player player, Team team) {
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
