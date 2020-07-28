package pl.szymonprz.cheatsh.plugin.domain

enum class Action(val text: String, val defaultValue: Boolean){
    enableComments("Enable comments", false)
}