package cn.epicfx.winfxk.donthitme.team;

import cn.epicfx.winfxk.donthitme.MakeBase;
import cn.nukkit.Player;

/**
 * @author Winfxk
 */
public class TeamForm extends MakeBase {

	public TeamForm(Player player) {
		super(player);
	}

	@Override
	public boolean MakeMain() {
		return true;
	}

	@Override
	public boolean disMain() {
		return true;
	}
}
