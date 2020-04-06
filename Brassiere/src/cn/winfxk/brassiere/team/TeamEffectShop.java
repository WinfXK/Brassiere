package cn.winfxk.brassiere.team;

import java.util.Map;

import cn.nukkit.Player;
import cn.nukkit.form.response.FormResponse;
import cn.nukkit.utils.Config;
import cn.winfxk.brassiere.form.FormBase;
import cn.winfxk.brassiere.money.MyEconomy;
import cn.winfxk.brassiere.tool.SimpleForm;
import cn.winfxk.brassiere.tool.Tool;

/**
 * 给队长购买队伍专属特效使用
 * 
 * @author Winfxk
 */
public class TeamEffectShop extends FormBase {
	private Team team;
	private Config config;
	private String[] Key;

	public TeamEffectShop(Player player) {
		super(player);
		setK("{Player}", "{Money}", "{TeamID}", "{TeamName}", "{PlayerCount}", "{MaxPlayerCount}", "{Level}",
				"{Captain}", "{Admins}", "{ShopItems}", "{Buffs}");
		Key = new String[] { "{Player}", "{Money}", "{EffectID}", "{BuyMoney}", "{EconomyName}", "{TeamLevel}",
				"{EffectLevel}" };
	}

	/**
	 * 队伍Buff商店
	 *
	 * @param player
	 */
	@Override
	public boolean MakeMain() {
		setD(player.getName(), myPlayer.getMoney(), team.getID(), team.getName(), team.getPlayers().size(),
				team.getMaxCounts(), team.getLevel(), team.getCaptain(), team.getAdmins(), team.getShop().size(),
				team.getEffects().size());
		SimpleForm form = new SimpleForm(getID(), msg.getSun("Team", "TeamEffectShop", "Title", K, D),
				msg.getSun("Team", "TeamEffectShop", "Content", K, D));
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
			listKey.add(Tool.objToString(map2.get("Key")));
			if (EffectLevel == null || EffectID == null || BuyMoney == null || EconomyName == null
					|| (economy = ac.getEconomyManage().getEconomy(Tool.objToString(EconomyName))) == null)
				continue;
			TeamLevel = map2.get("TeamLevel") == null ? msg.getSun("Team", "TeamEffectShop", "Content", K, D)
					: Tool.ObjToInt(map2.get("TeamLevel"));
			form.addButton(msg.getSun("Team", "TeamEffectShop", "Content", Key,
					new Object[] { player.getName(), myPlayer.getMoney(), Tool.ObjToInt(EffectID),
							Tool.ObjToDouble(BuyMoney), economy.getEconomyName(), TeamLevel, EffectLevel }));
		}
		form.sendPlayer(player);
		return true;
	}

	@Override
	public boolean disMain(FormResponse data) {
		int ID = getSimple(data).getClickedButtonId();
		return ID >= listKey.size() ? MakeMain() : setForm(new ClickShopItem(player, listKey.get(ID))).make();
	}

	/**
	 * 用于处理玩家点击项目后的处理事件
	 * 
	 * @Createdate 2020/04/07 03:50:25
	 * @author Winfxk
	 */
	private class ClickShopItem extends FormBase {
		private Map<String, Object> map;

		public ClickShopItem(Player player, String ID) {
			super(player);
			map = (Map<String, Object>) config.get(ID);
		}

		@Override
		public boolean MakeMain() {

			return true;
		}

		@Override
		public boolean disMain(FormResponse data) {
			return true;
		}
	}
}
