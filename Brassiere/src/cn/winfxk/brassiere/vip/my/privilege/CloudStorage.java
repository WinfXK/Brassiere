package cn.winfxk.brassiere.vip.my.privilege;

import java.util.Map;

import cn.nukkit.Player;
import cn.nukkit.form.response.FormResponse;
import cn.nukkit.item.Item;
import cn.winfxk.brassiere.form.FormBase;
import cn.winfxk.brassiere.tool.ItemIDSunName;
import cn.winfxk.brassiere.tool.SimpleForm;
import cn.winfxk.brassiere.tool.Tool;
import cn.winfxk.brassiere.vip.VipApi;
import cn.winfxk.brassiere.vip.VipForm;
import cn.winfxk.brassiere.vip.my.privilege.cs.SwitchItem;
import cn.winfxk.brassiere.vip.my.privilege.cs.SwitchUpItem;

/**
 * 玩家的云端仓库选择页
 * 
 * @Createdate 2020/05/04 09:10:37
 * @author Winfxk
 */
public class CloudStorage extends VipForm {
	private Map<String, Map<String, Object>> items;
	private String[] KK = { "{ItemName}", "{ItemID}", "{ItemCount}" };

	public CloudStorage(Player player, FormBase upForm) {
		super(player, upForm);
		setK("{Player}", "{Money}", "{VipName}", "{VipID}", "{VipLevel}", "{VipExp}", "{VipTime}");
		items = myPlayer.getCloudStorage();
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
		Item item;
		if (vip.isCloudStorage()) {
			player.sendMessage(getString("NotCloudStorage"));
			return upForm == null ? true : setForm(upForm).make();
		}
		SimpleForm form = new SimpleForm(getID(), getString(Title), getString(Content));
		for (String Key : items.keySet()) {
			item = Tool.loadItem(items.get(Key));
			if (item == null)
				continue;
			form.addButton(msg.getText(getString("ItemFormat"), KK,
					new Object[] { ItemIDSunName.getName(item), item.getId(), item.getDamage(), item.getCount() }));
			listKey.add(Key);
		}
		form.addButton(getString("addItem"));
		form.addButton(upForm == null ? Close : Back);
		form.sendPlayer(player);
		return true;
	}

	@Override
	public boolean disMain(FormResponse data) {
		int ID = getSimple(data).getClickedButtonId();
		if (ID == listKey.size())
			return setForm(new SwitchUpItem(player, this)).make();
		if (ID == listKey.size() + 1)
			return upForm == null ? true : setForm(upForm).make();
		return setForm(new SwitchItem(player, this, listKey.get(ID))).make();
	}
}
