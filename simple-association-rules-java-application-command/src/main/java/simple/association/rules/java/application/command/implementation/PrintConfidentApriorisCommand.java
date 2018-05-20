package simple.association.rules.java.application.command.implementation;

import simple.association.rules.java.application.command.Command;
import simple.association.rules.java.application.command.model.request.PrintConfidentApriorisRequest;
import simple.association.rules.java.application.model.Apriori;

import java.util.List;

/**
 * @author Faza Zulfika P P
 * @version 1.0.0
 * @since 20 May 2018
 */

public class PrintConfidentApriorisCommand implements Command<PrintConfidentApriorisRequest, Void> {

    private static final Integer EMPTY_APRIORIS = 0;
    private static final Integer FIRST_INDEX = 0;
    private static final Integer REMOVE_LAST_INDEX = 1;
    private static final Integer GET_LAST_INDEX = 1;
    private static final Integer SINGLETON_SIZE = 1;

    @Override
    public Void execute(PrintConfidentApriorisRequest printConfidentApriorisRequest) {
        List<Apriori> confidentAprioris = printConfidentApriorisRequest.getConfidentAprioris();

        if (isHasConfidentAprioris(confidentAprioris))
            printConfidentAprioris(confidentAprioris);
        else
            printEmptyAprioris();

        return null; // Void object need return null
    }

    private Boolean isHasConfidentAprioris(List<Apriori> confidentAprioris) {
        return confidentAprioris.size() != EMPTY_APRIORIS;
    }

    private void printConfidentAprioris(List<Apriori> confidentAprioris) {
        confidentAprioris.forEach(confidentApriori -> {
            printBuyConclusion(confidentApriori);
            printThenConclusion(confidentApriori);
        });
    }

    private void printBuyConclusion(Apriori confidentApriori) {
        Integer size = confidentApriori.getLabel().getLabels().size() - REMOVE_LAST_INDEX;

        System.out.print("If customers buy ");

        for (Integer i = FIRST_INDEX; i < size; i++)
            printBuyConclusionIteration(confidentApriori, i, size);
    }

    private void printBuyConclusionIteration(Apriori confidentAprioris, Integer iteration, Integer size) {
        if (isLastLabel(iteration, size))
            printLastLabelBuyConclusionIteration(confidentAprioris, iteration, size);
        else
            printLabelBuyConclusionIteration(confidentAprioris, iteration);
    }

    private Boolean isLastLabel(Integer iteration, Integer size) {
        return iteration.equals(size - GET_LAST_INDEX);
    }

    private void printLastLabelBuyConclusionIteration(Apriori confidentAprioris, Integer iteration, Integer size) {
        if (!isSingleton(size))
            System.out.print("and ");

        System.out.print(confidentAprioris.getLabel().getLabels().get(iteration) + " ");
    }

    private Boolean isSingleton(Integer size) {
        return size.equals(SINGLETON_SIZE);
    }

    private void printLabelBuyConclusionIteration(Apriori confidentAprioris, Integer iteration) {
        System.out.print(confidentAprioris.getLabel().getLabels().get(iteration) + ", ");
    }

    private void printThenConclusion(Apriori confidentAprioris) {
        Integer index = confidentAprioris.getLabel().getLabels().size() - GET_LAST_INDEX;

        System.out.println("then definetely buy " + confidentAprioris.getLabel().getLabels().get(index));
    }

    private void printEmptyAprioris() {
        System.out.println("Sorry, data sets didn't has confident aprioris...");
    }
}
