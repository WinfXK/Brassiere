package cn.winfxk.brassiere.vip.shop;

import cn.nukkit.Player;
import cn.nukkit.form.response.FormResponse;
import cn.winfxk.brassiere.form.FormBase;
import cn.winfxk.brassiere.tool.SimpleForm;
import cn.winfxk.brassiere.vip.Vip;
import cn.winfxk.brassiere.vip.VipForm;

/**
 * VIP商店主页
 * 
 * @Createdate 2020/05/02 08:06:09
 * @author Winfxk
 */
public class ShopMain extends VipForm {

	public ShopMain(Player player, FormBase upForm) {
		super(player, upForm);
		setK(Vip.Key);
	}

	@Override
	public boolean MakeMain() {
		setD(player.getName(), myPlayer.getMoney(), getVip());
		SimpleForm form = new SimpleForm(getID(), getString(Title), getString(Content));
		
		return true;
	}

	@Override
	public boolean disMain(FormResponse data) {
		return true;
	}
}
