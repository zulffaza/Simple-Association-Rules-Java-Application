package simple.association.rules.java.application.command.util;

import simple.association.rules.java.application.model.Apriori;
import simple.association.rules.java.application.model.DataSet;
import simple.association.rules.java.application.model.Label;

import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author Faza Zulfika P P
 * @version 1.0.0
 * @since 20 May 2018
 */

public class AprioriHelper {

    private static AprioriHelper instance;

    private AprioriHelper() {

    }

    public static AprioriHelper getInstance() {
        if (instance == null)
            instance = new AprioriHelper();

        return instance;
    }

    public Apriori createApriori(List<Integer> labels) {
        Label label = Label.builder()
                .labels(labels)
                .build();

        return Apriori.builder()
                .label(label)
                .build();
    }

    public void calculateSupport(Apriori apriori, List<DataSet> dataSets) {
        AtomicInteger numberOfMatchLabel = new AtomicInteger();
        Integer size = dataSets.size();

        dataSets.forEach(dataSet ->
                checkLabels(apriori, dataSet, numberOfMatchLabel));

        Double support = numberOfMatchLabel.doubleValue() / size;
        apriori.setSupport(support);
    }

    private void checkLabels(Apriori apriori, DataSet dataSet, AtomicInteger numberOfMatchLabel) {
        if (containsLabels(apriori, dataSet))
            numberOfMatchLabel.getAndIncrement();
    }

    private Boolean containsLabels(Apriori apriori, DataSet dataSet) {
        return dataSet.getLabel()
                .getLabels()
                .containsAll(apriori.getLabel().getLabels());
    }

    public Boolean checkSupport(Apriori apriori, Double minimumSupport) {
        return apriori.getSupport() >= minimumSupport;
    }
}
