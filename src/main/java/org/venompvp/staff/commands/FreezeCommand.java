package org.venompvp.staff.commands;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.venompvp.staff.Staff;
import org.venompvp.staff.enums.Messages;
import org.venompvp.venom.commands.Command;
import org.venompvp.venom.commands.ParentCommand;
import org.venompvp.venom.commands.arguments.Argument;
import org.venompvp.venom.commands.arguments.PlayerArgument;
import org.venompvp.venom.module.Module;

import java.util.Collections;
import java.util.List;

public class FreezeCommand extends Command implements ParentCommand {

    public FreezeCommand(Module module) {
        super(module,
                "freeze",
                "freeze players",
                Collections.singletonList(PlayerArgument.class),
                "venom.freeze",
                false,
                "ss",
                "screenshare");
    }

    @Override
    public void execute(CommandSender sender, List<Argument> args, String label) {
        if (args.size() < 1 || args.size() > 1) {
            sender.sendMessage(getUsage(label));
        } else {
            Player target = (Player) args.get(0).getValue();
            if (target.isOp() && !sender.isOp()) {
                sender.sendMessage(Messages.NO_PERMISSION.toString());
                return;
            }
            if (sender instanceof Player && target.getUniqueId().toString().equals(((Player) sender).getUniqueId().toString())) {
                sender.sendMessage(Messages.CANT_FREEZE_YOURSELF.toString());
                return;
            }

            if (Staff.getInstance().getFrozenPlayers().contains(target.getUniqueId())) {
                Staff.getInstance().unfreeze(target);
                sender.sendMessage(Messages.YOU_UNFROZE.toString().replace("{player}", target.getName()));
            } else {
                Staff.getInstance().freeze(target);
                sender.sendMessage(Messages.YOU_FROZE.toString().replace("{player}", target.getName()));
            }
        }
    }

    @Override
    public String getUsage(String label) {
        return "/" + label + " [player]";
    }

    @Override
    public void setupSubCommands() {

    }
}
