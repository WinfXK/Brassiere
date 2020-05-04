package cn.winfxk.brassiere.vip.my;

import cn.nukkit.Player;
import cn.nukkit.form.response.FormResponse;
import cn.winfxk.brassiere.form.FormBase;
import cn.winfxk.brassiere.tool.SimpleForm;
import cn.winfxk.brassiere.vip.VipApi;
import cn.winfxk.brassiere.vip.VipForm;
import cn.winfxk.brassiere.vip.my.privilege.CloudStorage;
import cn.winfxk.brassiere.vip.my.privilege.Gamemode;
import cn.winfxk.brassiere.vip.my.privilege.Transfer;

/**
 * 显示我的特权页
 * 
 * @Createdate 2020/05/04 07:24:53
 * @author Winfxk
 */
public class MyPrivilege extends VipForm {

	public MyPrivilege(Player player, FormBase upForm) {
		super(player, upForm);
		setK("{Player}", "{Money}", "{VipName}", "{VipID}", "{VipLevel}", "{VipExp}", "{VipTime}");
	}

	@Override
	public boolean MakeMain() {
		setD(player.getName(), myPlayer.getMoney(), myPlayer.isVip() ? vip.getName() : notVip,
				myPlayer.isVip() ? vip.getID() : notVip,
				myPlayer.isVip() ? vip.getAlg().getLevel(player.getName()) : notVip, VipApi.getLevel(player.getName()),
				VipApi.getTime(player.getName()));
		if (!myPlayer.isVip()) {
			player.sendMessage(getString("NotVip"));
			return upForm == null ? true : setForm(upForm).make();
		}
		SimpleForm form = new SimpleForm(getID(), getString(Title), getString(Content));
		if (vip.isGamemode()) {
			listKey.add("g");
			form.addButton(getString("Gamemode"));
		}
		if (vip.isSimpleTP() || vip.isTP()) {
			listKey.add("t");
			form.addButton(getString("Transfer"));
		}
		if (vip.isCloudStorage()) {
			listKey.add("c");
			form.addButton(getString("CloudStorage"));
		}
		if (form.getButtonSize() <= 0) {
			player.sendMessage(getString("NotPrivilege"));
			return upForm == null ? true : setForm(upForm).make();
		}
		listKey.add("b");
		form.addButton(getString(upForm == null ? Close : Back));
		form.sendPlayer(player);
		return true;
	}

	@Override
	public boolean disMain(FormResponse data) {
		switch (listKey.get(getSimple(data).getClickedButtonId())) {
		case "g":
			setForm(new Gamemode(player, this));
			break;
		case "t":
			setForm(new Transfer(player, this));
			break;
		case "c":
			setForm(new CloudStorage(player, this));
			break;
		case "b":
		default:
			return upForm == null ? true : setForm(upForm).make();
		}
		return make();
	}
}
