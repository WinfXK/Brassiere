package cn.winfxk.brassiere.team.join;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cn.nukkit.Player;
import cn.nukkit.form.response.FormResponse;
import cn.nukkit.form.response.FormResponseCustom;
import cn.nukkit.utils.Config;
import cn.winfxk.brassiere.Activate;
import cn.winfxk.brassiere.form.FormBase;
import cn.winfxk.brassiere.money.MyEconomy;
import cn.winfxk.brassiere.team.MyTeam;
import cn.winfxk.brassiere.tool.CustomForm;
import cn.winfxk.brassiere.tool.Tool;

/**
 * 玩家选择了创建队伍
 *
 * @author Winfxk
 */
public class MakeTeam extends FormBase {
	private double Money;
	private MyEconomy economy;
	private List<MyEconomy> economies = new ArrayList<>();
	public static final Map<Integer, Double> map = new HashMap<>();
	static {
		Config config = Activate.getActivate().getConfig();
		map.put(0, config.getDouble("一级称号价格"));
		map.put(1, config.getDouble("二级称号价格"));
		map.put(2, config.getDouble("三级称号价格"));
		map.put(3, config.getDouble("四级称号价格"));
		map.put(4, config.getDouble("五级称号价格"));
		map.put(5, config.getDouble("六级称号价格"));
	}

	public MakeTeam(Player player) {
		super(player);
		Money = ac.getConfig().getDouble("创建队伍价格");
		economy = ac.getEconomyManage().getEconomy(ac.getConfig().getString("创建队伍币种"));
	}

	@Override
	public boolean MakeMain() {
		if (economy == null)
			return ac.makeForm.Tip(player, msg.getMessage("无法获取币种", myPlayer));
		if (economy.getMoney(player) < Money)
			return ac.makeForm.Tip(player, msg.getMessage("金币不足", myPlayer));
		economies = ac.getEconomyManage().getEconomys();
		List<String> list = new ArrayList<>();
		for (MyEconomy economy : economies)
			list.add(msg.getSun("Team", "MakeTeam", "JoinTariffEconomyText",
					new String[] { "{EconomyName}", "{EconomyMoney}", "{Player}", "{Money}" }, new Object[] {
							economy.getEconomyName(), economy.getMoneyName(), player.getName(), myPlayer.getMoney() }));
		CustomForm form = new CustomForm(getID(), msg.getSun("Team", "MakeTeam", "Title", myPlayer));
		form.addInput(msg.getSun("Team", "MakeTeam", "TeamName", myPlayer));
		form.addToggle(msg.getSun("Team", "MakeTeam", "AllowedJoin", myPlayer), true);
		form.addToggle(msg.getSun("Team", "MakeTeam", "AllowedChat", myPlayer), true);
		form.addToggle(msg.getSun("Team", "MakeTeam", "AllowedMakeShop", myPlayer), true);
		form.addToggle(msg.getSun("Team", "MakeTeam", "AllowedShop", myPlayer), true);
		form.addToggle(msg.getSun("Team", "MakeTeam", "AllowedGain", myPlayer), true);
		form.addToggle(msg.getSun("Team", "MakeTeam", "AllowedSign", myPlayer), true);
		form.addInput(msg.getSun("Team", "MakeTeam", "JoinTariff", myPlayer), 0,
				msg.getSun("Team", "MakeTeam", "JoinTariff", myPlayer));
		form.addDropdown(msg.getSun("Team", "MakeTeam", "JoinTariffEconomy", myPlayer), list);
		form.addToggle(msg.getSun("Team", "MakeTeam", "AllowedPVP", myPlayer), false);
		form.addInput(msg.getSun("Team", "MakeTeam", "Securitypd", myPlayer), 10086,
				msg.getSun("Team", "MakeTeam", "Securitypd", myPlayer));
		form.addInput(msg.getSun("Team", "MakeTeam", "签到奖励", myPlayer), 1,
				msg.getSun("Team", "MakeTeam", "签到奖励", myPlayer));
		form.addToggle(msg.getSun("Team", "MakeTeam", "追加签到奖励", myPlayer), true);
		// 12
		form.sendPlayer(player);
		return true;
	}

