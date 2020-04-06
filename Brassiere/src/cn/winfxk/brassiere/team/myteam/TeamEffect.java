package cn.winfxk.brassiere.team.myteam;

import cn.nukkit.Player;
import cn.nukkit.form.response.FormResponse;
import cn.winfxk.brassiere.form.FormBase;
import cn.winfxk.brassiere.team.Team;

/**
 * 显示队伍的效果列表
 * 
 * @Createdate 2020/04/06 07:14:49<br/>
 * @author Winfxk
 */
public class TeamEffect extends FormBase {
	private Team team;

	public TeamEffect(Player player, Team team) {
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
