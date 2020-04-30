package cn.winfxk.brassiere.sign;

import java.util.Map;

import cn.nukkit.Player;
import cn.nukkit.form.response.FormResponse;
import cn.winfxk.brassiere.form.FormBase;
import cn.winfxk.brassiere.sign.admin.addSignItem;
import cn.winfxk.brassiere.sign.admin.alterSignItem;
import cn.winfxk.brassiere.sign.admin.delSignItem;
import cn.winfxk.brassiere.tool.SimpleForm;
import cn.winfxk.brassiere.tool.Tool;

/**
 * 称号商店页
 * 
 * @Createdate 2020/04/18 21:06:32
 * @author Winfxk
 */
public class SignShop extends FormBase {
	private Map<String, Object> map;
	private int formSize;

	public SignShop(Player player) {
		super(player);
		setK("{Player}", "{Money}", "{Sign}");
	}

	@Override
	public boolean MakeMain() {
		setD(player.getName(), myPlayer.getMoney(), myPlayer.getSign());
		map = ac.getSignMag().getConfig().getAll();
		if (map.size() <= 0 && !myPlayer.isAdmin())
			return ac.makeForm.Tip(player, msg.getSun("Sign", Son, "NotSign", K, D));
		SimpleForm form = new SimpleForm(getID(), msg.getSun("Sign", Son, "Title", K, D),
				msg.getSun("Sign", Son, "Content", K, D));
		Map<String, Object> data;
		Object obj;
		String[] Ks = Tool.Arrays(K, new String[] { "{SignContent}", "{SignPrice}", "{EconomyName}" });
		for (String Key : map.keySet()) {
			obj = map.get(Key);
			data = obj != null && obj instanceof Map ? (Map<String, Object>) obj : null;
			if (data == null || data.size() != 4)
				continue;
			try {
				form.addButton(msg.getSun("Sign", Son, "Content", Ks,
						Tool.Arrays(D, new Object[] { data.get("Sign"), data.get("Money"), data.get("Economy") })));
				listKey.add(Key);
			} catch (SignException e) {
				e.printStackTrace();
			}
		}
		if ((formSize = form.getButtonSize()) > 0 || myPlayer.isAdmin()) {
			form.addButton(msg.getSun("Sign", Son, "removeSign", K, D));
			form.addButton(msg.getSun("Sign", Son, "alterSign", K, D));
		} else
			return ac.makeForm.Tip(player, msg.getSun("Sign", Son, "NotSign", K, D));
		form.addButton(msg.getSun("Sign", Son, "addSign", K, D));
		form.sendPlayer(player);
		return true;
	}

	@Override
	public boolean disMain(FormResponse data) {
		int ID = getSimple(data).getClickedButtonId();
		return setForm(ID < listKey.size() ? new SignShopSwitch(player, this, listKey.get(ID))
				: formSize == 0 ? new addSignItem(player)
						: ID == listKey.size() ? new delSignItem(player, this)
								: ID == listKey.size() + 1 ? new alterSignItem(player, this) : new addSignItem(player))
										.make();
	}
}
