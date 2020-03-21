package cn.winfxk.brassiere.team.myteam.mag.captain;

import cn.winfxk.brassiere.form.FormBase;
import cn.winfxk.brassiere.team.Team;

import cn.nukkit.Player;
import cn.nukkit.form.response.FormResponse;

/**
 *
 * 2020年3月22日 上午1:31:04
 *
 * @author Winfxk
 */
public class Signpricefixing extends FormBase {
	private Team team;

	/**
	 * 队伍管理员设置当前队伍的称号价格
	 *
	 * @param player
	 */
	public Signpricefixing(Player player, Team team) {
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
