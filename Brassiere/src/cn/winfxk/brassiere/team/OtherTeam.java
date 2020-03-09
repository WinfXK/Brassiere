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
	private Team team;

	public OtherTeam(Player player, Team team) {
		super(player);
		this.team = team;
	}

	@Override
	public boolean MakeMain() {
		ModalForm form=new ModalForm(getID(), Title, Content)
		return true;
	}

	@Override
	public boolean disMain(FormResponse data) {
		return true;
	}
}
