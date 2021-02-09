package co.kr.datapia.domain;

import org.junit.Test;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;
import static org.junit.jupiter.api.Assertions.*;

public class BoardTests {

    @Test
    public void creation(){
        Board board = Board.builder()
                .boardId(1004L)
                .content("Hello world")
                .time("Wed Jan 20 2021 17:00:00 GMT+0900")
                .build();

        assertThat(board.getBoardId(), is("Ji Hun"));
        assertThat(board.getContent(), is("Hello world"));
        assertThat(board.getTime(), is("Wed Jan 20 2021 17:00:00 GMT+0900"));

    }

}