package com.lethalflame.bot.Utlis;

import com.google.common.reflect.ClassPath;
import com.lethalflame.bot.bot;

import java.io.IOException;

public class ClassUtlis {

    public static void registerAllCommands() throws IOException {

        ClassPath cp = ClassPath.from(ClassUtlis.class.getClassLoader());
        cp.getTopLevelClassesRecursive("com.lethalflame.bot.commands").forEach(classInfo -> {
            try{
                Class c = Class.forName(classInfo.getName());
                Object obj = c.newInstance();
                if (obj instanceof CommandExecutor) {
                    CommandExecutor commandExecutor = (CommandExecutor) obj;
                    bot.COMMANDS.put(commandExecutor.name().toLowerCase(), commandExecutor);
                }
            } catch (IllegalAccessException | ClassNotFoundException | InstantiationException e) {
                e.printStackTrace();
            }
        });

    }
}

