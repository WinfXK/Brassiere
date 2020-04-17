package cn.winfxk.brassiere.team;

import cn.nukkit.Player;
import cn.nukkit.form.response.FormResponse;
import cn.winfxk.brassiere.form.FormBase;
import cn.winfxk.brassiere.tool.SimpleForm;
import cn.winfxk.brassiere.tool.Tool;

/**
 * 处理玩家的签到时事件
 * 
 * @Createdate 2020/04/18 03:05:09
 * @author Winfxk
 */
public class TeamSignIn extends FormBase {
	private boolean isOK = false;
	private MyTeam myTeam;

	public TeamSignIn(Player player, Team team, MyTeam team2) {
		super(player);
		double prestige = ac.getConfig().getDouble("prestige") <= 0 ? team.getSignIn()
				: ac.getConfig().getDouble("prestige");
		String string = myPlayer.config.getString("SignIn");
		if (string == null || !string.equals(Tool.getDate())) {
			if (Tool.getDay(string, Tool.getDate()) == 1) {
				int day = myPlayer.getConfig().getInt("SignCount");
				myPlayer.getConfig().set("SignCount", day + 1);
				if (team.isSignIn())
					prestige += get(day);
			} else
				myPlayer.getConfig().set("SignCount", 1);
			myPlayer.getConfig().save();
			myPlayer.config.set("SignIn", Tool.getDate());
			team.addPrestige(player.getName(), prestige);
		} else
			isOK = true;
		setK("{Player}", "{Money}", "{TeamID}", "{TeamName}", "{PlayerCount}", "{MaxPlayerCount}", "{Level}",
				"{Captain}", "{Admins}", "{ShopItems}", "{Buffs}", "{Continuous}", "{Prestige}");
		setD(player.getName(), myPlayer.getMoney(), team.getID(), team.getName(), team.getPlayers().size(),
				team.getMaxCounts(), team.getLevel(), team.getCaptain(), team.getAdmins(), team.getShop().size(),
				team.getEffects().size(), myPlayer.getConfig().get("SignCount"), prestige);
		myTeam = team2;
	}

	@Override
	public boolean MakeMain() {
		if (isOK)
			return ac.makeForm.Tip(player, msg.getSun("Team", Son, "重复", K, D));
		SimpleForm form = new SimpleForm(getID(), msg.getSun("Team", Son, "Title", K, D),
				msg.getSun("Team", Son, "Content", K, D));
		form.addButton(msg.getSun("Team", Son, "Back", K, D));
		form.sendPlayer(player);
		return true;
	}

	@Override
	public boolean disMain(FormResponse data) {
		return setForm(myTeam).make();
	}

	public double get(double i) {
		return i < 1 ? i : get(i / 10);
	}
}
