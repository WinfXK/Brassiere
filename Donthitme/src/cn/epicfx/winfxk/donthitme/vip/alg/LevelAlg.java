package cn.epicfx.winfxk.donthitme.vip.alg;

import cn.epicfx.winfxk.donthitme.vip.Vip;

/**
 * @author Winfxk
 */
public abstract class LevelAlg {
	public int exp;
	public Vip vip;

	public LevelAlg setPlayer(int exp, Vip vip) {
		this.exp = exp;
		this.vip = vip;
		return this;
	}

	public int getLevel() {
		for (int level = vip.getMinLevel(); level < vip.getMaxLevel(); level++)
			if (exp >= getMaxExp(level) && exp < getMaxExp(level + 1))
				return level + 1;
		return vip.getMaxLevel();
	}

	public abstract int getMaxExp(int level);

	public String getName() {
		return getClass().getSimpleName();
	}

	public int addExp(int exp) {
		this.exp += exp;
		return this.exp;
	}

	public int redExp(int exp) {
		this.exp -= exp;
		return this.exp;
	}

	public int setExp(int exp) {
		return this.exp = exp;
	}

	public int delExp(int exp) {
		return redExp(exp);
	}

}
