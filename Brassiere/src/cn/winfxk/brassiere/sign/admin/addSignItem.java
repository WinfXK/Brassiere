package cn.winfxk.brassiere.sign.admin;

import cn.nukkit.Player;
import cn.nukkit.form.response.FormResponse;
import cn.winfxk.brassiere.form.FormBase;

/**
 * 添加商店项目
 * 
 * @Createdate 2020/04/18 22:00:29
 * @author Winfxk
 */
public class addSignItem extends FormBase {

	public addSignItem(Player player) {
		super(player);
	}

	@Override
	public boolean MakeMain() {
		return true;
	}

	@Override
	public boolean disMain(FormResponse data) {
		return true;
	}
}
