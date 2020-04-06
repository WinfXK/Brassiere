package cn.winfxk.brassiere.team.lord;

import java.util.Map;

import cn.nukkit.Player;
import cn.nukkit.form.response.FormResponse;
import cn.winfxk.brassiere.form.FormBase;
import cn.winfxk.brassiere.tool.SimpleForm;

/**
 * 当管理员点击一个药水效果可以选择的管理方式页
 * 
 * @Createdate 2020/04/06 07:31:07
 * @author Winfxk
 */
public class ClickEffect extends FormBase {
	protected TeamEffectMag form;
	private String Key;

	public ClickEffect(Player player, TeamEffectMag form, String Key) {
		super(player);
		this.form = form;
		this.Key = Key;
		setK("{Player}", "{Money}");
		setD(player.getName(), myPlayer.getMoney());
	}

	@Override
	public boolean MakeMain() {
		SimpleForm form = new SimpleForm(getID(), msg.getSun("Team", "AdminAddTeamEffect", "Title", K, D),
				msg.getSun("Team", "AdminAddTeamEffect", "ChoiceContent", K, D));
		listKey.add("ae");
		form.addButton(msg.getSun("Team", "AdminAddTeamEffect", "addEffect", K, D));
		listKey.add("ee");
		form.addButton(msg.getSun("Team", "AdminAddTeamEffect", "editEffect", K, D));
		listKey.add("re");
		form.addButton(msg.getSun("Team", "AdminAddTeamEffect", "removeEffect", K, D));
		listKey.add("bk");
		form.addButton(msg.getSun("Team", "AdminAddTeamEffect", "Back", K, D));
		form.sendPlayer(player);
		return true;
	}

	@Override
	public boolean disMain(FormResponse data) {
		switch (listKey.get(getSimple(data).getClickedButtonId())) {
		case "re":
			setForm(new removeEffect(player, this, Key));
			break;
		case "ee":
			setForm(new addTeamEffect(player, (Map<String, Object>) ac.getTeamMag().getEffectConfig().get(Key)));
			break;
		case "ae":
			setForm(new addTeamEffect(player));
			break;
		default:
			setForm(form);
			break;
		}
		return make();
	}
}
