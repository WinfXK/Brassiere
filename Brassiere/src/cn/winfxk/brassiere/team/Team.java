package cn.winfxk.brassiere.team;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import cn.winfxk.brassiere.Activate;
import cn.winfxk.brassiere.MyPlayer;
import cn.winfxk.brassiere.money.MyEconomy;
import cn.winfxk.brassiere.tool.Effectrow;
import cn.winfxk.brassiere.tool.Tool;

import cn.nukkit.Player;
import cn.nukkit.potion.Effect;
import cn.nukkit.utils.Config;

/**
 * 每个队伍的数据
 *
 * @author Winfxk
 */
public class Team {
	private Activate ac;
	/**
	 * 队伍的ID
	 */
	private String ID;
	/**
	 * 队伍的名称
	 */
	private String Name;
	/**
	 * 声望
	 */
	private int Prestige;
	/**
	 * 公会金库
	 */
	private double Money;
	/**
	 * 队伍可容纳人数
	 */
	private int MaxCounts;
	/**
	 * 队伍仓库大小
	 */
	private int MaxShopItem;
	/**
	 * 队伍申请
	 */
	private Map<String, Map<String, Object>> ApplyFor;
	/**
	 * 允许玩家获取队伍称号
	 */
	private boolean AllowedSign;
	/**
	 * 允许玩家使用队伍商城
	 */
	private boolean AllowedShop;
	/**
	 * 允许玩家申请加入
	 */
	private boolean AllowedJoin;
	/**
	 * 允许玩家加入聊天室
	 */
	private boolean AllowedChat;
	/**
	 * 允许玩家上架物品到队伍商城
	 */
	private boolean AllowedMakeShop;
	/**
	 * 允许玩家享用队伍增益(如果有)
	 */
	private boolean AllowedGain;
	/**
	 * 队伍的配置文件对象
	 */
	private Config config;
	/**
	 * 队伍的文件对象
	 */
	private File file;
	/**
	 * 队伍中有的玩家列表
	 */
	private Map<String, Map<String, Object>> Players = new HashMap<>();
	/**
	 * 队伍的管理员列表
	 */
	private List<String> Admins = new ArrayList<>();
	/**
	 * 队长
	 */
	private String Captain;
	/**
	 * 队伍聊天信息
	 */
	private Map<String, Object> Message;
	/**
	 * 入队资费
	 */
	private double JoinTariff;
	/**
	 * 如对资费需要的币种
	 */
	private MyEconomy JoinTariffEconomy;
	/**
	 * 队伍商城
	 */
	private Map<String, Object> Shop;
	/**
	 * 队伍拥有的效果加成
	 */
	private List<Effect> Effects;
	/**
	 * 是否允许队员之间互相公鸡
	 */
	private boolean AllowedPVP;
	/**
	 * 队伍的内容介绍
	 */
	private String Content;

	/**
	 * 存储一个队伍的数据
	 *
	 * @param ID
	 * @param file
	 */
	public Team(String ID, File file) {
		this.file = file;
		this.ID = ID;
		Object obj;
		int EffectID, EffectLevel;
		Effect effect;
		Map<?, ?> map;
		config = new Config(file, Config.YAML);
		config.set("ID", ID);
		Name = config.getString("Name");
		Captain = config.getString("Captain");
		Prestige = config.getInt("Prestige");
		Money = config.getDouble("Money");
		MaxCounts = config.getInt("MaxCounts");
		MaxShopItem = config.getInt("MaxShopItem");
		AllowedJoin = config.getBoolean("AllowedJoin");
		AllowedChat = config.getBoolean("AllowedChat");
		AllowedMakeShop = config.getBoolean("AllowedMakeShop");
		AllowedShop = config.getBoolean("AllowedShop");
		AllowedGain = config.getBoolean("AllowedGain");
		AllowedSign = config.getBoolean("AllowedSign");
		Admins = config.getList("Admin");
		obj = config.get("Players");
		Players = obj != null && obj instanceof Map ? (HashMap<String, Map<String, Object>>) obj : new HashMap<>();
		obj = config.get("Effects");
		Effects = new ArrayList<>();
		map = obj != null && obj instanceof Map ? (HashMap<Object, Object>) obj : new HashMap<>();
		for (Entry<?, ?> entry : map.entrySet()) {
			EffectID = Tool.ObjectToInt(entry.getKey());
			EffectLevel = Tool.ObjectToInt(entry.getValue());
			effect = Effectrow.getEffect(EffectID);
			effect.setAmplifier((EffectLevel >= 0 ? EffectLevel : 0) <= 5 ? EffectLevel : 5);
			Effects.add(effect);
		}
		obj = config.get("Shop");
		Shop = obj != null && obj instanceof Map ? (HashMap<String, Object>) obj : new HashMap<>();
		obj = config.get("Message");
		Message = obj != null && obj instanceof Map ? (HashMap<String, Object>) obj : new HashMap<>();
		obj = config.get("ApplyFor");
		ApplyFor = obj != null && obj instanceof Map ? (HashMap<String, Map<String, Object>>) obj : new HashMap<>();
		JoinTariff = config.getDouble("JoinTariff");
		JoinTariffEconomy = ac.getEconomyManage().getEconomy(config.getString("JoinTariffEconomy"));
		AllowedPVP = config.getBoolean("AllowedPVP");
		Content = config.getString("Content");
	}

