package cn.winfxk.brassiere.vip.my.privilege.cs;

import cn.nukkit.Player;
import cn.nukkit.form.response.FormResponse;
import cn.winfxk.brassiere.form.FormBase;
import cn.winfxk.brassiere.vip.VipForm;

/**
 * 上传一个物品到云端仓库
 * 
 * @Createdate 2020/05/04 10:43:04
 * @author Winfxk
 */
public class upItem extends VipForm {
	public upItem(Player player, FormBase upForm) {
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
