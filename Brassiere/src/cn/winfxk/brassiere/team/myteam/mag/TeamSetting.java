package cn.winfxk.brassiere.team.myteam.mag;

import cn.nukkit.Player;
import cn.nukkit.form.response.FormResponse;
import cn.winfxk.brassiere.form.FormBase;

/**
 * @author Winfxk
 *
 */
public class TeamSetting extends FormBase {
	/**
	 * 组队设置
	 *
	 * @param player
	 */
	public TeamSetting(Player player) {
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
