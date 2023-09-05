package academy.prog.chatserversprint.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Entity
@Data
@NoArgsConstructor
public class Message {
    @Id
    @GeneratedValue
    private Long id;

    @Column(name = "msg_to")
    private String to;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "msg_date")
    private Date date;

    @Column(name = "msg_from", nullable = false)
    private String from;

    @Column(name = "msg_text", nullable = false)
    private String text;

    @Column(name = "file_name")
    private String fileName;

    @Column(name = "file_data")
    private String fileData; // Base64(x) -> text

    public static Message fromDTO(MessageDTO dto) {
        var result = new Message();

        result.setId(dto.getId());
        result.setTo(dto.getTo());
        result.setDate(dto.getDate());
        result.setFrom(dto.getFrom());
        result.setText(dto.getText());
        result.setFileName(dto.getFileName());
        result.setFileData(dto.getFileData());

        return result;
    }

    public MessageDTO toDTO() {
        var result = new MessageDTO();

        result.setId(id);
        result.setTo(to);
        result.setDate(date);
        result.setFrom(from);
        result.setText(text);
        result.setFileName(fileName);
        result.setFileData(fileData);

        return result;
    }
}
