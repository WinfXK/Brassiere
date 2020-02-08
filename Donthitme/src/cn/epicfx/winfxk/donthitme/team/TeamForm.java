package cn.epicfx.winfxk.donthitme.team;

import cn.epicfx.winfxk.donthitme.form.FormBase;
import cn.epicfx.winfxk.donthitme.team.join.JoinTeam;
import cn.epicfx.winfxk.donthitme.tool.SimpleForm;
import cn.nukkit.Player;
import cn.nukkit.form.response.FormResponse;
import cn.nukkit.form.response.FormResponseSimple;

/**
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
			myPlayer.makeBase = new JoinTeam(player);
			break;
		case "tl":
			myPlayer.makeBase = new TeamList(player);
			break;
		case "mt":
			myPlayer.makeBase = new MyTeam(player);
			break;
		case "td":
			myPlayer.makeBase = new TeamSns(player);
			break;
		}
		return myPlayer.makeBase == null ? false : myPlayer.makeBase.MakeMain();
	}
}
