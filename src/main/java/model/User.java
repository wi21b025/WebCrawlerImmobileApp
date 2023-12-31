package model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Map;

@Document(collection = "Users")

public class User
{
    @Id
    private String id;
    private String email;

    private Map<String, Object> filter;

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

}
