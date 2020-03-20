package cn.winfxk.brassiere.team;

import cn.winfxk.brassiere.form.FormBase;
import cn.winfxk.brassiere.team.myteam.QuitTeam;
import cn.winfxk.brassiere.team.myteam.TeamDatails;
import cn.winfxk.brassiere.team.myteam.TeamEffect;
import cn.winfxk.brassiere.team.myteam.TeamManage;
import cn.winfxk.brassiere.team.myteam.TeamMessage;
import cn.winfxk.brassiere.team.myteam.TeamPlayerList;
import cn.winfxk.brassiere.team.myteam.TeamShop;
import cn.winfxk.brassiere.team.myteam.TeamSign;
import cn.winfxk.brassiere.tool.SimpleForm;

import cn.nukkit.Player;
import cn.nukkit.form.response.FormResponse;
import cn.nukkit.form.response.FormResponseSimple;

/**
 * @author Winfxk
 */
public class MyTeam extends FormBase {
	private Team team;

	/**
	 * 我的队伍
	 *
	 * @param player
	 */
	public MyTeam(Player player) {
		super(player);
		team = myPlayer.getTeam();
		setK("{Captain}", "{TeamID}", "{TeamName}", "{TeamSize}", "{TeamMaxCount}", "{Player}", "{Money}",
				"{MaxShopItem}", "{ShopItem}");
	}

	@Override
	public boolean MakeMain() {
		if (team == null)
			return ac.makeForm.Tip(player, msg.getSun("Team", "MyTeam", "NotTeam", myPlayer));
		setD(team.getCaptain(), team.getID(), team.getName(), team.getMaxCounts(), player.getName(),
				myPlayer.getMoney(), team.getMaxShopItem(), team.getShop().size());
		SimpleForm form = new SimpleForm(getID(), msg.getSun("Team", "MyTeam", "Title", K, D), team.getContent(player));
		if (team.isCaptain(player) || team.isAdmin(player)) {
			form.addButton(msg.getSun("Team", "MyTeam", "MagTeam", K, D));
			fk.add("mag");
		}
		form.addButton(msg.getSun("Team", "MyTeam", "Message", K, D));
		fk.add("msg");
		form.addButton(msg.getSun("Team", "MyTeam", "List", K, D));
		fk.add("list");
		form.addButton(msg.getSun("Team", "MyTeam", "Shop", K, D));
		fk.add("shop");
		form.addButton(msg.getSun("Team", "MyTeam", "Sign", K, D));
		fk.add("sign");
		form.addButton(msg.getSun("Team", "MyTeam", "Effects", K, D));
		fk.add("effects");
		form.addButton(msg.getSun("Team", "MyTeam", "Datails", K, D));
		fk.add("datails");
		form.addButton(msg.getSun("Team", "MyTeam", "QuitTeam", K, D));
		fk.add("quit");
		form.sendPlayer(player);
		return true;
	}

	@Override
	public boolean disMain(FormResponse data) {
		FormResponseSimple d = getSimple(data);
		switch (fk.get(d.getClickedButtonId())) {
		case "msg":
			setForm(new TeamMessage(player, team));
			break;
		case "list":
			setForm(new TeamPlayerList(player, team));
			break;
		case "shop":
			setForm(new TeamShop(player, team));
			break;
		case "sign":
			setForm(new TeamSign(player, team));
			break;
		case "effects":
			setForm(new TeamEffect(player, team));
			break;
		case "datails":
			setForm(new TeamDatails(player));
			break;
		case "quit":
			setForm(new QuitTeam(player, team));
			break;
		case "mag":
			setForm(new TeamManage(player, team));
			break;
		default:
			throw new TeamException("Unable to get data type, please contact Winfxk!");
		}
		return make();
	}
}
