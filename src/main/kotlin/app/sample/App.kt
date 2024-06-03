package app.sample

import io.quarkus.runtime.Quarkus
import io.quarkus.runtime.annotations.QuarkusMain

@QuarkusMain
class App

fun main(args: Array<String>) {
    Quarkus.run(*args)
}