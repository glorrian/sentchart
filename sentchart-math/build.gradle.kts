plugins {
    id("java")
    id("dev.infochem.cmake-gradle-plugin")
}

group = "dev.sentchart"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    implementation("io.fair-acc:chartfx:11.3.1")
    testImplementation(platform("org.junit:junit-bom:5.9.1"))
    testImplementation("org.junit.jupiter:junit-jupiter")
}

tasks.test {
    useJUnitPlatform()
}

var nativeMathLibraryPath = file(projectDir.absolutePath + findProperty("nativeLibraryPath"))

cmake {
    arguments = listOf("-DLIBRARY_PATH=$nativeMathLibraryPath",
        "-DPROJECT_NAME=${findProperty("nativeLibraryName")}"
    )

}
