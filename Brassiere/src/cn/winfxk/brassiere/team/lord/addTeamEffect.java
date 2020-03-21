package cn.winfxk.brassiere.team.lord;

import cn.winfxk.brassiere.form.FormBase;

import cn.nukkit.Player;
import cn.nukkit.form.response.FormResponse;

/**
 *
 * 2020年3月22日 上午1:29:53
 *
 * @author Winfxk
 */
public class addTeamEffect extends FormBase {

	/**
	 * 服主设定队伍可以购买哪些Buff作为队伍Buff
	 *
	 * @param player
	 */
	public addTeamEffect(Player player) {
		super(player);
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
