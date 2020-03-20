package cn.winfxk.brassiere;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cn.winfxk.brassiere.form.FormBase;
import cn.winfxk.brassiere.team.Team;
import cn.winfxk.brassiere.team.TeamApi;
import cn.winfxk.brassiere.tool.Tool;
import cn.winfxk.brassiere.vip.Vip;

import cn.nukkit.Player;
import cn.nukkit.utils.Config;

/**
 * @author Winfxk
 */
public class MyPlayer {
	private Activate ac;
	public Config config;
	private Player player;
	public FormBase form;
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
		config = getConfig(getName());
		config = ac.resCheck.Check(this);
		config.set("name", player.getName());
		config.save();
	}

	public void setPlayer(Player player) {
		this.player = player;
	}

	/**
	 * 判断玩家是否是VIP
	 *
	 * @return
	 */
	public boolean isVip() {
		String obj = config.getString("Vip");
		return obj != null && !obj.isEmpty();
	}

	/**
	 * 判断玩家是否是某个VIP用户
	 *
	 * @param vip
	 * @return
	 */
	public boolean isVip(String vip) {
		if (vip == null || vip.isEmpty())
			return false;
		return vip.equals(getVipID());
	}

	/**
	 * 得到玩家的VIP的ID
	 *
	 * @return
	 */
	public String getVipID() {
		return config.getString("Vip");
	}

	/**
	 * 给忘记生成一个加入队伍的申请记录
	 *
	 * @param team
	 * @return
	 */
	public MyPlayer addApplyFor(Team team) {
		List<String> list = config.getList("ApplyFor");
		if (!list.contains(team.getID()))
			list.add(team.getID());
		return this;
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
	public Team getTeam() {
		String ID = getTeamID();
		if (ID == null || ID.isEmpty())
			return null;
		return ac.teamMag.isTeam(ID) ? ac.teamMag.getTeam(ID) : null;
	}

	/**
	 * 返回玩家队伍的ID
	 *
	 * @return
	 */
	public String getTeamID() {
		String ID = config.getString("Team");
		return ID == null || ID.isEmpty() ? null : ID;
	}

	/**
	 * 判断玩家是否加入了一个组队
	 *
	 * @return
	 */
	public boolean isTeam() {
		String ID = getTeamID();
		if (ID == null || ID.isEmpty())
			return false;
		return ac.teamMag.isTeam(ID);
	}

	/**
	 * 判断玩家是否加入了一个组队
	 *
	 * @return
	 */
	public static boolean isTeam(String player) {
		return TeamApi.isJoinTeam(player);
	}

	/**
	 * 得到一个玩家的配置文件对象
	 *
	 * @param player 玩家名称
	 * @return
	 */
	public static Config getConfig(String player) {
		return new Config(getFile(player), Config.YAML);
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
	public Map<String, Object> getOnceJoined() {
		Object obj = config.get("OnceJoined");
		Map<String, Object> map = obj == null || !(obj instanceof Map) ? new HashMap<>()
				: (HashMap<String, Object>) obj;
		return map;
	}

	public String getName() {
		return player.getName();
	}

	/**
	 * 判断玩家的配置文件是否存在
	 *
	 * @param player
	 * @return
	 */
	public static boolean isPlayer(String player) {
		File file = getFile(player);
		return file.exists();
	}

	/**
	 * 清除玩家的入队申请
	 *
	 * @return
	 */
	public boolean clearApplyFor() {
		config.set("ApplyFor", new ArrayList<String>());
		config.save();
		return true;
	}

	/**
	 * 清除玩家的入队申请
	 *
	 * @param player
	 * @return
	 */
	public static boolean clearApplyFor(String player) {
		Config config = getConfig(player);
		config.set("ApplyFor", new ArrayList<String>());
		config.save();
		return true;
	}

	/**
	 * 给玩家添加一个进入队伍的记录
	 *
	 * @param team
	 * @return
	 */
	public static boolean addOnceJoined(String player, Team team) {
		Config config = getConfig(player);
		Map<String, Object> map = getOnceJoined(player);
		Object obj = map.get(team.getID());
		Map<String, Object> map2 = obj != null && obj instanceof Map ? (HashMap<String, Object>) obj : new HashMap<>();
		map2.put("ID", team.getID());
		map2.put("Name", team.getName());
		map2.put("Captain", team.getCaptain());
		map2.put("JoinDate", Tool.getDate() + " " + Tool.getTime());
		map2.put("QuitDate", null);
		map2.put("Count", Tool.ObjectToInt(map2.get("Count"), 0) + 1);
		map.put(team.getID(), map2);
		config.set("OnceJoined", map);
		config.save();
		return true;
	}

	/**
	 * 获取玩家加入过的队伍的记录
	 *
	 * @param player
	 * @return
	 */
	private static Map<String, Object> getOnceJoined(String player) {
		Object obj = getConfig(player).get("OnceJoined");
		Map<String, Object> map = obj == null || !(obj instanceof Map) ? new HashMap<>()
				: (HashMap<String, Object>) obj;
		return map;
	}

	/**
	 * 给玩家添加一个进入队伍的记录
	 *
	 * @param team
	 * @return
	 */
	public boolean addOnceJoined(Team team) {
		Map<String, Object> map = getOnceJoined();
		Object obj = map.get(team.getID());
		Map<String, Object> map2 = obj != null && obj instanceof Map ? (HashMap<String, Object>) obj : new HashMap<>();
		map2.put("ID", team.getID());
		map2.put("Name", team.getName());
		map2.put("Captain", team.getCaptain());
		map2.put("JoinDate", Tool.getDate() + " " + Tool.getTime());
		map2.put("QuitDate", null);
		map2.put("Count", Tool.ObjectToInt(map2.get("Count"), 0) + 1);
		map.put(team.getID(), map2);
		config.set("OnceJoined", map);
		config.save();
		return true;
	}

	/**
	 * 让玩家尝试加入一个队伍
	 *
	 * @param team
	 * @return
	 */
	public static boolean JoinTeam(String player, Team team) {
		if (team == null || isTeam(player) || !getFile(player).exists())
			return false;
		team.addPlayer(player);
		Config config = getConfig(player);
		config.set("Team", team.getID());
		config.save();
		return true;
	}

	/**
	 * 让玩家尝试加入一个队伍
	 *
	 * @param team
	 * @return
	 */
	public boolean JoinTeam(Team team) {
		if (team == null || isTeam())
			return false;
		team.addPlayer(getName());
		config.set("Team", team.getID());
		config.save();
		return true;
	}

	/**
	 * 将一条信息打包给玩家
	 *
	 * @param player
	 * @param Message
	 * @return 若玩家在线将返回True，否则返回False
	 */
	public static boolean sendMessage(String player, String Message) {
		if (player == null || Message == null || player.isEmpty() || Message.isEmpty() || !isPlayer(player))
			return false;
		if (Activate.getActivate().isPlayers(player)) {
			Activate.getActivate().getPlayers(player).player.sendMessage(Message);
			return true;
		}
		Config config = getConfig(player);
		Object obj = config.get("Message");
		List<Object> list = obj == null || !(obj instanceof List) ? new ArrayList<>() : (ArrayList<Object>) obj;
		list.add(Message);
		config.set("Message", list);
		config.save();
		return false;
	}

	/**
	 * 删除一个入队申请
	 *
	 * @param player
	 * @param team
	 * @return
	 */
	public static boolean remoeApplyFor(String player, Team team) {
		if (player == null || player.isEmpty() || team == null || !isPlayer(player))
			return false;
		if (Activate.getActivate().isPlayers(player)) {
			MyPlayer myPlayer = Activate.getActivate().getPlayers(player);
			if (myPlayer != null) {
				Object obj = myPlayer.config.get("ApplyFor");
				List<Object> list = obj != null && obj instanceof List ? (ArrayList<Object>) obj : new ArrayList<>();
				list.remove(team.getID());
				myPlayer.config.set("ApplyFor", list);
				return myPlayer.config.save();
			}
		}
		Config config = getConfig(player);
		Object obj = config.get("ApplyFor");
		List<Object> list = obj != null && obj instanceof List ? (ArrayList<Object>) obj : new ArrayList<>();
		list.remove(team.getID());
		config.set("ApplyFor", list);
		return config.save();
	}
}
