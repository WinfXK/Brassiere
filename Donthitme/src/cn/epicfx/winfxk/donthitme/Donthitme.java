package cn.epicfx.winfxk.donthitme;

import java.time.Instant;

import cn.nukkit.plugin.PluginBase;

/**
 * @author Winfxk
 */
public class Donthitme extends PluginBase {
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
	 * PY
	 *
	 * @return
	 */
	public static Activate getInstance() {
		return ac;
	}
}
