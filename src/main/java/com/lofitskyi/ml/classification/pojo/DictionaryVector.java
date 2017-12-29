package com.lofitskyi.ml.classification.pojo;

import lombok.ToString;
import org.apache.commons.lang3.StringUtils;

import java.util.LinkedList;
import java.util.List;

import static java.util.Arrays.asList;

@ToString
public class DictionaryVector {

    private List<String> dictionary;

    public DictionaryVector() {
        this.dictionary = new LinkedList<>();
    }

    public void put(String text) {
        String normalizedText = StringUtils.removeAll(text, "\\*");
        String[] words = normalizedText.split(" ");

        this.dictionary.addAll(asList(words));
    }

    public List<String> getDictionary() {
        return dictionary;
    }
}
