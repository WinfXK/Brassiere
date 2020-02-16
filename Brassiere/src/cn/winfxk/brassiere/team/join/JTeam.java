package cn.winfxk.brassiere.team.join;

import cn.nukkit.Player;
import cn.nukkit.form.response.FormResponse;
import cn.winfxk.brassiere.form.FormBase;
import cn.winfxk.brassiere.team.TeamException;
import cn.winfxk.brassiere.tool.SimpleForm;

/**
 * 玩家要加入一个队伍，给予选择是要创建还是加入
 *
 * @author Winfxk
 */
public class JTeam extends FormBase {
	public JTeam(Player player) {
		super(player);
	}

	@Override
	public boolean MakeMain() {
		if (myPlayer.isTeam())
			return ac.makeForm.Tip(player, msg.getSun("Team", "JoinTeam", "已加入队伍", myPlayer));
		SimpleForm form = new SimpleForm(getID(), msg.getSun("Team", "JoinTeam", "Title", myPlayer),
				msg.getSun("Team", "JoinTeam", "Content", myPlayer));
		form.addButton(msg.getSun("Team", "JoinTeam", "Join", myPlayer));
		form.addButton(msg.getSun("Team", "JoinTeam", "Make", myPlayer));
		form.sendPlayer(player);
		return true;
	}

	@Override
	public boolean disMain(FormResponse data) {
		switch (getSimple(data).getClickedButtonId()) {
		case 0:
			setForm(new JoinTeam(player));
			break;
		case 1:
			setForm(new MakeTeam(player));
			break;
		default:
			throw new TeamException("Unable to get data type, please contact Winfxk!");
		}
		return make();
	}
}
