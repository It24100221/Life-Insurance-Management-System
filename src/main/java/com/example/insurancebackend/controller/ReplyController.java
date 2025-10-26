package com.example.insurancebackend.controller;



import com.example.insurancebackend.entity.Reply;
import com.example.insurancebackend.service.ReplyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/tickets/{ticketId}/replies")
@CrossOrigin(origins = "*")
public class ReplyController {

    @Autowired
    private ReplyService replyService;

    @PostMapping
    public ResponseEntity<Reply> addReply(@PathVariable Long ticketId, @RequestBody Reply reply) {
        reply.setTicketId(ticketId);
        return ResponseEntity.ok(replyService.addReply(reply));
    }

    @GetMapping
    public ResponseEntity<List<Reply>> getReplies(@PathVariable Long ticketId) {
        return ResponseEntity.ok(replyService.getRepliesByTicketId(ticketId));
    }
}
