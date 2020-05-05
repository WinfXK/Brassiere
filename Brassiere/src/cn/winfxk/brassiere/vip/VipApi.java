package cn.winfxk.brassiere.vip;

import cn.nukkit.AdventureSettings.Type;
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
	 * 删除一个玩家的VIP特权
	 * 
	 * @param player
	 * @return
	 */
	public static boolean remove(String player) {
		if (!VipApi.isVip(player))
			return false;
		Config config;
		if (ac.isPlayers(player)) {
			MyPlayer myPlayer = ac.getPlayers(player);
			myPlayer.getPlayer().getAdventureSettings().set(Type.ALLOW_FLIGHT, false);
			myPlayer.getPlayer().getAdventureSettings().update();
			config = myPlayer.getConfig();
		} else
			config = MyPlayer.getConfig(player);
		config.set("Vip", null);
		config.set("VipLevel", 0);
		config.set("VipTime", 0);
		config.set("VipSign", null);
		return config.save();
	}

	/**
	 * 获取玩家的剩余特权时长(小时)
	 * 
	 * @param player
	 * @return
	 */
	public static int getTime(String player) {
		if (!VipApi.isVip(player))
			return 0;
		Config config = ac.isPlayers(player) ? ac.getPlayers(player).getConfig() : MyPlayer.getConfig(player);
		return config.getInt("VipTime");
	}

	/**
	 * 获取一个玩家的经验点
	 * 
	 * @param player
	 * @return
	 */
	public static int getLevel(String player) {
		if (!isVip(player))
			return 0;
		Config config = ac.isPlayers(player) ? ac.getPlayers(player).getConfig() : MyPlayer.getConfig(player);
		return config.getInt("VipLevel");
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
	 * 设置一个玩家的特权为
	 * 
	 * @param player 要设置特权的玩家
	 * @param ID     特权的ID
	 * @param Time   要给与的时长
	 * @param Level  要给与的经验
	 * @return
	 */
	public static boolean setVip(Player player, String ID, int Time, int Level) {
		return setVip(player.getName(), ID, Time, Level);
	}

	/**
	 * 设置一个玩家的特权为
	 * 
	 * @param player 要设置特权的玩家
	 * @param ID     特权的ID
	 * @param Time   要给与的时长
	 * @param Level  要给与的经验
	 * @return
	 */
	public static boolean setVip(String player, String ID, int Time, int Level) {
		return setVip(player, ID, Time, Level, true);
	}

	/**
	 * 设置一个玩家的特权为
	 * 
	 * @param player   要设置特权的玩家
	 * @param ID       特权的ID
	 * @param Time     要给与的时长
	 * @param Level    要给与的经验
	 * @param isRepeat 是否和之前的叠加
	 * @return
	 */
	public static boolean setVip(Player player, String ID, int Time, int Level, boolean isRepeat) {
		return setVip(player.getName(), ID, Time, Level, isRepeat);
	}

	/**
	 * 设置一个玩家的特权为
	 * 
	 * @param player   要设置特权的玩家
	 * @param ID       特权的ID
	 * @param Time     要给与的时长
	 * @param Level    要给与的经验
	 * @param isRepeat 是否和之前的叠加
	 * @return
	 */
	public static boolean setVip(String player, String ID, int Time, int Level, boolean isRepeat) {
		Config config = ac.isPlayers(player) ? ac.getPlayers(player).getConfig() : MyPlayer.getConfig(player);
		config.set("Vip", ID);
		config.set("VipTime", isRepeat ? Time + config.getInt("VipTime") : Time);
		config.set("VipLevel", isRepeat ? Level + config.getInt("VipLevel") : Level);
		config.set("VipSign", null);
		return config.save();
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