	/**
	 * 是否允许队员之间互相公鸡
	 */
	public boolean isAllowedPVP() {
		return AllowedPVP;
	}

	/**
	 * 队伍拥有的加成效果
	 *
	 * @return
	 */
	public List<Effect> getEffects() {
		return Effects;
	}

	/**
	 * 获取组队的文件对象
	 *
	 * @return
	 */
	public File getFile() {
		return file;
	}

	/**
	 * 获取队伍的ID
	 *
	 * @return
	 */
	public String getID() {
		return ID;
	}

	/**
	 * 队伍的内容介绍
	 *
	 * @return
	 */
	public String getContent() {
		return getContent(null);
	}

	/**
	 * 队伍的内容介绍
	 *
	 * @return
	 */
	public String getContent(Player player) {
		return ac.getMessage().getText(Content,
				new String[] { "{Captain}", "{TeamID}", "{TeamName}", "{TeamSize}", "{TeamMaxCount}", "{Player}",
						"{Money}", "{MaxShopItem}", "{ShopItem}" },
				new Object[] { getCaptain(), getID(), getName(), getMaxCounts(), player == null ? "" : player.getName(),
						player == null ? 0 : MyPlayer.getMoney(player.getName()), getMaxShopItem(), getShop().size() });
	}

	/**
	 * 返回队伍的人数
	 *
	 * @return
	 */
	public int size() {
		return Players.size();
	}

	/**
	 * 队伍的内容介绍
	 *
	 * @return
	 */
	public String getreContent() {
		return Content;
	}

	/**
	 * 获取队伍的配置文件对象
	 *
	 * @return
	 */
	public Config getConfig() {
		return config;
	}

	/**
	 * 保存队伍的数据
	 *
	 * @return
	 */
	public boolean save() {
		return config.save();
	}

	/**
	 * 队伍的管理员列表
	 *
	 * @return
	 */
	public List<String> getAdmins() {
		return Admins;
	}

	/**
	 * 队长
	 *
	 * @return
	 */
	public String getCaptain() {
		return Captain;
	}

	/**
	 * 判断一个逗比是不是队长
	 *
	 * @param player
	 * @return
	 */
	public boolean isCaptain(Player player) {
		return player.getName().equals(Captain);
	}

	/**
	 * 判断一个逗比是不是队伍管理员
	 *
	 * @param player
	 * @return
	 */
	public boolean isAdmin(Player player) {
		return Admins.contains(player.getName()) || isCaptain(player);
	}

	/**
	 * 判断一个逗比是不是队伍管理员
	 *
	 * @param player
	 * @return
	 */
	public boolean isAdmin(String player) {
		return Admins.contains(player) || isCaptain(player);
	}

	/**
	 * 判断一个逗比是不是队长
	 *
	 * @param player
	 * @return
	 */
	public boolean isCaptain(String player) {
		return player.equals(Captain);
	}

	/**
	 * 队伍中有的玩家列表
	 *
	 * @return
	 */
	public Map<String, Map<String, Object>> getPlayers() {
		return Players;
	}

