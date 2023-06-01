package com.modeul.web.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@ToString
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ParticipationView extends Participation {
    private String stuffTitle;
    private String stuffPlace;
    private String stuffNumPeople;
    private String stuffDeadline;
    private String stuffPrice;
    private Long stuffMemberId;
    private Long categoryId;
    private String categoryName;
    private String imageName;
    private int participantCount;
}
