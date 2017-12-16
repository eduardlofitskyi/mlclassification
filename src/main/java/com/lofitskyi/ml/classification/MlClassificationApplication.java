package com.lofitskyi.ml.classification;

import com.google.common.base.Preconditions;
import com.lofitskyi.ml.classification.pojo.JobAd;
import com.lofitskyi.ml.classification.util.CsvReaderUtils;

import java.io.IOException;
import java.util.List;

public class MlClassificationApplication {
    public static void main(String[] args) throws IOException {

        Preconditions.checkArgument(args.length == 1);

        String pathToDataSet = args[0];

        List<JobAd> jobAds = CsvReaderUtils.readJobAdFromCsv(pathToDataSet);

        jobAds.forEach(System.out::println);
    }
}
