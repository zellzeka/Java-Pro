package academy.prog.chatserversprint.model;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class FileDTO extends MessageDTO{
    private List<String> fileName = new ArrayList<>();

    private List<String> fileData = new ArrayList<>();

}
