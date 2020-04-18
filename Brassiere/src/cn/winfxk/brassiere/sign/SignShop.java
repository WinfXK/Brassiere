package cn.winfxk.brassiere.sign;

import cn.nukkit.Player;
import cn.nukkit.form.response.FormResponse;
import cn.winfxk.brassiere.form.FormBase;

/**
 * 称号商店页
 * 
 * @Createdate 2020/04/18 21:06:32
 * @author Winfxk
 */
public class SignShop extends FormBase {

	public SignShop(Player player) {
		super(player);
	}

	@Override
	public boolean MakeMain() {
		return false;
	}

	@Override
	public boolean disMain(FormResponse data) {
		return false;
	}
}
