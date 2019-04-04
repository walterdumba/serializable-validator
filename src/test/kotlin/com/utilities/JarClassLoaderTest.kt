package com.utilities

import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import java.nio.file.Path
import java.nio.file.Paths
import java.util.*


class JarClassLoaderTest{




    var WORKING_DIR:String = System.getProperty("user.dir")
    var LIBS_PATH:Path     = Paths.get(WORKING_DIR).resolve("input")

    val jarRegex = "(.+\\.jar\$)|(.+\\.war\$)|(.+\\.ear\$)".toRegex()




    @Test fun testLoadClassesFromJar(){

        //Testing timesheetGen.jar
        var classesList: List<Class<*>> = CustomJarClassLoader(LIBS_PATH).load()
        var expectedClasses = Arrays.asList("common.Comment",
        "common.CustomWorkBook",
        "common.DailyReport\$Companion",
        "common.DailyReport",
        "common.DailyReportKt",
        "common.DocumentPart",
        "common.DocumentVisitor",
        "common.ReportBody",
        "common.ReportContent",
        "common.ReportHeader",
        "common.ReportTemplateVisitor\$onBodySectionVisit$1",
        "common.ReportTemplateVisitor\$onHeaderSectionVisit$1",
        "common.ReportTemplateVisitor\$postBodySectionVisit$1",
        "common.ReportTemplateVisitor\$postHeaderSectionVisit$1",
        "common.ReportTemplateVisitor",
        "generator.LazyReportGenerator",
        "generator.strategies.CustomizableTimesheetGenerator",
        "generator.strategies.DefaultTimeSheetGenerator",
        "generator.strategies.TimesheetGenerationStrategy")

        Assertions.assertArrayEquals(
                classesList.stream().map { clazz->clazz.name }.toArray(),
                expectedClasses.toTypedArray() )
    }






}


