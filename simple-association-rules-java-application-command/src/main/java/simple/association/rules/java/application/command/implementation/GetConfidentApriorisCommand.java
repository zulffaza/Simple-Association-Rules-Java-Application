package simple.association.rules.java.application.command.implementation;

import simple.association.rules.java.application.command.Command;
import simple.association.rules.java.application.command.model.request.GetConfidentApriorisRequest;
import simple.association.rules.java.application.command.model.response.GetConfidentApriorisResponse;
import simple.association.rules.java.application.command.util.AprioriHelper;
import simple.association.rules.java.application.model.Apriori;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author Faza Zulfika P P
 * @version 1.0.0
 * @since 20 May 2018
 */

public class GetConfidentApriorisCommand implements Command<GetConfidentApriorisRequest, GetConfidentApriorisResponse> {

    private static final Integer NORMALIZE_INDEX = 1;

    @Override
    public GetConfidentApriorisResponse execute(GetConfidentApriorisRequest getConfidentApriorisRequest) {
        List<Apriori> confidentAprioris = createConfidentAprioris(getConfidentApriorisRequest.getLastApriori());
        confidentAprioris = getConfidentAprioris(confidentAprioris, getConfidentApriorisRequest);

        return GetConfidentApriorisResponse.builder()
                .confidentAprioris(confidentAprioris)
                .build();
    }

    private List<Apriori> createConfidentAprioris(Apriori lastApriori) {
        AprioriHelper aprioriHelper = AprioriHelper.getInstance();
        List<Apriori> confidentAprioris = new ArrayList<>();
        Integer size = lastApriori.getLabel().getLabels().size();

        for (Integer i = 0; i < size; i++) {
            Apriori apriori = createConfidentApriori(lastApriori, aprioriHelper, i);
            confidentAprioris.add(apriori);
        }

        return confidentAprioris;
    }

    private Apriori createConfidentApriori(Apriori lastApriori, AprioriHelper aprioriHelper, Integer i) {
        Apriori apriori = aprioriHelper.createApriori(new ArrayList<>());
        Integer label = lastApriori.getLabel().getLabels().get(i);

        addAnotherLabel(apriori, lastApriori, label);

        apriori.getLabel().getLabels().add(label);
        apriori.setSupport(lastApriori.getSupport());

        return apriori;
    }

    private void addAnotherLabel(Apriori apriori, Apriori lastApriori, Integer label) {
        Integer size = lastApriori.getLabel().getLabels().size();

        for (Integer j = 0; j < size; j++) {
            Integer nextLabel = lastApriori.getLabel().getLabels().get(j);

            if (!label.equals(nextLabel))
                apriori.getLabel().getLabels().add(nextLabel);
        }
    }

    private List<Apriori> getConfidentAprioris(List<Apriori> confidentAprioris,
                                               GetConfidentApriorisRequest getConfidentApriorisRequest) {
        return confidentAprioris.stream()
                .peek(apriori ->
                        calculateConfident(apriori, getConfidentApriorisRequest.getFirstAprioris()))
                .filter(apriori ->
                        isConfidence(apriori, getConfidentApriorisRequest.getMinimumConfidence()))
                .collect(Collectors.toList());
    }

    private void calculateConfident(Apriori apriori, List<Apriori> firstAprioris) {
        Integer lastIndex = apriori.getLabel().getLabels().size() - NORMALIZE_INDEX;
        Integer lastLabel = apriori.getLabel().getLabels().get(lastIndex);

        Apriori lastLabelApriori = getAprioriFromLabel(firstAprioris, lastLabel);
        Double confidence = apriori.getSupport() / lastLabelApriori.getSupport();

        apriori.setConfidence(confidence);
    }

    private Apriori getAprioriFromLabel(List<Apriori> firstAprioris, Integer label) {
        return firstAprioris.stream()
                .filter(apriori ->
                        isHasLabel(apriori, label))
                .findFirst()
                .orElse(null);
    }

    private Boolean isHasLabel(Apriori apriori, Integer label) {
        return apriori.getLabel().getLabels().contains(label);
    }

    private Boolean isConfidence(Apriori apriori, Double minimumConfidence) {
        return apriori.getConfidence() >= minimumConfidence;
    }
}
