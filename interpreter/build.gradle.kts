plugins {
    id("printscript.java-library-conventions")
}

dependencies{
    implementation(project(":parser"))
    implementation(project(":lexer"))

}