package cn.winfxk.brassiere.team.my;

import cn.winfxk.brassiere.form.FormBase;
import cn.winfxk.brassiere.team.Team;
import cn.winfxk.brassiere.team.TeamApi;
import cn.winfxk.brassiere.team.TeamException;
import cn.winfxk.brassiere.tool.SimpleForm;

import cn.nukkit.Player;
import cn.nukkit.form.response.FormResponse;

/**
 * 玩家所在队伍的详情
 *
 * @author Winfxk
 */
public class TeamDatails extends FormBase {
	private Team team, MyTeam;

	public TeamDatails(Player player) {
		this(player, TeamApi.getTeam(player.getName()));
	}

	public TeamDatails(Player player, Team team) {
		super(player);
		if (team == null)
			throw new TeamException("Unable to get team data!");
		this.team = team;
		MyTeam = TeamApi.getTeam(player.getName());
		setK("{Player}", "{Money}", "{TeamID}", "{TeamName}", "{PlayerCount}", "{MaxPlayerCount}", "{Level}",
				"{Captain}", "{Admins}", "{ShopItems}", "{Buffs}");
		setD(player.getName(), myPlayer.getMoney(), team.getID(), team.getName(), team.getPlayers().size(),
				team.getMaxCounts(), team.getLevel(), team.getCaptain(), team.getAdmins(), team.getShop().size(),
				team.getEffects().size());
	}

	@Override
	public boolean MakeMain() {
		SimpleForm form = new SimpleForm(getID(), msg.getSun("Team", "TeamDatails", "Title", K, D),
				msg.getSun("Team", "TeamDatails", "Content", K, D));
		form.addButton(msg.getSun("Team", "TeamDatails", "OK", K, D));
		if (MyTeam != null && team != null && team.getID().equals(MyTeam.getID()))
			form.addButton(msg.getSun("Team", "TeamDatails", "OpenMyTeam", K, D));
		return true;
	}

	@Override
	public boolean disMain(FormResponse data) {
		if (MyTeam != null && getSimple(data).getClickedButtonId() != 0)
			setForm(new cn.winfxk.brassiere.team.MyTeam(player)).make();
		return true;
	}
}
