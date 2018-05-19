package simple.association.rules.java.application.command.implementation;

import org.junit.Before;
import org.junit.Test;
import simple.association.rules.java.application.command.Command;
import simple.association.rules.java.application.command.model.request.ReadDatasetRequest;
import simple.association.rules.java.application.command.model.response.ReadDatasetResponse;
import simple.association.rules.java.application.model.DataSet;
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
                .label(createLabel())
                .build();

        ReadDatasetResponse expectedReadDatasetResponse = ReadDatasetResponse.builder()
                .dataSets(createExpectedResult())
                .build();

        ReadDatasetResponse readDatasetResponse = command.execute(readDatasetRequest);

        assertNotNull(readDatasetResponse);
        assertEquals(expectedReadDatasetResponse, readDatasetResponse);
    }

    @Test(expected = IndexOutOfBoundsException.class)
    public void readDataSet_failed_labelIsEmpty() throws Exception {
        ReadDatasetRequest readDatasetRequest = ReadDatasetRequest.builder()
                .lines(createLines())
                .label(createEmptyLabel())
                .build();

        command.execute(readDatasetRequest);
    }

    @Test(expected = NullPointerException.class)
    public void readDataSet_failed_lineIsNull() throws Exception {
        ReadDatasetRequest readDatasetRequest = ReadDatasetRequest.builder()
                .label(createEmptyLabel())
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

    private Label createLabel() {
        List<Integer> labels = new ArrayList<>();

        for (int i = 1; i <= 5; i++)
            labels.add(i);

        return Label.builder()
                .labels(labels)
                .build();
    }

    private Label createEmptyLabel() {
        return Label.builder()
                .labels(Collections.emptyList())
                .build();
    }

    private List<DataSet> createExpectedResult() {
        List<DataSet> dataSets = new ArrayList<>();

        dataSets.add(createExpectedDataSet1());
        dataSets.add(createExpectedDataSet2());

        return dataSets;
    }

    private DataSet createExpectedDataSet1() {
        List<Integer> labels = new ArrayList<>();

        labels.add(2);
        labels.add(4);
        labels.add(5);

        return DataSet.builder()
                .id(1)
                .label(Label.builder()
                        .labels(labels)
                        .build())
                .build();
    }

    private DataSet createExpectedDataSet2() {
        List<Integer> labels = new ArrayList<>();

        labels.add(2);
        labels.add(5);

        return DataSet.builder()
                .id(2)
                .label(Label.builder()
                        .labels(labels)
                        .build())
                .build();
    }
}