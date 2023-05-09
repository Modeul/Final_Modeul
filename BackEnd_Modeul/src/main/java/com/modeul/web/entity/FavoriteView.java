package com.modeul.web.entity;

import java.time.LocalDateTime;

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
public class FavoriteView {
    
    private Integer id;
    private Long memberId;
    private Long StuffId;
    private String stuffTitle;
    private String stuffDeadline;
    private String categoryId;
    private String stuffPlace;
    private LocalDateTime favoriteDate;
    private String imageName;


}
