package cn.winfxk.brassiere.vip.my.privilege.cs;

import cn.nukkit.Player;
import cn.nukkit.form.response.FormResponse;
import cn.nukkit.item.Item;
import cn.winfxk.brassiere.form.FormBase;
import cn.winfxk.brassiere.tool.CustomForm;
import cn.winfxk.brassiere.tool.ItemIDSunName;
import cn.winfxk.brassiere.tool.Tool;
import cn.winfxk.brassiere.vip.VipApi;
import cn.winfxk.brassiere.vip.VipForm;

/**
 * 删除一个云端物品
 * 
 * @Createdate 2020/05/04 10:42:28
 * @author Winfxk
 */
public class deleteItem extends VipForm {
	private String Key;
	private Item item;

	public deleteItem(Player player, FormBase upForm, String Key) {
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
		CustomForm form = new CustomForm(getID(), getString(Title));
		form.addLabel(getString(Content));
		form.addSlider(getString("SelectCount"), 1, item.getCount(), 1, item.getCount());
		form.sendPlayer(player);
		return true;
	}

	@Override
	public boolean disMain(FormResponse data) {
		item.setCount(Tool.ObjToInt(getCustom(data).getSliderResponse(1)));
		return setForm(new ConfirmDeletion(player, this, item)).make();
	}
}
