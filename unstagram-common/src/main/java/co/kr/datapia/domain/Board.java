package co.kr.datapia.domain;

import lombok.*;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.List;

@Entity
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Board {

    @Id
    @GeneratedValue
    @Setter
    private Long boardId;

    @NotEmpty
    private String content;

    @NotEmpty
    private String time;

    public String getInformation() {
        return content + " in "+ time;
    }

    // 업데이트 해야하는 정보들 (시간, 글 내용)
    public void updateInformation(String content, String time) {
        this.content = content;
        this.time = time;
    }

}
