package com.kick.it.kickit.services.impl;

import com.kick.it.kickit.entities.Options;
import com.kick.it.kickit.entities.Question;
import com.kick.it.kickit.entities.Quiz;
import com.kick.it.kickit.repository.QuestionRepo;
import com.kick.it.kickit.repository.QuizRepository;
import com.kick.it.kickit.services.QuestionService;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Service
public class QuestionServiceImpl implements QuestionService {

    @Autowired
    QuestionRepo questionRepo;

    @Autowired
    QuizRepository quizRepository;

    @Override
    public Question saveQuestion(Question question) {
        return questionRepo.save(question);
    }

    @Override
    public Question getQuestion(Long questionId) {
        return questionRepo.findById(questionId).get();
    }

    @Override
    public List<Question> getQuestions() {
        Authentication principal = SecurityContextHolder.getContext().getAuthentication();
        if(principal.getName().substring(0,11).equalsIgnoreCase("masterAdmin")){
            return questionRepo.findAll();
        }
        return questionRepo.findAll().stream().filter(question -> question.getCreatedBy().equalsIgnoreCase(principal.getName())).toList();
    }

    @Override
    public Question updateQuestion(Question question) {
        return questionRepo.saveAndFlush(question);
    }

    @Override
    public void deleteQuestion(Long questionId) {
        questionRepo.deleteById(questionId);
    }

    @Override
    public List<Question> getQuestionByQuizId(Long quizId) {
       List<Question> localList= questionRepo.findAll();
        List<Question> result = new ArrayList<>();
       for(int i=0;i<localList.size();i++){
           String quizStr = localList.get(i).getQuizIds().substring(0,localList.get(i).getQuizIds().length()-1);

           String[] quizIds= quizStr.split(",");
           for(int j=0;j<quizIds.length;j++){
               System.out.println(quizIds[j]);
               if(quizIds[j]!=null){
                   if(Long.parseLong(quizIds[j])==quizId){
                       result.add(localList.get(i));
                   }
                   else {
                       System.out.println("Not containing quiz id");
                   }
               }
           }
       }
        System.out.println("result"+result);
        return result;
    }

    @Override
    public Question getQuestionByQuestionId(Long questionId) {
        return questionRepo.findById(questionId).get();
    }

    @Override
    public void addToQuiz(String[] quizIds) {
        for(String quizId : quizIds){
            System.out.println(Long.parseLong(quizId));
            Quiz local=quizRepository.findById(Long.parseLong(quizId)).get();

            local.setQuestionLeft(local.getQuestionLeft()-1);
            quizRepository.save(local);

            System.out.println("Local "+local);
        }
    }

    @Override
    public int uploadQuestionFile(MultipartFile readExcelDataFile) throws IOException {
        List<Question> tempQuestionList = new ArrayList<Question>();
        XSSFWorkbook workbook = new XSSFWorkbook(readExcelDataFile.getInputStream());
        XSSFSheet worksheet = workbook.getSheetAt(0);
        Authentication principal = SecurityContextHolder.getContext().getAuthentication();
            System.out.println(worksheet.getPhysicalNumberOfRows());
        for(int i=1; i<worksheet.getPhysicalNumberOfRows(); i++) {
            Question tempQuestion = new Question();

            XSSFRow row = worksheet.getRow(i);
            tempQuestion.setTitle(row.getCell(0).getStringCellValue());
            tempQuestion.setExplanation(row.getCell(1).getStringCellValue());
            tempQuestion.setAnswer(row.getCell(2).getStringCellValue());
            tempQuestion.setQuestionType(row.getCell(3).getStringCellValue());
            tempQuestion.setExamTags(row.getCell(4).getStringCellValue());

            Options options =new Options();
            options.setOption1(row.getCell(5).getStringCellValue());
            options.setOption2(row.getCell(6).getStringCellValue());
            options.setOption3(row.getCell(7).getStringCellValue());
            options.setOption4(row.getCell(8).getStringCellValue());
           if(row.getCell(9)!=null) options.setOption4(row.getCell(9).getStringCellValue());

            tempQuestion.setOptions(options);

            tempQuestion.setTopic(row.getCell(10).getStringCellValue());
            tempQuestion.setSubject(row.getCell(11).getStringCellValue());
            tempQuestion.setQuizIds(row.getCell(12).getStringCellValue());

            tempQuestion.setCreatedBy(principal.getName());
            tempQuestion.setCreatedDate(LocalDate.now());

            tempQuestionList.add(tempQuestion);
        }

        questionRepo.saveAll(tempQuestionList);

        return worksheet.getPhysicalNumberOfRows()-1;
    }

}
