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

	public VipThread(VipMag vipMag) {
		this.vipMag = vipMag;
		ac = Activate.getActivate();
	}
}
