package ua.kiev.prog;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class CsvService {
    static void writeToCsv(List<Contact> checkedContacts, String path) throws IOException {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(path))){
            for (Contact contact: checkedContacts) {
                writer.write(contact.getName() + ", " + contact.getSurname() + ", " + contact.getPhone() + ", " + contact.getEmail() + ", " + contact.getGroup());
                writer.newLine();
            }
        }
    }

    static List<String[]> readFromCsv(String path) throws IOException{
        List<String[]> readList = new ArrayList<>();
        try(BufferedReader reader = new BufferedReader(new FileReader(path))) {
            String line;
            while ((line = reader.readLine()) != null){
                String[] lineArray =  line.split(",");
                readList.add(lineArray);
            }
        }
        return readList;
    }
}
