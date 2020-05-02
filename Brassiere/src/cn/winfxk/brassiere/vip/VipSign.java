package cn.winfxk.brassiere.vip;

import cn.nukkit.Player;
import cn.nukkit.form.response.FormResponse;
import cn.winfxk.brassiere.form.FormBase;

/**
 * VIP签到功能页
 * 
 * @Createdate 2020/05/02 08:05:33
 * @author Winfxk
 */
public class VipSign extends VipForm {

	public VipSign(Player player, FormBase upForm) {
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
