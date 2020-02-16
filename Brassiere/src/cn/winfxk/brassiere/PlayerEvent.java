package cn.winfxk.brassiere;

import cn.nukkit.Player;
import cn.nukkit.event.EventHandler;
import cn.nukkit.event.Listener;
import cn.nukkit.event.block.BlockBreakEvent;
import cn.nukkit.event.block.BlockPlaceEvent;
import cn.nukkit.event.entity.EntityDamageEvent;
import cn.nukkit.event.player.PlayerDeathEvent;
import cn.nukkit.event.player.PlayerFormRespondedEvent;
import cn.nukkit.event.player.PlayerInteractEvent;
import cn.nukkit.event.player.PlayerJoinEvent;
import cn.nukkit.event.player.PlayerQuitEvent;
import cn.nukkit.event.player.PlayerRespawnEvent;
import cn.nukkit.form.response.FormResponse;
import cn.nukkit.form.response.FormResponseCustom;
import cn.nukkit.form.response.FormResponseModal;
import cn.nukkit.form.response.FormResponseSimple;

/**
 * @author Winfxk
 */
public class PlayerEvent implements Listener {
	private Activate ac;
	private Message msg;

	/**
	 * 监听玩家事件
	 *
	 * @param ac
	 */
	public PlayerEvent(Activate ac) {
		this.ac = ac;
		msg = ac.getMessage();
	}

	/**
	 * 表单接受事件
	 *
	 * @param e
	 */
	@EventHandler
	public void onFormResponded(PlayerFormRespondedEvent e) {
		Player player = e.getPlayer();
		try {
			FormResponse data = e.getResponse();
			if (player == null || e.wasClosed() || data == null || !(data instanceof FormResponseCustom)
					&& !(data instanceof FormResponseSimple) && !(data instanceof FormResponseModal))
				return;
			MyPlayer myPlayer = ac.getPlayers(player.getName());
			if (myPlayer == null)
				return;
			int ID = e.getFormID();
			FormID f = ac.getFormID();
			if ((ID == f.getID(0) || ID == f.getID(1)) && myPlayer.makeBase != null)
				myPlayer.makeBase.disMain(data);
		} catch (Exception e2) {
			e2.printStackTrace();
			if (player != null)
				player.sendMessage(msg.getMessage("数据处理错误", new String[] { "{Player}", "{Money}", "{Error}" },
						new Object[] { player.getName(), MyPlayer.getMoney(player.getName()), e2.getMessage() }));
		}
	}

	/**
	 * 监听玩家进服事件
	 *
	 * @param e
	 */
	@EventHandler
	public void onJoin(PlayerJoinEvent e) {
		if (!ac.getPluginBase().isEnabled())
			return;
		Player player = e.getPlayer();
		ac.setPlayers(player, new MyPlayer(player));
	}

	/**
	 * 监听都比重生事件
	 *
	 * @param e
	 */
	@EventHandler
	public void onRespawn(PlayerRespawnEvent e) {
		if (!ac.getPluginBase().isEnabled())
			return;
	}

	/**
	 * 监听玩家退服事件
	 *
	 * @param e
	 */
	@EventHandler
	public void onQuit(PlayerQuitEvent e) {
		if (!ac.getPluginBase().isEnabled())
			return;
		Player player = e.getPlayer();
		if (ac.isPlayers(player))
			ac.removePlayers(player);
	}

	/**
	 * 监听玩家死亡事件
	 *
	 * @param e
	 */
	@EventHandler
	public void onDeath(PlayerDeathEvent e) {
		if (!ac.getPluginBase().isEnabled())
			return;
	}

	/**
	 * 监听玩家付香商骇事件
	 *
	 * @param e
	 */
	@EventHandler
	public void onDamage(EntityDamageEvent e) {
		if (!ac.getPluginBase().isEnabled())
			return;
	}

	/**
	 * 监听玩家点击交互事件
	 *
	 * @param e
	 */
	@EventHandler
	public void onClick(PlayerInteractEvent e) {
		if (!ac.getPluginBase().isEnabled())
			return;
	}

	/**
	 * 方块放置事件
	 *
	 * @param e
	 */
	@EventHandler
	public void onBlockPlace(BlockPlaceEvent e) {
		if (!ac.getPluginBase().isEnabled())
			return;
	}

	/**
	 * 玩家破坏方块事件
	 *
	 * @param e
	 */
	@EventHandler
	public void onBreak(BlockBreakEvent e) {
		if (!ac.getPluginBase().isEnabled())
			return;
	}
}
