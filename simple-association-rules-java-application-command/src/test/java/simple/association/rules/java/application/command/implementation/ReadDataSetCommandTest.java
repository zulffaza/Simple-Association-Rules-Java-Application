package simple.association.rules.java.application.command.implementation;

import org.junit.Before;
import org.junit.Test;
import simple.association.rules.java.application.command.Command;
import simple.association.rules.java.application.command.model.request.ReadDatasetRequest;
import simple.association.rules.java.application.command.model.response.ReadDatasetResponse;
import simple.association.rules.java.application.model.DataSet;
import simple.association.rules.java.application.model.Label;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.*;

/**
 * @author Faza Zulfika P P
 * @version 1.0.0
 * @since 19 May 2018
 */

public class ReadDataSetCommandTest {

    private Command<ReadDatasetRequest, ReadDatasetResponse> command;

    @Before
    public void setUp() {
        command = new ReadDataSetCommand();
    }

    @Test
    public void readDataSet_success() throws Exception {
        ReadDatasetRequest readDatasetRequest = ReadDatasetRequest.builder()
                .lines(createLines())
                .label(createLabel(1, 2, 3, 4, 5))
                .build();

        ReadDatasetResponse expectedReadDatasetResponse = ReadDatasetResponse.builder()
                .dataSets(createExpectedDataSets())
                .build();

        ReadDatasetResponse readDatasetResponse = command.execute(readDatasetRequest);

        assertNotNull(readDatasetResponse);
        assertEquals(expectedReadDatasetResponse, readDatasetResponse);
    }

    @Test(expected = IndexOutOfBoundsException.class)
    public void readDataSet_failed_labelIsEmpty() throws Exception {
        ReadDatasetRequest readDatasetRequest = ReadDatasetRequest.builder()
                .lines(createLines())
                .label(createLabel())
                .build();

        command.execute(readDatasetRequest);
    }

    @Test(expected = NullPointerException.class)
    public void readDataSet_failed_lineIsNull() throws Exception {
        ReadDatasetRequest readDatasetRequest = ReadDatasetRequest.builder()
                .label(createLabel())
                .build();

        command.execute(readDatasetRequest);
    }

    @Test(expected = NullPointerException.class)
    public void readDataSet_failed_labelIsNull() throws Exception {
        ReadDatasetRequest readDatasetRequest = ReadDatasetRequest.builder()
                .lines(createLines())
                .build();

        command.execute(readDatasetRequest);
    }

    private List<String> createLines() {
        List<String> lines = new ArrayList<>();

        lines.add("1 0 1 0 1 1");
        lines.add("2 0 1 0 0 1");

        return lines;
    }

    private Label createLabel(Integer... labels) {
        return Label.builder()
                .labels(Arrays.asList(labels))
                .build();
    }

    private List<DataSet> createExpectedDataSets() {
        return Arrays.asList(
                createDataSet(1, 2, 4, 5),
                createDataSet(2, 2, 5)
        );
    }

    private DataSet createDataSet(Integer id, Integer... labels) {
        return DataSet.builder()
                .id(id)
                .label(Label.builder()
                        .labels(Arrays.asList(labels))
                        .build())
                .build();
    }
}