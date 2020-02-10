package cn.winfxk.brassiere.team.myteam;

import cn.nukkit.Player;
import cn.nukkit.form.response.FormResponse;
import cn.winfxk.brassiere.form.FormBase;
import cn.winfxk.brassiere.team.Team;

/**
 * 玩家所在队伍的详情
 *
 * @author Winfxk
 */
public class TeamDatails extends FormBase {
	private Team team;

	public TeamDatails(Player player, Team team) {
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
