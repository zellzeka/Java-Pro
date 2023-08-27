package ua.kiev.prog;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;

@Controller
public class MyController {
    static final int DEFAULT_GROUP_ID = -1;
    static final int ITEMS_PER_PAGE = 6;

    private final ContactService contactService;

    private CsvService csvService;

    public MyController(ContactService contactService) {
        this.contactService = contactService;
    }

    @GetMapping("/")
    public String index(Model model,
                        @RequestParam(required = false, defaultValue = "0") Integer page) {
        if (page < 0) page = 0;

        List<Contact> contacts = contactService
                .findAll(PageRequest.of(page, ITEMS_PER_PAGE, Sort.Direction.DESC, "id"));

        model.addAttribute("groups", contactService.findGroups());
        model.addAttribute("contacts", contacts);
        model.addAttribute("allPages", getPageCount());

        return "index";
    }

    @GetMapping("/reset")
    public String reset() {
        contactService.reset();
        return "redirect:/";
    }

    @GetMapping("/contact_add_page")
    public String contactAddPage(Model model) {
        model.addAttribute("groups", contactService.findGroups());
        return "contact_add_page";
    }

    @GetMapping("/group_add_page")
    public String groupAddPage() {
        return "group_add_page";
    }



    @GetMapping("/group/{id}")
    public String listGroup(
            @PathVariable(value = "id") long groupId,
            @RequestParam(required = false, defaultValue = "0") Integer page,
            Model model)
    {
        Group group = (groupId != DEFAULT_GROUP_ID) ? contactService.findGroup(groupId) : null;
        if (page < 0) page = 0;

        List<Contact> contacts = contactService
                .findByGroup(group, PageRequest.of(page, ITEMS_PER_PAGE, Sort.Direction.DESC, "id"));

        model.addAttribute("groups", contactService.findGroups());
        model.addAttribute("contacts", contacts);
        model.addAttribute("byGroupPages", getPageCount(group));
        model.addAttribute("groupId", groupId);

        return "index";
    }

    @PostMapping(value = "/search")
    public String search(
            @RequestParam String pattern, Model model) {

        List<Contact> contacts = contactService
                .findByPattern(pattern, PageRequest.of(0, ITEMS_PER_PAGE, Sort.Direction.ASC, "id"));

        model.addAttribute("groups", contactService.findGroups());
        model.addAttribute("contacts",  contacts);
        model.addAttribute("pattern",  pattern);
        model.addAttribute("byPatternPages", getPageCount(pattern));

        return "index";
    }
// Завдання 1 Pagination для search
    @GetMapping(value = "/search")
    public String search(Model model,
                         @RequestParam(required = false, defaultValue = "0") Integer page,
                         @RequestParam String pattern) {
        if (page < 0) page = 0;

        List<Contact> contacts = contactService
                .findByPattern(pattern, PageRequest.of(page, ITEMS_PER_PAGE, Sort.Direction.ASC, "id"));

        model.addAttribute("groups", contactService.findGroups());
        model.addAttribute("contacts", contacts);
        model.addAttribute("pattern",  pattern);
        model.addAttribute("byPatternPages", getPageCount(pattern));

        return "index";
    }

    @PostMapping(value = "/contact/delete")
    public ResponseEntity<Void> delete(
            @RequestParam(value = "toDelete[]", required = false) long[] toDelete) {
        if (toDelete != null && toDelete.length > 0)
            contactService.deleteContacts(toDelete);

        return new ResponseEntity<>(HttpStatus.OK);
    }
// Завдання 2 Вивантаження контактів зі сторінки у CSV файл
    @PostMapping(value = "/write")
    public ResponseEntity<Void> writeCSV(
            @RequestParam(value = "toWrite[]", required = false) List<Long> toWrite) {
        if (toWrite != null && toWrite.size()> 0) {
            try {
                List<Contact> contactsList = contactService.findById(toWrite);
                csvService.writeToCsv(contactsList, "saved_contacts.csv");
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
        return new ResponseEntity<>(HttpStatus.OK);
    }
//Завдання 3  Завантаження довільної кількості контактів з CSV файлу)
    @GetMapping(value = "/read")
    public String addFromCSV(){
        try {
            List<String[]> dataList = CsvService.readFromCsv("saved_contacts.csv");
            for (String[] contactData: dataList) {
                long groupId = Long.parseLong(contactData[4].trim());
                Group group = (groupId != DEFAULT_GROUP_ID) ? contactService.findGroup(groupId) : null;
                String name = contactData[0];
                String surname = contactData[1];
                String phone = contactData[2];
                String email = contactData[3];

                Contact contact = new Contact(group, name, surname, phone, email);
                contactService.addContact(contact);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return "redirect:/";
    }

    @PostMapping(value="/contact/add")
    public String contactAdd(@RequestParam(value = "group") long groupId,
                             @RequestParam String name,
                             @RequestParam String surname,
                             @RequestParam String phone,
                             @RequestParam String email)
    {
        Group group = (groupId != DEFAULT_GROUP_ID) ? contactService.findGroup(groupId) : null;

        Contact contact = new Contact(group, name, surname, phone, email);
        contactService.addContact(contact);

        return "redirect:/";
    }

    @PostMapping(value="/group/add")
    public String groupAdd(@RequestParam String name) {
        contactService.addGroup(new Group(name));
        return "redirect:/";
    }

    private long getPageCount() {
        long totalCount = contactService.count();
        return (totalCount / ITEMS_PER_PAGE) + ((totalCount % ITEMS_PER_PAGE > 0) ? 1 : 0);
    }

    private long getPageCount(Group group) {
        long totalCount = contactService.countByGroup(group);
        return (totalCount / ITEMS_PER_PAGE) + ((totalCount % ITEMS_PER_PAGE > 0) ? 1 : 0);
    }
    private long getPageCount(String pattern) {
        long totalCount = contactService.countByPattern(pattern);
        return (totalCount / ITEMS_PER_PAGE) + ((totalCount % ITEMS_PER_PAGE > 0) ? 1 : 0);
    }
}
