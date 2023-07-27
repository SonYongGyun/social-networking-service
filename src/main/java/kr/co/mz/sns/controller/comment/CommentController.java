package kr.co.mz.sns.controller.comment;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import kr.co.mz.sns.dto.comment.CommentDto;
import kr.co.mz.sns.service.CommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/auth/comments")
public class CommentController {

    private final CommentService commentService;

    @PostMapping
    public ResponseEntity<String> insert(@Valid @RequestBody CommentDto commentDto) {
        commentService.insert(commentDto);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body("Success!");
    }

    @DeleteMapping("/{commentSeq}")
    public ResponseEntity<String> delete(@NotNull @PathVariable("commentSeq") Long commentSeq) {
        commentService.delete(commentSeq);
        return ResponseEntity
                .ok("Success!");
    }

    @PutMapping("/{commentSeq}")
    public ResponseEntity<String> update(@NotNull @PathVariable("commentSeq") Long commentSeq,
                                         @Valid @RequestBody CommentDto commentDto) {
        commentService.update(commentSeq, commentDto);
        return ResponseEntity
                .ok("Success!");
    }

    @PutMapping("/{commentSeq}/like") // 같은 PutMapping
    public ResponseEntity<String> like(@NotNull @PathVariable("commentSeq") Long commentSeq) {
//        commentService.like(commentSeq);
        return ResponseEntity.ok("Success!");
    }
}
