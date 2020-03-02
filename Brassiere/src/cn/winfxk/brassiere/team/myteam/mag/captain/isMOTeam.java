package cn.winfxk.brassiere.team.myteam.mag.captain;

import cn.winfxk.brassiere.MyPlayer;
import cn.winfxk.brassiere.form.FormBase;
import cn.winfxk.brassiere.team.MyTeam;
import cn.winfxk.brassiere.team.Team;
import cn.winfxk.brassiere.tool.ModalForm;

import cn.nukkit.Player;
import cn.nukkit.form.response.FormResponse;

/**
 * @author Winfxk
 */
public class isMOTeam extends FormBase {
	private Team team;
	private String name;
	private double TransferMoney;

	/**
	 * 玩家确认是否要转让队伍
	 *
	 * @param player
	 */
	public isMOTeam(Player player, Team team, String name) {
		super(player);
		this.team = team;
		this.name = name;
		TransferMoney = ac.getConfig().getDouble("TransferMoney");
		TransferMoney = TransferMoney <= 0 ? 0 : TransferMoney;
	}

	@Override
	public boolean MakeMain() {
		if (name == null || name.isEmpty())
			return ac.makeForm.Tip(player, msg.getSun("Team", "TransferTeam", "NotAdmin", getK(), getD()));
		setK("{Prestige}", "{TransferMoney}", "{Player}", "{ByPlayer}", "{ByMoney}", "{Money}", "{TeamName}",
				"{TeamID}");
		setD(team.getPrestige(player.getName()), TransferMoney, player.getName(), player.getName(), myPlayer.getMoney(),
				myPlayer.getMoney(), team.getName(), team.getID());
		new ModalForm(getID(), msg.getSun("Team", "TransferTeam", "Title", getK(), getD()),
				msg.getSun("Team", "TransferTeam", "Warning", getK(), getD()),
				msg.getSun("Team", "TransferTeam", "Confirm", getK(), getD()),
				msg.getSun("Team", "TransferTeam", "Cancel", getK(), getD())).sendPlayer(player);
		return true;
	}

	@Override
	public boolean disMain(FormResponse data) {
		if (getModal(data).getClickedButtonId() != 0)
			return setForm(new MyTeam(player)).make();
		if (myPlayer.getMoney() < TransferMoney)
			return ac.makeForm.Tip(player, msg.getSun("Team", "TransferTeam", "NotMoney", getK(), getD()));
		ac.getEconomy().reduceMoney(player, TransferMoney);
		team.setCaptain(name);
		player.sendMessage(msg.getSun("Team", "TransferTeam", "TransferSucceed", getK(), getD()));
		MyPlayer.sendMessage(name, msg.getSun("Team", "TransferTeam", "BecomeCaptain", getK(), getD()));
		return setForm(new MyTeam(player)).make();
	}
}
