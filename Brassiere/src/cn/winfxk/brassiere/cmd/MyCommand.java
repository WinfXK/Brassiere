package cn.winfxk.brassiere.cmd;

import cn.nukkit.command.Command;
import cn.nukkit.command.CommandSender;
import cn.winfxk.brassiere.Activate;
import cn.winfxk.brassiere.Message;
import cn.winfxk.brassiere.MyPlayer;
import cn.winfxk.brassiere.form.FormMain;

/**
 * 插件主命令
 * 
 * @Createdate 2020/05/05 23:08:25
 * @author Winfxk
 */
public class MyCommand extends Command {
	private Message msg = Activate.getActivate().getMessage();;
	private Activate ac;

	public MyCommand(Activate ac) {
		super(ac.getPluginBase().getName(), "/" + ac.getPluginBase().getName(),
				ac.getMessage().getSun("Command", "Main", "Main"), ac.getCommands("Command", "Main"));
		this.ac = ac;
	}

	@Override
	public boolean execute(CommandSender sender, String commandLabel, String[] args) {
		if (!sender.hasPermission("Brassiere.Command.main")) {
			sender.sendMessage(msg.getMessage("权限不足", new String[] { "{Player}", "{Money}" },
					new Object[] { sender.getName(), MyPlayer.getMoney(sender.getName()) }));
			return true;
		}
		if (!sender.isPlayer()) {
			sender.sendMessage(msg.getMessage("NotPlayer", new String[] { "{Player}", "{Money}" },
					new Object[] { sender.getName(), MyPlayer.getMoney(sender.getName()) }));
			return true;
		}
		MyPlayer myPlayer = ac.getPlayers(sender.getName());
		myPlayer.form = new FormMain(myPlayer.getPlayer(), null);
		return myPlayer.form.MakeMain();
	}
}
