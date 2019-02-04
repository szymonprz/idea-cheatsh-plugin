package pl.szymonprz.cheatsh.plugin.question

import spock.lang.Specification

class QuestionBuilderTest extends Specification {

    def "should replace white chars to '+'"() {
        expect:
        question(input) == result
        where:
        input           | result
        "text text"     | "text+text?Q"
        "text\ttext"    | "text+text?Q"
        "text\ntext"    | "text+text?Q"
    }

    def "should specify context from question"() {
        expect:
        question("java/my question") == "java/my+question?Q"
        question("java/my question/extra data") == "java/my+question/extra+data?Q"
    }

    def "should specify context from extension when not given in question"() {
        expect:
        question("my question", "kt") == "kotlin/my+question?Q"
    }

    def "should not specify context from invalid extension and without context in question"() {
        expect:
        question("my question", "invalidExt") == "my+question?Q"
    }

    def "should specify question even when extension is valid"() {
        expect:
        question("java/my question", "kt") == "java/my+question?Q"
    }

    def "should ignore comments by default"() {
        expect:
        question("java/my question", "kt") == "java/my+question?Q"
    }

    String question(String text){
        return new QuestionBuilder().fromQuestion(text).build()
    }

    String question(String text, String fileExtension){
        return new QuestionBuilder().fromQuestion(text).fromFile(fileExtension).build()
    }
}
