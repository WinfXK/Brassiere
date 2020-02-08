package cn.epicfx.winfxk.donthitme.form;

import java.util.ArrayList;
import java.util.List;

import cn.epicfx.winfxk.donthitme.Activate;
import cn.epicfx.winfxk.donthitme.FormID;
import cn.epicfx.winfxk.donthitme.Message;
import cn.epicfx.winfxk.donthitme.MyPlayer;
import cn.nukkit.Player;
import cn.nukkit.form.response.FormResponse;
import cn.nukkit.form.response.FormResponseCustom;
import cn.nukkit.form.response.FormResponseModal;
import cn.nukkit.form.response.FormResponseSimple;

/**
 * @author Winfxk
 */
public abstract class FormBase {
	public FormBase make;
	public Player player;
	public Message msg;
	public FormID formID;
	public Activate ac;
	public MyPlayer myPlayer;
	public List<String> fk = new ArrayList<>();

	public FormBase(Player player) {
		this.player = player;
		ac = Activate.getActivate();
		msg = ac.getMessage();
		formID = ac.getFormID();
		myPlayer = ac.getPlayers(player.getName());
	}

	public int getID() {
		return formID.getID(myPlayer.ID = myPlayer.ID == 0 ? 1 : 0);
	}

	public abstract boolean MakeMain();

	public abstract boolean disMain(FormResponse data);

	public FormResponseCustom getCustom(FormResponse data) {
		return (FormResponseCustom) data;
	}

	public FormResponseSimple getSimple(FormResponse data) {
		return (FormResponseSimple) data;
	}

	public FormResponseModal getModal(FormResponse data) {
		return (FormResponseModal) data;
	}
}
