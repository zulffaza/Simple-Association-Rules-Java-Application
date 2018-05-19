package simple.association.rules.java.application.command.implementation;

import org.junit.Before;
import org.junit.Test;
import simple.association.rules.java.application.command.Command;
import simple.association.rules.java.application.command.model.request.ReadLabelRequest;
import simple.association.rules.java.application.command.model.response.ReadLabelResponse;
import simple.association.rules.java.application.model.Label;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static org.junit.Assert.*;

/**
 * @author Faza Zulfika P P
 * @version 1.0.0
 * @since 19 May 2018
 */

public class ReadLabelCommandTest {

    private Command<ReadLabelRequest, ReadLabelResponse> command;

    @Before
    public void setUp() {
        command = new ReadLabelCommand();
    }

    @Test
    public void readLabel_success() throws Exception {
        ReadLabelRequest readLabelRequest = ReadLabelRequest.builder()
                .lines(createLines())
                .build();

        ReadLabelResponse expectedReadLabelResponse = ReadLabelResponse.builder()
                .label(createExpectedLabel())
                .build();

        ReadLabelResponse readLabelResponse = command.execute(readLabelRequest);

        assertNotNull(readLabelResponse);
        assertEquals(expectedReadLabelResponse, readLabelResponse);
    }

    @Test(expected = IndexOutOfBoundsException.class)
    public void readLabel_failed_emptyLines() throws Exception {
        ReadLabelRequest readLabelRequest = ReadLabelRequest.builder()
                .lines(Collections.emptyList())
                .build();

        command.execute(readLabelRequest);
    }

    @Test(expected = NullPointerException.class)
    public void readLabel_failed_linesIsNull() throws Exception {
        ReadLabelRequest readLabelRequest = ReadLabelRequest.builder()
                .build();

        command.execute(readLabelRequest);
    }

    private List<String> createLines() {
        List<String> lines = new ArrayList<>();

        lines.add("1 0 1 0 1 1");
        lines.add("2 0 1 0 0 1");

        return lines;
    }

    private Label createExpectedLabel() {
        List<Integer> labels = new ArrayList<>();

        for (int i = 1; i <= 5; i++)
            labels.add(i);

        return Label.builder()
                .labels(labels)
                .build();
    }
}