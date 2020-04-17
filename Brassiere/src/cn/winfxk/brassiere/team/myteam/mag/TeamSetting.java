package cn.winfxk.brassiere.team.myteam.mag;

import cn.nukkit.Player;
import cn.nukkit.form.response.FormResponse;
import cn.nukkit.form.response.FormResponseCustom;
import cn.winfxk.brassiere.form.FormBase;
import cn.winfxk.brassiere.team.MyTeam;
import cn.winfxk.brassiere.team.Team;
import cn.winfxk.brassiere.tool.CustomForm;
import cn.winfxk.brassiere.tool.Tool;

/**
 * @author Winfxk
 *
 */
public class TeamSetting extends FormBase {
	private Team team;

	/**
	 * 组队设置
	 *
	 * @param player
	 * @param team
	 */
	public TeamSetting(Player player, Team team) {
		super(player);
		this.team = team;
		setK("{Player}", "{Money}", "{TeamID}", "{TeamName}", "{PlayerCount}", "{MaxPlayerCount}", "{Level}",
				"{Captain}", "{Admins}", "{ShopItems}", "{Buffs}");
		setD(player.getName(), myPlayer.getMoney(), team.getID(), team.getName(), team.getPlayers().size(),
				team.getMaxCounts(), team.getLevel(), team.getCaptain(), team.getAdmins(), team.getShop().size(),
				team.getEffects().size());
	}

	@Override
	public boolean MakeMain() {
		CustomForm form = new CustomForm(getID(), msg.getSun("Team", Son, "Title", K, D));
		form.addInput(get("setName"), team.getName(), get("setName"));
		form.addToggle(get("AllowedSign"), team.isAllowedSign());
		form.addToggle(get("AllowedJoin"), team.isAllowedJoin());
		form.addToggle(get("AllowedChat"), team.isAllowedChat());
		form.addToggle(get("AllowedGain"), team.isAllowedGain());
		form.addToggle(get("isSignIn"), team.isSignIn());
		form.addInput(get("SignIn"), team.getSignIn(), get("SignIn"));
		form.addInput(get("JoinTariff"), team.getJoinTariff(), get("JoinTariff"));
		form.addToggle(get("AllowedPVP"), team.isAllowedPVP());
		form.addInput(get("Content"), team.getContent(), get("Content"));
		form.sendPlayer(player);
		return true;
	}

	@Override
	public boolean disMain(FormResponse data) {
		FormResponseCustom d = getCustom(data);
		String string = d.getInputResponse(0);
		team.setName(string != null && !string.isEmpty() ? string : team.getName());
		team.setAllowedSign(d.getToggleResponse(1));
		team.setAllowedJoin(d.getToggleResponse(2));
		team.setAllowedChat(d.getToggleResponse(3));
		team.setAllowedGain(d.getToggleResponse(4));
		team.setSignIn(d.getToggleResponse(5));
		string = d.getInputResponse(6);
		team.setSignIn(string != null && !string.isEmpty()
				? Tool.isInteger(string) && Tool.objToDouble(string, team.getSignIn()) > 0
						? Tool.objToDouble(string, team.getSignIn())
						: team.getSignIn()
				: team.getSignIn());
		string = d.getInputResponse(7);
		team.setJoinTariff(string != null && string.isEmpty()
				? Tool.isInteger(string) && Tool.objToDouble(string, team.getJoinTariff()) > 0
						? Tool.objToDouble(string, team.getJoinTariff())
						: team.getJoinTariff()
				: team.getJoinTariff());
		team.setAllowedPVP(d.getToggleResponse(8));
		string = d.getInputResponse(9);
		team.setContent(string);
		player.sendMessage(get("OK"));
		return setForm(new MyTeam(player)).make();
	}

	public String get(String string) {
		return msg.getSun("Team", Son, string, K, D);
	}
}
