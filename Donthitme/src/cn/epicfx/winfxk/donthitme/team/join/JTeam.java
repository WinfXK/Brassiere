package cn.epicfx.winfxk.donthitme.team.join;

import java.util.ArrayList;
import java.util.List;

import cn.epicfx.winfxk.donthitme.form.FormBase;
import cn.epicfx.winfxk.donthitme.money.MyEconomy;
import cn.epicfx.winfxk.donthitme.team.Team;
import cn.epicfx.winfxk.donthitme.team.TeamConfig;
import cn.epicfx.winfxk.donthitme.tool.SimpleForm;
import cn.nukkit.Player;
import cn.nukkit.form.response.FormResponse;

/**
 * @author Winfxk
 */
public class JTeam extends FormBase {
	private Team team;
	private List<String> IDs = new ArrayList<>();

	public JTeam(Player player) {
		super(player);
		team = ac.getTeam();
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
		for (TeamConfig tc : team.getTeams().values()) {
			JoinTariff = tc.getJoinTariffEconomy() == null || tc.getJoinTariff() <= 0 ? "0"
					: tc.getJoinTariff() + ":" + tc.getJoinTariffEconomy().getEconomyName();
			string = msg.getSun("Team", "JoinTeam", tc.isAllowedJoin() ? "AllowedJoinOK" : "AllowedJoinNo", myPlayer);
			form.addButton(msg.getSun("Team", "JoinTeam", "TeamList",
					new String[] { "{Captain}", "{TeamID}", "{TeamName}", "{AllowedJoin}", "{TeamSize}",
							"{TeamMaxCount}", "{JoinTariff}" },
					new Object[] { tc.getCaptain(), tc.getID(), tc.getName(), string, tc.getPlayers().size(),
							tc.getMaxCounts(), JoinTariff }));
			IDs.add(tc.getID());
		}
		if (form.getButtonSize() <= 0)
			return ac.makeForm.Tip(player, msg.getSun("Team", "JoinTeam", "无队伍", myPlayer));
		return true;
	}

	@Override
	public boolean disMain(FormResponse data) {
		TeamConfig team = ac.getTeam().getTeam(IDs.get(getSimple(data).getClickedButtonId()));
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

		return ac.makeForm.Tip(player, msg.getSun("Team", "JoinTeam", "ApplyFor", myPlayer), true);
	}

}
