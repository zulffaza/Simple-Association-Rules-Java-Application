package simple.association.rules.java.application.command.implementation;

import simple.association.rules.java.application.command.Command;
import simple.association.rules.java.application.command.model.request.GetLastAprioriRequest;
import simple.association.rules.java.application.command.model.response.GetLastAprioriResponse;
import simple.association.rules.java.application.command.util.AprioriHelper;
import simple.association.rules.java.application.model.Apriori;
import simple.association.rules.java.application.model.Label;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;
import java.util.stream.Collectors;

/**
 * @author Faza Zulfika P P
 * @version 1.0.0
 * @since 20 May 2018
 */

public class GetLastAprioriCommand implements Command<GetLastAprioriRequest, GetLastAprioriResponse> {

    private static final Integer FINAL_APRIORIS_SIZE = 1;
    private static final Integer NEXT_INDEX = 1;
    private static final Integer LAST_INDEX = 0;

    @Override
    public GetLastAprioriResponse execute(GetLastAprioriRequest getLastAprioriRequest) {
        List<Apriori> aprioris = new ArrayList<>(getLastAprioriRequest.getFirstAprioris());

        while (!isComplete(aprioris))
            aprioris = createNextAprioris(aprioris, getLastAprioriRequest);

        return GetLastAprioriResponse.builder()
                .lastApriori(aprioris.get(LAST_INDEX))
                .build();
    }

    private Boolean isComplete(List<Apriori> aprioris) {
        return aprioris.size() == FINAL_APRIORIS_SIZE;
    }

    private List<Apriori> createNextAprioris(List<Apriori> aprioris, GetLastAprioriRequest getLastAprioriRequest) {
        AprioriHelper aprioriHelper = AprioriHelper.getInstance();
        List<Apriori> nextAprioris = nextAprioris(aprioris, aprioriHelper);

        return nextAprioris.stream()
                .peek(apriori ->
                        aprioriHelper.calculateSupport(apriori, getLastAprioriRequest.getDataSets()))
                .filter(apriori ->
                        aprioriHelper.checkSupport(apriori, getLastAprioriRequest.getMinimumSupport()))
                .collect(Collectors.toList());
    }

    private List<Apriori> nextAprioris(List<Apriori> aprioris, AprioriHelper aprioriHelper) {
        List<Apriori> nextAprioris = new ArrayList<>();
        Integer size = aprioris.size();

        for (Integer i = 0; i < size; i++) {
            for (Integer j = i + NEXT_INDEX; j < size; j++) {
                Label newLabel = createUnionLabels(aprioris, i, j);
                Apriori newApriori = aprioriHelper.createApriori(newLabel.getLabels());

                addNewApriori(nextAprioris, newApriori);
            }
        }

        return nextAprioris;
    }

    private Label createUnionLabels(List<Apriori> aprioris, Integer i, Integer j) {
        Label label = aprioris.get(i).getLabel();
        Label nextLabel = aprioris.get(j).getLabel();

        return unionLabels(label, nextLabel);
    }

    private Label unionLabels(Label label, Label nextLabel) {
        Set<Integer> newLabels = new TreeSet<>();

        newLabels.addAll(label.getLabels());
        newLabels.addAll(nextLabel.getLabels());

        return Label.builder()
                .labels(new ArrayList<>(newLabels))
                .build();
    }

    private void addNewApriori(List<Apriori> nextAprioris, Apriori newApriori) {
        if (isNew(nextAprioris, newApriori))
            nextAprioris.add(newApriori);
    }

    private Boolean isNew(List<Apriori> nextAprioris, Apriori newApriori) {
        return !nextAprioris.contains(newApriori);
    }
}
