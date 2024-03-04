package com.kick.it.kickit.responses;

import com.kick.it.kickit.entities.Question;
import lombok.Data;

import java.util.List;

@Data
public class QuestionResponse {
    private String massage;
    private List<Question> questionList;
}
