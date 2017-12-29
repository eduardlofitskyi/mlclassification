package com.lofitskyi.ml.classification;

import com.google.common.base.Preconditions;
import com.lofitskyi.ml.classification.pojo.DictionaryVector;
import com.lofitskyi.ml.classification.util.CsvReaderUtils;
import com.lofitskyi.ml.classification.util.StatisticUtils;
import lombok.val;

import java.io.IOException;

public class MlClassificationApplication {
    public static void main(String[] args) throws IOException {

        Preconditions.checkArgument(args.length == 1);

        val pathToDataSet = args[0];

        val jobAds = CsvReaderUtils.readJobAdFromCsv(pathToDataSet);

        val medianSalary = StatisticUtils.getMedianSalary(jobAds);

        val classifiedBySalary =
                StatisticUtils.getClassifiedListBySalary(jobAds, medianSalary);

        DictionaryVector dictionaryLow = new DictionaryVector();
        DictionaryVector dictionaryHigh = new DictionaryVector();


    }

}
