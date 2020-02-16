package cn.winfxk.brassiere.team.myteam.mag.captain;

import cn.nukkit.Player;
import cn.nukkit.form.response.FormResponse;
import cn.winfxk.brassiere.form.FormBase;
import cn.winfxk.brassiere.team.Team;

/**
 * @author Winfxk
 */
public class notAdmin extends FormBase {
	private Team team;

	/**
	 * 队长管理管理员列表页点击的玩家不是管理员
	 *
	 * @param player
	 */
	public notAdmin(Player player, Team team) {
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
