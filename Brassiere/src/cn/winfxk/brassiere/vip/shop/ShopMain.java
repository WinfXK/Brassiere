package cn.winfxk.brassiere.vip.shop;

import java.util.HashMap;
import java.util.Map;

import cn.nukkit.Player;
import cn.nukkit.form.response.FormResponse;
import cn.winfxk.brassiere.form.FormBase;
import cn.winfxk.brassiere.tool.SimpleForm;
import cn.winfxk.brassiere.tool.Tool;
import cn.winfxk.brassiere.vip.Vip;
import cn.winfxk.brassiere.vip.VipForm;
import cn.winfxk.brassiere.vip.shop.admin.AdminSwitch;
import cn.winfxk.brassiere.vip.shop.admin.addShop;

/**
 * VIP商店主页
 * 
 * @Createdate 2020/05/02 08:06:09
 * @author Winfxk
 */
public class ShopMain extends VipForm {
	private Map<String, Object> all;
	public static final String[] KK = { "{Player}", "{Money}", "{Vip}", "{VipName}", "{VipID}", "{BuyMoney}",
			"{BuyTime}", "{Economy}", "{BuyLevel}", "{Repeat}" };

	public ShopMain(Player player, FormBase upForm) {
		super(player, upForm);
		setK(Vip.Key);
		all = vm.getShop().getAll();
	}

	@Override
	public boolean MakeMain() {
		setD(player.getName(), myPlayer.getMoney(), getVip());
		if (all.size() <= 0)
			return ac.makeForm.Tip(player, getString("NotItem"));
		SimpleForm form = new SimpleForm(getID(), getString(Title), getString(Content));
		Map<String, Object> map;
		Vip vip;
		for (Map.Entry<String, Object> entry : all.entrySet()) {
			map = entry.getValue() != null && entry.getValue() instanceof Map
					? (HashMap<String, Object>) entry.getValue()
					: new HashMap<>();
			if (map.size() <= 0)
				continue;
			vip = map.get("ID") != null ? vm.getVip(Tool.objToString(map.get("ID"))) : null;
			if (vip == null)
				continue;
			form.addButton(msg.getSun(t, Son, "VipItem", KK,
					new Object[] { player.getName(), myPlayer.getMoney(), getVip(), vip.getName(), vip.getID(),
							map.get("Money"), map.get("BuyTime"), map.get("Economy"), map.get("BuyLevel"),
							map.get("Repeat") }));
			listKey.add(entry.getKey());
		}
		if (form.getButtonSize() <= 0)
			return ac.makeForm.Tip(player, getString("NotItem"));
		form.addButton(getString(upForm == null ? Close : Back));
		if (myPlayer.isAdmin())
			form.addButton(getString("addItem"));
		form.sendPlayer(player);
		return true;
	}

	@Override
	public boolean disMain(FormResponse data) {
		int ID = getSimple(data).getClickedButtonId();
		return listKey.size() == ID ? upForm == null ? true : setForm(upForm).make()
				: setForm(listKey.size() < ID ? new addShop(player, this)
						: myPlayer.isAdmin() ? new AdminSwitch(player, this, listKey.get(ID))
								: new BuyVip(player, this, listKey.get(ID))).make();
	}
}
