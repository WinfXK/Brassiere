package cn.epicfx.winfxk.donthitme;

import java.time.Duration;
import java.time.Instant;
import java.util.LinkedHashMap;
import java.util.List;

import cn.epicfx.winfxk.donthitme.money.EconomyAPI;
import cn.epicfx.winfxk.donthitme.money.EconomyManage;
import cn.epicfx.winfxk.donthitme.money.MyEconomy;
import cn.epicfx.winfxk.donthitme.money.Snowmn;
import cn.epicfx.winfxk.donthitme.tool.Tool;
import cn.nukkit.Player;
import cn.nukkit.Server;
import cn.nukkit.plugin.Plugin;
import cn.nukkit.utils.Config;

/**
 * @author Winfxk
 */
public class Activate {
	public Player setPlayer;
	public MakeForm makeForm;
	public ResCheck resCheck;
	public final static String[] FormIDs = {};
	public final static String MessageFileName = "Message.yml", ConfigFileName = "Config.yml",
			CommandFileName = "Command.yml", EconomyListConfigName = "EconomyList.yml", FormIDFileName = "FormID.yml",
			PlayerDataDirName = "Players", LanguageDirName = "language";
	private Donthitme mis;
	private MyEconomy economy;
	private EconomyManage money;
	private static Activate activate;
	private LinkedHashMap<String, MyPlayer> Players;
	protected FormID FormID;
	protected Message message;
	protected Config config, CommandConfig;
	protected static final String[] loadFile = { ConfigFileName, CommandFileName };
	protected static final String[] defaultFile = { ConfigFileName, CommandFileName, MessageFileName };

	/**
	 * 插件数据的集合类
	 *
	 * @param kis
	 */
	public Activate(Donthitme kis) {
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
		kis.getServer().getPluginManager().registerEvents(new PlayerEvent(this), kis);
		kis.getLogger().info(message.getMessage("插件启动", new String[] { "{loadTime}" },
				new Object[] { ((float) Duration.between(mis.loadTime, Instant.now()).toMillis()) + "ms" }));
	}

	/**
	 * 得到默认经济插件
	 *
	 * @return
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
	public String[] getCommands(String string) {
		List<Object> list = CommandConfig.getList(string);
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
	public Donthitme getDonthitme() {
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
		setPlayers(player.getName(), myPlayer);
	}

	/**
	 * 设置玩家数据
	 *
	 * @param player
	 * @return
	 */
	public void setPlayers(String player, MyPlayer myPlayer) {
		if (!Players.containsKey(player))
			Players.put(player, myPlayer);
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