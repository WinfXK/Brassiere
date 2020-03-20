package cn.winfxk.brassiere.team.myteam;

import cn.winfxk.brassiere.form.FormBase;
import cn.winfxk.brassiere.team.Team;

import cn.nukkit.Player;
import cn.nukkit.form.response.FormResponse;

/**
 * @author Winfxk
 */
public class TeamSign extends FormBase {
	private Team team;
	/** TODO 未完成！待称号完成后接上 */
	private Player xasfasfasfasa;

//TODO 未完成！待称号完成后接上
	public TeamSign(Player player, Team team) {
		super(player);
		this.team = team;
		setK("{Player}", "{Money}", "{TeamID}", "{TeamName}", "{PlayerCount}", "{MaxPlayerCount}", "{Level}",
				"{Captain}", "{Admins}", "{ShopItems}", "{Buffs}", "{Prestige}");
		setD(player.getName(), myPlayer.getMoney(), team.getID(), team.getName(), team.getPlayers().size(),
				team.getMaxCounts(), team.getLevel(), team.getCaptain(), team.getAdmins(), team.getShop().size(),
				team.getEffects().size(), team.getPrestige(player.getName()));
	}

	@Override
	public boolean MakeMain() {
		if (!team.isAllowedSign())
			return ac.makeForm.Tip(player, msg.getSun("Team", "TeamSign", "Unenabled", K, D));

		return true;
	}

	@Override
	public boolean disMain(FormResponse data) {
		return false;
	}
}
