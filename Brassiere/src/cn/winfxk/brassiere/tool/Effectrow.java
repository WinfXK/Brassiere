package cn.winfxk.brassiere.tool;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cn.nukkit.potion.Effect;

/**
 * @author Winfxk
 */
public enum Effectrow {
	/**
	 * 速度
	 */
	Speed(1, "速度", "speed_effect.png"),
	/**
	 * 缓慢
	 */
	Slowness(2, "缓慢", "slowness_effect.png"),
	/**
	 * 急迫
	 */
	Haste(3, "急迫", "haste_effect.png"),
	/**
	 * 挖掘疲劳
	 */
	Mining_Fatigue(4, "挖掘疲劳", "mining_fatigue_effect.png"),
	/**
	 * 力量
	 */
	Strength(5, "力量", "strength_effect.png"),
	/**
	 * 瞬间资料
	 */
	Instant_Health(6, " 瞬间资料", "health_boost_effect.png"),
	/**
	 * 瞬间伤害
	 */
	Instant_Damage(7, " 瞬间伤害", "strength_effect.png"),
	/**
	 * 跳跃提升
	 */
	Jump_Boost(8, "跳跃提升", "jump_boost_effect.png"),
	/**
	 * 反胃
	 */
	Nausea(9, "反胃", "nausea_effect.png"),
	/**
	 * 生命恢复
	 */
	Regeneration(10, "生命恢复", "regeneration_effect.png"),
	/**
	 * 抗性提升
	 */
	Resistance(11, "抗性提升", "resistance_effect.png"),
	/**
	 * 防火
	 */
	Fire_Resistance(12, "防火", "fire_resistance_effect.png"),
	/**
	 * 水下呼吸
	 */
	Water_Breathing(13, "水下呼吸", "water_breathing_effect.png"),
	/**
	 * 隐身
	 */
	Invisibility(14, "隐身", "invisibility_effect.png"),
	/**
	 * 失明
	 */
	Blindness(15, "失明", "blindness_effect.png"),
	/**
	 * 夜视
	 */
	Night_Vision(16, "夜视", "night_vision_effect.png"),
	/**
	 * 饥饿
	 */
	Hunger(17, "饥饿", "hunger_effect.png"),
	/**
	 * 虚弱
	 */
	Weakness(18, " 虚弱", "weakness_effect.png"),
	/**
	 * 中毒
	 */
	Poison(19, "中毒", "poison_effect.png"),
	/**
	 * 凋零
	 */
	Wither(20, "凋零", "wither_effect.png"),
	/**
	 * 生命提升
	 */
	Health_Boost(21, "生命提升", "health_boost_effect.png"),
	/**
	 * 伤害吸收
	 */
	Absorption(22, "伤害吸收", "absorption_effect.png"),
	/**
	 * 饱和
	 */
	Saturatio(23, "饱和", "water_breathing_effect.png"),
	/**
	 * 漂浮
	 */
	Levitation(24, "漂浮", "levitation_effect.png"),
	/**
	 * 中毒
	 */
	FATAL_POISON(25, "剧毒", "poison_effect.png"),
	/**
	 * 导管能量
	 */
	COUNDIT_POWER(26, "导管能量", "conduit_power_effect.png"),
	/**
	 * 缓慢下降
	 */
	SLOW_FALLING(27, "缓慢下降", "slow_falling_effect.png");

	private int ID;
	private String Name;
	private String Path;
	private static Map<String, Effectrow> NameKey = new HashMap<>();
	private static Map<Integer, Effectrow> IDKey = new HashMap<>();
	private static List<Effectrow> list = new ArrayList<>();
	static {
		for (Effectrow item : Effectrow.values()) {
			list.add(item);
			IDKey.put(item.ID, item);
			NameKey.put(item.Name, item);
		}
	}

	/**
	 * 获取项目ID
	 * 
	 * @return
	 */
	public int getID() {
		return ID;
	}

	/**
	 * 获取项目名称
	 * 
	 * @return
	 */
	public String getName() {
		return Name;
	}

	/**
	 * 获取已效果名称为Key的列表
	 * 
	 * @return
	 */
	public static Map<String, Effectrow> getNameKey() {
		return NameKey;
	}

	/**
	 * 获取项目列表
	 * 
	 * @return
	 */
	public static List<Effectrow> getList() {
		return list;
	}

	/**
	 * 获取已效果ID为Key的列表
	 */
	public static Map<Integer, Effectrow> getIDKey() {
		return IDKey;
	}

	/**
	 * 获取项目贴图路径
	 * 
	 * @return
	 */
	public String getPath() {
		return Path;
	}

