package cn.winfxk.brassiere.vip;

import cn.nukkit.Player;
import cn.nukkit.utils.Config;
import cn.winfxk.brassiere.Activate;
import cn.winfxk.brassiere.MyPlayer;

/**
 * @author Winfxk
 */
public class VipApi {
	private static VipMag vip;
	private static Activate ac;
	public static VipApi api;

	public VipApi(VipMag vip) {
		VipApi.vip = vip;
		VipApi.ac = vip.ac;
		api = this;
	}

	/**
	 * 判断某个玩家是不是一个VIP
	 *
	 * @param player 要判断检测的玩家
	 * @param ID     VIP的ID
	 * @return
	 */
	public static boolean isVip(Player player, String ID) {
		return isVip(player.getName(), ID);
	}

	/**
	 * 判断某个玩家是不是一个VIP
	 *
	 * @param player 要判断检测的玩家名称
	 * @return
	 */
	public static boolean isVip(Player player) {
		return isVip(player.getName());
	}

	/**
	 * 判断某个玩家是不是一个VIP
	 *
	 * @param player 要判断检测的玩家名称
	 * @return
	 */
	public static boolean isVip(String player) {
		if (ac.isPlayers(player)) {
			MyPlayer myPlayer = ac.getPlayers(player);
			if (myPlayer != null)
				return myPlayer.isVip();
		}
		if (MyPlayer.isPlayer(player)) {
			Config config = MyPlayer.getConfig(player);
			String vip = config.getString("Vip");
			return vip == null || vip.isEmpty() ? false : VipApi.vip.isVip(vip);
		}
		return false;
	}

	/**
	 * 判断某个玩家是不是一个VIP
	 *
	 * @param player 要判断检测的玩家名称
	 * @param ID     VIP的ID
	 * @return
	 */
	public static boolean isVip(String player, String ID) {
		if (vip.isVip(ID)) {
			if (ac.isPlayers(player)) {
				MyPlayer myPlayer = ac.getPlayers(player);
				if (myPlayer != null)
					return myPlayer.isVip(ID);
			}
			if (MyPlayer.isPlayer(player)) {
				Config config = MyPlayer.getConfig(player);
				String vip = config.getString("Vip");
				return vip == null || vip.isEmpty() ? false : vip.equals(ID);
			}
		}
		return false;
	}

	/**
	 * 获取一个玩家的VIP对象
	 *
	 * @param player
	 * @return
	 */
	public static Vip getVip(Player player) {
		return getVip(player.getName());
	}

	/**
	 * 获取一个玩家的VIP对象
	 *
	 * @param player
	 * @return
	 */
	public static Vip getVip(String player) {
		String ID;
		if (ac.isPlayers(player)) {
			MyPlayer myPlayer = ac.getPlayers(player);
			if (myPlayer != null) {
				ID = myPlayer.getVipID();
				if (ID == null || ID.isEmpty())
					return null;
				return ac.getVipMag().getVip(ID);
			}
		}
		ID = MyPlayer.getConfig(player).getString("Vip");
		if (ID == null || ID.isEmpty())
			return null;
		return ac.getVipMag().getVip(ID);
	}
}
