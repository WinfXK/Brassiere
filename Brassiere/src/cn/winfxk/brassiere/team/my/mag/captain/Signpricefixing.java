package cn.winfxk.brassiere.team.my.mag.captain;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cn.nukkit.Player;
import cn.nukkit.form.response.FormResponse;
import cn.nukkit.form.response.FormResponseCustom;
import cn.winfxk.brassiere.form.FormBase;
import cn.winfxk.brassiere.team.MyTeam;
import cn.winfxk.brassiere.team.Team;
import cn.winfxk.brassiere.tool.CustomForm;
import cn.winfxk.brassiere.tool.Tool;

/**
 *
 * 2020年3月22日 上午1:31:04
 *
 * @author Winfxk
 */
public class Signpricefixing extends FormBase {
	private Team team;
	private Map<Integer, Double> map;
	private List<String> list;

	/**
	 * 队伍管理员设置当前队伍的称号价格
	 *
	 * @param player
	 */
	public Signpricefixing(Player player, Team team) {
		super(player);
		this.team = team;
		setK("{Player}", "{Money}", "{TeamID}", "{TeamName}", "{PlayerCount}", "{MaxPlayerCount}", "{Level}",
				"{Captain}", "{Admins}", "{ShopItems}", "{Buffs}");
	}

	@Override
	public boolean MakeMain() {
		list = team.getTeamMag().getSignList(team, player);
		map = team.getSignPrice();
		setD(player.getName(), myPlayer.getMoney(), team.getID(), team.getName(), team.getPlayers().size(),
				team.getMaxCounts(), team.getLevel(), team.getCaptain(), team.getAdmins(), team.getShop().size(),
				team.getEffects().size());
		CustomForm form = new CustomForm(getID(), msg.getSun("Team", Son, "Title", K, D));
		for (int i = 0; i < list.size() && i < map.size(); i++)
			form.addInput(list.get(i), map.get(i), list.get(i));
		form.sendPlayer(player);
		return true;
	}

	@Override
	public boolean disMain(FormResponse data) {
		FormResponseCustom d = getCustom(data);
		Map<Integer, Double> map2 = new HashMap<>();
		double Money = 0;
		String string;
		for (int i = 0; i < list.size() && i < map.size(); i++) {
			string = d.getInputResponse(i);
			if (string == null || string.isEmpty()) {
				player.sendMessage(msg.getSun("Team", Son, "请输入价格", K, D));
				return MakeMain();
			}
			Money = Tool.objToDouble(string);
			if (!Tool.isInteger(string) || Money <= 0) {
				player.sendMessage(msg.getSun("Team", Son, "价格不合法", K, D));
				return MakeMain();
			}
			map2.put(i, Money);
		}
		team.setSignPrice(map2);
		player.sendMessage(msg.getSun("Team", Son, "设置成功", K, D));
		return setForm(new MyTeam(player)).make();
	}
}
