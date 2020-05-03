package cn.winfxk.brassiere.team.my.mag.captain;

import cn.winfxk.brassiere.form.FormBase;
import cn.winfxk.brassiere.team.MyTeam;
import cn.winfxk.brassiere.team.Team;
import cn.winfxk.brassiere.team.TeamForm;
import cn.winfxk.brassiere.tool.ModalForm;

import cn.nukkit.Player;
import cn.nukkit.form.response.FormResponse;

/**
 * @author Winfxk
 */
public class DissolveTeam extends FormBase {
	private Team team;

	/**
	 * 解散队伍
	 *
	 * @param player
	 */
	public DissolveTeam(Player player, Team team) {
		super(player);
		this.team = team;
	}

	@Override
	public boolean MakeMain() {
		setK("{Prestige}", "{Player}", "{Money}", "{TeamName}", "{TeamID}");
		setD(team.getPrestige(player.getName()), player.getName(), myPlayer.getMoney(), team.getName(), team.getID());
		new ModalForm(getID(), msg.getSun("Team", "DissolveTeam", "Title", getK(), getD()),
				msg.getSun("Team", "DissolveTeam", "Warning", getK(), getD()),
				msg.getSun("Team", "DissolveTeam", "Confirm", getK(), getD()),
				msg.getSun("Team", "DissolveTeam", "Cancel", getK(), getD())).sendPlayer(player);
		return true;
	}

	@Override
	public boolean disMain(FormResponse data) {
		if (getModal(data).getClickedButtonId() != 0) {
			player.sendMessage(msg.getSun("Team", "DissolveTeam", "CancelMessage", getK(), getD()));
			return setForm(new MyTeam(player)).make();
		}
		team.dissolve();
		player.sendMessage(ac.getMessage().getSun("Team", "DissolveTeam", "Succeed", getK(), getD()));
		return setForm(new TeamForm(player)).make();
	}
}