	/**
	 * 
	 * @param ID   效果ID
	 * @param Name 效果名称
	 * @param Path 效果贴图
	 */
	private Effectrow(int ID, String Name, String Path) {
		this.ID = ID;
		this.Name = Name;
		this.Path = "textures/ui/" + Path;
	}

	/**
	 * 获取药水效果
	 * 
	 * @param item
	 * @return
	 */
	public static Effect getEffect(Effectrow item) {
		return getEffect(item.getID());
	}

	/**
	 * 获取药水效果
	 * 
	 * @param Name
	 * @return
	 */
	public static Effect getEffect(String Name) {
		if (NameKey.containsKey(Name))
			return getEffect(NameKey.get(Name));
		return null;
	}

	/**
	 * 判断是否存在一个药水效果
	 * 
	 * @param obj 药水效果的名称或者ID
	 * @return
	 */
	public static boolean isEffect(Object obj) {
		if (Tool.isInteger(obj)) {
			if (IDKey.containsKey(obj))
				return true;
		}
		return NameKey.containsKey(obj);
	}

	/**
	 * 获取药水效果
	 * 
	 * @param ID
	 * @return
	 */
	public static Effect getEffect(int ID) {
		return Effect.getEffect(ID);
	}

	/**
	 * 获取药水效果的名称
	 * 
	 * @param effect
	 * @param Default
	 * @return
	 */
	public static String getName(Effect effect, Object Default) {
		return getName(effect.getDuration(), Default);
	}

	/**
	 * 获取药水效果的名称
	 * 
	 * @param effect
	 * @return
	 */
	public static String getName(Effect effect) {
		return getName(effect.getId());
	}

	/**
	 * 获取药水效果的名称
	 * 
	 * @param ID
	 * @return
	 */
	public static String getName(int ID) {
		return IDKey.containsKey(ID) ? IDKey.get(ID).Name : null;
	}

	/**
	 * 获取药水效果的名称
	 * 
	 * @param ID
	 * @return
	 */
	public static String getName(int ID, Object Default) {
		return IDKey.containsKey(ID) ? IDKey.get(ID).Name : Tool.objToString(Default);
	}

	/**
	 * 获取药水效果的ID
	 * 
	 * @param Name
	 * @return
	 */
	public static int getID(String Name) {
		return Name == null || Name.isEmpty() ? -1 : NameKey.containsKey(Name) ? NameKey.get(Name).ID : -1;
	}

	/**
	 * 获取药水效果的ID
	 * 
	 * @param Name
	 * @param Default
	 * @return
	 */
	public static int getID(String Name, int Default) {
		return Name == null || Name.isEmpty() ? Default : NameKey.containsKey(Name) ? NameKey.get(Name).ID : Default;
	}

	/**
	 * 获取药水效果的ID
	 * 
	 * @param effect
	 * @return
	 */
	public static int getID(Effect effect) {
		return getID(getName(effect));
	}

	/**
	 * 获取药水效果的ID
	 * 
	 * @param effect
	 * @param Default
	 * @return
	 */
	public static int getID(Effect effect, int Default) {
		return getID(getName(effect), Default);
	}

	/**
	 * 获取药水贴图
	 * 
	 * @param Name
	 * @return
	 */
	public static String getPath(String Name) {
		return Name == null || Name.isEmpty() ? null : NameKey.containsKey(Name) ? NameKey.get(Name).Path : null;
	}

	/**
	 * 获取药水贴图
	 * 
	 * @param Name
	 * @return
	 */
	public static String getPath(String Name, Object Default) {
		return Name == null || Name.isEmpty() ? Tool.objToString(Default)
				: NameKey.containsKey(Name) ? NameKey.get(Name).Path : Tool.objToString(Default);
	}

	/**
	 * 获取药水贴图
	 * 
	 * @param ID
	 * @return
	 */
	public static String getPath(int ID) {
		return IDKey.containsKey(ID) ? IDKey.get(ID).Path : null;
	}

	/**
	 * 获取药水贴图
	 * 
	 * @param ID
	 * @return
	 */
	public static String getPath(int ID, Object Default) {
		return IDKey.containsKey(ID) ? IDKey.get(ID).Path : Tool.objToString(Default);
	}

	/**
	 * 获取药水贴图
	 * 
	 * @param effect
	 * @return
	 */
	public static String getPath(Effect effect) {
		return getPath(effect.getId());
	}

	/**
	 * 获取药水贴图
	 * 
	 * @param effect
	 * @param Default
	 * @return
	 */
	public static String getPath(Effect effect, Object Default) {
		return getPath(effect.getId(), Default);
	}
}
