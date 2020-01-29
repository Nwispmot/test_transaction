package transactions.runner;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import transactions.model.User;
import transactions.service.UserService;
import java.util.Arrays;

@Component
public class UserRunner implements CommandLineRunner {


    @Autowired
    private UserService userService;

    @Override
    public void run(String... args) throws Exception {
        final String GREEN = "\u001B[32m";
        final String RESET = "\u001B[0m";
        final String RED = "\u001B[31m";
        try {
            User user1 = new User("User1", "Ops", 80L);
            User user2 = new User("User2", "Tech", 120000L);
            User user3 = new User("User3", "Tech", 220000L);
            userService.insert(Arrays.asList(
                    user1, user2, user3
            ));
        }
        catch (RuntimeException ex){
            System.out.println(RED + "Exception has been caught in try 1..." + ex.getMessage());
            System.out.println("roll back" + RESET);
        }

        try {
            User user4 = new User("User4", "Ops", 18000L);
            User user5 = new User("User5", "Tech", 320000L);
            User user6 = new User("User6", "Ops", 200000L);
            User user7 = new User("User7", "Tech", 100000L);
            User user8 = new User("InvalidName", "Tech", 150000L);
            userService.insert(Arrays.asList(
                    user4, user5, user6, user7, user8
            ));
        }
        catch (RuntimeException ex){
            System.out.println(RED + "Exception has been caught in try 2..." + ex.getMessage());
            System.out.println("roll back" + RESET);
        }

        System.out.println(GREEN + userService.getUsers() + RESET);
    }
}
