package com.elan.media.server.annotationprocessor

import com.google.devtools.ksp.processing.CodeGenerator
import com.google.devtools.ksp.processing.Dependencies
import com.google.devtools.ksp.processing.KSPLogger
import com.google.devtools.ksp.processing.Resolver
import com.google.devtools.ksp.processing.SymbolProcessor
import com.google.devtools.ksp.processing.SymbolProcessorEnvironment
import com.google.devtools.ksp.processing.SymbolProcessorProvider
import com.google.devtools.ksp.symbol.KSAnnotated
import com.google.devtools.ksp.symbol.KSFunctionDeclaration

class MyAnnotationProcessor(private val codeGenerator: CodeGenerator, private val logger: KSPLogger) : SymbolProcessor {

    override fun process(resolver: Resolver): List<KSAnnotated> {
        val symbols = resolver.getSymbolsWithAnnotation("com.elan.media.server.android.annotation.Navigation")
        val collectedData = mutableMapOf<String, com.elan.media.server.shared.enums.NavigationType>()

        symbols.forEach { symbol ->
            if (symbol is KSFunctionDeclaration) {
                val annotation = symbol.annotations.firstOrNull { it.shortName.asString() == "Navigation" }
                val annotationValue = annotation?.arguments?.firstOrNull()?.value as? com.elan.media.server.shared.enums.NavigationType
                annotationValue?.let { collectedData[symbol.simpleName.asString()] = it }
                logger.info("Found annotated function: ${symbol.qualifiedName?.asString()}")
            }
        }

        if (collectedData.isNotEmpty()) {
            generateCode(collectedData)
        }

        return emptyList()
    }

    private fun generateCode(data: Map<String, com.elan.media.server.shared.enums.NavigationType>) {
        val file = codeGenerator.createNewFile(
            dependencies = Dependencies(false),
            packageName = "com.example.generated",
            fileName = "GeneratedNavigation"
        )

        file.bufferedWriter().use { writer ->
            writer.write("package com.example.generated\n\n")
            writer.write("object GeneratedNavigation {\n")
            data.forEach { (funcName, navType) ->
                writer.write("    val $funcName = \"$navType\"\n")
            }
            writer.write("}")
        }
    }
}

class MyProcessorProvider : SymbolProcessorProvider {
    override fun create(environment: SymbolProcessorEnvironment): SymbolProcessor {
        return MyAnnotationProcessor(environment.codeGenerator, environment.logger)
    }
}
