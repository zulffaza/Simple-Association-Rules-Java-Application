package simple.association.rules.java.application.command.implementation;

import org.junit.Before;
import org.junit.Test;
import simple.association.rules.java.application.command.Command;
import simple.association.rules.java.application.command.model.request.PrintConfidentApriorisRequest;
import simple.association.rules.java.application.model.Apriori;
import simple.association.rules.java.application.model.Label;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * @author Faza Zulfika P P
 * @version 1.0.0
 * @since 20 May 2018
 */

public class PrintConfidentApriorisCommandTest {

    private Command<PrintConfidentApriorisRequest, Void> command;

    @Before
    public void setUp() {
        command = new PrintConfidentApriorisCommand();
    }

    @Test
    public void printConfidentAprioris_success() throws Exception {
        PrintConfidentApriorisRequest printConfidentApriorisRequest = PrintConfidentApriorisRequest.builder()
                .confidentAprioris(createConfidentAprioris())
                .build();

        command.execute(printConfidentApriorisRequest);
    }

    @Test
    public void printConfidentAprioris_success_emptyConfidentAprioris() throws Exception {
        PrintConfidentApriorisRequest printConfidentApriorisRequest = PrintConfidentApriorisRequest.builder()
                .confidentAprioris(Collections.emptyList())
                .build();

        command.execute(printConfidentApriorisRequest);
    }

    @Test(expected = NullPointerException.class)
    public void printConfidentAprioris_failed_confidentApriorisIsNull() throws Exception {
        PrintConfidentApriorisRequest printConfidentApriorisRequest = PrintConfidentApriorisRequest.builder()
                .build();

        command.execute(printConfidentApriorisRequest);
    }

    private List<Apriori> createConfidentAprioris() {
        return Arrays.asList(
                createApriori(0.5, 0.5, 4, 5, 2),
                createApriori(0.5, 1D, 2, 5, 4),
                createApriori(0.5, 0.5, 2, 4, 5)
        );
    }

    private Apriori createApriori(Double support, Double confidence, Integer... labels) {
        return Apriori.builder()
                .label(Label.builder()
                        .labels(Arrays.asList(labels))
                        .build())
                .support(support)
                .confidence(confidence)
                .build();
    }
}