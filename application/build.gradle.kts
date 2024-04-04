plugins {
    id("java")
    id("application")
    id("org.openjfx.javafxplugin") version "0.1.0"
}

group = "dev.sentchart"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    implementation("io.fair-acc:chartfx:11.3.1")
    implementation(project(":sentchart-math"))
    implementation("fr.brouillard.oss:cssfx:11.4.0")
    testImplementation(platform("org.junit:junit-bom:5.9.1"))
    testImplementation("org.junit.jupiter:junit-jupiter")
}

application {
    mainClass.set("dev.sentchart.application.ApplicationInvoker")
}

javafx {
    modules("javafx.controls", "javafx.fxml")
    version = "21";
}

tasks.test {
    useJUnitPlatform()
}

//
tasks.jar {
    manifest.attributes["Main-Class"] = "dev.sentchart.application.ApplicationInvoker"
//    val dependencies = configurations
//        .runtimeClasspath
//        .get()
//        .map(::zipTree) // OR .map { zipTree(it) }
//    from(dependencies)
//    duplicatesStrategy = DuplicatesStrategy.EXCLUDE
}