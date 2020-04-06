package cn.winfxk.brassiere.team.lord;

import java.util.Map;

import cn.nukkit.Player;
import cn.nukkit.form.response.FormResponse;
import cn.nukkit.utils.Config;
import cn.winfxk.brassiere.form.FormBase;
import cn.winfxk.brassiere.money.MyEconomy;
import cn.winfxk.brassiere.tool.SimpleForm;
import cn.winfxk.brassiere.tool.Tool;

/**
 *
 * 2020年3月22日 上午1:29:53
 *
 * @author Winfxk
 */
public class TeamEffectMag extends FormBase {
	protected Config config;
	private String[] Key;

	/**
	 * 服主设定队伍可以购买哪些Buff作为队伍Buff
	 *
	 * @param player
	 */
	public TeamEffectMag(Player player) {
		super(player);
		setK("{Player}", "{Money}");
		config = ac.getTeamMag().getEffectConfig();
		Key = new String[] { "{Player}", "{Money}", "{EffectID}", "{BuyMoney}", "{EconomyName}", "{TeamLevel}",
				"{EffectLevel}" };
	}

	@Override
	public boolean MakeMain() {
		setD(player.getName(), myPlayer.getMoney());
		SimpleForm form = new SimpleForm(getID(), msg.getSun("Team", "AdminAddTeamEffect", "Title", K, D),
				msg.getSun("Team", "AdminAddTeamEffect", "Content", K, D));
		Map<String, Object> map = config.getAll(), map2;
		Object TeamLevel, EffectID, BuyMoney, EconomyName, EffectLevel;
		MyEconomy economy;
		for (Object obj : map.values()) {
			map2 = obj != null && obj instanceof Map ? (Map<String, Object>) obj : null;
			if (map2 == null)
				continue;
			EffectID = map2.get("EffectID");
			BuyMoney = map2.get("BuyMoney");
			EconomyName = map2.get("EconomyName");
			EffectLevel = map2.get("EffectLevel");
			if (EffectLevel == null || EffectID == null || BuyMoney == null || EconomyName == null
					|| (economy = ac.getEconomyManage().getEconomy(Tool.objToString(EconomyName))) == null)
				continue;
			TeamLevel = map2.get("TeamLevel") == null ? msg.getSun("Team", "AdminAddTeamEffect", "Content", K, D)
					: Tool.ObjToInt(map2.get("TeamLevel"));
			form.addButton(msg.getSun("Team", "AdminAddTeamEffect", "Content", Key,
					new Object[] { player.getName(), myPlayer.getMoney(), Tool.ObjToInt(EffectID),
							Tool.ObjToDouble(BuyMoney), economy.getEconomyName(), TeamLevel, EffectLevel }));
		}
		form.addButton(msg.getSun("Team", "AdminAddTeamEffect", "addEffect", K, D));
		form.sendPlayer(player);
		return true;
	}

	@Override
	public boolean disMain(FormResponse data) {
		int ID = getSimple(data).getClickedButtonId();
		return setForm(
				ID >= listKey.size() ? new addTeamEffect(player) : new ClickEffect(player, this, listKey.get(ID)))
						.make();
	}
}
