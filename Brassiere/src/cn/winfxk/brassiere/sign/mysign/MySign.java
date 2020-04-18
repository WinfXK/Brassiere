package cn.winfxk.brassiere.sign.mysign;

import cn.nukkit.Player;
import cn.nukkit.form.response.FormResponse;
import cn.winfxk.brassiere.form.FormBase;
import cn.winfxk.brassiere.sign.SignMain;
import cn.winfxk.brassiere.tool.SimpleForm;

/**
 * 玩家个人称号主页
 * 
 * @Createdate 2020/04/18 21:05:34
 * @author Winfxk
 */
public class MySign extends FormBase {

	public MySign(Player player) {
		super(player);
		setK("{Player}", "{Money}", "{Sign}", "{Signs}");
		setD(player.getName(), myPlayer.getMoney(), myPlayer.getSign(), myPlayer.getSigns());
	}

	@Override
	public boolean MakeMain() {
		setD(player.getName(), myPlayer.getMoney(), myPlayer.getSign(), myPlayer.getSigns());
		SimpleForm form = new SimpleForm(getID(), msg.getSun("Sign", "MySign", "Title", K, D),
				msg.getSun("Sign", "MySign", "Content", K, D));
		form.addButton(msg.getSun("Sign", "MySign", "MySign", K, D));
		form.addButton(msg.getSun("Sign", "MySign", "Back", K, D));
		form.sendPlayer(player);
		return true;
	}

	@Override
	public boolean disMain(FormResponse data) {
		return setForm(getSimple(data).getClickedButtonId() != 0 ? new SignMain(player) : new Signlist(player)).make();
	}
}
