package cn.winfxk.brassiere.team.myteam.effect;

import java.util.List;

import cn.nukkit.Player;
import cn.nukkit.form.response.FormResponse;
import cn.nukkit.potion.Effect;
import cn.winfxk.brassiere.form.FormBase;
import cn.winfxk.brassiere.team.Team;
import cn.winfxk.brassiere.tool.Effectrow;
import cn.winfxk.brassiere.tool.SimpleForm;
import cn.winfxk.brassiere.tool.Tool;

/**
 * 显示队伍的效果列表
 * 
 * @Createdate 2020/04/06 07:14:49<br/>
 * @author Winfxk
 */
public class TeamEffect extends FormBase {
	protected Team team;
	protected List<Effect> effects;

	public TeamEffect(Player player, Team team) {
		super(player);
		this.team = team;
		setK("{Player}", "{Money}", "{TeamID}", "{TeamName}", "{PlayerCount}", "{MaxPlayerCount}", "{Level}",
				"{Captain}", "{Admins}", "{ShopItems}", "{Buffs}");
	}

	@Override
	public boolean MakeMain() {
		setD(player.getName(), myPlayer.getMoney(), team.getID(), team.getName(), team.getPlayers().size(),
				team.getMaxCounts(), team.getLevel(), team.getCaptain(), team.getAdmins(), team.getShop().size(),
				team.getEffects().size());
		if (team == null || !team.isPlayer(player))
			return ac.makeForm.Tip(player, msg.getSun("Team", Son, "NotTeam", K, D));
		effects = team.getEffects();
		if (effects.size() <= 0)
			return ac.makeForm.Tip(player, msg.getSun("Team", Son, "NotEff", K, D));
		SimpleForm form = new SimpleForm(getID(), msg.getSun("Team", Son, "Title", K, D),
				msg.getSun("Team", Son, "Content", K, D));
		String[] KK = Tool.Arrays(K, new String[] { "{EffectID}", "{EffectName}", "{EffectLevel}" });
		for (Effect effect : effects)
			form.addButton(
					msg.getSun("Team", Son, "EffectItem", KK,
							Tool.Arrays(D,
									new Object[] { effect.getId(), Effectrow.getName(effect), effect.getAmplifier() })),
					true, Effectrow.getPath(effect));
		form.sendPlayer(player);
		return true;
	}

	@Override
	public boolean disMain(FormResponse data) {
		if (!team.isAdmin(player))
			return MakeMain();
		return setForm(new EffectMag(player, getSimple(data).getClickedButtonId(), this)).make();
	}
}