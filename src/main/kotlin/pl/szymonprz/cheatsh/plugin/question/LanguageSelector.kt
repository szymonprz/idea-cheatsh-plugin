package pl.szymonprz.cheatsh.plugin.question

class LanguageSelector {
    private val supportedLangages = listOf(KotlinLang(), JavaLang(), ScalaLang(), ClojureLang(), GroovyLang(), PythonLang())

    fun langFromExtension(extension: String): String {
        return supportedLangages.firstOrNull { it.accepts(extension) }
            ?.language() ?: ""
    }
}