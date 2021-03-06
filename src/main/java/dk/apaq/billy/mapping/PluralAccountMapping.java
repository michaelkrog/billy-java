package dk.apaq.billy.mapping;

import dk.apaq.billy.model.Account;
import java.util.List;
import org.springframework.data.domain.Page;

public class PluralAccountMapping extends AccountMapping implements PluralMapping<Account>{
    private List<Account> accounts;

    public List<Account> getAccounts() {
        return accounts;
    }

    public void setAccounts(List<Account> accounts) {
        this.accounts = accounts;
    }

    @Override
    public Page<Account> getPage() {
        accounts.forEach(c -> resolveExtraData(c));
        return resolvePage(getMeta(), accounts);
    }
    
}
