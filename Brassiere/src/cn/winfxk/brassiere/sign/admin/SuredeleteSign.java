package cn.winfxk.brassiere.sign.admin;

import java.util.Map;

import cn.nukkit.Player;
import cn.nukkit.form.response.FormResponse;
import cn.nukkit.utils.Config;
import cn.winfxk.brassiere.form.FormBase;
import cn.winfxk.brassiere.tool.SimpleForm;

/**
 * @Createdate 2020/04/22 11:29:18
 * @author Winfxk
 */
public class SuredeleteSign extends FormBase {
	private String Key;
	private Map<String, Object> map;
	private Config config;

	/**
	 * 玩家确定是否要删除称号项目
	 * 
	 * @param player
	 */
	public SuredeleteSign(Player player, FormBase base, String Key) {
		super(player);
		this.Key = Key;
		map = (Map<String, Object>) (config = ac.getSignMag().getConfig()).get(Key);
		setK("{Player}", "{Money}", "{Sign}", "{SignContent}", "{SignPrice}", "{EconomyName}");
		setForm(base);
	}

	@Override
	public boolean MakeMain() {
		setD(player.getName(), myPlayer.getMoney(), myPlayer.getSign(), map.get("Sign"), map.get("Money"),
				map.get("Economy"));
		SimpleForm form = new SimpleForm(getID(), msg.getSun("Sign", Son, "Title", K, D),
				msg.getSun("Sign", Son, "SuredeleteSign", K, D));
		form.addButton(msg.getSun("Sign", Son, "OK", K, D));
		form.addButton(msg.getSun("Sign", Son, "Back", K, D));
		form.sendPlayer(player);
		return true;
	}

	@Override
	public boolean disMain(FormResponse data) {
		if (getSimple(data).getClickedButtonId() == 0) {
			config.remove(Key);
			config.save();
			player.sendMessage(msg.getSun("Sign", Son, "delOK", K, D));
		}
		return make();
	}
}
