package cn.winfxk.brassiere.sign.admin;

import cn.nukkit.Player;
import cn.nukkit.form.response.FormResponse;
import cn.winfxk.brassiere.form.FormBase;
import cn.winfxk.brassiere.tool.CustomForm;

/**
 * 添加商店项目
 * 
 * @Createdate 2020/04/18 22:00:29
 * @author Winfxk
 */
public class addSignItem extends FormBase {

	public addSignItem(Player player) {
		super(player);
		setK("{Player}", "{Money}", "{Sign}");
	}

	@Override
	public boolean MakeMain() {
		setD(player.getName(), myPlayer.getMoney(), myPlayer.getSign());
		CustomForm form = new CustomForm(getID(), msg.getSun("Sign", "addSignShop", "Title", K, D));
		form.addInput(msg.getSun("Sign", "addSignShop", "Sign", K, D), "",
				msg.getSun("Sign", "addSignShop", "Sign", K, D));
		form.addInput(msg.getSun("Sign", "addSignShop", "Price", K, D), "",
				msg.getSun("Sign", "addSignShop", "Price", K, D));
		form.addDropdown(msg.getSun("Sign", "addSignShop", "Economy", K, D), ac.getEconomyManage().getEconomy());
		form.sendPlayer(player);
		return true;
	}

	@Override
	public boolean disMain(FormResponse data) {
		return true;
	}
}
