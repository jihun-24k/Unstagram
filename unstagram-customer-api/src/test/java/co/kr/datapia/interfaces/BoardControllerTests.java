package co.kr.datapia.interfaces;

import co.kr.datapia.application.BoardService;
import co.kr.datapia.domain.Board;
import co.kr.datapia.domain.BoardNotFoundException;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.core.StringContains.containsString;
import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(SpringRunner.class)
@WebMvcTest(BoardController.class)
public class BoardControllerTests {

    @Autowired
    private MockMvc mvc;

    @MockBean
    private BoardService boardService;

    @Test
    public void list() throws Exception {
        List<Board> boards = new ArrayList<>();
        boards.add(Board.builder()
                .boardId(1004L)
                .content("Hello world")
                .time("Tue Jan 19 2021 17:06:30 GMT+0900")
                .build());

        given(boardService.getBoards()).willReturn(boards);

        mvc.perform(get("/board"))
                .andExpect(status().isOk())
                .andExpect(content().string(
                        containsString("\"boardId\":1004")
                ))
                .andExpect(content().string(
                        containsString("\"content\":\"Hello world\"")
                ));
    }
    @Test
    public void detailWithExisted() throws Exception {

        Board board = Board.builder()
                .boardId(1004L)
                .content("Hello world")
                .time("Tue Jan 19 2021 17:06:30 GMT+0900")
                .build();

        given(boardService.getBoard(1004L)).willReturn(board);

        mvc.perform(get("/board/1004"))
                .andExpect(status().isOk())
                .andExpect(content().string(
                        containsString("\"boardId\":1004")
                ))
                .andExpect(content().string(
                        containsString("\"content\":\"Hello world\"")
                ));
    }
    @Test
    public void detailWithNotExisted() throws Exception{
        given(boardService.getBoard(404L))
                .willThrow(new BoardNotFoundException(404L));

        mvc.perform(get("/board/404"))
                .andExpect(status().isNotFound());
    }

    @Test
    public void createWithValidData() throws Exception{
        given(boardService.addBoard(any())).will(invocation -> {
            Board board = invocation.getArgument(0);
            return Board.builder()
                    .boardId(1234L)
                    .content(board.getContent())
                    .time(board.getTime())
                    .build();

        });

        mvc.perform(post("/board")
                .contentType(MediaType.APPLICATION_JSON)
                .content(" {\"content\":\"Nice to meet you\",\"time\":\"Tue Jan 26 2021 17:06:30 GMT+0900\"}"))
                .andExpect(status().isCreated())
                .andExpect(header().string("location","/board/1234"))
                .andExpect((content().string("{}")));

        verify(boardService).addBoard(any());
    }
    @Test
    public void createWithInvalidData() throws Exception{
        mvc.perform(post("/board")
                .contentType(MediaType.APPLICATION_JSON)
                .content(" {\"name\":\"\",\"address\":\"\"}"))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void updateWithValidData() throws Exception {
        mvc.perform(patch("/board/1004")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"content\":\"Nice to meet you\",\"time\":\"Tue Jan 26 2021 17:06:30 GMT+0900\"}"))
                .andExpect(status().isOk());

        verify(boardService).updateBoard(1004L,"Nice to meet you","Tue Jan 26 2021 17:06:30 GMT+0900");
    }
    @Test
    public void updateWithInvalidData() throws Exception {
        mvc.perform(patch("/board/1004")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"content\":\"Nice to meet you\",\"time\":\"Wed Jan 27 2021 17:06:30 GMT+0900\"}"))
                .andExpect(status().isOk());
    }

}