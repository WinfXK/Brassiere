package cn.winfxk.brassiere.tip;

import java.util.LinkedHashMap;

import cn.nukkit.Player;
import cn.winfxk.brassiere.Activate;
import cn.winfxk.brassiere.Message;
import cn.winfxk.brassiere.MyPlayer;
import cn.winfxk.brassiere.sign.SignMag;
import cn.winfxk.brassiere.team.TeamApi;
import cn.winfxk.brassiere.vip.VipApi;

/**
 * 顶部底部文本格式化程序
 * 
 * @Createdate 2020/04/30 05:59:57
 * @author Winfxk
 */
public class TipContent {
	private transient LinkedHashMap<String, Tiptext> Tip = new LinkedHashMap<>();
	private transient LinkedHashMap<String, Tiptext> Top = new LinkedHashMap<>();
	private Activate ac;
	private Tip tip;
	private Message msg;
	private String Not;

	public TipContent(Tip tip) {
		ac = Activate.getActivate();
		this.tip = tip;
		msg = ac.getMessage();
		Not = ac.getConfig().getString("底部顶部无解析对象");
		Not = Not == null ? "无" : Not;
		Tip.put("{Player}", Player -> Player.getName());
		Tip.put("{Money}", Player -> MyPlayer.getMoney(Player.getName()));
		Tip.put("{WorldName}", Player -> Player.getLevel().getFolderName());
		Tip.put("{TeamName}", Player -> TeamApi.getTeam(Player.getName()) == null ? Not
				: TeamApi.getTeam(Player.getName()).getName());
		Tip.put("{Vip}",
				Player -> VipApi.getVip(Player.getName()) == null ? Not : VipApi.getVip(Player.getName()).getName());
		Tip.put("{Sign}", Player -> SignMag.getSignMag().getSign(Player.getName()) == null ? Not
				: SignMag.getSignMag().getSign(Player.getName()));
		for (String Key : Tip.keySet())
			Top.put(Key, Tip.get(Key));
	}

	/**
	 * 获取一个玩家的底部显示内容
	 * 
	 * @param player
	 * @return
	 */
	public String getTipString(Player player) {
		return msg.getText(tip.getTip(), player, Tip);
	}
 
	/**
	 * 获取一个玩家的顶部显示
	 * 
	 * @param player
	 * @return
	 */
	public String getTopString(Player player) {
		return msg.getText(tip.getTip(), player, Top);
	}

	/**
	 * 判断是否支持某个顶部数据内容
	 * 
	 * @param Key
	 * @return
	 */
	public boolean isTopContent(String Key) {
		return Top.containsKey(Key);
	}

	/**
	 * 删除一个已经支持了的顶部显示内容
	 * 
	 * @param Key
	 * @return
	 */
	public TipContent delTopContent(String Key) {
		if (Top.containsKey(Key))
			Top.remove(Key);
		return this;
	}

	/**
	 * 添加一个顶部显示内容
	 * 
	 * @param Key     数据Key
	 * @param tiptext 数据交互内容
	 * @return
	 */
	public TipContent addTopContent(String Key, Tiptext tiptext) {
		Top.put(Key, tiptext);
		return this;
	}

	/**
	 * 添加底部显示项目
	 * 
	 * @param Key     数据Key
	 * @param tiptext 数据交互内容
	 * @return
	 */
	public TipContent addContent(String Key, Tiptext tiptext) {
		Tip.put(Key, tiptext);
		return this;
	}

	/**
	 * 判断是否支持某个数据显示内容
	 * 
	 * @param Key 数据Key
	 * @return
	 */
	public boolean isContent(String Key) {
		return Tip.containsKey(Key);
	}

	/**
	 * 删除一个已经支持了的显示项目
	 * 
	 * @param Key 数据Key
	 * @return
	 */
	public TipContent delContent(String Key) {
		if (Tip.containsKey(Key))
			Tip.remove(Key);
		return this;
	}
}
