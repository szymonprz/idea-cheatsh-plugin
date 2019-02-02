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