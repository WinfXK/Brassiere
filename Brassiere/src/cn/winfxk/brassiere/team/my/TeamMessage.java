package cn.winfxk.brassiere.team.my;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import cn.winfxk.brassiere.form.FormBase;
import cn.winfxk.brassiere.team.Team;
import cn.winfxk.brassiere.tool.CustomForm;

import cn.nukkit.Player;
import cn.nukkit.form.response.FormResponse;

/**
 * 队伍聊天
 *
 * @author Winfxk
 */
public class TeamMessage extends FormBase {
	private Team team;
	private String[] MsK;
	private int loc, lo;

	public TeamMessage(Player player, Team team) {
		super(player);
		this.team = team;
		setK("{Player}", "{Money}", "{TeamID}", "{TeamName}", "{PlayerCount}", "{MaxPlayerCount}", "{Level}",
				"{Captain}", "{Admins}", "{ShopItems}", "{Buffs}");
		MsK = new String[] { "{ByDate}", "{ByTime}", "{Message}", "{Player}", "{Money}", "{TeamID}", "{TeamName}",
				"{PlayerCount}", "{MaxPlayerCount}", "{Level}", "{Captain}", "{Admins}", "{ShopItems}", "{Buffs}" };
		lo = ac.getConfig().getInt("队伍聊天内容限制长度");
	}

	@Override
	public boolean MakeMain() {
		if (!team.isAllowedChat())
			return ac.makeForm.Tip(player, msg.getSun("Team", "TeamMessage", "Unenabled", K, D));
		setD(player.getName(), myPlayer.getMoney(), team.getID(), team.getName(), team.getPlayers().size(),
				team.getMaxCounts(), team.getLevel(), team.getCaptain(), team.getAdmins(), team.getShop().size(),
				team.getEffects().size());
		loc = 1;
		HashMap<String, Map<String, Object>> map = team.getMessage();
		CustomForm form = new CustomForm(getID(), msg.getSun("Team", "TeamMessage", "Title", K, D));
		form.addLabel(msg.getSun("Team", "TeamMessage", "Content", K, D));
		for (Entry<String, Map<String, Object>> entry : map.entrySet()) {
			loc++;
			form.addLabel(msg.getSun("Team", "TeamMessage", "ContentItem", MsK,
					new Object[] { entry.getValue().get("Date"), entry.getValue().get("Time"),
							entry.getValue().get("Message"), player.getName(), myPlayer.getMoney(), team.getID(),
							team.getName(), team.getPlayers().size(), team.getMaxCounts(), team.getLevel(),
							team.getCaptain(), team.getAdmins(), team.getShop().size(), team.getEffects().size() }));
		}
		String InputTitle = msg.getSun("Team", "TeamMessage", "InputTitle", K, D);
		String InputContent = msg.getSun("Team", "TeamMessage", "InputContent", K, D);
		String InputHint = msg.getSun("Team", "TeamMessage", "InputHint", K, D);
		form.addInput(InputTitle == null || InputTitle.isEmpty() || InputTitle.equals(" ") ? "" : InputTitle,
				InputContent == null || InputContent.isEmpty() || InputContent.equals(" ") ? "" : InputContent,
				InputHint == null || InputHint.isEmpty() || InputHint.equals(" ") ? "" : InputHint);
		form.sendPlayer(player);
		return true;
	}

	@Override
	public boolean disMain(FormResponse data) {
		String string = getCustom(data).getInputResponse(loc);
		if (string == null || string.replace("", " ").isEmpty()) {
			player.sendMessage(msg.getSun("Team", "TeamMessage", "NotInput", K, D));
			return MakeMain();
		}
		if (lo > 0 && string.length() > lo)
			string = string.substring(0, lo);
		team.addMessage(player, string);
		return MakeMain();
	}
}
