package cn.winfxk.brassiere;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import cn.nukkit.Player;
import cn.nukkit.item.Item;
import cn.nukkit.utils.Config;
import cn.winfxk.brassiere.form.FormBase;
import cn.winfxk.brassiere.team.Team;
import cn.winfxk.brassiere.team.TeamApi;
import cn.winfxk.brassiere.tool.Tool;
import cn.winfxk.brassiere.vip.Vip;

/**
 * @author Winfxk
 */
public class MyPlayer {
	private static Activate ac = Activate.getActivate();
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
		config = getConfig(getName());
		config = ac.resCheck.Check(this);
		if (isVip())
			vip = ac.getVipMag().getVip(config.getString("Vip"));
		config.set("name", player.getName());
		config.save();
	}

	/**
	 * 判断玩家是否拥有某个称号
	 * 
	 * @param Sign
	 * @return
	 */
	public boolean isPossess(String Sign) {
		if (Sign == null)
			return false;
		try {
			return config.getStringList("Signs").contains(Sign);
		} catch (Exception e) {
			return false;
		}
	}

	/**
	 * 判断玩家是否是管理员
	 * 
	 * @return
	 */
	public boolean isAdmin() {
		return ac.isAdmin(player);
	}

	/**
	 * 设置玩家称号
	 * 
	 * @param string
	 * @return
	 */
	public boolean setSign(String string) {
		config.set("useSign", string);
		return config.save() && getSign().equals(string);
	}

	public void setPlayer(Player player) {
		this.player = player;
	}

	/**
	 * 获取玩家的称号列表
	 * 
	 * @return
	 */
	public List<String> getSigns() {
		return config.getStringList("Signs");
	}

	/**
	 * 获取玩家正在使用的称号
	 * 
	 * @return
	 */
	public String getSign() {
		return config.get("useSign") == null ? null : config.getString("useSign");
	}

	/**
	 * 给一个VIP用户增加VIP经验
	 * 
	 * @param player
	 * @param Exp
	 * @return
	 */
	public static boolean addVipExp(String player, int Exp) {
		Config config = ac.isPlayers(player) ? ac.getPlayers(player).getConfig() : MyPlayer.getConfig(player);
		config.set("VipLevel", config.getInt("VipLevel") + Exp);
		return config.save();
	}

	/**
	 * 给一个VIP用户增加VIP经验
	 * 
	 * @param Exp
	 * @return
	 */
	public boolean addVipExp(int Exp) {
		config.set("VipLevel", config.getInt("VipLevel") + Exp);
		return config.save();
	}

	/**
	 * 判断玩家是否是VIP
	 *
	 * @return
	 */
	public boolean isVip() {
		return vip != null;
	}

	/**
	 * 获取玩家的Vip经验点
	 * 
	 * @return
	 */
	public int getVipExp() {
		return isVip() ? config.getInt("VipLevel") : 0;
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
		return vip == null ? null : vip.getID();
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
	 * 获取一个云端仓库内容
	 * 
	 * @return
	 */
	public Map<String, Map<String, Object>> getCloudStorage() {
		return (Map<String, Map<String, Object>>) config.get("CloudStorage");
	}

	/**
	 * 获取一个云端仓库内容
	 * 
	 * @param player
	 * @return
	 */
	public static Map<String, Map<String, Object>> getCloudStorage(String player) {
		return (Map<String, Map<String, Object>>) (ac.isPlayers(player) ? ac.getPlayers(player).getConfig()
				: getConfig(player)).get("CloudStorage");
	}

	/**
	 * 删除一个云端的物品
	 * 
	 * @param item
	 * @return
	 */
	public static boolean removeCloudStorage(String player, Item item) {
		Map<String, Map<String, Object>> map = getCloudStorage(player);
		Item i;
		int Count = item.getCount();
		Set<String> Keys = map.keySet();
		for (String Key : Keys) {
			i = Tool.loadItem(map.get(Key));
			if (i == null || i.getId() == 0) {
				map.remove(Key);
				continue;
			}
			if (item.equals(i, true, true))
				if (i.getCount() > Count) {
					i.setCount(i.getCount() - Count);
					map.put(Key, Tool.saveItem(i));
					Count = 0;
				} else {
					Count -= i.getCount();
					map.remove(Key);
				}
			if (Count <= 0)
				break;
		}
		Config config = ac.isPlayers(player) ? ac.getPlayers(player).getConfig() : getConfig(player);
		config.set("CloudStorage", map);
		return config.save() && Count == 0;
	}

	/**
	 * 删除一个云端的物品
	 * 
	 * @param item
	 * @return
	 */
	public int removeCloudStorage(Item item) {
		Map<String, Map<String, Object>> map = getCloudStorage();
		Item i;
		int Count = item.getCount();
		Set<String> Keys = map.keySet();
		for (String Key : Keys) {
			i = Tool.loadItem(map.get(Key));
			if (i == null || i.getId() == 0) {
				map.remove(Key);
				continue;
			}
			if (item.equals(i, true, true))
				if (i.getCount() > Count) {
					i.setCount(i.getCount() - Count);
					map.put(Key, Tool.saveItem(i));
					Count = 0;
				} else {
					Count -= i.getCount();
					map.remove(Key);
				}
			if (Count <= 0)
				break;
		}
		config.set("CloudStorage", map);
		return item.getCount() - Count;
	}

	/**
	 * 添加一个物品到云端仓库
	 * 
	 * @param item 要上传的物品
	 */
	public boolean addCloudStorage(Item item) {
		Map<String, Map<String, Object>> map = getCloudStorage();
		Item i;
		int Count = 0;
		Set<String> Keys = map.keySet();
		for (String Key : Keys) {
			i = Tool.loadItem(map.get(Key));
			if (i == null || i.getId() == 0) {
				map.remove(Key);
				continue;
			}
			if (i.equals(item, true, true)) {
				Count += i.getCount();
				map.remove(Key);
			}
		}
		item.setCount(Count + item.getCount());
		map.put(getCloudStorageKey(map, 1), Tool.saveItem(item));
		config.set("CloudStorage", map);
		return config.save();
	}

	/**
	 * 添加一个物品到云端仓库
	 * 
	 * @param player 要上传物品的玩家名
	 * @param item   要上传的物品
	 * @return
	 */
	public static boolean addCloudStorage(String player, Item item) {
		Map<String, Map<String, Object>> map = getCloudStorage(player);
		Item i;
		int Count = 0;
		Set<String> Keys = map.keySet();
		for (String Key : Keys) {
			i = Tool.loadItem(map.get(Key));
			if (i == null || i.getId() == 0) {
				map.remove(Key);
				continue;
			}
			if (i.equals(item, true, true)) {
				Count += i.getCount();
				map.remove(Key);
			}
		}
		item.setCount(Count + item.getCount());
		map.put(getCloudStorageKey(map, 1), Tool.saveItem(item));
		Config config = ac.isPlayers(player) ? ac.getPlayers(player).getConfig() : getConfig(player);
		config.set("CloudStorage", map);
		return config.save();
	}

	/**
	 * 返回一个云端物品的储存Key，这个Key将不会重复
	 * 
	 * @param map      云端仓库的内容
	 * @param JJLength
	 * @return
	 */
	private static String getCloudStorageKey(Map<String, Map<String, Object>> map, int JJLength) {
		String string = "";
		for (int i = 0; i < JJLength; i++)
			string += Tool.getRandString();
		if (map.containsKey(string))
			return getCloudStorageKey(map, JJLength++);
		return string;
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
		map2.put("Count", Tool.ObjToInt(map2.get("Count"), 0) + 1);
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
		map2.put("Count", Tool.ObjToInt(map2.get("Count"), 0) + 1);
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
