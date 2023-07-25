package academy.prog;

import java.util.Calendar;
import java.util.Date;
public class Utils {
    private int delay = 60;

    public Utils() {
    }

    public boolean isUserPresent(Date userDate){
        Date currentDate = new Date();
        Calendar calendar = Calendar.getInstance();

        calendar.setTime(userDate);
        calendar.add(Calendar.SECOND, delay);
        Date compareDate = calendar.getTime();

        return currentDate.before(compareDate);
    }
}
