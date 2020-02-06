package cn.epicfx.winfxk.donthitme;

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
		FormResponse data = e.getResponse();
		int ID = e.getFormID();
		FormID f = ac.getFormID();
		Player player = e.getPlayer();
		if (player == null || e.wasClosed() || e.getResponse() == null
				|| (!(e.getResponse() instanceof FormResponseCustom) && !(e.getResponse() instanceof FormResponseSimple)
						&& !(e.getResponse() instanceof FormResponseModal)))
			return;
	}

	/**
	 * 监听玩家进服事件
	 *
	 * @param e
	 */
	@EventHandler
	public void onJoin(PlayerJoinEvent e) {
		if (!ac.getDonthitme().isEnabled())
			return;
	}

	/**
	 * 监听都比重生事件
	 *
	 * @param e
	 */
	@EventHandler
	public void onRespawn(PlayerRespawnEvent e) {
		if (!ac.getDonthitme().isEnabled())
			return;
	}

	/**
	 * 监听玩家退服事件
	 *
	 * @param e
	 */
	@EventHandler
	public void onQuit(PlayerQuitEvent e) {
		if (!ac.getDonthitme().isEnabled())
			return;
	}

	/**
	 * 监听玩家死亡事件
	 *
	 * @param e
	 */
	@EventHandler
	public void onDeath(PlayerDeathEvent e) {
		if (!ac.getDonthitme().isEnabled())
			return;
	}

	/**
	 * 监听玩家付香商骇事件
	 *
	 * @param e
	 */
	@EventHandler
	public void onDamage(EntityDamageEvent e) {
		if (!ac.getDonthitme().isEnabled())
			return;
	}

	/**
	 * 监听玩家点击交互事件
	 *
	 * @param e
	 */
	@EventHandler
	public void onClick(PlayerInteractEvent e) {
		if (!ac.getDonthitme().isEnabled())
			return;
	}

	/**
	 * 方块放置事件
	 *
	 * @param e
	 */
	@EventHandler
	public void onBlockPlace(BlockPlaceEvent e) {
		if (!ac.getDonthitme().isEnabled())
			return;
	}

	/**
	 * 玩家破坏方块事件
	 *
	 * @param e
	 */
	@EventHandler
	public void onBreak(BlockBreakEvent e) {
		if (!ac.getDonthitme().isEnabled())
			return;
	}
}
