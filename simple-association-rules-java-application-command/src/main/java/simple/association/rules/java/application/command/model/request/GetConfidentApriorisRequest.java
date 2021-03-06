package simple.association.rules.java.application.command.model.request;

import lombok.Builder;
import lombok.Data;
import simple.association.rules.java.application.model.Apriori;

import java.util.List;

/**
 * @author Faza Zulfika P P
 * @version 1.0.0
 * @since 20 May 2018
 */

@Data
@Builder
public class GetConfidentApriorisRequest {

    private List<Apriori> firstAprioris;

    private Apriori lastApriori;

    private Double minimumConfidence;
}
