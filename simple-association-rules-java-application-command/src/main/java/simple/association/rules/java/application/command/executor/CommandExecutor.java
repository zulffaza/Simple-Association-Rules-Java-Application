package simple.association.rules.java.application.command.executor;

import simple.association.rules.java.application.command.Command;

/**
 * @author Faza Zulfika P P
 * @version 1.0.0
 * @since 19 May 2018
 */

public interface CommandExecutor {

    <REQUEST, RESPONSE> RESPONSE doExecute(
            Class<? extends Command<REQUEST, RESPONSE>> commandClass, REQUEST request) throws Exception;
}
