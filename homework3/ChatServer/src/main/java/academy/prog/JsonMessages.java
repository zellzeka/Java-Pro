package academy.prog;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class JsonMessages {
    private List<Message> list = new ArrayList<>();

    public JsonMessages(List<Message> sourceList, int fromIndex, String receiver,int countOfMessages) {
        for (int i = fromIndex; i < sourceList.size(); i++){
            if (sourceList.get(i).getTo() == null
                    || sourceList.get(i).getTo().equals(receiver)
                    || sourceList.get(i).getFrom().equals(receiver)) {
                Message message = sourceList.get(i);
                message.setCurrentCount(countOfMessages);
                list.add(message);
            }
        }
    }

    public JsonMessages(List<Message> sourceList) {
        Utils utils = new Utils();
        list =  sourceList.stream().filter(o -> utils.isUserPresent(o.getDate()))
                .distinct().collect(Collectors.toList());
    }
}
