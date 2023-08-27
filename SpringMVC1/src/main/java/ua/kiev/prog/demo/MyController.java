package ua.kiev.prog.demo;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Controller
public class MyController {

    static final Map<String, String> accounts = new ConcurrentHashMap<>();

    static {
        accounts.put("user1", "password1");
        accounts.put("user2", "password2");
    }

    @GetMapping("/")
    public String onIndex() {
        return "index";
    }

    /*

    POST /login
    ....
    login=xxx&password=yyy

     */

    // MVC: V -> C -> M -> V

    @PostMapping("/login")
    public String onLogin(Model model,
                          @RequestParam String login,
                          @RequestParam String password) {
        String pass = accounts.get(login);

        model.addAttribute("login", login);
        model.addAttribute("message", password.equals(pass) ? "Success" : "Failure");

        return "result";
    }
}
