package pl.com.bottega.photostock.sales.model.users;

import java.util.ArrayList;
import java.util.List;

/**
 * lublin-3-16-photostock
 * Sergii
 * 2016-05-13.
 */

public class Company {
    private String name;
    private List<Client> employ = new ArrayList<>();

    public Company(String name) {
        this.name = name;
    }

    private void addEmploy(Client employ){
        this.employ.add(employ);
    }

    public String getName() {
        return name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Company company = (Company) o;

        if (!name.equals(company.name)) return false;
        return employ != null ? employ.equals(company.employ) : company.employ == null;
    }

    @Override
    public int hashCode() {
        int result = name.hashCode();
        result = 31 * result + (employ != null ? employ.hashCode() : 0);
        return result;
    }
}
