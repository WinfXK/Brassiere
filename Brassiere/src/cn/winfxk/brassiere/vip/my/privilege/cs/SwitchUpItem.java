package cn.winfxk.brassiere.vip.my.privilege.cs;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import cn.nukkit.Player;
import cn.nukkit.form.response.FormResponse;
import cn.nukkit.form.response.FormResponseCustom;
import cn.nukkit.item.Item;
import cn.winfxk.brassiere.form.FormBase;
import cn.winfxk.brassiere.tool.CustomForm;
import cn.winfxk.brassiere.tool.ItemIDSunName;
import cn.winfxk.brassiere.tool.Tool;
import cn.winfxk.brassiere.vip.VipApi;
import cn.winfxk.brassiere.vip.VipForm;

/**
 * 上传一个物品到云端仓库 ,这个页面使用来选择要上传的物品的页面
 * 
 * @Createdate 2020/05/04 10:43:04
 * @author Winfxk
 */
public class SwitchUpItem extends VipForm {
	private String[] KK = { "{ItemName}", "{ItemID}", "{ItemCount}" };
	private List<Item> list = new ArrayList<>();

	public SwitchUpItem(Player player, FormBase upForm) {
		super(player, upForm);
		setK("{Player}", "{Money}", "{VipName}", "{VipID}", "{VipLevel}", "{VipExp}", "{VipTime}");
	}

	@Override
	public boolean MakeMain() {
		setD(player.getName(), myPlayer.getMoney(), myPlayer.isVip() ? myPlayer.vip.getName() : notVip,
				myPlayer.isVip() ? myPlayer.vip.getID() : notVip,
				myPlayer.isVip() ? myPlayer.vip.getAlg().getLevel(player.getName()) : notVip,
				VipApi.getLevel(player.getName()), VipApi.getTime(player.getName()));
		Collection<Item> items = player.getInventory().getContents().values();
		if (player.getInventory().isEmpty()) {
			player.sendMessage(getString("notItem"));
			return upForm == null ? true : setForm(upForm).make();
		}
		CustomForm form = new CustomForm(getID(), getString(Title));
		form.addLabel(getString(Content));
		for (Item item : items) {
			if (item == null || item.getId() == 0)
				continue;
			listKey.add(msg.getText(getString("ItemFormat"), KK,
					new Object[] { ItemIDSunName.getName(item), item.getId(), item.getDamage(), item.getCount() }));
			list.add(item);
		}
		if (list.size() <= 0) {
			player.sendMessage(getString("notItem"));
			return upForm == null ? true : setForm(upForm).make();
		}
		form.addInput(getString("ItemCount"), "", getString("ItemCount"));
		form.sendPlayer(player);
		return true;
	}

	@Override
	public boolean disMain(FormResponse data) {
		FormResponseCustom d = getCustom(data);
		Item item = list.get(d.getDropdownResponse(1).getElementID());
		int Count;
		String string = d.getInputResponse(2);
		if (string == null || string.isEmpty() || !Tool.isInteger(string) || (Count = Tool.ObjToInt(string)) <= 0) {
			player.sendMessage(getString("ItemCountError"));
			return upForm == null ? true : setForm(upForm).make();
		}
		item.setCount(Count);
		return setForm(new UpItem(player, this, item)).make();
	}

}
