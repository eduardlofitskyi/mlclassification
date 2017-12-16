package com.lofitskyi.ml.classification.util;

import com.lofitskyi.ml.classification.pojo.JobAd;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVRecord;

import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.util.LinkedList;
import java.util.List;

public class CsvReaderUtils {

    private CsvReaderUtils() {
        throw new UnsupportedOperationException("Cannot instantiate utility class");
    }

    public static List<JobAd> readJobAdFromCsv(String filePath) {
        try (Reader in = new FileReader(filePath)) {
            List<JobAd> jobList = new LinkedList<>();
            Iterable<CSVRecord> records = CSVFormat.DEFAULT.withFirstRecordAsHeader().parse(in);

            for (CSVRecord record : records) {
                    jobList.add(new JobAd(
                            Long.valueOf(record.get(0)),
                            record.get(1),
                            record.get(2),
                            record.get(3),
                            record.get(4),
                            record.get(5),
                            record.get(6),
                            record.get(7),
                            record.get(8),
                            record.get(9),
                            Long.valueOf(record.get(10)),
                            record.get(11)));
            }

            return jobList;

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
