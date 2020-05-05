package cn.winfxk.brassiere.form;

import cn.nukkit.Player;
import cn.nukkit.form.response.FormResponse;
import cn.winfxk.brassiere.team.TeamForm;
import cn.winfxk.brassiere.tool.SimpleForm;
import cn.winfxk.brassiere.vip.MainForm;

/**
 * 插件功能主页
 * 
 * @Createdate 2020/05/05 21:36:48
 * @author Winfxk
 */
public class FormMain extends MainBase {
	/**
	 * 将会显示该插件的所有功能
	 * 
	 * @param player
	 */
	public FormMain(Player player, FormBase formBase) {
		super(player, formBase);
		Son = "Main";
	}

	@Override
	public boolean MakeMain() {
		reloadD();
		SimpleForm form = new SimpleForm(getID(), getString(Title), getString(Content));
		listKey.add("t");
		form.addButton(getString("Team"));
		listKey.add("v");
		form.addButton(getString("Vip"));
		if (myPlayer.isAdmin()) {
			listKey.add("a");
			form.addButton(getString("Setting"));
		}
		form.sendPlayer(player);
		return true;
	}

	@Override
	public boolean disMain(FormResponse data) {
		switch (listKey.get(getSimple(data).getClickedButtonId())) {
		case "t":
			setForm(new TeamForm(player));
			break;
		case "v":
			setForm(new MainForm(player, this));
			break;
		case "a":
			setForm(new Setting(player, this));
			break;
		}
		return make();
	}
}
