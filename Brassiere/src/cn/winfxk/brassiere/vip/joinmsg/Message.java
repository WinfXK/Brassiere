package cn.winfxk.brassiere.vip.joinmsg;

import cn.nukkit.Player;

/**
 * @author Winfxk
 */
public class Message extends JoinMsg {
	@Override
	public void out(Player player, String Msg) {
		player.sendMessage(Msg);
	}
}
