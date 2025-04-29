package com.main.board.mainBoard.Controller;

import com.main.board.mainBoard.DTO.CursorRequest;
import com.main.board.mainBoard.DTO.MainBoardPostResponse;
import com.main.board.mainBoard.service.MainBoardService;
import com.main.board.mainBoard.util.MainBoardConstants;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@RequiredArgsConstructor
@Controller
@RequestMapping("/mainBoard")
public class MainBoardController {

    private final MainBoardService mainBoardService;

    @GetMapping("/main")
    public String showBoard(Model model) {
        //Request안에서 상수화
        List<MainBoardPostResponse> posts = mainBoardService.getMainBoardForCursor(CursorRequest.defaultDesc());
        // 상수클래스로 관리
        // List<MainBoardPostResponse> posts2 = mainBoardService.getMainBoardForCursor(MainBoardConstants.DEFAULT_DESC);
        model.addAttribute("posts", posts);
        return "index"; // templates/index.html 열림
    }
}
