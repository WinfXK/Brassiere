package cn.winfxk.brassiere.vip;

import cn.nukkit.Player;
import cn.nukkit.form.response.FormResponse;
import cn.winfxk.brassiere.form.FormBase;
import cn.winfxk.brassiere.tool.SimpleForm;
import cn.winfxk.brassiere.vip.my.ExitVip;
import cn.winfxk.brassiere.vip.my.MyPrivilege;
import cn.winfxk.brassiere.vip.shop.ShopMain;

/**
 * 我的VIP页面
 * 
 * @Createdate 2020/05/02 07:54:20
 * @author Winfxk
 */
public class MyVip extends VipForm {

	public MyVip(Player player, FormBase upForm) {
		super(player, upForm);
		setK("{Player}", "{Money}", "{VipName}", "{VipID}", "{VipLevel}", "{VipExp}", "{VipTime}");
	}

	@Override
	public boolean MakeMain() {
		setD(player.getName(), myPlayer.getMoney(), myPlayer.isVip() ? myPlayer.vip.getName() : notVip,
				myPlayer.isVip() ? myPlayer.vip.getID() : notVip,
				myPlayer.isVip() ? myPlayer.vip.getAlg().getLevel(player.getName()) : notVip,
				VipApi.getLevel(player.getName()), VipApi.getTime(player.getName()));
		SimpleForm form = new SimpleForm(getID(), getString(Title), getString(Content));
		listKey.add("b");
		form.addButton(getString("BuyVIp"));
		if (myPlayer.isVip()) {
			if (ac.getConfig().getBoolean("允许玩家退出VIP")) {
				listKey.add("e");
				form.addButton(getString("ExitVip"));
			}
			listKey.add("m");
			form.addButton(getString("MyVip"));
		}
		listKey.add("c");
		form.addButton(getString(upForm == null ? Close : Back));
		form.sendPlayer(player);
		return true;
	}

	@Override
	public boolean disMain(FormResponse data) {
		switch (listKey.get(getSimple(data).getClickedButtonId())) {
		case "b":
			setForm(new ShopMain(player, this));
			break;
		case "e":
			setForm(new ExitVip(player, this));
			break;
		case "m":
			setForm(new MyPrivilege(player, this));
			break;
		case "c":
			return upForm == null ? true : setForm(upForm).make();
		}
		return make();
	}
}
