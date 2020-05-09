package cn.winfxk.brassiere.vip.shop;

import java.util.Map;

import cn.nukkit.Player;
import cn.nukkit.form.response.FormResponse;
import cn.winfxk.brassiere.form.FormBase;
import cn.winfxk.brassiere.money.MyEconomy;
import cn.winfxk.brassiere.tool.SimpleForm;
import cn.winfxk.brassiere.tool.Tool;
import cn.winfxk.brassiere.vip.Vip;
import cn.winfxk.brassiere.vip.VipApi;
import cn.winfxk.brassiere.vip.VipForm;

/**
 * 购买了VIP的处理类
 * 
 * @Createdate 2020/05/03 07:52:50
 * @author Winfxk
 */
public class BuyVip extends VipForm {
	private Map<String, Object> map;
	private Vip vip;
	private MyEconomy economy;
	private double Money;
	private int Time, Level;

	/**
	 * 购买VIP
	 * 
	 * @param player 触发事件的玩家对象
	 * @param upForm 上级界面
	 * @param Key    购买了VIP的数据键
	 */
	public BuyVip(Player player, FormBase upForm, String Key) {
		super(player, upForm);
		setK("{Player}", "{Money}", "{Vip}", "{VipName}", "{VipID}", "{BuyMoney}", "{BuyTime}", "{Economy}",
				"{BuyLevel}", "{Repeat}", "{BuyMoneyName}");
		map = (Map<String, Object>) vm.getShop().get(Key);
		vip = vm.getVip(Tool.objToString(map.get("ID")));
		economy = ac.getEconomyManage().getEconomy(Tool.objToString(map.get("Economy")));
	}

	@Override
	public boolean MakeMain() {
		setD(player.getName(), myPlayer.getMoney(), getVip(), vip.getName(), vip.getID(),
				Money = Tool.objToDouble(map.get("Money")), Time = Tool.ObjToInt(map.get("BuyTime")),
				map.get("Economy"), Level = Tool.ObjToInt(map.get("BuyLevel")), map.get("Repeat"),
				economy != null ? economy.getMoneyName() : "货币");
		if (economy == null) {
			player.sendMessage(getString("EconomyError"));
			return setForm(upForm).make();
		}
		if (!Tool.ObjToBool(map.get("Repeat")))
			if (myPlayer.getVipID() != null && !myPlayer.getVipID().equals(vip.getID())) {
				player.sendMessage(getString("Repeat"));
				return setForm(upForm).make();
			}
		SimpleForm form = new SimpleForm(getID(), getString(Title), getString(Content));
		form.addButton(getString("Buy"));
		form.addButton(getString(upForm != null ? Back : Close));
		form.sendPlayer(player);
		return true;
	}

	@Override
	public boolean disMain(FormResponse data) {
		if (getSimple(data).getClickedButtonId() != 0)
			return upForm == null ? true : setForm(upForm).make();
		if (economy.getMoney(player) < Money && !economy.allowArrears()) {
			player.sendMessage(getString("notMoney"));
			return MakeMain();
		}
		economy.reduceMoney(player, Money);
		VipApi.setVip(player, vip.getID(), Time, Level);
		player.sendMessage(getString("BuyOK"));
		return upForm == null ? true : setForm(upForm).make();
	}
}
