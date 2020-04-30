package cn.winfxk.brassiere.sign;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cn.nukkit.Player;
import cn.nukkit.utils.Config;
import cn.winfxk.brassiere.Activate;
import cn.winfxk.brassiere.MyPlayer;
import cn.winfxk.brassiere.tool.Tool;

/**
 * 称号插件管理中心
 * 
 * @Createdate 2020/04/18 20:06:50
 * @author Winfxk
 */
public class SignMag {
	private Activate ac;
	private Config config;
	private static SignMag sm;

	/**
	 * 称号管理系统
	 * 
	 * @param activate
	 */
	public SignMag(Activate activate) {
		ac = activate;
		sm = this;
		config = new Config(new File(activate.getPluginBase().getDataFolder(), Activate.SignShopName), Config.YAML);
	}

	/**
	 * 检查玩家是否拥有某个称号
	 * 
	 * @param player 玩家名称
	 * @param Sign   称号内容
	 * @return
	 */
	public boolean isPossess(String player, String Sign) {
		if (Sign == null)
			return false;
		Config config = ac.isPlayers(player) ? ac.getPlayers(player).getConfig() : MyPlayer.getConfig(player);
		try {
			return config.getStringList("Signs").contains(Sign);
		} catch (Exception e) {
			return false;
		}
	}

	/**
	 * 获取外部API
	 * 
	 * @return
	 */
	public static SignMag getSignMag() {
		return sm;
	}

	/**
	 * 获取称号商店的配置文件
	 * 
	 * @return
	 */
	public Config getConfig() {
		return config;
	}

	/**
	 * 构建主页
	 * 
	 * @return
	 */
	public boolean makeMain(Player player) {
		MyPlayer myPlayer = ac.getPlayers(player);
		myPlayer.form = new SignMain(player);
		return myPlayer.form.MakeMain();
	}

	/**
	 * 设置玩家称号
	 * 
	 * @param player
	 * @param sign
	 * @return
	 */
	public boolean setSign(String player, String sign) {
		MyPlayer myPlayer = ac.getPlayers(player);
		if (myPlayer != null)
			return myPlayer.setSign(sign);
		Config config = MyPlayer.getConfig(player);
		config.set("useSign", sign);
		return config.save();
	}

	/**
	 * 返回玩家已经拥有了的称号列表
	 * 
	 * @param player
	 * @return
	 */
	public List<String> getSigns(String player) {
		MyPlayer myPlayer = ac.getPlayers(player);
		return myPlayer != null ? myPlayer.getSigns() : MyPlayer.getConfig(player).getStringList("Signs");
	}

	/**
	 * 获取玩家正在使用的称号
	 * 
	 * @param player
	 * @return
	 */
	public String getSign(String player) {
		MyPlayer myPlayer = ac.getPlayers(player);
		return myPlayer != null ? myPlayer.getSign() : MyPlayer.getConfig(player).getString("useSign");
	}

	/**
	 * 设置玩家已经拥有了的称号
	 * 
	 * @param player
	 * @param list
	 * @return
	 */
	public boolean setSigns(String player, List<String> list) {
		MyPlayer myPlayer = ac.getPlayers(player);
		Config config = myPlayer != null ? myPlayer.config : MyPlayer.getConfig(player);
		config.set("Signs", list);
		return config.save();
	}

	/**
	 * 给玩家添加一个称号
	 * 
	 * @param player 要添加称号的玩家昵称
	 * @param Sign   要添加的称号
	 * @param isUse  添加的称号是否要使用
	 * @return
	 */
	public boolean addSign(String player, String Sign, boolean isUse) {
		List<String> list = getSigns(player);
		if (!list.contains(Sign))
			list.add(Sign);
		return setSigns(player, list) && isUse ? setSign(player, Sign) : true;
	}

	/**
	 * 给玩家添加一个称号
	 * 
	 * @param player 要添加称号的玩家昵称
	 * @param Sign   要添加的称号
	 * @return
	 */
	public boolean addSign(String player, String Sign) {
		return addSign(player, Sign, true);
	}

	/**
	 * 重置玩家称号
	 * 
	 * @param player
	 * @return
	 */
	public boolean reload(String player) {
		return setSign(player, null) && setSigns(player, new ArrayList<String>());
	}

	/**
	 * 删除一个制定的称号
	 * 
	 * @param player 要删除称号的玩家
	 * @param Sign   要删除的称号
	 * @return
	 */
	public boolean removeSign(String player, String Sign, boolean isUnSign) {
		List<String> list = getSigns(player);
		list.remove(Sign);
		return setSigns(player, list) && isUnSign ? getSign(player).equals(Sign) ? setSign(player, null) : true : true;
	}

	/**
	 * 向服务器添加一个称号数据
	 * 
	 * @param Sign    称号的内容
	 * @param Money   称号的价格
	 * @param Economy 称号支持的货币
	 * @return
	 */
	public boolean addSignData(String Sign, double Money, String Economy) {
		return setSignData(getKey(1), Sign, Money, Economy);
	}

	/**
	 * 获取一个不重复的Key
	 * 
	 * @param JJLength
	 * @return
	 */
	public String getKey(int JJLength) {
		String string = "";
		for (int i = 0; i < JJLength; i++)
			string += Tool.getRandColor("-+abcdefghijklmnopqrstuvwxyz_.0123456789");
		if (config.getAll().containsKey(string))
			return getKey(JJLength + 1);
		return string;
	}

	/**
	 * 设置一个称号数据
	 * 
	 * @param Key     称号数据的Key
	 * @param Sign    称号的内容
	 * @param Money   称号价格
	 * @param Economy 称号购买所需要使用的货币
	 * @return
	 */
	public boolean setSignData(String Key, String Sign, double Money, String Economy) {
		Map<String, Object> map = new HashMap<>();
		map.put("Key", Key);
		map.put("Sign", Sign);
		map.put("Money", Money);
		map.put("Economy", Economy);
		config.set(Key, map);
		return config.save();
	}

	/**
	 * 设置玩家为未使用称号的状态
	 * 
	 * @param player
	 * @return
	 */
	public boolean empty(String player) {
		return setSign(player, null);
	}
}
