package cn.epicfx.winfxk.donthitme.vip.alg;

/**
 * @author Winfxk
 */
public class Hard extends LevelAlg {

	@Override
	public int getMaxExp(int exp) {
		int i = 150;
		for (int j = 0; j < exp; j++)
			i *= 2;
		return i;
	}
}
