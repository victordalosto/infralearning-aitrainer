package inframachine.trainer.controller;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import inframachine.trainer.model.Form;
import inframachine.trainer.service.DatabaseRepository;


@Controller
public class HandleController {

    @Autowired
    private DatabaseRepository repository;

    @PostMapping("/test/{id}")
    public String test(@PathVariable String id,
                       Form form,
                       Model model) {
        repository.save(form.getMethod(), id);
        return "redirect:" + form.getNextPage();
    }
 
}
