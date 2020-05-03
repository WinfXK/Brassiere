package cn.winfxk.brassiere.team.my.effect;

import cn.nukkit.Player;
import cn.nukkit.form.response.FormResponse;
import cn.nukkit.potion.Effect;
import cn.winfxk.brassiere.form.FormBase;
import cn.winfxk.brassiere.tool.Effectrow;
import cn.winfxk.brassiere.tool.SimpleForm;
import cn.winfxk.brassiere.tool.Tool;

/**
 * 当管理员点击效果项目后将会显示的对话框，这个对话框的内容是让管理员选择是要删除效果还是返回列表
 * 
 * @Createdate 2020/04/18 01:50:50
 * @author Winfxk
 */
public class EffectMag extends FormBase {
	private int pos;
	private TeamEffect teamEffect;
	private Effect effect;

	public EffectMag(Player player, int pos, TeamEffect tEffect) {
		super(player);
		this.pos = pos;
		teamEffect = tEffect;
		effect = tEffect.effects.get(pos);
		setK("{Player}", "{Money}", "{TeamID}", "{TeamName}", "{PlayerCount}", "{MaxPlayerCount}", "{Level}",
				"{Captain}", "{Admins}", "{ShopItems}", "{Buffs}", "{EffectID}", "{EffectName}", "{EffectLevel}");
	}

	@Override
	public boolean MakeMain() {
		setD(Tool.Arrays(teamEffect.getK(),
				new Object[] { effect.getId(), Effectrow.getName(effect), effect.getAmplifier() }));
		SimpleForm form = new SimpleForm(getID(), msg.getSun("Team", teamEffect.Son, "MagTitle", K, D),
				msg.getSun("Team", teamEffect.Son, "MagContent", K, D));
		form.addButton(msg.getSun("Team", teamEffect.Son, "Back", K, D));
		form.addButton(msg.getSun("Team", teamEffect.Son, "Delete", K, D));
		form.sendPlayer(player);
		return true;
	}

	@Override
	public boolean disMain(FormResponse data) {
		return setForm(
				getSimple(data).getClickedButtonId() != 1 ? teamEffect : new DeleteEffect(player, pos, teamEffect))
						.make();
	}
}
