package cn.winfxk.brassiere.team.myteam.mag;

import java.util.List;
import java.util.Map;

import cn.winfxk.brassiere.MyPlayer;
import cn.winfxk.brassiere.form.FormBase;
import cn.winfxk.brassiere.team.Team;
import cn.winfxk.brassiere.tool.SimpleForm;
import cn.winfxk.brassiere.tool.Tool;

import cn.nukkit.Player;
import cn.nukkit.form.response.FormResponse;

/**
 * @author Winfxk
 */
public class PlayersMag extends FormBase {
	private Team team;
	private List<String> list;

	/**
	 * 队伍成员管理
	 *
	 * @param player
	 */
	public PlayersMag(Player player, Team team) {
		super(player);
		this.team = team;
		setK("{ByPlayer}", "{ByMoney}", "{Player}", "{Money}", "{Prestige}", "{TeamName}", "{TeamID}", "{Captain}");
		setD(player.getName(), MyPlayer.getMoney(player.getName()), player.getName(), myPlayer.getMoney(),
				team.getPrestige(player.getName()), team.getName(), team.getID(), team.getCaptain());

	}

	@Override
	public boolean MakeMain() {
		if (team.getPlayers().size() <= 1)
			return ac.makeForm.Tip(player, msg.getSun("Team", "PlayersMag", "NotPlayer", getK(), getD()));
		SimpleForm form = new SimpleForm(getID(), msg.getSun("Team", "PlayersMag", "Title", getK(), getD()),
				msg.getSun("Team", "PlayersMag", "Content", getK(), getD()));
		String name;
		for (Map<String, Object> map : team.getPlayers().values()) {
			name = Tool.objToString(map.get("name"));
			if (name == null || name.isEmpty())
				continue;
			list.add(name);
			form.addButton(msg.getSun("Team", "PlayersMag", "PlayerItem", getK(),
					new Object[] { name, MyPlayer.getMoney(name), player.getName(), myPlayer.getMoney(),
							team.getPrestige(player.getName()), team.getName(), team.getID(), team.getCaptain() }));
		}
		if (form.getButtonSize() <= 1)
			return ac.makeForm.Tip(player, msg.getSun("Team", "PlayersMag", "NotPlayer", getK(), getD()));
		form.sendPlayer(player);
		return true;
	}

	@Override
	public boolean disMain(FormResponse data) {
		int ID = getSimple(data).getClickedButtonId();
		if (ID >= list.size())
			return ac.makeForm.Tip(player,
					msg.getSun("Team", "PlayersMag", "ErrorPlayer", getK(),
							new Object[] { ID, 0, player.getName(), myPlayer.getMoney(),
									team.getPrestige(player.getName()), team.getName(), team.getID(),
									team.getCaptain() }));
		return setForm(new TMPlayer(player, team.getPlayerdata(list.get(ID)), this)).make();
	}
}
