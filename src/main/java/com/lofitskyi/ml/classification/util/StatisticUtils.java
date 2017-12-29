package com.lofitskyi.ml.classification.util;

import com.lofitskyi.ml.classification.pojo.DictionaryVector;
import com.lofitskyi.ml.classification.pojo.JobAd;
import com.lofitskyi.ml.classification.pojo.MedianSide;

import java.util.*;

import static com.lofitskyi.ml.classification.pojo.MedianSide.HIGHER_SIDE;
import static com.lofitskyi.ml.classification.pojo.MedianSide.LOWER_SIDE;

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
        Map<MedianSide, List<JobAd>> classifiedBySalary = new HashMap<>();
        classifiedBySalary.put(LOWER_SIDE, new LinkedList<>());
        classifiedBySalary.put(HIGHER_SIDE, new LinkedList<>());

        for (JobAd job : jobAds) {
            if (job.getSalaryNormalised() > medianSalary) {
                classifiedBySalary.get(HIGHER_SIDE).add(job);
            } else {
                classifiedBySalary.get(LOWER_SIDE).add(job);
            }
        }
        return classifiedBySalary;
    }

    public static DictionaryVector countDictionaryVector(List<JobAd> jobs){
        return null;
    }
}
