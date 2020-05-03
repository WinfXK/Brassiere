package cn.winfxk.brassiere.team.my.sign;

import cn.nukkit.Player;
import cn.nukkit.form.response.FormResponse;
import cn.winfxk.brassiere.form.FormBase;
import cn.winfxk.brassiere.team.Team;
import cn.winfxk.brassiere.tool.SimpleForm;

/**
 * 玩家购买队伍称号的确认页
 * 
 * @Createdate 2020/04/23 00:35:16
 * @author Winfxk
 */
public class BuySign extends FormBase {
	private static final String[] SignKey = { "一级", "二级", "三级", "四级", "五级", "六级" };
	private int Level;
	private FormBase base;
	private Team team;
	private String Sign;
	private double Money;

	public BuySign(Player player, FormBase base, int Level) {
		super(player);
		this.Level = Level;
		this.base = base;
		Son = "TeamSign";
		Sign = SignKey[Level];
		team = myPlayer.getTeam();
		setK("{Player}", "{Money}", "{TeamID}", "{TeamName}", "{PlayerCount}", "{MaxPlayerCount}", "{Level}",
				"{Captain}", "{Admins}", "{ShopItems}", "{Buffs}", "{Prestige}", "{SignContent}", "{SignPrice}");
	}

	@Override
	public boolean MakeMain() {
		if (team == null)
			return ac.makeForm.Tip(player, msg.getSun("Sign", Son, "NotTeam", K, D));
		Money = team.getSignPrice().get(Level);
		setD(player.getName(), myPlayer.getMoney(), team.getID(), team.getName(), team.getPlayers().size(),
				team.getMaxCounts(), team.getLevel(), team.getCaptain(), team.getAdmins(), team.getShop().size(),
				team.getEffects().size(), team.getPrestige(player.getName()), Sign, Money);
		SimpleForm form = new SimpleForm(getID(), msg.getSun("Sign", Son, "Title", K, D),
				msg.getSun("Sign", Son, "BuyContent", K, D));
		form.addButton(msg.getSun("Sign", Son, "OK", K, D));
		form.addButton(msg.getSun("Sign", Son, "Back", K, D));
		form.sendPlayer(player);
		return true;
	}

	@Override
	public boolean disMain(FormResponse data) {
		if (getSimple(data).getClickedButtonId() == 1)
			return setForm(base).make();
		if (myPlayer.isPossess(Sign)) {
			player.sendMessage(msg.getSun("Sign", Son, "Alreadyowns", K, D));
			return setForm(base).make();
		}
		if (team.getPrestige(player.getName()) <= Money) {
			player.sendMessage(msg.getSun("Sign", Son, "NotMoney", K, D));
			return setForm(base).make();
		}
		team.reducePrestige(player.getName(), Money);
		ac.getSignMag().addSign(player.getName(), Sign);
		player.sendMessage(msg.getSun("Sign", Son, "BuyOK", K, D));
		return setForm(base).make();
	}
}
