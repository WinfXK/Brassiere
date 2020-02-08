package cn.epicfx.winfxk.donthitme.form;

import cn.epicfx.winfxk.donthitme.Activate;
import cn.epicfx.winfxk.donthitme.Message;
import cn.epicfx.winfxk.donthitme.tool.ModalForm;
import cn.epicfx.winfxk.donthitme.tool.SimpleForm;
import cn.epicfx.winfxk.donthitme.tool.Tool;
import cn.nukkit.Player;

/**
 * @author Winfxk
 */
public class MakeForm {
	private Activate ac;
	private Message msg;

	public MakeForm(Activate ac) {
		this.ac = ac;
		msg = ac.getMessage();
	}

	public boolean Tip(Player player, String Content) {
		return Tip(player, Content, false);
	}

	public boolean Tip(Player player, String Content, boolean Return) {
		return Tip(player, Tool.getRandColor() + ac.getPluginBase().getName(), Content, Return);
	}

	public boolean Tip(Player player, String Title, String Content, boolean Return) {
		return Tip(player, Title, Content, Tool.getRandColor() + "Yes", Tool.getRandColor() + "No", true, Return);
	}

	public boolean Tip(Player player, String Title, String Content, String Bt1, String Bt2, boolean isModal,
			boolean Return) {
		if (Title != null)
			Title = msg.getText(Title);
		if (Content != null)
			Content = msg.getText(Content);
		if (Bt1 != null)
			Bt1 = msg.getText(Bt1);
		if (Bt2 != null)
			Bt2 = msg.getText(Bt2);
		if (isModal) {
			SimpleForm form = new SimpleForm(Tool.getRand(), Title, Content);
			form.addButton(Bt1);
			if (Bt2 != null && !Bt2.isEmpty())
				form.addButton(Bt2);
			form.sendPlayer(player);
		} else {
			ModalForm form = new ModalForm(Tool.getRand(), Title, Content, Bt1, Bt2);
			form.sendPlayer(player);
		}
		return Return;
	}
}
