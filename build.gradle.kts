plugins {
    application
    id("org.openjfx.javafxplugin") version "0.1.0"
}

repositories { mavenCentral() }

dependencies {
    testImplementation("org.junit.jupiter:junit-jupiter:5.9.2")
    testRuntimeOnly("org.junit.platform:junit-platform-launcher")
    testImplementation("org.junit.jupiter:junit-jupiter:5.10.0")
    implementation("com.google.guava:guava:32.0.1-jre")
    implementation("org.openjfx:javafx-controls:23")
    implementation("org.openjfx:javafx-fxml:23")
    implementation("org.openjfx:javafx-base:23")
    implementation("org.openjfx:javafx-graphics:23")
    implementation("org.openjfx:javafx-media:23")
    implementation("org.openjfx:javafx-swing:23")
    implementation("org.openjfx:javafx-web:23")
    implementation(files("libs/json-20140107.jar"))
    implementation(files("libs/org.eclipse.paho.client.mqttv3-1.2.0.jar"))
    implementation("com.microsoft.sqlserver:mssql-jdbc:12.10.0.jre11")
}

javafx {
    modules(
            "javafx.controls",
            "javafx.fxml",
            "javafx.base",
            "javafx.graphics",
            "javafx.media",
            "javafx.swing",
            "javafx.web"
    )
}

java {
    toolchain { languageVersion.set(JavaLanguageVersion.of(23)) }

	sourceSets {
    main {
        java.setSrcDirs(listOf("Lektion16"))
    }
}
	
    sourceSets["main"].resources {
        setSrcDirs(
                listOf("src/resources")
        ) // Points to Application/resources as the resources directory
    }
    sourceSets["test"].java {
        setSrcDirs(listOf("src/Test")) // Configure test directory if applicable
    }
}

tasks.test {
    useJUnitPlatform()
    testLogging { showStandardStreams = true }
}

tasks.named<JavaExec>("run") {
    standardInput = System.`in`
    enableAssertions = true
    workingDir = project.projectDir
    jvmArgs = listOf("-Djava.awt.headless=true")
    systemProperty("java.awt.headless", "true")
}

application { mainClass.set("transactionsVersion") }
