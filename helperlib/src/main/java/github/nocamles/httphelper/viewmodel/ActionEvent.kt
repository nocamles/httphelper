package github.nocamles.httphelper.viewmodel

import kotlinx.coroutines.Job

open class BaseActionEvent

class ShowLoadingEvent(val job: Job?) : BaseActionEvent()

object DismissLoadingEvent : BaseActionEvent()

object FinishViewEvent : BaseActionEvent()

class ShowToastEvent(val message: String) : BaseActionEvent()