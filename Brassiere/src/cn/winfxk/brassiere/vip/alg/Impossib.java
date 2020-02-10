package cn.winfxk.brassiere.vip.alg;

/**
 * @author Winfxk
 */
public class Impossib extends LevelAlg {

	@Override
	public int getMaxExp(int exp) {
		int i = 100;
		for (int j = 0; j < exp; j++)
			i = Double.valueOf((Math.sqrt(i / 10 + j * 50) * Math.sqrt(j) + 50 * (j + 1) * (Math.sqrt(j + 1) + 1)) * 2.5
					+ j * 3000 / (Math.sqrt(j + 1) + 1) * 1.5).intValue();
		return i;
	}

}