	@Override
	public boolean disMain(FormResponse data) {
		if (economy == null)
			return ac.makeForm.Tip(player, msg.getMessage("无法获取币种", myPlayer));
		if (economy.getMoney(player) < Money)
			return ac.makeForm.Tip(player, msg.getMessage("金币不足", myPlayer));
		FormResponseCustom d = getCustom(data);
		String TeamName = d.getInputResponse(0);
		if (TeamName == null || TeamName.isEmpty())
			return ac.makeForm.Tip(player, msg.getSun("Team", "MakeTeam", "NotName", myPlayer));
		String ID = getRandID(1);
		Map<String, Map<String, Object>> players = new HashMap<>();
		Map<String, Object> map = new HashMap<>();
		map.put("name", player.getName());
		map.put("identity", "captain");
		map.put("Prestige", (int) Money / 100);
		map.put("date", Tool.getDate() + " " + Tool.getTime());
		String JoinTariffs = d.getInputResponse(7);
		double JoinTariff = 0;
		if (!Tool.isInteger(JoinTariffs) || (JoinTariff = Tool.ObjToDouble(JoinTariffs, 0d)) <= 0)
			JoinTariff = 0;
		String JoinTariffEconomy;
		int JoinTariffEconomyID = d.getDropdownResponse(8).getElementID();
		if (economies.size() <= 0)
			JoinTariffEconomy = null;
		else
			JoinTariffEconomy = economies.get(JoinTariffEconomyID).getEconomyName();
		players.put(player.getName(), map);
		String Securitypd = d.getInputResponse(10);
		double SingIn = Tool.objToDouble(d.getResponse(11), 1d);
		boolean isSignIn = d.getToggleResponse(12);
		Securitypd = Securitypd == null || Securitypd.isEmpty() ? "10086" : Securitypd;
		Config config = ac.getTeamMag().getConfig(ID);
		config.set("ID", ID);
		config.set("Captain", player.getName());
		config.set("Name", TeamName);
		config.set("Prestige", 0);
		config.set("Money", Money / 100);
		config.set("SingIn", SingIn);
		config.set("isSignIn", isSignIn);
		config.set("MaxCounts", ac.getConfig().getInt("队伍初始人数上限"));
		config.set("MaxShopItem", ac.getConfig().getInt("队伍商城数量上限"));
		config.set("AllowedJoin", d.getToggleResponse(1));
		config.set("AllowedChat", d.getToggleResponse(2));
		config.set("AllowedMakeShop", d.getToggleResponse(3));
		config.set("AllowedShop", d.getToggleResponse(4));
		config.set("AllowedGain", d.getToggleResponse(5));
		config.set("AllowedSign", d.getToggleResponse(6));
		config.set("Admin", new ArrayList<String>());
		config.set("Players", players);
		config.set("Effects", new HashMap<Integer, Object>());
		config.set("Shop", new HashMap<String, Object>());
		config.set("Message", new HashMap<String, HashMap<String, Object>>());
		config.set("ApplyFor", new HashMap<String, Map<String, Object>>());
		config.set("JoinTariff", JoinTariff);
		config.set("JoinTariffEconomy", JoinTariffEconomy);
		config.set("AllowedPVP", d.getToggleResponse(9));
		config.set("Content", "");
		config.set("SignPrice", MakeTeam.map);
		config.set("Securitypd", Securitypd);
		config.save();
		ac.getTeamMag().load(ID);
		setForm(new MyTeam(player));
		player.sendMessage(msg.getSun("Team", "MakeTeam", "CreatingSuccessful", myPlayer));
		return make();
	}

	public String getRandID(int s) {
		s = s <= 0 ? 1 : s;
		String string = "";
		for (int i = 0; i < s; i++)
			string += Tool.getRandString("0123456789");
		if (ac.getTeamMag().getTeamFile(string).exists())
			return getRandID(s++);
		return string;
	}
}
