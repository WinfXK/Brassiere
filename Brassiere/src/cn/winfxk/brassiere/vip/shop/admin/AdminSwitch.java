package cn.winfxk.brassiere.vip.shop.admin;

import java.util.Map;

import cn.nukkit.Player;
import cn.nukkit.form.response.FormResponse;
import cn.winfxk.brassiere.form.FormBase;
import cn.winfxk.brassiere.tool.SimpleForm;
import cn.winfxk.brassiere.tool.Tool;
import cn.winfxk.brassiere.vip.Vip;
import cn.winfxk.brassiere.vip.VipForm;
import cn.winfxk.brassiere.vip.shop.BuyVip;
import cn.winfxk.brassiere.vip.shop.ShopMain;

/**
 * 管理员点击了一个商店项目，现在提供一些选项
 * 
 * @Createdate 2020/05/03 07:57:38
 * @author Winfxk
 */
public class AdminSwitch extends VipForm {
	private String Key;

	/**
	 * 管理员点击了一个商店项目，现在提供一些选项
	 * 
	 * @param player 触发事件的玩家对象
	 * @param upForm 上级页面
	 * @param Key    点击的商店项目Key
	 */
	public AdminSwitch(Player player, FormBase upForm, String Key) {
		super(player, upForm);
		this.Key = Key;
		setK(ShopMain.KK);
	}

	@Override
	public boolean MakeMain() {
		Map<String, Object> map = (Map<String, Object>) vm.getShop().get(Key);
		Vip vip = vm.getVip(Tool.objToString(map.get("ID")));
		setD(player.getName(), myPlayer.getMoney(), getVip(), vip.getName(), vip.getID(), map.get("Money"),
				map.get("BuyTime"), ac.getEconomyManage().getEconomy(Tool.objToString(map.get("Economy"))),
				map.get("BuyLevel"), map.get("Repeat"));
		SimpleForm form = new SimpleForm(getID(), getString(Title), getString(Content));
		form.addButton(getString("addShop"));
		form.addButton(getString("delItem"));
		form.addButton(getString("BuyItem"));
		form.addButton(getString(upForm == null ? Close : Back));
		form.sendPlayer(player);
		return true;
	}

	@Override
	public boolean disMain(FormResponse data) {
		switch (getSimple(data).getClickedButtonId()) {
		case 0:
			setForm(new addShop(player, upForm));
			break;
		case 1:
			setForm(new delItem(player, this, Key));
			break;
		case 2:
			setForm(new BuyVip(player, this, Key));
			break;
		case 3:
		default:
			return upForm == null ? true : setForm(upForm).make();
		}
		return make();
	}
}
