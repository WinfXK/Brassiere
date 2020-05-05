package cn.winfxk.brassiere.vip.joinmsg;

import java.util.Map;
import java.util.UUID;

import cn.nukkit.Player;
import cn.nukkit.Server;
import cn.winfxk.brassiere.Activate;
import cn.winfxk.brassiere.Message;

/**
 * @author Winfxk
 */
public abstract class JoinMsg {
	public Activate ac;
	public Message msg;
	public Map<UUID, Player> players;

	/**
	 * Vip玩家进服将会发送的方式基础类
	 */
	public JoinMsg() {
		ac = Activate.getActivate();
		msg = ac.getMessage();
	}

	/**
	 * 将进服会发送的消息发送出去
	 * 
	 * @param Msg
	 * @return
	 */
	public boolean send(String Msg) {
		players = Server.getInstance().getOnlinePlayers();
		for (Player p : players.values())
			out(p, msg.getText(Msg));
		return true;
	}

	/**
	 * 交给子类记成用于编写消息发送方式
	 * 
	 * @param player 要发送消息的玩家对象
	 * @param Msg    将会发送的消息内容
	 */
	public abstract void out(Player player, String Msg);
}
