package cn.winfxk.brassiere.sign;

import cn.nukkit.Player;
import cn.nukkit.form.response.FormResponse;
import cn.winfxk.brassiere.form.FormBase;
import cn.winfxk.brassiere.sign.my.MySign;
import cn.winfxk.brassiere.tool.SimpleForm;

/**
 * 称号页面首页
 * 
 * @Createdate 2020/04/18 20:09:57
 * @author Winfxk
 */
public class SignMain extends FormBase {

	public SignMain(Player player) {
		super(player);
		setK("{Player}", "{Money}", "{Sign}");
	}

	@Override
	public boolean MakeMain() {
		setD(player.getName(), myPlayer.getMoney(), myPlayer.getSign());
		SimpleForm form = new SimpleForm(getID(), msg.getSun("Sign", "Main", "Title", K, D),
				msg.getSun("Sign", "Main", "Content", K, D));
		form.addButton(msg.getSun("Sign", "Main", "SignShop", K, D));
		if (myPlayer.getSigns() != null || myPlayer.getSigns().size() >= 1)
			form.addButton(msg.getSun("Sign", "Main", "MySign", K, D));
		form.sendPlayer(player);
		return true;
	}

	@Override
	public boolean disMain(FormResponse data) {
		return setForm(getSimple(data).getClickedButtonId() == 0 ? setForm(new SignShop(player)) : new MySign(player))
				.make();
	}
}
