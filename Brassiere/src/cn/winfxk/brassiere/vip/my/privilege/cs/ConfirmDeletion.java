package cn.winfxk.brassiere.vip.my.privilege.cs;

import cn.nukkit.Player;
import cn.nukkit.form.response.FormResponse;
import cn.nukkit.item.Item;
import cn.winfxk.brassiere.form.FormBase;
import cn.winfxk.brassiere.tool.ItemIDSunName;
import cn.winfxk.brassiere.tool.SimpleForm;
import cn.winfxk.brassiere.vip.VipApi;
import cn.winfxk.brassiere.vip.VipForm;

/**
 * 删除云端物品的确认页
 * 
 * @Createdate 2020/05/05 20:32:48
 * @author Winfxk
 */
public class ConfirmDeletion extends VipForm {
	private Item item;

	public ConfirmDeletion(Player player, FormBase upForm, Item item) {
		super(player, upForm);
		this.item = item;
		Son = "deleteItem";
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
		if (item.getId() == 0) {
			player.sendMessage(getString("NotItem"));
			return upForm == null ? true : setForm(upForm).make();
		}
		SimpleForm form = new SimpleForm(getID(), getString(Title), getString("ConfirmDeletion"));
		form.addButton(getString("OK"));
		form.addButton(getString(upForm == null ? Close : Back));
		form.sendPlayer(player);
		return true;
	}

	@Override
	public boolean disMain(FormResponse data) {
		if (getSimple(data).getClickedButtonId() == 0) {
			myPlayer.removeCloudStorage(item);
			player.sendMessage(getString("ItemDelOK"));
		}
		return upForm == null ? true : setForm(upForm).make();
	}

}
