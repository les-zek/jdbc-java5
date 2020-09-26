package entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor

public class Question implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(nullable = false, unique = true)
    private String body;

    @Column(nullable = false)
    private String option1;

    @Column(nullable = false)
    private String option2;

    @Column(nullable = false)
    private String option3;

    @Column(nullable = false)
    private String option4;

    @Column(nullable = false)
    private int validOption;

    private int points;


}
