package cn.winfxk.brassiere.cmd;

import cn.nukkit.Player;
import cn.nukkit.command.Command;
import cn.nukkit.command.CommandSender;
import cn.winfxk.brassiere.Activate;
import cn.winfxk.brassiere.Message;
import cn.winfxk.brassiere.MyPlayer;

/**
 * @author Winfxk
 */
public class TeamCommand extends Command {
	private Activate ac;
	private Message msg;

	public TeamCommand(Activate ac) {
		super("team", ac.getMessage().getSon("Team-Command", "Usage"), "/team help",
				ac.getCommands("PlayerCommand", "Team"));
		this.ac = ac;
		msg = ac.getMessage();
	}

	@Override
	public boolean execute(CommandSender sender, String commandLabel, String[] args) {
		if (!ac.getPluginBase().isEnabled())
			return true;
		if (!sender.isPlayer())
			return false;
		Player player = (Player) sender;
		MyPlayer myPlayer = ac.getPlayers(player);
		if (args == null || args.length <= 0)
			return ac.getTeamMag().makeMain(player);
		return false;
	}
}
