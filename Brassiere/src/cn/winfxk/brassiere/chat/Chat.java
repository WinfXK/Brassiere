package cn.winfxk.brassiere.chat;

import java.util.LinkedHashMap;

import cn.nukkit.Player;
import cn.nukkit.Server;
import cn.nukkit.event.EventHandler;
import cn.nukkit.event.Listener;
import cn.nukkit.event.player.PlayerChatEvent;
import cn.winfxk.brassiere.Activate;
import cn.winfxk.brassiere.Message;
import cn.winfxk.brassiere.MyPlayer;
import cn.winfxk.brassiere.sign.SignMag;
import cn.winfxk.brassiere.team.TeamApi;
import cn.winfxk.brassiere.vip.VipApi;

/**
 * 玩家聊天的控制类
 * 
 * @Createdate 2020/04/30 08:30:11
 * @author Winfxk
 */
public class Chat implements Listener {
	private Activate ac;
	private Message msg;
	private Server server;
	private static final String[] Key = { "{Player}", "{Money}", "{TeamName}", "{Sign}", "{Vip}" };
	private boolean isEnabled;
	private static Chat chat;
	/**
	 * 聊天展现模式，当这个值为真时仅玩家可以看到消息
	 */
	private boolean showMode;
	/**
	 * 聊天内容最大长度
	 */
	private int ChatMaxLength;
	private String ChatString, ChatByTeam, ChatByVip, ChatBySign, notString;
	/**
	 * 当这个值为真时将不再显示没有值的内容（如未加入队伍，不是VIP无称号等）
	 */
	private boolean SimpleModel;
	private transient LinkedHashMap<String, ChatText> map = new LinkedHashMap<>();

	public Chat(Activate ac) {
		chat = this;
		this.ac = ac;
		server = Server.getInstance();
		ac.getPluginBase().getServer().getPluginManager().registerEvents(this, ac.getPluginBase());
		isEnabled = ac.getConfig().getBoolean("聊天功能");
		map.put("{Player}", Player -> Player.getName());
		map.put("{Money}", Player -> MyPlayer.getMoney(Player.getName()));
		map.put("{WorldName}", Player -> Player.getLevel().getFolderName());
		map.put("{TeamName}",
				Player -> TeamApi.getTeam(Player.getName()) == null ? "" : TeamApi.getTeam(Player.getName()).getName());
		map.put("{Vip}",
				Player -> VipApi.getVip(Player.getName()) == null ? "" : VipApi.getVip(Player.getName()).getName());
		map.put("{Sign}",
				Player -> SignMag.getSignMag().getSign(Player.getName()) == null
						|| SignMag.getSignMag().getSign(Player.getName()).isEmpty() ? ""
								: SignMag.getSignMag().getSign(Player.getName()));
		map.put("{聊天队伍部分}", Player -> getChatByTeam(Player));
		map.put("{聊天VIP部分}", Player -> getChatByVip(Player));
		map.put("{聊天称号部分}", Player -> getChatBySign(Player));
		ChatString = ac.getConfig().getString("聊天文本");
		ChatByTeam = ac.getConfig().getString("聊天队伍部分");
		ChatByVip = ac.getConfig().getString("聊天VIP部分");
		ChatBySign = ac.getConfig().getString("聊天称号部分");
		notString = ac.getConfig().getString("聊天填充");
		SimpleModel = ac.getConfig().getBoolean("聊天缩放模式");
		ChatMaxLength = ac.getConfig().getInt("聊天最大长度");
		showMode = ac.getConfig().getBoolean("聊天限制模式");
		msg = ac.getMessage();
	}

	/**
	 * 删除一个聊天格式化规则
	 * 
	 * @param Key 简要删除格式化的变量
	 * @return
	 */
	public boolean delChat(String Key) {
		if (!map.containsKey(Key))
			return false;
		map.remove(Key);
		return true;
	}

	/**
	 * 添加一个聊天格式化规则
	 * 
	 * @param Key  将会被格式化的变量
	 * @param text 变量处理程序
	 * @return
	 */
	public boolean addChat(String Key, ChatText text) {
		if (map.containsKey(Key))
			return false;
		map.put(Key, text);
		return true;
	}

	/**
	 * 聊天监听
	 * 
	 * @param e
	 */
	@EventHandler
	public void onChat(PlayerChatEvent e) {
		if (e.isCancelled() || !ac.getPluginBase().isEnabled() || !isEnabled)
			return;
		if (showMode) {
			for (Player player : server.getOnlinePlayers().values())
				player.sendMessage(msg.getText(ChatString, e.getPlayer(), map, e.getMessage()));
		} else
			server.broadcastMessage(msg.getText(ChatString, e.getPlayer(), map, e.getMessage()));
		e.setCancelled();
	}

	/**
	 * 获取对接接口
	 * 
	 * @return
	 */
	public static Chat getChat() {
		return chat;
	}

	/**
	 * 判断聊天功能是否开启
	 * 
	 * @return
	 */
	public boolean isEnabled() {
		return isEnabled;
	}

	/**
	 * 设置聊天功能启动状态
	 * 
	 * @param isEnabled
	 * @return
	 */
	public boolean setEnabled(boolean isEnabled) {
		ac.getConfig().set("聊天功能", this.isEnabled = isEnabled);
		return ac.getConfig().save();
	}

	/**
	 * 称号部分将会显示的内容
	 * 
	 * @return
	 */
	public String getChatBySign(Player player) {
		String sign = SignMag.getSignMag().getSign(player.getName());
		return sign != null && !sign.isEmpty() ? getString(player, ChatBySign) : SimpleModel ? "" : notString;
	}

	/**
	 * 队伍部分将会显示的内容
	 * 
	 * @return
	 */
	public String getChatByTeam(Player player) {
		return TeamApi.isJoinTeam(player.getName()) ? getString(player, ChatByTeam) : SimpleModel ? "" : notString;
	}

	/**
	 * VIP部分将会显示的内容
	 * 
	 * @return
	 */
	public String getChatByVip(Player player) {
		return VipApi.isVip(player.getName()) ? getString(player, ChatByVip) : SimpleModel ? "" : notString;
	}

	/**
	 * 获取对应值下得格式化文本
	 * 
	 * @param player
	 * @param Key
	 * @return
	 */
	private String getString(Player player, String Key) {
		return msg.getText(Key, Chat.Key, new Object[] { player.getName(), MyPlayer.getMoney(player.getName()),
				TeamApi.isJoinTeam(player.getName()) ? TeamApi.getTeam(player.getName()) : notString,
				SignMag.getSignMag().getSign(player.getName()) == null ? notString
						: SignMag.getSignMag().getSign(player.getName()),
				VipApi.getVip(player.getName()) != null ? VipApi.getVip(player.getName()).getName() : notString });
	}

	/**
	 * 最大聊天长度
	 * 
	 * @return
	 */
	public int getChatMaxLength() {
		return ChatMaxLength;
	}

	/**
	 * 聊天时将会显示的内容
	 * 
	 * @return
	 */
	public String getChatString() {
		return ChatString;
	}

	/**
	 * 聊天展现模式，当这个值为真时仅玩家可以看到消息
	 * 
	 * @return
	 */
	public boolean isShowMode() {
		return showMode;
	}

	/**
	 * 当这个值为真时将不再显示没有值的内容（如未加入队伍，不是VIP无称号等）
	 * 
	 * @return
	 */
	public boolean isSimpleModel() {
		return SimpleModel;
	}
}
