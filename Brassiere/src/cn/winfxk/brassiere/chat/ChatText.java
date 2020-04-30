package cn.winfxk.brassiere.chat;

import cn.nukkit.Player;

/**
 * 玩家聊天格式化文本%&*……&*%……&*&%……我也不知道应该怎么表达
 * 
 * @Createdate 2020/04/30 08:42:11
 * @author Winfxk
 */
public interface ChatText {
	/**
	 * 获取对应的文本
	 * 
	 * @param player
	 * @return
	 */
	public Object getString(Player player);
}
