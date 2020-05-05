package cn.winfxk.brassiere.vip.my.privilege.cs;

import java.util.Collection;

import cn.nukkit.Player;
import cn.nukkit.form.response.FormResponse;
import cn.nukkit.item.Item;
import cn.winfxk.brassiere.form.FormBase;
import cn.winfxk.brassiere.tool.ItemIDSunName;
import cn.winfxk.brassiere.vip.VipApi;
import cn.winfxk.brassiere.vip.VipForm;

/**
 * 物品上传处理页
 * 
 * @Createdate 2020/05/05 17:39:52
 * @author Winfxk
 */
public class UpItem extends VipForm {
	private Item item;

	/**
	 * 想要上传的物品的信息
	 * 
	 * @param player 要上传物品的玩家对象
	 * @param upForm 上一页
	 * @param item   要上传到云端的物品（包含了物品数量的信息）
	 */
	public UpItem(Player player, FormBase upForm, Item item) {
		super(player, upForm);
		setK("{Player}", "{Money}", "{VipName}", "{VipID}", "{VipLevel}", "{VipExp}", "{VipTime}", "{ItemName}",
				"{ItemID}", "{ItemCount}");
	}

	@Override
	public boolean MakeMain() {
		setD(player.getName(), myPlayer.getMoney(), myPlayer.isVip() ? myPlayer.vip.getName() : notVip,
				myPlayer.isVip() ? myPlayer.vip.getID() : notVip,
				myPlayer.isVip() ? myPlayer.vip.getAlg().getLevel(player.getName()) : notVip,
				VipApi.getLevel(player.getName()), VipApi.getTime(player.getName()), ItemIDSunName.getName(item),
				item.getId(), item.getCount());
		Collection<Item> items = player.getInventory().getContents().values();
		if (player.getInventory().isEmpty()) {
			player.sendMessage(getString("notItem"));
			return upForm == null ? true : setForm(upForm).make();
		}
		int Count = 0;
		for (Item i : items) {
			if (item.equals(null, true, true))
				Count += i.getCount();
		}
		if (Count < item.getCount()) {
			player.sendMessage(getString("ItemCountInsufficient"));
			return upForm == null ? true : setForm(upForm).make();
		}
		player.getInventory().remove(item);
		myPlayer.addCloudStorage(item);
		player.sendMessage(getString("UpItemOK"));
		return upForm == null ? true : setForm(upForm).make();
	}

	@Override
	public boolean disMain(FormResponse data) {
		return true;
	}
}
