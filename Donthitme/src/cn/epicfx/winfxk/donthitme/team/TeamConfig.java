package cn.epicfx.winfxk.donthitme.team;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cn.epicfx.winfxk.donthitme.MyPlayer;
import cn.epicfx.winfxk.donthitme.money.MyEconomy;
import cn.epicfx.winfxk.donthitme.tool.Tool;
import cn.nukkit.Player;
import cn.nukkit.potion.Effect;
import cn.nukkit.utils.Config;

/**
 * @author Winfxk
 */
public class TeamConfig {
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
	private Map<String, Object> ApplyFor;
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
	private Map<String, String> Players = new HashMap<>();
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
	 * 存储一个队伍的数据
	 *
	 * @param ID
	 * @param file
	 */
	public TeamConfig(String ID, File file) {
		this.file = file;
		this.ID = ID;
		config = new Config(file, Config.YAML);
		config.set("ID", ID);
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
	 * 队伍中有的玩家列表
	 *
	 * @return
	 */
	public Map<String, String> getPlayers() {
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
	public Map<String, Object> getApplyFor() {
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
	public TeamConfig sendJoinApplyFor(MyPlayer player) {
		Map<String, Object> map = new HashMap<>();
		map.put("Player", player.getName());
		map.put("Date", Tool.getDate() + " " + Tool.getTime());
		map.put("OnceJoined", player.getOnceJoined());
		ApplyFor.put(player.getName(), map);
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
}
