plugins {
	kotlin("multiplatform")
	id(Deps.Plugins.KOVER) version Deps.Plugins.Versions.KOVER
	kotlin("plugin.serialization") version "1.7.21"
}

kotlin {
	jvm {
		compilations.all {
			kotlinOptions.jvmTarget = "17"
		}
	}

	sourceSets {
		val commonMain by getting {
			dependencies {
				implementation(Deps.EXPOSED)
				implementation(Deps.EXPOSED_DAO)
				implementation(Deps.EXPOSED_JDBC)
				implementation(Deps.SQLITE)
				implementation(Deps.COROUTINES)
				//implementation("org.jetbrains.kotlin:kotlin-stdlib:1.7.20")
			}
		}
		val commonTest by getting {
			dependencies {
				implementation(Deps.KOTEST_API)
				implementation(Deps.KOTEST_ASSERT)
			}
		}
		val jvmMain by getting {
			dependencies {
				implementation(Deps.EXPOSED)
				implementation(Deps.EXPOSED_DAO)
				implementation(Deps.EXPOSED_JDBC)
				implementation(Deps.SQLITE)
				implementation(Deps.COROUTINES)
				implementation(Deps.KOTLIN_CSV)
				implementation("org.jetbrains.kotlinx:kotlinx-serialization-json:1.4.1")
			}
		}
		val jvmTest by getting {
			dependencies {
				implementation(Deps.KOTEST)
				implementation(Deps.KOTEST_ASSERT)
			}
		}
	}
}

tasks.withType<Test> {
	useJUnitPlatform()
	finalizedBy(tasks.koverHtmlReport)
}