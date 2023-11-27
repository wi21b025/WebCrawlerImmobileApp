package model;

import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "Users")

public class User
{
    private String id;
    private String email;

    public String getEmail()
    {
        return email;
    }

    public void setEmail(String email)
    {
        this.email = email;
    }

}
