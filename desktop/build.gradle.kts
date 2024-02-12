import org.jetbrains.compose.desktop.application.dsl.TargetFormat

plugins {
	kotlin("multiplatform")
	id("org.jetbrains.compose")
}

kotlin {
	jvm {
		compilations.all {
			kotlinOptions.jvmTarget = "17"
		}
		withJava()
	}
	sourceSets {
		val jvmMain by getting {
			dependencies {
				implementation(project(":compose"))
				implementation(compose.desktop.currentOs)
				//implementation(Deps.COROUTINES)
			}
		}
		val jvmTest by getting
	}
}

compose.desktop {
	application {
		mainClass = "MainKt"
		nativeDistributions {
			targetFormats(TargetFormat.Dmg, TargetFormat.Msi, TargetFormat.Deb)
			packageName = "BookLibrary"
			packageVersion = "1.0.0"
		}
	}
}
