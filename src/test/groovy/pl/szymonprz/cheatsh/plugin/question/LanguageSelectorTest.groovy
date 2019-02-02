package pl.szymonprz.cheatsh.plugin.question

import spock.lang.Specification

class LanguageSelectorTest extends Specification {

    def "should resolve kt to kotlin"() {
        expect:
        new LanguageSelector().langFromExtension("kt") == "kotlin"
    }

    def "should resolve java to java"(){
        expect:
        new LanguageSelector().langFromExtension("java") == "java"
    }

    def "should resolve scala to scala"(){
        expect:
        new LanguageSelector().langFromExtension("scala") == "scala"
    }

    def "should resolve clj to clojure"(){
        expect:
        new LanguageSelector().langFromExtension("clj") == "clojure"
    }

    def "should resolve groovy to groovy"(){
        expect:
        new LanguageSelector().langFromExtension("groovy") == "groovy"
    }

    def "should resolve py to python"(){
        expect:
        new LanguageSelector().langFromExtension("py") == "python"
    }

    def "should resolve uknown language to empty string"(){
        expect:
        new LanguageSelector().langFromExtension("uknown") == ""
    }
}
