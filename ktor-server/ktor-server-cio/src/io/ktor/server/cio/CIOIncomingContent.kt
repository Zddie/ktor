package io.ktor.server.cio

import io.ktor.content.*
import io.ktor.http.*
import io.ktor.http.cio.*
import kotlinx.coroutines.experimental.io.*

@Deprecated("IncomingContent is no longer supported", level = DeprecationLevel.ERROR)
class CIOIncomingContent(private val channel: ByteReadChannel,
                         private val headersMap: HttpHeadersMap,
                         request: CIOApplicationRequest) : @Suppress("DEPRECATION_ERROR") IncomingContent {
    override val headers: Headers = request.headers

    override fun readChannel(): ByteReadChannel = channel

    override fun multiPartData(): MultiPartData = CIOMultipartData(channel, headersMap)
}