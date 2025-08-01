plugins {
	kotlin("jvm") version "1.9.25"
	kotlin("plugin.spring") version "1.9.25"
	id("org.springframework.boot") version "3.5.4"
	id("io.spring.dependency-management") version "1.1.7"
}

group = "com.example"
version = "0.0.1-SNAPSHOT"

java {
	toolchain {
		languageVersion = JavaLanguageVersion.of(17)
	}
}

repositories {
	mavenCentral()
}

dependencies {
	implementation("org.springframework.boot:spring-boot-starter")
	implementation("org.springframework.boot:spring-boot-starter-web")
	implementation("org.springframework.boot:spring-boot-starter-data-jpa")
	implementation("org.projectlombok:lombok")
	implementation("software.amazon.awssdk:sqs:2.20.0")
	implementation("mysql:mysql-connector-java:8.0.32")
	implementation("org.jetbrains.kotlin:kotlin-reflect")
	testImplementation("org.springframework.boot:spring-boot-starter-test")
	testImplementation("org.jetbrains.kotlin:kotlin-test-junit5")
	testRuntimeOnly("org.junit.platform:junit-platform-launcher")
	testImplementation("org.junit.jupiter:junit-jupiter:5.7.0")
	testImplementation("org.mockito:mockito-core:3.11.2")
	testImplementation("org.mockito:mockito-inline:3.11.2")
	implementation("org.springframework.kafka:spring-kafka:4.0.0-M3")
	testImplementation("com.github.tomakehurst:wiremock-jre8:2.35.0")
	testImplementation("org.eclipse.jetty:jetty-util:11.0.15")
	testImplementation("org.junit.jupiter:junit-jupiter")
	testImplementation("org.mockito:mockito-core")
	testImplementation("org.junit.jupiter:junit-jupiter")
	testImplementation("javax.servlet:javax.servlet-api:4.0.1")
	implementation("log4j:log4j:1.2.16")
	testImplementation("org.springframework.boot:spring-boot-starter-test") {
		exclude(group = "org.junit.vintage", module = "junit-vintage-engine")
	}

	testImplementation("io.rest-assured:rest-assured:5.3.0")
	testImplementation("io.rest-assured:spring-mock-mvc:5.3.0")
	testImplementation("com.fasterxml.jackson.module:jackson-module-kotlin")
}

kotlin {
	compilerOptions {
		freeCompilerArgs.addAll("-Xjsr305=strict")
	}
}

tasks.withType<Test> {
	useJUnitPlatform()
}
