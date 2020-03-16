package cn.winfxk.brassiere.team;

import cn.winfxk.brassiere.form.FormBase;
import cn.winfxk.brassiere.tool.ModalForm;

import cn.nukkit.Player;
import cn.nukkit.form.response.FormResponse;

/**
 * Create by 2020-03-09 11:58:07
 *
 * @author <a href="https://github.com/WinfXK">Winfxk for GitHub</a>
 * @author <a href="http://WinfXK.cn">Winfxk for Web</a>
 */
public class OtherTeam extends FormBase {
	private FormBase base;

	public OtherTeam(Player player, Team team, FormBase base) {
		super(player);
		setK("{Player}", "{Money}", "{TeamName}", "{TeamID}", "{PlayerCount}", "{ManPlayerCount}", "{Level}",
				"{Captain}");
		setD(player.getName(), myPlayer.getMoney(), team.getName(), team.getID(), team.getPlayers().size(),
				team.getMaxCounts(), team.getLevel(), team.getCaptain());
		this.base = base;
	}

	@Override
	public boolean MakeMain() {
		new ModalForm(getID(), msg.getSun("Team", "OtherTeam", "Title", K, D),
				msg.getSun("Team", "OtherTeam", "Content", K, D), msg.getSun("Team", "OtherTeam", "OK", K, D),
				msg.getSun("Team", "OtherTeam", "No", K, D)).sendPlayer(player);
		return true;
	}

	@Override
	public boolean disMain(FormResponse data) {
		if (getModal(data).getClickedButtonId() == 0)
			return setForm(base).make();
		return true;
	}
}
