package cn.winfxk.brassiere.sign.admin;

import java.util.Map;

import cn.nukkit.Player;
import cn.nukkit.form.response.FormResponse;
import cn.winfxk.brassiere.form.FormBase;
import cn.winfxk.brassiere.sign.SignException;
import cn.winfxk.brassiere.tool.SimpleForm;
import cn.winfxk.brassiere.tool.Tool;

/**
 * 删除商店项目
 * 
 * @Createdate 2020/04/18 22:00:38
 * @author Winfxk
 */
public class delSignItem extends FormBase {
	protected Map<String, Object> map;
	private FormBase base;

	public delSignItem(Player player, FormBase base) {
		super(player);
		setK("{Player}", "{Money}", "{Sign}");
		this.base = base;
	}

	@Override
	public boolean MakeMain() {
		setD(player.getName(), myPlayer.getMoney(), myPlayer.getSign());
		map = ac.getSignMag().getConfig().getAll();
		if (map.size() <= 0) {
			player.sendMessage(msg.getSun("Sign", Son, "NotSign", K, D));
			return setForm(base).make();
		}
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
		if (form.getButtonSize() <= 0) {
			player.sendMessage(msg.getSun("Sign", Son, "NotSign", K, D));
			return setForm(base).make();
		}
		form.sendPlayer(player);
		return true;
	}

	@Override
	public boolean disMain(FormResponse data) {
		return setForm(new SuredeleteSign(player, this, listKey.get(getSimple(data).getClickedButtonId()))).make();
	}
}
