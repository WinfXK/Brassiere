package cn.epicfx.winfxk.donthitme.vip.alg;

/**
 * @author Winfxk
 */
public class Impossib extends LevelAlg {

	@Override
	public int getMaxExp(int exp) {
		int i = 100;
		for (int j = 0; j < exp; j++)
			i *= 5;
		return i;
	}

}
