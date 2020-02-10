package cn.winfxk.brassiere.team.myteam;

import cn.nukkit.Player;
import cn.nukkit.form.response.FormResponse;
import cn.winfxk.brassiere.form.FormBase;
import cn.winfxk.brassiere.team.Team;

/**
 * 队长或管理员管理自己的队伍
 *
 * @author Winfxk
 */
public class TeamManage extends FormBase {
	private boolean isCaptain;
	private Team team;

	/**
	 * 玩家管理队伍的界面
	 *
	 * @param player
	 */
	public TeamManage(Player player, Team team) {
		super(player);
		isCaptain = team.isCaptain(player);
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
