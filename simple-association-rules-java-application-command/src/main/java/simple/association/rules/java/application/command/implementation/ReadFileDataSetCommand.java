package simple.association.rules.java.application.command.implementation;

import simple.association.rules.java.application.command.Command;
import simple.association.rules.java.application.command.model.request.ReadFileDataSetRequest;
import simple.association.rules.java.application.command.model.response.ReadFileDataSetResponse;

import java.io.*;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author Faza Zulfika P P
 * @version 1.0.0
 * @since 19 May 2018
 */

public class ReadFileDataSetCommand implements Command<ReadFileDataSetRequest, ReadFileDataSetResponse> {

    @Override
    public ReadFileDataSetResponse execute(ReadFileDataSetRequest readFileDataSetRequest)
            throws Exception {
        InputStream inputStream = getFileInputStream(readFileDataSetRequest.getPathname());
        List<String> lines = readFileLines(inputStream);

        return ReadFileDataSetResponse.builder()
                .lines(lines)
                .build();
    }

    private InputStream getFileInputStream(String pathname) throws FileNotFoundException {
        return new FileInputStream(pathname);
    }

    private List<String> readFileLines(InputStream inputStream) {
        InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
        BufferedReader bufferedReader = new BufferedReader(inputStreamReader);

        return bufferedReader.lines()
                .collect(Collectors.toList());
    }
}
