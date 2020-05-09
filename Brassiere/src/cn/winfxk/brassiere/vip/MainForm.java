package cn.winfxk.brassiere.vip;

import cn.nukkit.Player;
import cn.nukkit.form.response.FormResponse;
import cn.winfxk.brassiere.form.FormBase;
import cn.winfxk.brassiere.tool.SimpleForm;
import cn.winfxk.brassiere.tool.Tool;
import cn.winfxk.brassiere.vip.shop.ShopMain;

/**
 * 构建显示VIP主页
 * 
 * @Createdate 2020/05/02 07:13:02
 * @author Winfxk
 */
public class MainForm extends VipForm {
	public MainForm(Player player, FormBase form) {
		super(player, form);
		setK(Vip.Key);
		Son = "Main";
	}

	@Override
	public boolean MakeMain() {
		setD(player.getName(), myPlayer.getMoney(), getVip());
		SimpleForm form = new SimpleForm(getID(), getString(Title), getString(Content));
		if (!myPlayer.getConfig().getString("VipSign").equals(Tool.getDate())) {
			listKey.add("s");
			form.addButton(getString("VipSign"));
		}
		if (myPlayer.isVip()) {
			listKey.add("m");
			form.addButton(getString("MyVip"));
		}
		listKey.add("v");
		form.addButton(getString("VipShop"));
		listKey.add(upForm == null ? "c" : "b");
		form.addButton(msg.getSon(t, upForm == null ? Close : Back, this));
		form.sendPlayer(player);
		return true;
	}

	@Override
	public boolean disMain(FormResponse data) {
		switch (listKey.get(getSimple(data).getClickedButtonId())) {
		case "s":
			setForm(new VipSign(player, this));
			break;
		case "v":
			setForm(new ShopMain(player, this));
			break;
		case "b":
			setForm(upForm == null ? this : upForm);
			break;
		case "m":
			setForm(new MyVip(player, this));
			break;
		default:
			return false;
		}
		return make();
	}
}
