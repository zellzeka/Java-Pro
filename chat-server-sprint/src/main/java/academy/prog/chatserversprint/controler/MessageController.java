package academy.prog.chatserversprint.controler;

import academy.prog.chatserversprint.model.FileDTO;
import academy.prog.chatserversprint.model.MessageDTO;
import academy.prog.chatserversprint.service.MessageService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;


@RestController
public class MessageController {
    private final MessageService messageService;

    public MessageController(MessageService messageService) {
        this.messageService = messageService;
    }

    @PostMapping("add")
    public ResponseEntity<Void> add(@RequestBody MessageDTO messageDTO) {
        messageService.add(messageDTO);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping("get")
    public List<MessageDTO> get(
            @RequestParam(required = false, defaultValue = "0") Long fromId,
            @RequestParam(required = false) String from)
    {
        return messageService.get(fromId, from);
    }

    @GetMapping("file")
    public ResponseEntity<FileDTO> file(@RequestParam Long messageId) {
        Optional<FileDTO> fileOptional = messageService.getFileById(messageId);
        if (fileOptional.isPresent()){
            FileDTO file = fileOptional.get();
            return ResponseEntity.ok(file);
        } else return ResponseEntity.notFound().build();
    }

    @GetMapping("test")
    public String test() {
        return "It works!";
    }
}
