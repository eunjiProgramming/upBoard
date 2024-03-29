package com.upboard.app.controller;


import com.upboard.app.domain.CommentDto;
import com.upboard.app.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.List;

@RestController
public class CommentController {

    @Autowired
    CommentService service;

    // 댓글을 수정하는 메서드
    @PatchMapping("/comments/{cno}") // /upboard/comments?bno=1085 PATCH
    public ResponseEntity<String> modify(@PathVariable Integer cno, @RequestBody CommentDto dto, HttpSession session) {
        // String commenter = (String)session.getAttribute("id");

        String commenter = "asdf";
        dto.setCommenter(commenter);

        dto.setCno(cno);
        System.out.println("dto = " + dto);

        try {
            if(service.modify(dto) != 1)
                throw new Exception("Write failed");

            return new ResponseEntity<>("MOD_OK", HttpStatus.OK);

        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<String>("MOD_ERR", HttpStatus.BAD_REQUEST);
        }
    }

    // 댓글을 등록하는 메서드
    @PostMapping("/comments") // /upboard/comments?bno=1085 POST
    public ResponseEntity<String> write(@RequestBody CommentDto dto, Integer bno, HttpSession session) {
        // String commenter = (String)session.getAttribute("id");
        String commenter = "asdf";
        dto.setCommenter(commenter);
        dto.setBno(bno);
        System.out.println("dto = " + dto);

        try {
            if(service.write(dto) != 1)
                throw new Exception("Write failed");

            return new ResponseEntity<String>("WRT_OK", HttpStatus.OK);

        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<String>("WRT_ERR", HttpStatus.BAD_REQUEST);
        }
    }

    // 지정된 댓글을 삭제하는 메서드
    @DeleteMapping("/comments/{cno}") // DELETE /comments/1 <-- 삭제할 댓글 번호
    public ResponseEntity<String> remove(@PathVariable Integer cno, Integer bno, HttpSession session) {
        // String commenter = (String)session.getAttribute("id");
        String commenter= "asdf";
        try {
            int rowCnt = service.remove(cno, bno, commenter);

            if(rowCnt!=1)
                throw new Exception("Delete Failed");

            return new ResponseEntity<String>("DEL_OK", HttpStatus.OK);

        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<String>("DEL_ERR", HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/comments") // /comments?bno=1080
    public ResponseEntity<List<CommentDto>> list(Integer bno) {
        List<CommentDto> list = null;
        try {
            list = service.getList(bno);
            return new ResponseEntity<List<CommentDto>>(list, HttpStatus.OK); // 200
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<List<CommentDto>>(HttpStatus.BAD_REQUEST); // 400
        }
    }
}
