package cn.epicfx.winfxk.donthitme.vip.joinmsg;

import cn.nukkit.Player;

/**
 * @author Winfxk
 */
public class Tip extends JoinMsg {
	@Override
	public void out(Player player, String Msg) {
		player.sendTip(Msg);
	}
}
