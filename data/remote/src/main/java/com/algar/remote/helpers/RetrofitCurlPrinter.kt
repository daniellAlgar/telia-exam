package com.algar.remote.helpers

import okhttp3.Interceptor
import okhttp3.Request
import okhttp3.RequestBody
import okhttp3.Response
import okio.Buffer
import java.nio.charset.Charset

private val UTF8 = Charset.forName("UTF-8")

fun RequestBody.toCommand(): String {
    val buffer = Buffer()
    this.writeTo(buffer)
    val contentType = this.contentType()
    val charset = if (contentType == null) {
        UTF8
    } else {
        contentType.charset(UTF8) ?: UTF8
    }

    @Suppress("NULLABILITY_MISMATCH_BASED_ON_JAVA_ANNOTATIONS")
    return buffer.readString(charset)
}

class RetrofitCurlGenerator {
    fun curlCommandForRequest(request: Request): String {

        var command = ShellCommand("curl", listOf("-X" to request.method))

        request.headers.names().forEach { element ->
            command = command.argument("-H", "'$element: ${request.header(element)}'")
        }

        val body = request.body
        if (body != null) {
            if (body.contentType() != null) {
                command = command.argument("-H", "'Content-Type: ${body.contentType()}'")
            }
            if (body.contentLength() != (-1).toLong()) {
                command = command.argument("-H", "'Content-Length: ${body.contentLength()}'")
            }
            command = command.argument("-d" to "'${body.toCommand()}'")
        }

        command = command.argument("-i" to "'${request.url}'")

        return command.toString()
    }
}

class RetrofitCurlPrinterInterceptor(private val logger: Logger) : Interceptor {
    private val generator = RetrofitCurlGenerator()
    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request()

        logger.i { "==> CURL COMMAND: ${request.url}" }
        logger.i { " ${generator.curlCommandForRequest(request)}\n" }
        logger.i { "==> END CURL COMMAND" }

        return chain.proceed(request)
    }
}

data class ShellCommand(
    val name: String,
    val arguments: List<Pair<String, String>>
) {
    fun argument(name: String, value: String): ShellCommand {
        return copy(arguments = arguments + Pair(name, value))
    }
    fun argument(arg: Pair<String, String>): ShellCommand {
        return copy(arguments = arguments + arg)
    }

    override fun toString(): String {
        return arguments.fold(name) { prev, el ->
            "$prev \\\n${el.first} ${el.second}"
        }
    }
}