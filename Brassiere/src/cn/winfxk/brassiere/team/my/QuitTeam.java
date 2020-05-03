package cn.winfxk.brassiere.team.my;

import java.util.ArrayList;
import java.util.List;

import cn.winfxk.brassiere.MyPlayer;
import cn.winfxk.brassiere.form.FormBase;
import cn.winfxk.brassiere.team.Team;
import cn.winfxk.brassiere.team.my.mag.captain.ExitChoose;
import cn.winfxk.brassiere.tool.ModalForm;

import cn.nukkit.Player;
import cn.nukkit.form.response.FormResponse;

/**
 * 玩家想要退出队伍的对话界面
 *
 * @author Winfxk
 */
public class QuitTeam extends FormBase {
	private Team team;

	public QuitTeam(Player player, Team team) {
		super(player);
		this.team = team;
		setK("{Player}", "{Money}", "{TeamID}", "{TeamName}", "{PlayerCount}", "{MaxPlayerCount}", "{Level}",
				"{Captain}", "{Admins}", "{ShopItems}", "{Buffs}");
		setD(player.getName(), myPlayer.getMoney(), team.getID(), team.getName(), team.getPlayers().size(),
				team.getMaxCounts(), team.getLevel(), team.getCaptain(), team.getAdmins(), team.getShop().size(),
				team.getEffects().size());
	}

	@Override
	public boolean disMain(FormResponse data) {
		if (getModal(data).getClickedButtonId() == 0)
			setForm(new isQuit(player, this)).make();
		return true;
	}

	@Override
	public boolean MakeMain() {
		if (!team.isPlayer(player) || !myPlayer.getTeamID().equals(team.getID()))
			return ac.makeForm.Tip(player, msg.getSun("Team", "QuitTeam", "TeamDataError", K, D));
		if (team.isCaptain(player))
			return setForm(new ExitChoose(player, team)).make();
		new ModalForm(getID(), msg.getSun("Team", "QuitTeam", "Title", K, D),
				msg.getSun("Team", "QuitTeam", "Content", K, D), msg.getSun("Team", "QuitTeam", "OK", K, D),
				msg.getSun("Team", "QuitTeam", "NO", K, D)).sendPlayer(player);
		return true;
	}

	private static class isQuit extends FormBase {
		private QuitTeam qt;

		public isQuit(Player player, QuitTeam qt) {
			super(player);
			setD(qt.getD());
			setK(qt.getK());
			this.qt = qt;
		}

		@Override
		public boolean MakeMain() {
			new ModalForm(getID(), msg.getSun("Team", "QuitTeam", "Title", K, D),
					msg.getSun("Team", "QuitTeam", "ConfirmOperation", K, D),
					msg.getSun("Team", "QuitTeam", "OK", K, D), msg.getSun("Team", "QuitTeam", "NO", K, D))
							.sendPlayer(player);
			return true;
		}

		@Override
		public boolean disMain(FormResponse data) {
			if (getModal(data).getClickedButtonId() != 0)
				return true;
			qt.team.removePlayer(player);
			List<String> list = new ArrayList<>(qt.team.getAdmins());
			if (!list.contains(qt.team.getCaptain()))
				list.add(qt.team.getCaptain());
			for (String name : list)
				MyPlayer.sendMessage(name, msg.getText(msg.getSun("Team", "QuitTeam", "AdminQuit", K, D),
						new String[] { "{ByPlayer}", "{ByMoney}" }, new Object[] { name, MyPlayer.getMoney(name) }));
			return ac.makeForm.Tip(player, msg.getSun("Team", "QuitTeam", "OK", K, D),
					msg.getSun("Team", "QuitTeam", "Quit", K, D));
		}
	}
}
