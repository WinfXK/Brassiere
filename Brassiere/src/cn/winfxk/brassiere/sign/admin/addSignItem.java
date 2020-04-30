package cn.winfxk.brassiere.sign.admin;

import cn.nukkit.Player;
import cn.nukkit.form.response.FormResponse;
import cn.nukkit.form.response.FormResponseCustom;
import cn.winfxk.brassiere.form.FormBase;
import cn.winfxk.brassiere.money.MyEconomy;
import cn.winfxk.brassiere.sign.SignMag;
import cn.winfxk.brassiere.tool.CustomForm;
import cn.winfxk.brassiere.tool.Tool;

/**
 * 添加商店项目
 * 
 * @Createdate 2020/04/18 22:00:29
 * @author Winfxk
 */
public class addSignItem extends FormBase {
	private String Key;

	public addSignItem(Player player) {
		this(player, null);
	}

	public addSignItem(Player player, String Key) {
		super(player);
		this.Key = Key;
		setK("{Player}", "{Money}", "{Sign}");
	}

	public boolean Main(String Sign, double Money, MyEconomy economy) {
		int pos = 0;
		if (ac.getEconomyManage().supportEconomy(economy.getEconomyName()))
			pos = ac.getEconomyManage().getEconomys().indexOf(economy);
		setD(player.getName(), myPlayer.getMoney(), myPlayer.getSign());
		CustomForm form = new CustomForm(getID(), msg.getSun("Sign", "addSignShop", "Title", K, D));
		form.addInput(msg.getSun("Sign", "addSignShop", "Sign", K, D), Sign == null ? "" : Sign,
				msg.getSun("Sign", "addSignShop", "Sign", K, D));
		form.addInput(msg.getSun("Sign", "addSignShop", "Price", K, D), Money,
				msg.getSun("Sign", "addSignShop", "Price", K, D));
		form.addDropdown(msg.getSun("Sign", "addSignShop", "Economy", K, D), ac.getEconomyManage().getEconomy(), pos);
		form.sendPlayer(player);
		return true;
	}

	@Override
	public boolean disMain(FormResponse data) {
		FormResponseCustom d = getCustom(data);
		String Sign = d.getInputResponse(0);
		if (Sign == null || Sign.isEmpty()) {
			player.sendMessage(msg.getSun("Sign", "addSignShop", "SignNull", K, D));
			return Main(Sign, Tool.objToDouble(d.getInputResponse(1)),
					ac.getEconomyManage().getEconomys().get(d.getDropdownResponse(2).getElementID()));
		}
		double Money = 0;
		String string = d.getInputResponse(1);
		if (string == null || string.isEmpty() || Tool.isInteger(string)
				|| (Money = Tool.objToDouble(string, 0d)) <= 0) {
			player.sendMessage(msg.getSun("Sign", "addSignShop", "PriceError", K, D));
			return Main(Sign, Tool.objToDouble(d.getInputResponse(1)),
					ac.getEconomyManage().getEconomys().get(d.getDropdownResponse(2).getElementID()));

		}
		String economy = d.getDropdownResponse(2).getElementContent();
		if (Key == null || Key.isEmpty())
			SignMag.getSignMag().addSignData(Sign, Money, economy);
		else
			SignMag.getSignMag().setSignData(Key, Sign, Money, economy);
		player.sendMessage(msg.getSun("Sign", "addSignShop", "OK", K, D));
		return SignMag.getSignMag().makeMain(player);
	}

	@Override
	public boolean MakeMain() {
		return Main("", 0d, ac.getEconomy());
	}
}
