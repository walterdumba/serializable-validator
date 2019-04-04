package com.utilities

import java.io.Serializable
import java.lang.reflect.Field

class SerializableIntrospector(

        private val legacyClassLoader: ClassLoader
){

    fun doScan() {
        val serializableClasses = (this.legacyClassLoader as CustomJarClassLoader)
                .load()
                .filter { clazz -> Serializable::class.java.isAssignableFrom(clazz) }
                .toList()
        val (_, classesWithNoSerialVersionUID) =
                serializableClasses
                .partition { clazz -> tryGetSerialVersionUIDField(clazz)!= null }
        show(classesWithNoSerialVersionUID)
    }


    private fun show(clazzes: List<Class<*>>){
        println("Serializable classes without serialVersionUID constant:")
        println()
        clazzes.forEach(::println)
    }


    private fun tryGetSerialVersionUIDField(clazz: Class<*>):Field?{
        return try {
            return clazz.getDeclaredField("serialVersionUID")
        }catch (ex: NoSuchFieldException){
            null
        }
    }


}