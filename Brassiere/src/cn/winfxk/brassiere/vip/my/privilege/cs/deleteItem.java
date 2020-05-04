package cn.winfxk.brassiere.vip.my.privilege.cs;

import cn.nukkit.Player;
import cn.nukkit.form.response.FormResponse;
import cn.winfxk.brassiere.form.FormBase;
import cn.winfxk.brassiere.vip.VipForm;

/**
 * 删除一个云端物品
 * 
 * @Createdate 2020/05/04 10:42:28
 * @author Winfxk
 */
public class deleteItem extends VipForm {
	private String Key;

	public deleteItem(Player player, FormBase upForm, String Key) {
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
