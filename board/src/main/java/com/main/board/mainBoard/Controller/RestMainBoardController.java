package com.main.board.mainBoard.Controller;

import com.main.board.mainBoard.DTO.CursorRequest;
import com.main.board.mainBoard.DTO.MainBoardPostResponse;
import com.main.board.mainBoard.DTO.OffsetRequest;
import com.main.board.mainBoard.service.MainBoardService;
import lombok.RequiredArgsConstructor;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/mainBoard")
public class RestMainBoardController {

    private final MainBoardService mainBoardService;

    /*
    @ModelAttribute를 안쓰는 이유는 모든데이터를 받을수있다면 쓰겠지만
    데이터가 무조건 다 들어오지않기떄문에 명시적으로쉽게 볼수있는 장점도있는 @RequestParam을 사용하여
    Request에 매핑을 진행한다

    1. @ModelAttribute는Setter 기반으로 객체 필드명과 일치하는 파라미터를 찾아 바인딩
    2. @RequestParam은 단일값에 사용되며 내부적으로 HttpServletRequest.getParameter("key")로 값을 꺼내고 타입 변환
    3. @RequestBody는 HTTP Body의 JSON 데이터를 Jackson으로 역직렬화해서 객체에 바인딩
     */

    @GetMapping
    public List<MainBoardPostResponse> getMainBoardForOffset(@RequestParam(required = false) Long limit,
                                             @RequestParam(required = false) Long page,
                                             @RequestParam(required = false) String keyword,
                                             @RequestParam(required = false) String sort) {
        OffsetRequest offsetRequest = new OffsetRequest(limit, page, keyword, sort);
        List<MainBoardPostResponse> mainBoardPostResponsesList = mainBoardService.getMainBoardForOffset(offsetRequest);
        return mainBoardPostResponsesList;
    }

    @GetMapping("/offset_MK2")
    public List<MainBoardPostResponse> getMainBoardForOffset_MK2(@RequestParam(required = true) Long limit,
                                                             @RequestParam(required = false) Long page,
                                                             @RequestParam(required = false) String keyword,
                                                             @RequestParam(required = false) String sort) {
        OffsetRequest offsetRequest = new OffsetRequest(limit, page, keyword, sort);
        List<MainBoardPostResponse> mainBoardPostResponsesList = mainBoardService.getMainBoardForOffsetMK2(offsetRequest);
        return mainBoardPostResponsesList;
    }


    @GetMapping("/cursor")
    public List<MainBoardPostResponse> getMainBoardForCursor(@RequestParam Long limit,
                                                             @RequestParam(required = false) Long cursorId,
                                                             @RequestParam(required = false) String keyword,
                                                             @RequestParam(required = false) String sort) {

        CursorRequest cursorRequest = new CursorRequest(limit, cursorId, keyword, sort);
        List<MainBoardPostResponse> mainBoardPostResponsesList = mainBoardService.getMainBoardForCursor(cursorRequest);
        return mainBoardPostResponsesList;
    }
}
