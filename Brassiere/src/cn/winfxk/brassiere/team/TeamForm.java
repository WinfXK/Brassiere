package cn.winfxk.brassiere.team;

import cn.nukkit.Player;
import cn.nukkit.form.response.FormResponse;
import cn.nukkit.form.response.FormResponseSimple;
import cn.winfxk.brassiere.form.FormBase;
import cn.winfxk.brassiere.team.join.JTeam;
import cn.winfxk.brassiere.team.myteam.TeamMessage;
import cn.winfxk.brassiere.tool.SimpleForm;

/**
 * 组队系统界面主页
 *
 * @author Winfxk
 */
public class TeamForm extends FormBase {
	public TeamForm(Player player) {
		super(player);
	}

	@Override
	public boolean MakeMain() {
		SimpleForm form = new SimpleForm(getID(), msg.getSun("Team", "Main", "Title", myPlayer),
				msg.getSun("Team", "Main", "Content", myPlayer));
		fk.add("tl");
		form.addButton(msg.getSun("Team", "Main", "队伍列表", myPlayer));
		if (myPlayer.isTeam()) {
			fk.add("mt");
			fk.add("td");
			form.addButton(msg.getSun("Team", "Main", "我的队伍", myPlayer));
			form.addButton(msg.getSun("Team", "Main", "队伍社区", myPlayer));
		} else {
			fk.add("jt");
			form.addButton(msg.getSun("Team", "Main", "加入队伍", myPlayer));
		}
		form.sendPlayer(player);
		return true;
	}

	@Override
	public boolean disMain(FormResponse data) {
		FormResponseSimple d = getSimple(data);
		switch (fk.get(d.getClickedButtonId())) {
		case "jt":
			setForm(new JTeam(player));
			break;
		case "tl":
			setForm(new TeamList(player));
			break;
		case "mt":
			setForm(new MyTeam(player));
			break;
		case "td":
			setForm(new TeamMessage(player, myPlayer.geTeam()));
			break;
		default:
			throw new TeamException("Unable to get data type, please contact Winfxk!");
		}
		return make();
	}
}
