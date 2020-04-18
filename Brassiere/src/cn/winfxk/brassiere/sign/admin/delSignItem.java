package cn.winfxk.brassiere.sign.admin;

import cn.nukkit.Player;
import cn.nukkit.form.response.FormResponse;
import cn.winfxk.brassiere.form.FormBase;

/**
 * 删除商店项目
 * 
 * @Createdate 2020/04/18 22:00:38
 * @author Winfxk
 */
public class delSignItem extends FormBase {

	public delSignItem(Player player) {
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
