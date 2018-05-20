package simple.association.rules.java.application.command.implementation;

import org.junit.Before;
import org.junit.Test;
import simple.association.rules.java.application.command.Command;
import simple.association.rules.java.application.command.model.request.GetLastAprioriRequest;
import simple.association.rules.java.application.command.model.response.GetLastAprioriResponse;
import simple.association.rules.java.application.model.Apriori;
import simple.association.rules.java.application.model.DataSet;
import simple.association.rules.java.application.model.Label;

import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.*;

/**
 * @author Faza Zulfika P P
 * @version 1.0.0
 * @since 20 May 2018
 */

public class GetLastAprioriCommandTest {

    private Command<GetLastAprioriRequest, GetLastAprioriResponse> command;

    @Before
    public void setUp() {
        command = new GetLastAprioriCommand();
    }

    @Test
    public void getLastAprioris_success() throws Exception {
        GetLastAprioriRequest getLastAprioriRequest = GetLastAprioriRequest.builder()
                .dataSets(createDataSets())
                .firstAprioris(createFirstAprioris())
                .minimumSupport(0.5)
                .build();

        GetLastAprioriResponse expectedGetLastAprioriResponse = GetLastAprioriResponse.builder()
                .lastApriori(createExpectedApriori())
                .build();

        GetLastAprioriResponse getLastAprioriResponse = command.execute(getLastAprioriRequest);

        assertNotNull(getLastAprioriResponse);
        assertEquals(expectedGetLastAprioriResponse, getLastAprioriResponse);
    }

    @Test(expected = NullPointerException.class)
    public void getLastAprioris_failed_dataSetsIsNull() throws Exception {
        GetLastAprioriRequest getLastAprioriRequest = GetLastAprioriRequest.builder()
                .dataSets(null)
                .firstAprioris(createFirstAprioris())
                .minimumSupport(0.5)
                .build();

        command.execute(getLastAprioriRequest);
    }

    @Test(expected = NullPointerException.class)
    public void getLastAprioris_failed_firstApriorisIsNull() throws Exception {
        GetLastAprioriRequest getLastAprioriRequest = GetLastAprioriRequest.builder()
                .dataSets(createDataSets())
                .firstAprioris(null)
                .minimumSupport(0.5)
                .build();

        command.execute(getLastAprioriRequest);
    }

    @Test(expected = NullPointerException.class)
    public void getLastAprioris_failed_minimumSupportIsNull() throws Exception {
        GetLastAprioriRequest getLastAprioriRequest = GetLastAprioriRequest.builder()
                .dataSets(createDataSets())
                .firstAprioris(createFirstAprioris())
                .minimumSupport(null)
                .build();

        command.execute(getLastAprioriRequest);
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

    private List<Apriori> createFirstAprioris() {
        return Arrays.asList(
                createApriori(1D, 2),
                createApriori(0.5, 4),
                createApriori(1D, 5)
        );
    }

    private Apriori createExpectedApriori() {
        return createApriori(0.5, 2, 4, 5);
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