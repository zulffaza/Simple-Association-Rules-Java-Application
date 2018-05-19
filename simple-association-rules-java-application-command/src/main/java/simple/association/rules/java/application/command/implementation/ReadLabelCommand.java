package simple.association.rules.java.application.command.implementation;

import simple.association.rules.java.application.command.Command;
import simple.association.rules.java.application.command.model.request.ReadLabelRequest;
import simple.association.rules.java.application.command.model.response.ReadLabelResponse;
import simple.association.rules.java.application.model.Label;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @author Faza Zulfika P P
 * @version 1.0.0
 * @since 19 May 2018
 */

public class ReadLabelCommand implements Command<ReadLabelRequest, ReadLabelResponse> {

    private static final Integer FIRST_LINE_INDEX = 0;
    private static final Integer ID_COUNT = 1;
    private static final Integer FIRST_LABEL = 1;

    @Override
    public ReadLabelResponse execute(ReadLabelRequest readLabelRequest) throws Exception {
        String firstLine = readLabelRequest.getLines().get(FIRST_LINE_INDEX);
        Integer labelCount = countLabel(firstLine);
        Label label = createLabel(labelCount);

        return ReadLabelResponse.builder()
                .label(label)
                .build();
    }

    private Integer countLabel(String firstLine) {
        Long count = Arrays.stream(firstLine.split("\\s"))
                .count();

        return count.intValue() - ID_COUNT;
    }

    private Label createLabel(Integer labelCount) {
        List<Integer> labels = generateLabels(labelCount);

        return Label.builder()
                .labels(labels)
                .build();
    }

    private List<Integer> generateLabels(Integer labelCount) {
        List<Integer> labels = new ArrayList<>();

        for (Integer label = FIRST_LABEL; label <= labelCount; label++)
            labels.add(label);

        return labels;
    }
}
