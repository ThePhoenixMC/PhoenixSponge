package com.lss233.phoenix.sponge.utils.sponge.command;

import com.lss233.phoenix.command.CommandSender;
import org.spongepowered.api.command.CommandSource;
import org.spongepowered.api.text.Text;

public interface CommandSourceTransform {
    default CommandSender toPhoenix(CommandSource src) {
        return new CommandSender() {

            @Override
            public boolean hasPermission(String s) {
                return false;
            }

            @Override
            public void sendMessage(String message) {
                src.sendMessage(Text.of(message));
            }

            @Override
            public void sendMessage(String[] message) {
                for (String s : message) {
                    src.sendMessage(Text.of(s));
                }

            }
        };
    }
}
