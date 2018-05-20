package simple.association.rules.java.application.command.implementation;

import org.junit.Before;
import org.junit.Test;
import simple.association.rules.java.application.command.Command;
import simple.association.rules.java.application.command.model.request.GetConfidentApriorisRequest;
import simple.association.rules.java.application.command.model.response.GetConfidentApriorisResponse;
import simple.association.rules.java.application.model.Apriori;
import simple.association.rules.java.application.model.Label;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.junit.Assert.*;

/**
 * @author Faza Zulfika P P
 * @version 1.0.0
 * @since 20 May 2018
 */

public class GetConfidentApriorisCommandTest {

    private Command<GetConfidentApriorisRequest, GetConfidentApriorisResponse> command;

    @Before
    public void setUp() {
        command = new GetConfidentApriorisCommand();
    }

    @Test
    public void getConfidentAprioris_success() throws Exception {
        GetConfidentApriorisRequest getConfidentApriorisRequest = GetConfidentApriorisRequest.builder()
                .firstAprioris(createFirstAprioris())
                .lastApriori(createLastAprioris())
                .minimumConfidence(0.5)
                .build();

        GetConfidentApriorisResponse expectedGetConfidentApriorisResponse = GetConfidentApriorisResponse.builder()
                .confidentAprioris(createExpectedAprioris())
                .build();

        GetConfidentApriorisResponse getConfidentApriorisResponse = command.execute(getConfidentApriorisRequest);

        assertNotNull(getConfidentApriorisResponse);
        assertEquals(expectedGetConfidentApriorisResponse, getConfidentApriorisResponse);
    }

    @Test(expected = NullPointerException.class)
    public void getConfidentAprioris_failed_emptyFirstAprioris() throws Exception {
        GetConfidentApriorisRequest getConfidentApriorisRequest = GetConfidentApriorisRequest.builder()
                .firstAprioris(Collections.emptyList())
                .lastApriori(createLastAprioris())
                .minimumConfidence(0.5)
                .build();

        command.execute(getConfidentApriorisRequest);
    }

    @Test(expected = NullPointerException.class)
    public void getConfidentAprioris_failed_firstApriorisIsNull() throws Exception {
        GetConfidentApriorisRequest getConfidentApriorisRequest = GetConfidentApriorisRequest.builder()
                .lastApriori(createLastAprioris())
                .minimumConfidence(0.5)
                .build();

        command.execute(getConfidentApriorisRequest);
    }

    @Test(expected = NullPointerException.class)
    public void getConfidentAprioris_failed_lastAprioriIsNull() throws Exception {
        GetConfidentApriorisRequest getConfidentApriorisRequest = GetConfidentApriorisRequest.builder()
                .firstAprioris(createFirstAprioris())
                .minimumConfidence(0.5)
                .build();

        command.execute(getConfidentApriorisRequest);
    }

    @Test(expected = NullPointerException.class)
    public void getConfidentAprioris_failed_minimumConfidenceIsNull() throws Exception {
        GetConfidentApriorisRequest getConfidentApriorisRequest = GetConfidentApriorisRequest.builder()
                .firstAprioris(createFirstAprioris())
                .lastApriori(createLastAprioris())
                .build();

        command.execute(getConfidentApriorisRequest);
    }

    private List<Apriori> createFirstAprioris() {
        return Arrays.asList(
                createApriori(1D, 2),
                createApriori(0.5, 4),
                createApriori(1D, 5)
        );
    }

    private Apriori createLastAprioris() {
        return createApriori(0.5, 2, 4, 5);
    }

    private List<Apriori> createExpectedAprioris() {
        List<Apriori> expectedAprioris = Arrays.asList(
                createApriori(0.5, 4, 5, 2),
                createApriori(0.5, 2, 5, 4),
                createApriori(0.5, 2, 4, 5)
        );

        expectedAprioris.get(0).setConfidence(0.5);
        expectedAprioris.get(1).setConfidence(1D);
        expectedAprioris.get(2).setConfidence(0.5);

        return expectedAprioris;
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