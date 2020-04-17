package cn.winfxk.brassiere.team.myteam.effect;

import cn.nukkit.Player;
import cn.nukkit.form.response.FormResponse;
import cn.nukkit.potion.Effect;
import cn.winfxk.brassiere.form.FormBase;
import cn.winfxk.brassiere.tool.CustomForm;
import cn.winfxk.brassiere.tool.Effectrow;
import cn.winfxk.brassiere.tool.Tool;

/**
 * s 在管理员准备删除队伍效果的时候用于输入队伍的安全码
 * 
 * @Createdate 2020/04/18 01:54:47
 * @author Winfxk
 */
public class DeleteEffect extends FormBase {
	private TeamEffect teamEffect;
	private Effect effect;

	public DeleteEffect(Player player, int pos, TeamEffect teamEffect) {
		super(player);
		this.teamEffect = teamEffect;
		effect = teamEffect.effects.get(pos);
		setK("{Player}", "{Money}", "{TeamID}", "{TeamName}", "{PlayerCount}", "{MaxPlayerCount}", "{Level}",
				"{Captain}", "{Admins}", "{ShopItems}", "{Buffs}", "{EffectID}", "{EffectName}", "{EffectLevel}");
	}

	@Override
	public boolean MakeMain() {
		setD(Tool.Arrays(teamEffect.getK(),
				new Object[] { effect.getId(), Effectrow.getName(effect), effect.getAmplifier() }));
		CustomForm form = new CustomForm(getID(), msg.getSun("Team", teamEffect.Son, "MagTitle", K, D));
		form.addLabel(msg.getSun("Team", teamEffect.Son, "MagContent", K, D));
		form.addInput(msg.getSun("Team", teamEffect.Son, "SecurityCode", K, D), "",
				msg.getSun("Team", teamEffect.Son, "SecurityCode", K, D));
		form.sendPlayer(player);
		return true;
	}

	@Override
	public boolean disMain(FormResponse data) {
		String SecurityCode = getCustom(data).getInputResponse(1);
		if (SecurityCode == null || SecurityCode.isEmpty() || !teamEffect.team.checkSecuritypd(SecurityCode))
			return ac.makeForm.Tip(player, msg.getSun("Team", teamEffect.Son, "SecurityCodeError", K, D));
		teamEffect.team.removeEffect(effect);
		player.sendMessage(msg.getSun("Team", teamEffect.Son, "DeleteSucceed", K, D));
		return setForm(teamEffect).make();
	}
}
