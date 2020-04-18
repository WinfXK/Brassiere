package cn.winfxk.brassiere.sign.mysign;

import cn.nukkit.Player;
import cn.nukkit.form.response.FormResponse;
import cn.winfxk.brassiere.form.FormBase;
import cn.winfxk.brassiere.sign.SignMain;
import cn.winfxk.brassiere.tool.SimpleForm;
import cn.winfxk.brassiere.tool.Tool;

/**
 * 查看我的称号列表
 * 
 * @Createdate 2020/04/18 21:22:58
 * @author Winfxk
 */
public class Signlist extends FormBase {

	public Signlist(Player player) {
		super(player);
		setK("{Player}", "{Money}", "{Sign}", "{Signs}");
		listKey = myPlayer.getSigns();
	}

	@Override
	public boolean MakeMain() {
		setD(player.getName(), myPlayer.getMoney(), myPlayer.getSign(), listKey);
		if (listKey.size() <= 0)
			return ac.makeForm.Tip(player, msg.getSun("Sign", "Signlist", "NotSign", K, D));
		SimpleForm form = new SimpleForm(getID(), msg.getSun("Sign", "Signlist", "Title", K, D),
				msg.getSun("Sign", "Signlist", "Content", K, D));
		String[] Ks = Tool.Arrays(K, new String[] { "{SignItem}" });
		for (String string : listKey)
			form.addButton(msg.getSun("Sign", "Signlist", "Content", Ks, Tool.Arrays(D, new Object[] { string })));
		form.addButton(msg.getSun("Sign", "Signlist", "Back", K, D));
		form.sendPlayer(player);
		return true;
	}

	@Override
	public boolean disMain(FormResponse data) {
		int ID = getSimple(data).getClickedButtonId();
		if (ID >= listKey.size())
			return setForm(new SignMain(player)).make();
		player.sendMessage(msg.getSun("Sign", "Signlist", "OK", K, D));
		myPlayer.setSign(listKey.get(ID));
		return setForm(new MySign(player)).make();
	}
}
