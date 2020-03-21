package cn.winfxk.brassiere.team;

import java.io.File;
import java.util.LinkedHashMap;

import cn.winfxk.brassiere.Activate;
import cn.winfxk.brassiere.MyPlayer;

import cn.nukkit.Player;
import cn.nukkit.utils.Config;

/**
 * 这个不是界面，这儿是管理各个队伍的交互中心
 *
 * @author Winfxk
 */
public class TeamMag {
	private Activate ac;
	private LinkedHashMap<String, Team> teams;
	private Config EffectConfig;

	/**
	 * 队伍交互系统
	 *
	 * @param activate
	 */
	public TeamMag(Activate activate) {
		this.ac = activate;
		teams = new LinkedHashMap<>();
		EffectConfig = new Config(new File(activate.getPluginBase().getDataFolder(), Activate.TeamEffectName),
				Config.YAML);
	}

	/**
	 *
	 * @param player
	 * @return
	 */
	public TeamMag isLoad(MyPlayer myPlayer) {
		if (myPlayer == null)
			return this;
		String ID = myPlayer.getTeamID();
		if (ID == null || ID.isEmpty() || !myPlayer.isTeam() || teams.containsKey(ID))
			return this;
		teams.put(ID, new Team(ID, getTeamFile(ID)));
		return this;
	}

	/**
	 * 存储队伍队长可以购买的Buff
	 *
	 * @return
	 */
	public Config getEffectConfig() {
		return EffectConfig;
	}

	/**
	 * 构建组队系统主页
	 *
	 * @param player
	 * @return
	 */
	public boolean makeMain(Player player) {
		MyPlayer myPlayer = ac.getPlayers(player.getName());
		myPlayer.form = new TeamForm(player);
		return myPlayer.form.MakeMain();
	}

	/**
	 * 判断一个队伍是否存在
	 *
	 * @param ID
	 * @return
	 */
	public boolean isTeam(String ID) {
		return teams.containsKey(ID);
	}

	/**
	 * 根据队伍ID获取一个队伍对象
	 *
	 * @param ID
	 * @return
	 */
	public Team getTeam(String ID) {
		return teams.get(ID);
	}

	/**
	 * 获取所有队伍的数据
	 *
	 * @return
	 */
	public LinkedHashMap<String, Team> getTeams() {
		return new LinkedHashMap<>(teams);
	}

	/**
	 * 重载队伍数据
	 *
	 * @return
	 */
	public TeamMag reload() {
		for (MyPlayer myPlayer : ac.getPlayers().values())
			isLoad(myPlayer);
		return this;
	}

	/**
	 * 根据队伍ID获取一个队伍的配置文件对象
	 *
	 * @param ID
	 * @return
	 */
	public Config getConfig(String ID) {
		return new Config(getTeamFile(ID), Config.YAML);
	}

	/**
	 * 从数据库卸载一个已经加载的队伍对象
	 *
	 * @param ID
	 * @return
	 */
	public void remove(String ID) {
		teams.remove(ID);
	}

	/**
	 * 根据队伍ID获取一个队伍的配置文件所在位置的对象
	 *
	 * @param ID
	 * @return
	 */
	public File getTeamFile(String ID) {
		return new File(new File(ac.getPluginBase().getDataFolder(), Activate.TeamDirName), ID + ".yml");
	}
}
