package cn.winfxk.brassiere.team.myteam;

import cn.epicfx.winfxk.money.sn.tool.SimpleForm;
import cn.nukkit.Player;
import cn.nukkit.form.response.FormResponse;
import cn.nukkit.form.response.FormResponseSimple;
import cn.winfxk.brassiere.form.FormBase;
import cn.winfxk.brassiere.team.Team;
import cn.winfxk.brassiere.team.TeamEffectShop;
import cn.winfxk.brassiere.team.myteam.mag.TeamApplyFor;
import cn.winfxk.brassiere.team.myteam.mag.TeamSetting;
import cn.winfxk.brassiere.team.myteam.mag.setAdmin;

/**
 * 队长或管理员管理自己的队伍
 *
 * @author Winfxk
 */
public class TeamManage extends FormBase {
	private boolean isCaptain;
	private Team team;

	/**
	 * 玩家管理队伍的界面
	 *
	 * @param player
	 */
	public TeamManage(Player player, Team team) {
		super(player);
		isCaptain = team.isCaptain(player);
		this.team = team;
	}

	@Override
	public boolean MakeMain() {
		SimpleForm form = new SimpleForm(getID(), msg.getSun("Team", "TeamManage", "Title", myPlayer),
				msg.getSun("Team", "TeamManage", "Content", myPlayer));
		if (isCaptain) {
			form.addButton(msg.getSun("Team", "TeamManage", "setAdmin", myPlayer));
			fk.add("sa");
		}
		form.addButton(msg.getSun("Team", "TeamManage", "ApplyFor", myPlayer));
		fk.add("af");
		form.addButton(msg.getSun("Team", "TeamManage", "Setting", myPlayer));
		fk.add("s");
		form.addButton(msg.getSun("Team", "TeamManage", "BuyEffect", myPlayer));
		fk.add("b");
		form.sendPlayer(player);
		return true;
	}

	@Override
	public boolean disMain(FormResponse data) {
		FormResponseSimple d = getSimple(data);
		switch (fk.get(d.getClickedButtonId())) {
		case "s":
			setForm(new TeamSetting(player));
			break;
		case "af":
			setForm(new TeamApplyFor(player, team));
			break;
		case "sa":
			setForm(new setAdmin(player));
			break;
		case "b":
			setForm(new TeamEffectShop(player));
			break;
		}
		return myPlayer.makeBase.MakeMain();
	}
}
