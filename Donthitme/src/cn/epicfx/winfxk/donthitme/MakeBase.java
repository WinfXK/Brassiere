package cn.epicfx.winfxk.donthitme;

import cn.nukkit.Player;

/**
 * @author Winfxk
 */
public abstract class MakeBase {
	public Player player;
	public Message msg;
	public FormID formID;
	public Activate ac;
	public int ID = 0;

	public MakeBase(Player player) {
		this.player = player;
		ac = Activate.getActivate();
		msg = ac.message;
		formID = ac.FormID;
	}

	public int getFormID() {
		return formID.getID(ID = ID == 0 ? 1 : 0);
	}

	public abstract boolean MakeMain();

	public abstract boolean disMain();
}
