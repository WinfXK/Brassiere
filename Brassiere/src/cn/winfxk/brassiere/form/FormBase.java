package cn.winfxk.brassiere.form;

import java.util.ArrayList;
import java.util.List;

import cn.nukkit.Player;
import cn.nukkit.form.response.FormResponse;
import cn.nukkit.form.response.FormResponseCustom;
import cn.nukkit.form.response.FormResponseModal;
import cn.nukkit.form.response.FormResponseSimple;
import cn.winfxk.brassiere.Activate;
import cn.winfxk.brassiere.FormID;
import cn.winfxk.brassiere.Message;
import cn.winfxk.brassiere.MyPlayer;

/**
 * @author Winfxk
 */
public abstract class FormBase {
	public Player player;
	public Message msg;
	public FormID formID;
	public Activate ac;
	public MyPlayer myPlayer;
	public List<String> fk = new ArrayList<>();
	protected Object[] D = {};
	protected String[] K = {};

	/**
	 * 界面交互基础类
	 *
	 * @param player
	 */
	public FormBase(Player player) {
		this.player = player;
		ac = Activate.getActivate();
		msg = ac.getMessage();
		formID = ac.getFormID();
		myPlayer = ac.getPlayers(player.getName());
	}

	/**
	 * 返回页面的不重复ID</br>
	 * <b>PS: </b> 我自己懂这个是啥意思就好了你瞎掺和啥
	 *
	 * @return
	 */
	public int getID() {
		return formID.getID(myPlayer.ID = myPlayer.ID == 0 ? 1 : 0);
	}

	/**
	 * 返回初始化的数据
	 *
	 * @return
	 */
	public Object[] getD() {
		return D;
	}

	/**
	 * 返回初始化的表
	 *
	 * @return
	 */
	public String[] getK() {
		return K;
	}

	/**
	 * 页面主页
	 *
	 * @return
	 */
	public abstract boolean MakeMain();

	/**
	 * 页面返回的数据
	 *
	 * @param data
	 * @return
	 */
	public abstract boolean disMain(FormResponse data);

	/**
	 * 将书强转多样型
	 *
	 * @param data
	 * @return
	 */
	public FormResponseCustom getCustom(FormResponse data) {
		return (FormResponseCustom) data;
	}

	/**
	 * 将数据强转简单型
	 *
	 * @param data
	 * @return
	 */
	public FormResponseSimple getSimple(FormResponse data) {
		return (FormResponseSimple) data;
	}

	/**
	 * 将数据强转选择型
	 *
	 * @param data
	 * @return
	 */
	public FormResponseModal getModal(FormResponse data) {
		return (FormResponseModal) data;
	}

	/**
	 * 设置数据
	 *
	 * @param objects
	 */
	public void setD(Object... objects) {
		D = objects;
	}

	/**
	 * 设置表
	 *
	 * @param strings
	 */
	public void setK(String... strings) {
		K = strings;
	}

	/**
	 * 设置一个页面为当前玩家操作的页面
	 *
	 * @param base
	 * @return
	 */
	public FormBase setForm(FormBase base) {
		myPlayer.makeBase = base;
		return this;
	}

	/**
	 * 构建下一个界面
	 *
	 * @return
	 */
	public boolean make() {
		if (myPlayer.makeBase == null)
			throw new FormException("The interface is empty, unable to display normally! Please contact Winfxk.");
		return myPlayer.makeBase.MakeMain();
	}

	@Override
	public String toString() {
		return "Player: " + player.getName() + "\nForm: " + getID();
	}
}
