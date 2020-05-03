package cn.winfxk.brassiere.team.my.mag.captain;

import java.util.ArrayList;
import java.util.List;

import cn.winfxk.brassiere.MyPlayer;
import cn.winfxk.brassiere.form.FormBase;
import cn.winfxk.brassiere.team.Team;
import cn.winfxk.brassiere.tool.SimpleForm;

import cn.nukkit.Player;
import cn.nukkit.form.response.FormResponse;

/**
 * @author Winfxk
 */
public class MOTeam extends FormBase {
	private Team team;
	private double TransferMoney;
	private List<String> list = new ArrayList<>();

	/**
	 * 转让队伍
	 *
	 * @param player
	 */
	public MOTeam(Player player, Team team) {
		super(player);
		this.team = team;
		TransferMoney = ac.getConfig().getDouble("TransferMoney");
		TransferMoney = TransferMoney <= 0 ? 0 : TransferMoney;
	}

	@Override
	public boolean MakeMain() {
		if (team.getAdmins().size() < 1)
			return ac.makeForm.Tip(player, msg.getSun("Team", "TransferTeam",
					"NotAdmin", getK(), getD()));
		setK("{Prestige}", "{TransferMoney}", "{Player}", "{ByPlayer}",
				"{ByMoney}", "{Money}", "{TeamName}", "{TeamID}");
		setD(team.getPrestige(player.getName()), TransferMoney,
				player.getName(), player.getName(), myPlayer.getMoney(),
				myPlayer.getMoney(), team.getName(), team.getID());
		SimpleForm form = new SimpleForm(getID(),
				msg.getSun("Team", "TransferTeam", "Title", getK(), getD()),
				msg.getSun("Team", "TransferTeam", "Content", getK(), getD()));
		for (String name : team.getAdmins())
			if (name != null && !name.isEmpty()) {
				form.addButton(msg.getSun("Team", "TransferTeam", "Content",
						getK(),
						new Object[]{team.getPrestige(name), TransferMoney,
								player.getName(), name, MyPlayer.getMoney(name),
								myPlayer.getMoney(), team.getName(),
								team.getID()}));
				list.add(name);
			}
		if (form.getButtonSize() < 1)
			return ac.makeForm.Tip(player, msg.getSun("Team", "TransferTeam",
					"NotAdmin", getK(), getD()));
		form.sendPlayer(player);
		return true;
	}

	@Override
	public boolean disMain(FormResponse data) {
		int ButtonID = getSimple(data).getClickedButtonId();
		if (ButtonID >= list.size())
			return ac.makeForm.Tip(player, msg.getSun("Team", "TransferTeam",
					"无法解析玩家名", getK(), getD()));
		return setForm(new isMOTeam(player, team, list.get(ButtonID))).make();
	}
}
