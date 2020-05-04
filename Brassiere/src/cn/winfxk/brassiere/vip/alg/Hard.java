package cn.winfxk.brassiere.vip.alg;

import cn.winfxk.brassiere.vip.Vip;

/**
 * VIP升计算法，该算法是困难
 * 
 * @author Winfxk
 */
public class Hard extends LevelAlg {

	public Hard(Vip vip) {
		super(vip);
	}

	@Override
	public int getMaxExp(int exp) {
		int i = 150;
		for (int j = 0; j < exp; j++)
			i = Double.valueOf((Math.sqrt(i / 10 + j * 50) * Math.sqrt(j) + 50 * (j + 1) * (Math.sqrt(j + 1) + 1)) * 2.5
					+ j * 3000 / (Math.sqrt(j + 1) + 1)).intValue();
		return i;
	}
}
