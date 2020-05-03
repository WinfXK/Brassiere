package cn.winfxk.brassiere.vip;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.google.common.base.Preconditions;

import cn.nukkit.Player;
import cn.nukkit.item.Item;
import cn.nukkit.level.particle.Particle;
import cn.nukkit.network.protocol.PlaySoundPacket;
import cn.nukkit.potion.Effect;
import cn.nukkit.utils.Config;
import cn.winfxk.brassiere.Activate;
import cn.winfxk.brassiere.money.MyEconomy;
import cn.winfxk.brassiere.tool.MyParticle;
import cn.winfxk.brassiere.tool.Tool;
import cn.winfxk.brassiere.vip.alg.LevelAlg;
import cn.winfxk.brassiere.vip.joinmsg.JoinMsg;
import cn.winfxk.brassiere.vip.joinmsg.Message;
import cn.winfxk.brassiere.vip.joinmsg.Popup;
import cn.winfxk.brassiere.vip.joinmsg.Tip;
import cn.winfxk.brassiere.vip.joinmsg.Title;

/**
 * @author Winfxk
 */
public class Vip {
	/**
	 * 默认的VIP语言数据键 <b>{Player}, {Money}, {Vip}</b>
	 */
	public static final String[] Key = { "{Player}", "{Money}", "{Vip}" };
	private Activate ac;
	private File file;
	/**
	 * 特权ID
	 */
	private String ID;
	/**
	 * 特权名称
	 */
	private String Name;
	/**
	 * 是否允许飞行
	 */
	private boolean Flight;
	/**
	 * 特权最低等级
	 */
	private int MinLevel;
	/**
	 * 特权最高等级
	 */
	private int MaxLevel;
	/**
	 * 升级算法
	 */
	private LevelAlg alg;
	/**
	 * 药水效果列表
	 */
	private List<Effect> effects;
	/**
	 * 能用游戏币购买的次数，为零时不显示，小于零时无法使用游戏币购买
	 */
	private int BuyCount;
	/**
	 * 签到能获得的经验值
	 */
	private int SignExp;
	/**
	 * 玩家能更换的游戏模式
	 */
	private List<Integer> Gamemode;
	/**
	 * 是否允许普通TP
	 */
	private boolean SimpleTP;
	/**
	 * 是否允许强制TP
	 */
	private boolean TP;
	/**
	 * 每次签到能获得的金币
	 */
	private double SignMoney;
	/**
	 * 签到给钱的货币
	 */
	private MyEconomy SignEconomy;
	/**
	 * 每次签到会获得的物品
	 */
	private List<Item> SignItem;
	/**
	 * 否允许使用云端仓库
	 */
	private boolean CloudStorage;
	/**
	 * 是否允许挖掘时随机增加掉落物
	 */
	private boolean ExcavateIncrease;
	/**
	 * 掉落物增幅倍率，最高为10（100%）小于等于零时不开启
	 */
	private int Increases;
	/**
	 * 进服是否全服公报
	 */
	private boolean JoinTip;
	/**
	 * 进服全服公报内容
	 */
	private String JoinMsg;
	/**
	 * 进服公报类型，包含Msg，Tip，Title，Popup
	 */
	private JoinMsg JoinMsgType;
	/**
	 * 玩家进服是否播放音乐
	 */
	private boolean JoinSound;
	/**
	 * 玩家进服播放音乐的名称，具体名称参考：Nukkit
	 */
	private String JoinSoundName;
	/**
	 * 是否为玩家生成粒子跟随
	 */
	private boolean isParticle;
	/**
	 * 粒子类型，包含：LAVA、WATER
	 */
	private Particle ParticleType;
	/**
	 * 是否开启传送音效
	 */
	private boolean TPSound;
	/**
	 * 传送音效名称，具体参考Nukkit
	 */
	private String TPSoundName;

	public Vip(Activate activate, File file) throws Exception {
		this.file = file;
		ac = activate;
		load();
	}

