package com.lofitskyi.ml.classification.pojo;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@EqualsAndHashCode
public class JobAd {

    private Long id;
    private String title;
    private String fullDescription;
    private String locationRaw;
    private String locationNormalized;
    private String contractType;
    private String contractTime;
    private String company;
    private String category;
    private String salaryRaw;
    private String salaryNormalised;
    private String sourceName;

}
