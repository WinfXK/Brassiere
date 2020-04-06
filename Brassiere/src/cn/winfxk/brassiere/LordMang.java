package cn.winfxk.brassiere;

import cn.winfxk.brassiere.form.FormBase;

import cn.nukkit.Player;
import cn.nukkit.form.response.FormResponse;

/**
 *
 * 2020年3月22日 上午1:44:07
 *
 * @author Winfxk
 */
public class LordMang extends FormBase {
	/**
	 * 对服务器管理员显示哪些可以特殊更改的配置
	 *
	 * @param player
	 */
	public LordMang(Player player) {
		super(player);
	}

	/**
	 * 即将完成{@linkplain cn.winfxk.brassiere.team.lord.TeamEffectMag addTeamEffect}
	 */
	@Override
	public boolean MakeMain() {
		return false;
	}

	@Override
	public boolean disMain(FormResponse data) {
		return false;
	}
}
