package cn.winfxk.brassiere.vip.alg;

import cn.winfxk.brassiere.vip.Vip;

/**
 * 一般算法，升级速度一般
 *
 * @author Winfxk
 */
public class General extends LevelAlg {
	public General(Vip vip) {
		super(vip);
	}

	@Override
	public int getMaxExp(int exp) {
		int i = 100;
		for (int j = 0; j < exp; j++)
			i = Double.valueOf(Math.sqrt(i / 10 + j * 50) * Math.sqrt(j) + 50 * (j + 1)).intValue();
		return i;
	}
}
