import com.possible_triangle.gradle.features.publishing.DependencyBuilder
import net.minecraftforge.gradle.common.util.MinecraftExtension
import net.minecraftforge.gradle.userdev.jarjar.JarJarProjectExtension
import org.spongepowered.asm.gradle.plugins.MixinExtension

val mod_id: String by extra
val mixin_extras_version: String by extra
val mc_version: String by extra
val registrate_version: String by extra
val multikulti_version: String by extra
val create_version: String by extra
val ponder_version: String by extra
val flywheel_version: String by extra
val jei_version: String by extra
val supplementaries_version: String by extra
val moonlight_lib_version: String by extra
val dye_depot_version: String by extra
val another_furniture_version: String by extra
val comforts_version: String by extra
val supplementaries_squared_version: String by extra
val quark_version: String by extra
val zeta_version: String by extra
val jade_version: String by extra
val farmers_delight_version: String by extra
val clayworks_version: String by extra
val upgrade_aquatic_version: String by extra
val blueprint_version: String by extra
val gallery_version: String by extra
val alexs_caves_version: String by extra
val domestication_innovation_version: String by extra
val citadel_version: String by extra
val chalk_version: String by extra
val create_deco_version: String by extra
val create_railways_version: String by extra
val create_interiors_version: String by extra
val ars_nouveau_version: String by extra
val curios_version: String by extra
val more_concrete_version: String by extra
val alexs_mobs_version: String by extra
val waystones_version: String by extra
val balm_version: String by extra

plugins {
    id("com.possible-triangle.gradle") version ("0.0.0-dev")
}

withKotlin()

forge {
    enableMixins()

    dataGen {
        existing("dye_depot")
        existing("another_furniture")
        existing("supplementaries")
        existing("create")
        existing("comforts")
        existing("quark")
        existing("suppsquared")
        existing("farmersdelight")
        existing("domesticationinnovation")
        existing("createdeco")
        existing("railways")
        existing("chalk")
        existing("upgrade_aquatic")
        existing("waystones")
        existing("moreconcrete")
        existing("interiors")
    }

    includesMod("com.tterrag.registrate:Registrate:${registrate_version}")
    includesMod("com.possible-triangle:multikulti-datagen-forge-fix:${mc_version}-${multikulti_version}")
    // TODO do I need this?
    includesMod("com.possible-triangle:multikulti-core-forge:${mc_version}-${multikulti_version}")
    // includesMod("com.possible-triangle:multikulti-registrate-forge:${mc_version}-${multikulti_version}")
}

configure<MixinExtension> {
    config("${mod_id}.data.mixins.json")
}

// needed because of flywheel accessing the config too early
configure<MinecraftExtension> {
    runs {
        forEach {
            it.property("production", "true")
        }
    }
}

repositories {
    modrinthMaven()
    mavenLocal()

    nexus {
        content {
            includeGroup("com.possible-triangle")
            includeGroup("com.ninni.dye_depot")
        }
    }

    maven {
        url = uri("https://maven.blamejared.com/")
        content {
            includeGroup("mezz.jei")
        }
    }
    maven {
        url = uri("https://maven.tterrag.com/")
        content {
            includeGroup("com.tterrag.registrate")
        }
    }
    maven {
        url = uri("https://maven.createmod.net")
        content {
            includeGroup("com.simibubi.create")
            includeGroup("net.createmod.ponder")
            includeGroup("dev.engine-room.flywheel")
        }
    }
}

val jarJar = the<JarJarProjectExtension>()

dependencies {
    compileOnly(annotationProcessor("io.github.llamalad7:mixinextras-common:${mixin_extras_version}")!!)
    implementation("jarJar"("io.github.llamalad7:mixinextras-forge:${mixin_extras_version}")) {
        jarJar.ranged(this, "[${mixin_extras_version},)")
    }

    modImplementation("com.tterrag.registrate:Registrate:${registrate_version}")
    modImplementation("com.possible-triangle:multikulti-datagen-forge:${mc_version}-${multikulti_version}")
    modImplementation("com.simibubi.create:create-${mc_version}:${create_version}:slim") { isTransitive = false }
    modImplementation("net.createmod.ponder:Ponder-Forge-${mc_version}:${ponder_version}")
    modCompileOnly("dev.engine-room.flywheel:flywheel-forge-api-${mc_version}:${flywheel_version}")
    modImplementation(pack.modrinth.another.furniture)
    modImplementation(pack.modrinth.comforts)
    modImplementation(pack.modrinth.moonlight)
    modImplementation(pack.modrinth.supplementaries)
    modImplementation(pack.modrinth.supplementaries.squared)
    modImplementation(pack.modrinth.quark)
    modImplementation(pack.modrinth.zeta)
    modImplementation(pack.modrinth.farmers.delight)
    modImplementation(pack.modrinth.clayworks)
    modImplementation(pack.modrinth.upgrade.aquatic)
    modImplementation(pack.modrinth.blueprint)
    modImplementation(pack.modrinth.chalk.mod)
    modImplementation(pack.modrinth.create.deco)
    modImplementation(pack.modrinth.domestication.innovation)
    modImplementation(pack.modrinth.alexs.caves)
    modImplementation(pack.modrinth.alexs.mobs)
    modImplementation(pack.modrinth.waystones)

    modRuntimeOnly("dev.engine-room.flywheel:flywheel-forge-${mc_version}:${flywheel_version}")
    modRuntimeOnly("mezz.jei:jei-${mc_version}-forge:${jei_version}")
    modRuntimeOnly("com.ninni.dye_depot:dye_depot:${dye_depot_version}")
    modRuntimeOnly(pack.modrinth.jade)
    modRuntimeOnly(pack.modrinth.citadel)
    modRuntimeOnly(pack.modrinth.create.steam.n.rails)
    modRuntimeOnly(pack.modrinth.interiors)
    modRuntimeOnly(pack.modrinth.curios)
    modRuntimeOnly(pack.modrinth.ars.nouveau)
    modRuntimeOnly(pack.modrinth.gallery)
    modRuntimeOnly(pack.modrinth.more.concrete)
    modRuntimeOnly(pack.modrinth.balm)
}

tasks.processResources {
    duplicatesStrategy = DuplicatesStrategy.EXCLUDE
}

enablePublishing {
    githubPackages()
    nexus()
}

fun DependencyBuilder.addDependencies() {
    required("dye-depot")
    optional("create")
    optional("another-furniture")
    optional("comforts")
    optional("clayworks")
    optional("farmers-delight")
    optional("quark")
    optional("domestication-innovation")
    optional("supplementaries")
    optional("supplementaries-squared")
    optional("alexs-caves")
    optional("ars-nouveau")
    optional("create-deco")
    optional("create-steam-n-rails")
    optional("upgrade-aquatic")
    optional("more-concrete")
    optional("waystones")
    optional("interiors")
}

uploadToCurseforge {
    dependencies {
        addDependencies()
        optional("chalk")
    }
}

uploadToModrinth {
    dependencies {
        addDependencies()
        optional("chalk-mod")
    }

    syncBodyFromReadme()
}

enableSonarQube()
enableSpotless()