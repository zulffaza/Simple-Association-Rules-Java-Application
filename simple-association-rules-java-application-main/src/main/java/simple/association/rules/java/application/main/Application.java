package simple.association.rules.java.application.main;

import simple.association.rules.java.application.command.executor.CommandExecutor;
import simple.association.rules.java.application.command.executor.implementation.CommandExecutorImpl;
import simple.association.rules.java.application.command.implementation.*;
import simple.association.rules.java.application.command.model.request.*;
import simple.association.rules.java.application.command.model.response.*;
import simple.association.rules.java.application.command.util.CommandExceptionHelper;
import simple.association.rules.java.application.model.Apriori;
import simple.association.rules.java.application.model.DataSet;
import simple.association.rules.java.application.model.Label;

import java.util.List;
import java.util.Scanner;

/**
 * @author Faza Zulfika P P
 * @version 1.0.0
 * @since 20 May 2018
 */

public class Application {

//    private static final String PATHNAME = "dataset-latihan.txt";
    private static final String PATHNAME = "dataset-tugas.txt";

    private static final Integer PERCENTAGE_TO_DECIMAL = 100;

    private static Application instance;

    private Application() {

    }

    private static Application getInstance() {
        if (instance == null)
            instance = new Application();

        return instance;
    }

    public static void main(String[] args) {
        Application application = Application.getInstance();

        Scanner scanner = new Scanner(System.in);

        CommandExecutor commandExecutor = CommandExecutorImpl.getInstance();
        CommandExceptionHelper commandExceptionHelper = CommandExceptionHelper.getInstance();

        Double minimumSupport = application.getMinimumSupport(scanner);
        Double minimumConfidence = application.getMinimumConfidence(scanner);

        application.printNewLine();

        ReadFileDataSetRequest readFileDataSetRequest = application.createReadFileDataSetRequest();
        List<String> lines = application.readFileDataSet(readFileDataSetRequest,
                commandExecutor, commandExceptionHelper);

        lines.forEach(System.out::println);
        application.printNewLine();

        ReadLabelRequest readLabelRequest = application.createReadLabelRequest(lines);
        Label label = application.readLabel(readLabelRequest, commandExecutor, commandExceptionHelper);

        System.out.println(label);
        application.printNewLine();

        ReadDatasetRequest readDatasetRequest = application.createReadDatasetRequest(lines, label);
        List<DataSet> dataSets = application.readDataSet(readDatasetRequest, commandExecutor, commandExceptionHelper);

        dataSets.forEach(System.out::println);
        application.printNewLine();

        CreateFirstApriorisRequest createFirstApriorisRequest = application.createCreateFirstApriorisRequest(
                dataSets, label, minimumSupport);
        List<Apriori> firstAprioris = application.createFirstAprioris(createFirstApriorisRequest,
                commandExecutor, commandExceptionHelper);

        firstAprioris.forEach(System.out::println);
        application.printNewLine();

        GetLastAprioriRequest getLastAprioriRequest = application.createGetLastApriorisRequest(dataSets,
                firstAprioris, minimumSupport);
        Apriori lastApriori = application.getLastApriori(getLastAprioriRequest,
                commandExecutor, commandExceptionHelper);

        System.out.println(lastApriori);
        application.printNewLine();

        GetConfidentApriorisRequest getConfidentApriorisRequest = application.createGetConfidentApriorisRequest(
                firstAprioris, lastApriori, minimumConfidence);
        List<Apriori> confidentAprioris = application.getConfidenceAprioris(getConfidentApriorisRequest,
                commandExecutor, commandExceptionHelper);

        confidentAprioris.forEach(System.out::println);
    }

    private Double getMinimumSupport(Scanner scanner) {
        System.out.print("Minimum support (%) : ");

        return scanner.nextDouble() / PERCENTAGE_TO_DECIMAL;
    }

    private Double getMinimumConfidence(Scanner scanner) {
        System.out.print("Minimum confidence (%) : ");

        return scanner.nextDouble() / PERCENTAGE_TO_DECIMAL;
    }

    private void printNewLine() {
        System.out.println();
    }

    private ReadFileDataSetRequest createReadFileDataSetRequest() {
        return ReadFileDataSetRequest.builder()
                .pathname(PATHNAME)
                .build();
    }

    private List<String> readFileDataSet(ReadFileDataSetRequest readFileDataSetRequest,
                                         CommandExecutor commandExecutor,
                                         CommandExceptionHelper commandExceptionHelper) {
        ReadFileDataSetResponse readFileDataSetResponse = ReadFileDataSetResponse.builder()
                .build();

        try {
            readFileDataSetResponse = commandExecutor.doExecute(
                    ReadFileDataSetCommand.class, readFileDataSetRequest);
        } catch (Exception e) {
            commandExceptionHelper.printMessage("Error while running ReadFileDataSetCommand...");
        }

        return readFileDataSetResponse.getLines();
    }

