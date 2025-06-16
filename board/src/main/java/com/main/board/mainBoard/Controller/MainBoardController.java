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
public class MainBoardController {

    private final MainBoardService mainBoardService;

    @GetMapping("/")
    public String showBoard(Model model) {
        return "index"; // templates/index.html 열림
    }
}
