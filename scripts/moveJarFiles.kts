#!/usr/bin/env kscript

import java.io.File
import java.nio.file.Files
import java.nio.file.Paths
import java.nio.file.StandardCopyOption
import kotlin.system.exitProcess

/**
 * Install kscript to run this script - https://github.com/holgerbrandl/kscript
 *
 * This script will find all jar files from current folder and subfolders and
 * move them to the current folder. The purpose is to find all jar files used by
 * our repositories, including transitive dependencies, which is needed in Tink's
 * annual review process. It used to be we needed to upload the jar files which is
 * why they are collected in the current folder, but for 2022 we just needed the names
 * of the dependencies. If this is the case going forward the script can be simplified
 * to accommodate for this simplified use case.
 *
 * To extract dependencies, first clear your local gradle cache - ~/.gradle/caches/modules-2/files-2.1
 * (see https://stackoverflow.com/questions/10834111/gradle-store-on-local-file-system), then
 * sync your project to gradle so it downloads your dependencies, run this scrip from the gradle
 * cache. As this needs to be done for all 3 repositories, core, tink-link and money-manager
 * the cache needs to be removed between each sync.
 */

if (args.isEmpty()) {
    println("Specify the absolute path of the directory to run the script for as the first argument")
    exitProcess(-1)
}

val startDir = File(args[0])

if(!startDir.exists()) {
    println("No directory exists at ${startDir.absolutePath}")
    exitProcess(-1)
} else if(!startDir.isDirectory) {
    println("${startDir.absolutePath} is a file, please specify a directory")
    exitProcess(-1)
}

println("Are you sure you want to move all jar files in subfolders to ${startDir.absolutePath}? [y/n]")

do {
    val input = readLine()
    when {
        isYes(input) -> {
            println("Moving files...")
            moveJarFiles(startDir)
        }
        isNo(input) -> {
            println("Bye!")
        }
        else -> {
            println("Please choose [y/n]")
        }
    }
} while (!isYes(input) && !isNo(input))

fun moveJarFiles(startDirectory: File) {
    var movedFileCount = 0
    startDirectory.walk().forEach { file ->
        if (file.isFile && file.extension.equals("jar", true)) {
            println("Moving ${file.absolutePath} -> ${startDirectory.absolutePath}/${file.name}")
            Files.move(
                Paths.get(file.absolutePath),
                Paths.get(startDirectory.absolutePath, file.name),
                StandardCopyOption.REPLACE_EXISTING
            )
            movedFileCount++
        }
    }
    println("Moved $movedFileCount files")
}

fun isYes(input: String?) = input?.trim().equals("y", true) || input?.trim().equals("yes", true)

fun isNo(input: String?) = input?.trim().equals("n", true) || input?.trim().equals("no", true)
