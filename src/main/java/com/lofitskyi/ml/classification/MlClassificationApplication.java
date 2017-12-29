package com.lofitskyi.ml.classification;

import com.google.common.base.Preconditions;
import com.lofitskyi.ml.classification.pojo.DictionaryVector;
import com.lofitskyi.ml.classification.pojo.MedianSide;
import com.lofitskyi.ml.classification.util.CsvReaderUtils;
import com.lofitskyi.ml.classification.util.StatisticUtils;
import de.daslaboratorium.machinelearning.classifier.Classifier;
import de.daslaboratorium.machinelearning.classifier.bayes.BayesClassifier;
import lombok.val;

import java.io.IOException;

import static com.lofitskyi.ml.classification.pojo.MedianSide.*;

public class MlClassificationApplication {
    public static void main(String[] args) throws IOException {

        Preconditions.checkArgument(args.length == 1);

        val pathToDataSet = args[0];

        val jobAds = CsvReaderUtils.readJobAdFromCsv(pathToDataSet);

        val medianSalary = StatisticUtils.getMedianSalary(jobAds);

        val baseClassificationBySalary =
                StatisticUtils.getClassifiedListBySalary(jobAds, medianSalary);

        val dictionaryLow = StatisticUtils
                .countDictionaryVector(baseClassificationBySalary.get(LOWER_SIDE));

        val dictionaryHigh = StatisticUtils
                .countDictionaryVector(baseClassificationBySalary.get(HIGHER_SIDE));

        Classifier<String, String> bayes = new BayesClassifier<>();

        bayes.learn(LOWER_SIDE.getValue(), dictionaryLow.getDictionary());
        bayes.learn(HIGHER_SIDE.getValue(), dictionaryHigh.getDictionary());

        
    }

}
