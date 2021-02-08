package mysql.db.controller;

import mysql.db.domain.Question;
import mysql.db.domain.QuestionAndAnswerRecord;
import mysql.db.domain.Questionnaire;
import mysql.db.domain.Type;
import mysql.db.repository.QuestionAndAnswerRecordDao;
import mysql.db.repository.QuestionnaireDao;
import mysql.db.repository.UserDao;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

@RestController
public class ScoreController {
  private QuestionnaireDao questionnaireDao;
  private UserDao userDao;
  private QuestionAndAnswerRecordDao questionAndAnswerRecordDao;

  public ScoreController(QuestionnaireDao questionnaireDao, UserDao userDao,
      QuestionAndAnswerRecordDao questionAndAnswerRecordDao) {
    this.questionnaireDao = questionnaireDao;
    this.userDao = userDao;
    this.questionAndAnswerRecordDao = questionAndAnswerRecordDao;
  }

  @RequestMapping(value = "/scores", method = RequestMethod.POST)
  public Map<String, Integer> calculateScore(@RequestBody Map<String, Object> payload) {
    Map<String, Integer> results = new HashMap<>();
    String username = (String) payload.get("username");
    String questionnaire = (String) payload.get("questionnaire");
    List<Map<String, String>> choices = (List<Map<String, String>>) payload.get("choices");
    System.out.println(choices);
    Questionnaire q = questionnaireDao.findFirstByDescription(questionnaire);
    if (Objects.isNull(q)) {
      return results;
    }
    List<Question> questions = q.getQuestions();
    for (int i = 0; i < choices.size(); i++) {
      Map<String, String> choice = choices.get(i);
      choice.forEach((no, option) -> {
        Type type = questions.get(Integer.parseInt(no) - 1).getType();
        Integer op = Integer.parseInt(option);
        int score = results.getOrDefault(type.name(), 0);
        results.put(type.name(), score + op * 2);
      });
    }
    QuestionAndAnswerRecord record = QuestionAndAnswerRecord.builder().user(userDao.findFirstByUsername(username))
                                     .scores(results).build();
    questionAndAnswerRecordDao.save(record);
    return results;
  }
}
