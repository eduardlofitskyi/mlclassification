package com.lofitskyi.ml.classification.pojo;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
@ToString
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
    private Integer salaryNormalised;
    private String sourceName;

}
