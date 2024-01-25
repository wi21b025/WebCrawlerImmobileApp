package model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Collections;
import java.util.List;
import java.util.Map;

@Document(collection = "Users")

public class User
{
    @Id
    private String id;

    private String email;

    private Map<String, Object> filter;

    private List<Map<String, Object>> filters;
    // Store filters as a list of maps

    private String username;
    private String password;

    public String getEmail()
    {
        return email;
    }
    public void setEmail(String email)
    {
        this.email = email;
    }
    public void setFilter(Map<String, Object> filter)
    {
        this.filter = filter;
    }
    public Map<String, Object> getFilter()
    {
        return filter;
    }

   /* public List<Map<String, Object>> getFilters()
    {
        return filters;
    }*/

    public void setFilters(List<Map<String, Object>> filters) {
        this.filters = filters;
    }

    public List<Map<String, Object>> getFilters()
    {
        return filters != null ? filters : Collections.emptyList();
    }
    public void setUsername(String username)
    {
        this.username = username;
    }

    public void setPassword(String password)
    {
        this.password = password;
    }

    public String getUsername() {
        return username;
    }
    public String getPassword() {
        return password;
    }
}
