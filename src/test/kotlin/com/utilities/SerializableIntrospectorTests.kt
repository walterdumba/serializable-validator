package com.utilities

import org.junit.jupiter.api.Test
import java.nio.file.Path
import java.nio.file.Paths

class SerializableIntrospectorTests{




    var WORKING_DIR:String = System.getProperty("user.dir")
    var LIBS_PATH: Path = Paths.get(WORKING_DIR).resolve("input")

    @Test fun test_doScan(){

        var introspector = SerializableIntrospector( CustomJarClassLoader(LIBS_PATH) )
        introspector.doScan()
    }

}