package cn.epicfx.winfxk.donthitme.tool;

import java.util.List;

import cn.nukkit.Player;
import cn.nukkit.Server;
import cn.nukkit.form.window.FormWindow;

/**
 * @author Winfxk
 */
public abstract class RootForm {
	public int ID;
	public String Title;

	/**
	 * @param ID 表单ID
	 */
	public RootForm(int ID) {
		this(ID, "");
	}

	public RootForm(int ID, String Title) {
		this.ID = ID;
		this.Title = Title;
	}

	/**
	 * 返回要显示的窗口
	 *
	 * @return
	 */
	public abstract FormWindow getFormWindow();

	/**
	 * 设置表单ID
	 *
	 * @param ID
	 * @return
	 */
	public RootForm setID(int ID) {
		this.ID = ID;
		return this;
	}

	/**
	 * 设置表单标题
	 *
	 * @param Title
	 * @return
	 */
	public RootForm setTitle(String Title) {
		this.Title = Title;
		return this;
	}

	/**
	 * 发送给服务器全部玩家
	 *
	 * @return 表单ID
	 */
	public int sendAllPlayer() {
		for (Player player : Server.getInstance().getOnlinePlayers().values())
			player.showFormWindow(getFormWindow(), ID);
		return ID;
	}

	/**
	 * 将表单发送给指定玩家列表
	 *
	 * @param players
	 * @return
	 */
	public int sendPlayer(List<Player> players) {
		for (Player player : players)
			player.showFormWindow(getFormWindow(), ID);
		return ID;
	}

	/**
	 * 将表单发送给指定玩家列表
	 *
	 * @param players
	 * @return
	 */
	public int sendPlayer(Player... player) {
		for (Player p : player)
			p.showFormWindow(getFormWindow(), ID);
		return ID;
	}

	public String getTitle() {
		return Title;
	}

	public int getID() {
		return ID;
	}
}