	protected void load() throws Exception {
		Config config = new Config(file, Config.YAML);
		List<?> list;
		String s;
		ID = config.getString("ID");
		if (ID == null || ID.isEmpty())
			throw new VipException("Unable to read privilege ID!");
		ID = ID.toLowerCase();
		Name = config.getString("name");
		if (Name == null || Name.isEmpty())
			throw new VipException("Unable to read privilege Name!");
		MinLevel = config.getInt("MinLevel");
		MinLevel = MinLevel <= 0 ? 0 : MinLevel;
		MaxLevel = config.getInt("MaxLevel");
		MaxLevel = MaxLevel <= 1 ? 1 : MaxLevel;
		MaxLevel = MaxLevel <= MinLevel ? MinLevel + 9 : MaxLevel;
		alg = LevelAlg.getAlg(config.getString("LevelAlg"));
		Flight = config.getBoolean("Flight");
		list = config.getList("Effect");
		effects = new ArrayList<>();
		Effect effect;
		if (list != null && list.size() >= 1)
			for (Object obj : list)
				if (Tool.isInteger(obj)) {
					effect = Effect.getEffect(Tool.ObjToInt(obj, 1));
					effect.setColor(Tool.getRand(0, 255), Tool.getRand(0, 255), Tool.getRand(0, 255));
					if (!effects.contains(effect))
						effects.add(effect);
				}
		BuyCount = config.getInt("BuyCount");
		SignExp = config.getInt("SignExp");
		SignMoney = config.getDouble("SignMoney");
		SignEconomy = ac.getEconomyManage().getEconomy(config.getString("SignEconomy"));
		Object object = config.get("SignItem");
		SignItem = new ArrayList<>();
		Map<?, ?> map = object != null && object instanceof Map ? (HashMap<?, ?>) object : new HashMap<>();
		Map<String, ?> map2;
		for (Object obj : map.values())
			try {
				map2 = obj != null && obj instanceof Map ? (HashMap<String, ?>) obj : new HashMap<>();
				Item item = new Item(Tool.ObjToInt(map2.get("ID")), Tool.ObjToInt(map2.get("Damage")),
						Tool.ObjToInt(map2.get("Count")), String.valueOf(map2.get("Name")));
				item.setCompoundTag((byte[]) map2.get("Nbt"));
				SignItem.add(item);
			} catch (Exception e) {
				e.printStackTrace();
			}
		list = config.getList("gamemode");
		Gamemode = new ArrayList<>();
		int i = 0;
		if (list != null && list.size() >= 1)
			for (Object obj : list) {
				i = Tool.ObjToInt(obj);
				if (i > 0 && i <= 3)
					Gamemode.add(i);
			}
		SimpleTP = config.getBoolean("SimpleTP");
		TP = config.getBoolean("TP");
		TPSound = config.getBoolean("TPSound");
		TPSoundName = config.getString("TPSoundName");
		CloudStorage = config.getBoolean("CloudStorage");
		ExcavateIncrease = config.getBoolean("ExcavateIncrease");
		Increases = config.getInt("Increases");
		Increases = Increases <= 0 ? 0 : Increases >= 10 ? 10 : Increases;
		ExcavateIncrease = Increases > 0 && ExcavateIncrease;
		JoinTip = config.getBoolean("JoinTip");
		JoinMsg = config.getString("JoinMsg");
		JoinTip = JoinTip && JoinMsg != null && !JoinMsg.isEmpty();
		s = config.getString("JoinMsgType");
		s = s == null ? "Msg" : s;
		switch (s.toLowerCase()) {
		case "popup":
			JoinMsgType = new Popup();
			break;
		case "title":
			JoinMsgType = new Title();
			break;
		case "tip":
			JoinMsgType = new Tip();
		case "msg":
		default:
			JoinMsgType = new Message();
			break;
		}
		JoinSound = config.getBoolean("JoinSound");
		JoinSoundName = config.getString("JoinSoundName");
		isParticle = config.getBoolean("Particle");
		ParticleType = MyParticle.Unknown(config.get("ParticleType"));
	}

	/**
	 * 玩家进服播放音乐的名称，具体名称参考：Nukkit
	 *
	 * @return
	 */
	public String getJoinSoundName() {
		return JoinSoundName;
	}

	/**
	 * 发送进服音效
	 *
	 * @param player
	 * @return
	 */
	public boolean sendJoinSoundName(Player player) {
		if (isJoinSound())
			sendSound(player, JoinSoundName);
		return true;
	}

	/**
	 * 粒子类型，包含：LAVA、WATER
	 *
	 * @return
	 */
	public Particle getParticleType() {
		return ParticleType;
	}

	/**
	 * 传送音效名称，具体参考Nukkit
	 *
	 * @return
	 */
	public String getTPSoundName() {
		return TPSoundName;
	}

	/**
	 * 播放传送音效
	 *
	 * @param player
	 * @return
	 */
	public boolean sendTPSoundName(Player player) {
		if (isTPSound())
			sendSound(player, TPSoundName);
		return true;
	}

