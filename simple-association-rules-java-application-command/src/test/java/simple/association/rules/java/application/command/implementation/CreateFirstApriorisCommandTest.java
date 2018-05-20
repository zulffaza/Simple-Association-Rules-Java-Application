package simple.association.rules.java.application.command.implementation;

import org.junit.Before;
import org.junit.Test;
import simple.association.rules.java.application.command.Command;
import simple.association.rules.java.application.command.model.request.CreateFirstApriorisRequest;
import simple.association.rules.java.application.command.model.response.CreateFirstApriorisResponse;
import simple.association.rules.java.application.model.Apriori;
import simple.association.rules.java.application.model.DataSet;
import simple.association.rules.java.application.model.Label;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.junit.Assert.*;

/**
 * @author Faza Zulfika P P
 * @version 1.0.0
 * @since 10 May 2018
 */

public class CreateFirstApriorisCommandTest {

    private Command<CreateFirstApriorisRequest, CreateFirstApriorisResponse> command;

    @Before
    public void setUp() {
        command = new CreateFirstApriorisCommand();
    }

    @Test
    public void createFirstAprioris_success() throws Exception {
        CreateFirstApriorisRequest createFirstApriorisRequest = CreateFirstApriorisRequest.builder()
                .dataSets(createDataSets())
                .label(createLabel(1, 2, 3, 4, 5))
                .minimumSupport(0.5)
                .build();

        CreateFirstApriorisResponse expectedCreateFirstApriorisResponse = CreateFirstApriorisResponse.builder()
                .firstAprioris(createExpectedAprioris())
                .build();

        CreateFirstApriorisResponse createFirstApriorisResponse = command.execute(createFirstApriorisRequest);

        assertNotNull(createFirstApriorisResponse);
        assertEquals(expectedCreateFirstApriorisResponse, createFirstApriorisResponse);
    }

    @Test
    public void createFirstAprioris_success_emptyDataSets() throws Exception {
        CreateFirstApriorisRequest createFirstApriorisRequest = CreateFirstApriorisRequest.builder()
                .dataSets(Collections.emptyList())
                .label(createLabel(1, 2, 3, 4, 5))
                .minimumSupport(0.5)
                .build();

        CreateFirstApriorisResponse expectedCreateFirstApriorisResponse = CreateFirstApriorisResponse.builder()
                .firstAprioris(Collections.emptyList())
                .build();

        CreateFirstApriorisResponse createFirstApriorisResponse = command.execute(createFirstApriorisRequest);

        assertNotNull(createFirstApriorisResponse);
        assertEquals(expectedCreateFirstApriorisResponse, createFirstApriorisResponse);
    }

    @Test
    public void createFirstAprioris_success_emptyLabels() throws Exception {
        CreateFirstApriorisRequest createFirstApriorisRequest = CreateFirstApriorisRequest.builder()
                .dataSets(createDataSets())
                .label(createLabel())
                .minimumSupport(0.5)
                .build();

        CreateFirstApriorisResponse expectedCreateFirstApriorisResponse = CreateFirstApriorisResponse.builder()
                .firstAprioris(Collections.emptyList())
                .build();

        CreateFirstApriorisResponse createFirstApriorisResponse = command.execute(createFirstApriorisRequest);

        assertNotNull(createFirstApriorisResponse);
        assertEquals(expectedCreateFirstApriorisResponse, createFirstApriorisResponse);
    }

    @Test(expected = NullPointerException.class)
    public void createFirstAprioris_failed_dataSetsIsNull() throws Exception {
        CreateFirstApriorisRequest createFirstApriorisRequest = CreateFirstApriorisRequest.builder()
                .label(createLabel(1, 2, 3, 4, 5))
                .minimumSupport(0.5)
                .build();

        command.execute(createFirstApriorisRequest);
    }

    @Test(expected = NullPointerException.class)
    public void createFirstAprioris_failed_labelsIsNull() throws Exception {
        CreateFirstApriorisRequest createFirstApriorisRequest = CreateFirstApriorisRequest.builder()
                .dataSets(createDataSets())
                .label(createLabel(1, 2, 3, 4, 5))
                .minimumSupport(0.5)
                .build();

        createFirstApriorisRequest.getLabel().setLabels(null);

        command.execute(createFirstApriorisRequest);
    }

    @Test(expected = NullPointerException.class)
    public void createFirstAprioris_failed_labelIsNull() throws Exception {
        CreateFirstApriorisRequest createFirstApriorisRequest = CreateFirstApriorisRequest.builder()
                .dataSets(createDataSets())
                .minimumSupport(0.5)
                .build();

        command.execute(createFirstApriorisRequest);
    }

    @Test(expected = NullPointerException.class)
    public void createFirstAprioris_failed_minimumSupportIsNull() throws Exception {
        CreateFirstApriorisRequest createFirstApriorisRequest = CreateFirstApriorisRequest.builder()
                .dataSets(createDataSets())
                .label(createLabel(1, 2, 3, 4, 5))
                .build();

        command.execute(createFirstApriorisRequest);
    }

    private Label createLabel(Integer... labels) {
        return Label.builder()
                .labels(Arrays.asList(labels))
                .build();
    }

    private List<DataSet> createDataSets() {
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

    private List<Apriori> createExpectedAprioris() {
        return Arrays.asList(
                createApriori(1D, 2),
                createApriori(0.5, 4),
                createApriori(1D, 5)
        );
    }

    private Apriori createApriori(Double support, Integer... labels) {
        return Apriori.builder()
                .label(Label.builder()
                        .labels(Arrays.asList(labels))
                        .build())
                .support(support)
                .build();
    }
}