package cn.winfxk.brassiere.team;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cn.nukkit.Player;
import cn.nukkit.potion.Effect;
import cn.nukkit.utils.Config;
import cn.winfxk.brassiere.Activate;
import cn.winfxk.brassiere.MyPlayer;
import cn.winfxk.brassiere.money.MyEconomy;
import cn.winfxk.brassiere.tool.Tool;

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
	private MyEconomy joinTariffEconomy;
	/**
	 * 队伍商城
	 */
	private Map<String, Object> Shop;
	/**
	 * 队伍拥有的效果加成
	 */
	private List<Effect> effects;
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
		config = new Config(file, Config.YAML);
		config.set("ID", ID);
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
		return effects;
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
	public String getContent(String[] K, Object[] D) {
		return ac.getMessage().getText(Content, K, D);
	}

	/**
	 * 队伍的内容介绍
	 *
	 * @return
	 */
	public String getContent() {
		return ac.getMessage().getText(Content);
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
		return Admins.contains(player.getName());
	}

	/**
	 * 判断一个逗比是不是队伍管理员
	 *
	 * @param player
	 * @return
	 */
	public boolean isAdmin(String player) {
		return Admins.contains(player);
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
		return joinTariffEconomy;
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
}
