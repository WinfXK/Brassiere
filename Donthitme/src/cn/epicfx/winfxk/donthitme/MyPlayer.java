package cn.epicfx.winfxk.donthitme;

import java.io.File;

import cn.nukkit.Player;
import cn.nukkit.utils.Config;

/**
 * @author Winfxk
 */
public class MyPlayer {
	private Activate ac;
	public Config config;
	private Player player;
	public MakeBase makeBase;

	/**
	 * 记录存储玩家的一些数据
	 *
	 * @param player
	 */
	public MyPlayer(Player player) {
		this.player = player;
		ac = Activate.getActivate();
		config = getConfig(player.getName());
		config.set("name", player.getName());
	}

	public Player getPlayer() {
		return player;
	}

	/**
	 * 获取逗比玩家的金币数量
	 *
	 * @return
	 */
	public double getMoney() {
		return ac.getEconomy().getMoney(player);
	}

	/**
	 * 获取逗比玩家的金币数量
	 *
	 * @return
	 */
	public static double getMoney(String player) {
		return Activate.getActivate().getEconomy().getMoney(player);
	}

	public Config getConfig() {
		return config;
	}

	/**
	 * 增加游戏局数
	 *
	 * @return
	 */
	public MyPlayer addGames() {
		config.set("游戏局数", config.getInt("游戏局数") + 1);
		config.save();
		return this;
	}

	/**
	 * 得到一个玩家的配置文件对象
	 *
	 * @param player 玩家名称
	 * @return
	 */
	public static Config getConfig(String player) {
		return Activate.getActivate().resCheck.Check(new Config(getFile(player), Config.YAML));
	}

	/**
	 * 得到一个玩家配置文件的文件对象
	 *
	 * @return
	 */
	public File getFile() {
		return new File(new File(ac.getPluginBase().getDataFolder(), Activate.PlayerDataDirName),
				player.getName() + ".yml");
	}

	/**
	 * 得到一个玩家配置文件的文件对象
	 *
	 * @param player 玩家名称
	 * @return
	 */
	public static File getFile(String player) {
		return new File(new File(Activate.getActivate().getPluginBase().getDataFolder(), Activate.PlayerDataDirName),
				player + ".yml");
	}
}
