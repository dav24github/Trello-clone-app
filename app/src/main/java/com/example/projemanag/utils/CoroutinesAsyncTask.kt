package com.example.projemanag.utils

import kotlinx.coroutines.Dispatchers.Default
import kotlinx.coroutines.Dispatchers.Main
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

abstract class CoroutinesAsyncTask<Params, Progress, Result> {
    open fun onPreExecute(){}

    abstract fun doInBackground(vararg params: Params?): Result

    open fun onProgressUpdate(vararg values: Progress?) {}

    open fun onPostExecute(result: Result?) {}

    open fun onCancelled(result: Result?) {}

    protected var isCancelled = false

    protected fun publishProgress(vararg progress: Progress?){
        GlobalScope.launch(Main) {
            onProgressUpdate(*progress)
        }
    }

    fun execute(vararg params: Params?){
        isCancelled = false
        onPreExecute()
        GlobalScope.launch(Default) {
            val result = doInBackground(*params)

            withContext(Main){
                onPostExecute(result)
            }
        }
    }

    fun cancel(mayInterruptIfRunning: Boolean){
        isCancelled = true
    }
}