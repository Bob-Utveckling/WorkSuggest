package com.mygrouptasks.worksuggest.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Entity
@Table(name="worksuggest")
public class WorkSuggest {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int worksuggest_id;
    private String title;
    private String description;
    //@Column(name="adminId")
    private int adminid;
    private int personid;
    private String generalstatus;

    @Override
    public String toString() {
        return
                "Worksuggest -- title: " + this.title +
                        ", description: " + this.description +
                        ", adminid: " + this.adminid +
                        ", personid: " + this.personid +
                        ", generalstatus: " + this.getGeneralstatus();
    }
}