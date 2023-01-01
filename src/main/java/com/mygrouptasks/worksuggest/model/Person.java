package com.mygrouptasks.worksuggest.model;


import com.mygrouptasks.worksuggest.config.ApplicationFunctionsConfig;
import com.mygrouptasks.worksuggest.config.MessagesConfig;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.context.annotation.Import;

import javax.json.Json;
import javax.json.JsonObject;
import javax.json.JsonReader;
import javax.persistence.*;
import javax.validation.constraints.*;
import java.io.StringReader;
import java.util.HashSet;
import java.util.Set;

@Import({MessagesConfig.class})
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
public class Person {

    @Transient
    MessagesConfig messagesConfig = new MessagesConfig();
    @Transient
    ApplicationFunctionsConfig applicationFunctionsConfig = new ApplicationFunctionsConfig();

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int person_id;
    @NotBlank
    @NotEmpty
    private String username;
    @Size(min=3, max=50)
    private String password;
    @NotNull
    @Size(min=2, max=30)
    private String name;
    @Email(message = "Email Address")
    private String email;
    private String phone;
    private String address;

    @ManyToMany(cascade = {
            CascadeType.ALL
    })
    @JoinTable(
            name = "persons_tasks",
            joinColumns = {
                    @JoinColumn(name = "person_id")
            },
            inverseJoinColumns = {
                    @JoinColumn(name = "task_id")
            }
    )
    Set<Task> tasks = new HashSet<Task>();

    public Set<Task> getTasks() {
        return tasks;
    }

    public void setTasks(Set<Task> tasks) {
        this.tasks = tasks;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    @Override
    public String toString() {
        return "Person -- name: " + this.getName() + ", username: " + this.getUsername();
    }

    public Person toEntity(Person person) {
        System.out.println("received person. checking and converting to entity...");

        Person personToEntity = new Person();

        System.out.println("- username: " +  checkUsername(person.getUsername()));
        System.out.println("- password: " + checkPassword(person.getPassword()));
        System.out.println("- name: " + checkName(person.getName()));
        System.out.println("- email: " + checkEmail(person.getEmail()));
        System.out.println("- phone: " + checkPhone(person.getPhone()));
        System.out.println("- address: " + checkAddress(person.getAddress()));

        if (
                checkUsername(person.getUsername()) == "ok" &&
                        checkPassword(person.getPassword()) == "ok" &&
                        checkName(person.getName()) == "ok" &&
                        checkEmail(person.getEmail()) == "ok" &&
                        checkPhone(person.getPhone()) == "ok" &&
                        checkAddress(person.getAddress()) == "ok")

        {

        } else {
            System.out.println("- warning: to entity check failed");
        }

        return personToEntity;
    }

    private String checkAddress (String address) {
        if (address.length()>100) {
            return messagesConfig.messagesEng().errAddress;
        }
        return "ok";
    }

    private String checkPhone (String phone) {
        if (phone.length() > 20) {
            return messagesConfig.messagesEng().errPhone;
        }
        return "ok";
    }

    private String checkEmail (String email) {
        if (!applicationFunctionsConfig.ApplicationFunctions().isValidEmail(email)) {
            return messagesConfig.messagesEng().errInvalidEmail;
        }
        return "ok";
    }

    private String checkName (String name) {
        if (applicationFunctionsConfig.ApplicationFunctions().hasOneOfChars(name, "!#$%&/(){}@) ")) {
            return messagesConfig.messagesEng().errNameContainsIllegalChars;
        }
        return "ok";
    }

    private String checkPassword (String password) {
        if (password.length() > 50 || password.length() < 4 ) {
            return messagesConfig.messagesEng().errPassword;
        }
        return "ok";
    }

    private String checkUsername (String username) {
        if (username.length() < 3) {
            return messagesConfig.messagesEng().errUsernameShort;
        }
        if (username.length() > 25) {
            return messagesConfig.messagesEng().errUsernameLong;
        }
        if (!applicationFunctionsConfig.ApplicationFunctions().isAlphaNumeric(username)) {
            return messagesConfig.messagesEng().errUsernameNotAlphaNumeric;
        }
        return "ok";
    }

    //toEntity
    public Person toEntityFromJsonString(String strToConvert) {
        System.out.println("received str: " + strToConvert);
        Person person = new Person();
        try {
            JsonReader reader = Json.createReader(new StringReader(strToConvert));
            JsonObject jsonObject = reader.readObject();
            person.setName(jsonObject.getString("username"));
            person.setName(jsonObject.getString("password"));
            person.setName(jsonObject.getString("name"));
            person.setEmail(jsonObject.getString("email"));
            person.setPhone(jsonObject.getString("phone"));
            person.setAddress(jsonObject.getString("address"));
            return person;
        } catch (NullPointerException npe) {
            System.out.println("error: not all JSON key/value pairs submitted.\n" + npe);
            person.setName("error in JSON submit");
            return person;
        } catch (Exception e) {
            System.out.println("error doing toEntity: " + e);
            person.setName("error doing toEntity");
            return person;
        }


    }
}
