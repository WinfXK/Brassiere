package cn.winfxk.brassiere.team;

import java.util.Map;

import cn.winfxk.brassiere.tool.Tool;

/**
 * 2020年3月3日 上午10:20:15
 *
 * @author Winfxk
 */
public class TPlayerdata {
	private String name;
	private int Prestige;
	private String identity;
	private Team team;
	private String JoinDate;

	/**
	 * 玩家在队伍里面的数据
	 *
	 * @param map
	 * @param team
	 */
	public TPlayerdata(Map<String, Object> map, Team team) {
		this.team = team;
		name = Tool.objToString(map.get("name"));
		Prestige = team.getPrestige(name);
		identity = Tool.objToString(map.get("identity"));
		JoinDate = Tool.objToString(map.get("JoinDate"));
	}

	/**
	 * 获取玩家入队时间
	 *
	 * @return
	 */
	public String getJoinDate() {
		return JoinDate;
	}

	/**
	 * 获取玩家所在的队伍
	 *
	 * @return
	 */
	public Team getTeam() {
		return team;
	}

	/**
	 * 获取玩家的队伍身份
	 *
	 * @return
	 */
	public String getIdentity() {
		return identity;
	}

	/**
	 * 获取玩家的名称
	 *
	 * @return
	 */
	public String getName() {
		return name;
	}

	/**
	 * 获取玩家的声望点
	 *
	 * @return
	 */
	public int getPrestige() {
		return Prestige;
	}

}
