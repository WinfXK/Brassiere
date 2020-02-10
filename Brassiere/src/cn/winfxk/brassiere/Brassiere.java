package cn.winfxk.brassiere;

import java.time.Instant;

import cn.nukkit.plugin.PluginBase;

/**
 * @author Winfxk
 */
public class Brassiere extends PluginBase {
	public Instant loadTime;
	private static Activate ac;

	@Override
	public void onEnable() {
		loadTime = Instant.now();
		ac = new Activate(this);
		super.onEnable();
	}

	@Override
	public void onLoad() {
		getLogger().info(getName() + " start load..");
		if (!getDataFolder().exists())
			getDataFolder().mkdirs();
	}

	@Override
	public void onDisable() {
		try {
			getLogger().info(ac.getMessage().getMessage("插件关闭"));
		} catch (Exception e) {
		}
		super.onDisable();
	}

	/**
	 * 返回插件数据中心</br>
	 * <b>PS: </b> 我不喜欢把太多数据放到插件主类，有意见你就去屎吧
	 *
	 * @return
	 */
	public static Activate getInstance() {
		return ac;
	}
}
