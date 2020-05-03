package cn.winfxk.brassiere.team.my.mag;

import cn.winfxk.brassiere.MyPlayer;
import cn.winfxk.brassiere.form.FormBase;
import cn.winfxk.brassiere.team.TPlayerdata;
import cn.winfxk.brassiere.team.my.mag.captain.ExitChoose;
import cn.winfxk.brassiere.tool.SimpleForm;

import cn.nukkit.Player;
import cn.nukkit.form.response.FormResponse;

/**
 * 2020年3月3日 上午11:10:57
 *
 * @author Winfxk
 */
public class UndockPlayer extends FormBase {
	private TPlayerdata data;
	private FormBase base;

	/**
	 * 开除玩家界面
	 *
	 * @param player 操作界面的玩家对象
	 * @param data   要被开除的玩家数据类
	 */
	public UndockPlayer(Player player, TPlayerdata data, FormBase base) {
		super(player);
		this.data = data;
		this.base = base;
		setK("{ByPlayer}", "{ByMoney}", "{Player}", "{Money}", "{TeamName}", "{TeamID}", "{Identity}", "{Prestige}",
				"{JoinDate}");
		setD(data.getName(), MyPlayer.getMoney(data.getName()), player.getName(), myPlayer.getMoney(),
				data.getTeam().getName(), data.getTeam().getID(), data.getIdentity(), data.getPrestige(),
				data.getJoinDate());
	}

	@Override
	public boolean MakeMain() {
		if (data.getName().equals(player.getName()))
			return data.getTeam().isCaptain(player) ? setForm(new ExitChoose(player, data.getTeam())).make()
					: ac.makeForm.Tip(player, msg.getSun("Team", "PlayersData", "UndockME", K, D));
		if (data.getTeam().isCaptain(data.getName()) && !data.getTeam().isCaptain(player))
			return ac.makeForm.Tip(player, msg.getSun("Team", "PlayersData", "Usurp", K, D));
		SimpleForm form = new SimpleForm(getID(), msg.getSun("Team", "UndockPlayer", "Title", K, D),
				msg.getSun("Team", "UndockPlayer", "Content", K, D));
		form.addButton(msg.getSun("Team", "UndockPlayer", "Cancel", K, D),
				msg.getSun("Team", "UndockPlayer", "Confirm", K, D));
		form.sendPlayer(player);
		return true;
	}

	@Override
	public boolean disMain(FormResponse data) {
		if (getSimple(data).getClickedButtonId() == 0)
			return setForm(base).make();
		this.data.getTeam().undock(this.data.getName());
		MyPlayer.sendMessage(this.data.getName(), msg.getSun("Team", "PlayersData", "BeUndock", K, D));
		player.sendMessage(msg.getSun("Team", "PlayersData", "Succeed", K, D));
		return setForm(new PlayersMag(player, this.data.getTeam())).make();
	}
}
