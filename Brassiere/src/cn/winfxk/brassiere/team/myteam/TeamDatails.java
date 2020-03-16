package cn.winfxk.brassiere.team.myteam;

import cn.winfxk.brassiere.form.FormBase;
import cn.winfxk.brassiere.team.Team;
import cn.winfxk.brassiere.tool.SimpleForm;

import cn.nukkit.Player;
import cn.nukkit.form.response.FormResponse;

/**
 * 玩家所在队伍的详情
 *
 * @author Winfxk
 */
public class TeamDatails extends FormBase {
	private Team team;

	public TeamDatails(Player player, Team team) {
		super(player);
		this.team = team;
		setK("{Player}", "{Money}", "{TeamID}", "{TeamName}", "{PlayerCount}", "{MaxPlayerCount}", "{Level}",
				"{Captain}", "{Admins}", "{ShopItems}", "{Buffs}");
		setD(player.getName(), myPlayer.getMoney(), team.getID(), team.getName(), team.getPlayers().size(),
				team.getMaxCounts(), team.getLevel(), team.getCaptain(), team.getAdmins(), team.getShop().size(),
				team.getEffects().size());
	}

	@Override
	public boolean MakeMain() {
		SimpleForm form = new SimpleForm(ID, Title, Content);
		return true;
	}

	@Override
	public boolean disMain(FormResponse data) {
		return false;
	}
}
