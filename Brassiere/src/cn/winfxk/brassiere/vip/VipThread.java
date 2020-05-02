package cn.winfxk.brassiere.vip;

import cn.winfxk.brassiere.Activate;

/**
 * Vip的异步线程,用来刷新VIP状态
 * 
 * @Createdate 2020/04/30 09:53:41
 * @author Winfxk
 */
public class VipThread extends Thread {
	private Activate ac;
	private VipMag vipMag;
	private transient int reloadTime;

	public VipThread(VipMag vipMag) {
		this.vipMag = vipMag;
		ac = Activate.getActivate();
		reloadTime = Double.valueOf(ac.getConfig().getDouble("VIP刷新间隔") * 60).intValue();
	}

	@Override
	public void run() {
		
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
