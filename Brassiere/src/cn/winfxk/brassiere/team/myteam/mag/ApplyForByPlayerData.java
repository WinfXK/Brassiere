package cn.winfxk.brassiere.team.myteam.mag;

import cn.nukkit.Player;
import cn.nukkit.form.response.FormResponse;
import cn.winfxk.brassiere.form.FormBase;
import cn.winfxk.brassiere.team.Team;

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
		return false;
	}

	@Override
	public boolean disMain(FormResponse data) {
		return false;
	}
}
