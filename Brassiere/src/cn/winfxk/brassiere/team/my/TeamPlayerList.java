package cn.winfxk.brassiere.team.my;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import cn.winfxk.brassiere.MyPlayer;
import cn.winfxk.brassiere.form.FormBase;
import cn.winfxk.brassiere.team.TPlayerdata;
import cn.winfxk.brassiere.team.Team;
import cn.winfxk.brassiere.team.my.mag.TMPlayer;
import cn.winfxk.brassiere.tool.SimpleForm;
import cn.winfxk.brassiere.tool.Tool;

import cn.nukkit.Player;
import cn.nukkit.form.response.FormResponse;

/**
 * @author Winfxk
 */
public class TeamPlayerList extends FormBase {
	private Team team;
	private List<String> list = new ArrayList<>();

	public TeamPlayerList(Player player, Team team) {
		super(player);
		this.team = team;
		setK("{Player}", "{Money}", "{TeamID}", "{TeamName}", "{PlayerCount}", "{MaxPlayerCount}", "{Level}",
				"{Captain}", "{Admins}", "{ShopItems}", "{Buffs}", "{ByPlayer}", "{ByMoney}", "{Permission}");
		setD(player.getName(), myPlayer.getMoney(), team.getID(), team.getName(), team.getPlayers().size(),
				team.getMaxCounts(), team.getLevel(), team.getCaptain(), team.getAdmins(), team.getShop().size(),
				team.getEffects().size(), "", 0,
				team.isCaptain(player) ? msg.getSun("Team", "TeamDatails", "PermissionByCaptain", player)
						: team.isAdmin(player) ? msg.getSun("Team", "TeamDatails", "PermissionByAdmin", player)
								: msg.getSun("Team", "TeamDatails", "PermissionByPlayer", player));
	}

	@Override
	public boolean MakeMain() {
		if (team.getPlayers().size() <= 1)
			return ac.makeForm.Tip(player, msg.getSun("Team", "TeamDatails", "NotPlayers", K, D));
		SimpleForm form = new SimpleForm(getID(), msg.getSun("Team", "TeamDatails", "Title", K, D),
				msg.getSun("Team", "TeamDatails", "Content", K, D));
		String name;
		for (Map<String, Object> map : team.getPlayers().values()) {
			name = Tool.objToString(map.get("name"));
			list.add(name);
			form.addButton(msg.getSun("Team", "TeamDatails", "PlayerItem", K,
					new Object[] { player.getName(), myPlayer.getMoney(), team.getID(), team.getName(),
							team.getPlayers().size(), team.getMaxCounts(), team.getLevel(), team.getCaptain(),
							team.getAdmins(), team.getShop().size(), team.getEffects().size(), name,
							MyPlayer.getMoney(name),
							team.isCaptain(name) ? msg.getSun("Team", "TeamDatails", "PermissionByCaptain", name)
									: team.isAdmin(name) ? msg.getSun("Team", "TeamDatails", "PermissionByAdmin", name)
											: msg.getSun("Team", "TeamDatails", "PermissionByPlayer", name) }));
		}
		form.sendPlayer(player);
		return true;
	}

	@Override
	public boolean disMain(FormResponse data) {
		if (!team.isAdmin(player))
			return true;
		int ID = getSimple(data).getClickedButtonId();
		if (ID >= list.size())
			return ac.makeForm.Tip(player, msg.getSun("Team", "TeamDatails", "PlayerDataError", K, D));
		String name = list.get(ID);
		if (team.isAdmin(name) || team.isCaptain(player))
			return true;
		return setForm(new TMPlayer(player, new TPlayerdata(team.getPlayer(name), team), this)).make();
	}

}
