package cn.winfxk.brassiere;

import java.time.Duration;
import java.time.Instant;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import cn.nukkit.Player;
import cn.nukkit.Server;
import cn.nukkit.plugin.Plugin;
import cn.nukkit.plugin.PluginBase;
import cn.nukkit.utils.Config;
import cn.nukkit.utils.Utils;
import cn.winfxk.brassiere.cmd.TeamCommand;
import cn.winfxk.brassiere.form.MakeForm;
import cn.winfxk.brassiere.money.EconomyAPI;
import cn.winfxk.brassiere.money.EconomyManage;
import cn.winfxk.brassiere.money.MyEconomy;
import cn.winfxk.brassiere.money.Snowmn;
import cn.winfxk.brassiere.team.TeamMag;
import cn.winfxk.brassiere.tool.Tool;
import cn.winfxk.brassiere.vip.VipMag;

/**
 * @author Winfxk
 */
public class Activate {
	public Player setPlayer;
	public MakeForm makeForm;
	public ResCheck resCheck;
	public final static String[] FormIDs = { /* 0 */"主页", /* 1 */"备用主页", /* 2 */"次页", /* 3 */"备用次页" };
	public final static String MessageFileName = "Message.yml", ConfigFileName = "Config.yml",
			CommandFileName = "Command.yml", EconomyListConfigName = "EconomyList.yml", FormIDFileName = "FormID.yml",
			PlayerDataDirName = "Players", LanguageDirName = "language", VipFileName = "VIP/VIP.yml",
			SVIPFileName = "VIP/SVIP.yml", RVIPFileName = "VIP/RVIP.yml", VIPDirName = "VIP",
			SystemFileName = "System.xml", TeamDirName = "Team", TeamEffectName = "TeamEffectShop.yml";
	private Brassiere mis;
	private MyEconomy economy;
	private EconomyManage money;
	private static Activate activate;
	private LinkedHashMap<String, MyPlayer> Players;
	protected TeamMag teamMag;
	protected FormID FormID;
	protected Message message;
	protected VipMag vipMag;
	protected Config config, CommandConfig;
	/**
	 * 默认要加载的配置文件，这些文件将会被用于与插件自带数据匹配
	 */
	protected static final String[] loadFile = { ConfigFileName, CommandFileName };
	/**
	 * 插件基础配置文件
	 */
	protected static final String[] defaultFile = { CommandFileName, MessageFileName };
	/**
	 * 只加载一次的数据
	 */
	protected static final String[] ForOnce = { RVIPFileName, VipFileName, SVIPFileName, ConfigFileName };
	protected static final String[] Mkdir = { TeamDirName, VIPDirName, PlayerDataDirName };

	/**
	 * 插件数据的集合类
	 *
	 * @param kis
	 */
	public Activate(Brassiere kis) {
		activate = this;
		mis = kis;
		FormID = new FormID();
		Players = new LinkedHashMap<>();
		if ((resCheck = new ResCheck(this).start()) == null)
			return;
		money = new EconomyManage();
		Plugin plugin = Server.getInstance().getPluginManager().getPlugin(Snowmn.Name);
		if (plugin != null)
			money.addEconomyAPI(new Snowmn(this));
		plugin = Server.getInstance().getPluginManager().getPlugin(EconomyAPI.Name);
		if (plugin != null)
			money.addEconomyAPI(new EconomyAPI(this));
		economy = money.getEconomy(config.getString("默认货币"));
		makeForm = new MakeForm(this);
		vipMag = new VipMag(this);
		teamMag = new TeamMag(this);
		kis.getServer().getCommandMap().register(kis.getName() + "Team", new TeamCommand(this));
		kis.getServer().getPluginManager().registerEvents(new PlayerEvent(this), kis);
		kis.getLogger().info(message.getMessage("插件启动", "{loadTime}",
				(float) Duration.between(mis.loadTime, Instant.now()).toMillis() + "ms"));
	}

	/**
	 * 返回队伍管理
	 *
	 * @return
	 */
	public TeamMag getTeamMag() {
		return teamMag;
	}

	public VipMag getVipMag() {
		return vipMag;
	}

