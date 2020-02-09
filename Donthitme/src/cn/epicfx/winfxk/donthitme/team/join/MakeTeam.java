package cn.epicfx.winfxk.donthitme.team.join;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cn.epicfx.winfxk.donthitme.form.FormBase;
import cn.epicfx.winfxk.donthitme.money.MyEconomy;
import cn.epicfx.winfxk.donthitme.team.MyTeam;
import cn.epicfx.winfxk.donthitme.tool.CustomForm;
import cn.epicfx.winfxk.donthitme.tool.Tool;
import cn.nukkit.Player;
import cn.nukkit.form.response.FormResponse;
import cn.nukkit.form.response.FormResponseCustom;
import cn.nukkit.utils.Config;

/**
 * @author Winfxk
 */
public class MakeTeam extends FormBase {
	private double Money;
	private MyEconomy economy;
	private List<MyEconomy> economies = new ArrayList<>();

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
		map.put("Prestige", (int) Money / 100);
		map.put("date", Tool.getDate() + " " + Tool.getTime());
		String JoinTariffs = d.getInputResponse(7);
		double JoinTariff = 0;
		if (!Tool.isInteger(JoinTariffs) || (JoinTariff = Tool.ObjectToDouble(JoinTariffs, 0d)) <= 0)
			JoinTariff = 0;
		String JoinTariffEconomy;
		int JoinTariffEconomyID = d.getDropdownResponse(8).getElementID();
		if (economies.size() <= 0)
			JoinTariffEconomy = null;
		else
			JoinTariffEconomy = economies.get(JoinTariffEconomyID).getEconomyName();
		players.put(player.getName(), map);
		Config config = ac.getTeam().getConfig(ID);
		config.set("ID", ID);
		config.set("Captain", player.getName());
		config.set("Name", TeamName);
		config.set("Prestige", 0);
		config.set("Money", Money / 100);
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
		config.set("Message", new HashMap<String, Object>());
		config.set("ApplyFor", new HashMap<String, Object>());
		config.set("JoinTariff", JoinTariff);
		config.set("JoinTariffEconomy", JoinTariffEconomy);
		config.save();
		ac.getTeam().reload();
		myPlayer.makeBase = new MyTeam(player);
		player.sendMessage(msg.getSun("Team", "MakeTeam", "CreatingSuccessful", myPlayer));
		return myPlayer.makeBase.MakeMain();
	}

	public String getRandID(int s) {
		s = s <= 0 ? 1 : s;
		String string = "";
		for (int i = 0; i < s; i++)
			string += Tool.getRandString("0123456789");
		if (ac.getTeam().getTeamFile(string).exists())
			return getRandID(s++);
		return string;
	}
}
