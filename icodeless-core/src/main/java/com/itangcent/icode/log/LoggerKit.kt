package com.itangcent.icode.log

import org.apache.commons.lang3.exception.ExceptionUtils

fun Logger.traceWarn(msg: String, e: Throwable) {
    this.log(msg)
    this.traceError(e)
}

fun Logger.traceError(msg: String, e: Throwable) {
    this.log(msg)
    this.traceError(e)
}

fun Logger.traceError(e: Throwable) {
    this.log(ExceptionUtils.getStackTrace(e))
}