package simple.association.rules.java.application.command.executor.implementation;

import simple.association.rules.java.application.command.Command;
import simple.association.rules.java.application.command.executor.CommandExecutor;

/**
 * @author Faza Zulfika P P
 * @version 1.0.0
 * @since 19 May 2018
 */


public class CommandExecutorImpl implements CommandExecutor {

    private static CommandExecutorImpl instance;

    private CommandExecutorImpl() {

    }

    public static CommandExecutorImpl getInstance() {
        if (instance == null)
            instance = new CommandExecutorImpl();

        return instance;
    }

    @Override
    public <REQUEST, RESPONSE> RESPONSE doExecute(
            Class<? extends Command<REQUEST, RESPONSE>> commandClass, REQUEST request) throws Exception {
        return commandClass.newInstance().execute(request);
    }
}
