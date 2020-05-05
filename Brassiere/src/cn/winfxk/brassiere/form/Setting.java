package cn.winfxk.brassiere.form;

import cn.nukkit.Player;
import cn.nukkit.form.response.FormResponse;
import cn.winfxk.brassiere.Activate;

/**
 * 管理员设置插件页面
 * 
 * @Createdate 2020/05/05 22:03:16
 * @author Winfxk
 */
public class Setting extends MainBase {
	private Activate activate;

	public Setting(Player player, FormBase form) {
		super(player, form);
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
