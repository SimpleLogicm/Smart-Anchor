package cpm.simplelogic.helper;

/**
 * Created by vinod on 19-10-2016.
 */

public class QuestionModel {

    private String question,Questioncode;
    private int state;
    private int seleectedAnswerPosition;
    private boolean op1Sel,op2Sel,op3Sel,op4Sel,op5Sel; // options
    private String option1value,option2value,option3value,option4value,option5value;

    public boolean isOp1Sel() {
        return op1Sel;
    }

    public void setOp1Sel(boolean op1Sel) {
        this.op1Sel = op1Sel;
        if(op1Sel){ // To make sure only one option is selected at a time
            setOp2Sel(false);
            setOp3Sel(false);
            setOp4Sel(false);
            setOp5Sel(false);
        }
    }

    public boolean isOp2Sel() {
        return op2Sel;
    }

    public void setOp2Sel(boolean op2Sel) {
        this.op2Sel = op2Sel;
        if(op2Sel){
            setOp1Sel(false);
            setOp3Sel(false);
            setOp4Sel(false);
            setOp5Sel(false);
        }
    }

    public boolean isOp3Sel() {
        return op3Sel;
    }

    public void setOp3Sel(boolean op3Sel) {
        this.op3Sel = op3Sel;
        if(op3Sel){
            setOp2Sel(false);
            setOp1Sel(false);
            setOp4Sel(false);
            setOp5Sel(false);
        }
    }

    public boolean isOp4Sel() {
        return op4Sel;
    }

    public void setOp4Sel(boolean op4Sel) {
        this.op4Sel = op4Sel;
        if(op4Sel){
            setOp2Sel(false);
            setOp1Sel(false);
            setOp3Sel(false);
            setOp5Sel(false);
        }
    }


    public boolean isOp5Sel() {
        return op5Sel;
    }

    public void setOp5Sel(boolean op5Sel) {
        this.op5Sel = op5Sel;
        if(op5Sel){
            setOp2Sel(false);
            setOp1Sel(false);
            setOp3Sel(false);
            setOp4Sel(false);
        }
    }



    public int getSeleectedAnswerPosition() {
        return seleectedAnswerPosition;
    }

    public void setSeleectedAnswerPosition(int seleectedAnswerPosition) {
        this.seleectedAnswerPosition = seleectedAnswerPosition;
    }

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }

    public String getQuestion() {
        return question;
    }
    public void setQuestion(String question) {
        this.question = question;
    }

    public String getQuestioncode() {
        return Questioncode;
    }
    public void setQuestioncode(String Questioncode) {
        this.Questioncode = Questioncode;
    }

    public String getoption1value() {
        return option1value;
    }
    public void setoption1value(String option1value) {
        this.option1value = option1value;
    }

    public String getoption2value() {
        return option2value;
    }
    public void setoption2value(String option2value) {
        this.option2value = option2value;
    }

    public String getoption3value() {
        return option3value;
    }
    public void setoption3value(String option3value) {
        this.option3value = option3value;
    }

    public String getoption4value() {
        return option4value;
    }
    public void setoption4value(String option4value) {
        this.option4value = option4value;
    }

    public String getoption5value() {
        return option5value;
    }
    public void setoption5value(String option5value) {
        this.option5value = option5value;
    }
}
