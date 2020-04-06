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
 * 基础UI操作类
 *
 * @author Winfxk
 */
public abstract class FormBase {
	public Player player;
	protected Message msg;
	protected FormID formID;
	protected Activate ac;
	protected MyPlayer myPlayer;
	protected List<String> listKey = new ArrayList<>();
	protected Object[] D = {};
	protected String[] K = {};
	private FormBase make;
	protected int ID;

	/**
	 * 界面交互基础类
	 *
	 * @param player 操作界面的玩家对象
	 */
	public FormBase(Player player) {
		this.player = player;
		ac = Activate.getActivate();
		msg = ac.getMessage();
		formID = ac.getFormID();
		myPlayer = ac.getPlayers(player.getName());
		ID = getID();
	}

	/**
	 * 返回页面的不重复ID</br>
	 * <b>PS: </b> 我自己懂这个是啥意思就好了你瞎掺和啥
	 *
	 * @return 不重复的ID
	 */
	public int getID() {
		return formID.getID(myPlayer.ID = myPlayer.ID == 0 ? 1 : 0);
	}

	/**
	 * 返回初始化的数据
	 *
	 * @return 返回Msg数据
	 */
	public Object[] getD() {
		return D;
	}

	/**
	 * 返回初始化的表
	 *
	 * @return 返回Msg键
	 */
	public String[] getK() {
		return K;
	}

	/**
	 * 页面主页
	 *
	 * @return 构建是否成功
	 */
	public abstract boolean MakeMain();

	/**
	 * 页面返回的数据
	 *
	 * @param data 界面传递的数据
	 * @return 数据处理是否成功
	 */
	public abstract boolean disMain(FormResponse data);

	/**
	 * 将书强转多样型
	 *
	 * @param data 默认的数据
	 * @return 自定义数据
	 */
	public FormResponseCustom getCustom(FormResponse data) {
		return (FormResponseCustom) data;
	}

	/**
	 * 将数据强转简单型
	 *
	 * @param data 默认的数据
	 * @return 简单截面数据
	 */
	public FormResponseSimple getSimple(FormResponse data) {
		return (FormResponseSimple) data;
	}

	/**
	 * 将数据强转选择型
	 *
	 * @param data 默认的数据
	 * @return 选择型界面的数据
	 */
	public FormResponseModal getModal(FormResponse data) {
		return (FormResponseModal) data;
	}

	/**
	 * 设置数据
	 *
	 * @param objects 要设置的Msg数据
	 */
	public void setD(Object... objects) {
		D = objects;
	}

	/**
	 * 设置表
	 *
	 * @param strings 要设置的Msg键
	 */
	public void setK(String... strings) {
		K = strings;
	}

	/**
	 * 设置一个页面为当前玩家操作的页面
	 *
	 * @param base 即将给玩家显示的界面对象
	 * @return 当前操作的界面
	 */
	public FormBase setForm(FormBase base) {
		make = base;
		return this;
	}

	/**
	 * 构建下一个界面
	 *
	 * @return 下一个构建是否成功
	 */
	public boolean make() {
		if (make == null)
			throw new FormException("The interface is empty, unable to display normally! Please contact Winfxk.");
		return (myPlayer.form = make).MakeMain();
	}

	@Override
	public String toString() {
		return "Player: " + player.getName() + "\nForm: " + getID();
	}
}
