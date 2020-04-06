package cn.winfxk.brassiere.team.lord;

import java.util.HashMap;
import java.util.Map;

import cn.nukkit.Player;
import cn.nukkit.form.response.FormResponse;
import cn.nukkit.form.response.FormResponseCustom;
import cn.winfxk.brassiere.form.FormBase;
import cn.winfxk.brassiere.tool.CustomForm;
import cn.winfxk.brassiere.tool.Tool;

/**
 * 添加或删除可供队长购买的药水效果
 * 
 * @Createdate 2020/04/06 07:21:49<br/>
 * @author Winfxk
 */
public class addTeamEffect extends FormBase {
	private Map<String, Object> map;

	public addTeamEffect(Player player) {
		this(player, new HashMap<String, Object>());
	}

	public addTeamEffect(Player player, Map<String, Object> map) {
		super(player);
		this.map = map == null ? new HashMap<>() : map;
	}

	@Override
	public boolean MakeMain() {
		CustomForm form = new CustomForm(ID, msg.getSun("Team", "AdminAddTeamEffect", "Title", K, D));
		form.addLabel(msg.getSun("Team", "AdminAddTeamEffect", "addEffectContent", K, D));
		form.addInput(msg.getSun("Team", "AdminAddTeamEffect", "InputEffectID", K, D), map.get("EffectID"),
				msg.getSun("Team", "AdminAddTeamEffect", "InputEffectID", K, D));
		form.addInput(msg.getSun("Team", "InputEffectLevel", "InputEffectID", K, D), map.get("EffectLevel"),
				msg.getSun("Team", "AdminAddTeamEffect", "InputEffectLevel", K, D));
		form.addInput(msg.getSun("Team", "InputEffectLevel", "InputTeamLevel", K, D), map.get("TeamLevel"),
				msg.getSun("Team", "AdminAddTeamEffect", "InputTeamLevel", K, D));
		form.addInput(msg.getSun("Team", "InputEffectLevel", "InputBuyMoney", K, D), map.get("BuyMoney"),
				msg.getSun("Team", "AdminAddTeamEffect", "InputBuyMoney", K, D));
		form.addDropdown(msg.getSun("Team", "AdminAddTeamEffect", "SelectEconomy", K, D),
				ac.getEconomyManage().getEconomy(),
				ac.getEconomyManage().getEconomy().indexOf(Tool.objToString(map.get("EconomyName"))));
		form.sendPlayer(player);
		return true;
	}

	@Override
	public boolean disMain(FormResponse data) {
		FormResponseCustom d = getCustom(data);
		String string = d.getInputResponse(3);
		int TeamLevel = Tool.ObjToInt(string);
		if (!Tool.isInteger(string))
			return ac.makeForm.Tip(player, msg.getSun("Team", "AdminAddTeamEffect", "TeamLevelNotOK", K, D));
		string = d.getInputResponse(1);
		int EffectID = Tool.ObjToInt(string);
		if (!Tool.isInteger(string))
			return ac.makeForm.Tip(player, msg.getSun("Team", "AdminAddTeamEffect", "EffectIDNotOK", K, D));
		string = d.getInputResponse(2);
		int EffectLevel = Tool.ObjToInt(string);
		if (!Tool.isInteger(string))
			return ac.makeForm.Tip(player, msg.getSun("Team", "AdminAddTeamEffect", "EffectLevelNotOK", K, D));
		string = d.getInputResponse(4);
		double BuyMoney = Tool.objToDouble(string);
		if (!Tool.isInteger(string))
			return ac.makeForm.Tip(player, msg.getSun("Team", "AdminAddTeamEffect", "BuyMoneyNotOK", K, D));
		String EconomyName = d.getDropdownResponse(5).getElementContent();
		map.put("EffectID", EffectID);
		map.put("EffectLevel", EffectLevel);
		map.put("TeamLevel", TeamLevel);
		map.put("BuyMoney", BuyMoney);
		map.put("EconomyName", EconomyName);
		String Key = Tool.objToString(map.get("Key"), getKey(1));
		Key = Key == null || Key.isEmpty() ? getKey(1) : Key;
		map.put("Key", Key);
		ac.getTeamMag().getEffectConfig().set(Key, map);
		return ac.makeForm.Tip(player, msg.getSun("Team", "AdminAddTeamEffect", "Succeed", K, D),
				ac.getTeamMag().getEffectConfig().save());
	}

	/**
	 * 获取一个不重复的队伍要睡ID
	 * 
	 * @param JJLength
	 * @return
	 */
	public String getKey(int JJLength) {
		String string = "";
		for (int i = 0; i < JJLength; i++)
			string += Tool.getRandString();
		if (ac.getTeamMag().getEffectConfig().exists(string))
			return getKey(JJLength++);
		return string;
	}
}
