package cn.winfxk.brassiere;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import cn.nukkit.AdventureSettings.Type;
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
import cn.nukkit.item.Item;
import cn.winfxk.brassiere.tool.Tool;
import cn.winfxk.brassiere.vip.VipApi;

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
			if (player == null)
				return;
			MyPlayer myPlayer = ac.getPlayers(player.getName());
			if (myPlayer == null)
				return;
			FormResponse data = e.getResponse();
			if (e.wasClosed() || data == null || !(data instanceof FormResponseCustom)
					&& !(data instanceof FormResponseSimple) && !(data instanceof FormResponseModal)) {
				myPlayer.form = null;
				return;
			}
			int ID = e.getFormID();
			FormID f = ac.getFormID();
			if ((ID == f.getID(0) || ID == f.getID(1)) && myPlayer.form != null)
				myPlayer.form.disMain(data);
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
		MyPlayer myPlayer = new MyPlayer(player);
		String notTeam = msg.getSon("Main", "notTeam", player);
		String notVip = msg.getSon("Main", "notVip", player);
		ac.setPlayers(player, myPlayer);
		if (myPlayer.isVip()) {
			if (myPlayer.vip.isJoinTip())
				myPlayer.vip.getJoinMsgType().send(msg.getText(myPlayer.vip.getJoinMsg(),
						new String[] { "{Player}", "{Money}", "{TeamID}", "{TeamName}", "{Player}", "{Money}",
								"{VipName}", "{VipID}", "{VipLevel}", "{VipExp}", "{VipTime}" },
						new Object[] { player.getName(), myPlayer.getMoney(),
								myPlayer.isTeam() ? notTeam : myPlayer.getTeam().getID(),
								myPlayer.isTeam() ? notTeam : myPlayer.getTeam().getName(),
								myPlayer.isVip() ? myPlayer.vip.getName() : notVip,
								myPlayer.isVip() ? myPlayer.vip.getID() : notVip,
								myPlayer.isVip() ? myPlayer.vip.getAlg().getLevel(player.getName()) : notVip }));
			if (myPlayer.vip.isJoinSound())
				myPlayer.vip.sendJoinSoundName(player);
			Calendar calendar = Calendar.getInstance();
			calendar.setTime(Tool.parseDate(myPlayer.getConfig().getString("QuitTime")));
			long timeInMillis1 = calendar.getTimeInMillis();
			calendar.setTime(Tool.parseDate(Tool.getDate() + " " + Tool.getTime()));
			long timeInMillis2 = calendar.getTimeInMillis();
			player.getAdventureSettings().set(Type.ALLOW_FLIGHT, myPlayer.vip.isFlight());
			player.getAdventureSettings().update();
			myPlayer.getConfig().set("VipTime",
					myPlayer.getConfig().getInt("VipTime") - ((timeInMillis2 - timeInMillis1) / (1000 * 3600)));
			if (myPlayer.getConfig().getInt("VipTime") <= 0)
				VipApi.remove(player.getName());
			myPlayer.config.save();
		}
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
		Player player = e.getEntity();
		MyPlayer myPlayer = ac.getPlayers(player);
		if (myPlayer.isVip())
			e.setKeepInventory(myPlayer.vip.isKeepInventory());
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
		if (!(e.getEntity() instanceof Player))
			return;
		Player player = (Player) e.getEntity();
		MyPlayer myPlayer = ac.getPlayers(player);
		if (myPlayer.isTeam() && !myPlayer.getTeam().isAllowedPVP())
			e.setCancelled();
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
		Player player = e.getPlayer();
		MyPlayer myPlayer = ac.getPlayers(player);
		if (myPlayer.isVip() && myPlayer.vip.isExcavateIncrease()) {
			List<Item> list = new ArrayList<>();
			for (Item item : e.getDrops()) {
				item.setCount(item.count * (2 * (Tool.getRand(1, myPlayer.vip.getIncreases()) / 10)));
				list.add(item);
			}
			e.setDrops((Item[]) list.toArray());
		}
	}
}
