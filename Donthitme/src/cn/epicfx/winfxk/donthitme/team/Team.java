package cn.epicfx.winfxk.donthitme.team;

import cn.epicfx.winfxk.donthitme.Activate;
import cn.epicfx.winfxk.donthitme.MyPlayer;
import cn.nukkit.Player;

/**
 * @author Winfxk
 */
public class Team {
	private Activate ac;

	public Team(Activate activate) {
		ac = activate;
	}

	public boolean makeMain(Player player) {
		MyPlayer myPlayer = ac.getPlayers(player.getName());
		myPlayer.makeBase = new TeamForm(player);
		return myPlayer.makeBase.MakeMain();
	}
}
