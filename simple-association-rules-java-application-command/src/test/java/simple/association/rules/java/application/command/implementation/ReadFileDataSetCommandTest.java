package simple.association.rules.java.application.command.implementation;

import org.junit.Before;
import org.junit.Test;
import simple.association.rules.java.application.command.Command;
import simple.association.rules.java.application.command.model.request.ReadFileDataSetRequest;
import simple.association.rules.java.application.command.model.response.ReadFileDataSetResponse;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

/**
 * @author Faza Zulfika P P
 * @version 1.0.0
 * @since 19 May 2018
 */

public class ReadFileDataSetCommandTest {

    private Command<ReadFileDataSetRequest, ReadFileDataSetResponse> command;

    @Before
    public void setUp() {
        command = new ReadFileDataSetCommand();
    }

    @Test
    public void readFileDataSet_success() throws Exception {
        ReadFileDataSetRequest readFileDataSetRequest = ReadFileDataSetRequest.builder()
                .pathname("dataset.txt")
                .build();

        ReadFileDataSetResponse expectedReadFileDataSetResponse = ReadFileDataSetResponse.builder()
                .lines(createExpectedResult())
                .build();

        ReadFileDataSetResponse readFileDataSetResponse = command.execute(readFileDataSetRequest);

        assertNotNull(readFileDataSetResponse);
        assertEquals(expectedReadFileDataSetResponse, readFileDataSetResponse);
    }

    @Test(expected = NullPointerException.class)
    public void readFileDataSet_failed_fileNotFound() throws Exception {
        ReadFileDataSetRequest readFileDataSetRequest = ReadFileDataSetRequest.builder()
                .pathname("notfound.txt")
                .build();

        command.execute(readFileDataSetRequest);
    }

    @Test(expected = NullPointerException.class)
    public void readFileDataSet_failed_pathnameIsNull() throws Exception {
        ReadFileDataSetRequest readFileDataSetRequest = ReadFileDataSetRequest.builder()
                .build();

        command.execute(readFileDataSetRequest);
    }

    private List<String> createExpectedResult() {
        List<String> lines = new ArrayList<>();

        lines.add("1 0 1 0 1 1");
        lines.add("2 0 1 0 0 1");

        return lines;
    }
}