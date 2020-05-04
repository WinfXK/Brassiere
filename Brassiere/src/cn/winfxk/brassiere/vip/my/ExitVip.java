package cn.winfxk.brassiere.vip.my;

import cn.nukkit.Player;
import cn.nukkit.form.response.FormResponse;
import cn.winfxk.brassiere.form.FormBase;
import cn.winfxk.brassiere.tool.SimpleForm;
import cn.winfxk.brassiere.vip.VipApi;
import cn.winfxk.brassiere.vip.VipForm;

/**
 * 玩家点击了退出VIP的处理页
 * 
 * @Createdate 2020/05/04 07:23:46
 * @author Winfxk
 */
public class ExitVip extends VipForm {

	public ExitVip(Player player, FormBase upForm) {
		super(player, upForm);
		setK("{Player}", "{Money}", "{VipName}", "{VipID}", "{VipLevel}", "{VipExp}", "{VipTime}");
	}

	@Override
	public boolean MakeMain() {
		setD(player.getName(), myPlayer.getMoney(), myPlayer.isVip() ? myPlayer.vip.getName() : notVip,
				myPlayer.isVip() ? myPlayer.vip.getID() : notVip,
				myPlayer.isVip() ? myPlayer.vip.getAlg().getLevel(player.getName()) : notVip,
				VipApi.getLevel(player.getName()), VipApi.getTime(player.getName()));
		if (ac.getConfig().getBoolean("允许玩家退出VIP")) {
			player.sendMessage(getString("exitVip"));
			return upForm == null ? true : setForm(upForm).make();
		}
		SimpleForm form = new SimpleForm(getID(), getString(Title), getString(Content));
		form.addButton(getString("Ok"));
		form.addButton(getString(upForm == null ? Close : Back));
		form.sendPlayer(player);
		return true;
	}

	@Override
	public boolean disMain(FormResponse data) {
		if (getSimple(data).getClickedButtonId() != 0)
			return upForm == null ? true : setForm(upForm).make();
		VipApi.remove(player.getName());
		player.sendMessage(getString("ExitOK"));
		return upForm == null ? true : setForm(upForm).make();
	}
}
