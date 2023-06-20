package inframachine.trainer.service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import inframachine.trainer.model.Domain;
import inframachine.trainer.model.Form;
import inframachine.trainer.repository.DomainRepository;
import lombok.Getter;


@Service
public class DatabaseRepository {

    @Autowired
    private DomainRepository repository;

    private long tableCount = 0;               // cacheable
    private int totalOfPages = 0;            // cacheable
    private int totalOfItensInLastPage = 0;  // cacheable

    @Getter
    private int pageSize = 10;


    public Page<Domain> getDomainsInPage(int page) {
        return repository.findAll(PageRequest.of(page, pageSize, Sort.by("id")));
    }


    public long getTableCount() {
        if (tableCount == 0) {
            tableCount = repository.count();
        }
        return tableCount;
    }


    public int getTotalOfPages() {
        if (totalOfPages == 0) {
            totalOfPages = repository.findAll(PageRequest.of(0, pageSize)).getTotalPages();
        }
        return totalOfPages;
    }


    public int getTotalOfItensInLastPage() {
        if (totalOfItensInLastPage == 0) {
            int totalOfPages = getTotalOfPages();
            totalOfItensInLastPage = repository.findAll(PageRequest.of(totalOfPages-1, pageSize)).getNumberOfElements();
        }
        return totalOfItensInLastPage;
    }


    public long getTotalOfDomainsmapped() {
        return repository.countByIsMapped(true);
    }


    public void save(Form form, String id) {
        Domain domain = repository.findById(Integer.valueOf(id)).get();
        domain.setPrimaryLayer(form.getPrimaryLayer());
        domain.setSecondaryLayer(form.getSecondaryLayer());
        if (form.getMethod().equals("SAVE")) {
            domain.setValid(true);
            domain.setMapped(true);
            repository.save(domain);
        } else if (form.getMethod().equals("DELETE")) {
            domain.setValid(false);
            domain.setMapped(false);
            repository.save(domain);
        }
    }

}
