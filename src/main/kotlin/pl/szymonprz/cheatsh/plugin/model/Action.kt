package pl.szymonprz.cheatsh.plugin.model

enum class Action(val text: String, val defaultValue: Boolean){
    enableComments("Enable comments", false)
}