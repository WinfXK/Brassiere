package cn.winfxk.brassiere.team.my.mag.captain;

import cn.nukkit.Player;
import cn.nukkit.form.response.FormResponse;
import cn.winfxk.brassiere.MyPlayer;
import cn.winfxk.brassiere.form.FormBase;
import cn.winfxk.brassiere.team.Team;
import cn.winfxk.brassiere.team.my.TeamPlayerList;
import cn.winfxk.brassiere.tool.CustomForm;
import cn.winfxk.brassiere.tool.Tool;

/**
 * 用于队长管理队伍内的玩家声望
 * 
 * @Createdate 2020/04/18 03:22:20
 * @author Winfxk
 */
public class ReputationMag extends FormBase {
	private Team team;
	private String name;

	/**
	 * 用于队长管理队伍内的玩家声望
	 * 
	 * @param player 队长
	 * @param team   队伍
	 */
	public ReputationMag(Player player, Team team, String name) {
		super(player);
		this.team = team;
		this.name = name;
		setK("{ByPlayer}", "{ByMoney}", "{Player}", "{Money}", "{TeamID}", "{TeamName}", "{PlayerCount}",
				"{MaxPlayerCount}", "{Level}", "{Captain}", "{Admins}", "{ShopItems}", "{Buffs}");
	}

	@Override
	public boolean MakeMain() {
		setD(player.getName(), myPlayer.getMoney(), name, MyPlayer.getMoney(name), team.getID(), team.getName(),
				team.getPlayers().size(), team.getMaxCounts(), team.getLevel(), team.getCaptain(), team.getAdmins(),
				team.getShop().size(), team.getEffects().size());
		CustomForm form = new CustomForm(getID(), msg.getSun("Team", Son, "Title", K, D));
		form.addInput(msg.getSun("Team", Son, "玩家声望", K, D), team.getPrestige(name),
				msg.getSun("Team", Son, "玩家声望", K, D));
		form.sendPlayer(player);
		return true;
	}

	@Override
	public boolean disMain(FormResponse data) {
		String string = getCustom(data).getInputResponse(0);
		if (string == null || string.isEmpty()) {
			player.sendMessage(msg.getSun("Team", Son, "格式错误", K, D));
			return MakeMain();
		}
		if (!Tool.isInteger(string)) {
			player.sendMessage(msg.getSun("Team", Son, "纯数字", K, D));
			return MakeMain();
		}
		double Prestige = Tool.objToDouble(string, team.getPrestige(name));
		team.setPrestige(name, Prestige);
		player.sendMessage(msg.getSun("Team", Son, "成功", K, D));
		return setForm(new TeamPlayerList(player, team)).make();
	}
}
