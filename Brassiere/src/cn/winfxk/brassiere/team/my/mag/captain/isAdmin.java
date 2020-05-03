package cn.winfxk.brassiere.team.my.mag.captain;

import cn.winfxk.brassiere.form.FormBase;
import cn.winfxk.brassiere.team.MyTeam;
import cn.winfxk.brassiere.team.Team;
import cn.winfxk.brassiere.tool.ModalForm;

import cn.epicfx.winfxk.money.sn.Money;
import cn.nukkit.Player;
import cn.nukkit.form.response.FormResponse;

/**
 * @author Winfxk
 */
public class isAdmin extends FormBase {
	private Team team;
	private String name;

	/**
	 * 队长点击的玩家已经是管理员
	 *
	 * @param player
	 * @param team
	 * @param name
	 */
	public isAdmin(Player player, Team team, String name) {
		super(player);
		this.team = team;
		this.name = name;
	}

	@Override
	public boolean MakeMain() {
		setK("{Player}", "{ByPlayer}", "{ByMoney}", "{Money}", "{TeamName}",
				"{TeamID}");
		setD(player.getName(), name, Money.getMoney(name), myPlayer.getMoney(),
				team.getName(), team.getID());
		new ModalForm(getID(),
				msg.getSun("Team", "isAdmin", "Title", getK(), getD()),
				msg.getSun("Team", "isAdmin", "Content", getK(), getD()),
				msg.getSun("Team", "isAdmin", "Repeal", getK(), getD()),
				msg.getSun("Team", "isAdmin", "Cancel", getK(), getD()))
						.sendPlayer(player);
		return true;
	}

	@Override
	public boolean disMain(FormResponse data) {
		if (getModal(data).getClickedButtonId() != 0)
			return setForm(new MyTeam(player)).make();
		player.sendMessage(
				msg.getSun("Team", "isAdmin", "RepealMessage", getK(), getD()));
		team.repealAdmin(name);
		return setForm(new MyTeam(player)).make();
	}
}
