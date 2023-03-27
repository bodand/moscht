import org.apache.tools.ant.taskdefs.condition.Os
import org.gradle.internal.jvm.Jvm

plugins {
    `cpp-library`
}

library {
    if (Os.isFamily(Os.FAMILY_WINDOWS))
        source.from(file("src/main/cpp/win"))
    else
        source.from(file("src/main/cpp/fallback"))

    binaries.configureEach {
        compileTask.get().includes.from(compileTask.get().targetPlatform.map { nativePlatform ->
            val mainInclude = "${Jvm.current().javaHome.canonicalPath}/include"
            val os = nativePlatform.operatingSystem
            if (os.isMacOsX) return@map arrayOf(mainInclude, "${Jvm.current().javaHome.canonicalPath}/include/darwin")
            if (os.isWindows) return@map arrayOf(mainInclude, "${Jvm.current().javaHome.canonicalPath}/include/win32")
            if (os.isFreeBSD) return@map arrayOf(mainInclude, "${Jvm.current().javaHome.canonicalPath}/include/freebsd")
            return@map arrayOf(mainInclude, "${Jvm.current().javaHome.canonicalPath}/include/linux")
        })
        compileTask.get().isPositionIndependentCode = true
    }
}
