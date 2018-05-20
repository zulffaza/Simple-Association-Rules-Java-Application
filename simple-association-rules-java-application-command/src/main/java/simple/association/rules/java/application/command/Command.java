package simple.association.rules.java.application.command;

/**
 * @author Faza Zulfika P P
 * @version 1.0.0
 * @since 19 May 2018
 */

public interface Command<REQUEST, RESPONSE> {

    RESPONSE execute(REQUEST request) throws Exception;
}
