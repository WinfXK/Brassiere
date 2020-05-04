package cn.winfxk.brassiere.vip.my.privilege;

import cn.nukkit.Player;
import cn.nukkit.form.response.FormResponse;
import cn.winfxk.brassiere.form.FormBase;
import cn.winfxk.brassiere.tool.SimpleForm;
import cn.winfxk.brassiere.vip.VipApi;
import cn.winfxk.brassiere.vip.VipForm;

/**
 * 玩家可以切换的游戏模式选择页
 * 
 * @Createdate 2020/05/04 09:09:35
 * @author Winfxk
 */
public class Gamemode extends VipForm {

	public Gamemode(Player player, FormBase upForm) {
		super(player, upForm);
		setK("{Player}", "{Money}", "{VipName}", "{VipID}", "{VipLevel}", "{VipExp}", "{VipTime}");
	}

	@Override
	public boolean MakeMain() {
		setD(player.getName(), myPlayer.getMoney(), myPlayer.isVip() ? myPlayer.vip.getName() : notVip,
				myPlayer.isVip() ? myPlayer.vip.getID() : notVip,
				myPlayer.isVip() ? myPlayer.vip.getAlg().getLevel(player.getName()) : notVip,
				VipApi.getLevel(player.getName()), VipApi.getTime(player.getName()));
		if (!myPlayer.isVip()) {
			player.sendMessage(getString("NotVip"));
			return upForm == null ? true : setForm(upForm).make();
		}
		SimpleForm form = new SimpleForm(getID(), getString(Title), getString(Content));
		if (vip.isGamemode(0)) {
			listKey.add("s");
			form.addButton(getString("Survival"));
		}
		if (vip.isGamemode(1)) {
			listKey.add("c");
			form.addButton(getString("Create"));
		}
		if (vip.isGamemode(2)) {
			listKey.add("a");
			form.addButton(getString("Adventure"));
		}
		if (form.getButtonSize() <= 0) {
			player.sendMessage(getString("NotGamemode"));
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
		case "s":
			if (player.getGamemode() == 0) {
				player.sendMessage(msg.getText(getString("isGamemode"), "{Gamemode}", getString("Survival")));
				return MakeMain();
			}
			player.setGamemode(0);
			player.sendMessage(msg.getText(getString("setGamemode"), "{Gamemode}", getString("Survival")));
			break;
		case "a":
			if (player.getGamemode() == 2) {
				player.sendMessage(msg.getText(getString("isGamemode"), "{Gamemode}", getString("Adventure")));
				return MakeMain();
			}
			player.setGamemode(0);
			player.sendMessage(msg.getText(getString("setGamemode"), "{Gamemode}", getString("Adventure")));
			break;
		case "c":
			if (player.getGamemode() == 0) {
				player.sendMessage(msg.getText(getString("isGamemode"), "{Gamemode}", getString("Create")));
				return MakeMain();
			}
			player.setGamemode(0);
			player.sendMessage(msg.getText(getString("setGamemode"), "{Gamemode}", getString("Create")));
			break;
		}
		return upForm == null ? true : setForm(upForm).make();
	}
}
