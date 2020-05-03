package cn.winfxk.brassiere.sign;

import java.util.Map;

import cn.epicfx.winfxk.money.sn.tool.Tool;
import cn.nukkit.Player;
import cn.nukkit.form.response.FormResponse;
import cn.winfxk.brassiere.form.FormBase;
import cn.winfxk.brassiere.money.MyEconomy;
import cn.winfxk.brassiere.sign.admin.SuredeleteSign;
import cn.winfxk.brassiere.sign.admin.SuremodifySign;
import cn.winfxk.brassiere.sign.my.Signlist;
import cn.winfxk.brassiere.tool.SimpleForm;

/**
 * @Createdate 2020/04/22 11:15:06
 * @author Winfxk
 */
public class SignShopSwitch extends FormBase {
	private SignShop shop;
	private String Key;
	private Map<String, Object> map;
	private MyEconomy economy;
	private String EconomyName;
	private double Money;
	private String Sign;

	/**
	 * 用于玩家在点击称号商店项目后的选择操作
	 * 
	 * @param player
	 */
	public SignShopSwitch(Player player, SignShop shop, String Key) {
		super(player);
		this.shop = shop;
		this.Key = Key;
		Son = "SignData";
		map = (Map<String, Object>) ac.getSignMag().getConfig().get(Key);
		Sign = Tool.objToString(map.get("Sign"));
		Money = Tool.ObjectToDouble(map.get("Money"), 0d);
		EconomyName = Tool.objToString(map.get("Economy"));
		economy = ac.getEconomyManage().getEconomy(Tool.objToString(map.get("Economy"), null));
		setK("{Player}", "{Money}", "{Sign}", "{SignContent}", "{SignPrice}", "{EconomyName}", "{BuyEconomyName}");
	}

	@Override
	public boolean MakeMain() {
		setD(player.getName(), myPlayer.getMoney(), myPlayer.getSign(), Sign, Money, EconomyName,
				economy != null ? economy.getMoneyName() : null);
		SimpleForm form = new SimpleForm(getID(), msg.getSun("Sign", Son, "Title", K, D),
				msg.getSun("Sign", Son, "Content", K, D));
		form.addButton(msg.getSun("Sign", Son, "buySign", K, D));
		form.addButton(msg.getSun("Sign", Son, "Back", K, D));
		if (myPlayer.isAdmin()) {
			form.addButton(msg.getSun("Sign", Son, "removeSign", K, D));
			form.addButton(msg.getSun("Sign", Son, "alterSign", K, D));
		}
		form.sendPlayer(player);
		return true;
	}

	@Override
	public boolean disMain(FormResponse data) {
		int ID = getSimple(data).getClickedButtonId();
		if (!myPlayer.isAdmin() || ID == 0) {
			if (myPlayer.isPossess(Sign)) {
				player.sendMessage(msg.getSun("Sign", Son, "Alreadyowns", K, D));
				return MakeMain();
			}
			if (economy == null) {
				player.sendMessage(msg.getSun("Sign", Son, "NotEconomy", K, D));
				return MakeMain();
			}
			if (!economy.allowArrears() && economy.getMoney(player) < Money) {
				player.sendMessage(msg.getSun("Sign", Son, "NotMoney", K, D));
				return MakeMain();
			}
			ac.getSignMag().addSign(player.getName(), Sign);
			economy.reduceMoney(player, Money);
			player.sendMessage(msg.getSun("Sign", Son, "BuyOK", K, D));
			return setForm(new Signlist(player)).make();
		}
		return setForm(
				ID == 1 ? shop : ID == 2 ? new SuredeleteSign(player, this, Key) : new SuremodifySign(player, Key))
						.make();
	}
}
