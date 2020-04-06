package cn.winfxk.brassiere.cmd;

import cn.winfxk.brassiere.Activate;

import cn.nukkit.command.Command;
import cn.nukkit.command.CommandSender;

/**
 *
 * 2020年3月22日 上午1:45:59
 *
 * @author Winfxk
 */
public class AdminCommand extends Command {
	private Activate ac;

	/**
	 * 未完成：{@linkplain cn.winfxk.brassiere.LordMang LordMang}
	 *
	 * @param name
	 */
	public AdminCommand(Activate activate) {
		super("");
		ac = activate;
	}

	@Override
	public boolean execute(CommandSender sender, String commandLabel, String[] args) {
		return false;
	}
}
