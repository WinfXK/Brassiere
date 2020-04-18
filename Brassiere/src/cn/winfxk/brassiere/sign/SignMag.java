package cn.winfxk.brassiere.sign;

import java.io.File;

import cn.nukkit.Player;
import cn.nukkit.utils.Config;
import cn.winfxk.brassiere.Activate;
import cn.winfxk.brassiere.MyPlayer;

/**
 * 称号插件管理中心
 * 
 * @Createdate 2020/04/18 20:06:50
 * @author Winfxk
 */
public class SignMag {
	private Activate ac;
	private Config config;

	public SignMag(Activate activate) {
		ac = activate;
		config = new Config(new File(activate.getPluginBase().getDataFolder(), Activate.SignShopName), Config.YAML);
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
}
