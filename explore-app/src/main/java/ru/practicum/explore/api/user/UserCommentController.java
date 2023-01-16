package ru.practicum.explore.api.user;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import ru.practicum.explore.commons.dto.CommentDto;
import ru.practicum.explore.commons.dto.NewCommentDto;
import ru.practicum.explore.service.CommentService;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/users/{userId}/events/{eventId}/comments")
public class UserCommentController {

    private final CommentService commentService;

    @GetMapping
    public List<CommentDto> getComments(@PathVariable long eventId,
                                        @RequestParam(required = false, defaultValue = "0") int from,
                                        @RequestParam(required = false, defaultValue = "10") int size) {
        return commentService.getComments(eventId, from, size);
    }

    @GetMapping(path = "{commentId}")
    public CommentDto getComment(@PathVariable long eventId,
                                 @PathVariable long commentId) {
        return commentService.getComment(eventId, commentId);
    }

    @PostMapping
    public CommentDto addComment(@PathVariable long userId,
                                 @PathVariable long eventId,
                                 @Valid @RequestBody NewCommentDto commentDto) {
        return commentService.addComment(userId, eventId, commentDto);
    }

    @DeleteMapping(path = "{commentId}")
    public String deleteComment(@PathVariable long userId,
                                @PathVariable long eventId,
                                @PathVariable long commentId) {
        commentService.deleteComment(userId, eventId, commentId);
        return "Комментарий удален";
    }

    @PatchMapping(path = "{commentId}")
    public CommentDto updateComment(@PathVariable long userId,
                                    @PathVariable long eventId,
                                    @PathVariable long commentId,
                                    @Valid @RequestBody NewCommentDto comment) {
        return commentService.updateComment(userId, eventId, commentId, comment);
    }

}
