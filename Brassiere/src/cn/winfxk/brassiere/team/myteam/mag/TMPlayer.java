package cn.winfxk.brassiere.team.myteam.mag;

import java.util.List;

import cn.winfxk.brassiere.MyPlayer;
import cn.winfxk.brassiere.form.FormBase;
import cn.winfxk.brassiere.team.TPlayerdata;
import cn.winfxk.brassiere.team.myteam.mag.captain.ExitChoose;
import cn.winfxk.brassiere.team.myteam.mag.captain.isAdmin;
import cn.winfxk.brassiere.team.myteam.mag.captain.notAdmin;
import cn.winfxk.brassiere.tool.SimpleForm;

import cn.nukkit.Player;
import cn.nukkit.form.response.FormResponse;

/**
 * 2020年3月3日 上午10:28:26
 *
 * @author Winfxk
 */
public class TMPlayer extends FormBase {
	private TPlayerdata data;
	private List<String> list;
	private FormBase base;

	/**
	 * 玩家数据详情页
	 *
	 * @param player
	 * @param name
	 */
	public TMPlayer(Player player, TPlayerdata data, FormBase base) {
		super(player);
		this.base = base;
		this.data = data;
		setK("{ByPlayer}", "{ByMoney}", "{Player}", "{Money}", "{TeamName}", "{TeamID}", "{Identity}", "{Prestige}",
				"{JoinDate}");
		setD(data.getName(), MyPlayer.getMoney(data.getName()), player.getName(), myPlayer.getMoney(),
				data.getTeam().getName(), data.getTeam().getID(), data.getIdentity(), data.getPrestige(),
				data.getJoinDate());
	}

	@Override
	public boolean MakeMain() {
		SimpleForm form = new SimpleForm(getID(), msg.getSun("Team", "PlayersData", "Title", K, D),
				msg.getSun("Team", "PlayersData", "Content", K, D));
		form.addButton(msg.getSun("Team", "PlayersData", "OK", K, D));
		list.add("ok");
		if (data.getTeam().isAdmin(player) || data.getTeam().isCaptain(player)) {
			list.add("un");
			form.addButton(msg.getSun("Team", "PlayersData", "Undock", K, D));
		}
		if (data.getTeam().isCaptain(player)) {
			form.addButton(msg.getSun("Team", "PlayersData", "Admin", K, D));
			list.add("a");
		}
		form.sendPlayer(player);
		return true;
	}

	@Override
	public boolean disMain(FormResponse data) {
		int ID = getSimple(data).getClickedButtonId();
		String Key = ID >= list.size() ? "ok" : list.get(ID);
		Key = Key == null || Key.isEmpty() ? "ok" : Key;
		switch (Key) {
		case "a":
			setForm(this.data.getName().equals(player.getName()) ? new ExitChoose(player, this.data.getTeam())
					: this.data.getTeam().isAdmin(this.data.getName())
							? new isAdmin(player, this.data.getTeam(), this.data.getName())
							: new notAdmin(player, this.data.getTeam(), this.data.getName()));
			break;
		case "un":
			if (this.data.getName().equals(player.getName()))
				return ac.makeForm.Tip(player, msg.getSun("Team", "PlayersData", "UndockME", K, D));
			setForm(new UndockPlayer(player, this.data,this));
			break;
		case "ok":
		default:
			setForm(base);
			break;
		}
		return make();
	}
}
