package co.kr.datapia.application;

import co.kr.datapia.domain.Board;
import co.kr.datapia.domain.BoardNotFoundException;
import co.kr.datapia.domain.BoardRepository;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;

public class BoardServiceTests {

    private BoardService boardService;

    @Mock
    private BoardRepository boardRepository;

    @Before // 테스트 하기 전에 처음으로 실행되는 구문
    public void setUp(){
        MockitoAnnotations.initMocks(this);
        mockBoardRepository();
        boardService = new BoardService(boardRepository);
    }

    private void mockBoardRepository(){
        List<Board> boards = new ArrayList<>();
        Board board = Board.builder()
                .boardId(1004L)
                .content("Hello world")
                .time("Tue Jan 19 2021 17:06:30 GMT+0900")
                .build();

        boards.add(board);
    }

    @Test
    public void getBoards(){
        List<Board> boards = boardService.getBoards();

        Board board = boards.get(0);
        assertThat(board.getBoardId(), is(1234L));
    }

    @Test
    public void getBoardWithExisted(){
        Board board = boardService.getBoard(1234L);

        assertThat(board.getBoardId(),is(1234L));
    }

    @Test(expected = BoardNotFoundException.class)
    public void getRestaurantWithNotExisted(){
        boardService.getBoard(404L);
    }

    @Test
    public void addRestaurant(){

        given(boardRepository.save(any())).will(invocation -> {
            Board board = invocation.getArgument(0);
            board.setBoardId(1234L);
            return board;
        });

        Board board= Board.builder()
                .content("Hello world")
                .time("Tue Jan 19 2021 17:06:30 GMT+0900")
                .build();

        Board created = boardService.addBoard(board);

        assertThat(created.getBoardId(),is(1234L));


    }
    @Test
    public void updateRestaurant(){

        Board board = Board.builder()
                .boardId(1004L)
                .content("Hello world")
                .time("Tue Jan 19 2021 17:06:30 GMT+0900")
                .build();

        given(boardRepository.findById(1004L)).willReturn(Optional.of(board));

        boardService.updateBoard
                (1004L,"Nice to meet you",
                        "Tue Feb 9 2021 17:06:30 GMT+0900");

        assertThat(board.getContent(),is("Nice to meet you"));
        assertThat(board.getTime(),is("Tue Feb 9 2021 17:06:30 GMT+0900"));
    }
}