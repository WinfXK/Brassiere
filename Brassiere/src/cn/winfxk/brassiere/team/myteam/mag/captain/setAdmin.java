package cn.winfxk.brassiere.team.myteam.mag.captain;

import java.util.List;

import cn.nukkit.Player;
import cn.nukkit.form.response.FormResponse;
import cn.winfxk.brassiere.MyPlayer;
import cn.winfxk.brassiere.form.FormBase;
import cn.winfxk.brassiere.team.Team;
import cn.winfxk.brassiere.tool.SimpleForm;

/**
 * @author Winfxk
 */
public class setAdmin extends FormBase {
	private Team team;
	private List<String> list;

	/**
	 * 设置或取消服务器管理员
	 *
	 * @param player
	 */
	public setAdmin(Player player, Team team) {
		super(player);
		this.team = team;
	}

	@Override
	public boolean MakeMain() {
		setK("{Player}", "{Money}", "{TeameID}", "{TeamName}");
		setD(player.getName(), myPlayer.getMoney(), team.getID(), team.getName());
		if (team.size() <= 1)
			return ac.makeForm.Tip(player, msg.getSun("Team", "setAdmin", "NotPlayers", getK(), getD()));
		SimpleForm form = new SimpleForm(getID(), msg.getSun("Team", "setAdmin", "Title", getK(), getD()),
				msg.getSun("Team", "setAdmin", "Content", getK(), getD()));
		String na = msg.getSun("Team", "setAdmin", "NotPlayers", getK(), getD()),
				a = msg.getSun("Team", "setAdmin", "NotPlayers", getK(), getD()),
				c = msg.getSun("Team", "setAdmin", "NotPlayers", getK(), getD());
		setK("{Player}", "{Money}", "{TeameID}", "{TeamName}", "{Admin}", "{AdminMoney}", "{isAdmin}");
		for (String name : team.getPlayers().keySet()) {
			list.add(name);
			form.addButton(msg.getSun("Team", "setAdmin", "ListItem", getK(),
					new Object[] { name, MyPlayer.getMoney(name), team.getID(), team.getName(), player.getName(),
							myPlayer.getMoney(), team.isCaptain(name) ? c : team.isAdmin(name) ? a : na }));
		}
		if (form.getButtonSize() <= 1)
			return ac.makeForm.Tip(player, msg.getSun("Team", "setAdmin", "NotPlayers", getK(), getD()));
		form.sendPlayer(player);
		return true;
	}

	@Override
	public boolean disMain(FormResponse data) {
		if (list.size() <= 1)
			return ac.makeForm.Tip(player, msg.getSun("Team", "setAdmin", "NotPlayers", getK(), getD()));
		String name = list.get(getSimple(data).getClickedButtonId());
		if (team.isCaptain(name))
			setForm(new ExitChoose(player, team));
		else if (team.isAdmin(name))
			setForm(new isAdmin(player, team, name));
		else
			setForm(new notAdmin(player, team, name));
		return make();
	}
}
