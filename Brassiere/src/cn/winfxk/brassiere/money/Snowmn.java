package cn.winfxk.brassiere.money;

import cn.epicfx.winfxk.money.sn.Money;
import cn.winfxk.brassiere.Activate;

/**
 * @author Winfxk
 */
public class Snowmn extends MyEconomy {
	public final static String Name = "Snowmn";

	public Snowmn(Activate ac) {
		super(Name, ac.getConfig().getString("Snowmn货币名称"));
	}

	@Override
	public double getMoney(String player) {
		return Money.getMoney(player);
	}

	@Override
	public double addMoney(String player, double money) {
		return Money.addMoney(player, money);
	}

	@Override
	public double reduceMoney(String player, double money) {
		return Money.redMoney(player, money);
	}

	@Override
	public double setMoney(String player, double money) {
		return Money.setMoney(player, money);
	}

}
