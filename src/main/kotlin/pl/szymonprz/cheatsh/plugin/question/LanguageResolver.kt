package pl.szymonprz.cheatsh.plugin.question

interface LanguageResolver {
    fun accepts(extension: String): Boolean
    fun language(): String
}

class KotlinLang : LanguageResolver {
    override fun language(): String = "kotlin"
    override fun accepts(extension: String): Boolean = extension == "kt"
}

class JavaLang : LanguageResolver {
    override fun language(): String = "java"
    override fun accepts(extension: String): Boolean = extension == "java"
}

class ScalaLang : LanguageResolver {
    override fun language(): String = "scala"
    override fun accepts(extension: String): Boolean = extension == "scala"
}

class ClojureLang : LanguageResolver {
    override fun language(): String = "clojure"
    override fun accepts(extension: String): Boolean = extension == "clj"
}

class GroovyLang : LanguageResolver {
    override fun language(): String = "groovy"
    override fun accepts(extension: String): Boolean = extension == "groovy"
}

class PythonLang : LanguageResolver {
    override fun language(): String = "python"
    override fun accepts(extension: String): Boolean = extension == "py"
}

class CLang : LanguageResolver {
    override fun language(): String = "c"
    override fun accepts(extension: String): Boolean = extension == "c"
}

class CPPLang : LanguageResolver {
    override fun language(): String = "cpp"
    override fun accepts(extension: String): Boolean = extension == "cpp"
}

class CSharpLang : LanguageResolver {
    override fun language(): String = "csharp"
    override fun accepts(extension: String): Boolean = extension == "cs"
}

class CoffeeScriptLang : LanguageResolver {
    override fun language(): String = "coffee"
    override fun accepts(extension: String): Boolean = extension == "coffee"
}

class GoLang : LanguageResolver {
    override fun language(): String = "go"
    override fun accepts(extension: String): Boolean = extension == "go"
}

class PhpLang : LanguageResolver {
    override fun language(): String = "php"
    override fun accepts(extension: String): Boolean = extension == "php"
}

class RubyLang : LanguageResolver {
    override fun language(): String = "ruby"
    override fun accepts(extension: String): Boolean = extension == "rb"
}

class SwiftLang : LanguageResolver {
    override fun language(): String = "swift"
    override fun accepts(extension: String): Boolean = extension == "swift"
}

class ObjectiveCLang : LanguageResolver {
    override fun language(): String = "objective-c"
    override fun accepts(extension: String): Boolean = extension == "m"
}