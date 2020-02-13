package cn.winfxk.brassiere.vip;
/**
 *@author Winfxk
 */

import java.io.File;
import java.io.FilenameFilter;
import java.util.LinkedHashMap;

import cn.winfxk.brassiere.Activate;

public class VipMag implements FilenameFilter {
	public LinkedHashMap<String, Vip> Vips = new LinkedHashMap<>();
	protected Activate ac;
	private static VipApi vipApi;

	public VipMag(Activate activate) {
		this.ac = activate;
		reload();
		vipApi = new VipApi(this);
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
		String[] files = file.list(this);
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
		ac.getPluginBase().getLogger()
				.info(ac.getMessage().getMessage("加载特权规则", new String[] { "{Count}" }, new Object[] { VipSize() }));
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

	@Override
	public boolean accept(File arg0, String arg1) {
		return new File(arg0, arg1).isFile();
	}
}
