package cn.winfxk.brassiere.vip.my.privilege.cs;

import cn.nukkit.Player;
import cn.nukkit.form.response.FormResponse;
import cn.winfxk.brassiere.form.FormBase;
import cn.winfxk.brassiere.vip.VipForm;

/**
 * 玩家点击了一个物品，这个页面为再次上传物品的页面
 * 
 * @Createdate 2020/05/04 10:56:18
 * @author Winfxk
 */
public class ReplenishItem extends VipForm {
	private String Key;

	public ReplenishItem(Player player, FormBase upForm, String Key) {
		super(player, upForm);
		this.Key = Key;
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
