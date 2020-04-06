package cn.winfxk.brassiere.team.lord;

import cn.nukkit.Player;
import cn.nukkit.form.response.FormResponse;
import cn.winfxk.brassiere.form.FormBase;
import cn.winfxk.brassiere.tool.SimpleForm;

/**
 * 删除一个可供队长购买的药水效果
 * 
 * @Createdate 2020/04/06 07:41:30
 * @author Winfxk
 */
public class removeEffect extends FormBase {
	private ClickEffect form;
	private String Key;

	public removeEffect(Player player, ClickEffect form, String Key) {
		super(player);
		this.form = form;
		this.Key = Key;
		setK("{Player}", "{Money}");
		setD(player.getName(), myPlayer.getMoney());
	}

	@Override
	public boolean MakeMain() {
		SimpleForm form = new SimpleForm(getID(), msg.getSun("Team", "AdminAddTeamEffect", "Title", K, D),
				msg.getSun("Team", "AdminAddTeamEffect", "isRemove", K, D));
		form.addButton(msg.getSun("Team", "AdminAddTeamEffect", "OK", K, D));
		form.addButton(msg.getSun("Team", "AdminAddTeamEffect", "Back", K, D));
		form.sendPlayer(player);
		return true;
	}

	@Override
	public boolean disMain(FormResponse data) {
		if (getSimple(data).getClickedButtonId() != 0)
			return setForm(form).make();
		ac.getTeamMag().getEffectConfig().remove(Key);
		return ac.makeForm.Tip(player, msg.getSun("Team", "AdminAddTeamEffect", "Succeed", K, D),
				ac.getTeamMag().getEffectConfig().save());
	}
}
