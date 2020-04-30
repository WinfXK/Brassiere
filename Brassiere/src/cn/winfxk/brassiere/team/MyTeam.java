package cn.winfxk.brassiere.team;

import cn.nukkit.Player;
import cn.nukkit.form.response.FormResponse;
import cn.nukkit.form.response.FormResponseSimple;
import cn.winfxk.brassiere.form.FormBase;
import cn.winfxk.brassiere.team.myteam.QuitTeam;
import cn.winfxk.brassiere.team.myteam.TeamDatails;
import cn.winfxk.brassiere.team.myteam.TeamManage;
import cn.winfxk.brassiere.team.myteam.TeamMessage;
import cn.winfxk.brassiere.team.myteam.TeamPlayerList;
import cn.winfxk.brassiere.team.myteam.effect.TeamEffect;
import cn.winfxk.brassiere.team.myteam.sign.TeamSign;
import cn.winfxk.brassiere.tool.SimpleForm;
import cn.winfxk.brassiere.tool.Tool;

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
		form.addButton(msg.getSun("Team", "MyTeam", "SignIn", K, D));
		listKey.add("signin");
		if (team.isCaptain(player) || team.isAdmin(player)) {
			form.addButton(msg.getSun("Team", "MyTeam", "MagTeam", K, D));
			listKey.add("mag");
		}
		form.addButton(msg.getSun("Team", "MyTeam", "Message", K, D));
		listKey.add("msg");
		form.addButton(msg.getSun("Team", "MyTeam", "List", K, D));
		listKey.add("list");
		// form.addButton(msg.getSun("Team", "MyTeam", "Shop", K, D));
		// listKey.add("shop");
		if (myPlayer.getConfig().getString("SignIn") == null
				|| !myPlayer.getConfig().getString("SignIn").equals(Tool.getDate())) {
			form.addButton(msg.getSun("Team", "MyTeam", "Sign", K, D));
			listKey.add("sign");
		}
		form.addButton(msg.getSun("Team", "MyTeam", "Effects", K, D));
		listKey.add("effects");
		form.addButton(msg.getSun("Team", "MyTeam", "Datails", K, D));
		listKey.add("datails");
		form.addButton(msg.getSun("Team", "MyTeam", "QuitTeam", K, D));
		listKey.add("quit");
		form.sendPlayer(player);
		return true;
	}

	@Override
	public boolean disMain(FormResponse data) {
		FormResponseSimple d = getSimple(data);
		switch (listKey.get(d.getClickedButtonId())) {
		case "signin":
			setForm(new TeamSignIn(player, team, this));
		case "msg":
			setForm(new TeamMessage(player, team));
			break;
		case "list":
			setForm(new TeamPlayerList(player, team));
			break;
		/*
		 * case "shop": setForm(new TeamShop(player, team)); break; 暂时弃用
		 */
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
