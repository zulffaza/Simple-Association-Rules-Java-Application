package simple.association.rules.java.application.command.model.response;

import lombok.Builder;
import lombok.Data;
import simple.association.rules.java.application.model.Apriori;

/**
 * @author Faza Zulfika P P
 * @version 1.0.0
 * @since 20 May 2018
 */

@Data
@Builder
public class GetLastAprioriResponse {

    private Apriori lastApriori;
}
