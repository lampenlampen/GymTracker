plugins {
	kotlin("multiplatform")
	id("org.jetbrains.compose")
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
				api(compose.runtime)
				api(compose.foundation)
				api(compose.material)
				implementation(project(":common"))
				implementation(Deps.COROUTINES)
			}
		}
		val commonTest by getting {
			dependencies {
				implementation(Deps.KOTEST)
				implementation(Deps.KOTEST_ASSERT)
			}
		}
		val jvmMain by getting {
			dependencies {
				api(compose.preview)
			}
		}
		val jvmTest by getting
	}
}