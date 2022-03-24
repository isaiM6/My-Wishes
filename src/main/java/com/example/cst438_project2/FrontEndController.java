package com.example.cst438_project2;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Optional;

@Controller
public class FrontEndController {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private ItemListRepository itemListRepository;
    @Autowired
    private ItemRepository itemRepository;




    public static final String BASE_URI = "http://localhost:9090/api/";

    // Authenticated User Object
    //Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();


    @RequestMapping("/")
    String landingPage(Model model){
        return "index";
    }

    @GetMapping("/register")
    String register(Model model){
        User user = new User();
        model.addAttribute("user", user);

        return "register";
    }
    @PostMapping("/register")
    String registerSubmit(@ModelAttribute("user") User user){
        userRepository.save(user);

        return"index";
    }

    @GetMapping("/login")
    String login(Model model){
        return "login";
    }


    @RequestMapping("/allUsers")
    String allUsers(Model model){
        String uri = BASE_URI + "allUsers";
        RestTemplate restTemplate = new RestTemplate();

        User[] users = restTemplate.getForObject(uri, User[].class);


        model.addAttribute("users", users);

        return "allUsers";
    }

    @RequestMapping("/home")
    String home(Model model){
        // Authenticated User Object
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Integer userId = ((MyUserDetails)principal).getUserId();
        Optional<User> optionalUser = userRepository.findById(userId);
        User loggedInUser = optionalUser.get();

        List<ItemList> listsOfUser = loggedInUser.getItemLists();

        model.addAttribute("lists", listsOfUser);
        return "home";
    }
    @RequestMapping(value="/editList", method = RequestMethod.GET)
    String editList(Model model, @RequestParam Integer id) {
        Optional<ItemList> optionalList = itemListRepository.findById(id);
        ItemList list = optionalList.get();
        String listName = list.getName();
        List<Item> items = itemRepository.findItemsByListId(id);
        model.addAttribute("items", items);
        model.addAttribute("listName", listName);

        return "editList";
    }

    @GetMapping("/makeList")
    String createList(Model model){
        return "makeList";
    }

    /*

    @RequestMapping("/addItem")
    String addItem(Model model){
        String uri = BASE_URI + "addItem";
        RestTemplate restTemplate = new RestTemplate();

        Item[] items = restTemplate.getForObject(uri, Item[].class);

        model.addAttribute("items", items);

        return "addItem";
    }
    */


}
