package cn.epicfx.winfxk.donthitme.vip.alg;

/**
 * @author Winfxk
 */
public class Hard extends LevelAlg {

	@Override
	public int getMaxExp(int exp) {
		int i = 150;
		for (int j = 0; j < exp; j++)
			i = Double.valueOf((Math.sqrt(i / 10 + j * 50) * Math.sqrt(j) + 50 * (j + 1) * (Math.sqrt(j + 1) + 1)) * 2.5
					+ j * 3000 / (Math.sqrt(j + 1) + 1)).intValue();
		return i;
	}
}
