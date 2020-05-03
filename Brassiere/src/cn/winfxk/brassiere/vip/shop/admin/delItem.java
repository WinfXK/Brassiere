package cn.winfxk.brassiere.vip.shop.admin;

import java.util.Map;

import cn.nukkit.Player;
import cn.nukkit.form.response.FormResponse;
import cn.winfxk.brassiere.form.FormBase;
import cn.winfxk.brassiere.tool.SimpleForm;
import cn.winfxk.brassiere.tool.Tool;
import cn.winfxk.brassiere.vip.Vip;
import cn.winfxk.brassiere.vip.VipForm;
import cn.winfxk.brassiere.vip.shop.ShopMain;

/**
 * 删除现有的商店项目
 * 
 * @Createdate 2020/05/03 07:51:59
 * @author Winfxk
 */
public class delItem extends VipForm {
	private String Key;

	/**
	 * 删除一个商店项目
	 * 
	 * @param player 处理事件的玩家对象
	 * @param upForm 上级目录
	 * @param Key    要删除的项目
	 */
	public delItem(Player player, FormBase upForm, String Key) {
		super(player, upForm);
		this.Key = Key;
		setK(ShopMain.KK);
	}

	@Override
	public boolean MakeMain() {
		Map<String, Object> map = (Map<String, Object>) vm.getShop().get(Key);
		Vip vip = vm.getVip(Tool.objToString(map.get("ID")));
		setD(player.getName(), myPlayer.getMoney(), getVip(), vip.getName(), vip.getID(), map.get("Money"),
				map.get("BuyTime"), map.get("Economy"), map.get("BuyLevel"), map.get("Repeat"));
		SimpleForm form = new SimpleForm(getID(), getString(Title), getString(Content));
		form.addButton(getString("OK"));
		form.addButton(getString(upForm == null ? Close : Back));
		form.sendPlayer(player);
		return true;
	}

	@Override
	public boolean disMain(FormResponse data) {
		if (getSimple(data).getClickedButtonId() == 0) {
			vm.getShop().remove(Key);
			vm.getShop().save();
			player.sendMessage(getString("delOK"));
		}
		return upForm != null ? setForm(upForm).make() : true;
	}
}
