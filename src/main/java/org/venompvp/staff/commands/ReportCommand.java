package org.venompvp.staff.commands;

import com.google.common.base.Joiner;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.venompvp.staff.Staff;
import org.venompvp.staff.enums.Messages;
import org.venompvp.staff.objs.StaffPlayer;
import org.venompvp.venom.commands.Command;
import org.venompvp.venom.commands.ParentCommand;
import org.venompvp.venom.commands.arguments.Argument;
import org.venompvp.venom.commands.arguments.StringArrayArgument;
import org.venompvp.venom.module.Module;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;
import java.util.function.Consumer;
import java.util.stream.Collectors;


public class ReportCommand extends Command implements ParentCommand {

    public ReportCommand(Module module) {
        super(module, "report", "report a player or bug", Collections.singletonList(StringArrayArgument.class), "venom.report", true);
    }

    @Override
    public void execute(CommandSender sender, List<Argument> args, String label) {
        String[] arguments = (String[]) args.get(0).getValue();
        String joined = Joiner.on(" ").skipNulls().join(arguments);

        HashMap<UUID, StaffPlayer> staffPlayers = Staff.getInstance().getStaffPlayers();
        if (staffPlayers.isEmpty()) {
            sender.sendMessage(Messages.NO_STAFF_ONLINE.toString());
        } else {
            final List<String> message = getModule().getConfig().getStringList("report-message").stream().map(s -> ChatColor.translateAlternateColorCodes('&', s.replace("{player}", sender.getName()).replace("{message}", joined))).collect(Collectors.toList());
            staffPlayers.keySet().stream().<Consumer<? super String>>map(uuid -> s -> Bukkit.getPlayer(uuid).sendMessage(s)).forEach(message::forEach);
        }
    }

    @Override
    public String getUsage(String label) {
        return "/" + label + " [reason]";
    }

    @Override
    public void setupSubCommands() {

    }
}
