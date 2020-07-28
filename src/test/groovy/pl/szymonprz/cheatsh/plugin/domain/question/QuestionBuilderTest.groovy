package pl.szymonprz.cheatsh.plugin.domain.question

import spock.lang.Specification

class QuestionBuilderTest extends Specification {

    def "should replace white chars to '+'"() {
        expect:
        question(input) == result
        where:
        input        | result
        "text text"  | "text+text"
        "text\ttext" | "text+text"
        "text\ntext" | "text+text"
    }

    def "should specify context from question"() {
        expect:
        question("java/my question") == "java/my+question"
        question("java/my question/extra data") == "java/my+question/extra+data"
    }

    def "should specify context from extension when not given in question"() {
        expect:
        question("my question", "kt") == "kotlin/my+question"
    }

    def "should not specify context from invalid extension and without context in question"() {
        expect:
        question("my question", "invalidExt") == "my+question"
    }

    def "should specify question even when extension is valid"() {
        expect:
        question("java/my question", "kt") == "java/my+question"
    }

    def "should add no comments modifier when comments are disabled"() {
        expect:
        questionWithoutComments("java/my question", "kt") == "java/my+question?Q"
    }

    def "should add answer number modifier"() {
        expect:
        questionWithoutComments("java/my question", 2) == "java/my+question/2?Q"
    }

    String question(String text) {
        return new QuestionBuilder(true, text, null).build()
    }

    String question(String text, String fileExtension) {
        return new QuestionBuilder(true, text, fileExtension).build()
    }

    String questionWithoutComments(String text, String fileExtension) {
        return new QuestionBuilder(false, text, fileExtension).build()
    }

    String question(String text, Integer answerNumber) {
        return new QuestionBuilder(true, text, null).askForAnswerNumber(answerNumber).build()
    }

    String questionWithoutComments(String text, Integer answerNumber) {
        return new QuestionBuilder(false, text, null).askForAnswerNumber(answerNumber).build()
    }
}
