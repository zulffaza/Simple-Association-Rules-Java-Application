package simple.association.rules.java.application.command.executor.implementation;

import org.junit.Before;
import org.junit.Test;
import simple.association.rules.java.application.command.executor.CommandExecutor;
import simple.association.rules.java.application.command.implementation.ReadFileDataSetCommand;
import simple.association.rules.java.application.command.model.request.ReadFileDataSetRequest;
import simple.association.rules.java.application.command.model.response.ReadFileDataSetResponse;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

/**
 * @author Faza Zulfika P P
 * @version 1.0.0
 * @since 20 May 2018
 */

public class CommandExecutorImplTest {

    private CommandExecutor commandExecutor;

    @Before
    public void setUp() {
        commandExecutor = CommandExecutorImpl.getInstance();
    }

    @Test
    public void doExecute_success() throws Exception {
        String pathname = "..\\simple-association-rules-java-application-command\\src\\test\\resources\\dataset.txt";

        ReadFileDataSetRequest readFileDataSetRequest = ReadFileDataSetRequest.builder()
                .pathname(pathname)
                .build();

        ReadFileDataSetResponse expectedReadFileDataSetResponse = ReadFileDataSetResponse.builder()
                .lines(createExpectedLines())
                .build();

        ReadFileDataSetResponse readFileDataSetResponse = commandExecutor.doExecute(
                ReadFileDataSetCommand.class, readFileDataSetRequest);

        assertNotNull(readFileDataSetResponse);
        assertEquals(expectedReadFileDataSetResponse, readFileDataSetResponse);
    }

    @Test(expected = Exception.class)
    public void doExecute_success_commandThrowException() throws Exception {
        ReadFileDataSetRequest readFileDataSetRequest = ReadFileDataSetRequest.builder()
                .build();

        commandExecutor.doExecute(
                ReadFileDataSetCommand.class, readFileDataSetRequest);
    }

    private List<String> createExpectedLines() {
        List<String> lines = new ArrayList<>();

        lines.add("1 0 1 0 1 1");
        lines.add("2 0 1 0 0 1");

        return lines;
    }
}