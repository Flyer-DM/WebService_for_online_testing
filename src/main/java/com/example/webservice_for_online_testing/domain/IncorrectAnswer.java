package com.example.webservice_for_online_testing.domain;

/**
 * Class used after testing to show user correct answer and his answer for question where he made a mistake.
 * @see Question
 * @author Kondrashov Dmitry
 * @version 1.0
 */
public class IncorrectAnswer {

    /** @see Question#getProblem() */
    private final String question;

    /**
     * @see Question#getVariant1()
     * @see Question#getVariant2()
     * */
    private final String yourAnswer;

    /** @see Question#getAnswer() */
    private final String correctAnswer;

    /**
     * Constructor of the class
     * @param question the content of the question
     * @param yourAnswer given answer of the user
     * @param correctAnswer correct answer for the question
     */
    public IncorrectAnswer(String question, String yourAnswer, String correctAnswer) {
        this.question = question;
        this.yourAnswer = yourAnswer;
        this.correctAnswer = correctAnswer;
    }

    /** Getter method */
    public String getQuestion() {
        return question;
    }

    /** Getter method */
    public String getYourAnswer() {
        return yourAnswer;
    }

    /** Getter method */
    public String getCorrectAnswer() {
        return correctAnswer;
    }
}
