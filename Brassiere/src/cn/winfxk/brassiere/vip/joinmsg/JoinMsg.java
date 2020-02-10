package cn.winfxk.brassiere.vip.joinmsg;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
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

	public JoinMsg() {
		ac = Activate.getActivate();
		msg = ac.getMessage();
	}

	public boolean send(String Msg) {
		return send(Msg, new ArrayList<String>(), new ArrayList<>());
	}

	public boolean send(String Msg, String[] key, Object[] dat) {
		return send(Msg, Arrays.asList(key), Arrays.asList(dat));
	}

	public boolean send(String Msg, List<String> key, List<Object> dat) {
		players = Server.getInstance().getOnlinePlayers();
		for (Player p : players.values())
			out(p, msg.getText(Msg, key, dat));
		return true;
	}

	public abstract void out(Player player, String Msg);
}