	/**
	 * 取得某个玩家的数据
	 *
	 * @param name
	 * @return
	 */
	public Map<String, Object> getPlayer(String name) {
		return Players.get(name);
	}

	/**
	 * 取得某个玩家的数据
	 *
	 * @param name
	 * @return
	 */
	public TPlayerdata getPlayerdata(String name) {
		return new TPlayerdata(getPlayer(name), this);
	}

	/**
	 * 队伍名称
	 *
	 * @return
	 */
	public String getName() {
		return Name;
	}

	/**
	 * 允许玩家加入聊天室
	 *
	 * @return
	 */
	public boolean isAllowedChat() {
		return AllowedChat;
	}

	/**
	 * 允许玩家享用队伍增益(如果有)
	 *
	 * @return
	 */
	public boolean isAllowedGain() {
		return AllowedGain;
	}

	/**
	 * 允许玩家申请加入
	 *
	 * @return
	 */
	public boolean isAllowedJoin() {
		return AllowedJoin;
	}

	/**
	 * 允许玩家上架物品到队伍商城
	 *
	 * @return
	 */
	public boolean isAllowedMakeShop() {
		return AllowedMakeShop;
	}

	/**
	 * 允许玩家使用队伍商城
	 *
	 * @return
	 */
	public boolean isAllowedShop() {
		return AllowedShop;
	}

	/**
	 * 允许玩家获取队伍称号
	 *
	 * @return
	 */
	public boolean isAllowedSign() {
		return AllowedSign;
	}

	/**
	 * 入队申请
	 *
	 * @return
	 */
	public Map<String, Map<String, Object>> getApplyFor() {
		return ApplyFor;
	}

	/**
	 * 队伍中是否存在一个玩家
	 *
	 * @param player
	 * @return
	 */
	public boolean isPlayer(Player player) {
		return isPlayer(player.getName());
	}

	/**
	 * 队伍中是否存在一个玩家
	 *
	 * @param player
	 * @return
	 */
	public boolean isPlayer(String player) {
		return Players.containsKey(player);
	}

	/**
	 * 删除队伍里面的一个玩家的入队生亲
	 *
	 * @param player
	 * @return
	 */
	public Team removeApplyFor(String player) {
		if (ApplyFor.containsKey(player))
			ApplyFor.remove(player);
		config.set("ApplyFor", ApplyFor);
		config.save();
		return this;
	}

	/**
	 * 判断一个队伍是否已满
	 *
	 * @return
	 */
	public boolean isMaxPlayer() {
		return MaxCounts <= Players.size();
	}

	/**
	 * 入队资费
	 *
	 * @return
	 */
	public double getJoinTariff() {
		return JoinTariff;
	}

	/**
	 * 入队资费所需币种
	 *
	 * @return
	 */
	public MyEconomy getJoinTariffEconomy() {
		return JoinTariffEconomy;
	}

	/**
	 * 队伍人数上限
	 *
	 * @return
	 */
	public int getMaxCounts() {
		return MaxCounts;
	}

	/**
	 * 队伍商城数量上限
	 *
	 * @return
	 */
	public int getMaxShopItem() {
		return MaxShopItem;
	}

	/**
	 * 队伍聊天室
	 *
	 * @return
	 */
	public Map<String, Object> getMessage() {
		return Message;
	}

	/**
	 * 队伍金库
	 *
	 * @return
	 */
	public double getMoney() {
		return Money;
	}

	/**
	 * 队伍声望
	 *
	 * @return
	 */
	public int getPrestige() {
		return Prestige;
	}

	/**
	 * 队伍商城
	 *
	 * @return
	 */
	public Map<String, Object> getShop() {
		return Shop;
	}

	/**
	 * 增加入队申请
	 *
	 * @param player
	 * @return
	 */
	public Team sendApplyFor(MyPlayer player) {
		Map<String, Object> map = new HashMap<>();
		map.put("Player", player.getName());
		map.put("Date", Tool.getDate() + " " + Tool.getTime());
		map.put("OnceJoined", player.getOnceJoined());
		ApplyFor.put(player.getName(), map);
		player.addApplyFor(this);
		return this;
	}

	/**
	 * 玩家是否申请加入过该队
	 *
	 * @param player
	 * @return
	 */
	public boolean isApplyFor(Player player) {
		return ApplyFor.containsKey(player.getName());
	}

