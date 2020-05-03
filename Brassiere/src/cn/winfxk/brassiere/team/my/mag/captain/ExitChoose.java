package cn.winfxk.brassiere.team.my.mag.captain;

import cn.nukkit.Player;
import cn.nukkit.form.response.FormResponse;
import cn.winfxk.brassiere.form.FormBase;
import cn.winfxk.brassiere.team.Team;
import cn.winfxk.brassiere.team.TeamException;
import cn.winfxk.brassiere.tool.SimpleForm;

/**
 * @author Winfxk
 */
public class ExitChoose extends FormBase {
	private Team team;

	/**
	 * 队长功能页
	 *
	 * @param player
	 */
	public ExitChoose(Player player, Team team) {
		super(player);
		this.team = team;
	}

	@Override
	public boolean MakeMain() {
		setK("{Player}", "{Money}", "{TeameID}", "{TeamName}");
		setD(player.getName(), myPlayer.getMoney(), team.getID(), team.getName());
		SimpleForm form = new SimpleForm(getID(), msg.getSun("Team", "ExitChoose", "Title", getK(), getD()),
				msg.getSun("Team", "ExitChoose", "Content", getK(), getD()));
		form.addButton(msg.getSun("Team", "ExitChoose", "Dissolve", getK(), getD()));
		if (team.size() > 1)
			form.addButton(msg.getSun("Team", "ExitChoose", "MOTeam", getK(), getD()));
		form.sendPlayer(player);
		return true;
	}

	@Override
	public boolean disMain(FormResponse data) {
		switch (getSimple(data).getClickedButtonId()) {
		case 0:
			setForm(new DissolveTeam(player, team));
			break;
		case 1:
			setForm(new MOTeam(player, team));
			break;
		default:
			throw new TeamException("Unable to get data type, please contact Winfxk!");
		}
		return make();
	}
}
