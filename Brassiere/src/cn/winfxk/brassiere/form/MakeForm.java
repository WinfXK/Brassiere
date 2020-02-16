package cn.winfxk.brassiere.form;

import cn.nukkit.Player;
import cn.winfxk.brassiere.Activate;
import cn.winfxk.brassiere.Message;
import cn.winfxk.brassiere.tool.ModalForm;
import cn.winfxk.brassiere.tool.SimpleForm;
import cn.winfxk.brassiere.tool.Tool;

/**
 * @author Winfxk
 */
public class MakeForm {
	private Activate ac;
	private Message msg;

	/**
	 * 基本界面类
	 *
	 * @param ac
	 */
	public MakeForm(Activate ac) {
		this.ac = ac;
		msg = ac.getMessage();
	}

	/**
	 * 显示一个提示框给玩家
	 *
	 * @param player
	 * @param Content
	 * @return
	 */
	public boolean Tip(Player player, String Content) {
		return Tip(player, Content, false);
	}

	/**
	 * 显示一个提示框给玩家
	 *
	 * @param player
	 * @param Content
	 * @param Title
	 * @return
	 */
	public boolean Tip(Player player, String Content, String Title) {
		return Tip(player, Tool.getRandColor() + ac.getPluginBase().getName(), Content, false);
	}

	/**
	 * 显示一个提示框给玩家
	 *
	 * @param player
	 * @param Content
	 * @param Return
	 * @return
	 */
	public boolean Tip(Player player, String Content, boolean Return) {
		return Tip(player, Tool.getRandColor() + ac.getPluginBase().getName(), Content, Return);
	}

	/**
	 * 显示一个提示框给玩家
	 *
	 * @param player
	 * @param Title
	 * @param Content
	 * @param Return
	 * @return
	 */
	public boolean Tip(Player player, String Title, String Content, boolean Return) {
		return Tip(player, Title, Content, Tool.getRandColor() + "Yes", Tool.getRandColor() + "No", true, Return);
	}

	/**
	 * 显示一个提示框给玩家
	 *
	 * @param player
	 * @param Title
	 * @param Content
	 * @param Bt1
	 * @param Bt2
	 * @param isModal
	 * @param Return
	 * @return
	 */
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
			ModalForm form = new ModalForm(Tool.getRand(), Title, Content, Bt1, Bt2);
			form.sendPlayer(player);
		} else {
			SimpleForm form = new SimpleForm(Tool.getRand(), Title, Content);
			form.addButton(Bt1);
			if (Bt2 != null && !Bt2.isEmpty())
				form.addButton(Bt2);
			form.sendPlayer(player);
		}
		return Return;
	}
}
