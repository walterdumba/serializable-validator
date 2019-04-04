package com.utilities

import org.reflections.ReflectionUtils
import java.io.File
import java.net.URL
import java.net.URLClassLoader
import java.nio.file.Path
import java.util.jar.JarFile
import kotlin.streams.toList

class CustomJarClassLoader(private var jarsDirectory: Path):ClassLoader(){



    val JAR_REGEX   = "(.+\\.jar\$)|(.+\\.war\$)|(.+\\.ear\$)".toRegex()
    val CLAZZ_REGEX = Regex(".+class$")
    val CLAZZ_SUFFIX=".class"
    val DIRECTORY_SEPARATOR_REGEX = Regex("\\/")
    val PACKAGE_SEPARATOR="."

    var urlClassLoader:ClassLoader

    init {
        urlClassLoader = URLClassLoader.newInstance( jarsToURLfrom(jarsDirectory.toFile()), javaClass.classLoader )
    }


    fun load(): List<Class<*>> {
        var clazzes: MutableList<Class<out Any?>>
        val jarsDirAsfile = jarsDirectory.toFile()
        var allClazzesFullQualifiedNameToBeLoad = ArrayList<String>()
        for(f: File in jarsDirAsfile.listFiles() ) {
            val currentJar = JarFile(f)
            val currentJarClasses: List<String> = extractClazzNamesFrom(currentJar)
            allClazzesFullQualifiedNameToBeLoad.addAll(currentJarClasses)
        }
        clazzes = ReflectionUtils.forNames(allClazzesFullQualifiedNameToBeLoad, this.urlClassLoader)
        return clazzes
    }


    private fun jarsToURLfrom(libsDir: File): Array<URL> {
        return libsDir.listFiles()
                .filter { file -> file.name.matches(JAR_REGEX) }
                .map    { file -> file.toURI().toURL()         }
                .toTypedArray()
    }

    private fun extractClazzNamesFrom(currentJar: JarFile): List<String> {
        var currentJarClasses: List<String> = currentJar
                .stream()
                .filter { el -> el.name.matches(this.CLAZZ_REGEX) }
                .map    { t -> t.name
                        .replace(this.DIRECTORY_SEPARATOR_REGEX, this.PACKAGE_SEPARATOR)
                        .replace(CLAZZ_SUFFIX, "")
                }.toList()
        return currentJarClasses
    }




}
