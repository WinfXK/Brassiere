package cn.winfxk.brassiere.team;

import java.io.File;
import java.util.LinkedHashMap;

import cn.nukkit.Player;
import cn.nukkit.utils.Config;
import cn.winfxk.brassiere.Activate;
import cn.winfxk.brassiere.MyPlayer;

/**
 * 这个不是界面，这儿是管理各个队伍的交互中心
 *
 * @author Winfxk
 */
public class TeamMag {
	private Activate ac;
	private LinkedHashMap<String, Team> teams;

	public TeamMag(Activate activate) {
		ac = activate;
		teams = new LinkedHashMap<>();
	}

	/**
	 * 构建组队系统主页
	 *
	 * @param player
	 * @return
	 */
	public boolean makeMain(Player player) {
		MyPlayer myPlayer = ac.getPlayers(player.getName());
		myPlayer.makeBase = new TeamForm(player);
		return myPlayer.makeBase.MakeMain();
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
	 * 根据队伍ID获取一个队伍的配置文件所在位置的对象
	 *
	 * @param ID
	 * @return
	 */
	public File getTeamFile(String ID) {
		return new File(new File(ac.getPluginBase().getDataFolder(), Activate.TeamDirName), ID + ".yml");
	}
}