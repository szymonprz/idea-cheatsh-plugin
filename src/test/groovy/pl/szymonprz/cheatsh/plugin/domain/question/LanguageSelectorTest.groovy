package pl.szymonprz.cheatsh.plugin.domain.question

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

    def "should resolve c to c"(){
        expect:
        new LanguageSelector().langFromExtension("c") == "c"
    }

    def "should resolve cpp to cpp"(){
        expect:
        new LanguageSelector().langFromExtension("cpp") == "cpp"
    }

    def "should resolve cs to csharp"(){
        expect:
        new LanguageSelector().langFromExtension("cs") == "csharp"
    }

    def "should resolve coffee to coffee"(){
        expect:
        new LanguageSelector().langFromExtension("coffee") == "coffee"
    }

    def "should resolve go to go"(){
        expect:
        new LanguageSelector().langFromExtension("go") == "go"
    }

    def "should resolve php to php"(){
        expect:
        new LanguageSelector().langFromExtension("php") == "php"
    }

    def "should resolve rb to ruby"(){
        expect:
        new LanguageSelector().langFromExtension("rb") == "ruby"
    }

    def "should resolve swift to swift"(){
        expect:
        new LanguageSelector().langFromExtension("swift") == "swift"
    }

    def "should resolve m to objective-c"(){
        expect:
        new LanguageSelector().langFromExtension("m") == "objective-c"
    }

    def "should resolve uknown language to empty string"(){
        expect:
        new LanguageSelector().langFromExtension("uknown") == ""
    }
}
