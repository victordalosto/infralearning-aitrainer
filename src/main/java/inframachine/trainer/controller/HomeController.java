package inframachine.trainer.controller;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import inframachine.trainer.model.Domain;
import inframachine.trainer.model.Layers;
import inframachine.trainer.model.Pagination;
import inframachine.trainer.service.DatabaseRepository;


@Controller
public class HomeController {

    @Value("#{systemEnvironment['INFRAMACHINE_IMAGES_URL']}")
    private String imageURL;

    @Autowired
    private List<Layers> primaryLayer;

    @Autowired
    private List<Layers> secondaryLayer;

    @Autowired
    private DatabaseRepository databaseRepository;


    @GetMapping("/")
    public String home(Model model, Pagination pagination) {
        return execute(model, pagination);
    }


    @GetMapping("/{type}")
    public String home(@PathVariable("type") String type, Model model, Pagination pagination) {
        System.out.println(type);
        return execute(model, pagination);
    }


    private String execute(Model model, Pagination pagination) {
        model.addAttribute("primaryLayer", primaryLayer);
        model.addAttribute("secondaryLayer", secondaryLayer);

        model.addAttribute("total", databaseRepository.getTableCount());
        model.addAttribute("count", databaseRepository.getTotalOfDomainsmapped());

        Page<Domain> domains = databaseRepository.getDomainsInPage(pagination.getPage());
        model.addAttribute("domains", domains);
        model.addAttribute("domain", domains.getContent().get(pagination.getItem()));
        model.addAttribute("page", domains.getPageable().getPageNumber());
        model.addAttribute("item", pagination.getItem());

        model.addAttribute("imageurl", imageURL);

        return "home";
    }




}
