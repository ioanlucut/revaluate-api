#!/usr/bin/env groovy
package com.revaluate.constants

// Set up enum attributes' names and data types
attributes = ['name': 'String', 'base': 'String', 'sub': 'String']

// Base package name off command-line parameter if provided
packageName = args.length > 0 ? args[0] : "com.revaluate.constants"

NEW_LINE = System.getProperty("line.separator")
SINGLE_INDENT = '  '
DOUBLE_INDENT = SINGLE_INDENT.multiply(2)
outputFile = new File("Constants.java")
outputFile.write "package ${packageName};${NEW_LINE.multiply(2)}"
outputFile << "public enum Constants${NEW_LINE}"
outputFile << "{${NEW_LINE}"

outputFile << generateEnumConstants()

// Build enum attributes
attributesSection = new StringBuilder();
attributesAccessors = new StringBuilder();
attributesCtorSetters = new StringBuilder();
attributes.each
        {
            attributesSection << generateAttributeDeclaration(it.key, it.value)
            attributesAccessors << buildAccessor(it.key, it.value) << NEW_LINE
            attributesCtorSetters << buildConstructorAssignments(it.key)
        }
outputFile << attributesSection
outputFile << NEW_LINE

outputFile << generateParameterizedConstructor(attributes)
outputFile << NEW_LINE

outputFile << attributesAccessors

outputFile << '}'

def String generateEnumConstants() {
    // Get input from XML sourceS
    constants = new XmlSlurper().parse("constants.xml")
    def enumConstants = new StringBuilder()
    constants.Constant.each
            {
                enumConstants << SINGLE_INDENT
                enumConstants << it.@base.toString().replace(' ', '_').toUpperCase()
                enumConstants << '_'
                enumConstants << it.@name.toString().replace(' ', '_').toUpperCase()
                enumConstants << "(\"${it.@name}\", \"${it.@base}\", \"${it.@path}\", \"${it.@path}\\\"),"
                enumConstants << NEW_LINE
            }
    // Subtract three off end of substring: one for new line, one for extra comma,
    // and one for zero-based indexing.
    returnStr = new StringBuilder(enumConstants.toString().substring(0, enumConstants.size() - 3))
    returnStr << ');' << NEW_LINE.multiply(2)
    return returnStr
}

def String generateAttributeDeclaration(String attrName, String attrType) {
    return "${SINGLE_INDENT}private ${attrType} ${attrName};${NEW_LINE}"
}

def String buildAccessor(String attrName, String attrType) {
    returnStr = new StringBuilder()
    returnStr << SINGLE_INDENT << "public ${attrType} get${capitalizeFirstLetter(attrName)}()" << NEW_LINE
    returnStr << SINGLE_INDENT << '{' << NEW_LINE
    returnStr << DOUBLE_INDENT << "return this.${attrName};" << NEW_LINE
    returnStr << SINGLE_INDENT << '}' << NEW_LINE
    return returnStr
}

def String generateParameterizedConstructor(Map<String, String> attributesMap) {
    constructorInit = new StringBuilder()
    constructorInit << SINGLE_INDENT << 'Constants('
    attributesMap.each
            {
                constructorInit << "final ${it.value} new${capitalizeFirstLetter(it.key)}, "
            }
    constructorFinal = new StringBuilder(constructorInit.substring(0, constructorInit.size() - 2))
    constructorFinal << ')'
    constructorFinal << NEW_LINE << SINGLE_INDENT << '{' << NEW_LINE
    constructorFinal << attributesCtorSetters
    constructorFinal << SINGLE_INDENT << "}${NEW_LINE}"
    return constructorFinal
}

def String buildConstructorAssignments(String attrName) {
    return "${DOUBLE_INDENT}this.${attrName} = new${capitalizeFirstLetter(attrName)};${NEW_LINE}"
}

def String capitalizeFirstLetter(String word) {
    firstLetter = word.getAt(0)
    uppercaseLetter = firstLetter.toUpperCase()
    return word.replaceFirst(firstLetter, uppercaseLetter)
}
