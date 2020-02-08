package cn.epicfx.winfxk.donthitme.team;

import cn.epicfx.winfxk.donthitme.form.FormBase;
import cn.nukkit.Player;
import cn.nukkit.form.response.FormResponse;

/**
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
