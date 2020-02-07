package cn.epicfx.winfxk.donthitme.vip.alg;

/**
 * 简单算法，容易升级
 *
 * @author Winfxk
 */
public class Simple extends LevelAlg {
	@Override
	public int getMaxExp(int exp) {
		int i = 50;
		for (int j = 0; j < exp; j++)
			i = Double.valueOf(Math.sqrt(i + j * 50) * Math.sqrt(j) + 50 * (j + 1)).intValue();
		return i;
	}
}
