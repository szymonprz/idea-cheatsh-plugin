package pl.szymonprz.cheatsh.plugin.question

class LanguageSelector {
    private val supportedLangages = listOf(
        KotlinLang(),
        JavaLang(),
        ScalaLang(),
        ClojureLang(),
        GroovyLang(),
        PythonLang(),
        CLang(),
        CPPLang(),
        CSharpLang(),
        CoffeeScriptLang(),
        GoLang(),
        PhpLang(),
        RubyLang(),
        SwiftLang(),
        ObjectiveCLang()
    )

    fun langFromExtension(extension: String): String {
        return supportedLangages.firstOrNull { it.accepts(extension) }
            ?.language() ?: ""
    }
}