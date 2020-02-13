package cn.winfxk.brassiere.team.myteam.mag;

import java.util.Map;

import cn.epicfx.winfxk.money.sn.tool.SimpleForm;
import cn.nukkit.Player;
import cn.nukkit.form.response.FormResponse;
import cn.nukkit.form.response.FormResponseSimple;
import cn.winfxk.brassiere.MyPlayer;
import cn.winfxk.brassiere.form.FormBase;
import cn.winfxk.brassiere.team.Team;
import cn.winfxk.brassiere.team.TeamApi;
import cn.winfxk.brassiere.tool.Tool;
import cn.winfxk.brassiere.vip.VipApi;

/**
 * @author Winfxk
 */
public class TeamApplyFor extends FormBase {
	private Team team;
	private String RandK = "";

	/**
	 * 设置队伍的入队请求
	 *
	 * @param player
	 */
	public TeamApplyFor(Player player, Team team) {
		super(player);
		this.team = team;
		for (int i = 0; i < 15; i++)
			RandK += Tool.getRandString();
	}

	@Override
	public boolean MakeMain() {
		if (team.getApplyFor().size() <= 0)
			return ac.makeForm.Tip(player, msg.getSun("Team", "TeamApplyFor", "NotApplyFor", myPlayer));
		SimpleForm form = new SimpleForm(getID(), msg.getSun("Team", "TeamApplyFor", "Title", myPlayer),
				msg.getSun("Team", "TeamApplyFor", "Content", myPlayer));
		String Valid, name;
		fk.add(RandK + "aa");
		fk.add(RandK + "ar");
		setK("{Player}", "{Date}", "{Valid}", "{Money}", "{Vip}", "{ClickPlayer}", "{ClickPlayerMoney}");
		form.addButton(msg.getSun("Team", "TeamApplyFor", "AllAccept", myPlayer));
		form.addButton(msg.getSun("Team", "TeamApplyFor", "AllRefuse", myPlayer));
		for (Map<String, Object> map : team.getApplyFor().values()) {
			name = map.get("Player") == null ? null : String.valueOf(map.get("Player"));
			Valid = MyPlayer.isPlayer(name)
					? TeamApi.isJoinTeam(name) ? msg.getSun("Team", "TeamApplyFor", "ValidFalse", myPlayer)
							: msg.getSun("Team", "TeamApplyFor", "ValidTrue", myPlayer)
					: msg.getSun("Team", "TeamApplyFor", "ValidFalse", myPlayer);
			form.addButton(msg.getSun("Team", "TeamApplyFor", "AllAccept", getK(),
					new Object[] { name, map.get("Date"), Valid, MyPlayer.getMoney(name),
							VipApi.isVip(name) && VipApi.getVip(name) != null ? VipApi.getVip(name).getName() : "",
							player.getName(), myPlayer.getMoney() }));
			fk.add(name);
		}
		form.sendPlayer(player);
		return true;
	}

	@Override
	public boolean disMain(FormResponse data) {
		FormResponseSimple d = getSimple(data);
		int ButtonID = d.getClickedButtonId();
		int i = 0;
		switch (fk.get(ButtonID)) {
		case "aa":
			for (String ike : team.getApplyFor().keySet())
				i += team.acceptApplyFor(ike) ? 1 : 0;
			team.clearApplyFor();
			player.sendMessage(msg.getSun("Team", "TeamApplyFor", "AllAcceptApplyForTrue",
					new String[] { "{Player}", "{Money}", "{TeamName}", "{TeamID}", "{Count}" },
					new Object[] { player.getName(), myPlayer.getMoney(), team.getName(), team.getID(), i }));
			return true;
		case "ar":
			return team.clearApplyFor();
		}
		myPlayer.makeBase = new ApplyForByPlayerData(player, team, fk.get(ButtonID));
		return myPlayer.makeBase.MakeMain();
	}
}
