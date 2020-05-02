package cn.winfxk.brassiere.vip;

import cn.nukkit.Player;
import cn.nukkit.form.response.FormResponse;
import cn.winfxk.brassiere.form.FormBase;

/**
 * 我的VIP页面
 * 
 * @Createdate 2020/05/02 07:54:20
 * @author Winfxk
 */
public class MyVip extends VipForm {

	public MyVip(Player player, FormBase upForm) {
		super(player, upForm);
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
