package ru.practicum.explore.api.admin;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import ru.practicum.explore.commons.dto.CommentDto;
import ru.practicum.explore.commons.dto.NewCommentDto;
import ru.practicum.explore.service.CommentService;

import javax.validation.Valid;

@RestController
@RequiredArgsConstructor
@RequestMapping("/admin/comments/{commentId}")
public class AdminCommentController {

    private final CommentService commentService;

    @DeleteMapping
    public String deleteComment(@PathVariable long commentId) {
        commentService.deleteCommentByAdmin(commentId);
        return "Комментарий удален";
    }

    @PatchMapping
    public CommentDto updateComment(@PathVariable long commentId,
                                    @Valid @RequestBody NewCommentDto commentDto) {
        return commentService.updateCommentByAdmin(commentId, commentDto);
    }

}
