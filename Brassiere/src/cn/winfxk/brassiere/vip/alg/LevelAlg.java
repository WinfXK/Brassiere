package cn.winfxk.brassiere.vip.alg;

import cn.winfxk.brassiere.vip.Vip;
import cn.winfxk.brassiere.vip.VipApi;

/**
 * VIp升计算法基础类
 * 
 * @author Winfxk
 */
public abstract class LevelAlg {
	public Vip vip;

	public LevelAlg(Vip vip) {
		this.vip = vip;
	}

	/**
	 * 获取一个计算器
	 *
	 * @param string
	 * @return
	 */
	public static LevelAlg getAlg(String string, Vip vip) {
		string = string == null ? "" : string;
		LevelAlg alg;
		switch (string.toLowerCase()) {
		case "impossib":
			alg = new Impossib(vip);
			break;
		case "hard":
			alg = new Hard(vip);
		case "general":
			alg = new General(vip);
			break;
		default:
			alg = new Simple(vip);
			break;
		}
		return alg;
	}

	/**
	 * 获取一个玩家的等级
	 * 
	 * @param player
	 * @return
	 */
	public int getLevel(String player) {
		if (!VipApi.isVip(player) || VipApi.getVip(player).getID() != vip.getID())
			return 0;
		return getLevel(VipApi.getLevel(player));
	}

	/**
	 * 获取一个玩家的等级
	 * 
	 * @param exp 玩家的特权经验点
	 * @return
	 */
	public int getLevel(int exp) {
		for (int level = vip.getMinLevel(); level < vip.getMaxLevel(); level++)
			if (exp >= getMaxExp(level) && exp < getMaxExp(level + 1))
				return level + 1;
		return vip.getMaxLevel();
	}

	/**
	 * 子类记成，用于实现算法
	 * 
	 * @param level
	 * @return
	 */
	protected abstract int getMaxExp(int level);

	/**
	 * 获取算法名称
	 * 
	 * @return
	 */
	public String getName() {
		return getClass().getSimpleName();
	}

	@Override
	public String toString() {
		return getName() + "(" + vip.getID() + "-" + vip.getName() + ")";
	}
}
