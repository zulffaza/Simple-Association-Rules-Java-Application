package simple.association.rules.java.application.command.model.request;

import lombok.Builder;
import lombok.Data;

/**
 * @author Faza Zulfika P P
 * @version 1.0.0
 * @since 19 May 2018
 */

@Data
@Builder
public class ReadFileDataSetRequest {

    private String pathname;
}
