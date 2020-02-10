package cn.winfxk.brassiere.tool;

import java.util.ArrayList;
import java.util.List;

import cn.nukkit.level.particle.GenericParticle;
import cn.nukkit.level.particle.LavaParticle;
import cn.nukkit.level.particle.Particle;
import cn.nukkit.math.Vector3;

/**
 * @author Winfxk
 */
public enum MyParticle {
	/**
	 * 气泡粒子
	 */
	TYPE_BUBBLE(1, "TYPE_BUBBLE"),
	/**
	 *
	 */
	TYPE_CRITICAL(3, "TYPE_CRITICAL"),
	/**
	 *
	 */
	TYPE_BLOCK_FORCE_FIELD(4, "TYPE_BLOCK_FORCE_FIELD"),
	/**
	 * 烟雾粒子
	 */
	TYPE_SMOKE(5, "TYPE_SMOKE"),
	/**
	 * 爆炸粒子
	 */
	TYPE_EXPLODE(6, "TYPE_EXPLODE"),
	/**
	 * 蒸汽粒子
	 */
	TYPE_EVAPORATION(7, "TYPE_EVAPORATION"),
	/**
	 * 火焰粒子
	 */
	TYPE_FLAME(8, "TYPE_FLAME"),
	/**
	 * 岩浆粒子
	 */
	TYPE_LAVA(9, "TYPE_LAVA"),
	/**
	 * 浓烟
	 */
	TYPE_LARGE_SMOKE(10, "TYPE_LARGE_SMOKE"),
	/**
	 * 红石粒子
	 */
	TYPE_REDSTONE(11, "TYPE_REDSTONE"),
	/**
	 * 红色尘埃上升
	 */
	TYPE_RISING_RED_DUST(12, "TYPE_RISING_RED_DUST"),
	/**
	 * 破坏物品
	 */
	TYPE_ITEM_BREAK(13, "TYPE_ITEM_BREAK"),
	/**
	 * 雪球碎片
	 */
	TYPE_SNOWBALL_POOF(14, "TYPE_SNOWBALL_POOF"),
	/**
	 * 巨大的爆炸粒子
	 */
	TYPE_HUGE_EXPLODE(15, "TYPE_HUGE_EXPLODE"),
	/**
	 *
	 */
	TYPE_HUGE_EXPLODE_SEED(16, "TYPE_HUGE_EXPLODE_SEED"),
	/**
	 * 生气冒泡效果
	 */
	TYPE_MOB_FLAME(17, "TYPE_MOB_FLAME"),
	/**
	 * 心脏粒子
	 */
	TYPE_HEART(18, "TYPE_HEART"),
	/**
	 * 跑动地形粒子
	 */
	TYPE_TERRAIN(19, "TYPE_TERRAIN"),
	/**
	 *
	 */
	TYPE_SUSPENDED_TOWN(20, "TYPE_SUSPENDED_TOWN"),
	/**
	 * 门粒子
	 */
	TYPE_PORTAL(21, "TYPE_PORTAL"), TYPE_SPLASH(23, "TYPE_SPLASH"), TYPE_WATER_WAKE(25, "TYPE_WATER_WAKE"),
	TYPE_DRIP_WATER(26, "TYPE_DRIP_WATER"), TYPE_DRIP_LAVA(27, "TYPE_DRIP_LAVA"),
	TYPE_DRIP_HONEY(28, "TYPE_DRIP_HONEY"), TYPE_FALLING_DUST(29, "TYPE_FALLING_DUST"),
	TYPE_MOB_SPELL(30, "TYPE_MOB_SPELL"), TYPE_MOB_SPELL_AMBIENT(31, "TYPE_MOB_SPELL_AMBIENT"),
	TYPE_MOB_SPELL_INSTANTANEOUS(32, "TYPE_MOB_SPELL_INSTANTANEOUS"), TYPE_NOTE_AND_DUST(33, "TYPE_NOTE_AND_DUST"),
	TYPE_SLIME(34, "TYPE_SLIME"), TYPE_RAIN_SPLASH(35, "TYPE_RAIN_SPLASH"),
	TYPE_VILLAGER_ANGRY(36, "TYPE_VILLAGER_ANGRY"), TYPE_VILLAGER_HAPPY(37, "TYPE_VILLAGER_HAPPY"),
	TYPE_ENCHANTMENT_TABLE(38, "TYPE_ENCHANTMENT_TABLE"), TYPE_TRACKING_EMITTER(39, "TYPE_TRACKING_EMITTER"),
	TYPE_NOTE(40, "TYPE_NOTE"), TYPE_WITCH_SPELL(41, "TYPE_WITCH_SPELL"), TYPE_CARROT(42, "TYPE_CARROT"),
	TYPE_END_ROD(44, "TYPE_END_ROD"), TYPE_RISING_DRAGONS_BREATH(45, "TYPE_RISING_DRAGONS_BREATH"),
	TYPE_SPIT(46, "TYPE_SPIT"), TYPE_TOTEM(47, "TYPE_TOTEM"), TYPE_FOOD(48, "TYPE_FOOD"),
	TYPE_FIREWORKS_STARTER(49, "TYPE_FIREWORKS_STARTER"), TYPE_FIREWORKS_SPARK(50, "TYPE_FIREWORKS_SPARK"),
	TYPE_FIREWORKS_OVERLAY(51, "TYPE_FIREWORKS_OVERLAY"), TYPE_BALLOON_GAS(52, "TYPE_BALLOON_GAS"),
	TYPE_COLORED_FLAME(53, "TYPE_COLORED_FLAME"), TYPE_SPARKLER(54, "TYPE_SPARKLER"), TYPE_CONDUIT(55, "TYPE_CONDUIT"),
	TYPE_BUBBLE_COLUMN_UP(56, "TYPE_BUBBLE_COLUMN_UP"), TYPE_BUBBLE_COLUMN_DOWN(57, "TYPE_BUBBLE_COLUMN_DOWN"),
	TYPE_SNEEZE(58, "TYPE_SNEEZE"), TYPE_LARGE_EXPLOSION(61, "TYPE_LARGE_EXPLOSION"), TYPE_INK(62, "TYPE_INK"),
	TYPE_FALLING_RED_DUST(63, "TYPE_FALLING_RED_DUST"), TYPE_CAMPFIRE_SMOKE(64, "TYPE_CAMPFIRE_SMOKE"),
	TYPE_FALLING_DRAGONS_BREATH(66, "TYPE_FALLING_DRAGONS_BREATH"), TYPE_DRAGONS_BREATH(67, "TYPE_DRAGONS_BREATH");
	private String name;
	private int ID;
	public static List<MyParticle> list = new ArrayList<>();
	static {
		for (MyParticle item : values())
			list.add(item);
	}

