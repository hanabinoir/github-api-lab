package xyz.hanabinoir.githubuserslab.utility

import java.time.ZoneId
import java.time.ZonedDateTime
import java.time.format.DateTimeFormatter

fun String.toLocalDateTime(): String {
    val zonedDateTime = ZonedDateTime.parse(this)
        .withZoneSameInstant(ZoneId.systemDefault())
    val formattedDate = zonedDateTime.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"))
    return formattedDate
}