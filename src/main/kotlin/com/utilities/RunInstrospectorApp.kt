package com.utilities

import java.nio.file.Path
import java.nio.file.Paths

class  RunInstrospectorApp{







companion object {

    var WORKING_DIR:String = System.getProperty("user.dir")
    var LIBS_PATH: Path = Paths.get(WORKING_DIR).resolve("input")

    @JvmStatic fun main(args:Array<String>){
        SerializableIntrospector( CustomJarClassLoader(LIBS_PATH)).doScan()
    }
}

}