package com.lofitskyi.ml.classification.util;

import com.lofitskyi.ml.classification.pojo.DictionaryVector;
import com.lofitskyi.ml.classification.pojo.JobAd;
import com.lofitskyi.ml.classification.pojo.MedianSide;
import de.daslaboratorium.machinelearning.classifier.Classifier;
import org.apache.commons.lang3.StringUtils;

import java.util.*;

import static com.lofitskyi.ml.classification.pojo.MedianSide.HIGHER_SIDE;
import static com.lofitskyi.ml.classification.pojo.MedianSide.LOWER_SIDE;
import static java.util.Arrays.asList;

public class StatisticUtils {

    private StatisticUtils() {
        throw new UnsupportedOperationException("Cannot instantiate utility class");
    }

    public static Integer getMedianSalary(List<JobAd> jobs) {
        List<JobAd> sortedJob = new LinkedList<>(jobs);
        sortedJob.sort(Comparator.comparingInt(JobAd::getSalaryNormalised));

        return sortedJob.get(sortedJob.size() / 2).getSalaryNormalised();
    }

    public static Map<MedianSide, List<JobAd>> getClassifiedListBySalary(List<JobAd> jobAds, Integer medianSalary) {
        Map<MedianSide, List<JobAd>> calculatedBySalary = new HashMap<>();
        calculatedBySalary.put(LOWER_SIDE, new LinkedList<>());
        calculatedBySalary.put(HIGHER_SIDE, new LinkedList<>());

        for (JobAd job : jobAds) {
            if (job.getSalaryNormalised() > medianSalary) {
                calculatedBySalary.get(HIGHER_SIDE).add(job);
            } else {
                calculatedBySalary.get(LOWER_SIDE).add(job);
            }
        }
        return calculatedBySalary;
    }

    public static Map<MedianSide, List<JobAd>> classifyListBySalary(
            List<JobAd> jobAds, Classifier<String, MedianSide> classifier) {
        Map<MedianSide, List<JobAd>> classifiedBySalary = new HashMap<>();
        classifiedBySalary.put(LOWER_SIDE, new LinkedList<>());
        classifiedBySalary.put(HIGHER_SIDE, new LinkedList<>());

        for (JobAd job : jobAds) {

            List<String> wordsInDescription = convertToStringList(job.getFullDescription());
            MedianSide category = classifier.classify(wordsInDescription).getCategory();

            switch (category) {
                case LOWER_SIDE: {
                    classifiedBySalary.get(LOWER_SIDE).add(job);
                    break;
                }
                case HIGHER_SIDE: {
                    classifiedBySalary.get(HIGHER_SIDE).add(job);
                    break;
                }
                default: {
                    throw new IllegalArgumentException();
                }
            }
        }
        return classifiedBySalary;
    }

    public static DictionaryVector countDictionaryVector(List<JobAd> jobs){
        DictionaryVector dictionary = new DictionaryVector();
        jobs.forEach(it -> dictionary.put(it.getFullDescription()));

        return dictionary;
    }

    private static List<String> convertToStringList(String text) {
        String normalizedText = StringUtils.removeAll(text, "\\*");
        return asList(normalizedText.split(" "));
    }
}
