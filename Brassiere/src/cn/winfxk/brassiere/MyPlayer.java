package cn.winfxk.brassiere;

import java.io.File;
import java.util.List;

import cn.nukkit.Player;
import cn.nukkit.utils.Config;
import cn.winfxk.brassiere.form.FormBase;
import cn.winfxk.brassiere.team.Team;
import cn.winfxk.brassiere.vip.Vip;

/**
 * @author Winfxk
 */
public class MyPlayer {
	private Activate ac;
	public Config config;
	private Player player;
	public FormBase makeBase;
	public Vip vip;
	public int ID = 0;

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
	 * 获取玩家加入的队伍
	 *
	 * @return
	 */
	public Team geTeam() {
		String ID = config.getString("Team");
		if (ID == null || ID.isEmpty())
			return null;
		return ac.teamMag.isTeam(ID) ? ac.teamMag.getTeam(ID) : null;
	}

	/**
	 * 判断玩家是否加入了一个组队
	 *
	 * @return
	 */
	public boolean isTeam() {
		String ID = config.getString("Team");
		if (ID == null || ID.isEmpty())
			return false;
		return ac.teamMag.isTeam(ID);
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

	/**
	 * 获取玩家曾经加入过的队伍
	 *
	 * @return
	 */
	public List<String> getOnceJoined() {
		return config.getList("OnceJoined");
	}

	public String getName() {
		return player.getName();
	}
}