    private ReadLabelRequest createReadLabelRequest(List<String> lines) {
        return ReadLabelRequest.builder()
                .lines(lines)
                .build();
    }

    private Label readLabel(ReadLabelRequest readLabelRequest,
                            CommandExecutor commandExecutor,
                            CommandExceptionHelper commandExceptionHelper) {
        ReadLabelResponse readLabelResponse = ReadLabelResponse.builder()
                .build();

        try {
            readLabelResponse = commandExecutor.doExecute(
                    ReadLabelCommand.class, readLabelRequest);
        } catch (Exception e) {
            commandExceptionHelper.printMessage("Error while running ReadLabelCommand...");
        }

        return readLabelResponse.getLabel();
    }

    private ReadDatasetRequest createReadDatasetRequest(List<String> lines, Label label) {
        return ReadDatasetRequest.builder()
                .lines(lines)
                .label(label)
                .build();
    }

    private List<DataSet> readDataSet(ReadDatasetRequest readDatasetRequest,
                                      CommandExecutor commandExecutor,
                                      CommandExceptionHelper commandExceptionHelper) {
        ReadDatasetResponse readDatasetResponse = ReadDatasetResponse.builder()
                .build();

        try {
            readDatasetResponse = commandExecutor.doExecute(
                    ReadDataSetCommand.class, readDatasetRequest);
        } catch (Exception e) {
            commandExceptionHelper.printMessage("Error while running ReadDataSetCommand...");
        }

        return readDatasetResponse.getDataSets();
    }

    private CreateFirstApriorisRequest createCreateFirstApriorisRequest(List<DataSet> dataSets, Label label,
                                                                        Double minimumSupport) {
        return CreateFirstApriorisRequest.builder()
                .dataSets(dataSets)
                .label(label)
                .minimumSupport(minimumSupport)
                .build();
    }

    private List<Apriori> createFirstAprioris(CreateFirstApriorisRequest createFirstApriorisRequest,
                                              CommandExecutor commandExecutor,
                                              CommandExceptionHelper commandExceptionHelper) {
        CreateFirstApriorisResponse createFirstApriorisResponse = CreateFirstApriorisResponse.builder()
                .build();

        try {
            createFirstApriorisResponse = commandExecutor.doExecute(
                    CreateFirstApriorisCommand.class, createFirstApriorisRequest);
        } catch (Exception e) {
            commandExceptionHelper.printMessage("Error while running CreateFirstApriorisCommand...");
        }

        return createFirstApriorisResponse.getFirstAprioris();
    }

    private GetLastAprioriRequest createGetLastApriorisRequest(List<DataSet> dataSets, List<Apriori> firstAprioris,
                                                               Double minimumSupport) {
        return GetLastAprioriRequest.builder()
                .dataSets(dataSets)
                .firstAprioris(firstAprioris)
                .minimumSupport(minimumSupport)
                .build();
    }

    private Apriori getLastApriori(GetLastAprioriRequest getLastAprioriRequest,
                                   CommandExecutor commandExecutor,
                                   CommandExceptionHelper commandExceptionHelper) {
        GetLastAprioriResponse getLastAprioriResponse = GetLastAprioriResponse.builder()
                .build();

        try {
            getLastAprioriResponse = commandExecutor.doExecute(
                    GetLastAprioriCommand.class, getLastAprioriRequest);
        } catch (Exception e) {
            commandExceptionHelper.printMessage("Error while running GetLastAprioriCommand...");
        }

        return getLastAprioriResponse.getLastApriori();
    }

    private GetConfidentApriorisRequest createGetConfidentApriorisRequest(List<Apriori> firstAprioris,
                                                                          Apriori lastApriori,
                                                                          Double minimumConfidence) {
        return GetConfidentApriorisRequest.builder()
                .firstAprioris(firstAprioris)
                .lastApriori(lastApriori)
                .minimumConfidence(minimumConfidence)
                .build();
    }

    private List<Apriori> getConfidenceAprioris(GetConfidentApriorisRequest getConfidentApriorisRequest,
                                                CommandExecutor commandExecutor,
                                                CommandExceptionHelper commandExceptionHelper) {
        GetConfidentApriorisResponse getConfidentApriorisResponse = GetConfidentApriorisResponse.builder()
                .build();

        try {
            getConfidentApriorisResponse = commandExecutor.doExecute(
                    GetConfidentApriorisCommand.class, getConfidentApriorisRequest);
        } catch (Exception e) {
            commandExceptionHelper.printMessage("Error while running GetConfidentApriorisCommand...");
        }

        return getConfidentApriorisResponse.getConfidentAprioris();
    }
}
