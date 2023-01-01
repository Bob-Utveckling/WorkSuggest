package com.mygrouptasks.worksuggest.model;


import com.mygrouptasks.worksuggest.config.ApplicationFunctionsConfig;
import com.mygrouptasks.worksuggest.config.MessagesConfig;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.context.annotation.Import;

import javax.persistence.*;

@Import({MessagesConfig.class})
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
public class Log {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int log_id;
    private String message;
    private String datetime;
}
