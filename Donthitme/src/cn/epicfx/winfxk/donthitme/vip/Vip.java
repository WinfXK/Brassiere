package cn.epicfx.winfxk.donthitme.vip;

import java.io.File;
import java.util.List;
import java.util.Map;

import org.yaml.snakeyaml.DumperOptions;
import org.yaml.snakeyaml.Yaml;

import cn.epicfx.winfxk.donthitme.Activate;
import cn.epicfx.winfxk.donthitme.money.MyEconomy;
import cn.epicfx.winfxk.donthitme.vip.alg.LevelAlg;
import cn.nukkit.item.Item;
import cn.nukkit.level.Sound;
import cn.nukkit.level.particle.Particle;
import cn.nukkit.potion.Effect;
import cn.nukkit.utils.Config;
import you.sb.Utils;

/**
 * @author Winfxk
 */
public class Vip {
	public static DumperOptions dumperOptions = null;
	public static Yaml yaml = null;
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
	 * 会否允许使用游戏币购买
	 */
	private boolean EconomyBuy;
	/**
	 * 能用游戏币购买的次数，为零时不显示，小于零时无法使用游戏币购买
	 */
	private int BuyCount;
	/**
	 * 购买时使用的货币
	 */
	private MyEconomy economy;
	/**
	 * 购买价格
	 */
	private double Money;
	/**
	 * 购买时能获得的时长，单位是小时
	 */
	private int BuyTime;
	/**
	 * 新购特权时的等级
	 */
	private int DefaultLevel;
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
	private String JoinMsgType;
	/**
	 * 玩家进服是否播放音乐
	 */
	private boolean JoinSound;
	/**
	 * 玩家进服播放音乐的名称，具体名称参考：Nukkit
	 */
	private Sound JoinSoundName;
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
	private Sound TPSoundName;

	public Vip(Activate activate, File file) throws Exception {
		this.file = file;
		ac = activate;
		if (dumperOptions == null || yaml == null) {
			dumperOptions = new DumperOptions();
			dumperOptions.setDefaultFlowStyle(DumperOptions.FlowStyle.BLOCK);
			yaml = new Yaml(dumperOptions);
		}
		load();
	}

	protected void load() throws Exception {
		Config config = new Config(file, Config.YAML);
		Map<String, Object> map = yaml.loadAs(
				Utils.readFile(getClass()
						.getResourceAsStream("/resources/" + Activate.VIPDirName + "/" + Activate.VipFileName)),
				Map.class);

	}

	/**
	 * 玩家进服播放音乐的名称，具体名称参考：Nukkit
	 *
	 * @return
	 */
	public Sound getJoinSoundName() {
		return JoinSoundName;
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
	public Sound getTPSoundName() {
		return TPSoundName;
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
	public String getJoinMsgType() {
		return JoinTip ? JoinMsgType == null || JoinMsgType.isEmpty() ? "Msg" : JoinMsgType : null;
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
		return Gamemode.size() > 1 || (Gamemode.size() == 1 && Gamemode.get(0) != 0);
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
	 * 新购特权时的等级
	 *
	 * @return
	 */
	public int getDefaultLevel() {
		return DefaultLevel;
	}

	/**
	 * 购买时能获得的时长，单位是小时
	 *
	 * @return
	 */
	public int getBuyTime() {
		return BuyTime;
	}

	/**
	 * 购买价格
	 *
	 * @return
	 */
	public double getMoney() {
		return Money;
	}

	/**
	 * 购买时使用的货币
	 *
	 * @return
	 */
	public MyEconomy getEconomy() {
		return economy;
	}

	/**
	 * 会否允许使用游戏币购买
	 *
	 * @return
	 */
	public boolean isEconomyBuy() {
		return EconomyBuy && BuyCount >= 0;
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
