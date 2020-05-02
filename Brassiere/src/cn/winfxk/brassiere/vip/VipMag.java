package cn.winfxk.brassiere.vip;

/**
 *@author Winfxk
 */

import java.io.File;
import java.util.LinkedHashMap;

import cn.nukkit.Player;
import cn.nukkit.utils.Config;
import cn.winfxk.brassiere.Activate;

public class VipMag {
	public LinkedHashMap<String, Vip> Vips = new LinkedHashMap<>();
	protected final Activate ac;
	private static VipApi vipApi;
	private final VipThread vipThread;
	/**
	 * 存储已经是VIP的玩家列表
	 */
	private final Config VipList;
	/**
	 * vip商店
	 */
	private final Config VipShop;

	public VipMag(Activate activate) {
		this.ac = activate;
		reload();
		VipList = new Config(new File(activate.getPluginBase().getDataFolder(), Activate.haveVipName), Config.YAML);
		VipShop = new Config(new File(activate.getPluginBase().getDataFolder(), Activate.VipShopName), Config.YAML);
		vipApi = new VipApi(this);
		(vipThread = new VipThread(this)).start();
	}

	/**
	 * 显示VIP功能主页
	 * 
	 * @param player
	 * @return
	 */
	public boolean makeMain(Player player) {
		if (ac.isPlayers(player))
			return (ac.getPlayers(player).form = new MainForm(player, null)).MakeMain();
		ac.getPluginBase().getLogger().warning(ac.getMessage().getSun("Vip", "Main", "PlayerOffline", player));
		return false;
	}

	/**
	 * 返回存储VIP列表的配置文件对象
	 * 
	 * @return
	 */
	public Config getVipList() {
		return VipList;
	}

	/**
	 * 返回VIP商店
	 * 
	 * @return
	 */
	public Config getVipShop() {
		return VipShop;
	}

	/**
	 * 返回已经支持了的VIP规则
	 * 
	 * @return
	 */
	public LinkedHashMap<String, Vip> getVips() {
		return Vips;
	}

	/**
	 * 获取VIP线程
	 * 
	 * @return
	 */
	public VipThread getVipThread() {
		return vipThread;
	}

	/**
	 * 取得VIP的API
	 *
	 * @return
	 */
	public static VipApi getVipApi() {
		return vipApi;
	}

	/**
	 * VIP特权的数量
	 *
	 * @return
	 */
	public int VipSize() {
		return Vips.size();
	}

	/**
	 * 根据VIP的ID获取一个VIP
	 *
	 * @param ID
	 * @return
	 */
	public Vip getVip(String ID) {
		return Vips.get(ID);
	}

	public void reload() {
		File file = new File(ac.getPluginBase().getDataFolder(), Activate.VIPDirName);
		String[] files = file.list((File, S) -> new File(File, S).isFile());
		Vip vip;
		for (String filename : files)
			try {
				vip = new Vip(ac, new File(file, filename));
				Vips.put(vip.getID(), vip);
			} catch (Exception e) {
				e.printStackTrace();
				ac.getPluginBase().getLogger().error(ac.getMessage().getMessage("无法加载特权配置",
						new String[] { "{FileName}", "{Error}" }, new Object[] { file.getName(), e.getMessage() }));
			}
		ac.getPluginBase().getLogger().info(ac.getMessage().getMessage("加载特权规则", "{Count}", VipSize()));
	}

	/**
	 * 判断服务器是否已经加载一个VIP
	 *
	 * @param ID
	 * @return
	 */
	public boolean isVip(String ID) {
		if (ID == null || ID.isEmpty())
			return false;
		return Vips.containsKey(ID);
	}
}
