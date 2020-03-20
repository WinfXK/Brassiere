package cn.winfxk.brassiere.team.myteam.mag;

import cn.nukkit.Player;
import cn.nukkit.form.response.FormResponse;
import cn.nukkit.form.response.FormResponseSimple;
import cn.winfxk.brassiere.MyPlayer;
import cn.winfxk.brassiere.form.FormBase;
import cn.winfxk.brassiere.team.Team;
import cn.winfxk.brassiere.tool.SimpleForm;
import cn.winfxk.brassiere.tool.Tool;
import cn.winfxk.brassiere.vip.VipApi;

/**
 * @author Winfxk
 */
public class ApplyForByPlayerData extends FormBase {
	private Team team;
	private String sb;

	/**
	 * 在入队审批处点击一个玩家，这个是查看玩家信息的
	 *
	 * @param player
	 */
	public ApplyForByPlayerData(Player player, Team team, String sb) {
		super(player);
		this.team = team;
		this.sb = sb;
	}

	@Override
	public boolean MakeMain() {
		setK("{Player}", "{Money}", "{State}", "{Date-Time}", "{TeamList}", "{Vip}", "{Admin}", "{AdminMoney}");
		setD(sb, MyPlayer.getMoney(sb),
				MyPlayer.isTeam(sb) ? msg.getSun("Team", "ApplyForByPlayerData", "Invalid", myPlayer)
						: msg.getSun("Team", "ApplyForByPlayerData", "Valid", myPlayer),
				Tool.getDate() + " " + Tool.getTime(), team.getApplyFor().get(sb).get("OnceJoined"),
				VipApi.isVip(sb) ? VipApi.getVip(sb).getName()
						: msg.getSun("Team", "ApplyForByPlayerData", "NotVip", myPlayer),
				player.getName(), myPlayer.getMoney());
		SimpleForm form = new SimpleForm(getID(), msg.getSun("Team", "ApplyForByPlayerData", "Title", getK(), getD()),
				msg.getSun("Team", "ApplyForByPlayerData", "Content", getK(), getD()));
		form.addButton(msg.getSun("Team", "ApplyForByPlayerData", "Refuse", getK(), getD()));
		if (!MyPlayer.isTeam(sb)) {
			form.addButton(msg.getSun("Team", "ApplyForByPlayerData", "Accept", getK(), getD()));
			form.setContent(form.getContent() + msg.getSun("Team", "ApplyForByPlayerData", "isOK", getK(), getD()));
		}
		form.sendPlayer(player);
		return true;
	}

	@Override
	public boolean disMain(FormResponse data) {
		FormResponseSimple d = getSimple(data);
		switch (d.getClickedButtonId()) {
		case 1:
			if (team.isMaxPlayer())
				return ac.makeForm.Tip(player, msg.getSun("Team", "ApplyForByPlayerData", "CountMax", getK(), getD()),
						true);
			if (MyPlayer.isTeam(sb)) {
				MyPlayer.remoeApplyFor(sb, team.removeApplyFor(sb));
				return ac.makeForm.Tip(player, msg.getSun("Team", "ApplyForByPlayerData", "isInvalid", getK(), getD()),
						true);
			}
			MyPlayer.JoinTeam(sb, team);
			MyPlayer.remoeApplyFor(sb, team.removeApplyFor(sb));
			ac.makeForm.Tip(player, msg.getSun("Team", "ApplyForByPlayerData", "AcceptMsg", getK(), getD()));
			MyPlayer.sendMessage(sb, msg.getSun("Team", "ApplyForByPlayerData", "ByAcceptMsg", getK(), getD()));
			return true;
		case 0:
		default:
			MyPlayer.remoeApplyFor(sb, team.removeApplyFor(sb));
			ac.makeForm.Tip(player, msg.getSun("Team", "ApplyForByPlayerData", "RefuseMsg", getK(), getD()));
			MyPlayer.sendMessage(sb, msg.getSun("Team", "ApplyForByPlayerData", "ByRefuseMsg", getK(), getD()));
			return true;
		}
	}
}