	/**
	 * 让玩家加入队伍
	 *
	 * @param player
	 * @return
	 */
	public boolean addPlayer(String player) {
		Map<String, Object> map = new HashMap<>();
		map.put("name", player);
		// 玩家的身份
		map.put("identity", "player");
		map.put("JoinDate", Tool.getDate() + " " + Tool.getTime());
		map.put("Prestige", ac.getConfig().getInt("玩家初始声望"));
		Players.put(player, map);
		config.set("Players", Players);
		config.save();
		return true;
	}

	/**
	 * 清空队伍的所有申请
	 *
	 * @return
	 */
	public boolean clearApplyFor() {
		for (String ike : ApplyFor.keySet()) {
			MyPlayer.sendMessage(ike,
					ac.getMessage().getSun("Team", "JoinTeam", "JoinFalse",
							new String[] { "{Player}", "{Money}", "{TeamID}", "{TeamName}" },
							new Object[] { ike, MyPlayer.getMoney(ike), getID(), getName() }));
			MyPlayer.remoeApplyFor(ike, this);
		}
		ApplyFor.clear();
		config.set("ApplyFor", ApplyFor);
		config.save();
		return true;
	}

	/**
	 * 同意一个玩家的入队请求
	 *
	 * @param player
	 * @return
	 */
	public boolean acceptApplyFor(String player) {
		if (Players.size() >= MaxCounts)
			return false;
		if (player == null || player.isEmpty() || !MyPlayer.isPlayer(player))
			return false;
		if (ApplyFor.containsKey(player)) {
			if (MyPlayer.isTeam(getID()))
				return false;
			ApplyFor.remove(player);
			config.set("ApplyFor", ApplyFor);
			config.save();
			if (ac.isPlayers(player)) {
				MyPlayer myPlayer = ac.getPlayers(player);
				if (myPlayer != null) {
					myPlayer.addOnceJoined(this);
					myPlayer.clearApplyFor();
					myPlayer.JoinTeam(this);
					myPlayer.getPlayer()
							.sendMessage(ac.getMessage().getSun("Team", "JoinTeam", "JoinTrue",
									new String[] { "{Player}", "{Money}", "{TeamID}", "{TeamName}" },
									new Object[] { myPlayer.getName(), myPlayer.getMoney(), getID(), getName() }));
					return true;
				}
			}
			MyPlayer.addOnceJoined(player, this);
			MyPlayer.clearApplyFor(player);
			MyPlayer.JoinTeam(player, this);
			MyPlayer.sendMessage(player,
					ac.getMessage().getSun("Team", "JoinTeam", "JoinTrue",
							new String[] { "{Player}", "{Money}", "{TeamID}", "{TeamName}" },
							new Object[] { player, MyPlayer.getMoney(player), getID(), getName() }));
		}
		return false;
	}

	/**
	 * 判断玩家是否在一个队伍里面
	 *
	 * @param player
	 * @return
	 */
	public boolean exist(String player) {
		if (player == null || player.isEmpty())
			return false;
		return Players.containsKey(player);
	}

	@Override
	public String toString() {
		return getName() + "(" + getID() + ")";
	}

	/**
	 * 撤销一个管理员
	 *
	 * @return
	 */
	public Team repealAdmin(String player) {
		Admins.remove(player);
		config.set("Admin", Admins);
		config.save();
		return this;
	}

	/**
	 * 添加一个队伍的管理员
	 *
	 * @param player
	 * @return
	 */
	public Team addAdmin(String player) {
		if (Admins.contains(player))
			return this;
		Admins.add(player);
		config.set("Admin", Admins);
		config.save();
		return this;
	}

	/**
	 * 返回队伍内一个玩家的声望
	 *
	 * @param name
	 * @return
	 */
	public int getPrestige(String name) {
		if (Players.containsKey(name)) {
			Map<String, Object> map = Players.get(name);
			if (map == null)
				return 0;
			return Tool.ObjectToInt(map.get("Prestige"), 0);
		}
		return 0;
	}

	/**
	 * 增加玩家的声望点
	 *
	 * @param name
	 * @param prestige
	 * @return
	 */
	public Team addPrestige(String name, int prestige) {
		return setPrestige(name, prestige + getPrestige(name));
	}