	/**
	 * 返回一个默认的玩家数据
	 *
	 * @return
	 * @throws Exception
	 */
	public Map<String, Object> getPlayerConfig() throws Exception {
		return resCheck.yaml.loadAs(Utils.readFile(getClass().getResourceAsStream("/resources/player.yml")), Map.class);
	}

	/**
	 * 得到默认经济插件
	 *
	 * @reaturn
	 */
	public MyEconomy getEconomy() {
		return economy;
	}

	/**
	 * 设置默认经济插件
	 *
	 * @param EconomyName
	 */
	public void setEconomy(String EconomyName) {
		if (money.supportEconomy(EconomyName))
			this.economy = money.getEconomy(EconomyName);
	}

	/**
	 * 获取自定义命令内容
	 *
	 * @param string
	 * @return
	 */
	public String[] getCommands(String string, String string2) {
		Object obj = CommandConfig.get(string);
		Map<String, List<Object>> map = obj != null && obj instanceof Map ? (HashMap<String, List<Object>>) obj
				: new HashMap<>();
		List<Object> list = map.get(string2);
		String[] s = new String[list.size()];
		for (int i = 0; i < list.size(); i++)
			s[i] = Tool.objToString(list.get(i));
		return s;
	}

	/**
	 * 得到插件名称
	 *
	 * @return
	 */
	public String getName() {
		return mis.getName();
	}

	/**
	 * 得到插件主类
	 *
	 * @return
	 */
	public PluginBase getPluginBase() {
		return mis;
	}

	/**
	 * 删除玩家数据
	 *
	 * @param player
	 */
	public void removePlayers(Player player) {
		removePlayers(player.getName());
	}

	/**
	 * 删除玩家数据
	 *
	 * @param player
	 */
	public void removePlayers(String player) {
		if (Players.containsKey(player))
			Players.remove(player);
	}

	/**
	 * 设置玩家数据
	 *
	 * @param player
	 * @return
	 */
	public void setPlayers(Player player, MyPlayer myPlayer) {
		if (!Players.containsKey(player.getName()))
			Players.put(player.getName(), myPlayer);
		myPlayer = Players.get(player.getName());
		myPlayer.setPlayer(player);
	}

	/**
	 * 设置玩家数据
	 *
	 * @param player
	 * @return
	 */
	public MyPlayer getPlayers(Player player) {
		return isPlayers(player.getName()) ? Players.get(player.getName()) : null;
	}

	/**
	 * 设置玩家数据
	 *
	 * @param player
	 * @return
	 */
	public MyPlayer getPlayers(String player) {
		return isPlayers(player) ? Players.get(player) : null;
	}

	/**
	 * 玩家数据是否存在
	 *
	 * @param player
	 * @return
	 */
	public boolean isPlayers(Player player) {
		return Players.containsKey(player.getName());
	}

	/**
	 * 玩家数据是否存在
	 *
	 * @param player
	 * @return
	 */
	public boolean isPlayers(String player) {
		return Players.containsKey(player);
	}

	/**
	 * 得到玩家数据
	 *
	 * @return
	 */
	public LinkedHashMap<String, MyPlayer> getPlayers() {
		return new LinkedHashMap<>(Players);
	}

	/**
	 * 返回经济支持管理器</br>
	 * Return to the economic support manager
	 *
	 * @return
	 */
	public EconomyManage getEconomyManage() {
		return money;
	}

	/**
	 * 得到ID类
	 *
	 * @return
	 */
	public FormID getFormID() {
		return FormID;
	}

	/**
	 * 得到语言类
	 *
	 * @return
	 */
	public Message getMessage() {
		return message;
	}

	/**
	 * 对外接口
	 *
	 * @return
	 */
	public static Activate getActivate() {
		return activate;
	}

	/**
	 * 返回EconomyAPI货币的名称
	 *
	 * @return
	 */
	public String getMoneyName() {
		return economy == null ? config.getString("货币名称") : economy.getMoneyName();
	}

	/**
	 * 得到MostBrain主配置文件
	 *
	 * @return
	 */
	public Config getConfig() {
		return config;
	}
}
