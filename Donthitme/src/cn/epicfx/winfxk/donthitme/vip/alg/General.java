package cn.epicfx.winfxk.donthitme.vip.alg;

/**
 * 一般算法，升级速度一般
 *
 * @author Winfxk
 */
public class General extends LevelAlg {
	@Override
	public int getMaxExp(int exp) {
		int i = 100;
		for (int j = 0; j < exp; j++)
			i *= 1.8;
		return i;
	}
}
