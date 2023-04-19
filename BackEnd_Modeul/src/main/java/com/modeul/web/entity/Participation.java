package com.modeul.web.entity;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Participation {
    private Long id;
    private LocalDateTime date;
    private Long memberId;
    private Long stuffId;

    // 참여 insert용
    public Participation(Long memberId, Long stuffId){
        this.memberId = memberId;
        this.stuffId = stuffId;
    }
}
