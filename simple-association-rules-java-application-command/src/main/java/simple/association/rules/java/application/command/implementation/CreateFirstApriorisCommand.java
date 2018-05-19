package simple.association.rules.java.application.command.implementation;

import simple.association.rules.java.application.command.Command;
import simple.association.rules.java.application.command.model.request.CreateFirstApriorisRequest;
import simple.association.rules.java.application.command.model.response.CreateFirstApriorisResponse;
import simple.association.rules.java.application.command.util.AprioriHelper;
import simple.association.rules.java.application.model.Apriori;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author Faza Zulfika P P
 * @version 1.0.0
 * @since 20 May 2018
 */

public class CreateFirstApriorisCommand implements Command<CreateFirstApriorisRequest, CreateFirstApriorisResponse> {

    @Override
    public CreateFirstApriorisResponse execute(CreateFirstApriorisRequest createFirstApriorisRequest) {
        AprioriHelper aprioriHelper = AprioriHelper.getInstance();
        List<Apriori> firstAprioris = createFirstAprioris(createFirstApriorisRequest, aprioriHelper);

        return CreateFirstApriorisResponse.builder()
                .firstAprioris(firstAprioris)
                .build();
    }

    private List<Apriori> createFirstAprioris(CreateFirstApriorisRequest createFirstApriorisRequest,
                                              AprioriHelper aprioriHelper) {
        return createFirstApriorisRequest.getLabel().getLabels()
                .stream()
                .map(Arrays::asList)
                .map(aprioriHelper::createApriori)
                .peek(apriori ->
                        aprioriHelper.calculateSupport(apriori, createFirstApriorisRequest.getDataSets()))
                .filter(apriori ->
                        aprioriHelper.checkSupport(apriori, createFirstApriorisRequest.getMinimumSupport()))
                .collect(Collectors.toList());
    }
}
