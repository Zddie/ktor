package io.ktor.http

import java.net.*

fun URLBuilder.takeFrom(url: String) {
    takeFrom(URI(url))
}

fun URLBuilder.takeFrom(uri: URI) {
    if (uri.port > 0) {
        port = uri.port
    } else {
        when (uri.scheme) {
            "http" -> port = 80
            "https" -> port = 443
        }
    }

    if (uri.userInfo != null && uri.userInfo.isNotEmpty()) {
        val parts = uri.userInfo.split(":")
        user = parts.first()
        password = parts.getOrNull(1)
    }

    uri.scheme?.let { protocol = URLProtocol.createOrDefault(it) }
    uri.host?.let { host = it }
    uri.rawPath?.let {
        encodedPath = when (it) {
            "" -> "/"
            else -> it
        }
    }
    uri.query?.let { parameters.appendAll(parseQueryString(it)) }
    if (uri.query?.isEmpty() == true) {
        trailingQuery = true
    }

    uri.fragment?.let { fragment = it }
}

fun URLBuilder.takeFrom(url: URL) = takeFrom(url.toURI())