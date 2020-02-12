package cn.winfxk.brassiere.team.myteam.mag;

import cn.nukkit.Player;
import cn.nukkit.form.response.FormResponse;
import cn.winfxk.brassiere.form.FormBase;

/**
 * @author Winfxk
 */
public class TeamApplyFor extends FormBase {
	/**
	 * 设置队伍的入队请求
	 *
	 * @param player
	 */
	public TeamApplyFor(Player player) {
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
