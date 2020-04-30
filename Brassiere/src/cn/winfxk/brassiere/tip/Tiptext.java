package cn.winfxk.brassiere.tip;

import cn.nukkit.Player;

/**
 * 底部内容对接类
 * 
 * @Createdate 2020/04/30 06:25:48
 * @author Winfxk
 */
public interface Tiptext {
	/**
	 * 获取对应的文本
	 * 
	 * @param player
	 * @return
	 */
	public Object getString(Player player);
}
