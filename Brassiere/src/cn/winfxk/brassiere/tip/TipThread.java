package cn.winfxk.brassiere.tip;

import cn.nukkit.Player;
import cn.nukkit.Server;
import cn.winfxk.brassiere.Activate;

/**
 * 底部显示的异步线程,用来显示底部信息
 * 
 * @Createdate 2020/04/28 12:35:29
 * @author Winfxk
 */
public class TipThread extends Thread {
	private Tip tip;
	private Activate ac;
	private transient int Tip, Top;
	private Server server;

	/**
	 * 底部显示的操作线程
	 * 
	 * @param tip
	 */
	public TipThread(Tip tip) {
		this.tip = tip;
		ac = Activate.getActivate();
		Tip = ac.getConfig().getInt("底部显示频率");
		Top = ac.getConfig().getInt("头部显示频率");
		server = Server.getInstance();
	}

	@Override
	public void run() {
		while (true) {
			try {
				sleep(50);
				if ((tip.isEnabled() && Tip-- <= 0) || (Top-- <= 0 && tip.isTopEnabled())) {
					for (Player player : server.getOnlinePlayers().values()) {
						if (tip.isEnabled() && Tip-- <= 0)
							player.sendTip(tip.getTipsString(player));
						if (Top-- <= 0 && tip.isTopEnabled())
							player.setNameTag(tip.getTopString(player));
					}
					if (tip.isEnabled() && Tip-- <= 0)
						Tip = ac.getConfig().getInt("底部显示频率");
					if (Top-- <= 0 && tip.isTopEnabled())
						Top = ac.getConfig().getInt("头部显示频率");
				}
			} catch (InterruptedException e) {
				e.printStackTrace();
				ac.getPluginBase().getLogger().warning("TipThread has stopped!");
				break;
			}
		}
	}
}
