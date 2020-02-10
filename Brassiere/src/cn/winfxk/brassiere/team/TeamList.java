package cn.winfxk.brassiere.team;

import cn.nukkit.Player;
import cn.nukkit.form.response.FormResponse;
import cn.winfxk.brassiere.form.FormBase;

/**
 * 用于显示当前服务器已经有了的队伍列表
 *
 * @author Winfxk
 */
public class TeamList extends FormBase {
	public TeamList(Player player) {
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
