package cn.winfxk.brassiere.sign.admin;

import java.util.Map;

import cn.nukkit.Player;
import cn.nukkit.form.response.FormResponse;
import cn.winfxk.brassiere.form.FormBase;
import cn.winfxk.brassiere.tool.Tool;

/**
 * 用来转接称号数据
 * 
 * @Createdate 2020/04/22 11:48:29
 * @author Winfxk
 */
public class SuremodifySign extends FormBase {
	private String Key;

	public SuremodifySign(Player player, String Key) {
		super(player);
		this.Key = Key;
	}

	@Override
	public boolean MakeMain() {
		Map<String, Object> map = (Map<String, Object>) ac.getSignMag().getConfig().get(Key);
		myPlayer.form = new addSignItem(player, Key);
		return ((addSignItem) myPlayer.form).Main(Tool.objToString(map.get("Sign")), Tool.objToDouble(map.get("Money")),
				ac.getEconomyManage().getEconomy(Tool.objToString(map.get("Economy"))));
	}

	@Override
	public boolean disMain(FormResponse data) {
		return false;
	}
}
