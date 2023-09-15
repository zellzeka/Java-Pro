package academy.prog.chatserversprint.service;

import academy.prog.chatserversprint.model.FileDTO;
import academy.prog.chatserversprint.model.Message;
import academy.prog.chatserversprint.model.MessageDTO;
import academy.prog.chatserversprint.repo.MessageRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Base64;
import java.util.List;
import java.util.Optional;

@Service
public class MessageService {

    private final MessageRepository messageRepository;

    public MessageService(MessageRepository messageRepository) {
        this.messageRepository = messageRepository;
    }

    @Transactional
    public void add(MessageDTO messageDTO) {
        var message = Message.fromDTO(messageDTO);
        messageRepository.save(message);
    }

    @Transactional(readOnly = true)
    public List<MessageDTO> get(long id, String to) {
        var messages = messageRepository.findNew(id, to);
        var result = new ArrayList<MessageDTO>();

        messages.forEach(message -> result.add(message.toDTO()));
        return result;
    }

    @Transactional
    public Optional <FileDTO> getFileById(Long id){
        var messageOptional = messageRepository.findById(id);
        if (messageOptional.isPresent()) {
            Message message = messageOptional.get();
            FileDTO file = new FileDTO();
            file.setFileName(message.getFileName());
            String data = Base64.getEncoder().encodeToString(message.getFileData());
            file.setFileData(data);

            return Optional.of(file);
        } else return Optional.empty();
    }
}
