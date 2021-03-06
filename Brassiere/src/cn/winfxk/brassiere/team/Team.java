package cn.winfxk.brassiere.team;

import java.io.File;
import java.time.Instant;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import cn.nukkit.Player;
import cn.nukkit.potion.Effect;
import cn.nukkit.utils.Config;
import cn.winfxk.brassiere.Activate;
import cn.winfxk.brassiere.MyPlayer;
import cn.winfxk.brassiere.money.MyEconomy;
import cn.winfxk.brassiere.tool.Effectrow;
import cn.winfxk.brassiere.tool.Tool;

/**
 * 每个队伍的数据
 *
 * @author Winfxk
 */
public class Team {
	/**
	 * 队伍的配置文件对象
	 */
	private Config config;
	/**
	 * 队伍的文件对象
	 */
	private File file;
	private static Activate ac = Activate.getActivate();
	/**
	 * 队伍的ID
	 */
	private String ID;
	/**
	 * 队伍称号的价格 <br>
	 * <br>
	 * 一级称号价格: 1 <br>
	 * 二级称号价格: 100 <br>
	 * 三级称号价格: 5000 <br>
	 * 五级称号价格: 10000 <br>
	 * 六级称号价格: 10010 <br>
	 * 七级称号价格: 10086 <br>
	 */
	private Map<Integer, Double> SignPrice;
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
	 * 队伍安全口令
	 */
	private String Securitypd;
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
	 * 队伍的签到奖励<将会获得的队伍声望数量>
	 */
	private double SignIn;
	/**
	 * 是否启用追加声望算法
	 */
	private boolean isSignIn;
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
	private HashMap<String, Map<String, Object>> Message;
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
		obj = config.get("SignPrice");
		SignPrice = obj != null && obj instanceof Map ? (HashMap<Integer, Double>) obj : new HashMap<>();
		obj = config.get("Players");
		Players = obj != null && obj instanceof Map ? (HashMap<String, Map<String, Object>>) obj : new HashMap<>();
		obj = config.get("Effects");
		Effects = new ArrayList<>();
		map = obj != null && obj instanceof Map ? (HashMap<Object, Object>) obj : new HashMap<>();
		for (Entry<?, ?> entry : map.entrySet()) {
			EffectID = Tool.ObjToInt(entry.getKey());
			EffectLevel = Tool.ObjToInt(entry.getValue());
			effect = Effectrow.getEffect(EffectID);
			effect.setAmplifier((EffectLevel >= 0 ? EffectLevel : 0) <= 5 ? EffectLevel : 5);
			Effects.add(effect);
		}
		obj = config.get("Shop");
		Shop = obj != null && obj instanceof Map ? (HashMap<String, Object>) obj : new HashMap<>();
		obj = config.get("Message");
		Message = obj != null && obj instanceof Map ? (HashMap<String, Map<String, Object>>) obj : new HashMap<>();
		obj = config.get("ApplyFor");
		ApplyFor = obj != null && obj instanceof Map ? (HashMap<String, Map<String, Object>>) obj : new HashMap<>();
		JoinTariff = config.getDouble("JoinTariff");
		JoinTariffEconomy = ac.getEconomyManage().getEconomy(config.getString("JoinTariffEconomy"));
		AllowedPVP = config.getBoolean("AllowedPVP");
		Content = config.getString("Content");
		Securitypd = config.getString("Securitypd");
		SignIn = config.getDouble("SignIn");
		isSignIn = config.getBoolean("isSignIn");
	}

	/**
	 * 允许玩家使用聊天室
	 * 
	 * @param allowedChat
	 * @return
	 */
	public boolean setAllowedChat(boolean allowedChat) {
		AllowedChat = allowedChat;
		return save();
	}

	/**
	 * 允许玩家使用队伍增益
	 * 
	 * @param allowedGain
	 * @return
	 */
	public boolean setAllowedGain(boolean allowedGain) {
		AllowedGain = allowedGain;
		return save();
	}

	/**
	 * 允许玩家上架物品到队伍商城
	 * 
	 * @param allowedMakeShop
	 * @return
	 */
	public boolean setAllowedMakeShop(boolean allowedMakeShop) {
		AllowedMakeShop = allowedMakeShop;
		return save();
	}

	/**
	 * 设置是否允许使用签到增益
	 * 
	 * @param isSignIn
	 * @return
	 */
	public boolean setSignIn(boolean isSignIn) {
		this.isSignIn = isSignIn;
		return save();
	}

	/**
	 * 设置每次签到都会获得的数额
	 * 
	 * @param Money
	 * @return
	 */
	public boolean setSignIn(double Money) {
		SignIn = Money;
		return save();
	}

	/**
	 * 允许队员之间PVP
	 * 
	 * @param allowedPVP
	 * @return
	 */
	public boolean setAllowedPVP(boolean allowedPVP) {
		AllowedPVP = allowedPVP;
		return save();
	}

	/**
	 * 允许玩家使用队伍商城
	 * 
	 * @param allowedShop
	 * @return
	 */
	public boolean setAllowedShop(boolean allowedShop) {
		AllowedShop = allowedShop;
		return save();
	}

	/**
	 * 设置允许玩家加入队伍
	 * 
	 * @param allowedJoin
	 * @return
	 */
	public boolean setAllowedJoin(boolean allowedJoin) {
		AllowedJoin = allowedJoin;
		return save();
	}

	/**
	 * 设置允许玩家或许队伍称号
	 * 
	 * @param allowedSign
	 * @return
	 */
	public boolean setAllowedSign(boolean allowedSign) {
		AllowedSign = allowedSign;
		return save();
	}

	/**
	 * 获取队伍签到将会获得的声望数量
	 * 
	 * @return
	 */
	public double getSignIn() {
		return SignIn;
	}

	/**
	 * 是否启用连续签到将会获得奖励算法
	 * 
	 * @return
	 */
	public boolean isSignIn() {
		return isSignIn;
	}

	/**
	 * 设置队伍名称
	 * 
	 * @param name
	 * @return
	 */
	public boolean setName(String name) {
		Name = name;
		return save();
	}

	/**
	 * 获取队伍安全口令
	 * 
	 * @return
	 */
	public String getSecuritypd() {
		return Securitypd;
	}

	/**
	 * 设置队伍内容消息
	 * 
	 * @param content
	 * @return
	 */
	public boolean setContent(String content) {
		Content = content;
		return save();
	}

	/**
	 * 设置入队资费
	 * 
	 * @param joinTariff
	 * @return
	 */
	public boolean setJoinTariff(double joinTariff) {
		JoinTariff = joinTariff;
		return save();
	}

	/**
	 * 判断安全口令是否匹配
	 * 
	 * @return
	 */
	public boolean checkSecuritypd(String SecurityCode) {
		return Securitypd.equals(SecurityCode);
	}

	/**
	 * 设置队伍安全口令
	 * 
	 * @param securitypd
	 */
	public boolean setSecuritypd(String securitypd) {
		Securitypd = securitypd;
		return save();
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
		return close();
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
		close();
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
	 * 保存队伍的数据
	 *
	 * @return
	 */
	public boolean close() {
		Map<Object, Object> map;
		config.set("Name", Name);
		config.set("Captain", Captain);
		config.set("Prestige", Prestige);
		config.set("Money", Money);
		config.getInt("MaxCounts", MaxCounts);
		config.set("MaxShopItem", MaxShopItem);
		config.set("AllowedJoin", AllowedJoin);
		config.set("AllowedChat", AllowedChat);
		config.set("AllowedMakeShop", AllowedMakeShop);
		config.set("AllowedShop", AllowedShop);
		config.set("AllowedGain", AllowedGain);
		config.set("AllowedSign", AllowedSign);
		config.set("Admin", Admins);
		config.set("Players", Players);
		map = new HashMap<>();
		for (Effect effect : Effects)
			map.put(effect.getId(), effect.getAmplifier());
		config.set("Effects", map);
		config.set("Shop", Shop);
		config.set("Message", Message);
		config.set("ApplyFor", ApplyFor);
		config.set("JoinTariff", JoinTariff);
		config.set("JoinTariffEconomy", JoinTariffEconomy.getEconomyName());
		config.set("AllowedPVP", AllowedPVP);
		config.set("Content", Content);
		config.set("SignPrice", SignPrice);
		config.set("SignIn", SignIn);
		config.set("isSignIn", isSignIn);
		return config.save();
	}

	/**
	 * 队伍称号的价格 <br>
	 * <br>
	 * 一级称号价格: <br>
	 * 二级称号价格: <br>
	 * 三级称号价格: <br>
	 * 五级称号价格: <br>
	 * 六级称号价格: <br>
	 * 七级称号价格: <br>
	 */
	public Map<Integer, Double> getSignPrice() {
		return SignPrice;
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
	 * 队伍聊天室 <br>
	 * <br>
	 * <b> 聊天时间及玩家名： <br>
	 * &nbsp;&nbsp;&nbsp;&nbsp;Name: 玩家名<br>
	 * &nbsp;&nbsp;&nbsp;&nbsp;Date: 时间 <br>
	 * &nbsp;&nbsp;&nbsp;&nbsp;Time: 时间 <br>
	 * &nbsp;&nbsp;&nbsp;&nbsp;Msg: 发话的内容 </b>
	 *
	 * @return
	 */
	public HashMap<String, Map<String, Object>> getMessage() {
		if (Message.size() > ac.getConfig().getInt("100")) {
			List<String> list = new ArrayList<>(Message.keySet());
			for (int i = ac.getConfig().getInt("100"); i < list.size(); i++)
				Message.remove(list.get(i));
		}
		return Message;
	}

	/**
	 * 添加队伍聊天室内容
	 *
	 * @param player 发消息的玩家
	 * @param string 发消息的内容
	 * @return
	 */
	public boolean addMessage(Player player, String string) {
		Map<String, Object> map = new HashMap<>();
		Instant Ins = Instant.now();
		map.put("Name", player.getName());
		map.put("Date", Tool.getDate());
		map.put("Time", Tool.getTime());
		map.put("Msg", string);
		Message.put(player.getName() + " " + Ins.getEpochSecond() + Ins.getNano(), map);
		return close();
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
		close();
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
		close();
		return true;
	}

	/**
	 * 往队伍Buff中添加一些Buff
	 * 
	 * @param effect
	 * @return
	 */
	public boolean addEffect(Effect effect) {
		if (isEffect(effect))
			return false;
		Effects.add(effect);
		return save();
	}

	/**
	 * 判断队伍是否拥有一个Buff
	 * 
	 * @param effect
	 * @return
	 */
	public boolean isEffect(Effect effect) {
		for (Effect effect2 : Effects)
			if (effect.getId() == effect2.getId() && effect.getAmplifier() == effect2.getAmplifier())
				return true;
		return false;
	}

	/**
	 * 从队伍Buff列表删除一个Buff
	 *
	 * @param effect
	 * @return
	 */
	public boolean removeEffect(Effect effect) {
		boolean isOK = false;
		for (int i = 0; i < Effects.size(); i++)
			if (effect.getId() == Effects.get(i).getId() && effect.getAmplifier() == Effects.get(i).getAmplifier()) {
				Effects.remove(i);
				isOK = true;
			}
		return isOK;
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
			close();
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
		return getID() + "-" + getName();
	}

	/**
	 * 撤销一个管理员
	 *
	 * @return
	 */
	public Team repealAdmin(String player) {
		Admins.remove(player);
		close();
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
		close();
		return this;
	}

	/**
	 * 返回队伍内一个玩家的声望
	 *
	 * @param name
	 * @return
	 */
	public double getPrestige(String name) {
		if (Players.containsKey(name)) {
			Map<String, Object> map = Players.get(name);
			if (map == null)
				return 0;
			return Tool.objToDouble(map.get("Prestige"), 0d);
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
	public Team addPrestige(String name, double prestige) {
		return setPrestige(name, prestige + getPrestige(name));
	}

	/**
	 * 减少一个玩家的声望点
	 *
	 * @param name
	 * @param prestige
	 * @return
	 */
	public Team reducePrestige(String name, double prestige) {
		return setPrestige(name, getPrestige(name) - prestige);
	}

	/**
	 * 设置玩家的声望点
	 *
	 * @param name
	 * @param Prestige
	 * @return
	 */
	public Team setPrestige(String name, double prestige) {
		if (!Players.containsKey(name))
			return this;
		Map<String, Object> map = Players.get(name);
		if (map == null)
			return this;
		map.put("Prestige", prestige);
		Players.put(name, map);
		close();
		return this;
	}

	/**
	 * 设置队伍的队长
	 *
	 * @param captain 队长名称
	 * @return
	 */
	public boolean setCaptain(String captain) {
		String c = getCaptain();
		if (!Players.containsKey(captain))
			return false;
		Captain = captain;
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
		return close();
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
		return close();
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
		return close();
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

	/**
	 * 返回队伍管理核心
	 * 
	 * @return
	 */
	public TeamMag getTeamMag() {
		return ac.getTeamMag();
	}

	/**
	 * 设置称号的价格
	 * 
	 * @param signPrice
	 */
	public void setSignPrice(Map<Integer, Double> signPrice) {
		SignPrice = signPrice;
	}

	/**
	 * 设置称号的价格
	 * 
	 * @param pos   称号的等级<b>[0-5]</b>
	 * @param Money 称号的价格
	 */
	public void setSignPrice(Integer pos, Double Money) {
		SignPrice.replace(pos, Money);
	}

}
