package simple.association.rules.java.application.command.implementation;

import simple.association.rules.java.application.command.Command;
import simple.association.rules.java.application.command.model.request.ReadDatasetRequest;
import simple.association.rules.java.application.command.model.response.ReadDatasetResponse;
import simple.association.rules.java.application.model.DataSet;
import simple.association.rules.java.application.model.Label;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author Faza Zulfika P P
 * @version 1.0.0
 * @since 19 May 2018
 */

public class ReadDataSetCommand implements Command<ReadDatasetRequest, ReadDatasetResponse> {

    private static final Integer ID_INDEX = 0;
    private static final Integer FIRST_DATA_INDEX = 1;
    private static final Integer DATA_TRUE = 1;

    @Override
    public ReadDatasetResponse execute(ReadDatasetRequest readDatasetRequest) {
        List<DataSet> dataSets = new ArrayList<>();

        readDatasetRequest.getLines().forEach(line ->
                createDataSet(line, readDatasetRequest.getLabel(), dataSets));

        return ReadDatasetResponse.builder()
                .dataSets(dataSets)
                .build();
    }

    private void createDataSet(String line, Label label, List<DataSet> dataSets) {
        List<String> words = splitLine(line);
        List<Integer> dataLists = parseWordsToInteger(words);

        Integer id = dataLists.get(ID_INDEX);
        Label dataLabel = createDataLabel(dataLists, label);

        DataSet dataSet = DataSet.builder()
                .id(id)
                .label(dataLabel)
                .build();
        dataSets.add(dataSet);
    }

    private Label createDataLabel(List<Integer> dataLists, Label label) {
        List<Integer> dataLabels = new ArrayList<>();

        for (Integer i = FIRST_DATA_INDEX; i < dataLists.size(); i++) {
            if (isDataTrue(dataLists.get(i)))
                dataLabels.add(getLabel(label, i));
        }

        return Label.builder()
                .labels(dataLabels)
                .build();
    }

    private Boolean isDataTrue(Integer data) {
        return data.equals(DATA_TRUE);
    }

    private Integer getLabel(Label label, Integer index) {
        return label.getLabels().get(index - FIRST_DATA_INDEX);
    }

    private List<String> splitLine(String line) {
        return Arrays.stream(line.split("\\s"))
                .collect(Collectors.toList());
    }

    private List<Integer> parseWordsToInteger(List<String> words) {
        return words.stream()
                .map(Integer::parseInt)
                .collect(Collectors.toList());
    }
}
