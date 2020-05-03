package cn.winfxk.brassiere.vip.shop.admin;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cn.nukkit.Player;
import cn.nukkit.form.response.FormResponse;
import cn.nukkit.form.response.FormResponseCustom;
import cn.winfxk.brassiere.form.FormBase;
import cn.winfxk.brassiere.money.MyEconomy;
import cn.winfxk.brassiere.tool.CustomForm;
import cn.winfxk.brassiere.tool.Tool;
import cn.winfxk.brassiere.vip.Vip;
import cn.winfxk.brassiere.vip.VipForm;

/**
 * 添加VIP商店页面
 * 
 * @Createdate 2020/05/03 08:20:34
 * @author Winfxk
 */
public class addShop extends VipForm {
	private List<String> list = new ArrayList<>();
	public static final String[] KK = { "{Player}", "{Money}", "{Vip}", "{VipName}", "{VipID}" };
	private List<Vip> vips;

	public addShop(Player player, FormBase upForm) {
		super(player, upForm);
		setK(Vip.Key);
		Object o;
		for (Object obj : vm.getShop().getAll().values()) {
			if (obj == null || !(obj instanceof Map))
				continue;
			o = ((Map<String, Object>) obj).get("ID");
			if (o == null)
				continue;
			if (!list.contains(Tool.objToString(o)))
				list.add(Tool.objToString(o));
		}
	}

	@Override
	public boolean MakeMain() {
		setD(player.getName(), myPlayer.getMoney(), getVip());
		List<String> vipList = new ArrayList<>();
		int viploc = 0;
		Vip vip;
		vips = vm.getVips();
		for (int i = 0; i < vips.size(); i++) {
			vip = vips.get(i);
			vipList.add(msg.getSun(t, Son, "VipItem", KK,
					new Object[] { player.getName(), myPlayer.getMoney(), getVip(), vip.getName(), vip.getID() }));
			if (viploc != 0 && !list.contains(vip.getID()))
				viploc = i;
		}
		CustomForm form = new CustomForm(getID(), getString(Title));
		form.addDropdown(getString("Vip"), vipList, viploc);
		form.addInput(getString("BuyMoney"), "", getString("BuyMoney"));
		form.addDropdown(getString("Economy"), ac.getEconomyManage().getEconomy());
		form.addInput(getString("BuyTime"), "", getString("BuyTime"));
		form.addInput(getString("BuyLevel"), "", getString("BuyLevel"));
		form.addToggle(getString("Repeat"), true);
		form.sendPlayer(player);
		return true;
	}

	@Override
	public boolean disMain(FormResponse data) {
		FormResponseCustom d = getCustom(data);
		Vip vip = vips.get(d.getDropdownResponse(0).getElementID());
		String string = d.getInputResponse(1);
		double Money;
		if (string == null || string.isEmpty() || !Tool.isInteger(string) || (Money = Tool.objToDouble(string)) <= 0) {
			player.sendMessage(getString("MoneyError"));
			return MakeMain();
		}
		MyEconomy economy = ac.getEconomyManage().getEconomys().get(d.getDropdownResponse(2).getElementID());
		int BuyTime;
		string = d.getInputResponse(3);
		if (string == null || string.isEmpty() || !Tool.isInteger(string) || (BuyTime = Tool.ObjToInt(string)) <= 0) {
			player.sendMessage(getString("BuyTimeError"));
			return MakeMain();
		}
		int BuyLevel;
		string = d.getInputResponse(4);
		if (string == null || string.isEmpty() || !Tool.isInteger(string) || (BuyLevel = Tool.ObjToInt(string)) <= 0) {
			player.sendMessage(getString("BuyLevelError"));
			return MakeMain();
		}
		boolean Repeat = d.getToggleResponse(5);
		String Key = getKey(1);
		Map<String, Object> map = new HashMap<>();
		map.put("Key", Key);
		map.put("ID", vip.getID());
		map.put("Money", Money);
		map.put("BuyTime", BuyTime);
		map.put("Economy", economy.getEconomyName());
		map.put("BuyLevel", BuyLevel);
		map.put("Repeat", Repeat);
		map.put("Player", player.getName());
		map.put("Time", Tool.getDate() + " " + Tool.getTime());
		vm.getShop().set(Key, map);
		vm.getShop().save();
		player.sendMessage(getString("addOK"));
		return upForm == null ? true : setForm(upForm).make();
	}

	/**
	 * 获取一个不重复的Key
	 * 
	 * @param JJLength 初始长度
	 * @return
	 */
	private String getKey(int JJLength) {
		String string = "";
		for (int i = 0; i < JJLength; i++)
			string += Tool.getRandString();
		if (vm.getShop().getAll().containsKey(string))
			return getKey(JJLength++);
		return string;
	}
}
