package cn.winfxk.brassiere.team.myteam.mag;

import cn.nukkit.Player;
import cn.nukkit.form.response.FormResponse;
import cn.winfxk.brassiere.form.FormBase;

/**
 * @author Winfxk
 */
public class setAdmin extends FormBase {
	/**
	 * 设置或取消服务器管理员
	 *
	 * @param player
	 */
	public setAdmin(Player player) {
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
