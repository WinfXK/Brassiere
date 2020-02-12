package cn.winfxk.brassiere.team.join;

import java.util.ArrayList;
import java.util.List;

import cn.nukkit.Player;
import cn.nukkit.form.response.FormResponse;
import cn.winfxk.brassiere.form.FormBase;
import cn.winfxk.brassiere.money.MyEconomy;
import cn.winfxk.brassiere.team.Team;
import cn.winfxk.brassiere.team.TeamMag;
import cn.winfxk.brassiere.tool.SimpleForm;

/**
 * 再加入队伍页面选择了加入一个队伍
 *
 * @author Winfxk
 */
public class JoinTeam extends FormBase {
	private TeamMag team;
	private List<String> IDs = new ArrayList<>();

	public JoinTeam(Player player) {
		super(player);
		team = ac.getTeamMag();
		setK("{Captain}", "{TeamID}", "{TeamName}", "{AllowedJoin}", "{TeamSize}", "{TeamMaxCount}", "{JoinTariff}");
	}

	@Override
	public boolean MakeMain() {
		if (myPlayer.isTeam())
			return ac.makeForm.Tip(player, msg.getSun("Team", "JoinTeam", "已加入队伍", myPlayer));
		if (team.getTeams().size() <= 0)
			return ac.makeForm.Tip(player, msg.getSun("Team", "JoinTeam", "无队伍", myPlayer));
		SimpleForm form = new SimpleForm(getID(), msg.getSun("Team", "JoinTeam", "Title", myPlayer),
				msg.getSun("Team", "JoinTeam", "Content", myPlayer));
		String string, JoinTariff;
		for (Team tc : team.getTeams().values()) {
			JoinTariff = tc.getJoinTariffEconomy() == null || tc.getJoinTariff() <= 0 ? "0"
					: tc.getJoinTariff() + ":" + tc.getJoinTariffEconomy().getEconomyName();
			string = msg.getSun("Team", "JoinTeam", tc.isAllowedJoin() ? "AllowedJoinOK" : "AllowedJoinNo", myPlayer);
			form.addButton(msg.getSun("Team", "JoinTeam", "TeamList", getK(), new Object[] { tc.getCaptain(),
					tc.getID(), tc.getName(), string, tc.getPlayers().size(), tc.getMaxCounts(), JoinTariff }));
			IDs.add(tc.getID());
		}
		if (form.getButtonSize() <= 0)
			return ac.makeForm.Tip(player, msg.getSun("Team", "JoinTeam", "无队伍", myPlayer));
		return true;
	}

	@Override
	public boolean disMain(FormResponse data) {
		Team team = ac.getTeamMag().getTeam(IDs.get(getSimple(data).getClickedButtonId()));
		if (!team.isAllowedJoin())
			return ac.makeForm.Tip(player, msg.getSun("Team", "JoinTeam", "RefusedJoin", myPlayer));
		if (team.isApplyFor(player))
			return ac.makeForm.Tip(player, msg.getSun("Team", "JoinTeam", "isApplyFor", myPlayer));
		if (team.getPlayers().size() >= team.getMaxCounts())
			return ac.makeForm.Tip(player, msg.getSun("Team", "JoinTeam", "TeamFull", myPlayer));
		MyEconomy economy = team.getJoinTariffEconomy();
		double Money = team.getJoinTariff();
		if (economy != null && Money > 0) {
			if (economy.getMoney(player) < Money)
				return ac.makeForm.Tip(player, msg.getSun("Team", "JoinTeam", "NotMoney", myPlayer));
			economy.reduceMoney(player, Money);
		}
		team.sendApplyFor(myPlayer);
		myPlayer.addApplyFor(team);
		return ac.makeForm.Tip(player, msg.getSun("Team", "JoinTeam", "ApplyFor", myPlayer), true);
	}

}
