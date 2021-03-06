package cn.winfxk.brassiere.cmd;

import cn.nukkit.command.Command;
import cn.nukkit.command.CommandSender;
import cn.winfxk.brassiere.Activate;
import cn.winfxk.brassiere.Message;
import cn.winfxk.brassiere.MyPlayer;

/**
 * 插件主命令
 * 
 * @Createdate 2020/05/05 23:08:25
 * @author Winfxk
 */
public class TeamCommand extends Command {
	private Message msg = Activate.getActivate().getMessage();;
	private Activate ac;

	public TeamCommand(Activate ac) {
		super(ac.getPluginBase().getName(), "/" + ac.getPluginBase().getName(),
				ac.getMessage().getSun("Command", "Team", "Main"), ac.getCommands("Command", "Main"));
		this.ac = ac;
	}

	@Override
	public boolean execute(CommandSender sender, String commandLabel, String[] args) {
		if (!sender.hasPermission("Brassiere.Command.Team")) {
			sender.sendMessage(msg.getMessage("权限不足", new String[] { "{Player}", "{Money}" },
					new Object[] { sender.getName(), MyPlayer.getMoney(sender.getName()) }));
			return true;
		}
		if (!sender.isPlayer()) {
			sender.sendMessage(msg.getMessage("NotPlayer", new String[] { "{Player}", "{Money}" },
					new Object[] { sender.getName(), MyPlayer.getMoney(sender.getName()) }));
			return true;
		}
		return ac.getTeamMag().makeMain(ac.getPlayers(sender.getName()).getPlayer());
	}
}
