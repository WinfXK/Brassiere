package cn.winfxk.brassiere.team;

import cn.nukkit.Player;
import cn.nukkit.form.response.FormResponse;
import cn.winfxk.brassiere.form.FormBase;

/**
 * @author Winfxk
 */
public class TeamEffectShop extends FormBase {
	/**
	 * 队伍Buff商店
	 *
	 * @param player
	 */
	public TeamEffectShop(Player player) {
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
