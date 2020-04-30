package cn.winfxk.brassiere.team.myteam.sign;

import cn.nukkit.Player;
import cn.nukkit.form.response.FormResponse;
import cn.winfxk.brassiere.form.FormBase;
import cn.winfxk.brassiere.team.Team;
import cn.winfxk.brassiere.tool.SimpleForm;

/**
 * 用于显示队伍称号给玩家购买
 * 
 * @Createdate 2020/04/23 00:38:33
 * @author Winfxk
 */
public class TeamSign extends FormBase {
	private Team team;

	public TeamSign(Player player, Team team) {
		super(player);
		this.team = team;
		setK("{Player}", "{Money}", "{TeamID}", "{TeamName}", "{PlayerCount}", "{MaxPlayerCount}", "{Level}",
				"{Captain}", "{Admins}", "{ShopItems}", "{Buffs}", "{Prestige}");
	}

	@Override
	public boolean MakeMain() {
		if (team == null)
			return ac.makeForm.Tip(player, msg.getSun("Sign", Son, "NotTeam", K, D));
		setD(player.getName(), myPlayer.getMoney(), team.getID(), team.getName(), team.getPlayers().size(),
				team.getMaxCounts(), team.getLevel(), team.getCaptain(), team.getAdmins(), team.getShop().size(),
				team.getEffects().size(), team.getPrestige(player.getName()));
		if (!team.isAllowedSign())
			return ac.makeForm.Tip(player, msg.getSun("Team", Son, "Unenabled", K, D));
		SimpleForm form = new SimpleForm(getID(), msg.getSun("Sign", Son, "Title", K, D),
				msg.getSun("Sign", Son, "Content", K, D));
		form.addButton(msg.getSon("Team-Sign", "一级", K, D));
		form.addButton(msg.getSon("Team-Sign", "二级", K, D));
		form.addButton(msg.getSon("Team-Sign", "三级", K, D));
		form.addButton(msg.getSon("Team-Sign", "四级", K, D));
		form.addButton(msg.getSon("Team-Sign", "五级", K, D));
		form.addButton(msg.getSon("Team-Sign", "六级", K, D));
		form.sendPlayer(player);
		return true;
	}

	@Override
	public boolean disMain(FormResponse data) {
		return setForm(new BuySign(player, this, getSimple(data).getClickedButtonId())).make();
	}
}
