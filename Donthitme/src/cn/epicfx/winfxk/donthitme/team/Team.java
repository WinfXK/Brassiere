package cn.epicfx.winfxk.donthitme.team;

import java.io.File;
import java.util.LinkedHashMap;

import cn.epicfx.winfxk.donthitme.Activate;
import cn.epicfx.winfxk.donthitme.MyPlayer;
import cn.nukkit.Player;
import cn.nukkit.utils.Config;

/**
 * @author Winfxk
 */
public class Team {
	private Activate ac;
	private LinkedHashMap<String, TeamConfig> teams;

	public Team(Activate activate) {
		ac = activate;
		teams = new LinkedHashMap<>();
	}

	public boolean makeMain(Player player) {
		MyPlayer myPlayer = ac.getPlayers(player.getName());
		myPlayer.makeBase = new TeamForm(player);
		return myPlayer.makeBase.MakeMain();
	}

	public boolean isTeam(String ID) {
		return teams.containsKey(ID);
	}

	public TeamConfig getTeam(String ID) {
		return teams.get(ID);
	}

	public LinkedHashMap<String, TeamConfig> getTeams() {
		return new LinkedHashMap<>(teams);
	}

	public Team reload() {
		return this;
	}

	public Config getConfig(String ID) {
		return new Config(getTeamFile(ID), Config.YAML);
	}

	public File getTeamFile(String ID) {
		return new File(new File(ac.getPluginBase().getDataFolder(), Activate.TeamDirName), ID + ".yml");
	}
}
