package pl.szymonprz.cheatsh.plugin.model

import com.intellij.openapi.components.PersistentStateComponent
import com.intellij.openapi.components.State
import com.intellij.util.xmlb.XmlSerializerUtil

@State(name = "CheatshSettings")
class Storage : PersistentStateComponent<Storage> {

    var commentsEnabled = false

    override fun getState(): Storage = this

    override fun loadState(state: Storage): Unit = XmlSerializerUtil.copyBean(state, this)
}