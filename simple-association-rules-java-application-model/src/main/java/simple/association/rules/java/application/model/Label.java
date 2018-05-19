package simple.association.rules.java.application.model;

import lombok.Builder;
import lombok.Data;

import java.util.List;

/**
 * @author Faza Zulfika P P
 * @version 1.0.0
 * @since 19 May 2018
 */

@Data
@Builder
public class Label {

    private List<Integer> labels;
}
