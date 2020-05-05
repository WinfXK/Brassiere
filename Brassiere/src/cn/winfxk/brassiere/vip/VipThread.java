package cn.winfxk.brassiere.vip;

import cn.nukkit.Player;
import cn.nukkit.Server;
import cn.nukkit.potion.Effect;
import cn.winfxk.brassiere.Activate;
import cn.winfxk.brassiere.MyPlayer;

/**
 * Vip的异步线程,用来刷新VIP状态
 * 
 * @Createdate 2020/04/30 09:53:41
 * @author Winfxk
 */
public class VipThread extends Thread {
	private Activate ac;
	private transient int reloadTime = 7200;
	private transient int GiveEffectTime = 560;

	public VipThread() {
		ac = Activate.getActivate();
	}

	@Override
	public void run() {
		MyPlayer myPlayer;
		while (true) {
			try {
				sleep(500);
				if (reloadTime-- <= 0) {
					reloadTime = 7200;
					for (Player player : Server.getInstance().getOnlinePlayers().values()) {
						myPlayer = ac.getPlayers(player);
						if (!myPlayer.isVip())
							continue;
						myPlayer.getConfig().set("VipTime", myPlayer.getConfig().getInt("VipTime") - 1);
						if (myPlayer.getConfig().getInt("VipTime") <= 0)
							VipApi.remove(player.getName());
						myPlayer.config.save();
					}
				}
				if (GiveEffectTime-- <= 0) {
					GiveEffectTime = 560;
					for (Player player : Server.getInstance().getOnlinePlayers().values()) {
						myPlayer = ac.getPlayers(player);
						if (!myPlayer.isVip())
							continue;
						for (Effect effect : myPlayer.vip.getEffects())
							player.addEffect(effect.setDuration(400));
					}
				}
				for (Player player : Server.getInstance().getOnlinePlayers().values()) {
					myPlayer = ac.getPlayers(player);
					if (!myPlayer.isVip())
						continue;
					myPlayer.getPlayer().getLevel().addParticle(myPlayer.vip.getParticleType(), player);
				}
			} catch (InterruptedException e) {
				e.printStackTrace();
				ac.getPluginBase().getLogger().error("VIP thread has stopped!");
				break;
			}
		}
	}

	/**
	 * 获取线程的刷新时间
	 * 
	 * @return
	 */
	public int getReloadTime() {
		return reloadTime;
	}
}
