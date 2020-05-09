package cn.winfxk.brassiere.vip;

import cn.nukkit.Player;
import cn.nukkit.form.response.FormResponse;
import cn.nukkit.item.Item;
import cn.winfxk.brassiere.form.FormBase;
import cn.winfxk.brassiere.tool.SimpleForm;
import cn.winfxk.brassiere.tool.Tool;

/**
 * VIP签到功能页
 * 
 * @Createdate 2020/05/02 08:05:33
 * @author Winfxk
 */
public class VipSign extends VipForm {

	public VipSign(Player player, FormBase upForm) {
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
		if (myPlayer.getConfig().get("VipSign") != null
				&& myPlayer.getConfig().getString("VipSign").equals(Tool.getDate())) {
			player.sendMessage(getString("isSign"));
			return upForm == null ? true : setForm(upForm).make();
		}
		myPlayer.getConfig().set("VipSign", Tool.getDate());
		myPlayer.getConfig().save();
		myPlayer.addVipExp(vip.getSignExp());
		if (vip.getSignEconomy() != null)
			vip.getSignEconomy().addMoney(player, vip.getSignMoney());
		for (Item item : vip.getSignItem())
			player.getInventory().addItem(item);
		SimpleForm form = new SimpleForm(getID(), getString(Title), getString(Content));
		form.addButton(getString(upForm == null ? Close : Back));
		form.sendPlayer(player);
		return true;
	}

	@Override
	public boolean disMain(FormResponse data) {
		return upForm == null ? true : setForm(upForm).make();
	}
}
