package simple.association.rules.java.application.command.util;

import org.junit.Before;
import org.junit.Test;
import simple.association.rules.java.application.model.Apriori;
import simple.association.rules.java.application.model.DataSet;
import simple.association.rules.java.application.model.Label;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.junit.Assert.*;

/**
 * @author Faza Zulfika P P
 * @version 1.0.0
 * @since 20 May 2018
 */

public class AprioriHelperTest {

    private AprioriHelper aprioriHelper;

    @Before
    public void setUp() {
        aprioriHelper = AprioriHelper.getInstance();
    }

    @Test
    public void createApriori_success() {
        List<Integer> labels = Arrays.asList(1, 2, 3, 4, 5);

        Apriori expectedApriori = Apriori.builder()
                .label(Label.builder()
                        .labels(labels)
                        .build())
                .build();

        Apriori apriori = aprioriHelper.createApriori(labels);

        assertNotNull(apriori);
        assertEquals(expectedApriori, apriori);
    }

    @Test
    public void createApriori_success_emptyLabels() {
        List<Integer> labels = Collections.emptyList();

        Apriori expectedApriori = Apriori.builder()
                .label(Label.builder()
                        .labels(labels)
                        .build())
                .build();

        Apriori apriori = aprioriHelper.createApriori(labels);

        assertNotNull(apriori);
        assertEquals(expectedApriori, apriori);
    }

    @Test
    public void createApriori_success_nullLabels() {
        Apriori expectedApriori = Apriori.builder()
                .label(Label.builder()
                        .build())
                .build();

        Apriori apriori = aprioriHelper.createApriori(null);

        assertNotNull(apriori);
        assertEquals(expectedApriori, apriori);
    }

    @Test
    public void calculateSupport_success() {
        List<DataSet> dataSets = createDataSets();
        Apriori apriori = createApriori();

        aprioriHelper.calculateSupport(apriori, dataSets);

        assertEquals(Double.valueOf(1D), apriori.getSupport());
    }

    @Test
    public void calculateSupport_success_emptyDataSets() {
        List<DataSet> dataSets = Collections.emptyList();
        Apriori apriori = createApriori();

        aprioriHelper.calculateSupport(apriori, dataSets);

        assertEquals(Double.NaN, apriori.getSupport(), 0);
    }

    @Test
    public void calculateSupport_success_emptyLabels() {
        List<DataSet> dataSets = createDataSets();
        Apriori apriori = createApriori();
        apriori.getLabel().setLabels(Collections.emptyList());

        aprioriHelper.calculateSupport(apriori, dataSets);

        assertEquals(Double.valueOf(1D), apriori.getSupport());
    }

    @Test(expected = NullPointerException.class)
    public void calculateSupport_failed_dataSetsIsNull() {
        Apriori apriori = createApriori();
        apriori.getLabel().setLabels(Collections.emptyList());

        aprioriHelper.calculateSupport(apriori, null);
    }

    @Test(expected = NullPointerException.class)
    public void calculateSupport_failed_labelsIsNull() {
        List<DataSet> dataSets = createDataSets();
        Apriori apriori = createApriori();
        apriori.getLabel().setLabels(null);

        aprioriHelper.calculateSupport(apriori, dataSets);
    }

    @Test(expected = NullPointerException.class)
    public void calculateSupport_failed_labelIsNull() {
        List<DataSet> dataSets = createDataSets();
        Apriori apriori = createApriori();
        apriori.setLabel(null);

        aprioriHelper.calculateSupport(apriori, dataSets);
    }

    @Test(expected = NullPointerException.class)
    public void calculateSupport_failed_aprioriIsNull() {
        List<DataSet> dataSets = createDataSets();

        aprioriHelper.calculateSupport(null, dataSets);
    }

    @Test
    public void checkSupport_success_true() {
        Apriori apriori = createApriori();
        apriori.setSupport(0.6);

        assertTrue(aprioriHelper.checkSupport(apriori, 0.5));
    }

    @Test
    public void checkSupport_success_false() {
        Apriori apriori = createApriori();
        apriori.setSupport(0.6);

        assertFalse(aprioriHelper.checkSupport(apriori, 0.8));
    }

    @Test(expected = NullPointerException.class)
    public void checkSupport_failed_aprioriSupportIsNull() {
        Apriori apriori = createApriori();
        aprioriHelper.checkSupport(apriori, 0.5);
    }

    @Test(expected = NullPointerException.class)
    public void checkSupport_failed_minimumSupportIsNull() {
        Apriori apriori = createApriori();
        apriori.setSupport(0.6);

        aprioriHelper.checkSupport(apriori, null);
    }

    @Test(expected = NullPointerException.class)
    public void checkSupport_failed_aprioriIsNull() {
        aprioriHelper.checkSupport(null, 0.5);
    }

    private List<DataSet> createDataSets() {
        List<DataSet> dataSets = new ArrayList<>();

        dataSets.add(createDataSet1());
        dataSets.add(createDataSet2());

        return dataSets;
    }

    private DataSet createDataSet1() {
        List<Integer> labels = Arrays.asList(2, 4, 5);

        return DataSet.builder()
                .id(1)
                .label(Label.builder()
                        .labels(labels)
                        .build())
                .build();
    }

    private DataSet createDataSet2() {
        List<Integer> labels = Arrays.asList(2, 5);

        return DataSet.builder()
                .id(2)
                .label(Label.builder()
                        .labels(labels)
                        .build())
                .build();
    }

    private Apriori createApriori() {
        List<Integer> labels = Arrays.asList(2, 5);

        return Apriori.builder()
                .label(Label.builder()
                        .labels(labels)
                        .build())
                .build();
    }
}