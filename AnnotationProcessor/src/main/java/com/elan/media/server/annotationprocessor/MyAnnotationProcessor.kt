package com.elan.media.server.annotationprocessor

import com.google.devtools.ksp.processing.Dependencies
import com.google.devtools.ksp.processing.Resolver
import com.google.devtools.ksp.processing.SymbolProcessor
import com.google.devtools.ksp.processing.SymbolProcessorEnvironment
import com.google.devtools.ksp.processing.SymbolProcessorProvider
import com.google.devtools.ksp.symbol.KSAnnotated
import com.google.devtools.ksp.symbol.KSClassDeclaration
import com.google.devtools.ksp.symbol.KSFunctionDeclaration
import com.google.devtools.ksp.symbol.KSType
import java.io.OutputStreamWriter

class MyAnnotationProcessor(private val environment: SymbolProcessorEnvironment) : SymbolProcessor {

    override fun process(resolver: Resolver): List<KSAnnotated> {

        var functions = mutableMapOf<String, String>()

        val symbols = resolver.getSymbolsWithAnnotation("com.elan.media.server.shared.annotation.Navigation")
            .filterIsInstance<KSFunctionDeclaration>()

        val routeMappings = mutableListOf<String>()

        symbols.forEach { function ->

            val functionName = function.simpleName.asString()

            // Extract `@Navigation` details
            val navigationType = function.annotations
                .first { it.shortName.asString() == "Navigation" }
                .arguments.forEach {
                    environment.logger.warn("✅ Found NavigationType Enum: NavigationType.$it")

                    // Step 1: Extract `KSType`
                    val ksType = it.value as? KSType ?: return@forEach
                    environment.logger.warn("✅ Found NavigationType Enum 1: NavigationType.$ksType")

                    // Step 2: Check if the type is `NavigationType`
                    val classDeclaration = ksType.declaration as? KSClassDeclaration
                    environment.logger.warn("✅ Found NavigationType Enum 3: NavigationType.$classDeclaration")

                    routeMappings.add("$functionName(NavigationType.$classDeclaration),")
                }

        }


        // Generate Navigation Registry
        val generatedCode = """
            package com.elan.media.server.android.ui.common
            import com.elan.media.server.shared.enums.NavigationType
            
            enum class NavigationItems(val navigationType: NavigationType) {
            
                ${routeMappings.joinToString("")};
                
                companion object {
                    fun getMainMenuItems(): List<NavigationItem> {
                        return NavigationItem.entries.filter { NavigationType.MAIN_MENU == it.navigationType }
                    }
                }
            
            }
        """.trimIndent()

        val fileName = "Navigator"
        val packageName = "com.elan.media.server.android.annotation"


        try {// Create new file with fresh content using KSP's CodeGenerator
            val file = environment.codeGenerator.createNewFile(
                Dependencies(false, *resolver.getAllFiles().toList().toTypedArray()),
                packageName,
                fileName
            )
            OutputStreamWriter(file, Charsets.UTF_8).use { writer ->
                writer.write(generatedCode)
            }
        } catch (e: Exception) {
            return emptyList()
        }

        return emptyList()

    }
}
class MyProcessorProvider : SymbolProcessorProvider {
    override fun create(environment: SymbolProcessorEnvironment): SymbolProcessor {
        return MyAnnotationProcessor(environment)
    }
}
