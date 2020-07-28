package pl.szymonprz.cheatsh.plugin.domain.question

import pl.szymonprz.cheatsh.plugin.infrastructure.storage.Storage
import spock.lang.Specification

class QuestionBuilderTest extends Specification {

    private Storage storage = new Storage()

    def setup() {
        storage.commentsEnabled = true
    }

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
        given:
        storage.commentsEnabled = false
        expect:
        question("java/my question", "kt") == "java/my+question?Q"
    }

    def "should add answer number modifier"() {
        given:
        storage.commentsEnabled = false
        expect:
        question("java/my question", 2) == "java/my+question/2?Q"
    }

    String question(String text) {
        return new QuestionBuilder(storage,text, null).build()
    }

    String question(String text, String fileExtension) {
        return new QuestionBuilder(storage, text, fileExtension).build()
    }
    String question(String text, Integer answerNumber) {
        return new QuestionBuilder(storage, text, null).askForAnswerNumber(answerNumber).build()
    }
}
