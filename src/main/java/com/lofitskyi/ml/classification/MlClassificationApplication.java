package com.lofitskyi.ml.classification;

import com.google.common.base.Preconditions;
import com.lofitskyi.ml.classification.pojo.JobAd;
import com.lofitskyi.ml.classification.pojo.MedianSide;
import com.lofitskyi.ml.classification.util.CsvReaderUtils;
import com.lofitskyi.ml.classification.util.StatisticUtils;
import de.daslaboratorium.machinelearning.classifier.Classifier;
import de.daslaboratorium.machinelearning.classifier.bayes.BayesClassifier;
import lombok.val;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import static com.lofitskyi.ml.classification.pojo.MedianSide.HIGHER_SIDE;
import static com.lofitskyi.ml.classification.pojo.MedianSide.LOWER_SIDE;

public class MlClassificationApplication {
    public static void main(String[] args) throws IOException {

        Preconditions.checkArgument(args.length == 1);

        val pathToDataSet = args[0];

        val jobAds = CsvReaderUtils.readJobAdFromCsv(pathToDataSet);

        val medianSalary = StatisticUtils.getMedianSalary(jobAds);

        val baseCalculationBySalary =
                StatisticUtils.getClassifiedListBySalary(jobAds, medianSalary);

        val dictionaryLow = StatisticUtils
                .countDictionaryVector(baseCalculationBySalary.get(LOWER_SIDE));

        val dictionaryHigh = StatisticUtils
                .countDictionaryVector(baseCalculationBySalary.get(HIGHER_SIDE));

        Classifier<String, MedianSide> bayes = new BayesClassifier<>();

        bayes.learn(LOWER_SIDE, dictionaryLow.getDictionary());
        bayes.learn(HIGHER_SIDE, dictionaryHigh.getDictionary());

        val classificationBySalary = StatisticUtils.classifyListBySalary(jobAds, bayes);

        val baseLowMedianPositionsCount = baseCalculationBySalary.get(LOWER_SIDE).size();
        val baseHighMedianPositionsCount = baseCalculationBySalary.get(HIGHER_SIDE).size();

        val truePositiveLow = getTruePositiveLow(medianSalary, classificationBySalary);
        val truePositiveHigh = getTruePositiveHigh(medianSalary, classificationBySalary);

        val precisionLow =
                calculatePrecision(classificationBySalary, truePositiveLow, LOWER_SIDE);

        val precisionHigh =
                calculatePrecision(classificationBySalary, truePositiveHigh, HIGHER_SIDE);

        System.out.printf("| precision | low: %f | high: %f |\n", precisionLow, precisionHigh);

        val recallLow = calculateRecall(baseLowMedianPositionsCount, truePositiveLow);
        val recallHigh = calculateRecall(baseHighMedianPositionsCount, truePositiveHigh);

        System.out.printf("|    recall | low: %f | high: %f |\n", recallLow, recallHigh);

        val f1Low = calculateF1Score(precisionLow, recallLow);
        val f1High = calculateF1Score(precisionHigh, recallHigh);

        System.out.printf("|  f1-score | low: %f | high: %f |\n", f1Low, f1High);

    }

    private static double calculateF1Score(double precisionLow, double recallLow) {
        return (precisionLow + recallLow) / 2;
    }

    private static double calculateRecall(int baseLowMedianPositionsCount, Long truePositiveLow) {
        return truePositiveLow * 1.0 / baseLowMedianPositionsCount;
    }

    private static double calculatePrecision(Map<MedianSide, List<JobAd>> classificationBySalary, Long truePositiveLow, MedianSide lowerSide) {
        return calculateRecall(classificationBySalary.get(lowerSide).size(), truePositiveLow);
    }

    private static Long getTruePositiveLow(Integer medianSalary, Map<MedianSide, List<JobAd>> classificationBySalary) {
        return classificationBySalary.get(LOWER_SIDE)
                .stream()
                .filter(it -> it.getSalaryNormalised() <= medianSalary)
                .count();
    }

    private static Long getTruePositiveHigh(Integer medianSalary, Map<MedianSide, List<JobAd>> classificationBySalary) {
        return classificationBySalary.get(HIGHER_SIDE)
                .stream()
                .filter(it -> it.getSalaryNormalised() > medianSalary)
                .count();
    }

}
