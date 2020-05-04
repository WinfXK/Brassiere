package cn.winfxk.brassiere.vip.alg;

import cn.winfxk.brassiere.vip.Vip;

/**
 * Vip升级算法，这是最难得升计算法
 * 
 * @author Winfxk
 */
public class Impossib extends LevelAlg {

	public Impossib(Vip vip) {
		super(vip);
	}

	@Override
	public int getMaxExp(int exp) {
		int i = 100;
		for (int j = 0; j < exp; j++)
			i = Double.valueOf((Math.sqrt(i / 10 + j * 50) * Math.sqrt(j) + 50 * (j + 1) * (Math.sqrt(j + 1) + 1)) * 2.5
					+ j * 3000 / (Math.sqrt(j + 1) + 1) * 1.5).intValue();
		return i;
	}
}