	private MyParticle(int ID, String name) {
		this.ID = ID;
		this.name = name;
	}

	/**
	 * 得到效果ID
	 *
	 * @return
	 */
	public int getID() {
		return ID;
	}

	/**
	 * 得到效果名称
	 *
	 * @return
	 */
	public String getName() {
		return name;
	}

	/**
	 * 得到粒子效果
	 *
	 * @return
	 */
	public Particle getParticle() {
		return new GenericParticle(new Vector3(), ID);
	}

	/**
	 * 得到粒子效果
	 *
	 * @param vector3
	 * @return
	 */
	public Particle getParticle(Vector3 vector3) {
		return new GenericParticle(vector3, ID);
	}

	/**
	 * 得到所有的粒子效果
	 *
	 * @return
	 */
	public static List<MyParticle> getList() {
		return list;
	}

	/**
	 * 根据未知数据获取一个粒子效果
	 *
	 * @param object
	 * @return
	 */
	public static Particle Unknown(Object object) {
		return Unknown(object, new Vector3());
	}

	/**
	 * 根据未知数据获取一个粒子效果
	 *
	 * @param object   ID||Name
	 * @param particle 若获取失败将会获得的粒子效果
	 * @return
	 */
	public static Particle Unknown(Object object, Particle particle) {
		return Unknown(object, particle, particle);
	}

	/**
	 * 根据未知数据获取一个粒子效果
	 *
	 * @param object  ID||Name
	 * @param vector3 若获取成功粒子效果默认生成的位置
	 * @return
	 */
	public static Particle Unknown(Object object, Vector3 vector3) {
		return Unknown(object, new LavaParticle(vector3), vector3);
	}

	/**
	 * 根据未知数据获取一个粒子效果
	 *
	 * @param object   ID||Name
	 * @param particle 若获取失败将会获得的粒子效果
	 * @param vector3  若获取成功粒子效果默认生成的位置
	 * @return
	 */
	public static Particle Unknown(Object object, Particle particle, Vector3 vector3) {
		if (object == null)
			return particle;
		int ID = -1;
		String name = null;
		if (!Tool.isInteger(object)) {
			name = object.toString();
			if (name == null || name.isEmpty())
				return particle;
		} else
			ID = Tool.ObjectToInt(object, 9);
		if (name == null && ID < 0)
			return particle;
		for (MyParticle particle2 : list)
			if ((ID >= 0 && particle2.getID() == ID)
					|| (name != null && particle2.getName().toLowerCase().equals(name.toLowerCase())))
				return particle2.getParticle(vector3);
		return particle;
	}
}