	/**
	 * 减少一个玩家的声望点
	 *
	 * @param name
	 * @param prestige
	 * @return
	 */
	public Team reducePrestige(String name, int prestige) {
		return setPrestige(name, getPrestige(name) - prestige);
	}

	/**
	 * 设置玩家的声望点
	 *
	 * @param name
	 * @param Prestige
	 * @return
	 */
	public Team setPrestige(String name, int prestige) {
		if (!Players.containsKey(name))
			return this;
		Map<String, Object> map = Players.get(name);
		if (map == null)
			return this;
		map.put("Prestige", prestige);
		Players.put(name, map);
		config.set("Players", Players);
		config.save();
		return this;
	}

	/**
	 *
	 * @param captain 队长名称
	 * @return
	 */
	public boolean setCaptain(String captain) {
		String c = getCaptain();
		if (!Players.containsKey(captain))
			return false;
		Captain = captain;
		config.set("Captain", Captain);
		Map<String, Object> map = Players.get(captain);
		if (map == null)
			return false;
		map.put("identity", "captain");
		map.put("Prestige", getPrestige(captain) + getPrestige(c) / 2);
		Map<String, Object> map1 = Players.get(c);
		if (map1 == null)
			return false;
		map1.put("identity", "player");
		map1.put("Prestige", getPrestige(c) / 2);
		Players.put(captain, map);
		Players.put(c, map1);
		return config.save();
	}

	/**
	 * 从队伍中删除一个玩家
	 *
	 * @param player
	 * @return
	 */
	public boolean removePlayer(Player player) {
		return removePlayer(player.getName());
	}

	/**
	 * 从队伍中删除一个玩家
	 *
	 * @param player
	 * @return
	 */
	public boolean removePlayer(String player) {
		if (player == null || player.isEmpty() || !isPlayer(player) || isCaptain(player))
			return false;
		Players.remove(player);
		Config config = MyPlayer.getConfig(player);
		config.set("Team", null);
		config.save();
		if (isAdmin(player)) {
			Admins.remove(player);
			this.config.set("Admin", Admins);
		}
		this.config.set("Players", Players);
		this.config.save();
		return true;
	}

	/**
	 * 解散本队伍
	 *
	 * @return
	 */
	public boolean dissolve() {
		Config config;
		String name;
		String[] K = { "{Captain}", "{Player}", "{Money}", "{TeamName}", "{TeamID}" };
		for (Map<String, Object> map : Players.values()) {
			name = Tool.objToString(map.get("name"));
			MyPlayer.sendMessage(name, ac.getMessage().getSun("Team", "DissolveTeam", "Dissolve", K,
					new Object[] { Captain, name, MyPlayer.getMoney(name), Name, ID }));
			config = MyPlayer.getConfig(name);
			config.set("Team", null);
			config.save();
		}
		for (Map<String, Object> map : ApplyFor.values()) {
			name = Tool.objToString(map.get("Player"));
			config = MyPlayer.getConfig(name);
			List<String> list = config.getList("ApplyFor");
			list.remove(ID);
			config.set("ApplyFor", list);
			config.save();
			MyPlayer.sendMessage(name, ac.getMessage().getSun("Team", "DissolveTeam", "ApplyForMessage", K,
					new Object[] { Captain, name, MyPlayer.getMoney(name), Name, ID }));
		}
		this.config = null;
		ac.getTeamMag().remove(ID);
		return file.delete();
	}

	/**
	 * 从队伍中移除一个玩家
	 *
	 * @param name 要移除的玩家名称
	 * @return 移除是否成功
	 */
	public boolean undock(String name) {
		if (!Players.containsKey(name) || isCaptain(name))
			return false;
		if (isAdmin(name))
			repealAdmin(name);
		Players.remove(name);
		Config config = MyPlayer.getConfig(name);
		config.set("Team", null);
		config.save();
		return true;
	}

	/**
	 * 获取队伍的实时等级积分
	 *
	 * @return
	 */
	public double getLevel() {
		return Players.size() + Prestige / 2 + Effects.size() * 1.5 + MaxCounts / 2 + Shop.size() * 0.2 + Money * 0.1
				+ ApplyFor.size() * 0.1;
	}
}