	/**
	 * 播放音效
	 *
	 * @param player
	 * @param name
	 * @return
	 */
	public boolean sendSound(Player player, String name) {
		float volume = 1;
		float pitch = 1;
		Preconditions.checkArgument(volume >= 0 && volume <= 1, "Sound volume must be between 0 and 1");
		Preconditions.checkArgument(pitch >= 0, "Sound pitch must be higher than 0");
		PlaySoundPacket packet = new PlaySoundPacket();
		packet.name = name;
		packet.volume = volume;
		packet.pitch = pitch;
		packet.x = player.getFloorX();
		packet.y = player.getFloorY();
		packet.z = player.getFloorZ();
		player.getLevel().addChunkPacket(player.getFloorX() >> 4, player.getFloorZ() >> 4, packet);
		return true;
	}

	/**
	 * 玩家进服是否播放音乐
	 *
	 * @return
	 */
	public boolean isJoinSound() {
		return JoinSound;
	}

	/**
	 * 是否为玩家生成粒子跟随
	 *
	 * @return
	 */
	public boolean isParticle() {
		return isParticle;
	}

	/**
	 * 是否开启传送音效
	 *
	 * @return
	 */
	public boolean isTPSound() {
		return TPSound;
	}

	/**
	 * 进服是否全服公报
	 *
	 * @return
	 */
	public boolean isJoinTip() {
		return JoinTip && getJoinMsg() != null && getJoinMsgType() != null;
	}

	/**
	 * 进服公报类型，包含Msg，Tip，Title，Popup
	 *
	 * @return
	 */
	public JoinMsg getJoinMsgType() {
		return JoinTip ? JoinMsgType == null ? null : JoinMsgType : null;
	}

	/**
	 * 进服全服公报内容
	 *
	 * @return
	 */
	public String getJoinMsg() {
		return JoinTip ? JoinMsg == null || JoinMsg.isEmpty() ? null : JoinMsg : null;
	}

	/**
	 * 是否允许挖掘时随机增加掉落物
	 *
	 * @return
	 */
	public boolean isExcavateIncrease() {
		return ExcavateIncrease && Increases >= 0;
	}

	/**
	 * 掉落物增幅倍率，最高为10（100%）小于等于零时不开启
	 *
	 * @return
	 */
	public int getIncreases() {
		return Increases;
	}

	/**
	 * 否允许使用云端仓库
	 *
	 * @return
	 */
	public boolean isCloudStorage() {
		return CloudStorage;
	}

	/**
	 * 每次签到会获得的物品
	 *
	 * @return
	 */
	public List<Item> getSignItem() {
		return SignItem;
	}

	/**
	 * 每次签到能获得的金币
	 *
	 * @return
	 */
	public double getSignMoney() {
		return SignMoney;
	}

	/**
	 * 是否允许普通TP
	 *
	 * @return
	 */
	public boolean isSimpleTP() {
		return SimpleTP || TP;
	}

	/**
	 * 是否允许强制TP
	 *
	 * @return
	 */
	public boolean isTP() {
		return TP;
	}

	/**
	 * 玩家是否能更换游戏模式
	 *
	 * @return
	 */
	public boolean isGamemode() {
		return Gamemode.size() > 1 || Gamemode.size() == 1 && Gamemode.get(0) != 0;
	}

	/**
	 * 玩家能更换的游戏模式
	 *
	 * @return
	 */
	public List<Integer> getGamemode() {
		return Gamemode;
	}

	/**
	 * 能否签到
	 *
	 * @return
	 */
	public boolean isSign() {
		return SignExp <= 0;
	}

	/**
	 * 签到能获得的经验值
	 *
	 * @return
	 */
	public int getSignExp() {
		return SignExp;
	}
	/**
	 * 药水效果列表
	 *
	 * @return
	 */
	public List<Effect> getEffects() {
		return effects;
	}

	/**
	 * 特权ID
	 *
	 * @return
	 */
	public String getID() {
		return ID;
	}

	/**
	 * 特权最低等级
	 *
	 * @return
	 */
	public int getMinLevel() {
		return MinLevel;
	}

	/**
	 * 升级算法
	 *
	 * @return
	 */
	public LevelAlg getAlg() {
		return alg;
	}

	/**
	 * 特权名称
	 *
	 * @return
	 */
	public String getName() {
		return Name;
	}

	/**
	 * 是否允许飞行
	 *
	 * @return
	 */
	public boolean isFlight() {
		return Flight;
	}

	/**
	 * 特权最高等级
	 *
	 * @return
	 */
	public int getMaxLevel() {
		return MaxLevel;
	}

	/**
	 * 能用游戏币购买的次数，为零时不显示，小于零时无法使用游戏币购买
	 *
	 * @return
	 */
	public int getBuyCount() {
		return BuyCount;
	}

	/**
	 * 签到给钱的货币
	 *
	 * @return
	 */
	public MyEconomy getSignEconomy() {
		return SignEconomy;
	}
}
