package academy.prog.chatserversprint.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Data
public class MessageDTO {
    private Long id;

    private String to;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern="yyyy-MM-dd HH:mm:ss", timezone = "UTC")
    private Date date;

    private String from;

    private String text;

    private List<String> fileName = new ArrayList<>();

    private List<String> fileData = new ArrayList<>();
}
