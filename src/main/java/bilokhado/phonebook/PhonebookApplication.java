package bilokhado.phonebook;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class PhonebookApplication {
    @RequestMapping("/")
    @ResponseBody
    String home() {
	return "Hello World!";
    }
    public static void main(String[] args) {
	SpringApplication.run(PhonebookApplication.class, args);
	System.out.println("App started!");
    }
}
