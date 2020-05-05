package cn.winfxk.brassiere.vip.my.privilege;

import java.util.ArrayList;
import java.util.List;

import cn.nukkit.Player;
import cn.nukkit.Server;
import cn.nukkit.form.response.FormResponse;
import cn.winfxk.brassiere.form.FormBase;
import cn.winfxk.brassiere.tool.SimpleForm;
import cn.winfxk.brassiere.vip.VipApi;
import cn.winfxk.brassiere.vip.VipForm;

/**
 * 玩家可以传送，将要选择传送目标的页面
 * 
 * @Createdate 2020/05/04 09:10:10
 * @author Winfxk
 */
public class Transfer extends VipForm {
	private List<Player> list;

	public Transfer(Player player, FormBase upForm) {
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
		if (!vip.isSimpleTP() && !vip.isTP()) {
			player.sendMessage(getString("NotTransfer"));
			return upForm == null ? true : setForm(upForm).make();
		}
		SimpleForm form = new SimpleForm(getID(), getString(Title), getString(Content));
		list = new ArrayList<>();
		for (Player p : Server.getInstance().getOnlinePlayers().values()) {
			if (p.getName().equals(player.getName()))
				continue;
			form.addButton(msg.getText(getString("PlayerItem"), "{ByPlayer}", p.getName()));
			list.add(p);
		}
		if (list.size() <= 0) {
			player.sendMessage(getString("NotPlayer"));
			return upForm == null ? true : setForm(upForm).make();
		}
		form.addButton(getString(upForm == null ? Close : Back));
		form.sendPlayer(player);
		return true;
	}

	@Override
	public boolean disMain(FormResponse data) {
		int ID = getSimple(data).getClickedButtonId();
		if (ID >= list.size())
			return upForm == null ? true : setForm(upForm).make();
		Player p = list.get(ID);
		if (vip.isTP()) {
			player.teleport(p);
			vip.sendTPSoundName(p);
			vip.sendTPSoundName(player);
			player.sendMessage(msg.getText(getString("Transfer"), "{ByPlayer}", p.getName()));
			return true;
		}
		player.sendMessage(msg.getText(getString("TransferRequest"), "{ByPlayer}", p.getName()));
		return setForm(new acceptTransfer(list.get(ID), p)).make();
	}
}
