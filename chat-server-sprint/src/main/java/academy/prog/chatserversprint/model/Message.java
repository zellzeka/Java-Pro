package academy.prog.chatserversprint.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.Base64;
import java.util.Date;
import java.util.List;

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

    @ElementCollection
    private List <String> fileName = new ArrayList<>();

    @ElementCollection
    private List<byte[]> fileData = new ArrayList<>();

    public static Message fromDTO(MessageDTO dto) {
        var result = new Message();

        result.setId(dto.getId());
        result.setTo(dto.getTo());
        result.setDate(dto.getDate());
        result.setFrom(dto.getFrom());
        result.setText(dto.getText());
        result.setFileName(dto.getFileName());
        List<String> fileDataDTO = dto.getFileData();

        for (String data : fileDataDTO) {
            if(data != null){
                byte[] binaryData = Base64.getDecoder().decode(data);
                result.getFileData().add(binaryData);
            } else {
                result.getFileData().add(null);
            }
        }

        return result;
    }

    public MessageDTO toDTO() {
        var result = new MessageDTO();

        result.setId(id);
        result.setTo(to);
        result.setDate(date);
        result.setFrom(from);
        result.setText(text);
        encodeData(result);
        System.out.println(result);
        return result;
    }

    public FileDTO toFileDTO() {
        var result = new FileDTO();
        encodeData(result);
        return result;
    }

    public void encodeData(MessageDTO dto){
        dto.setFileName(fileName);

        for (byte[] data : fileData) {
            if(data != null){
                String encodedData = Base64.getEncoder().encodeToString(data);
                dto.getFileData().add(encodedData);
            } else{
                dto.getFileData().add(null);
            }
        }
    }
}
