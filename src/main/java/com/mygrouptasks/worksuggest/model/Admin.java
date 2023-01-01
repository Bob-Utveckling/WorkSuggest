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
public class Admin {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int admin_id;
    private String name;
    private String email;
    private String phone;
    private String address;

}
