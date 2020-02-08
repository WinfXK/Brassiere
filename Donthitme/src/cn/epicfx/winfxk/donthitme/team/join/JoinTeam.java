package cn.epicfx.winfxk.donthitme.team.join;

import cn.epicfx.winfxk.donthitme.form.FormBase;
import cn.epicfx.winfxk.donthitme.tool.SimpleForm;
import cn.nukkit.Player;
import cn.nukkit.form.response.FormResponse;

/**
 * @author Winfxk
 */
public class JoinTeam extends FormBase {
	public JoinTeam(Player player) {
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
			myPlayer.makeBase = new JTeam(player);
			break;
		case 1:
			myPlayer.makeBase = new MakeTeam(player);
			break;
		}
		return myPlayer.makeBase.MakeMain();
	}
}
