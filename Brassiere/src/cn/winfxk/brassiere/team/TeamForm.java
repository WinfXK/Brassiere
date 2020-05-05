package cn.winfxk.brassiere.team;

import cn.nukkit.Player;
import cn.nukkit.form.response.FormResponse;
import cn.nukkit.form.response.FormResponseSimple;
import cn.winfxk.brassiere.form.FormBase;
import cn.winfxk.brassiere.team.join.JTeam;
import cn.winfxk.brassiere.team.lord.TeamEffectMag;
import cn.winfxk.brassiere.team.my.TeamMessage;
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
		listKey.add("tl");
		form.addButton(msg.getSun("Team", "Main", "队伍列表", myPlayer));
		if (myPlayer.isTeam()) {
			listKey.add("mt");
			form.addButton(msg.getSun("Team", "Main", "我的队伍", myPlayer));
			if (myPlayer.getTeam().isAllowedChat()) {
				listKey.add("td");
				form.addButton(msg.getSun("Team", "Main", "队伍社区", myPlayer));
			}
		} else {
			listKey.add("jt");
			form.addButton(msg.getSun("Team", "Main", "加入队伍", myPlayer));
		}
		if (myPlayer.isAdmin()) {
			listKey.add("admin");
			form.addButton(msg.getSun("Team", "Main", "管理功能", myPlayer));
		}
		form.sendPlayer(player);
		return true;
	}

	@Override
	public boolean disMain(FormResponse data) {
		FormResponseSimple d = getSimple(data);
		switch (listKey.get(d.getClickedButtonId())) {
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
			setForm(new TeamMessage(player, myPlayer.getTeam()));
			break;
		case "admin":
			setForm(new TeamEffectMag(player));
			break;
		default:
			throw new TeamException("Unable to get data type, please contact Winfxk!");
		}
		return make();
	}
}
