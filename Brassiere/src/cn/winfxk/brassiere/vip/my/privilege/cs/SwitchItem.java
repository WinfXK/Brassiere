package cn.winfxk.brassiere.vip.my.privilege.cs;

import cn.nukkit.Player;
import cn.nukkit.form.response.FormResponse;
import cn.nukkit.item.Item;
import cn.winfxk.brassiere.form.FormBase;
import cn.winfxk.brassiere.tool.ItemIDSunName;
import cn.winfxk.brassiere.tool.SimpleForm;
import cn.winfxk.brassiere.tool.Tool;
import cn.winfxk.brassiere.vip.VipApi;
import cn.winfxk.brassiere.vip.VipForm;

/**
 * 玩家点击了一个物品，这是选择要对这个物品干什么
 * 
 * @Createdate 2020/05/04 10:53:11
 * @author Winfxk
 */
public class SwitchItem extends VipForm {
	private String Key;
	private Item item;

	public SwitchItem(Player player, FormBase upForm, String Key) {
		super(player, upForm);
		this.Key = Key;
		item = Tool.loadItem(myPlayer.getCloudStorage() != null ? myPlayer.getCloudStorage().get(Key) : null);
		item = item == null ? new Item(0) : item;
		setK("{Player}", "{Money}", "{VipName}", "{VipID}", "{VipLevel}", "{VipExp}", "{VipTime}", "{ItemName}",
				"{ItemID}", "{ItemCount}", "{ItemDamage}");
	}

	@Override
	public boolean MakeMain() {
		setD(player.getName(), myPlayer.getMoney(), myPlayer.isVip() ? myPlayer.vip.getName() : notVip,
				myPlayer.isVip() ? myPlayer.vip.getID() : notVip,
				myPlayer.isVip() ? myPlayer.vip.getAlg().getLevel(player.getName()) : notVip,
				VipApi.getLevel(player.getName()), VipApi.getTime(player.getName()), ItemIDSunName.getName(item),
				item.getId(), item.getCount(), item.getDamage());
		if (Key == null || item.getId() == 0) {
			player.sendMessage(getString("NotItem"));
			return upForm == null ? true : setForm(upForm).make();
		}
		SimpleForm form = new SimpleForm(getID(), getString(Title), getString(Content));
		listKey.add("d");
		form.addButton(getString("Delete"));
		if (!player.getInventory().isFull()) {
			listKey.add("e");
			form.addButton(getString("Down"));
		}
		if (!player.getInventory().isEmpty()) {
			listKey.add("u");
			form.addButton(getString("UpItem"));
		}
		form.sendPlayer(player);
		return true;
	}

	@Override
	public boolean disMain(FormResponse data) {
		switch (listKey.get(getSimple(data).getClickedButtonId())) {
		case "d":
			setForm(new deleteItem(player, this, Key));
			break;
		case "e":
			setForm(new downItem(player, this, Key));
			break;
		default:
			setForm(new SwitchUpItem(player, this));
			break;
		}
		return make();
	}
}
