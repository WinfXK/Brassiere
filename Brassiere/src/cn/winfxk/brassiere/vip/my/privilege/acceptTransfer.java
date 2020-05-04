package cn.winfxk.brassiere.vip.my.privilege;

import cn.nukkit.Player;
import cn.nukkit.form.response.FormResponse;
import cn.winfxk.brassiere.tool.SimpleForm;
import cn.winfxk.brassiere.vip.VipApi;
import cn.winfxk.brassiere.vip.VipForm;

/**
 * 玩家发送传送请求后的确认页
 * 
 * @Createdate 2020/05/04 09:39:05
 * @author Winfxk
 */
public class acceptTransfer extends VipForm {
	private Player p;

	/**
	 * 显示一个请求页面，请求成功后传送
	 * 
	 * @param player 被请求玩家
	 * @param p      发送请求的玩家
	 */
	public acceptTransfer(Player player, Player p) {
		super(player, null);
		this.p = p;
		setK("{ByPlayer}", "{Player}", "{Money}", "{VipName}", "{VipID}", "{VipLevel}", "{VipExp}", "{VipTime}");
	}

	@Override
	public boolean MakeMain() {
		setD(p.getName(), player.getName(), myPlayer.getMoney(), myPlayer.isVip() ? myPlayer.vip.getName() : notVip,
				myPlayer.isVip() ? myPlayer.vip.getID() : notVip,
				myPlayer.isVip() ? myPlayer.vip.getAlg().getLevel(player.getName()) : notVip,
				VipApi.getLevel(player.getName()), VipApi.getTime(player.getName()));
		SimpleForm form = new SimpleForm(getID(), getString(Title), getString(Content));
		form.addButton(getString("Accept"));
		form.addButton(getString("Refuse"));
		form.sendPlayer(player);
		return true;
	}

	@Override
	public boolean disMain(FormResponse data) {
		if (getSimple(data).getClickedButtonId() == 0) {
			p.sendMessage(getString("AcceptRequest"));
			p.teleport(player);
			return true;
		}
		p.sendMessage(getString("RefuseRequest"));
		return true;
	}
}
