package cn.winfxk.brassiere.team;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import cn.winfxk.brassiere.form.FormBase;
import cn.winfxk.brassiere.tool.SimpleForm;

import cn.nukkit.Player;
import cn.nukkit.form.response.FormResponse;

/**
 * 用于显示当前服务器已经有了的队伍列表
 *
 * @author Winfxk
 */
public class TeamList extends FormBase {
	private Map<String, Team> map;
	private Team myteam;
	private List<String> list;

	public TeamList(Player player) {
		super(player);
		map = ac.getTeamMag().getTeams();
		myteam = myPlayer.getTeam();
		setK("{Player}", "{Money}", "{TeamCaptain}", "{TeamName}", "{TeamID}");
		setD(player.getName(), myPlayer.getMoney(), myteam == null ? "" : myteam.getCaptain(),
				myteam == null ? "" : myteam.getName(), myteam == null ? "" : myteam.getID());
	}

	@Override
	public boolean MakeMain() {
		if (map == null || map.size() <= 0)
			return ac.makeForm.Tip(player, msg.getSun("Team", "TeamList", "NotTeam", getK(), getD()));
		SimpleForm form = new SimpleForm(getID(), msg.getSun("Team", "TeamList", "Title", getK(), getD()),
				msg.getSun("Team", "TeamList", "Content", getK(), getD()));
		list = new ArrayList<>();
		for (Team team : map.values())
			if (team != null) {
				form.addButton(msg.getSun("Team", "TeamList", "Item", getK(), new Object[] { player.getName(),
						myPlayer.getMoney(), team.getCaptain(), team.getName(), team.getID() }));
				list.add(team.getID());
			}
		if (form.getButtonSize() <= 0)
			return ac.makeForm.Tip(player, msg.getSun("Team", "TeamList", "NotTeam", getK(), getD()));
		form.sendPlayer(player);
		return true;
	}

	@Override
	public boolean disMain(FormResponse data) {
		Team team = ac.getTeamMag().getTeam(list.get(getSimple(data).getClickedButtonId()));
		if (myteam == null)
			return setForm(new OtherTeam(player, team, this)).make();
		if (team.getID().equals(myteam.getID()))
			return setForm(new MyTeam(player)).make();
		return setForm(new OtherTeam(player, team, this)).make();
	}
}
